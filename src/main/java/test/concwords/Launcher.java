package test.concwords;

import java.util.List;

public class Launcher {
    public static void main(String[] args) {

        ConcatenatedWordsFinder finder = new ConcatenatedWordsFinder();
        finder.findConcatenatedWords();

        String longest = finder.getLongestConcatenatedWord();
        List<String> secondLongest = finder.getSecondLongestConcatenatedWord();

        System.out.println("Longest(" + longest.length() + " symbols) - " + longest);
        System.out.println("2nd longest (27 symbols) - " + secondLongest);
        System.out.println("Total count of concatenated words - " + finder.getTotalCountOfConcatenatenatedWords());

    }
}
