package a.lab3;
public class LAB3 {

    public static void main(String[] args) {
        int i = 100;
        while(i<=1000){
        MovieCatalog mc1 = new MovieCatalog();
        mc1.generateMovies(i);
       
        long t1= System.nanoTime();
        mc1.getMoviesByDirector("Quentin Tarantino"); 
        long t2= System.nanoTime();
        long tiempolineal= (t2-t1); 
        
        mc1.sortByAlgorithm("QuickSort", "Director");
        long t3= System.nanoTime();
        mc1.getMoviesByDirector("Quentin Tarantino"); 
        long t4= System.nanoTime();
        long tiempobinario= (t4-t3); 
        i+= 100;
    }
        System.out.println(i+","+tiempolineal+","+tiempobinario);
    }
}
