package ua.dragunov.watchlist.persistence;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.dragunov.watchlist.exceptions.DatabaseConnetionException;
import ua.dragunov.watchlist.exceptions.EntityNotFoundException;
import ua.dragunov.watchlist.model.Role;
import ua.dragunov.watchlist.model.User;
import ua.dragunov.watchlist.persistence.repository.UserRepository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcUserRepository implements UserRepository {
    private static final Logger logger = LogManager.getLogger(JdbcUserRepository.class);
    private final DataSource dataSource;

    public JdbcUserRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    @Override
    public User findById(long id) {
        logger.info("findById started");
        User user = null;

        try(Connection connection = dataSource.getConnection()) {
            logger.info("findById connected to the database");

            user = new User();
            PreparedStatement preparedStatement = connection.prepareStatement("select * from users where id = ?");
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                throw new EntityNotFoundException("user " + id + " not found");
            }
            logger.info("findById executed successfully");

            user = mapResultSetToUser(resultSet);
            logger.info("(findById) user: {} founded and passed", user);


        } catch (SQLException e) {
            logger.error("JdbcUserRepository sql error : {} with user(id: {})", e.getMessage(), id);
            throw new DatabaseConnetionException("Database error occurred "
                    + "\nsql state: " + e.getSQLState(), e);
        }

        return user;
    }

    @Override
    public User findByEmail(String email) {
        logger.info("findByEmail started");
        User user = null;

        try(Connection connection = dataSource.getConnection()) {
            logger.info("findByEmail connected to the database");

            user = new User();
            PreparedStatement preparedStatement = connection.prepareStatement("select * from users where email = ?");
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                throw new EntityNotFoundException("user with email " + email + " not found");
            }
            logger.info("findByEmail executed successfully\n email: {}", email);

            user = mapResultSetToUser(resultSet);
            logger.info("user: {} founded and passed", user);

        } catch (SQLException e) {
            logger.error("JdbcUserRepository sql error : {} with user({})", e.getMessage(), email);
            throw new DatabaseConnetionException("Database error occurred "
                    + "\nsql state: " + e.getSQLState(), e);
        }

        return user;
    }

    @Override
    public void update(User user) {

        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("""
                UPDATE users
                SET first_name = ?, last_name = ?, email = ?, password = ?, role = ?
                WHERE id = ?;
            """);

            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, user.getRole().toString());
            preparedStatement.setLong(6, user.getId());

            preparedStatement.executeUpdate();

            logger.info("user {} updated successfully", user);
        } catch (SQLException e) {
            logger.error("JdbcUserRepository sql error : {} with user: {}",e.getMessage(), user);
            throw new DatabaseConnetionException("Database error occurred while fetching data item with id " + user.getId()
                    + "\nsql state: " + e.getSQLState(), e);
        }
    }

    @Override
    public void save(User user) {
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("""
                INSERT INTO users (first_name, last_name, email, password, role) VALUES (?, ?, ?, ?, ?);
            """);

            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, defaultRoleUserInsertion(user.getRole()).toString());



            preparedStatement.executeUpdate();

            logger.info("user {} saved successfully", user);
        } catch (SQLException e) {
            logger.error("JdbcUserRepository sql error : {} with user: {}",e.getMessage(), user);
            throw new DatabaseConnetionException("Database error occurred while fetching data item with email " + user.getEmail()
                    + "\nsql state: " + e.getSQLState(), e);
        }
    }

    @Override
    public void deleteById(long id) {
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("""
                DELETE FROM users
                WHERE id = ?;
            """);

            preparedStatement.setLong(1, id);

            preparedStatement.executeUpdate();

            logger.info("user with id {} deleted successfully", id);

        } catch (SQLException e) {
            logger.error("JdbcUserRepository sql error : with user(id: {})", e.getMessage(), id);
            throw new DatabaseConnetionException("Database error occurred "
                    + "\nsql state: " + e.getSQLState(), e);
        }
        catch (DatabaseConnetionException e) {
            logger.error("DatabaseConnection error: {}", e.getMessage());
        }
    }

    private User mapResultSetToUser(ResultSet resultSet) throws SQLException {
        User user = new User();

        user.setId(resultSet.getLong("id"));
        user.setFirstName(resultSet.getString("first_name"));
        user.setLastName(resultSet.getString("last_name"));
        user.setEmail(resultSet.getString("email"));
        user.setPassword(resultSet.getString("password"));
        user.setRole(Role.valueOf(resultSet.getString("role")));

        return user;
    }

    private Role defaultRoleUserInsertion(Role role) {
        return role == null ? Role.USER : role;
    }


}
