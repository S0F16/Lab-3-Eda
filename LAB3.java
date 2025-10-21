package a.lab3;
public class LAB3 {

    public static void main(String[] args) {
        int i = 100;
        MovieCatalog mc1 = new MovieCatalog();
        mc1.generateMovies(i);
        MovieCatalog mc2 = new MovieCatalog();
        mc2.generateMovies(i);
        MovieCatalog mc3 = new MovieCatalog();
        mc3.generateMovies(i);
        
        long t1 = System.nanoTime();
        mc1.sortByAlgorithm("InsertionSort", "rating");
        long t2 = System.nanoTime();
        long tiempoInsertion = t2 - t1;

        long t3 = System.nanoTime();
        mc2.sortByAlgorithm("QuickSort", "rating");
        long t4 = System.nanoTime();
        long tiempoQuick = t4 - t3;

        long t5 = System.nanoTime();
        mc3.sortByAlgorithm("RadixSort", "year");
        long t6 = System.nanoTime();
        long tiempoRadix = t6 - t5;

        System.out.println(i + "," + tiempoInsertion + "," + tiempoQuick + "," + tiempoRadix);
    }
}
