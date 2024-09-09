package ua.dragunov.watchlist.model;

public enum Status {
    WATCHED("Watched", "#33cc33"),
    WATCHING("Watching", "#2196F3"),
    PLAN_TO_WATCH("Plan to Watch", "#FF7F50");

    private final String statusName;
    private final String backgroundColor;

    Status(String statusName, String backgroundColor) {
        this.statusName = statusName;
        this.backgroundColor = backgroundColor;
    }

    public String getStatusName() {
        return statusName;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }
}
