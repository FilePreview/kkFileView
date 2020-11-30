package cn.keking.web.filter;

import cn.keking.config.ConfigConstants;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author chenjh
 * @since 2020/5/13 18:27
 */

/**
 * Author：houzheng
 * Date：11-18
 * url过滤器
 *
 */
public class BaseUrlFilter implements Filter {

    private static String BASE_URL;
    /**
     * Author：houzheng
     * Date：11-18
     * 获取baseurl
     *
     */
    public static String getBaseUrl() {
        String baseUrl;
        try {
            baseUrl = (String) RequestContextHolder.currentRequestAttributes().getAttribute("baseUrl",0);
        } catch (Exception e) {
            baseUrl = BASE_URL;
        }
        return baseUrl;
    }


    @Override
    public void init(FilterConfig filterConfig) {

    }
    /**
     * Author：houzheng
     * Date：11-18
     *检查url末尾是否有'/'，没有则添加
     *
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String baseUrl;
        StringBuilder pathBuilder = new StringBuilder();
        pathBuilder.append(request.getScheme()).append("://").append(request.getServerName()).append(":")
                .append(request.getServerPort()).append(((HttpServletRequest) request).getContextPath()).append("/");
        String baseUrlTmp = ConfigConstants.getBaseUrl();
        if (baseUrlTmp != null && !ConfigConstants.DEFAULT_BASE_URL.equals(baseUrlTmp.toLowerCase())) {
            if (!baseUrlTmp.endsWith("/")) {
                baseUrlTmp = baseUrlTmp.concat("/");
            }
            baseUrl = baseUrlTmp;
        } else {
            baseUrl = pathBuilder.toString();
        }
        BASE_URL = baseUrl;
        request.setAttribute("baseUrl", baseUrl);
        filterChain.doFilter(request, response);
    }

    @Override
    /**
     * Author：houzheng
     * Date：11-18
     * 销毁
     *
     */
    public void destroy() {

    }
}