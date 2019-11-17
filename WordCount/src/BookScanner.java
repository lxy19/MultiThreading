import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.atomic.LongAdder;

public class BookScanner extends Thread{

    private Map<String, LongAdder> counter;
    private String pathToTextFile;
    private String bookName;
    private Scanner bookScanner;

    public BookScanner(String pathToTextFile, Map<String, LongAdder> countMap){
        this.pathToTextFile = pathToTextFile;
        this.counter = countMap;
        this.bookName = pathToTextFile.substring(pathToTextFile.lastIndexOf('/')+1);
        try {
            bookScanner = new Scanner(new File(pathToTextFile));
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        //Step:
        // scan each line
        //
        while(bookScanner.hasNext()){
               String line = bookScanner.nextLine();
               String[]words = line.split("\\s");
               for(String word: words) {
                   counter.computeIfAbsent(word, c->new LongAdder()).increment();
               }
        }
    }
}
