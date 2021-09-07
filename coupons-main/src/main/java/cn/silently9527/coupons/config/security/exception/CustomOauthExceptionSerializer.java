package cn.silently9527.coupons.config.security.exception;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import cn.silently9527.coupons.rest.common.Result;
import cn.silently9527.coupons.rest.common.enums.OAuth2Enum;

import java.io.IOException;

/**
 * @author starBlues
 * @version 1.0
 * @since 2020-12-26
 */
public class CustomOauthExceptionSerializer extends StdSerializer<CustomOauthException> {
    private static final long serialVersionUID = 2652127645704345563L;

    public CustomOauthExceptionSerializer() {
        super(CustomOauthException.class);
    }

    @Override
    public void serialize(CustomOauthException value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        Result.Builder<Object> builder = Result.toBuilder();
        Result<Object> result = builder.responseEnum(OAuth2Enum.AUTH_FAILED)
                .message(value.getMessage())
                .data(value.getAdditionalInformation())
                .builder();
        gen.writeObject(result);
    }
}
