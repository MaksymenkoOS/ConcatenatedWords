package test.concwords;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Collectors;

public class ConcatenatedWordsFinder {

    private Map<String, Integer> words = new LinkedHashMap<>();
    private LinkedList<String> concatenatedWords = new LinkedList<>();

    public boolean downloadWordsFromFile(Path path) {

        LinkedList<String> wordsFromFile;
        Map<String, Integer> unsortedMap = new HashMap<>();

        try {
            wordsFromFile = FileWorker.read();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        for (String string : wordsFromFile) {

            unsortedMap.put(string, string.length());

        }

        words = sortByValueReversed(unsortedMap);

        if(wordsFromFile != null) {
            return true;
        } else {
            return false;
        }
    }

    public LinkedList<String> findConcatenatedWords() {

        if(words == null) {
            return null;
        }

        String word;

        for(Map.Entry entry : words.entrySet()) {
            word = entry.getKey().toString();
            if(isWordConcatenated(word, words)) {
                concatenatedWords.add(word);
//                FileIO.writeConc(word + " - " + word.length());
            } else {
                continue;
            }
        }

        return concatenatedWords;
    }

    public String getLongestConcatenatedWord() {
        return concatenatedWords.getFirst();
    }

    public String getSecondLongestConcatenatedWord() {
        return concatenatedWords.get(1);
    }

    public int getTotalCountOfConcatenatenatedWords() {
        return concatenatedWords.size();
    }

    private static Map<String, Integer> sortByValueReversed(final Map<String, Integer> wordCounts) {
        return wordCounts.entrySet()
                .stream()
                .sorted((Map.Entry.<String, Integer>comparingByValue().reversed()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

    private static Boolean isWordConcatenated(String word, Map<String, Integer> map) {
        String result = "";
        int repeated = 0;

        for(int s = 0, e = 0; e <= word.length(); e++) {

            if(!map.containsKey(word.substring(s, e))) {
                continue;
            } else {
                result = result + word.substring(s, e);

                repeated++;
                s = e;
                e++;
            }

        }


        if(word.equals(result) && repeated > 1) {
            return true;
        } else {
            return false;
        }
    }

}
