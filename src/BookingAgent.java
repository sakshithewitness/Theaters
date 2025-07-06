import java.util.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class BookingAgent {
    private static final double TICKET_PRICE = 1050.00; // ₹1050 per ticket
    private static final Scanner scanner = new Scanner(System.in);
    private static List<Theater> theaters = new ArrayList<>();
    private static List<Movie> movies = new ArrayList<>();
    private static List<Ticket> tickets = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("=== Movie Theater Management System ===");
        initializeData();

        boolean running = true;
        while (running) {
            displayMainMenu();
            try {
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1 -> bookTicket();
                    case 2 -> viewSchedules();
                    case 3 -> cancelBooking();
                    case 4 -> viewMyTickets();
                    case 5 -> {
                        running = false;
                        System.out.println("Thank you for using Movie Theater Management System!");
                    }
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
            }
        }

        scanner.close();
    }

    private static void initializeData() {
        movies.add(new Movie("The Shawshank Redemption", 180, "Drama", "R"));
        movies.add(new Movie("Inception", 180, "Sci-Fi", "PG-13"));
        movies.add(new Movie("Interstellar", 180, "Sci-Fi", "PG-13"));
        movies.add(new Movie("The Dark Knight", 180, "Action", "PG-13"));
        movies.add(new Movie("Mean Girls", 180, "Comedy", "PG-13"));
        movies.add(new Movie("Legally Blonde", 180, "Comedy", "PG-13"));
        movies.add(new Movie("The Devil Wears Prada", 180, "Comedy", "PG-13"));
        movies.add(new Movie("The Notebook", 180, "Romance", "PG-13"));
        movies.add(new Movie("Pride & Prejudice", 180, "Romance", "PG"));
        movies.add(new Movie("The Hangover", 180, "Comedy", "R"));
        movies.add(new Movie("Superbad", 180, "Comedy", "R"));
        movies.add(new Movie("Project X", 180, "Comedy", "R"));
        movies.add(new Movie("Avatar", 180, "Sci-Fi", "PG-13"));
        movies.add(new Movie("Titanic", 180, "Romance", "PG-13"));
        movies.add(new Movie("The Godfather", 180, "Crime", "R"));
        movies.add(new Movie("Pulp Fiction", 180, "Crime", "R"));
        movies.add(new Movie("Forrest Gump", 180, "Drama", "PG-13"));
        movies.add(new Movie("The Matrix", 180, "Sci-Fi", "R"));
        movies.add(new Movie("Star Wars: A New Hope", 180, "Sci-Fi", "PG"));
        movies.add(new Movie("Jurassic Park", 180, "Adventure", "PG-13"));
        movies.add(new Movie("The Lion King", 180, "Animation", "G"));
        movies.add(new Movie("Finding Nemo", 180, "Animation", "G"));
        movies.add(new Movie("Toy Story", 180, "Animation", "G"));
        movies.add(new Movie("Frozen", 180, "Animation", "PG"));
        movies.add(new Movie("The Avengers", 180, "Action", "PG-13"));

        theaters.add(new Theater("PVR Phoenix Palladium", "Lower Parel, Mumbai"));
        theaters.add(new Theater("INOX R-City Mall", "Ghatkopar, Mumbai"));
        theaters.add(new Theater("Carnival Cinemas IMAX", "Wadala, Mumbai"));
        theaters.add(new Theater("Cinepolis Viviana Mall", "Thane, Mumbai"));
        theaters.add(new Theater("MovieTime Goregaon West", "Mumbai"));

        scheduleMovies();
    }

    private static void scheduleMovies() {
        String[] rawStartTimes = {"09:00 AM", "12:00 PM", "03:00 PM", "06:00 PM", "09:00 PM"};
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("hh:mm a", Locale.ENGLISH);
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("HH:mm");

        int movieIndex = 0;

        for (Theater theater : theaters) {
            for (int screen = 1; screen <= 5; screen++) {
                for (String rawStart : rawStartTimes) {
                    Movie movie = movies.get(movieIndex % movies.size());
                    LocalTime start = LocalTime.parse(rawStart, inputFormatter);
                    LocalTime end = start.plusHours(3);
                    String formattedStart = start.format(outputFormatter);
                    String formattedEnd = end.format(outputFormatter);
                    Showtime showtime = new Showtime(movie, formattedStart, formattedEnd);
                    theater.addShowtime(screen, showtime);
                    movieIndex++;
                }
            }
        }
    }

    private static void displayMainMenu() {
        System.out.println("\n=== MAIN MENU ===");
        System.out.println("1. Book Ticket");
        System.out.println("2. View Theater Schedules");
        System.out.println("3. Cancel Booking");
        System.out.println("4. View My Tickets");
        System.out.println("5. Exit");
        System.out.print("Enter your choice: ");
    }

    private static void bookTicket() {
        try {
            Theater selectedTheater = selectTheater();
            if (selectedTheater == null) return;

            selectedTheater.displaySchedule();
            System.out.print("Enter screen number (1-5): ");
            int screenNumber = scanner.nextInt();
            scanner.nextLine();

            if (screenNumber < 1 || screenNumber > 5) {
                System.out.println("Invalid screen number.");
                return;
            }

            System.out.print("Enter showtime number: ");
            int showtimeNumber = scanner.nextInt();
            scanner.nextLine();

            Showtime selectedShowtime = selectedTheater.getShowtime(screenNumber, showtimeNumber - 1);
            if (selectedShowtime == null) {
                System.out.println("Invalid showtime selection.");
                return;
            }

            Screen screen = selectedTheater.getScreen(screenNumber);
            if (screen == null) {
                System.out.println("Error: Screen not found.");
                return;
            }
            screen.displaySeatMap();

            System.out.print("How many tickets do you want to book? ");
            int ticketCount = scanner.nextInt();
            scanner.nextLine();

            List<String> validSeats = new ArrayList<>();

            for (int i = 0; i < ticketCount; i++) {
                while (true) {
                    System.out.print("Enter seat " + (i + 1) + " (e.g., A01): ");
                    String seatId = scanner.nextLine().trim().toUpperCase();

                    if (screen.getSeat(seatId) == null) {
                        System.out.println("Seat does not exist.");
                    } else if (!screen.isSeatAvailable(seatId)) {
                        System.out.println("Seat already reserved.");
                    } else {
                        validSeats.add(seatId);
                        break;
                    }
                }
            }

            double totalPrice = validSeats.size() * TICKET_PRICE;
            System.out.printf("\nBooking Summary:\n");
            System.out.printf("Movie: %s\n", selectedShowtime.getMovie().getTitle());
            System.out.printf("Showtime: %s\n", selectedShowtime.getStartTime());
            System.out.printf("Theater: %s\n", selectedTheater.getName());
            System.out.printf("Screen: %d\n", screenNumber);
            System.out.printf("Seats: %s\n", validSeats);
            System.out.printf("Total Price: ₹%.2f\n", totalPrice);
            System.out.print("Confirm booking? (y/n): ");

            String confirm = scanner.nextLine();
            if (confirm.equalsIgnoreCase("y")) {
                for (String seatId : validSeats) {
                    screen.reserveSeat(seatId);
                    Screen.Seat seat = screen.getSeat(seatId);
                    Ticket ticket = new Ticket(
                            selectedShowtime.getMovie().getTitle(),
                            selectedShowtime.getStartTime(),
                            selectedTheater.getName(),
                            screenNumber,
                            seat,
                            TICKET_PRICE
                    );
                    tickets.add(ticket);
                    System.out.println("\n=== TICKET CONFIRMATION ===");
                    System.out.println("Ticket ID : " + ticket.getTicketId());
                    System.out.println("Movie     : " + ticket.getMovieTitle());
                    System.out.println("Showtime  : " + ticket.getShowtime());
                    System.out.println("Theater   : " + ticket.getTheaterName());
                    System.out.println("Screen    : " + ticket.getScreenNumber());
                    System.out.println("Seat      : " + ticket.getSeat());
                    System.out.printf("Price     : ₹%.2f\n", ticket.getPrice());
                    System.out.println("============================");
                }
                System.out.println("\nBooking successful!");
            } else {
                System.out.println("Booking cancelled.");
            }

        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("An unexpected error occurred during booking: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static Theater selectTheater() {
        System.out.println("\n=== SELECT THEATER ===");
        if (theaters.isEmpty()) {
            System.out.println("No theaters available.");
            return null;
        }
        for (int i = 0; i < theaters.size(); i++) {
            System.out.println((i + 1) + ". " + theaters.get(i));
        }
        System.out.print("Enter theater number: ");

        try {
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice < 1 || choice > theaters.size()) {
                System.out.println("Invalid theater selection.");
                return null;
            }

            return theaters.get(choice - 1);
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.nextLine();
            return null;
        }
    }

    private static void viewSchedules() {
        Theater selectedTheater = selectTheater();
        if (selectedTheater != null) {
            selectedTheater.displaySchedule();
        }
    }

    private static void cancelBooking() {
        if (tickets.isEmpty()) {
            System.out.println("No bookings found to cancel.");
            return;
        }

        System.out.println("\n=== CANCEL BOOKING ===");
        System.out.println("Your current tickets:");
        viewMyTickets();

        System.out.print("Enter ticket ID to cancel: ");
        String ticketId = scanner.nextLine().trim();

        Ticket ticketToCancel = null;
        for (Ticket ticket : tickets) {
            if (ticket.getTicketId().equalsIgnoreCase(ticketId)) {
                ticketToCancel = ticket;
                break;
            }
        }

        if (ticketToCancel == null) {
            System.out.println("Ticket not found with ID: " + ticketId);
            return;
        }

        System.out.println("Confirm cancellation for: " + ticketToCancel);
        System.out.print("Are you sure you want to cancel this ticket? (y/n): ");
        String confirm = scanner.nextLine();

        if (confirm.equalsIgnoreCase("y")) {
            Theater theater = null;
            for (Theater t : theaters) {
                if (t.getName().equals(ticketToCancel.getTheaterName())) {
                    theater = t;
                    break;
                }
            }

            if (theater != null) {
                Screen screen = theater.getScreen(ticketToCancel.getScreenNumber());
                if (screen != null) {
                    screen.cancelReservation(ticketToCancel.getSeat().toString());
                    tickets.remove(ticketToCancel);
                    System.out.println("Booking cancelled successfully.");
                    System.out.printf("Refund amount: ₹%.2f\n", ticketToCancel.getPrice());
                } else {
                    System.out.println("Error: Could not find screen for the ticket.");
                }
            } else {
                System.out.println("Error: Could not find theater for the ticket.");
            }
        } else {
            System.out.println("Cancellation aborted.");
        }
    }

    private static void viewMyTickets() {
        if (tickets.isEmpty()) {
            System.out.println("No tickets found.");
            return;
        }

        System.out.println("\n=== MY TICKETS ===");
        for (Ticket ticket : tickets) {
            System.out.println("\n=== TICKET CONFIRMATION ===");
            System.out.println("Ticket ID : " + ticket.getTicketId());
            System.out.println("Movie     : " + ticket.getMovieTitle());
            System.out.println("Showtime  : " + ticket.getShowtime());
            System.out.println("Theater   : " + ticket.getTheaterName());
            System.out.println("Screen    : " + ticket.getScreenNumber());
            System.out.println("Seat      : " + ticket.getSeat());
            System.out.printf("Price     : ₹%.2f\n", ticket.getPrice());
            System.out.println("============================");
        }
    }
}
