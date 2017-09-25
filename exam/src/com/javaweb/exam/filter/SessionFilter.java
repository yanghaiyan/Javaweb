package com.javaweb.exam.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaweb.exam.AppConstants;
import com.javaweb.exam.model.User;
import com.javaweb.exam.util.PathUtil;
import com.javaweb.exam.util.StringUtil;

public class SessionFilter implements Filter {

    private String notNeedLoginPages = "";
    protected FilterConfig filterConfig;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
        if(filterConfig.getInitParameter("notNeedLoginPages") != null) {
            notNeedLoginPages = filterConfig.getInitParameter("notNeedLoginPages");
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
            FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        String uri =  request.getRequestURI();
        String requestedUri = uri.substring(request.getContextPath().length() + 1);

        String [] pages = notNeedLoginPages.split(",");
        boolean isAllow = false;
        for(String page : pages) {
            if (page.equals(requestedUri)) {
                isAllow = true;
                break;
            }
        }

        if (isAllow) {
            filterChain.doFilter(request, response);
        } else {
            HttpSession session = request.getSession();
            User user = (User)session.getAttribute(AppConstants.USER);
            if (user == null) {
                if (request.getMethod().toLowerCase().equals("get")) {
                    String queryString = request.getQueryString();
                    String go = requestedUri;

                    if (!StringUtil.isEmpty(queryString)) {
                        go = go + "#" + request.getQueryString();
                    }

                    response.sendRedirect(PathUtil.getFullPath("user/login?go=" + go));
                } else {
                    response.sendRedirect(PathUtil.getFullPath("user/login"));
                }

            } else {
                filterChain.doFilter(request, response);
            }
        }
    }

    @Override
    public void destroy() {
        this.notNeedLoginPages = null;
    }
}
