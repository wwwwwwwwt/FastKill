package org.ztw.fastkill.application.service;

import org.ztw.fastkill.domain.dto.SecKillActivityDTO;
import org.ztw.fastkill.domain.model.SeckillActivity;

import java.util.Date;
import java.util.List;

public interface SeckillActivityService {
    Long saveSecKillActivity(SecKillActivityDTO secKillActivityDTO);

    List<SecKillActivityDTO> getSecKillActivityListByStatus(Integer status);

    List<SecKillActivityDTO> getSecKillActivityListByStatusAndTime(Integer status, Date currentTime);

    SecKillActivityDTO getSecKillActivityById(Long id);

    int updateSecKillActivity(SeckillActivity secKillActivity);
}
