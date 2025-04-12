package org.ztw.fastkill.interfaces.interception;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.ztw.fastkill.common.code.HttpCode;
import org.ztw.fastkill.common.constants.SeckillConstants;
import org.ztw.fastkill.common.exception.SeckillException;
import org.ztw.fastkill.common.utils.shiro.JwtUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    private static final String USER_ID = "userId";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 放行预检请求
        if (HttpMethod.OPTIONS.matches(request.getMethod())) {
            return true;
        }
        Object attribute = request.getAttribute(USER_ID);
        if(null != attribute){
            return true;
        }
        String token = request.getHeader(SeckillConstants.TOKEN_HEADER_NAME);
        if(StringUtils.isEmpty(token)){
            throw new SeckillException(HttpCode.USER_NOT_LOGIN);
        }
        if(!JwtUtils.verify(token, SeckillConstants.JWT_SECRET)){
            throw new SeckillException(HttpCode.USER_NOT_LOGIN);
        }
        Long userId = JwtUtils.getUserId(token);
        if(null == userId){
            throw new SeckillException(HttpCode.USER_NOT_LOGIN);
        }
        HttpServletRequestWrapper httpServletRequestWrapper = new HttpServletRequestWrapper(request);
        httpServletRequestWrapper.setAttribute(USER_ID, userId);
        return true;
    }
}
