package cn.keking.web.filter;


import javax.servlet.*;
import java.io.IOException;


/**
 * Author：houzheng
 * Date：11-18
 * 中文路径过滤器
 * 过滤请求和响应中的路径编码是否是UTF-8，如果不是，则修改为UTF-8
 */
public class ChinesePathFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {
       // do nothing but have to be overridden
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // do nothing but have to be overridden
    }
}