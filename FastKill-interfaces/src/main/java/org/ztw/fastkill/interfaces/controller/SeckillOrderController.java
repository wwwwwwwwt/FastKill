package org.ztw.fastkill.interfaces.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.ztw.fastkill.application.service.SeckillOrderService;
import org.ztw.fastkill.common.code.HttpCode;
import org.ztw.fastkill.common.response.ResponseMessage;
import org.ztw.fastkill.common.response.ResponseMessageBuilder;
import org.ztw.fastkill.domain.dto.SeckillOrderDTO;
import org.ztw.fastkill.domain.model.SeckillOrder;

import java.util.List;

/**
 * @author binghe(微信 : hacker_binghe)
 * @version 1.0.0
 * @description 订单
 * @github https://github.com/binghe001
 * @copyright 公众号: 冰河技术
 */

@RestController
@RequestMapping(value = "/order")
@Slf4j
//@CrossOrigin(allowCredentials = "true", allowedHeaders = "*", originPatterns = "*")
public class SeckillOrderController {
    @Autowired
    private SeckillOrderService seckillOrderService;

    /**
     * 保存秒杀订单
     */
    @RequestMapping(value = "/saveSeckillOrder", method = {RequestMethod.GET,RequestMethod.POST})
    public ResponseMessage<SeckillOrder> saveSeckillOrder(@RequestBody SeckillOrderDTO seckillOrderDTO){
        SeckillOrder seckillOrder = seckillOrderService.saveSeckillOrder(seckillOrderDTO);
        log.info("保存秒杀订单成功,{}", seckillOrder);
        return ResponseMessageBuilder.build(HttpCode.SUCCESS.getCode(), seckillOrder);
    }
    /**
     * 获取用户维度的订单列表
     */
    @RequestMapping(value = "/getSeckillOrderByUserId", method = {RequestMethod.GET,RequestMethod.POST})
    public ResponseMessage<List<SeckillOrder>> getSeckillOrderByUserId(@RequestParam Long userId){
        List<SeckillOrder> seckillOrderList = seckillOrderService.getSeckillOrderByUserId(userId);
        return ResponseMessageBuilder.build(HttpCode.SUCCESS.getCode(), seckillOrderList);
    }

    /**
     * 获取活动维度的订单列表
     */
    @RequestMapping(value = "/getSeckillOrderByActivityId", method = {RequestMethod.GET,RequestMethod.POST})
    public ResponseMessage<List<SeckillOrder>> getSeckillOrderByActivityId(@RequestParam Long activityId){
        List<SeckillOrder> seckillOrderList = seckillOrderService.getSeckillOrderByActivityId(activityId);
        return ResponseMessageBuilder.build(HttpCode.SUCCESS.getCode(), seckillOrderList);
    }
}
