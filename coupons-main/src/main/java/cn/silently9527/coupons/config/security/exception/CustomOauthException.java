package cn.silently9527.coupons.config.security.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

/**
 * @author starBlues
 * @version 1.0
 * @since 2020-09-08
 */
@Getter
@JsonSerialize(using = CustomOauthExceptionSerializer.class)
public class CustomOauthException extends OAuth2Exception {

    private final String oAuth2ErrorCode;
    private final int httpErrorCode;

    public CustomOauthException(String msg, String oAuth2ErrorCode, int httpErrorCode) {
        super(msg);
        this.oAuth2ErrorCode = oAuth2ErrorCode;
        this.httpErrorCode = httpErrorCode;
    }

}
