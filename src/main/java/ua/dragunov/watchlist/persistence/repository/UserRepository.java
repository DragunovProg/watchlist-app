package ua.dragunov.watchlist.persistence.repository;

import ua.dragunov.watchlist.model.User;

public interface UserRepository {
    public User findById(long id);
    public User findByEmail(String email);
    public void update(User user);
    public void save(User user);
    public void deleteById(long id);
}
