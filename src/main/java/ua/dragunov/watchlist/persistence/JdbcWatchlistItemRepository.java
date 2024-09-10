package ua.dragunov.watchlist.persistence;

import ua.dragunov.watchlist.exceptions.DatabaseConnetionException;
import ua.dragunov.watchlist.exceptions.EntityNotFoundException;
import ua.dragunov.watchlist.model.Status;
import ua.dragunov.watchlist.model.User;
import ua.dragunov.watchlist.model.WatchlistItem;
import ua.dragunov.watchlist.persistence.repository.UserRepository;
import ua.dragunov.watchlist.persistence.repository.WatchlistItemRepository;


import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcWatchlistItemRepository implements WatchlistItemRepository {
    private final DataSource dataSource;
    private final UserRepository userRepository;

    public JdbcWatchlistItemRepository(DataSource dataSource, UserRepository userRepository) {
        this.dataSource = dataSource;
        this.userRepository = userRepository;
    }

    @Override
    public WatchlistItem findById(long id) {
        WatchlistItem watchlistItem = null;
        try (Connection connection = dataSource.getConnection()) {
            watchlistItem = new WatchlistItem();
            PreparedStatement preparedStatement = connection.prepareStatement("""
                        SELECT * FROM watchlist_item
                        WHERE id = ?;
                    """);

            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                throw new EntityNotFoundException("watchlist item with id " + id + " not found");
            }


            watchlistItem.setId(resultSet.getLong("id"));
            watchlistItem.setTitle(resultSet.getString("title"));
            watchlistItem.setDescription(resultSet.getString("description"));
            watchlistItem.setStatus(Status.valueOf(resultSet.getString("status")));
            watchlistItem.setPicture(resultSet.getString("picture"));
            watchlistItem.setGenre(resultSet.getString("genre"));
            watchlistItem.setType(resultSet.getString("type"));
            watchlistItem.setReleaseYear(resultSet.getInt("release_year"));
            watchlistItem.setUser(userRepository.findById(resultSet.getLong("user_id")));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return watchlistItem;
    }

    @Override
    public List<WatchlistItem> findAll() {
        List<WatchlistItem> watchlistItems = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("""
                 SELECT id, title, picture, release_year, status, type, genre, description, user_id FROM watchlist_items
             """)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                WatchlistItem item = new WatchlistItem();

                item.setId(resultSet.getLong("id"));
                item.setTitle(resultSet.getString("title"));
                item.setPicture(resultSet.getString("picture"));
                item.setReleaseYear(resultSet.getInt("release_year"));
                item.setStatus(Status.valueOf(resultSet.getString("status")));
                item.setType(resultSet.getString("type"));
                item.setGenre(resultSet.getString("genre"));
                item.setDescription(resultSet.getString("description"));

                // Получение объекта User по user_id
                long userId = resultSet.getLong("user_id");
                User user = userRepository.findById(userId);
                item.setUser(user);

                // Добавление элемента в список
                watchlistItems.add(item);
            }

        } catch (SQLException e) {

            throw new DatabaseConnetionException("Database error occurred "
                    + "\nsql state: " + e.getSQLState(), e);
        }

        return watchlistItems;
    }

    @Override
    public void save(WatchlistItem watchlistItem) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("""
                 INSERT INTO watchlist_items (title, picture, release_year, status, type, genre, description, user_id)
                 VALUES (?, ?, ?, ?, ?, ?, ?, ?)
             """)) {


            preparedStatement.setString(1, watchlistItem.getTitle());
            preparedStatement.setString(2, watchlistItem.getPicture());
            preparedStatement.setInt(3, watchlistItem.getReleaseYear());
            preparedStatement.setString(4, watchlistItem.getStatus().toString());
            preparedStatement.setString(5, watchlistItem.getType());
            preparedStatement.setString(6, watchlistItem.getGenre());
            preparedStatement.setString(7, watchlistItem.getDescription());
            preparedStatement.setLong(8, watchlistItem.getUser().getId());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseConnetionException("Database error occurred while fetching data with name " + watchlistItem.getTitle()
                    + "\nsql state: " + e.getSQLState(), e);
        }
    }


    @Override
    public void update(WatchlistItem watchlistItem) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("""
                 UPDATE watchlist_items
                 SET title = ?, picture = ?, release_year = ?, status = ?, type = ?, genre = ?, description = ?, user_id = ?
                 WHERE id = ?;
             """)) {


            preparedStatement.setString(1, watchlistItem.getTitle());
            preparedStatement.setString(2, watchlistItem.getPicture());
            preparedStatement.setInt(3, watchlistItem.getReleaseYear());
            preparedStatement.setString(4, watchlistItem.getStatus().toString());
            preparedStatement.setString(5, watchlistItem.getType());
            preparedStatement.setString(6, watchlistItem.getGenre());
            preparedStatement.setString(7, watchlistItem.getDescription());
            preparedStatement.setLong(8, watchlistItem.getUser().getId());
            Status.valueOf(watchlistItem.getStatus().toString());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseConnetionException("Database error occurred while fetching data with name " + watchlistItem.getTitle()
                    + "\nsql state: " + e.getSQLState(), e);
        }
    }

    @Override
    public void deleteById(long id) {
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("""
                DELETE FROM watchlist_items
                WHERE id = ?;
            """);

            preparedStatement.setLong(1, id);

            preparedStatement.executeUpdate();


        } catch (SQLException e) {

            throw new DatabaseConnetionException("Database error occurred "
                    + "\nsql state: " + e.getSQLState(), e);
        }
    }
}
