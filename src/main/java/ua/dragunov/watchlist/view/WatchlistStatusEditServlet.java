package ua.dragunov.watchlist.view;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.dragunov.watchlist.exceptions.DatabaseConnetionException;
import ua.dragunov.watchlist.exceptions.EntityNotFoundException;
import ua.dragunov.watchlist.exceptions.InvalidInputDataException;
import ua.dragunov.watchlist.model.Status;
import ua.dragunov.watchlist.model.WatchlistItem;
import ua.dragunov.watchlist.persistence.DataSourceProvider;
import ua.dragunov.watchlist.persistence.JdbcUserRepository;
import ua.dragunov.watchlist.persistence.JdbcWatchlistItemRepository;
import ua.dragunov.watchlist.service.WatchlistItemService;
import ua.dragunov.watchlist.service.WatchlistItemServiceIml;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/update-status")
public class WatchlistStatusEditServlet extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(WatchlistStatusEditServlet.class);
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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.info("doPost status update method started");
        long watchlistId = Long.parseLong(req.getParameter("watchlist-id"));
        String status = req.getParameter("watchlist-status");
        PrintWriter writer = resp.getWriter();

        LOGGER.info("watchlist id: {}\nwatchlist status: {}", watchlistId, status);

        try {

            WatchlistItem watchlistItem = watchlistItemService.findById(watchlistId);
            LOGGER.info("watchlist item: {}", watchlistItem);

            Status watchlistItemStatus = Status.valueOf(status);
            watchlistItem.setStatus(watchlistItemStatus);

            watchlistItemService.update(watchlistItem);
            LOGGER.info("All good");

            resp.setContentType("application/json");
            writer.write("""
                {
                    "message": "success",
                    "status": "%s",
                    "background": "%s"
                }
                """.formatted(watchlistItemStatus.getStatusName(), watchlistItemStatus.getBackgroundColor()));
        } catch (InvalidInputDataException | DatabaseConnetionException | EntityNotFoundException e) {
            LOGGER.error("error with database connection", e);
            writer.write("""
                {
                    "message": "error"
                }
                """);

        }
    }
}
