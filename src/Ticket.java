import java.util.UUID;

public class Ticket {
    private String ticketId;
    private String movieTitle;
    private String showtime;
    private String theaterName;
    private int screenNumber;
    private Screen.Seat seat;
    private double price;

    public Ticket(String movieTitle, String showtime, String theaterName,
                  int screenNumber, Screen.Seat seat, double price) {
        this.ticketId = generateTicketId();
        this.movieTitle = movieTitle;
        this.showtime = showtime;
        this.theaterName = theaterName;
        this.screenNumber = screenNumber;
        this.seat = seat;
        this.price = price;
    }

    private String generateTicketId() {
        return "TKT-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    public String getTicketId() { return ticketId; }
    public String getMovieTitle() { return movieTitle; }
    public String getShowtime() { return showtime; }
    public String getTheaterName() { return theaterName; }
    public int getScreenNumber() { return screenNumber; }
    public Screen.Seat getSeat() { return seat; }
    public double getPrice() { return price; }

    @Override
    public String toString() {
        return String.format(
                "\n=== TICKET CONFIRMATION ===\n" +
                        "Ticket ID : %s\n" +
                        "Movie     : %s\n" +
                        "Showtime  : %s\n" +
                        "Theater   : %s\n" +
                        "Screen    : %d\n" +
                        "Seat      : %s\n" +
                        "Price     : $%.2f\n" +
                        "============================\n",
                ticketId, movieTitle, showtime, theaterName, screenNumber, seat, price
        );
    }
}
