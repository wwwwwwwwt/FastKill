package org.ztw.fastkill.application.service;

import org.ztw.fastkill.domain.dto.SeckillGoodsDTO;
import org.ztw.fastkill.domain.model.SeckillGoods;

import java.util.List;

public interface SecKillGoodsService {
    /**
     * 保存商品信息
     */
    int saveSeckillGoods(SeckillGoodsDTO seckillGoodsDTO);

    /**
     * 根据id获取商品详细信息
     */
    SeckillGoods getSeckillGoodsId(String id);

    /**
     * 根据活动id获取商品列表
     */
    List<SeckillGoods> getSeckillGoodsByActivityId(String activityId);

    /**
     * 修改商品状态
     */
    int updateStatus(Integer status, Long id);

    /**
     * 扣减库存
     */
    int updateAvailableStock(Integer count, Long id);

    /**
     * 获取当前可用库存
     */
    Integer getAvailableStockById(Long id);
}
