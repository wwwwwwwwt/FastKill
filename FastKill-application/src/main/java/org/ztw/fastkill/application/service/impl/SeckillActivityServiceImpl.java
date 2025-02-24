package org.ztw.fastkill.application.service.impl;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.ztw.fastkill.application.service.SeckillActivityService;
import org.ztw.fastkill.common.code.HttpCode;
import org.ztw.fastkill.common.enums.SeckillActivityStatus;
import org.ztw.fastkill.common.exception.SeckillException;
import org.ztw.fastkill.common.utils.snow.SnowFlakeFactory;
import org.ztw.fastkill.domain.dto.SecKillActivityDTO;
import org.ztw.fastkill.domain.model.SeckillActivity;
import org.ztw.fastkill.domain.repository.SecKillActivityRepository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class SeckillActivityServiceImpl implements SeckillActivityService {

    @Resource
    private SecKillActivityRepository secKillActivityRepository;

    @Override
    public Long saveSecKillActivity(SecKillActivityDTO secKillActivityDTO) {
        if (secKillActivityDTO == null) throw new SeckillException(HttpCode.PARAMS_INVALID);
        SeckillActivity seckillActivity = new SeckillActivity();
        seckillActivity.setActivityName(secKillActivityDTO.getActivityName());
        seckillActivity.setStartTime(secKillActivityDTO.getStartTime());
        seckillActivity.setEndTime(secKillActivityDTO.getEndTime());
        seckillActivity.setStatus(SeckillActivityStatus.PUBLISHED.getCode());
        seckillActivity.setActivityDesc(secKillActivityDTO.getActivityDesc());
        seckillActivity.setId(SnowFlakeFactory.getSnowFlake().nextId());
        return secKillActivityRepository.saveSecKillActivity(seckillActivity);
    }

    @Override
    public List<SecKillActivityDTO> getSecKillActivityListByStatus(Integer status) {
        List<SeckillActivity> seckillActivityList = secKillActivityRepository.getSecKillActivityListByStatus(status);
        if (seckillActivityList == null || seckillActivityList.isEmpty()) {
            return Collections.emptyList();
        }
        List<SecKillActivityDTO> secKillActivityDTOList = new ArrayList<>();
        for (SeckillActivity seckillActivity : seckillActivityList) {
            SecKillActivityDTO secKillActivityDTO = new SecKillActivityDTO();
            secKillActivityDTO.setActivityName(seckillActivity.getActivityName());
            secKillActivityDTO.setStartTime(seckillActivity.getStartTime());
            secKillActivityDTO.setEndTime(seckillActivity.getEndTime());
            secKillActivityDTO.setStatus(seckillActivity.getStatus());
            secKillActivityDTO.setActivityDesc(seckillActivity.getActivityDesc());
            secKillActivityDTOList.add(secKillActivityDTO);
        }
        return secKillActivityDTOList;
    }

    @Override
    public List<SecKillActivityDTO> getSecKillActivityListByStatusAndTime(Integer status, Date currentTime) {
        if (status == null || currentTime == null) return Collections.emptyList();
        List<SeckillActivity> seckillActivityList = secKillActivityRepository.getSecKillActivityListByStatusAndTime(status, currentTime);
        if(seckillActivityList == null || seckillActivityList.isEmpty()){
            return Collections.emptyList();
        }
        List<SecKillActivityDTO> secKillActivityDTOList = new ArrayList<>();
        for (SeckillActivity seckillActivity : seckillActivityList) {
            SecKillActivityDTO secKillActivityDTO = new SecKillActivityDTO();
            secKillActivityDTO.setActivityName(seckillActivity.getActivityName());
            secKillActivityDTO.setStartTime(seckillActivity.getStartTime());
            secKillActivityDTO.setEndTime(seckillActivity.getEndTime());
            secKillActivityDTO.setStatus(seckillActivity.getStatus());
            secKillActivityDTO.setActivityDesc(seckillActivity.getActivityDesc());
            secKillActivityDTOList.add(secKillActivityDTO);
        }
        return secKillActivityDTOList;
    }

    @Override
    public SecKillActivityDTO getSecKillActivityById(Long id) {
        if(id == null) throw new SeckillException(HttpCode.PARAMS_INVALID);
        SeckillActivity seckillActivity = secKillActivityRepository.getSecKillActivityById(id);
        if(seckillActivity == null) throw new SeckillException(HttpCode.GOODS_NOT_EXISTS);
        log.info("getSecKillActivityById:{}", JSON.toJSON(seckillActivity));
        return SecKillActivityDTO.builder()
                .activityName(seckillActivity.getActivityName())
                .startTime(seckillActivity.getStartTime())
                .endTime(seckillActivity.getEndTime())
                .status(seckillActivity.getStatus())
                .activityDesc(seckillActivity.getActivityDesc())
                .build();
    }

    @Override
    public int updateSecKillActivity(SeckillActivity secKillActivity) {
        if(secKillActivity == null || secKillActivity.getId() == null) throw new SeckillException(HttpCode.PARAMS_INVALID);
        SeckillActivity seckillActivity = new SeckillActivity();
        seckillActivity.setId(secKillActivity.getId());
        seckillActivity.setActivityName(secKillActivity.getActivityName());
        seckillActivity.setStartTime(secKillActivity.getStartTime());
        seckillActivity.setEndTime(secKillActivity.getEndTime());
        seckillActivity.setStatus(secKillActivity.getStatus());
        seckillActivity.setActivityDesc(secKillActivity.getActivityDesc());
        return secKillActivityRepository.updateSecKillActivity(seckillActivity);
    }
}
