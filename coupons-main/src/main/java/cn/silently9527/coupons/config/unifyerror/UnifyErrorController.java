package cn.silently9527.coupons.config.unifyerror;

import cn.silently9527.coupons.rest.common.Result;
import cn.silently9527.coupons.rest.common.enums.ApiEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 统一的错误结果控制
 * @author starBlues
 * @version 1.0
 * @since 2020-08-17
 */
@Slf4j
public class UnifyErrorController extends BasicErrorController {

    public UnifyErrorController(ErrorAttributes errorAttributes, ErrorProperties errorProperties) {
        this(errorAttributes, errorProperties, Collections.emptyList());
    }

    public UnifyErrorController(ErrorAttributes errorAttributes,
                                ErrorProperties errorProperties,
                                List<ErrorViewResolver> errorViewResolvers) {
        super(errorAttributes, errorProperties, errorViewResolvers);
    }


    @Override
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        HttpStatus status = this.getStatus(request);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json;charset=UTF-8");

        Result.Builder<String> builder = Result.toBuilder();
        builder.responseEnum(ApiEnum.OPERATE_ERROR);

        if (status != HttpStatus.NO_CONTENT) {
            Map<String, Object> body = this.getErrorAttributes(request, this.isIncludeStackTrace(request, MediaType.ALL));
            Object errorMsg = body.get("error");
            String message = "";
            if(errorMsg != null){
                message = String.valueOf(errorMsg);
            }
            Object bodyMessage = body.get("message");
            if(bodyMessage != null){
                message = message + ":" + bodyMessage;
            }
            builder.message(message);
        }
        int statusCode = 200;
        if(status == HttpStatus.NOT_FOUND){
            // 404
            statusCode = status.value();
        }
        return new ResponseEntity(builder.builder(), httpHeaders, statusCode);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<String> validationBodyException(MethodArgumentNotValidException exception,
                                                  HttpServletRequest request) {
        BindingResult result = exception.getBindingResult();
        String message = "";

        if (result.hasErrors()) {
            List<ObjectError> errors = result.getAllErrors();
            if (errors != null) {
                errors.forEach(p -> {
                    FieldError fieldError = (FieldError) p;
                    log.error("Data check failure : object{" + fieldError.getObjectName() + "},field{" + fieldError.getField() +
                            "},errorMessage{" + fieldError.getDefaultMessage() + "}");

                });
                if (errors.size() > 0) {
                    FieldError fieldError = (FieldError) errors.get(0);
                    message = fieldError.getDefaultMessage();
                }
            }
        }
        return Result.response(ApiEnum.OPERATE_ERROR, message);
    }



}


