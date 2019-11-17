import java.nio.file.Paths;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;

public class WordScanner {
    public static void main(String args[]){
        String book1 = Paths.get(System.getProperty("user.dir"),"book1.txt" ).toString();
        String book2= Paths.get(System.getProperty("user.dir"),"book2.txt" ).toString();
        String book3= Paths.get(System.getProperty("user.dir"),"book3.txt" ).toString();

        long startTime = System.nanoTime();
        Map<String, LongAdder>  counter = new ConcurrentHashMap<>();

        Thread bookScanner1 = new BookScanner(book1, counter);
        bookScanner1.start();

        Thread bookScanner2 = new BookScanner(book2, counter);
        bookScanner2.start();

        Thread bookScanner3 = new BookScanner(book3, counter);
        bookScanner3.start();

        try{
            bookScanner1.join();
            bookScanner2.join();
            bookScanner3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long totalCount = 0;
        for(String word: counter.keySet()){
            totalCount += counter.get(word).longValue();
        }
        System.out.println("Total number of words are: "+totalCount);
    }
}
