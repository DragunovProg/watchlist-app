package ua.dragunov.watchlist.service;

import ua.dragunov.watchlist.model.Status;
import ua.dragunov.watchlist.model.WatchlistItem;
import ua.dragunov.watchlist.persistence.repository.WatchlistItemRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class WatchlistItemServiceIml implements WatchlistItemService {
    private final WatchlistItemRepository watchlistItemRepository;

    public WatchlistItemServiceIml(WatchlistItemRepository watchlistItemRepository) {
        this.watchlistItemRepository = watchlistItemRepository;
    }

    @Override
    public List<WatchlistItem> findAll() {
        return watchlistItemRepository.findAll();
    }


    @Override
    public WatchlistItem findById(long id) {
        return null;
    }

    @Override
    public void save(WatchlistItem watchlistItem) {

        watchlistItemRepository.save(watchlistItem);
    }

    @Override
    public void update(WatchlistItem watchlistItem) {

    }

    @Override
    public void deleteById(long id) {

    }
}
