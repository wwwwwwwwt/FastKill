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
package org.ztw.fastkill.application.service.impl;


import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ztw.fastkill.application.service.RedisService;
import org.ztw.fastkill.application.service.SeckillUserService;
import org.ztw.fastkill.common.code.HttpCode;
import org.ztw.fastkill.common.constants.SeckillConstants;
import org.ztw.fastkill.common.exception.SeckillException;
import org.ztw.fastkill.common.utils.shiro.CommonsUtils;
import org.ztw.fastkill.common.utils.shiro.JwtUtils;
import org.ztw.fastkill.domain.model.SeckillUser;
import org.ztw.fastkill.domain.repository.SeckillUserRepository;

import javax.annotation.Resource;

/**
 * @author binghe(微信 : hacker_binghe)
 * @version 1.0.0
 * @description 用户Service
 * @github https://github.com/binghe001
 * @copyright 公众号: 冰河技术
 */
@Service
public class SeckillUserServiceImpl implements SeckillUserService {

    @Autowired
    private SeckillUserRepository seckillUserRepository;
    @Resource
    private RedisService redisService;

    @Override
    public SeckillUser getSeckillUserByUserName(String userName) {
        return seckillUserRepository.getSeckillUserByUserName(userName);
    }

    @Override
    public SeckillUser getSeckillUserById(Long userId) {
        String key = SeckillConstants.getKey(SeckillConstants.USER_KEY_PREFIX, String.valueOf(userId));
        return (SeckillUser) redisService.get(key);
    }

    @Override
    public String login(String userName, String password) {
        if (StringUtils.isBlank(userName)) {
            throw new SeckillException(HttpCode.USERNAME_IS_NULL);
        }
        if (StringUtils.isBlank(password)) {
            throw new SeckillException(HttpCode.PASSWORD_IS_NULL);
        }
        SeckillUser seckillUser = getSeckillUserByUserName(userName);
        if (null == seckillUser) {
            throw new SeckillException(HttpCode.FAILURE);
        }
        String encryptPassword = CommonsUtils.encryptPassword(password, userName);
        if (!encryptPassword.equals(seckillUser.getPassword())) {
            throw new SeckillException(HttpCode.PASSWORD_IS_ERROR);
        }
        String token = JwtUtils.sign(seckillUser.getId());
        String key = SeckillConstants.getKey(SeckillConstants.USER_KEY_PREFIX, String.valueOf(seckillUser.getId()));
        //缓存到Redis
        redisService.set(key, seckillUser);
        //返回Token
        return token;
    }
}
