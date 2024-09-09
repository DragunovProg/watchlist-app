package ua.dragunov.watchlist.view.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ua.dragunov.watchlist.model.User;

import java.io.IOException;

@WebFilter(urlPatterns = {"/admin/*", "/admin"})
public class AdminRoleRestrictionFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        if (isAdmin(req)) {
            System.out.println("admin authenticated");
            chain.doFilter(req, res);
        } else {
            System.out.println("admin denied");
            res.sendRedirect(req.getContextPath() + "/permission-error.jsp");
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

    private boolean isAdmin(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");

        return user.getRole().toString().equalsIgnoreCase("admin");
    }
}
