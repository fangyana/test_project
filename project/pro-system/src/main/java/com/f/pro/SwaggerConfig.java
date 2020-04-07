package com.f.pro;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.List;

/**
 * @Author: FangYN
 * @Date: 2020/4/3
 * @Description: swagger在线文档/接口测试配置文件
 * api页面 /swagger-ui.html
 */
@Configuration
@EnableSwagger2
@ConditionalOnExpression("${swagger.enable}")
public class SwaggerConfig implements WebMvcConfigurer {
    @Value("${swagger.ip}")
    private String IP;

/*    // 接口单独传token  ok
    @Bean
    public Docket swaggerSpringMvcPlugin() {
        //=====添加head参数start============================
        ParameterBuilder tokenPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<Parameter>();
        tokenPar.name("Authorization").description("Token令牌，输入Bearer {token} 即可").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        pars.add(tokenPar.build());
        // =========添加head参数end===================
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                //加了ApiOperation注解的类，才生成接口文档
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                // 自行修改为自己的包路径
                // .apis(RequestHandlerSelectors.basePackage("com.f.pro.controller"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(pars)
                ;
    }
    */

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("系统后台 APIS")
                .description("系统后台接口在线测试")
                .termsOfServiceUrl(IP)
                .version("1.0.0")
                .build();
    }

    // 全局 token
    @Bean
    public Docket createApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .genericModelSubstitutes(DeferredResult.class)
                .forCodeGeneration(true)
                .useDefaultResponseMessages(false)
                .apiInfo(apiInfo())
                // 加了ApiOperation注解的类，才生成接口文档
                .select().apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.regex("^.*(?<!error)$"))
                .build()
                .securitySchemes(securitySchemes())
                .securitySchemes(securityApiKey())
                .securityContexts(securityContexts());
    }

    /**
     * 设置完成后进入SwaggerUI，右上角出现“Authorization”按钮，点击即可输入我们配置的认证参数。
     * 对于不需要输入参数的接口（上文所述的包含auth的接口），在未输入Authorization参数就可以访问。
     * 其他接口则将返回401错误。点击右上角“Authorization”按钮，输入配置的参数后即可通过认证访问。参数输入后全局有效，无需每个接口单独输入。
     * 通过Swagger2的securitySchemes配置全局参数：如下列代码所示，securitySchemes的ApiKey中增加一个名为“Authorization”，type为“header”的参数。
     *
     * @return
     */
    private List<ApiKey> securityApiKey() {
        return Arrays.asList(new ApiKey("Authorization", "Authorization", "header"));
    }

    /**
     * SecurityScheme 子类 BasicAuth OAuth ApiKey
     *
     * @return
     */
    private List<SecurityScheme> securitySchemes() {
        return Arrays.asList(new BasicAuth("basicAuth"));
    }

    /**
     * 在Swagger2的securityContexts中通过正则表达式，设置需要使用参数的接口（或者说，是去除掉不需要使用认证参数的接口），
     * 如下列代码所示，通过PathSelectors.regex("^(?!auth).*$")，所有包含"auth"的接口不需要使用securitySchemes。
     * 即不需要使用上文中设置的名为“Authorization”，type为“header”的认证参数。
     * 通俗讲，就是能匹配上的就使用默认认证，就不使用header里面的Authorization认证参数
     */
    private List<SecurityContext> securityContexts() {
        return Arrays.asList(SecurityContext.builder().securityReferences(defaultAuth()).forPaths(PathSelectors.regex("^(?!auth).*$")).build());
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference("Authorization", authorizationScopes));
    }

//    /**
//     * 重写basePackage方法，让swagger支持多个包扫描
//     *
//     * @param basePackage
//     * @return
//     */
//    public static Predicate<RequestHandler> basePackage(final String basePackage) {
//        return input -> declaringClass(input).transform(handlerPackage(basePackage)).or(true);
//    }
//
//    private static Function<Class<?>, Boolean> handlerPackage(final String basePackage) {
//        return input -> {
//            // 循环判断匹配
//            for (String strPackage : basePackage.split(",")) {
//                boolean isMatch = input.getPackage().getName().startsWith(strPackage);
//                if (isMatch) {
//                    return true;
//                }
//            }
//            return false;
//        };
//    }
//
//    private static Optional<? extends Class<?>> declaringClass(RequestHandler input) {
//        return Optional.fromNullable(input.declaringClass());
//    }

}
