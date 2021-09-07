package cn.silently9527.coupons.config.unifyerror;

import cn.hutool.core.util.StrUtil;
import cn.silently9527.coupons.rest.common.Result;
import cn.silently9527.coupons.rest.common.enums.ApiEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * 统一操作异常处理
 * @author starBlues
 * @version 1.0
 */
@RestControllerAdvice
@Slf4j
public class UnifyErrorExceptionHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<String> validationBodyException(MethodArgumentNotValidException exception){

        BindingResult result = exception.getBindingResult();
        String error = "";
        if (result.hasErrors()) {

            List<ObjectError> errors = result.getAllErrors();
            for (ObjectError objectError : errors) {
                FieldError fieldError = (FieldError) objectError;
                if(StrUtil.isEmpty(error)){
                    error = objectError.getDefaultMessage();
                }
                log.error("Data check failure : object{"+fieldError.getObjectName()+"},field{"+fieldError.getField()+
                        "},errorMessage{"+fieldError.getDefaultMessage()+"}");
            }
        }
        return Result.response(ApiEnum.ERROR, "参数异常: " + error);
    }


    @ExceptionHandler(HttpMessageConversionException.class)
    public Result<String> parameterTypeException(HttpMessageConversionException exception){
        String localizedMessage = exception.getCause().getLocalizedMessage();
        return Result.response(ApiEnum.ERROR, localizedMessage);

    }

    @ExceptionHandler(BindException.class)
    public Result<String> handleBindException(BindException ex) {
        FieldError fieldError = ex.getBindingResult().getFieldError();
        return Result.response(ApiEnum.ERROR, fieldError.getDefaultMessage());
    }
}
