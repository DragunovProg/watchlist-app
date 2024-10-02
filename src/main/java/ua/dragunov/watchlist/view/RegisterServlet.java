package ua.dragunov.watchlist.view;


import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ua.dragunov.watchlist.config.security.PasswordEncryptor;
import ua.dragunov.watchlist.exceptions.DatabaseConnetionException;
import ua.dragunov.watchlist.model.User;
import ua.dragunov.watchlist.persistence.DataSourceProvider;
import ua.dragunov.watchlist.persistence.JdbcUserRepository;
import ua.dragunov.watchlist.service.UserService;
import ua.dragunov.watchlist.service.UserServiceImpl;

import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
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

        req.getRequestDispatcher("/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] fullName = req.getParameter("fullName").split(" ");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        HttpSession session = req.getSession();
        User user = new User();

        user.setFirstName(fullName[0]);
        user.setLastName(fullName[1] != null ? fullName[1] : "");
        user.setEmail(email);
        user.setPassword(password);

        userService.save(user);
        if (userService.findByEmail(email) != null) {
            session.setAttribute("user", user);
        }


        resp.sendRedirect("/");
    }
}
