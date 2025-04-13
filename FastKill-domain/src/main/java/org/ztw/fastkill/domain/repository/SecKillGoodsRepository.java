package org.ztw.fastkill.domain.repository;

import org.ztw.fastkill.domain.model.SeckillGoods;

import java.util.List;

public interface SecKillGoodsRepository {
    //保存秒杀商品
    int saveSecKillGoods(SeckillGoods seckillGoods);

    //根据id获取秒杀商品
    SeckillGoods getSecKillGoodsById(Long id);

    //根据活动id获取秒杀商品
    List<SeckillGoods> getSecKillGoodsByActivityId(Long activityId);

    //更新秒杀商品
    int updateSecKillGoodsStatus(Integer count, Long id);

    //更新库存
    int updateGoodsAvailableStock(Integer count, Long id);

    //根据id获取可用库存
    int getAvailableStockById(Long id);
}
