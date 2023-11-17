package com.mediring.config;

import com.mediring.exception.UncheckException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;
import java.util.Properties;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.mediring"})
public class WebConfig  extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] { RootConfig.class, SecurityConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] { ServletContext.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] {"/"};
    }

    @Override
    protected Filter[] getServletFilters() {
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);
        return new Filter[] {characterEncodingFilter};
    }

    @Bean
    public SimpleMappingExceptionResolver getExceptionResolver() {
        SimpleMappingExceptionResolver smer = new SimpleMappingExceptionResolver();
        smer.setDefaultErrorView("/common/error/error");        // 지정되지 않은 예외에 대한 기본 에러페이지 입니다.
        smer.setDefaultStatusCode(200);        // 상태코드 맵핑이 없는 예외를 위한 기본 상태값 입니다.
        smer.setExceptionAttribute("exception");// 기본값이 "exception" 입니다. 예외 모돌 속성의 키값입니다. ${exception.message}
        smer.setExcludedExceptions(UncheckException.class);// 하나 또는 그 이상의 예외를 리졸버에서 제외합니다. 제외된 예외는 web.xml에서 지정된 값이 적용됩니다.
        Properties mappings = new Properties();        // 예외 클래스에 대해 에러 페이지를 지정합니다.
        mappings.setProperty("com.mediring.exception.DatabaseException", "/common/error/databaseError");
        mappings.setProperty("com.mediring.exception.SecurityException", "/common/error/securityError");
        mappings.setProperty("com.mediring.exception.exception.BusinessException", "/common/error/businessError");
        mappings.setProperty("com.mediring.exception.exception.AjaxException", "/common/error/ajaxError");
        smer.setExceptionMappings(mappings);
        Properties statusCodes = new Properties();  // 에러페이지에 상태코드를 지정합니다.
        statusCodes.setProperty("/common/error/databaseError", "500");
        statusCodes.setProperty("/common/error/securityError", "403");
        statusCodes.setProperty("/common/error/businessError", "200");
        statusCodes.setProperty("/common/error/ajaxError", "200");
        smer.setStatusCodes(statusCodes);
        return smer;
    }

}
