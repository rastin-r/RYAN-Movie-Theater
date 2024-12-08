package model;

public class Reservation {
    private int reservationId;
    private int userId;
    private int movieId;
    private int showtimeId;
    private String[] seatNumbers;
    private boolean isStudentOrRetiree;

    public Reservation(int reservationId, int userId, int movieId, int showtimeId, String[] seatNumbers, boolean isStudentOrRetiree) {
        this.reservationId = reservationId;
        this.userId = userId;
        this.movieId = movieId;
        this.showtimeId = showtimeId;
        this.seatNumbers = seatNumbers;
        this.isStudentOrRetiree = isStudentOrRetiree;
    }

    // Getters and Setters
    public int getReservationId() { return reservationId; }
    public int getUserId() { return userId; }
    public int getMovieId() { return movieId; }
    public int getShowtimeId() { return showtimeId; }
    public String[] getSeatNumbers() { return seatNumbers; }
    public boolean isStudentOrRetiree() { return isStudentOrRetiree; }
}
