package org.ztw.fastkill.infrastructure.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.ztw.fastkill.domain.model.SeckillUser;

@Mapper
public interface SeckillUserMapper {

    /**
     * 根据用户名获取用户信息
     */
    SeckillUser getSeckillUserByUserName(@Param("userName") String userName);
}
