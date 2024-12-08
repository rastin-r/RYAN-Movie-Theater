package model;

import java.time.LocalDateTime;

public class Showtime {
    private int showtimeId;
    private int movieId;
    private LocalDateTime showtime;

    // Constructor
    public Showtime(int showtimeId, int movieId, LocalDateTime showtime) {
        this.showtimeId = showtimeId;
        this.movieId = movieId;
        this.showtime = showtime;
    }

    // Getters and Setters
    public int getShowtimeId() {
        return showtimeId;
    }

    public void setShowtimeId(int showtimeId) {
        this.showtimeId = showtimeId;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public LocalDateTime getShowtime() {
        return showtime;
    }

    public void setShowtime(LocalDateTime showtime) {
        this.showtime = showtime;
    }

    @Override
    public String toString() {
        return "Showtime{" +
                "showtimeId=" + showtimeId +
                ", movieId=" + movieId +
                ", showtime=" + showtime +
                '}';
    }
}
