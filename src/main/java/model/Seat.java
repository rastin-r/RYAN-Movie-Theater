package model;

import java.util.ArrayList;
import java.util.List;

public class Seat {
    private String seatNumber;
    private boolean isAvailable;

    public Seat(String seatNumber, boolean isAvailable) {
        this.seatNumber = seatNumber;
        this.isAvailable = isAvailable;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    // Generate seats for rows 1-9 and columns A-Z
    public static List<Seat> initializeSeats() {
        List<Seat> seats = new ArrayList<>();
        for (int row = 1; row <= 9; row++) {
            for (char col = 'A'; col <= 'Z'; col++) {
                seats.add(new Seat(row + String.valueOf(col), true));
            }
        }
        return seats;
    }
}