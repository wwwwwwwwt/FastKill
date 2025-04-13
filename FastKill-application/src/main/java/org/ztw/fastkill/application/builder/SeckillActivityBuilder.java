
package org.ztw.fastkill.application.builder;


import org.ztw.fastkill.application.builder.common.SeckillCommonBuilder;
import org.ztw.fastkill.application.command.SeckillActivityCommand;
import org.ztw.fastkill.common.utils.bean.BeanUtil;
import org.ztw.fastkill.domain.dto.SecKillActivityDTO;
import org.ztw.fastkill.domain.model.SeckillActivity;

public class SeckillActivityBuilder extends SeckillCommonBuilder {

    public static SeckillActivity toSeckillActivity(SeckillActivityCommand seckillActivityCommand){
        if (seckillActivityCommand == null){
            return null;
        }
        SeckillActivity seckillActivity = new SeckillActivity();
        BeanUtil.copyProperties(seckillActivityCommand, seckillActivity);
        return seckillActivity;
    }

    public static SecKillActivityDTO toSeckillActivityDTO(SeckillActivity seckillActivity){
        if (seckillActivity == null){
            return null;
        }
        SecKillActivityDTO seckillActivityDTO = new SecKillActivityDTO();
        BeanUtil.copyProperties(seckillActivity, seckillActivityDTO);
        return seckillActivityDTO;
    }
}
