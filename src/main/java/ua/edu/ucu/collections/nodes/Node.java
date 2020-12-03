package ua.edu.ucu.collections.nodes;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Node {
    private Object value;
    private Node prev;
    private Node next;

    public Node() {
        prev = null;
        next = null;
    }

    public Node(Object value) {
        this();
        this.value = value;
    }
}
