package org.ztw.fastkill.domain.repository;

import org.ztw.fastkill.domain.model.SeckillOrder;

import java.util.List;

public interface SeckillOrderRepository {
    int saveSeckillOrder(SeckillOrder seckillOrder);
    List<SeckillOrder> getSeckillOrderByUserId(Long id);
    List<SeckillOrder>  getSeckillOrderByActivityId(Long activityId);
}
