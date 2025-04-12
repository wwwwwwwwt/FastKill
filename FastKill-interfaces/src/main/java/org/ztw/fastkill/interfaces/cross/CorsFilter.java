package org.ztw.fastkill.interfaces.cross;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@WebFilter(urlPatterns = "/*")
public class CorsFilter implements Filter {

    private static final String HTTP_METHOD_OPTIONS = "OPTIONS";
    private static final Set<String> ALLOWED_ORIGINS = new HashSet<>();
    static {
        ALLOWED_ORIGINS.add("http://localhost:63342");
        ALLOWED_ORIGINS.add("http://127.0.0.1:63342");
        // 添加其他允许的来源
        // ALLOWED_ORIGINS.add("http://example.com");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String origin = request.getHeader("Origin");

        // 检查来源是否在允许列表中
        if (ALLOWED_ORIGINS.contains(origin)) {
            response.setHeader("Access-Control-Allow-Origin", origin);
            response.setHeader("Vary", "Origin"); // 重要：防止缓存污染
            response.setHeader("Access-Control-Allow-Credentials", "true");
        }

        // 设置其他 CORS 头
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, access-token");
        response.setHeader("Access-Control-Max-Age", "3600");

        // 处理预检请求
        if (HTTP_METHOD_OPTIONS.equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT); // 204 No Content
            response.getWriter().close();
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }
}
