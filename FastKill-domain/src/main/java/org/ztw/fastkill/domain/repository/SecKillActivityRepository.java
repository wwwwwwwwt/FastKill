package org.ztw.fastkill.domain.repository;

import org.ztw.fastkill.domain.model.SeckillActivity;

import java.util.Date;
import java.util.List;

public interface SecKillActivityRepository {
    long saveSecKillActivity(SeckillActivity seckillActivity);

    List<SeckillActivity> getSecKillActivityListByStatus(Integer status);

    List<SeckillActivity> getSecKillActivityListByStatusAndTime(Integer status, Date currentTime);

    SeckillActivity getSecKillActivityById(Long id);

    int updateSecKillActivity(SeckillActivity seckillActivity);

    List<SeckillActivity> getSeckillActivityList(Integer status);

}
