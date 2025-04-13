package org.ztw.fastkill.infrastructure.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.ztw.fastkill.common.code.HttpCode;
import org.ztw.fastkill.common.exception.SeckillException;
import org.ztw.fastkill.domain.model.SeckillOrder;
import org.ztw.fastkill.domain.repository.SeckillOrderRepository;
import org.ztw.fastkill.infrastructure.mapper.SeckillOrderMapper;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class SeckillOrderRepositoryImpl implements SeckillOrderRepository {
    @Resource
    private SeckillOrderMapper seckillOrderMapper;
    @Override
    public int saveSeckillOrder(SeckillOrder seckillOrder) {
        if(seckillOrder == null){
            throw new SeckillException(HttpCode.PARAMS_INVALID);
        }
        return seckillOrderMapper.saveSeckillOrder(seckillOrder);
    }

    @Override
    public List<SeckillOrder> getSeckillOrderByUserId(Long id) {
        return seckillOrderMapper.getSeckillOrderByUserId(id);
    }

    @Override
    public List<SeckillOrder> getSeckillOrderByActivityId(Long activityId) {
        return seckillOrderMapper.getSeckillOrderByActivityId(activityId);
    }
}
