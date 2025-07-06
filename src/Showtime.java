public class Showtime {
    private Movie movie;
    private String startTime;
    private String endTime;

    public Showtime(Movie movie, String startTime, String endTime) {
        this.movie = movie;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Movie getMovie() {
        return movie;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    @Override
    public String toString() {
        return String.format("Movie: %s | Time: %s - %s", movie.getTitle(), startTime, endTime);
    }
}
