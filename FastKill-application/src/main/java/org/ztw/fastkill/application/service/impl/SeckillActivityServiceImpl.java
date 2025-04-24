package org.ztw.fastkill.application.service.impl;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.ztw.fastkill.application.builder.SeckillActivityBuilder;
import org.ztw.fastkill.application.cache.model.SeckillBusinessCache;
import org.ztw.fastkill.application.cache.service.activity.SeckillActivityCacheService;
import org.ztw.fastkill.application.cache.service.activity.SeckillActivityListCacheService;
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
import java.util.stream.Collectors;

@Slf4j
@Service
public class SeckillActivityServiceImpl implements SeckillActivityService {

    @Resource
    private SecKillActivityRepository secKillActivityRepository;

    @Resource
    private SeckillActivityListCacheService seckillActivityListCacheService;

    @Resource
    private SeckillActivityCacheService seckillActivityCacheService;

    @Override
    public String saveSecKillActivity(SecKillActivityDTO secKillActivityDTO) {
        if (secKillActivityDTO == null) throw new SeckillException(HttpCode.PARAMS_INVALID);
        SeckillActivity seckillActivity = new SeckillActivity();
        seckillActivity.setActivityName(secKillActivityDTO.getActivityName());
        seckillActivity.setStartTime(secKillActivityDTO.getStartTime());
        seckillActivity.setEndTime(secKillActivityDTO.getEndTime());
        seckillActivity.setStatus(SeckillActivityStatus.PUBLISHED.getCode());
        seckillActivity.setActivityDesc(secKillActivityDTO.getActivityDesc());
        seckillActivity.setId(String.valueOf(SnowFlakeFactory.getSnowFlake().nextId()));
        return secKillActivityRepository.saveSecKillActivity(seckillActivity);
    }

    @Override
    public List<SeckillActivity> getSecKillActivityListByStatus(Integer status) {
        List<SeckillActivity> seckillActivityList = secKillActivityRepository.getSecKillActivityListByStatus(status);
        if (seckillActivityList == null || seckillActivityList.isEmpty()) {
            return Collections.emptyList();
        }
//        List<SecKillActivityDTO> secKillActivityDTOList = new ArrayList<>();
//        for (SeckillActivity seckillActivity : seckillActivityList) {
//            SecKillActivityDTO secKillActivityDTO = new SecKillActivityDTO();
//            secKillActivityDTO.setActivityName(seckillActivity.getActivityName());
//            secKillActivityDTO.setStartTime(seckillActivity.getStartTime());
//            secKillActivityDTO.setEndTime(seckillActivity.getEndTime());
//            secKillActivityDTO.setStatus(seckillActivity.getStatus());
//            secKillActivityDTO.setActivityDesc(seckillActivity.getActivityDesc());
//            secKillActivityDTOList.add(secKillActivityDTO);
//        }
        return seckillActivityList;
    }

    @Override
    public List<SeckillActivity> getSecKillActivityListByStatusAndTime(Integer status, Date currentTime) {
        if (status == null || currentTime == null) return Collections.emptyList();
        List<SeckillActivity> seckillActivityList = secKillActivityRepository.getSecKillActivityListByStatusAndTime(status, currentTime);
        if(seckillActivityList == null || seckillActivityList.isEmpty()){
            return Collections.emptyList();
        }
//        List<SecKillActivityDTO> secKillActivityDTOList = new ArrayList<>();
//        for (SeckillActivity seckillActivity : seckillActivityList) {
//            SecKillActivityDTO secKillActivityDTO = new SecKillActivityDTO();
//            secKillActivityDTO.setActivityName(seckillActivity.getActivityName());
//            secKillActivityDTO.setStartTime(seckillActivity.getStartTime());
//            secKillActivityDTO.setEndTime(seckillActivity.getEndTime());
//            secKillActivityDTO.setStatus(seckillActivity.getStatus());
//            secKillActivityDTO.setActivityDesc(seckillActivity.getActivityDesc());
//            secKillActivityDTOList.add(secKillActivityDTO);
//        }
        return seckillActivityList;
    }

    @Override
    public SeckillActivity getSecKillActivityById(Long id) {
        if(id == null) throw new SeckillException(HttpCode.PARAMS_INVALID);
        SeckillActivity seckillActivity = secKillActivityRepository.getSecKillActivityById(id);
        if(seckillActivity == null) throw new SeckillException(HttpCode.GOODS_NOT_EXISTS);
        log.info("getSecKillActivityById:{}", JSON.toJSON(seckillActivity));
        return seckillActivity;
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

    @Override
    public List<SecKillActivityDTO> getSeckillActivityList(Integer status, Long version) {
        SeckillBusinessCache<List<SeckillActivity>> seckillActivityListCache = seckillActivityListCacheService.getCachedActivities(status, version);
        if (!seckillActivityListCache.isExist()){
            throw new SeckillException(HttpCode.ACTIVITY_NOT_EXISTS);
        }
        //稍后再试，前端需要对这个状态做特殊处理，即不去刷新数据，静默稍后再试
        if (seckillActivityListCache.isRetryLater()){
            throw new SeckillException(HttpCode.RETRY_LATER);
        }
        List<SecKillActivityDTO> secKillActivityDTOS = seckillActivityListCache.getData().stream().map(seckillActivity -> {
            SecKillActivityDTO secKillActivityDTO = new SecKillActivityDTO();
            BeanUtils.copyProperties(seckillActivity, secKillActivityDTO);
            secKillActivityDTO.setVersion(seckillActivityListCache.getVersion());
            return secKillActivityDTO;
        }).collect(Collectors.toList());
        log.info("getSeckillActivityList:{}", JSON.toJSON(secKillActivityDTOS));
        return secKillActivityDTOS;
    }

    @Override
    public SecKillActivityDTO getSeckillActivity(Long activityId, Long version) {
        if(activityId == null){
            throw new SeckillException(HttpCode.PARAMS_INVALID);
        }
        SeckillBusinessCache<SeckillActivity> seckillActivityCache = seckillActivityCacheService.getCachedActivity(activityId, version);
        if (!seckillActivityCache.isExist()){
            throw new SeckillException(HttpCode.ACTIVITY_NOT_EXISTS);
        }
        if (seckillActivityCache.isRetryLater()){
            throw new SeckillException(HttpCode.RETRY_LATER);
        }
        SecKillActivityDTO secKillActivityDTO = SeckillActivityBuilder.toSeckillActivityDTO(seckillActivityCache.getData());
        secKillActivityDTO.setVersion(seckillActivityCache.getVersion());
        log.info("getSeckillActivity:{}", JSON.toJSON(secKillActivityDTO));
        return secKillActivityDTO;
    }
}
