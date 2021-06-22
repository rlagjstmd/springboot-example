package com.devh.hportal.configuration;

import com.fasterxml.classmate.TypeResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * <pre>
 * Description : 
 *     Swagger - API Specs
 * ===============================================
 * Member fields : 
 *     
 * ===============================================
 * 
 * Author : HeonSeung Kim
 * Date   : 2021-05-23
 * </pre>
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    /**
     * <pre>
     * Description
     *     UI 출력 설정
     * ===============================================
     * Parameters
     *
     * Returns
     *
     * Throws
     *
     * ===============================================
     *
     * Author : HeonSeung Kim
     * Date   : 2021-05-23
     * </pre>
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("HPortal")
                .description("API Specs")
                .build();
    }

    /**
     * <pre>
     * Description
     *     API Specs에 출력할 맵핑 설정
     * ===============================================
     * Parameters
     *     
     * Returns
     *     
     * Throws
     *     
     * ===============================================
     *
     * Author : HeonSeung Kim
     * Date   : 2021-05-23
     * </pre>
     */
    @Bean
    public Docket commonApi() {
        TypeResolver typeResolver = new TypeResolver();
        List<ResponseMessage> commonResponse = setCommonResponse();
        Set<String> contentType = Set.of(MediaType.APPLICATION_JSON_VALUE);

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("HPortal")
                .apiInfo(this.apiInfo())
                .select()
                .apis(RequestHandlerSelectors
                        .basePackage("com.devh.hportal.controller"))
                .paths(PathSelectors.ant("/api/**"))
                .build()
                .consumes(contentType)
                .produces(contentType)
                .directModelSubstitute(OffsetDateTime.class, String.class)
                .globalResponseMessage(RequestMethod.GET, commonResponse)
                .globalResponseMessage(RequestMethod.POST, commonResponse)
                .globalResponseMessage(RequestMethod.PUT, commonResponse)
                .globalResponseMessage(RequestMethod.PATCH, commonResponse)
                .globalResponseMessage(RequestMethod.DELETE, commonResponse)
                .additionalModels(typeResolver.resolve(ResponseEntity.class));
    }


    private List<ResponseMessage> setCommonResponse() {
        List<ResponseMessage> list = new ArrayList<>();
        list.add(new ResponseMessageBuilder().code(404).message("Not Found").responseModel(new ModelRef("ResponseEntity")).build());
        list.add(new ResponseMessageBuilder().code(500).message("Internal Error").responseModel(new ModelRef("ResponseEntity")).build());
        return list;
    }

}
