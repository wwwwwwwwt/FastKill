package org.ztw.fastkill.infrastructure.repository;

import org.springframework.stereotype.Repository;
import org.ztw.fastkill.common.code.HttpCode;
import org.ztw.fastkill.common.exception.SeckillException;
import org.ztw.fastkill.domain.model.SeckillGoods;
import org.ztw.fastkill.domain.repository.SecKillGoodsRepository;
import org.ztw.fastkill.infrastructure.mapper.SecKillGoodsMapper;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class SecKillGoodsRepositoryImpl implements SecKillGoodsRepository {

    @Resource
    private SecKillGoodsMapper secKillGoodsMapper;
    @Override
    public int saveSecKillGoods(SeckillGoods seckillGoods) {
        if (seckillGoods == null){
            throw new SeckillException(HttpCode.PARAMS_INVALID);
        }
        return secKillGoodsMapper.saveSeckillGoods(seckillGoods);
    }

    @Override
    public SeckillGoods getSecKillGoodsById(Long id) {
        return secKillGoodsMapper.getSeckillGoodsId(id);
    }

    @Override
    public List<SeckillGoods> getSecKillGoodsByActivityId(Long activityId) {
        return secKillGoodsMapper.getSeckillGoodsByActivityId(activityId);
    }

    @Override
    public int updateSecKillGoodsStatus(Integer status, Long id) {
        return secKillGoodsMapper.updateSecKillGoodsStatus(status, id);
    }

    @Override
    public int updateGoodsAvailableStock(Integer count, Long id) {
        return secKillGoodsMapper.updateGoodsAvailableStock(count, id);
    }

    @Override
    public int getAvailableStockById(Long id) {
        return secKillGoodsMapper.getAvailableStockById(id);
    }
}
