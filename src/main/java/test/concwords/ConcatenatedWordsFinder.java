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

//        LinkedList<String> history = new LinkedList<>();
//        history.add("---------");
//        history.add("WORD: " + word);
//        history.add("start variants:");

//        LinkedList<String> foundedWords = new LinkedList<>();
        LinkedHashMap<String, Integer> foundedWordsMap = new LinkedHashMap<>();

        for(int s = 0, e = 1; e < word.length(); e++) {

            String varian = word.substring(s, e);

            if (map.containsKey(varian)) {
//                foundedWords.add(varian);
                foundedWordsMap.put(varian, 1);
//                history.add("add to foundedWordsMap variant: " + varian + " = 1 repeat");
            }
        }

//        int itWhile = 0;

        while (foundedWordsMap.size() != 0) {

//            history.add("while cycle #" + itWhile);
//            history.add("foundedWordsMap ---");
//            history.add(foundedWordsMap.toString());

            for(Map.Entry variant : new LinkedHashMap<>(foundedWordsMap).entrySet()) {

                for(int s = variant.getKey().toString().length(), e = (variant.getKey().toString().length() + 1); e <= word.length(); e++) {

                    String subStr = word.substring(s, e);

                    if(map.containsKey(subStr)) {

                        String newKey = variant.getKey() + subStr;
                        Integer newValue = (Integer) variant.getValue() + 1;

                        foundedWordsMap.put(newKey, newValue);

//                        history.add("add new variant: " + variant + " + " + subStr);
//                        history.add("foundedWordsMap after adding---");
//                        history.add(foundedWordsMap.toString());
                    }

                }

                if(variant.getKey().toString().equals(word) && (Integer) variant.getValue() > 1) {

//                    history.add("variant (" + variant.getKey() + ") equals word (" + word + ")");
//                    history.add("and contains from " + variant.getValue() + " words");
//                    history.add("so word is CONCATENATED");
//                    history.add("and length is " + word.length());

//                    try {
//                        for(String s : history) {
//                            FileIO.writeLog(s);
//                        }
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }

                    return true;
                }

                foundedWordsMap.remove(variant.getKey());
//                history.add("remove from foundedWordsMap variant: " + variant.getKey());
//                history.add("foundedWordsMap after removing---");
//                history.add(foundedWordsMap.toString());

            }

//            itWhile++;

        }

//        history.add("word is NOT concatenated");

//        try {
//            for(String s : history) {
//                FileIO.writeLog(s);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        return false;

    }

    public Boolean checkWord(String word) {
        Map<String, Integer> words = downloadWordsFromFile();

        return isWordConcatenated(word, words);

    }

    public Boolean checkMethod(String word, Map<String, Integer> map) {
        return isWordConcatenated(word, map);
    }


}
