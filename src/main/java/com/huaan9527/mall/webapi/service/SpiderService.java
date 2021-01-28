package com.huaan9527.mall.webapi.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huaan9527.mall.webapi.domain.Collocation;
import com.huaan9527.mall.webapi.domain.CollocationProduct;
import com.huaan9527.mall.webapi.domain.enums.DataType;
import com.huaan9527.mall.webapi.domain.enums.ProductStatus;
import com.huaan9527.mall.webapi.exception.MsException;
import com.huaan9527.mall.webapi.repository.CollocationProductRepository;
import com.huaan9527.mall.webapi.repository.CollocationRepository;
import com.huaan9527.mall.webapi.service.operation.api.GoodsDetailApi;
import com.huaan9527.mall.webapi.utils.HttpsClientUtil;
import com.huaan9527.mall.webapi.utils.URLUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class SpiderService {
    private static final String nothingListUrl = "https://api.nothing.la/nothing/wechat/item/common/matchList.do";
    private static final String nothingDetailUrl = "https://api.nothing.la/nothing/wechat/match/common/matchDetail.do";
    private static final String yiXinListUrl = "http://api.yyixxx.com/api/discovery/match";
    private static final String yiXinDetailUrl = "http://api.yyixxx.com/api/match/find";
    private static final String yiXinImageUrlPre = "http://image.yyixxx.com/";

    private CollocationRepository collocationRepository;
    private CollocationProductRepository collocationProductRepository;
    private GoodsDetailApi goodsDetailApi;
    private ApplicationContext context;
    private RestTemplate restTemplate;
    private TagService tagService;

    public Integer spiderYiXinCollocationList(Integer page, Integer size) {
        MultiValueMap<String, String> parameter = new LinkedMultiValueMap<>();
        parameter.add("device", "1");
        parameter.add("type", "0");
        parameter.add("limit", size.toString());
        parameter.add("page", page.toString());
        String body = requestYixin(yiXinListUrl, parameter);

        JSONArray arr = JSON.parseObject(body).getJSONObject("return").getJSONArray("data");
        SpiderService spiderService = context.getBean(SpiderService.class);
        int amount = 0;
        for (Object o : arr) {
            try {
                JSONObject jb = (JSONObject) o;
                boolean result = spiderService.spiderYiXinCollocation(jb.getLong("match_id"));
                if (result) {
                    amount += 1;
                }
            } catch (Exception e) {
                log.error("保存Collocation失败", e);
            }
        }
        return amount;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public boolean spiderYiXinCollocation(Long matchId) {
        long amount = collocationRepository.countBySourceTypeAndSourceId(Collocation.YI_XIN_SOURCE_TYPE, matchId.toString());
        if (amount > 0) {
            log.error("Yi Xin matchId[{}] already save", matchId);
            return false;
        }
        MultiValueMap<String, String> parameter = new LinkedMultiValueMap<>();
        parameter.add("device", "1");
        parameter.add("match_id", matchId.toString());
        String body = requestYixin(yiXinDetailUrl, parameter);
        JSONObject match = JSON.parseObject(body).getJSONObject("return");

        Random random = new Random();
        Collocation collocation = new Collocation();
        collocation.setAppreciateCount((long) random.nextInt(100));
        collocation.setViewCount((long) random.nextInt(100));
        collocation.setDescription(match.getString("content"));
        collocation.setMainImage(yiXinImageUrlPre + match.getString("match_image"));
        JSONArray originUrls = match.getJSONArray("images");
        collocation.setImages(originUrls.stream().map(image -> yiXinImageUrlPre + image)
                .collect(Collectors.joining(";")));
        collocation.setSourceId(matchId.toString());
        collocation.setSourceType(Collocation.YI_XIN_SOURCE_TYPE);
        collocationRepository.save(collocation);

        JSONArray styles = match.getJSONArray("styles");
        JSONArray scenes = match.getJSONArray("scenes");
        JSONArray bodys = match.getJSONArray("bodys");
        List<Object> tagNames = new ArrayList<>();
        if (!CollectionUtils.isEmpty(scenes)) {
            tagNames.addAll(scenes);
        }
        if (!CollectionUtils.isEmpty(styles)) {
            tagNames.addAll(styles);
        }
        if (!CollectionUtils.isEmpty(bodys)) {
            tagNames.addAll(bodys);
        }
        tagNames.stream().map(String::valueOf).forEach(tagName -> {
            tagService.addTag(DataType.Collocation.name(), collocation.getId(), tagName);
        });

        List<CollocationProduct> productList = new ArrayList<>();
        JSONArray products = match.getJSONArray("product");
        products.forEach(o -> {
            JSONObject product = (JSONObject) o;
            String link = product.getString("tb_link");
            String tbGoodsId = parseTbGoodsId(link);
            CollocationProduct collocationProduct = buildCollocationProduct(collocation, tbGoodsId);
            if (Objects.nonNull(collocationProduct)) {
                productList.add(collocationProduct);
            }
        });
        if (CollectionUtils.isEmpty(productList)) {
            throw new MsException("spider", "商品为空");
        }
        collocationProductRepository.saveAll(productList);
        return true;
    }

    private CollocationProduct buildCollocationProduct(Collocation collocation, String tbGoodsId) {
        if (StringUtils.isEmpty(tbGoodsId)) {
            log.error("获取淘宝id失败");
            return null;
        }
        tbGoodsId=tbGoodsId.trim().replaceAll("\\u00A0","");
        JSONObject goodsDetail = goodsDetailApi.loadGoodsDetail(null, tbGoodsId);
        if (goodsDetail == null) {
            log.error("商品不是淘宝客商品");
            return null;
        }
        CollocationProduct cp = new CollocationProduct();
        cp.setCollocationId(collocation.getId());
        cp.setProductStatus(ProductStatus.Up.name());
        cp.setTbGoodsId(tbGoodsId);
        return cp;
    }

    private String parseTbGoodsId(String link) {
        try {
            link = URLDecoder.decode(link, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.error("解码url失败,link:{}", link);
            return null;
        }
        if (link.contains("item.taobao.com") || link.contains("detail.tmall.com")) {
            URLUtil.UrlEntity entity = URLUtil.parse(link);
            return entity.params.get("id");
        }
        Pattern pattern = Pattern.compile("https:.*sm=[0-9|A-Z|a-z]*");
        Matcher matcher = pattern.matcher(link);
        if (matcher.find()) {
            String body = restTemplate.getForObject(matcher.group(0), String.class);
            if (StringUtils.isEmpty(body)) {
                throw new MsException("spider", "parseTbGoodsId => 解析商品id失败");
            }
            Pattern pattern2 = Pattern.compile("https://.*(taobao.com|tmall.com).*");
            Matcher matcher2 = pattern2.matcher(body);
            if (matcher2.find()) {
                URLUtil.UrlEntity entity = URLUtil.parse(matcher2.group(0));
                return entity.params.get("id");
            }
        }
        return null;
    }

    private String requestYixin(String url, MultiValueMap<String, String> parameter) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("Host", "api.yyixxx.com");
        headers.add("User-Agent", "YiXingAPP/1.6.0 (com.yixin.xs; build:2; iOS 14.0.0) Alamofire/4.8.1");
        headers.add("access-token", "4c9fYmr5VV8YuQBHh9XotTTf+Mibp9Yf4eUSOFnjRZV/WyYQ1UcmBrI+EJNhfrvdf9MhtuPBVC3M+FO3");
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(parameter, headers);
        return restTemplate.postForObject(url, request, String.class);
    }

    public Integer spiderNothingCollocationList(Integer page, Integer size) {
        Map<String, Object> parameter = new HashMap<>();
        parameter.put("limit", size);
        parameter.put("startRow", page * size);
        String body = HttpsClientUtil.doPost(nothingListUrl, JSON.toJSONString(parameter));
        JSONArray arr = JSON.parseObject(body).getJSONObject("DATA").getJSONArray("list");
        SpiderService spiderService = context.getBean(SpiderService.class);
        int amount = 0;
        for (Object o : arr) {
            try {
                JSONObject jb = (JSONObject) o;
                boolean result = spiderService.spiderNothingCollocation(jb.getString("itemId"));
                if (result) {
                    amount += 1;
                }
            } catch (Exception e) {
                log.error("保存Collocation失败", e);
            }
        }
        return amount;
    }

    /**
     * 保存成功返回true
     *
     * @param matchId
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public boolean spiderNothingCollocation(String matchId) {
        long amount = collocationRepository.countBySourceTypeAndSourceId(Collocation.NOTHING_SOURCE_TYPE, matchId);
        if (amount > 0) {
            log.error("Noting matchId[{}] already save", matchId);
            return false;
        }
        Map<String, Object> parameter = new HashMap<>();
        parameter.put("matchId", matchId);
        String body = HttpsClientUtil.doPost(nothingDetailUrl, JSON.toJSONString(parameter));
        JSONObject data = JSON.parseObject(body).getJSONObject("DATA");
        JSONObject match = data.getJSONObject("match");

        Random random = new Random();
        Collocation collocation = new Collocation();
        collocation.setAppreciateCount((long) random.nextInt(100));
        collocation.setViewCount((long) random.nextInt(100));
        collocation.setDescription(match.getString("description"));
        JSONArray originUrls = match.getJSONArray("originUrls");
        collocation.setImages(originUrls.stream().map(String::valueOf).collect(Collectors.joining(";")));
        collocation.setMainImage(originUrls.getString(0));
        collocation.setSourceId(matchId);
        collocation.setSourceType(Collocation.NOTHING_SOURCE_TYPE);
        collocationRepository.save(collocation);

        JSONArray products = data.getJSONArray("productList");
        List<CollocationProduct> productList = new ArrayList<>();
        products.forEach(o -> {
            JSONObject product = (JSONObject) o;
            String buyUrl = product.getString("buyUrl");
            URLUtil.UrlEntity entity = URLUtil.parse(buyUrl);
            String tbGoodsId = entity.params.get("id");
            CollocationProduct collocationProduct = buildCollocationProduct(collocation, tbGoodsId);
            if (Objects.nonNull(collocationProduct)) {
                productList.add(collocationProduct);
            }
        });

        if (CollectionUtils.isEmpty(productList)) {
            throw new MsException("spider", "商品为空");
        }
        collocationProductRepository.saveAll(productList);
        return true;
    }


}
