package com.huaan9527.mall.webapi.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huaan9527.mall.webapi.domain.Collection;
import com.huaan9527.mall.webapi.domain.enums.DataType;
import com.huaan9527.mall.webapi.exception.MsException;
import com.huaan9527.mall.webapi.repository.CollectionRepository;
import com.huaan9527.mall.webapi.security.SecurityUtils;
import com.huaan9527.mall.webapi.service.operation.api.CateApi;
import com.huaan9527.mall.webapi.service.operation.api.ExplosiveGoodsListApi;
import com.huaan9527.mall.webapi.service.operation.api.GoodsApi;
import com.huaan9527.mall.webapi.service.operation.api.GoodsDetailApi;
import com.huaan9527.mall.webapi.service.operation.api.HotSearchTop10Api;
import com.huaan9527.mall.webapi.service.operation.api.NineGoodsApi;
import com.huaan9527.mall.webapi.service.operation.api.PrivilegeApi;
import com.huaan9527.mall.webapi.service.operation.api.RankGoodsApi;
import com.huaan9527.mall.webapi.service.operation.api.SearchApi;
import com.huaan9527.mall.webapi.service.operation.api.SimilarGoodsApi;
import com.huaan9527.mall.webapi.service.operation.api.TaobaoSearchApi;
import com.huaan9527.mall.webapi.utils.Constants;
import com.huaan9527.mall.webapi.vos.ProductVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Service
public class GoodsService {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private CateApi cateApi;
    @Autowired
    private NineGoodsApi nineGoodsApi;
    @Autowired
    private RankGoodsApi rankGoodsApi;
    @Autowired
    private GoodsApi goodsApi;
    @Autowired
    private GoodsDetailApi goodsDetailApi;
    @Autowired
    private PrivilegeApi privilegeApi;
    @Autowired
    private HotSearchTop10Api hotSearchTop10Api;
    @Autowired
    private SearchApi searchApi;
    @Autowired
    private TaobaoSearchApi taobaoSearchApi;
    @Autowired
    private CollectionRepository collectionRepository;
    @Autowired
    private SimilarGoodsApi similarGoodsApi;
    @Autowired
    private ExplosiveGoodsListApi explosiveGoodsListApi;

    public JSONArray loadCate() {
        return cateApi.loadCate();
    }

    public JSONObject nineGoods(Integer page, Integer size, String nineCid) {
        return nineGoodsApi.loadNineGoods(page, size, nineCid);
    }

    public JSONArray rankGoods(String cid) {
        return rankGoodsApi.loadRankGoods(cid);
    }

    @SuppressWarnings("unchecked")
    public JSONObject recommendGoods(Integer page, Integer size) {
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        String cachedRecommendGoods = operations.get(getRecommendGoodsKey(page));
        if (StringUtils.isEmpty(cachedRecommendGoods)) {
            Map<String, String> params = new HashMap<>();
            params.put("pageId", page.toString());
            params.put("pageSize", size.toString());
            params.put("PriceCid", "3");
            JSONObject data = explosiveGoodsListApi.get(params);
            operations.set(getRecommendGoodsKey(page), data.toJSONString());
            redisTemplate.expire(getRecommendGoodsKey(page), 2, TimeUnit.HOURS);
            return data;
        }
        return JSON.parseObject(cachedRecommendGoods);
    }

    private String getRecommendGoodsKey(Integer page) {
        return Constants.RECOMMEND_GOODS_REDIS_KEY + "_page_" + page;
    }

    public JSONObject loadGoodsByCate(String subcid, Integer page, Integer size, Integer sort) {
        Map<String, String> params = new HashMap<>();
        params.put("subcid", subcid);
        params.put("pageId", page.toString());
        params.put("pageSize", size.toString());
        params.put("sort", sort.toString());
        return goodsApi.loadGoods(params);
    }

    public ProductVo goodsDetail(String id, String goodsId, Boolean checkFavorite) {
        JSONObject goodsDetail = goodsDetailApi.loadGoodsDetail(id, goodsId);
        if (goodsDetail == null) {
            throw new MsException("Goods", "未查询到商品信息");
        }
        ProductVo productVo = buildProductVo(goodsDetail);
        if (!checkFavorite) {
            return productVo;
        }
        Long currentUserId = SecurityUtils.getCurrentUserId();
        if (currentUserId != null) {
            //是否已收藏
            Collection collection = collectionRepository.findByUserIdAndDataIdAndDataType(currentUserId, goodsId, DataType.Product.name());
            productVo.setFavorite(collection != null);
        }
        return productVo;
    }

