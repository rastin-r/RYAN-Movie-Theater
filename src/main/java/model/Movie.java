package model;

import java.time.LocalDateTime;

public class Movie {
    private int movieId;
    private String title;
    private String description;
    private String genre;
    private String cast;
    private String music;
    private String screenplay;
    private int duration;
    private LocalDateTime showTime;

    public Movie(int movieId, String title, String description, String genre, String cast,
                 String music, String screenplay, int duration, LocalDateTime showTime) {
        this.movieId = movieId;
        this.title = title;
        this.description = description;
        this.genre = genre;
        this.cast = cast;
        this.music = music;
        this.screenplay = screenplay;
        this.duration = duration;
        this.showTime = showTime;
    }

    // Getters
    public int getMovieId() { return movieId; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getGenre() { return genre; }
    public String getCast() { return cast; }
    public String getMusic() { return music; }
    public String getScreenplay() { return screenplay; }
    public int getDuration() { return duration; }
    public LocalDateTime getShowTime() { return showTime; }
}
