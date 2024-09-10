package ua.dragunov.watchlist.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class WatchlistItem {
    private long id;
    private String title;
    private String picture;
    private int releaseYear;
    private Status status;
    private String type;
    private String genre;
    private String description;
    private User user;

    public WatchlistItem() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WatchlistItem that = (WatchlistItem) o;
        return id == that.id && releaseYear == that.releaseYear && Objects.equals(title, that.title) && Objects.equals(picture, that.picture)
                && status == that.status && Objects.equals(type, that.type) && Objects.equals(genre, that.genre) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, picture, releaseYear, status, type, genre, description);
    }

    @Override
    public String toString() {
        return "WatchlistItem{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", picture='" + picture + '\'' +
                ", releaseYear=" + releaseYear +
                ", status=" + status +
                ", type='" + type + '\'' +
                ", genre='" + genre + '\'' +
                ", description='" + description + '\'' +
                ", user=" + user +
                '}';
    }



}
