package ua.edu.ucu.collections.immutable;

import lombok.Getter;
import lombok.Setter;
import ua.edu.ucu.collections.nodes.Node;

import java.util.Arrays;

public class ImmutableLinkedList implements ImmutableList {
    @Getter
    @Setter
    private Node head;
    @Getter
    @Setter
    private Node tail;
    private int size;

    public ImmutableLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    public ImmutableLinkedList(Object[] givenArray) {
        this();
        if (givenArray.length == 0) {
            return;
        }

        head = new Node(givenArray[0]);
        Node curNode = head;
        for (int i = 1; i < givenArray.length; ++i) {
            Node newNode = new Node(givenArray[i]);
            curNode.setNext(newNode);
            newNode.setPrev(curNode);
            curNode = newNode;
        }
        tail = curNode;
        size = givenArray.length;
    }

    public void validateIndex(int index) {
        if (index < 0 || index > size()) {
            throw new IndexOutOfBoundsException("Index" +
                    " <i> should be in bound: 0 < <i> > length");
        }

    }

    @Override
    public ImmutableLinkedList add(Object e) {
        return add(size, e);
    }

    @Override
    public ImmutableLinkedList add(int index, Object e) {
        return addAll(index, new Object[]{e});
    }

    @Override
    public ImmutableLinkedList addAll(Object[] c) {
        return addAll(size, c);
    }

    @Override
    public ImmutableLinkedList addAll(int index, Object[] c) {
        validateIndex(index);
        if (c.length == 0) {
            return this;
        }
        ImmutableLinkedList copy = new ImmutableLinkedList(toArray());
        if (index == 0) {
            copy = copy.addFirst(c[0]);
            return copy.addAll(1,
                    Arrays.copyOfRange(c, 1, c.length));
        }
        if (index == size) {
            copy = copy.addLast(c[0]);
            return copy.addAll(size + 1,
                    Arrays.copyOfRange(c, 1, c.length));

        }
        Node stNode = getNode(copy.getHead(), index - 1);
        Node prevIndexNode = stNode.getNext();
        Node curNode = stNode;
        for (Object o : c) {
            Node newNode = new Node(o);
            curNode.setNext(newNode);
            newNode.setPrev(curNode);
            curNode = newNode;
        }
        // It means that we should update the tail
        if (prevIndexNode == null) {
            copy.setTail(curNode);
        } else {
            curNode.setNext(prevIndexNode);
            prevIndexNode.setPrev(curNode);
        }
        copy.size += c.length;
        return copy;
    }

    @Override
    public Object get(int index) {

        validateIndex(index);
        if (size == 0) {
            throw new IndexOutOfBoundsException();
        }
        Node indexNode = getNode(head, index);
        return indexNode.getValue();
    }

    public ImmutableLinkedList removeFirst() {
        if (isEmpty()) {
            throw new IndexOutOfBoundsException();
        }
        if (size == 1) {
            return new ImmutableLinkedList();
        } else {
            ImmutableLinkedList copy = new ImmutableLinkedList(toArray());
            Node next = copy.getHead().getNext();
            next.setPrev(null);
            copy.setHead(next);
            copy.size -= 1;
            return copy;
        }
    }

    public ImmutableLinkedList removeLast() {
        if (isEmpty()) {
            throw new IndexOutOfBoundsException();
        }
        ImmutableLinkedList copy = new ImmutableLinkedList(toArray());
        Node prevTail = copy.getTail();
        Node preTail = prevTail.getPrev();
        preTail.setNext(null);
        copy.setTail(preTail);
        copy.size -= 1;
        return copy;
    }

    @Override
    public ImmutableLinkedList remove(int index) {
        validateIndex(index);
        if (index == 0) {
            return removeFirst();
        }
        if (index == size - 1) {
            return removeLast();
        }
        ImmutableLinkedList copy = new ImmutableLinkedList(toArray());
        Node preNode = getNode(copy.getHead(), index - 1);
        Node afterNode = getNode(copy.getHead(), index + 1);
        preNode.setNext(afterNode);
        afterNode.setPrev(preNode);
        copy.size -= 1;
        return copy;
    }

    @Override
    public ImmutableLinkedList set(int index, Object e) {
        validateIndex(index);
        ImmutableLinkedList copy = new ImmutableLinkedList(toArray());
        getNode(copy.getHead(), index).setValue(e);
        return copy;
    }

    @Override
    public int indexOf(Object e) {
        if (isEmpty()) {
            return -1;
        }
        Node curNode = head;
        for (int i = 0; i < size; ++i) {
            if (curNode.getValue() == e) {
                return i;
            }
            curNode = curNode.getNext();
        }
        return -1;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public ImmutableLinkedList clear() {
        return new ImmutableLinkedList();
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Object[] toArray() {
        if (size == 0) {
            return new Object[0];
        }
        Object[] newData = new Object[size];
        Node curNode = head;
        for (int i = 0; i < size; ++i) {
            newData[i] = curNode.getValue();
            curNode = curNode.getNext();
        }
        return newData;
    }

    public static Node getNode(Node e, int movementsNumber) {
        Node curNode = e;
        for (int i = 0; i < movementsNumber; ++i) {
            curNode = curNode.getNext();
        }
        return curNode;
    }

    @Override
    public String toString() {
        String newStr = Arrays.toString(toArray());
        return newStr.substring(1, newStr.length() - 1);
    }

    public ImmutableLinkedList addFirst(Object e) {
        ImmutableLinkedList copy = new ImmutableLinkedList(toArray());
        if (isEmpty()) {
            copy.setHead(new Node(e));
            copy.setTail(copy.getHead());
        } else {
            Node prevHead = copy.getHead();
            copy.setHead(new Node(e));
            copy.getHead().setNext(prevHead);
            prevHead.setPrev(copy.getHead());
        }
        copy.size++;
        return copy;
    }

    public ImmutableLinkedList addLast(Object e) {
        ImmutableLinkedList copy = new ImmutableLinkedList(toArray());
        if (isEmpty()) {
            copy.setHead(new Node(e));
            copy.setTail(copy.getHead());
        } else {
            Node preLast = copy.getTail();
            copy.setTail(new Node(e));
            preLast.setNext(copy.getTail());
            copy.getTail().setPrev(preLast);
        }
        copy.size++;
        return copy;
    }

    public Object getFirst() {
        return get(0);
    }

    public Object getLast() {
        return get(size() - 1);
    }


}
