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

        //***
        try {
            FileIO.clearConc();
            FileIO.clearNonConc();
            FileIO.clearLog();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int concRow = 1;
        int nonConcRow = 1;

        //***

        assert words != null;
        for(Map.Entry entry : words.entrySet()) {

            word = entry.getKey().toString();

            if(isWordConcatenated(word, words)) {
                concatenatedWords.add(word);

                //***
                concRow++;

                try {
                    FileIO.writeConc(concRow + " - " + word + " - (" + word.length() + ")");
                } catch (IOException e) {
                    System.out.println("problem to write conc");
                }
                //***
            } else {

                //***
                try {
                    FileIO.writeNonConc(nonConcRow + " - " + word + " - (" + word.length() + ")");
                    nonConcRow++;
                } catch (IOException e) {
                    System.out.println("problem to write nonConc");
                }
                //***
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

        LinkedList<String> history = new LinkedList<>();
        history.add("---------");
        history.add("WORD: " + word);
        history.add("start variants:");

        LinkedList<String> foundedWords = new LinkedList<>();

        for(int s = 0, e = 1; e < word.length(); e++) {

            String varian = word.substring(s, e);

            if (map.containsKey(varian)) {
                foundedWords.add(varian);
                history.add("add to foundedWords variant: " + varian);
            }
        }

        int iterations = 0;
        int itWhile = 0;

        while (foundedWords.size() != 0) {

            history.add("while cycle #" + itWhile);
            history.add("foundedWords list ---");
            history.add(foundedWords.toString());
            history.add("foundedWords list ---");

            for(String variant : new LinkedList<>(foundedWords)) {

                for(int s = variant.length(), e = (variant.length() + 1); e <= word.length(); e++) {

                    String subStr = word.substring(s, e);

                    if(map.containsKey(subStr)) {
                        foundedWords.add(variant + subStr);
                        iterations++;

                        history.add("add new variant: " + variant + " + " + subStr);
                        history.add("foundedWords after adding---");
                        history.add(foundedWords.toString());
                    }

                }

                if(variant.equals(word) && iterations > 1) {

                    history.add("variant (" + variant + ") equals word (" + word + ")");
                    history.add("and iterations = " + iterations);
                    history.add("so word is CONCATENATED");
                    history.add("and length is " + word.length());

                    try {
                        for(String s : history) {
                            FileIO.writeLog(s);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    return true;
                }

                foundedWords.remove(variant);
                history.add("remove from foundedWords variant: " + variant);
                history.add("foundedWords after removing---");
                history.add(foundedWords.toString());

            }

            itWhile++;

        }

        history.add("word is NOT concatenated");

        try {
            for(String s : history) {
                FileIO.writeLog(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;

    }

    public Boolean checkWord(String word) {
        Map<String, Integer> words = downloadWordsFromFile();

        return isWordConcatenated(word, words);

    }


}
