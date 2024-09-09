package ua.dragunov.watchlist.config.security;

public interface EncryptorProvider<T, V> {
    T encode(V value);
    T decode(V value);
}