    private ProductVo buildProductVo(JSONObject goodsDetail) {
        return ProductVo.builder()
                .id(goodsDetail.getLong("id"))
                .title(goodsDetail.getString("title"))
                .actualPrice(goodsDetail.getDouble("actualPrice"))
                .originalPrice(goodsDetail.getDouble("originalPrice"))
                .monthSales(goodsDetail.getInteger("monthSales"))
                .dailySales(goodsDetail.getInteger("dailySales"))
                .couponReceiveNum(goodsDetail.getInteger("couponReceiveNum"))
                .couponPrice(goodsDetail.getDouble("couponPrice"))
                .couponStartTime(goodsDetail.getString("couponStartTime"))
                .couponEndTime(goodsDetail.getString("couponEndTime"))
                .desc(goodsDetail.getString("desc"))
                .shopName(goodsDetail.getString("shopName"))
                .descScore(goodsDetail.getDouble("descScore"))
                .serviceScore(goodsDetail.getDouble("serviceScore"))
                .shipScore(goodsDetail.getDouble("shipScore"))
                .mainPic(goodsDetail.getString("mainPic"))
                .goodsId(goodsDetail.getString("goodsId"))
                .imgs(goodsDetail.getString("imgs"))
                .marketingMainPic(goodsDetail.getString("marketingMainPic"))
                .build();
    }

    public Map<String, String> getPrivilegeLink(String goodsId) {
        JSONObject result = privilegeApi.getPrivilegeLink(goodsId);
        Map<String, String> map = new HashMap<>();
        map.put("tpwd", result.getString("tpwd"));
        map.put("couponClickUrl", result.getString("couponClickUrl"));
        map.put("itemUrl", result.getString("itemUrl"));

        //todo: 审核代码
//        map.put("audit", "1");
//        map.put("tpwdTxt", "点击复制，在浏览器中打开链接，即可购买");
//        map.put("tpwdValue", result.getString("couponClickUrl"));
//        map.put("tpwdTip", "复制成功");
        //正式代码
        map.put("audit", "0");
        map.put("tpwdTxt", result.getString("longTpwd"));
        map.put("tpwdValue", result.getString("longTpwd"));
        map.put("tpwdTip", "口令复制成功，请打开手机淘宝");
        return map;
    }

    public List getHotSearchTop10() {
        return hotSearchTop10Api.loadHotSearch();
    }

    public JSONObject search(String keyword, Integer page, Integer sort) {
        return searchApi.search(keyword, page, sort);
    }

    public List<ProductVo> search2(String keyword, Integer page, String sort, String hasCoupon) {
        JSONArray array = taobaoSearchApi.search(keyword, page, sort, hasCoupon);
        if (CollectionUtils.isEmpty(array)) {
            return new ArrayList<>();
        }
        return array.stream().map(o -> {
            JSONObject jb = (JSONObject) o;
            ProductVo productVo = new ProductVo();
            productVo.setGoodsId(jb.getString("item_id"));
            productVo.setMainPic(jb.getString("pict_url"));
            productVo.setShopType(jb.getIntValue("user_type"));
            productVo.setTitle(jb.getString("title"));

            if (jb.getDoubleValue("coupon_amount") > 0) {
                productVo.setActualPrice(
                        jb.getBigDecimal("zk_final_price")
                        .subtract(jb.getBigDecimal("coupon_amount"))
                        .doubleValue());
            }else{
                productVo.setActualPrice(jb.getDoubleValue("zk_final_price"));
            }
            productVo.setCouponPrice(jb.getDoubleValue("coupon_amount"));
            productVo.setMonthSales(jb.getIntValue("volume"));
            productVo.setCouponReceiveNum(jb.getIntValue("coupon_total_count") - jb.getIntValue("coupon_remain_count"));
            return productVo;
        }).collect(Collectors.toList());
    }

    public JSONArray similarGoods(String daTaoKeGoodsId) {
        return similarGoodsApi.loadSimilarGoods(daTaoKeGoodsId, 20);
    }
}
