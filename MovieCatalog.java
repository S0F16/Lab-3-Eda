package a.lab3;
import java.io.*;
import java.util.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.function.ToIntFunction;

public class MovieCatalog {
    ArrayList<Movie> movies; 
    String sortedByAttribute;
    
public MovieCatalog() {
    this.movies = new ArrayList<>();
    this.sortedByAttribute = null; //el catalogo no esta ordenado inicialmente
    }

public void generateMovies (int cantPeli){
    try (BufferedReader br = new BufferedReader(new FileReader("pelis.csv"))) {
            String line;
            boolean firstLine = true;

            while ((line = br.readLine()) != null && cantPeli>0) {
                if (firstLine) {
                    firstLine = false; // Saltar encabezado
                    continue;
                }

                // Dividir por punto y coma ";"
                String[] values = line.split(";", -1);

                // Validar que la línea tenga al menos 11 campos (índices 0 a 10)
                if (values.length < 11) {
                    System.err.println("Línea incompleta ignorada: " + line);
                    continue;
                }

                String title = values[4].trim();
                String director = values[12].trim();
                String genre = values[8].trim();
                int releasedYear = Integer.parseInt(values[5].trim());
                double rating = Double.parseDouble(values[9].trim());

                movies.add(new Movie(title, director, genre, releasedYear, rating));
                cantPeli--;
            }

        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error al convertir número: " + e.getMessage());
        }
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
            if (m.getRating()==rating){ 
                peliculas.add(m);
            }
        }
    }
    
    else{ //esta ordenado,  busqueda binaria
        int lo=0; int hi=movies.size()-1;
        while (lo<=hi){
            int mid=(lo+hi)/2;
            Movie aux=movies.get(mid);
            if (aux.getRating()==rating){ 
                int temp=mid;
                while(temp >= 0 && movies.get(temp).getRating()==rating){
                    peliculas.add(movies.get(temp));
                    temp--;
                }
                temp=mid+1;
                while(temp<movies.size() && movies.get(temp).getRating()==rating){
                    peliculas.add(movies.get(temp));
                    temp++;
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
    ToIntFunction<Movie> keyExtractor=Movie::getYear;
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
            
        case "radixsort" -> {
            radixSort(keyExtractor); //este metodo es numerico solo podria ser para año
        }
        
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

private void radixSort (ToIntFunction<Movie> keyExtractor){
        //extraer claves y encontrar el maximo
        int n = movies.size();
        int[] keys = new int[n];
        int max = Integer.MIN_VALUE;

        //extraer claves y validar que no sean negativas
        for (int i = 0; i < n; i++) {
            int key = keyExtractor.applyAsInt(movies.get(i));
            if (key < 0) {
                throw new IllegalArgumentException("Radix Sort no utiliza numeros negativos");
            }
            keys[i] = key;
            if (key > max) max = key;
        }

        //aplicar RadixSort por digitos
        for (int exp = 1; max / exp > 0; exp *= 10) {
            countingSortListByDigit(keyExtractor,keys, exp, n);
        }
}
public void countingSortListByDigit(ToIntFunction<Movie>keyExtractor,int[] keys, int exp, int n) {
        ArrayList<Movie> output = new ArrayList<>(Collections.nCopies(n, (Movie) null));
        int[] count = new int[10];

        for (Movie m : movies) {
            int key = keyExtractor.applyAsInt(m);
            int digit = (key / exp) % 10;
            count[digit]++;
        }

        for (int i = 1; i < 10; i++) {
            count[i] += count[i - 1];
        }


        for (int i = n - 1; i >= 0; i--) {
            int key = keyExtractor.applyAsInt(movies.get(i));
            int digit = (key / exp) % 10;
            output.set(count[digit] - 1, movies.get(i));
            count[digit]--;
        }

        for (int i = 0; i < n; i++) {
            movies.set(i, output.get(i));
        }
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

