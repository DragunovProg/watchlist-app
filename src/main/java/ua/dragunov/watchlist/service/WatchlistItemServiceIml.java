package ua.dragunov.watchlist.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
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
    public List<WatchlistItem>  findAllByFilteringFields(long userId, Predicate<? super WatchlistItem>...filtrationKey) {
        List<WatchlistItem> watchlistItems = watchlistItemRepository.findAllByUser(userId);


        Stream<WatchlistItem> modifiedWatchlistItemsStream = watchlistItems.stream();

        for (int i = 0; i < filtrationKey.length; i++) {
            modifiedWatchlistItemsStream = modifiedWatchlistItemsStream.filter(filtrationKey[i]);
        }

        return modifiedWatchlistItemsStream.toList();
    }


//    public static List<WatchlistItem>  findAllByFiltering(long userId, Predicate<? super WatchlistItem>...filtrationKey) {
//        //List<WatchlistItem> watchlistItems = watchlistItemRepository.findAllByUser(userId);
//
//        List<WatchlistItem> watchlistItems = generateItems();
//        Stream<WatchlistItem> modifiedWatchlistItemsStream = watchlistItems.stream();
//
//        for (int i = 0; i < filtrationKey.length; i++) {
//            modifiedWatchlistItemsStream = modifiedWatchlistItemsStream.filter(filtrationKey[i]);
//        }
//
//        return modifiedWatchlistItemsStream.toList();
//    }
//
//    public static void main(String[] args) throws JsonProcessingException {
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
//
//         findAllByFiltering(1,
//                 watchlistItem -> watchlistItem.getStatus().getStatusName().equals("Watched"),
//                 watchlistItem -> watchlistItem.getType().equals("Movie"),
//                 watchlistItem -> watchlistItem.getGenre().contains("Drama"))
//            .forEach(System.out::println);
//
//
//
//        System.out.println(objectMapper.writeValueAsString(generateItems()));
//
//
//    }
//
//
//    private static List<WatchlistItem> generateItems() {
//        List<WatchlistItem> watchlist = new ArrayList<>();
//
//        WatchlistItem item1 = new WatchlistItem();
//        item1.setId(1);
//        item1.setTitle("The Matrix");
//        item1.setPicture("matrix.jpg");
//        item1.setReleaseYear(1999);
//        item1.setStatus(Status.WATCHED);
//        item1.setType("Movie");
//        item1.setGenre("Sci-fi, Action");
//        item1.setDescription("A computer hacker discovers the truth about the world he lives in and joins a rebellion against its oppressive rulers.");
//
//        WatchlistItem item2 = new WatchlistItem();
//        item2.setId(2);
//        item2.setTitle("Breaking Bad");
//        item2.setPicture("breaking_bad.jpg");
//        item2.setReleaseYear(2008);
//        item2.setStatus(Status.WATCHED);
//        item2.setType("TV Show");
//        item2.setGenre("Drama, Crime");
//        item2.setDescription("A high school chemistry teacher diagnosed with terminal cancer turns to manufacturing and selling methamphetamine to secure his family's future.");
//
//        WatchlistItem item3 = new WatchlistItem();
//        item3.setId(3);
//        item3.setTitle("Inception");
//        item3.setPicture("inception.jpg");
//        item3.setReleaseYear(2010);
//        item3.setStatus(Status.PLAN_TO_WATCH);
//        item3.setType("Movie");
//        item3.setGenre("Sci-fi, Action, Thriller");
//        item3.setDescription("A professional thief is offered a seemingly impossible task: 'inception' - implanting an idea into someone's subconscious mind");
//
//
//        watchlist.add(item1);
//        watchlist.add(item2);
//        watchlist.add(item3);
//
//        return watchlist;
//
//    }


}
