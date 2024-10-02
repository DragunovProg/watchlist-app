package ua.dragunov.watchlist.view.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebFilter(urlPatterns = {"/*"})
public class UnauthorizedUserRestrictionFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String requestURI = req.getRequestURI();

        if (isAuthorized(req) || checkExcludes(req)) {
            chain.doFilter(req, res);
        } else {
            res.sendRedirect(req.getContextPath() + "/login");
        }


    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

    private boolean isAuthorized(HttpServletRequest request) {
        return request.getSession().getAttribute("user") != null;
    }

    private boolean checkExcludes(HttpServletRequest request) {
        return request.getRequestURI().startsWith("/static/") || request.getRequestURI().endsWith("favicon.ico")
                || request.getRequestURI().equals("/login") || request.getRequestURI().equals("/register");
    }
}
