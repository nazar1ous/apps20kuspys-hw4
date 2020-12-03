package ua.edu.ucu.autocomplete;

import ua.edu.ucu.tries.Trie;
import ua.edu.ucu.tries.Tuple;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class PrefixMatches {

    private final Trie trie;

    public PrefixMatches(Trie trie) {
        this.trie = trie;
    }

    public int load(String... strings) {
        for (String str: strings){
            String[] words = str.split("\\s+");
            for (String word : words){
                if (word.length() >= 3){
                    trie.add(new Tuple(word, word.length()));
                }
            }
        }
        return trie.size();
    }

    public boolean contains(String word) {
        return trie.contains(word);
    }

    public boolean delete(String word) {
        return trie.delete(word);
    }

    public Iterable<String> wordsWithPrefix(String pref) {
        if (pref.length() < 2) {
            return Collections::emptyIterator;}
        return trie.wordsWithPrefix(pref);
    }

    public Iterable<String> wordsWithPrefix(String pref, int k) {
        if (pref.length() < 2 || k < 1) {
            return Collections::emptyIterator;}
        int len = k;
        if (k == 2) len = 3;
        Iterable<String> allWords = trie.wordsWithPrefix(pref);
        ArrayList<String> res = new ArrayList<>();
        HashSet<Integer> checker = new HashSet<>();
        for (String word : allWords){
            if (word.length() >= len &&
                    (checker.contains(word.length()) || checker.size() < k)){
                checker.add(word.length());
                res.add(word);
            }
        }
        return res;
    }

    public int size() {
        return trie.size();
    }
}
