package cn.keking.web.filter;

import cn.keking.config.ConfigConstants;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Author：houzheng
 * Date：11-18
 * url过滤器
 */
public class BaseUrlFilter implements Filter {

    private static String baseURL;

    /**
     * Author：houzheng
     * Date：11-18
     * 获取baseurl
     */
    public static String getBaseURL() {
        String baseUrl;
        try {
            baseUrl = (String) RequestContextHolder.currentRequestAttributes().getAttribute("baseUrl", 0);
        } catch (Exception e) {
            baseUrl = baseURL;
        }
        return baseUrl;
    }


    @Override
    public void init(FilterConfig filterConfig) {
        // do nothing but has to be override
    }

    /**
     * Author：houzheng
     * Date：11-18
     * 检查url末尾是否有'/'，没有则添加
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String baseUrl;
        StringBuilder pathBuilder = new StringBuilder();
        pathBuilder.append(request.getScheme()).append("://").append(request.getServerName()).append(":")
                .append(request.getServerPort()).append(((HttpServletRequest) request).getContextPath()).append("/");
        String baseUrlTmp = ConfigConstants.getBaseUrl();
        if (baseUrlTmp != null && !ConfigConstants.DEFAULT_BASE_URL.equalsIgnoreCase(baseUrlTmp)) {
            if (!baseUrlTmp.endsWith("/")) {
                baseUrlTmp = baseUrlTmp.concat("/");
            }
            baseUrl = baseUrlTmp;
        } else {
            baseUrl = pathBuilder.toString();
        }
        baseURL = baseUrl;
        request.setAttribute("baseUrl", baseUrl);
        filterChain.doFilter(request, response);
    }


    /**
     * Author：houzheng
     * Date：11-18
     * 销毁
     */
    @Override
    public void destroy() {
        // do nothing but have to be override
    }
}