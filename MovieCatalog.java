package a.lab3;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MovieCatalog {
    ArrayList<Movie> movies; 
    String sortedByAttribute;
    
public MovieCatalog() {
    this.movies = new ArrayList<>();
    this.sortedByAttribute = null; //el catalogo no esta ordenado inicialmente
    }

private int binarySearchByGenre(String genre) { //auxiliar
        int lo=0;
        int hi=movies.size()-1;

        while (lo<=hi){
            int mid=(lo+hi)/2;
            String midGenre = movies.get(mid).getGenre();
            int comGe = midGenre.compareToIgnoreCase(genre);
            if (comGe==0){
                return mid;
            } 
            
            else if (comGe<0){
                lo=mid+1;
            } 
            else {
                hi=mid-1;
            }
        }
        return -1; // No encontrado
    }

private int binarySearchByDirector(String director) { //auxiliar
        int lo=0;
        int hi=movies.size()-1;

        while (lo<=hi){
            int mid=(lo+hi)/2;
            String midDirector= movies.get(mid).getDirector();
            int comDi = midDirector.compareToIgnoreCase(director);
            if (comDi==0){
                return mid;
            } 
            
            else if (comDi<0){
                lo=mid+1;
            } 
            else {
                hi=mid-1;
            }
        }
        return -1; // No encontrado
    }

public ArrayList<Movie> getMoviesByRating(double rating){
    ArrayList <Movie>peliculas=new ArrayList <> ();
    if (!sortedByAttribute.equalsIgnoreCase("rating")){ //no ordenado, busqueda lineal
        for(Movie m : movies){
            if ((m.getRating()-rating)<=0.1 && (m.getRating()-rating)>= -0.1){ //margen de + - 0.1
                peliculas.add(m);
            }
        }
    }
    
    else{ //esta ordenado,  busqueda binaria
        int lo=0; int hi=movies.size()-1;
        while (lo<=hi){
            int mid=(lo+hi)/2;
            Movie aux=movies.get(mid);
            if ((aux.getRating()-rating)<=0.1 && (aux.getRating()-rating)>= -0.1){ //el rating tien margen de + - 0.1
                int temp=mid;
                double raTemp=movies.get(temp).getRating();
                while(temp>=0 && (raTemp-rating)<=0.1 && (raTemp-rating)>=0.1){
                    peliculas.add(movies.get(temp));
                    temp--;
                    raTemp=movies.get(temp).getRating();
                }
                temp=mid+1;
                while(temp<movies.size() && (raTemp-rating)<=0.1 && (raTemp-rating)>=0.1){
                    peliculas.add(movies.get(temp));
                    temp++;
                    raTemp=movies.get(temp).getRating();
                }
                break;
            }
            else if(aux.getRating()<rating){
                lo=mid+1;
            }
            else{
                hi=mid-1;
            }
        }
    }
    return peliculas;
    }

public ArrayList getMoviesByRatingRange(double lowerRating, double higherRating){
    ArrayList <Movie>peliculas=new ArrayList <> ();
    if (!sortedByAttribute.equalsIgnoreCase("rating")){ //no ordenado, busqueda lineal
        for(Movie m : movies){
            if (m.getRating()>=lowerRating && m.getRating()<=higherRating){
                peliculas.add(m);
            }
        }
    }
    
    else{ //busqueda binaria
        int lo= 0;  int hi= movies.size()-1;
            while (lo<= hi) {
            int mid= lo + (hi-lo)/2;
                if (movies.get(mid).getRating()<lowerRating) {
                    lo= mid+1;
                    } 
                    else {
                        hi=mid-1;
                    }
                }
                int Ini= lo;
                lo=0;
                hi= movies.size() - 1;
                while (lo<= hi) {
                    int mid= lo+(hi-lo)/2;
                    if (movies.get(mid).getRating()<=higherRating) {
                        lo= mid+1;
                    } 
                    else {
                        hi=mid-1;
                    }
                }
                int Fn=hi;
                for (int i=Ini; i<=Fn && i<movies.size(); i++) {
                    Movie m = movies.get(i);
                    if (m.getRating()>=lowerRating && m.getRating()<=higherRating) {
                        peliculas.add(m);
                    }
                }    
    }
    return peliculas;
}

public ArrayList getMoviesByGenre(String genre){
    ArrayList <Movie>peliculas=new ArrayList <> ();
    if (!sortedByAttribute.equalsIgnoreCase("genre")){ //no ordenado, busqueda lineal
        for(Movie m : movies){
            if (m.getGenre().equalsIgnoreCase(genre)){
                peliculas.add(m);
            }
        }
    }
    
    else{ //esta ordenado,  busqueda binaria
        int index = binarySearchByGenre(genre);
            if (index!= -1) {
                int left=index;
                while (left>=0 && movies.get(left).getGenre().equalsIgnoreCase(genre)) {
                    left--;
                }

                int right=index;
                while (right<movies.size() && movies.get(right).getGenre().equalsIgnoreCase(genre)) {
                    right++;
                }

                for (int i=left + 1; i<right; i++) { // Añade las películas dentro del rango
                    peliculas.add(movies.get(i));
                }
            }
        }
    return peliculas;
}

public ArrayList getMoviesByDirector(String director){
    ArrayList <Movie>peliculas=new ArrayList <> ();
    if (!sortedByAttribute.equalsIgnoreCase("genre")){ //no ordenado, busqueda lineal
        for(Movie m : movies){
            if (m.getGenre().equalsIgnoreCase(director)){
                peliculas.add(m);
            }
        }
    }
    
    else{ //esta ordenado,  busqueda binaria
        int index= binarySearchByDirector(director);
            if (index!= -1) {
                int left=index;
                while (left>=0 && movies.get(left).getDirector().equalsIgnoreCase(director)) {
                    left--;
                }

                int right=index;
                while (right<movies.size() && movies.get(right).getDirector().equalsIgnoreCase(director)) {
                    right++;
                }

                for (int i=left+1; i<right; i++) { // Añade las películas dentro del rango
                    peliculas.add(movies.get(i));
                }
            }
        }
    return peliculas;
}

public ArrayList getMoviesByYear(int year){
   ArrayList <Movie>peliculas=new ArrayList <> ();
   if (!sortedByAttribute.equalsIgnoreCase("releaseYear")){ //no ordenado, busqueda lineal
        for(Movie m : movies){
            if (m.getYear()==year){
            peliculas.add(m);
            }
        }
    }
    
    else{ //esta ordenado,  busqueda binaria
        int left=0;  int right=movies.size()-1;
            while(left<=right){
                int mid=(left+right)/2;
                Movie m= movies.get(mid);
                    if(m.getYear()==year){
                        int aux= mid;
                        while (aux>=0 && movies.get(aux).getYear()==year) {
                            peliculas.add(movies.get(aux));
                            aux--;
                        }
                        aux= mid+1;
                        while (aux<movies.size() && movies.get(aux).getYear()==year) {
                            peliculas.add(movies.get(aux));
                            aux++;
                        }
                        break;
                    }
                    else if (m.getYear()<year) {
                        left=mid+1;
                    }
                    else{
                        right=mid-1;
                    }
                }
        }
   return peliculas; 
}

public void sortByAlgorithm(String algorithm, String attribute){
    Comparator<Movie> comparator;
    switch (attribute.toLowerCase()) {
        case "rating" -> {
            comparator= Comparator.comparing(Movie::getRating);
            sortedByAttribute = attribute.toLowerCase();
            }
        
        case "director" -> {
            comparator= Comparator.comparing(Movie::getDirector);
            sortedByAttribute = attribute.toLowerCase();
            }
                    
        case "genre" -> {
            comparator= Comparator.comparing(Movie::getGenre);
            sortedByAttribute = attribute.toLowerCase();
            }
        
        case "year" -> {
            comparator= Comparator.comparing(Movie::getYear);
            sortedByAttribute = attribute.toLowerCase();
            }
                    
        default -> {
            comparator= Comparator.comparing(Movie::getRating);
            sortedByAttribute="rating";
            }
        }
    
    switch (algorithm.toLowerCase()) {
        case "insertionsort" -> insertionSort(comparator);
            
        case "selectionsort" -> selectionSort(comparator);//extra
            
        case "radixsort" -> radixSort();
            
        case "quicksort" -> quickSort (0, movies.size()-1, comparator);
            
        default -> Collections.sort(movies, comparator);
    }    
}

private void insertionSort(Comparator<Movie> Comp) {
            int n = movies.size();
            for (int i = 1; i < n; i++) { // ya que el primer elemento, se considera ordenado
                Movie key = movies.get(i);
                int j = i - 1;
                while (j >= 0 && Comp.compare(key, movies.get(j)) < 0) {
                    movies.set(j + 1, movies.get(j));
                    j--;
                }
                movies.set(j + 1, key);
            }
        }

private void selectionSort(Comparator<Movie> Comp) {
            int n = movies.size();
            for (int i = 0; i < n - 1; i++) {
                int min = i;
                for (int j = i + 1; j < n; j++) {
                    if (Comp.compare(movies.get(j), movies.get(min)) < 0) {
                        min = j;
                    }
                }
                if(min !=i){
                    Collections.swap(movies, i, min);
                }
            }
        }

private void radixSort (){
    
}

private void quickSort(int bj, int alt, Comparator<Movie> Comp) {
    if (bj<alt) {
        int pi= partition(bj, alt, Comp);
        quickSort(bj, pi - 1, Comp);
        quickSort(pi + 1, alt, Comp);
        }
    }

private int partition(int bj, int alt, Comparator<Movie> Comp) {
    Movie pivot = movies.get(alt);
    int i = bj - 1;
        for (int j = bj; j < alt; j++) {
            if (Comp.compare(movies.get(j), pivot) <= 0) {
                i++;
                Collections.swap(movies, i, j);
                }
            }
            Collections.swap(movies, i + 1, alt);
            return i + 1;
        }
}
