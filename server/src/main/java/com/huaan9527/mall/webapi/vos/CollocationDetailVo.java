package com.huaan9527.mall.webapi.vos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CollocationDetailVo implements Serializable {
    private Long collocationId;
    private Date createdDate;
    private String description;
    private String mainImage; //封面图片
    private String images; //多个用分号分隔

    private Long viewCount; //浏览次数
    private Long appreciateCount;  //点赞数

    private List<CollocationProductVo> products;

    private List<TagVo> tags;

    public List<String> getImagePaths() {
        if (StringUtils.isEmpty(images)) {
            return new ArrayList<>();
        }
        return Arrays.asList(images.split(";"));
    }

    public String getFormatCreatedDate() {
        if (Objects.isNull(this.createdDate)) {
            return null;
        }
        Locale.setDefault(Locale.CHINESE);
        PrettyTime prettyTime = new PrettyTime(new Date());
        return prettyTime.format(this.createdDate);
    }
}
