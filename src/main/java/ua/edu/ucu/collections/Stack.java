package ua.edu.ucu.collections;

import ua.edu.ucu.collections.immutable.ImmutableLinkedList;

public class Stack {
    private ImmutableLinkedList lst;

    public Stack() {
        lst = new ImmutableLinkedList();
    }

    public Object peek() {
        return lst.getFirst();
    }

    public Object pop() {
        Object o = lst.getFirst();
        lst = lst.removeFirst();
        return o;
    }

    public void push(Object o) {
        lst = lst.addFirst(o);
    }

}
