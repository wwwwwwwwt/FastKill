/**
 * Copyright 2022-9999 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.ztw.fastkill.interfaces.controller;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.ztw.fastkill.application.service.SeckillUserService;

import org.ztw.fastkill.common.code.ErrorCode;
import org.ztw.fastkill.common.exception.SeckillException;
import org.ztw.fastkill.common.response.ResponseMessage;
import org.ztw.fastkill.common.response.ResponseMessageBuilder;
import org.ztw.fastkill.domain.dto.SeckillUserDTO;
import org.ztw.fastkill.domain.model.SeckillUser;


@RestController
@RequestMapping(value = "/user")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*", originPatterns = "*")
@Slf4j
public class SeckillUserController {

    @Autowired
    private SeckillUserService seckillUserService;

    /**
     * 测试系统
     */
    @RequestMapping(value = "/get", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseMessage<SeckillUser> getUser(@RequestAttribute("userId") Long userId) {
        return ResponseMessageBuilder.build(ErrorCode.SUCCESS.getCode(), seckillUserService.getSeckillUserById(userId));
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseMessage<String> login(@RequestBody SeckillUserDTO seckillUserDTO) {
        log.info("用户登录信息：{}", seckillUserDTO);
        try {
            ResponseMessage<String> responseMessage = ResponseMessageBuilder.build(ErrorCode.SUCCESS.getCode(), seckillUserService.login(seckillUserDTO.getUserName(), seckillUserDTO.getPassword()));
            log.info("用户登录返回信息：{}", JSON.toJSON(responseMessage));
            return responseMessage;
        } catch (SeckillException e) {
            return ResponseMessageBuilder.build(e.getCode(), e.getMessage());
        } catch (Exception e) {
            return ResponseMessageBuilder.build(ErrorCode.SERVER_EXCEPTION.getCode(), e.getMessage());
        }

    }
}
