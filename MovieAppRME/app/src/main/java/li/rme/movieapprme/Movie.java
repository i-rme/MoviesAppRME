package li.rme.movieapprme;

public class Movie {

    int id;
    String title;
    String description;
    String director;
    int year;
    int runtime;
    float rating;
    int votes;
    float revenue;
    public int[] genres;
    public int[] actors;

    public String toString() {
        return "Movie ID:" + id + ", title: " + title + ", year: " + year;
    }
}
