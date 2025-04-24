package org.ztw.fastkill.infrastructure.repository;

import org.springframework.stereotype.Repository;
import org.ztw.fastkill.common.code.HttpCode;
import org.ztw.fastkill.common.exception.SeckillException;
import org.ztw.fastkill.domain.model.SeckillActivity;
import org.ztw.fastkill.domain.repository.SecKillActivityRepository;
import org.ztw.fastkill.infrastructure.mapper.SeckillActivityMapper;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Repository
public class SecKillActivityRepositoryImpl implements SecKillActivityRepository {

    @Resource
    private SeckillActivityMapper seckillActivityMapper;

    @Override
    public String saveSecKillActivity(SeckillActivity seckillActivity) {
        if (seckillActivity == null) throw new SeckillException(HttpCode.PARAMS_INVALID);
        long id = seckillActivityMapper.saveSecKillActivity(seckillActivity);
        if (id <= 0) throw new SeckillException(HttpCode.PARAMS_INVALID);
        return String.valueOf(seckillActivity.getId());
    }

    @Override
    public List<SeckillActivity> getSecKillActivityListByStatus(Integer status) {
        return seckillActivityMapper.getSecKillActivityListByStatus(status);
    }

    @Override
    public List<SeckillActivity> getSecKillActivityListByStatusAndTime(Integer status, Date currentTime) {
        if (status == null || currentTime == null) return Collections.emptyList();

        return seckillActivityMapper.getSecKillActivityListByStatusAndTime(status, currentTime);
    }

    @Override
    public SeckillActivity getSecKillActivityById(Long id) {
        if (id == null) throw new SeckillException(HttpCode.PARAMS_INVALID);
        return seckillActivityMapper.getSecKillActivityById(id);
    }

    @Override
    public int updateSecKillActivity(SeckillActivity seckillActivity) {
        if(seckillActivity == null)return 0;
        return seckillActivityMapper.updateSecKillActivity(seckillActivity);
    }

    @Override
    public List<SeckillActivity> getSeckillActivityList(Integer status) {
        return seckillActivityMapper.getSeckillActivityList(status);
    }
}
