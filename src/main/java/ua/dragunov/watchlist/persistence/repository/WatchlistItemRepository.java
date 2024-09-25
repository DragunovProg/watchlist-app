package ua.dragunov.watchlist.persistence.repository;

import ua.dragunov.watchlist.model.WatchlistItem;

import java.util.List;

public interface WatchlistItemRepository {
    public WatchlistItem findById(long id);
    public List<WatchlistItem> findAll();
    public List<WatchlistItem> findAllByUser(long userId);
    public void save(WatchlistItem watchlistItem);
    public void update(WatchlistItem watchlistItem);
    public void deleteById(long id);
}
