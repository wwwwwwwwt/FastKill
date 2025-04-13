package org.ztw.fastkill.application.service;

import org.ztw.fastkill.domain.dto.SeckillOrderDTO;
import org.ztw.fastkill.domain.model.SeckillOrder;

import java.util.List;

public interface SeckillOrderService {
    SeckillOrder saveSeckillOrder(SeckillOrderDTO seckillOrderDTO);
    List<SeckillOrder> getSeckillOrderByUserId(Long id);
    List<SeckillOrder>  getSeckillOrderByActivityId(Long activityId);
}
