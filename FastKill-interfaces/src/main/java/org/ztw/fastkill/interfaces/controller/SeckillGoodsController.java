package org.ztw.fastkill.interfaces.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.ztw.fastkill.application.service.SecKillGoodsService;
import org.ztw.fastkill.common.code.HttpCode;
import org.ztw.fastkill.common.response.ResponseMessage;
import org.ztw.fastkill.common.response.ResponseMessageBuilder;
import org.ztw.fastkill.domain.dto.SeckillGoodsDTO;
import org.ztw.fastkill.domain.model.SeckillGoods;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/goods")
//@CrossOrigin(allowCredentials = "true", allowedHeaders = "*", originPatterns = "*")
@Slf4j
public class SeckillGoodsController {
    @Resource
    private SecKillGoodsService seckillGoodsService;

    @RequestMapping(value = "/saveSeckillGoods", method = {RequestMethod.GET,RequestMethod.POST})
    public ResponseMessage<String> saveSeckillActivityDTO(@RequestBody SeckillGoodsDTO seckillGoodsDTO){
        seckillGoodsService.saveSeckillGoods(seckillGoodsDTO);
        return ResponseMessageBuilder.build(HttpCode.SUCCESS.getCode());
    }

    /**
     * 获取商品详情
     */
    @RequestMapping(value = "/getSeckillGoodsId", method = {RequestMethod.GET,RequestMethod.POST})
    public ResponseMessage<SeckillGoods> getSeckillGoodsId(@RequestParam Long id){
        return ResponseMessageBuilder.build(HttpCode.SUCCESS.getCode(), seckillGoodsService.getSeckillGoodsId(id));
    }

    /**
     * 获取商品列表
     */
    @RequestMapping(value = "/getSeckillGoodsByActivityId", method = {RequestMethod.GET,RequestMethod.POST})
    public ResponseMessage<List<SeckillGoods>> getSeckillGoodsByActivityId(@RequestParam Long activityId){
        return ResponseMessageBuilder.build(HttpCode.SUCCESS.getCode(), seckillGoodsService.getSeckillGoodsByActivityId(activityId));
    }

    /**
     * 更新商品状态
     */
    @RequestMapping(value = "/updateStatus", method = {RequestMethod.GET,RequestMethod.POST})
    public ResponseMessage<String> updateStatus(@RequestParam Integer status, @RequestParam Long id){
        seckillGoodsService.updateStatus(status, id);
        return ResponseMessageBuilder.build(HttpCode.SUCCESS.getCode());
    }
}
