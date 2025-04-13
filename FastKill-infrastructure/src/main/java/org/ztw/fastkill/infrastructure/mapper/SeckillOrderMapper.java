package org.ztw.fastkill.infrastructure.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.ztw.fastkill.domain.model.SeckillOrder;

import java.util.List;

@Mapper
public interface SeckillOrderMapper {
    int saveSeckillOrder(SeckillOrder seckillOrder);
    List<SeckillOrder> getSeckillOrderByUserId(Long id);
    List<SeckillOrder> getSeckillOrderByActivityId(Long activityId);
}
