package cn.silently9527.coupons.config;

import cn.hutool.core.collection.CollectionUtil;
import cn.silently9527.coupons.config.prop.SystemProp;
import cn.silently9527.coupons.rest.common.BaseResource;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.*;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import java.util.List;
import java.util.function.Predicate;

/**
 * Swagger2 配置
 * 访问地址：http:127.0.0.1:port/doc.html
 * 文档说明：https://doc.xiaominfo.com/knife4j/
 * @author starBlues
 * @version 1.0
 */
@Configuration
@EnableSwagger2WebMvc
public class Swagger2Config {

    @Autowired
    private SystemProp systemProp;


    @Value("${server.port}")
    private String port;


    @Bean(value = "defaultApi2")
    public Docket defaultApi2() {

        Predicate<String> predicate = null;
        Boolean swaggerEnable = systemProp.getSwaggerEnable();
        if(swaggerEnable == null || !swaggerEnable){
            predicate = PathSelectors.none();
        } else {
            predicate = PathSelectors.any();
        }

        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("dev")
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .paths(predicate)
                .build();
        configGlobalOperationParameters(docket);
        configOauth2(docket);
        return docket;
    }

    private String restPackage() {
        return "com.gitee.starblues.blocks.rest";
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("接口文档")
                .description("接口说明文档")
                .termsOfServiceUrl("http://ip:" + port  + BaseResource.API + "**")
                .contact(new Contact("starBlues", "", ""))
                .version("1.0.0-SNAPSHOT")
                .build();
    }

    private void configGlobalOperationParameters(Docket docket){
        ParameterBuilder ticketPar = new ParameterBuilder();
        List<Parameter> parameters = Lists.newArrayList();
        ticketPar.name("Authorization").description("访问授权Token")
                .modelRef(new ModelRef("string")).parameterType("header")
                .required(true).build();
        parameters.add(ticketPar.build());
        docket.globalOperationParameters(parameters);
    }

    private void configOauth2(Docket docket){
        //schema
        List<GrantType> grantTypes = Lists.newArrayList();
        //密码模式
        String passwordTokenUrl="http://127.0.0.1:" + port + "/oauth/token";
        ResourceOwnerPasswordCredentialsGrant passwordCredentialsGrant =
                new ResourceOwnerPasswordCredentialsGrant(passwordTokenUrl);
        grantTypes.add(passwordCredentialsGrant);

        OAuth oAuth = new OAuthBuilder()
                .name("oauth2")
                .grantTypes(grantTypes)
                .build();
        //context
        //scope范围
        List<AuthorizationScope> scopes = Lists.newArrayList();
        scopes.add(new AuthorizationScope("read","read all resources"));
        SecurityReference securityReference=new SecurityReference("oauth2", scopes.toArray(new AuthorizationScope[]{}));
        SecurityContext securityContext= new SecurityContext(CollectionUtil.newArrayList(securityReference),
                PathSelectors.ant("/api/**"));
        //schemas
        List<SecurityScheme> securitySchemes = CollectionUtil.newArrayList(oAuth);
        //securityContexts
        List<SecurityContext> securityContexts = CollectionUtil.newArrayList(securityContext);
        docket.securityContexts(securityContexts).securitySchemes(securitySchemes);
    }

}
