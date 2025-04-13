package org.ztw.fastkill.infrastructure.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.ztw.fastkill.domain.model.SeckillGoods;

import java.util.List;

@Mapper
public interface SecKillGoodsMapper {

    int saveSeckillGoods(SeckillGoods seckillGoods);

    SeckillGoods getSeckillGoodsId(Long id);

    List<SeckillGoods> getSeckillGoodsByActivityId(Long activityId);

    int updateSecKillGoodsStatus(@Param("status") Integer status, @Param("id")Long id);

    int updateGoodsAvailableStock(@Param("count") Integer count, @Param("id")Long id);

    int getAvailableStockById(Long id);
}
