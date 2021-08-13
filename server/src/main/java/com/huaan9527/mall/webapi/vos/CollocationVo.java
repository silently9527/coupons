package com.huaan9527.mall.webapi.vos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ocpsoft.prettytime.PrettyTime;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CollocationVo implements Serializable {
    private Long collocationId;
    private String description;
    private Date createdDate;
    private String mainImage; //封面图片
    private String images; //多个用分号分隔

    private Long viewCount; //浏览次数
    private Long appreciateCount;  //点赞数

    private List<TagVo> tags;

    private boolean appreciate;  //登录用户是否已点赞

    public List<String> getImagePaths() {
        if (org.springframework.util.StringUtils.isEmpty(images)) {
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
