package ua.dragunov.watchlist.service;

import ua.dragunov.watchlist.model.User;

public interface UserService {
    public User findByEmail(String email);
    public User findById(long id);

    public void deleteById(long id);
    public void deleteByEmail(String email);

    public void update(User user);
    public void save(User user);
}
