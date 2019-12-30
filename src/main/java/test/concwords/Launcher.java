package test.concwords;

public class Launcher {
    public static void main(String[] args) {

        ConcatenatedWordsFinder finder = new ConcatenatedWordsFinder();
        finder.findConcatenatedWords();

        String longest = finder.getLongestConcatenatedWord();
        String secondLongest = finder.getSecondLongestConcatenatedWord();

        System.out.println("Longest(" + longest.length() + " symbols) - " + longest);
        System.out.println("2nd longest(" + secondLongest.length() + " symbols) - " + secondLongest);
        System.out.println("Total count of concatenated words - " + finder.getTotalCountOfConcatenatenatedWords());


    }
}
