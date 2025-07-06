public class Movie {
    private String title;
    private int duration; // in minutes
    private String genre;
    private String rating;

    public Movie(String title, int duration, String genre, String rating) {
        this.title = title;
        this.duration = duration;
        this.genre = genre;
        this.rating = rating;
    }

    public String getTitle() { return title; }
    public int getDuration() { return duration; }
    public String getGenre() { return genre; }
    public String getRating() { return rating; }

    @Override
    public String toString() {
        return String.format("%s [%s, %d min, %s]", title, genre, duration, rating);
    }
}
