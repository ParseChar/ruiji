package com.example.filter;

import com.alibaba.fastjson.JSON;
import com.example.common.BaseContent;
import com.example.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.LogRecord;

@Slf4j
@WebFilter(filterName = "loginCheckFilter", urlPatterns = "/*")
public class LoginCheckFilter implements Filter {
    //路径匹配器，支持通配符
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //1.定义不需要处理的请求路径
        String requestURI = request.getRequestURI();
        String[] strings = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/user/sendMsg",
                "/user/login",

                "/doc.html",
                "/webjars/**",
                "/swagger-resources",
                "/v2/api-docs"

        };
        //2.判断这次请求是否需要处理
        boolean check = check(strings, requestURI);
        //3.如果不需要处理，则直接放行
        if (check){
            filterChain.doFilter(request, response);
            return;
        }
        //4.判断登录状态，如果已经登录则直接放行
        if (request.getSession().getAttribute("employee")!=null){
            Long id = (Long) request.getSession().getAttribute("employee");
            //通过BaseContent(common包下）将同一线程的用户id拿到，具体看ruiji.md，自动填充
            BaseContent.setCurrentId(id);
            filterChain.doFilter(request, response);
            return;
        }
        //4.判断登录状态，如果已经登录则直接放行
        if (request.getSession().getAttribute("user")!=null){
            log.info("用户已经登录,用户id为：{}",request.getSession().getAttribute("user"));
            Long userId = (Long) request.getSession().getAttribute("user");
            //通过BaseContent(common包下）将同一线程的用户id拿到，具体看ruiji.md，自动填充
            BaseContent.setCurrentId(userId);
            filterChain.doFilter(request, response);
            return;
        }
        //5.如果未登录则返回未登录结果
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));

    }

    public boolean check(String[] urls,String requestURI){
        for (String url : urls) {
            boolean match = PATH_MATCHER.match(url, requestURI);
            if (match){
                return true;
            }
        }
        return false;
    }
}
