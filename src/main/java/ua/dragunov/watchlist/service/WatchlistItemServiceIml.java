package ua.dragunov.watchlist.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.dragunov.watchlist.model.Status;
import ua.dragunov.watchlist.model.WatchlistItem;
import ua.dragunov.watchlist.persistence.repository.WatchlistItemRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

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
    public List<WatchlistItem> findAllById(long userId) {
        return watchlistItemRepository.findAllByUser(userId);
    }


    @Override
    public WatchlistItem findById(long id) {
        return watchlistItemRepository.findById(id);
    }

    @Override
    public void save(WatchlistItem watchlistItem) {

        watchlistItemRepository.save(watchlistItem);
    }

    @Override
    public void update(WatchlistItem watchlistItem) {
        watchlistItemRepository.update(watchlistItem);
    }

    @Override
    public void deleteById(long id) {
        watchlistItemRepository.deleteById(id);
    }

    @Override
    public List<WatchlistItem> findAllByFilteringFields(long userId, Predicate<? super WatchlistItem>...filtrationKey) {
        List<WatchlistItem> watchlistItems = watchlistItemRepository.findAllByUser(userId);
        Stream<WatchlistItem> modifiedWatchlistItemsStream = watchlistItems.stream();

        for (int i = 0; i < filtrationKey.length; i++) {
            modifiedWatchlistItemsStream = modifiedWatchlistItemsStream.filter(filtrationKey[i]);
        }

        return modifiedWatchlistItemsStream.toList();
    }
}
