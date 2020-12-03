package ua.edu.ucu.collections;

import ua.edu.ucu.collections.immutable.ImmutableLinkedList;

public class Queue {
    private ImmutableLinkedList lst;

    public Queue() {
        lst = new ImmutableLinkedList();
    }

    public Object peek() {
        return lst.getFirst();
    }

    public Object dequeue() {
        Object o = lst.getFirst();
        lst = lst.removeFirst();
        return o;
    }

    public void enqueue(Object o) {
        lst = lst.addLast(o);
    }

    public boolean isEmpty(){
        return lst.size() > 0;
    }


}
