package org.ztw.fastkill.interfaces.controller;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.ztw.fastkill.application.service.SeckillActivityService;
import org.ztw.fastkill.common.code.ErrorCode;
import org.ztw.fastkill.common.code.HttpCode;
import org.ztw.fastkill.common.exception.SeckillException;
import org.ztw.fastkill.common.response.ResponseMessage;
import org.ztw.fastkill.common.response.ResponseMessageBuilder;
import org.ztw.fastkill.domain.dto.SecKillActivityDTO;
import org.ztw.fastkill.domain.model.SeckillActivity;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/activity")
@Slf4j
public class SeckillActivityController {

    @Resource
    private SeckillActivityService seckillActivityService;

    @RequestMapping(value = "/getActivityById", method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS})
    public ResponseMessage<SeckillActivity> getSecKillActivityById(@RequestParam Long id) {
        log.info("getSecKillActivityById: {}", id);
        try{
            SeckillActivity seckillActivity = seckillActivityService.getSecKillActivityById(id);
            if (seckillActivity == null) {
                return ResponseMessageBuilder.build(HttpCode.ACTIVITY_NOT_EXISTS.getCode());
            }
            log.info("getSecKillActivityById返回信息：{}", JSON.toJSON(seckillActivity));
            return ResponseMessageBuilder.build(HttpCode.SUCCESS.getCode(), seckillActivity);
        } catch (SeckillException e) {
            log.error("getSecKillActivityById异常：{}", e.getMessage());
            return ResponseMessageBuilder.build(e.getCode());
        } catch (Exception e) {
            log.error("getSecKillActivityById异常：{}", e.getMessage());
            return ResponseMessageBuilder.build(ErrorCode.SERVER_EXCEPTION.getCode());
        }
    }
    @RequestMapping(value = "/getActivity", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseMessage<SecKillActivityDTO> getSeckillActivity(Long activityId, Long version){
        return ResponseMessageBuilder.build(HttpCode.SUCCESS.getCode(), seckillActivityService.getSeckillActivity(activityId, version));
    }

    @RequestMapping(value = "/getSeckillActivityList", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseMessage<List<SeckillActivity>> getSecKillActivityList() {
        try{
            List<SeckillActivity> secKillActivityDTOList = seckillActivityService.getSecKillActivityListByStatus(1);
            log.info("getSecKillActivityListByStatus返回信息：{}", JSON.toJSON(secKillActivityDTOList));
            return ResponseMessageBuilder.build(HttpCode.SUCCESS.getCode(), secKillActivityDTOList);
        } catch (SeckillException e) {
            log.error("getSecKillActivityListByStatus异常：{}", e.getMessage());
            return ResponseMessageBuilder.build(e.getCode());
        } catch (Exception e) {
            log.error("getSecKillActivityListByStatus异常：{}", e.getMessage());
            return ResponseMessageBuilder.build(ErrorCode.SERVER_EXCEPTION.getCode());
        }
    }

    @RequestMapping(value = "/getActivityListByStatus", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseMessage<List<SeckillActivity>> getSecKillActivityListByStatus(@RequestParam Integer status) {
        log.info("getSecKillActivityListByStatus: {}", status);
        try{
            List<SeckillActivity> secKillActivityDTOList = seckillActivityService.getSecKillActivityListByStatus(status);
            log.info("getSecKillActivityListByStatus返回信息：{}", JSON.toJSON(secKillActivityDTOList));
            return ResponseMessageBuilder.build(HttpCode.SUCCESS.getCode(), secKillActivityDTOList);
        } catch (SeckillException e) {
            log.error("getSecKillActivityListByStatus异常：{}", e.getMessage());
            return ResponseMessageBuilder.build(e.getCode());
        } catch (Exception e) {
            log.error("getSecKillActivityListByStatus异常：{}", e.getMessage());
            return ResponseMessageBuilder.build(ErrorCode.SERVER_EXCEPTION.getCode());
        }
    }

    @RequestMapping(value = "/getActivityListByStatusAndTime", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseMessage<List<SeckillActivity>> getSecKillActivityListByStatusAndTime(@RequestParam Integer status, @RequestParam Date currentTime) {
        log.info("getSecKillActivityListByStatusAndTime: {}, {}", status, currentTime);
        try{
            List<SeckillActivity> secKillActivityDTOList = seckillActivityService.getSecKillActivityListByStatusAndTime(status, currentTime);
            log.info("getSecKillActivityListByStatusAndTime返回信息：{}", JSON.toJSON(secKillActivityDTOList));
            return ResponseMessageBuilder.build(HttpCode.SUCCESS.getCode(), secKillActivityDTOList);
        } catch (SeckillException e) {
            log.error("getSecKillActivityListByStatusAndTime异常：{}", e.getMessage());
            return ResponseMessageBuilder.build(e.getCode());
        } catch (Exception e) {
            log.error("getSecKillActivityListByStatusAndTime异常：{}", e.getMessage());
            return ResponseMessageBuilder.build(ErrorCode.SERVER_EXCEPTION.getCode());
        }
    }

    @RequestMapping(value = "/getActivityListByStatusAndNow", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseMessage<List<SeckillActivity>> getSecKillActivityListByStatusAndNow(@RequestParam Integer status) {
       return getSecKillActivityListByStatusAndTime(status, new Date());
    }

    @RequestMapping(value = "/saveActivity", method = {RequestMethod.POST})
    public ResponseMessage<String> saveSecKillActivity(@RequestBody SecKillActivityDTO secKillActivityDTO) {
        log.info("saveSecKillActivity: {}", JSON.toJSON(secKillActivityDTO));
        try{
            String activityId = seckillActivityService.saveSecKillActivity(secKillActivityDTO);
            log.info("saveSecKillActivity返回活动id：{}", activityId);
            return ResponseMessageBuilder.build(HttpCode.SUCCESS.getCode(), activityId);
        } catch (SeckillException e) {
            log.error("saveSecKillActivity异常：{}", e.getMessage());
            return ResponseMessageBuilder.build(e.getCode());
        } catch (Exception e) {
            log.error("saveSecKillActivity异常：{}", e.getMessage());
            return ResponseMessageBuilder.build(ErrorCode.SERVER_EXCEPTION.getCode());
        }
    }

    /**
     *
     * @param secKillActivity
     * @return
     */
    @RequestMapping(value = "/updateActivity", method = {RequestMethod.POST})
    public ResponseMessage<Integer> updateSecKillActivity(@RequestBody SeckillActivity secKillActivity) {
        log.info("updateSecKillActivity: {}", JSON.toJSON(secKillActivity));
        try{
            Integer result = seckillActivityService.updateSecKillActivity(secKillActivity);
            log.info("updateSecKillActivity返回结果：{}", result);
            return ResponseMessageBuilder.build(HttpCode.SUCCESS.getCode(), result);
        } catch (SeckillException e) {
            log.error("updateSecKillActivity异常：{}", e.getMessage());
            return ResponseMessageBuilder.build(e.getCode());
        } catch (Exception e) {
            log.error("updateSecKillActivity异常：{}", e.getMessage());
            return ResponseMessageBuilder.build(ErrorCode.SERVER_EXCEPTION.getCode());
        }
    }

    @RequestMapping(value = "/seckillActivityList", method = {RequestMethod.GET,RequestMethod.POST})
    public ResponseMessage<List<SecKillActivityDTO>> getSeckillActivityList(@RequestParam(value = "status", required = false) Integer status,
                                                                            @RequestParam(value = "version", required = false) Long version){
        return ResponseMessageBuilder.build(HttpCode.SUCCESS.getCode(), seckillActivityService.getSeckillActivityList(status, version));
    }
}
