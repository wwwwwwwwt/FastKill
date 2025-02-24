package org.ztw.fastkill.infrastructure.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.ztw.fastkill.domain.model.SeckillActivity;

import java.util.Date;
import java.util.List;

@Mapper
public interface SeckillActivityMapper {
    List<SeckillActivity> getSecKillActivityListByStatus(@Param("status") Integer status);

    Long saveSecKillActivity(SeckillActivity seckillActivity);

    List<SeckillActivity> getSecKillActivityListByStatusAndTime(@Param("status") Integer status, @Param("currentTime") Date currentTime);

    SeckillActivity getSecKillActivityById(@Param("id") Long id);

    int updateSecKillActivity(SeckillActivity seckillActivity);
}
