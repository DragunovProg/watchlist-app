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
        User user = userService.findByEmail(req.getParameter("email"));

        try {
            if (user == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);

                throw new AuthenticationException("user not found");
            }
            if (!user.getPassword().equals(req.getParameter("password"))) {
                resp.sendError(HttpServletResponse.SC_FORBIDDEN);
                throw new AuthenticationException("wrong password");
            }

            HttpSession session = req.getSession();
            session.setAttribute("user", user);
        } catch (AuthenticationException e) {
            System.out.println(e.getMessage());
        }

        resp.sendRedirect("/");
    }
}
