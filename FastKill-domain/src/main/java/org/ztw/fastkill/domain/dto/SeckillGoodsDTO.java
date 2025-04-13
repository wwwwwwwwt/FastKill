package org.ztw.fastkill.domain.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class SeckillGoodsDTO implements Serializable {
    private static final long serialVersionUID = -8447592991812016065L;
    //数据id
    private Long id;
    //商品名称
    private String goodsName;
    //秒杀活动id
    private Long activityId;
    //活动开始时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    //活动结束时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
    //商品原价
    private BigDecimal originalPrice;
    //秒杀活动价格
    private BigDecimal activityPrice;
    //初始库存
    private Integer initialStock;
    //限购个数
    private Integer limitNum;
    //当前可用库存
    private Integer availableStock;
    //描述
    private String description;
    //图片
    private String imgUrl;
    //秒杀状态 0：已发布； 1：上线； 2：下线
    private Integer status;
}