package test.concwords;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Launcher {
    public static void main(String[] args) {

        ConcatenatedWordsFinder finder = new ConcatenatedWordsFinder();
        finder.downloadWordsFromFile(Paths.get(""));
        finder.findConcatenatedWords();

        System.out.println("longest - " + finder.getLongestConcatenatedWord());
        System.out.println("2nd longest - " + finder.getSecondLongestConcatenatedWord());
        System.out.println("total count - " + finder.getTotalCountOfConcatenatenatedWords());


    }
}
