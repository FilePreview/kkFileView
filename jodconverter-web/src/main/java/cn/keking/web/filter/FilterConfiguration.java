package cn.keking.web.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.Set;


/**
 * Author：houzheng
 * Date：11-18
 * 过滤器配置
 * 对url过滤器，中文路径过滤器，信任主机过滤器，以及水印配置过滤器进行配置
 */
@Configuration
public class FilterConfiguration {

    public static final String PREVIEW = "/preview";
    public static final String PICTURES_PREVIEW = "/picturesPreview";

    /**
     * Author：houzheng
     * Date：11-18
     * 中文路径过滤器相应bean
     */
    @Bean
    public FilterRegistrationBean getChinesePathFilter() {
        ChinesePathFilter filter = new ChinesePathFilter();
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(filter);
        return registrationBean;
    }

    /**
     * Author：houzheng
     * Date：11-18
     * 信任主机过滤器响应bean
     */
    @Bean
    public FilterRegistrationBean getTrustHostFilter() {
        Set<String> filterUri = new HashSet<>();
        filterUri.add(PICTURES_PREVIEW);
        filterUri.add("/getCorsFile");
        filterUri.add("/addTask");
        filterUri.add(PREVIEW);
        TrustHostFilter filter = new TrustHostFilter();
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(filter);
        registrationBean.setUrlPatterns(filterUri);
        return registrationBean;
    }

    /**
     * Author：houzheng
     * Date：11-18
     * url过滤器响应bean
     */
    @Bean
    public FilterRegistrationBean getBaseUrlFilter() {
        Set<String> filterUri = new HashSet<>();
        filterUri.add("/index");
        filterUri.add(PICTURES_PREVIEW);
        filterUri.add(PREVIEW);
        BaseUrlFilter filter = new BaseUrlFilter();
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(filter);
        registrationBean.setUrlPatterns(filterUri);
        return registrationBean;
    }

    /**
     * Author：houzheng
     * Date：11-18
     * 水印配置过滤器响应bean
     */
    @Bean
    public FilterRegistrationBean getWatermarkConfigFilter() {
        Set<String> filterUri = new HashSet<>();
        filterUri.add(PICTURES_PREVIEW);
        filterUri.add(PREVIEW);
        WatermarkConfigFilter filter = new WatermarkConfigFilter();
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(filter);
        registrationBean.setUrlPatterns(filterUri);
        return registrationBean;
    }
}
