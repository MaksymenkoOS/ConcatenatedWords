package test.concwords;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Collectors;

public class ConcatenatedWordsFinder {

    private LinkedList<String> concatenatedWords = new LinkedList<>();

    public void findConcatenatedWords() {

        Map<String, Integer> words = downloadWordsFromFile();

        String word;

        assert words != null;
        for(Map.Entry entry : words.entrySet()) {
            word = entry.getKey().toString();
            if(isWordConcatenated(word, words)) {
                concatenatedWords.add(word);
            }
        }

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

    private Map<String, Integer> downloadWordsFromFile() {

        LinkedList<String> wordsFromFile;
        Map<String, Integer> unsortedMap = new HashMap<>();

        try {
            wordsFromFile = FileWorker.read();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        for (String string : wordsFromFile) {
            unsortedMap.put(string, string.length());
        }

        return sortByValueReversed(unsortedMap);
    }

    private Map<String, Integer> sortByValueReversed(Map<String, Integer> wordCounts) {
        return wordCounts.entrySet()
                .stream()
                .sorted((Map.Entry.<String, Integer>comparingByValue().reversed()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

    private Boolean isWordConcatenated(String word, Map<String, Integer> map) {

        StringBuilder result = new StringBuilder();
        int repeated = 0;

        for(int s = 0, e = 0; e <= word.length(); e++) {

            if(map.containsKey(word.substring(s, e))) {
                result.append(word, s, e);

                repeated++;
                s = e;
                e++;
            }

        }

        return word.equals(result.toString()) && repeated > 1;

    }

}