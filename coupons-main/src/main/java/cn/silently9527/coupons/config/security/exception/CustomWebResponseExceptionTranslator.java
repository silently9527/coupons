package cn.silently9527.coupons.config.security.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;

/**
 * @author starBlues
 * @version 1.0
 * @since 2020-12-26
 */
public class CustomWebResponseExceptionTranslator extends DefaultWebResponseExceptionTranslator {

    @Override
    public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {
        ResponseEntity<OAuth2Exception> translate = super.translate(e);
        OAuth2Exception body = translate.getBody();
        e.printStackTrace();
        CustomOauthException customOauthException = new CustomOauthException(body.getMessage(),
                body.getOAuth2ErrorCode(),body.getHttpErrorCode());
        ResponseEntity<OAuth2Exception> response =
                new ResponseEntity<>(customOauthException, translate.getHeaders(),
                translate.getStatusCode());
        return response;
    }

}
