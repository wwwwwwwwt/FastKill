package org.ztw.fastkill.application.service.impl;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ztw.fastkill.application.service.SecKillGoodsService;
import org.ztw.fastkill.common.code.HttpCode;
import org.ztw.fastkill.common.enums.SeckillActivityStatus;
import org.ztw.fastkill.common.exception.SeckillException;
import org.ztw.fastkill.common.utils.snow.SnowFlakeFactory;
import org.ztw.fastkill.domain.dto.SeckillGoodsDTO;
import org.ztw.fastkill.domain.model.SeckillActivity;
import org.ztw.fastkill.domain.model.SeckillGoods;
import org.ztw.fastkill.domain.repository.SecKillActivityRepository;
import org.ztw.fastkill.domain.repository.SecKillGoodsRepository;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class SecKillGoodsServiceImpl implements SecKillGoodsService {

    @Resource
    private SecKillGoodsRepository secKillGoodsRepository;
    @Resource
    private SecKillActivityRepository secKillActivityRepository;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int saveSeckillGoods(SeckillGoodsDTO seckillGoodsDTO) {
        if(seckillGoodsDTO == null){
           throw new SeckillException(HttpCode.PARAMS_INVALID);
        }
        SeckillActivity secKillActivity = secKillActivityRepository.getSecKillActivityById(seckillGoodsDTO.getActivityId());
        if(secKillActivity == null){
            log.error("saveSeckillGoods:{} 未找到对应活动", JSON.toJSON(seckillGoodsDTO));
            throw new SeckillException(HttpCode.PARAMS_INVALID);
        }
        SeckillGoods seckillGoods = new SeckillGoods();
        BeanUtils.copyProperties(seckillGoodsDTO, seckillGoods);
        seckillGoods.setStartTime(secKillActivity.getStartTime());
        seckillGoods.setEndTime(secKillActivity.getEndTime());
        seckillGoods.setAvailableStock(seckillGoodsDTO.getInitialStock());
        seckillGoods.setId(SnowFlakeFactory.getSnowFlakeFromCache().nextId());
        seckillGoods.setStatus(SeckillActivityStatus.PUBLISHED.getCode());
        return secKillGoodsRepository.saveSecKillGoods(seckillGoods);
    }

    @Override
    public SeckillGoods getSeckillGoodsId(String id) {
        long Id = Long.parseLong(id);
        return secKillGoodsRepository.getSecKillGoodsById(Id);
    }


    @Override
    public List<SeckillGoods> getSeckillGoodsByActivityId(String activityId) {
        long id = Long.parseLong(activityId);
        return secKillGoodsRepository.getSecKillGoodsByActivityId(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateStatus(Integer status, Long id) {
        return secKillGoodsRepository.updateSecKillGoodsStatus(status, id);
    }

    @Override
    public int updateAvailableStock(Integer count, Long id) {
        return secKillGoodsRepository.updateGoodsAvailableStock(count, id);
    }

    @Override
    public Integer getAvailableStockById(Long id) {
        return secKillGoodsRepository.getAvailableStockById(id);
    }
}
