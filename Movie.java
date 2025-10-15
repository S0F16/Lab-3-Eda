package a.lab3;
public class Movie {
    String title;
    String director;
    String genre;
    int releaseYear;
    double rating;
    
    Movie(String title, String director, String genre, int releaseYear, double rating){
        this.title = title;
        this.director = director;
        this.genre = genre;
        this.releaseYear = releaseYear;
        this.rating = rating;    
    }
    
    double getRating (){
        return rating;
    }
    
    String getGenre(){
        return genre;
    }
    
    String getDirector(){
        return director;
    }
    
    int getYear(){
        return releaseYear;
    }
}
