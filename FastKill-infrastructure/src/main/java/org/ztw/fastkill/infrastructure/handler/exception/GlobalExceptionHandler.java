package org.ztw.fastkill.infrastructure.handler.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import org.ztw.fastkill.common.code.HttpCode;
import org.ztw.fastkill.common.exception.SeckillException;
import org.ztw.fastkill.common.response.ResponseMessage;
import org.ztw.fastkill.common.response.ResponseMessageBuilder;
import org.ztw.fastkill.domain.dto.SeckillUserDTO;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(SeckillException.class)
    public ResponseMessage<SeckillUserDTO> handleException(SeckillException e)
    {
        log.error("SeckillException: {}", e.getMessage());
        return ResponseMessageBuilder.build(e.getCode(), null);
    }

    @ExceptionHandler(Exception.class)
    public ResponseMessage<String> handleException(Exception e) {
        log.error("服务器抛出了异常：{}", e);
        return ResponseMessageBuilder.build(HttpCode.SERVER_EXCEPTION.getCode(), e.getMessage());
    }
}
