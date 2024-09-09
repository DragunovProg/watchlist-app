package ua.dragunov.watchlist.service;

import ua.dragunov.watchlist.model.WatchlistItem;

import java.util.List;

public interface WatchlistItemService {
    public List<WatchlistItem> findAll();
    public WatchlistItem findById(long id);
    public void save(WatchlistItem watchlistItem);
    public void update(WatchlistItem watchlistItem);
    public void deleteById(long id);

}
