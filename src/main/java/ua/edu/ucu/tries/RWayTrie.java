package ua.edu.ucu.tries;
import ua.edu.ucu.collections.Queue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;


public class RWayTrie implements Trie {

    private static final int R = 26;
    private Node root = new Node();

    private static class Node {
        private Object val;
        private final Node[] next = new Node[R];
    }

    private int size = 0;

    private static int pos(char c){ return c-'a';}

    private Node get(Node start, String key, int d){
        if (start == null){
            return null;
        }
        if (d == key.length()){
            return start;
        }
        char c = key.charAt(d);
        return get(start.next[pos(c)], key, d+1);
    }


    private static Node _add(Node start, Tuple t, int d){
        if (start == null){
            start = new Node();
        }
        // as we use it with start = root, we have a shift = 1
        if (t.weight == d){
            start.val = t.weight;
            return start;
        }

        int next = pos(t.term.charAt(d));
        start.next[next] =
                _add(start.next[next],
                        t, d + 1);
        return start;

    }

    @Override
    public void add(Tuple t) {
        if (t == null){
            throw new IllegalArgumentException("The argument is empty");
        }
        root = _add(root, t, 0);
        size++;
    }


    @Override
    public boolean contains(String word) {
        return get(root, word, 0) != null;
    }

    @Override
    public boolean delete(String word) {
        if (contains(word)){
            root = delete(root, word, 0);
            return true;
        }
        return false;
    }

    private Node delete(Node start, String key, int d){
        if (start == null) return null;
        if (d == key.length()){
            start.val = null;
        }else{
            char c = key.charAt(d);
            start.next[c-'a'] = delete(start.next[c-'a'], key,
                    d+1);
        }
        if (start.val != null){
            return start;
        }

        for (int i = 0; i < R; ++i){
            if (start.next[i] != null){
                return start;
            }
        }
        return null;
    }

    @Override
    public Iterable<String> words() {
        return wordsWithPrefix("");
    }

    @Override
    public Iterable<String> wordsWithPrefix(String s) {
        Queue q = new Queue();
        collect(get(root, s, 0), s, q);
        ArrayList<String> res = new ArrayList<>();
        String word;
        while (!q.isEmpty()){
            word = (String)q.dequeue();
            res.add(word);
        }
        return res;
    }

    private void collect(Node start, String s,
                         Queue q){
        if (start == null) return;
        if (start.val != null){
            q.enqueue(s);
        }
        for (int i = 0; i < R; ++i){
            collect(start.next[i], s+ (char)(i + 97), q);
        }
    }

    @Override
    public int size() {
        return size;
    }

}
