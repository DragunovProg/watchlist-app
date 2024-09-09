package ua.dragunov.watchlist.view;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ua.dragunov.watchlist.config.security.PasswordEncryptor;
import ua.dragunov.watchlist.persistence.DataSourceProvider;
import ua.dragunov.watchlist.persistence.JdbcUserRepository;
import ua.dragunov.watchlist.persistence.JdbcWatchlistItemRepository;
import ua.dragunov.watchlist.service.UserService;
import ua.dragunov.watchlist.service.UserServiceImpl;
import ua.dragunov.watchlist.service.WatchlistItemService;
import ua.dragunov.watchlist.service.WatchlistItemServiceIml;

import javax.naming.NamingException;
import java.io.IOException;


@WebServlet("/")
public class HomeServlet extends HttpServlet {
    private WatchlistItemService watchlistItemService;
    private UserService userService;




    @Override
    public void init(ServletConfig config) throws ServletException {
        try {
            watchlistItemService = new WatchlistItemServiceIml(
                    new JdbcWatchlistItemRepository(DataSourceProvider.getDataSource()
                            , new JdbcUserRepository(DataSourceProvider.getDataSource()))
            );

            userService = new UserServiceImpl(new PasswordEncryptor()
                    , new JdbcUserRepository(DataSourceProvider.getDataSource()));
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("watchlistItems", watchlistItemService.findAll());

        if (req.getRequestURI().equals("/")) {
            req.getRequestDispatcher("/home.jsp").forward(req, resp);
        }

        req.getRequestDispatcher("/404.jsp").forward(req, resp);
    }

}
