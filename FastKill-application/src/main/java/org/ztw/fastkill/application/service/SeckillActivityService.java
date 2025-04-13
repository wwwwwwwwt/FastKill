package org.ztw.fastkill.application.service;

import org.ztw.fastkill.domain.dto.SecKillActivityDTO;
import org.ztw.fastkill.domain.model.SeckillActivity;

import java.util.Date;
import java.util.List;

public interface SeckillActivityService {
    Long saveSecKillActivity(SecKillActivityDTO secKillActivityDTO);

    List<SeckillActivity> getSecKillActivityListByStatus(Integer status);

    List<SeckillActivity> getSecKillActivityListByStatusAndTime(Integer status, Date currentTime);

    SeckillActivity getSecKillActivityById(Long id);

    int updateSecKillActivity(SeckillActivity secKillActivity);

    /**
     * 活动列表
     */
    List<SecKillActivityDTO> getSeckillActivityList(Integer status, Long version);
}
