package ua.dragunov.watchlist.view;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ua.dragunov.watchlist.config.security.PasswordEncryptor;
import ua.dragunov.watchlist.exceptions.AuthenticationException;
import ua.dragunov.watchlist.exceptions.DatabaseConnetionException;
import ua.dragunov.watchlist.model.User;
import ua.dragunov.watchlist.persistence.DataSourceProvider;
import ua.dragunov.watchlist.persistence.JdbcUserRepository;
import ua.dragunov.watchlist.service.UserService;
import ua.dragunov.watchlist.service.UserServiceImpl;

import javax.naming.NamingException;
import java.io.IOException;

@WebServlet(urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        try {
            userService = new UserServiceImpl(new PasswordEncryptor(), new JdbcUserRepository(DataSourceProvider.getDataSource()));
        } catch (DatabaseConnetionException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.getRequestDispatcher("login.jsp").forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            User user = userService.login(req.getParameter("email"), req.getParameter("password"));

            HttpSession session = req.getSession();
            session.setAttribute("user", user);

            resp.sendRedirect("/");
        } catch (AuthenticationException e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }


    }
}
