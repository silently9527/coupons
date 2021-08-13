package com.huaan9527.mall.webapi.exception;

import com.huaan9527.mall.webapi.security.SecurityUtils;
import com.huaan9527.mall.webapi.utils.Constants;
import com.huaan9527.mall.webapi.utils.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity exceptionHandler(Exception exception, HttpServletRequest request) {
        logger.error("exception message:[{}]", exception.getMessage(), exception);
        logger.error("request userId:{}, User-Agent: {}", SecurityUtils.getCurrentUserId(), request.getHeader("User-Agent"));
        return ResponseEntity.failure(HttpStatus.INTERNAL_SERVER_ERROR.value(), "服务端异常");
    }

    @ExceptionHandler(MsException.class)
    @ResponseBody
    public ResponseEntity appExceptionHandler(Exception exception) {
        return ResponseEntity.failure(Constants.BIZ_ERROR_CODE, exception.getMessage());
    }

}
