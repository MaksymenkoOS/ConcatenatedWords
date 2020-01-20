package test.concwords;

import java.io.IOException;
import java.util.*;
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

    public List<String> getSecondLongestConcatenatedWord() {

        List<String> seconds = new ArrayList<>();

        for(String string : concatenatedWords) {
            if(string.length() == concatenatedWords.get(1).length()) {
                seconds.add(string);
            } else if(string.length() < concatenatedWords.get(1).length()) {
                return seconds;
            }
        }
        return seconds;
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

        LinkedHashMap<String, Integer> foundedWordsMap = new LinkedHashMap<>();

        for(int s = 0, e = 1; e < word.length(); e++) {

            String variant = word.substring(s, e);

            if (map.containsKey(variant)) {
                foundedWordsMap.put(variant, 1);
            }
        }

        while (foundedWordsMap.size() != 0) {

            for(Map.Entry variant : new LinkedHashMap<>(foundedWordsMap).entrySet()) {

                for(int s = variant.getKey().toString().length(), e = (variant.getKey().toString().length() + 1); e <= word.length(); e++) {

                    String subStr = word.substring(s, e);

                    if(map.containsKey(subStr)) {
                        String newKey = variant.getKey() + subStr;
                        Integer newValue = (Integer) variant.getValue() + 1;

                        foundedWordsMap.put(newKey, newValue);
                    }

                }

                if(variant.getKey().toString().equals(word) && (Integer) variant.getValue() > 1) {
                    return true;
                }

                foundedWordsMap.remove(variant.getKey());

            }

        }

        return false;

    }

}
