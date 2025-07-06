import java.util.*;

public class Screen{
    private int screenNumber;
    private int totalRows;
    private int seatsPerRow;
    private Map<String, Seat> seats;

    public Screen(int screenNumber, int totalRows, int seatsPerRow) {
        this.screenNumber = screenNumber;
        this.totalRows = totalRows;
        this.seatsPerRow = seatsPerRow;
        this.seats = new HashMap<>();
        initializeSeats();
    }

    private void initializeSeats() {
        for (int row = 0; row < totalRows; row++) {
            char rowChar = (char) ('A' + row);
            for (int seat = 1; seat <= seatsPerRow; seat++) {
                String seatId = String.format("%c%02d", rowChar, seat);
                seats.put(seatId, new Seat(rowChar, seat, false));
            }
        }
    }

    public int getScreenNumber() { return screenNumber; }
    public int getTotalRows() { return totalRows; }
    public int getSeatsPerRow() { return seatsPerRow; }

    public void displaySeatMap() {
        System.out.println("\n=== Screen " + screenNumber + " Seat Map ===");
        System.out.println("Legend: O = Available, X = Reserved");
        System.out.println();

        // Display column numbers
        System.out.print("    ");
        for (int i = 1; i <= seatsPerRow; i++) {
            System.out.printf("%02d ", i);
        }
        System.out.println();

        // Display rows with seats
        for (int row = 0; row < totalRows; row++) {
            char rowChar = (char) ('A' + row);
            System.out.printf("%-3s", rowChar);
            for (int seat = 1; seat <= seatsPerRow; seat++) {
                String seatId = String.format("%c%02d", rowChar, seat);
                Seat s = seats.get(seatId);
                System.out.print((s.isReserved() ? "X" : "O") + "  ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public boolean isSeatAvailable(String seatId) {
        Seat seat = seats.get(seatId.toUpperCase());
        return seat != null && !seat.isReserved();
    }

    public boolean reserveSeat(String seatId) {
        Seat seat = seats.get(seatId.toUpperCase());
        if (seat != null && !seat.isReserved()) {
            seat.setReserved(true);
            return true;
        }
        return false;
    }

    public boolean cancelReservation(String seatId) {
        Seat seat = seats.get(seatId.toUpperCase());
        if (seat != null && seat.isReserved()) {
            seat.setReserved(false);
            return true;
        }
        return false;
    }

    public Seat getSeat(String seatId) {
        return seats.get(seatId.toUpperCase());
    }

    public List<Seat> getAllReservedSeats() {
        List<Seat> reserved = new ArrayList<>();
        for (Seat seat : seats.values()) {
            if (seat.isReserved()) reserved.add(seat);
        }
        Collections.sort(reserved);
        return reserved;
    }

    // Inner Seat class
    public static class Seat implements Comparable<Seat> {
        private char rowChar;
        private int seatNumber;
        private boolean isReserved;

        public Seat(char rowChar, int seatNumber, boolean isReserved) {
            this.rowChar = rowChar;
            this.seatNumber = seatNumber;
            this.isReserved = isReserved;
        }

        public char getRowChar() { return rowChar; }
        public int getSeatNumber() { return seatNumber; }
        public boolean isReserved() { return isReserved; }
        public void setReserved(boolean reserved) { this.isReserved = reserved; }

        @Override
        public String toString() {
            return String.format("%c%02d", rowChar, seatNumber);
        }

        @Override
        public int compareTo(Seat other) {
            if (this.rowChar != other.rowChar) {
                return Character.compare(this.rowChar, other.rowChar);
            }
            return Integer.compare(this.seatNumber, other.seatNumber);
        }
    }
}
