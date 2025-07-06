import java.util.*;

public class Theater {
    private String name;
    private String location;
    private List<Screen> screens;
    private Map<Screen, List<Showtime>> schedule;

    public Theater(String name, String location) {
        this.name = name;
        this.location = location;
        this.screens = new ArrayList<>();
        this.schedule = new HashMap<>();
        initializeScreens();
    }

    private void initializeScreens() {
        for (int i = 1; i <= 5; i++) {
            Screen screen = new Screen(i, 4, 10); // 4 rows (A-D), 10 seats per row
            screens.add(screen);
            schedule.put(screen, new ArrayList<>());
        }
    }

    public String getName() { return name; }
    public String getLocation() { return location; }
    public List<Screen> getScreens() { return screens; }

    public void addShowtime(int screenNumber, Showtime showtime) {
        if (screenNumber < 1 || screenNumber > screens.size()) return;
        Screen screen = screens.get(screenNumber - 1);
        schedule.get(screen).add(showtime);
    }

    public void displaySchedule() {
        System.out.println("\n=== Schedule for " + name + " ===");
        System.out.println("Location: " + location);

        for (Screen screen : screens) {
            System.out.println("\nScreen " + screen.getScreenNumber() + ":");
            List<Showtime> showtimes = schedule.get(screen);
            if (showtimes.isEmpty()) {
                System.out.println("  No showtimes scheduled.");
            } else {
                for (int i = 0; i < showtimes.size(); i++) {
                    System.out.println("  " + (i + 1) + ". " + showtimes.get(i));
                }
            }
        }
    }

    public Showtime getShowtime(int screenNumber, int showtimeIndex) {
        if (screenNumber < 1 || screenNumber > screens.size()) return null;
        Screen screen = screens.get(screenNumber - 1);
        List<Showtime> showtimes = schedule.get(screen);
        if (showtimeIndex < 0 || showtimeIndex >= showtimes.size()) return null;
        return showtimes.get(showtimeIndex);
    }

    public Screen getScreen(int screenNumber) {
        if (screenNumber < 1 || screenNumber > screens.size()) return null;
        return screens.get(screenNumber - 1);
    }

    @Override
    public String toString() {
        return name + " - " + location;
    }
}
