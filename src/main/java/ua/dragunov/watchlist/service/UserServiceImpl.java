package ua.dragunov.watchlist.service;

import ua.dragunov.watchlist.config.security.EncryptorProvider;
import ua.dragunov.watchlist.config.security.PasswordEncryptor;
import ua.dragunov.watchlist.model.User;
import ua.dragunov.watchlist.persistence.DataSourceProvider;
import ua.dragunov.watchlist.persistence.JdbcUserRepository;
import ua.dragunov.watchlist.persistence.repository.UserRepository;

import javax.naming.NamingException;

public class UserServiceImpl implements UserService {
    private final EncryptorProvider<String, String> encryptorProvider;
    private final UserRepository userRepository;

    public UserServiceImpl(EncryptorProvider<String, String> encryptorProvider, UserRepository userRepository) {
        this.encryptorProvider = encryptorProvider;
        this.userRepository = userRepository;
    }

    @Override
    public User findByEmail(String email) {
        User user = userRepository.findByEmail(email);
        user.setPassword(encryptorProvider.decode(user.getPassword()));

        return user;
    }

    @Override
    public User findById(long id) {
        User user = userRepository.findById(id);
        user.setPassword(encryptorProvider.decode(user.getPassword()));

        return user;
    }

    @Override
    public void deleteById(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void deleteByEmail(String email) {

    }

    @Override
    public void update(User user) {
        user.setPassword(encryptorProvider.encode(user.getPassword()));
        userRepository.update(user);
    }

    @Override
    public void save(User user) {
        user.setPassword(encryptorProvider.encode(user.getPassword()));
        userRepository.save(user);
    }
}
