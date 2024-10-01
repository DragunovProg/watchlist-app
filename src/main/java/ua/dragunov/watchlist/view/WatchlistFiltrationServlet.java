package ua.dragunov.watchlist.view;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.dragunov.watchlist.exceptions.DatabaseConnetionException;
import ua.dragunov.watchlist.model.Status;
import ua.dragunov.watchlist.model.User;
import ua.dragunov.watchlist.model.WatchlistItem;
import ua.dragunov.watchlist.persistence.DataSourceProvider;
import ua.dragunov.watchlist.persistence.JdbcUserRepository;
import ua.dragunov.watchlist.persistence.JdbcWatchlistItemRepository;
import ua.dragunov.watchlist.service.WatchlistItemService;
import ua.dragunov.watchlist.service.WatchlistItemServiceIml;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.function.Predicate;

@WebServlet("/watchlist-filter")
public class WatchlistFiltrationServlet extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(WatchlistFiltrationServlet.class);
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
        User user = (User) req.getSession().getAttribute("user");
        PrintWriter writer = resp.getWriter();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        try {
            String title = req.getParameter("title");
            String type = req.getParameter("type");
            Status status = req.getParameter("status") != null ? Status.valueOf(req.getParameter("status")) : null;
            String genres = req.getParameter("genres");

            List<WatchlistItem> watchlistItemList =  watchlistItemService.findAllByFilteringFields(user.getId(),
                    watchlistItem -> {
                        if (title.isEmpty())
                            return true;
                        return watchlistItem.getTitle().equals(title);
                    },
                    watchlistItem -> {
                        if (type.isEmpty())
                            return true;
                        return watchlistItem.getType().equals(type);
                    },
                    watchlistItem -> {
                        if (status == null)
                            return true;
                        return watchlistItem.getStatus().equals(status);
                    },
                    watchlistItem -> {
                        if (genres.isEmpty())
                            return true;
                        return watchlistItem.getGenre().equals(genres);
                    });

            String watchlistItemListJson = objectMapper.writeValueAsString(watchlistItemList);

            writer.write(watchlistItemListJson);
        } catch (JsonProcessingException e) {
            LOGGER.error("Error with JSON, ", e);
            writer.write("""
                    {
                    "error": "Something went wrong"
                    }
                    """);
        }


    }
}
