package ua.dragunov.watchlist.view;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.dragunov.watchlist.config.PropertyConfigProvider;
import ua.dragunov.watchlist.config.security.PasswordEncryptor;
import ua.dragunov.watchlist.exceptions.DatabaseConnetionException;
import ua.dragunov.watchlist.exceptions.InvalidInputDataException;
import ua.dragunov.watchlist.model.Status;
import ua.dragunov.watchlist.model.User;
import ua.dragunov.watchlist.model.WatchlistItem;
import ua.dragunov.watchlist.persistence.DataSourceProvider;
import ua.dragunov.watchlist.persistence.JdbcUserRepository;
import ua.dragunov.watchlist.persistence.JdbcWatchlistItemRepository;
import ua.dragunov.watchlist.service.UserService;
import ua.dragunov.watchlist.service.UserServiceImpl;
import ua.dragunov.watchlist.service.WatchlistItemService;
import ua.dragunov.watchlist.service.WatchlistItemServiceIml;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

@MultipartConfig
@WebServlet(urlPatterns = {"/"})
public class HomeServlet extends HttpServlet {
    private WatchlistItemService watchlistItemService;
    private UserService userService;
    private static final Logger LOGGER = LogManager.getLogger(HomeServlet.class);



    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        try {
            watchlistItemService = new WatchlistItemServiceIml(
                    new JdbcWatchlistItemRepository(DataSourceProvider.getDataSource()
                            , new JdbcUserRepository(DataSourceProvider.getDataSource()))
            );

            userService = new UserServiceImpl(new PasswordEncryptor()
                    , new JdbcUserRepository(DataSourceProvider.getDataSource()));
        } catch (DatabaseConnetionException e) {
            LOGGER.error("error with database connection", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            User user = (User) req.getSession().getAttribute("user");
            req.setAttribute("watchlistItems", watchlistItemService.findAllById(user.getId() ));

            if (req.getRequestURI().equals("/")) {
                req.getRequestDispatcher("/home.jsp").forward(req, resp);
            }

        } catch (DatabaseConnetionException e) {
            LOGGER.error("error with database connection {}",e.getMessage(), e);
        }
        req.getRequestDispatcher("/404.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Properties properties = PropertyConfigProvider.getPropertyConfig();
            Part picture = req.getPart("file");



            if (!picture.getContentType().startsWith("image")) {
                throw new InvalidInputDataException("file must be an image");
            }


            String path = properties.getProperty("watchlist_uploads_directory") + picture.getSubmittedFileName();
            picture.write(path);



            WatchlistItem watchlistItem = new WatchlistItem();

            watchlistItem.setTitle(req.getParameter("title"));
            watchlistItem.setPicture(properties.getProperty("watchlist_uploads_server") + picture.getSubmittedFileName());
            watchlistItem.setType(req.getParameter("type"));
            watchlistItem.setReleaseYear(Integer.parseInt(req.getParameter("released-year")));
            watchlistItem.setGenre(req.getParameter("genres"));
            watchlistItem.setStatus(Status.valueOf(req.getParameter("status")));
            watchlistItem.setDescription(req.getParameter("description"));
            watchlistItem.setUser((User) req.getSession().getAttribute("user"));

            if (!watchlistItem.dataNullValidation()) {
                throw new InvalidInputDataException("fields in watchlist item cannot be null");
            }

            watchlistItemService.save(watchlistItem);

            resp.sendRedirect("/");
        } catch (IOException | InvalidInputDataException | NumberFormatException e) {
            if (e.getClass() == InvalidInputDataException.class) {
                LOGGER.error("Something wrong with input data from the client form", e);

                resp.sendRedirect("/?error=" + e.getMessage());
            }
            if (e.getClass() == IOException.class) {
                LOGGER.error("error with writing file", e);
                resp.sendRedirect("/?error=something went wrong");
            }

            if (e.getClass() == NumberFormatException.class) {
                LOGGER.error("error with number format in post form from client. Check a released year field", e);
                resp.sendRedirect("/?error=something went wrong");
            }

        }


    }

}
