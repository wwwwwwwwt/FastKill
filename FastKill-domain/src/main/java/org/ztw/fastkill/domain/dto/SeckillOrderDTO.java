package org.ztw.fastkill.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class SeckillOrderDTO implements Serializable {
    private static final long serialVersionUID = -3164396374622988886L;

    //订单id
    private Long id;
    //用户id
    private Long userId;
    //商品id
    private Long goodsId;
    //购买数量
    private Integer quantity;
    //活动id
    private Long activityId;
}
