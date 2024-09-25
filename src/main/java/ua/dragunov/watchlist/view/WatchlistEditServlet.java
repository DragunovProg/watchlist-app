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
import ua.dragunov.watchlist.exceptions.DatabaseConnetionException;
import ua.dragunov.watchlist.exceptions.InvalidInputDataException;
import ua.dragunov.watchlist.model.WatchlistItem;
import ua.dragunov.watchlist.persistence.DataSourceProvider;
import ua.dragunov.watchlist.persistence.JdbcUserRepository;
import ua.dragunov.watchlist.persistence.JdbcWatchlistItemRepository;
import ua.dragunov.watchlist.service.WatchlistItemService;
import ua.dragunov.watchlist.service.WatchlistItemServiceIml;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

@MultipartConfig
@WebServlet("/watchlist-edit")
public class WatchlistEditServlet extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(WatchlistEditServlet.class);
    private WatchlistItemService watchlistItemService;
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        try {
            watchlistItemService = new WatchlistItemServiceIml(new JdbcWatchlistItemRepository(DataSourceProvider.getDataSource()
                    , new JdbcUserRepository(DataSourceProvider.getDataSource())));
        } catch (DatabaseConnetionException e) {
            LOGGER.error("error with database connection", e);
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long id = Long.parseLong(req.getParameter("watchlist-id"));
        PrintWriter writer = resp.getWriter();

        try {
            Properties properties = PropertyConfigProvider.getPropertyConfig();
            Part image = req.getPart("file");
            WatchlistItem watchlistItem = watchlistItemService.findById(id);
            String path = watchlistItem.getPicture();;


            if (image.getSize() > 0) {

                if (!image.getContentType().startsWith("image")) {
                    throw new InvalidInputDataException("file must be an image");
                }

                path = properties.getProperty("watchlist_uploads_directory") + image.getName();
                image.write(path);

            }


            watchlistItem.setTitle(req.getParameter("watchlist-title"));
            watchlistItem.setDescription(req.getParameter("watchlist-description"));
            watchlistItem.setGenre(req.getParameter("watchlist-genres"));
            watchlistItem.setType(req.getParameter("watchlist-type"));
            watchlistItem.setPicture(path);
            watchlistItem.setReleaseYear(Integer.parseInt(req.getParameter("watchlist-released-year")));

            watchlistItemService.update(watchlistItem);

            resp.setContentType("application/json");
            writer.write("""
                    {
                        "message": "success"
                    }
                    """);

        } catch (InvalidInputDataException | DatabaseConnetionException e) {
            LOGGER.error("error with database connection", e);
            writer.write("""
                    {
                        "message": "error"
                    }
                    """);

    }

    }
}
