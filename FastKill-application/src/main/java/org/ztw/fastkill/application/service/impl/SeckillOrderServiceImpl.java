package org.ztw.fastkill.application.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ztw.fastkill.application.service.SecKillGoodsService;
import org.ztw.fastkill.application.service.SeckillOrderService;
import org.ztw.fastkill.common.code.HttpCode;
import org.ztw.fastkill.common.enums.SeckillGoodsStatus;
import org.ztw.fastkill.common.enums.SeckillOrderStatus;
import org.ztw.fastkill.common.exception.SeckillException;
import org.ztw.fastkill.common.utils.snow.SnowFlakeFactory;
import org.ztw.fastkill.domain.dto.SeckillOrderDTO;
import org.ztw.fastkill.domain.model.SeckillGoods;
import org.ztw.fastkill.domain.model.SeckillOrder;
import org.ztw.fastkill.domain.repository.SecKillGoodsRepository;
import org.ztw.fastkill.domain.repository.SeckillOrderRepository;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class SeckillOrderServiceImpl implements SeckillOrderService {
    @Resource
    private SeckillOrderRepository seckillOrderRepository;

    @Resource
    private SecKillGoodsService seckillGoodsService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SeckillOrder saveSeckillOrder(SeckillOrderDTO seckillOrderDTO) {
        if (seckillOrderDTO == null){
            throw new SeckillException(HttpCode.PARAMS_INVALID);
        }

        //获取商品
        SeckillGoods seckillGoods = seckillGoodsService.getSeckillGoodsId(String.valueOf(seckillOrderDTO.getGoodsId()));
        //商品不存在
        if (seckillGoods == null){
            throw new SeckillException(HttpCode.GOODS_NOT_EXISTS);
        }
        //商品未上线
        if (seckillGoods.getStatus() == SeckillGoodsStatus.PUBLISHED.getCode()){
            throw new SeckillException(HttpCode.GOODS_PUBLISH);
        }
        //商品已下架
        if (seckillGoods.getStatus() == SeckillGoodsStatus.OFFLINE.getCode()){
            throw new SeckillException(HttpCode.GOODS_OFFLINE);
        }
        //触发限购
        if (seckillGoods.getLimitNum() < seckillOrderDTO.getQuantity()){
            throw new SeckillException(HttpCode.BEYOND_LIMIT_NUM);
        }
        // 库存不足
        if (seckillGoods.getAvailableStock() == null || seckillGoods.getAvailableStock() <= 0 || seckillOrderDTO.getQuantity() > seckillGoods.getAvailableStock()){
            throw new SeckillException(HttpCode.STOCK_LT_ZERO);
        }

        SeckillOrder seckillOrder = new SeckillOrder();
        BeanUtils.copyProperties(seckillOrderDTO, seckillOrder);
        seckillOrder.setId(SnowFlakeFactory.getSnowFlakeFromCache().nextId());
        seckillOrder.setGoodsName(seckillGoods.getGoodsName());

        seckillOrder.setActivityPrice(seckillGoods.getActivityPrice());
        BigDecimal orderPrice = seckillGoods.getActivityPrice().multiply(BigDecimal.valueOf(seckillOrder.getQuantity()));
        seckillOrder.setOrderPrice(orderPrice);
        seckillOrder.setStatus(SeckillOrderStatus.CREATED.getCode());
        seckillOrder.setCreateTime(new Date());
        //保存订单
        seckillOrderRepository.saveSeckillOrder(seckillOrder);
        //扣减库存
        seckillGoodsService.updateAvailableStock(seckillOrderDTO.getQuantity(), seckillOrderDTO.getGoodsId());
        return seckillOrder;
    }

    @Override
    public List<SeckillOrder> getSeckillOrderByUserId(Long userId) {
        return seckillOrderRepository.getSeckillOrderByUserId(userId);
    }

    @Override
    public List<SeckillOrder> getSeckillOrderByActivityId(Long activityId) {
        return seckillOrderRepository.getSeckillOrderByActivityId(activityId);
    }
}
