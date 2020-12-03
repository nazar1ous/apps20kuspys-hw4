package ua.edu.ucu.collections.immutable;

import java.util.Arrays;

public class ImmutableArrayList implements ImmutableList {
    private final Object[] array;

    public ImmutableArrayList() {
        array = new Object[0];
    }

    public ImmutableArrayList(Object[] givenArray) {
        array = givenArray.clone();
    }

    public void validateIndex(int index) {
        if (index < 0 || index > array.length) {
            throw new IndexOutOfBoundsException("Index" +
                    " <i> should be in bound: 0 < <i> > length");
        }

    }

    @Override
    public ImmutableArrayList add(Object e) {
        return add(array.length, e);
    }

    @Override
    public ImmutableArrayList add(int index, Object e) {
        Object[] eArray = {e};
        return addAll(index, eArray);
    }

    @Override
    public ImmutableArrayList addAll(Object[] c) {
        return addAll(array.length, c);
    }

    @Override
    public ImmutableArrayList addAll(int index, Object[] c) {
        // As we have an immutable structure
        // There is no need in increasing length by 2
        validateIndex(index);
        Object[] newData = new Object[array.length + c.length];
        // Copy [0, index)
        System.arraycopy(array, 0, newData, 0, index);
        // Move to [index, ...), wiping needed elements
        System.arraycopy(c, 0, newData, index, c.length);
        // Restore wiping, we need to shift length - index
        // elements from it's new index
        System.arraycopy(array, index, newData,
                index + c.length, array.length - index);
        return new ImmutableArrayList(newData);
    }

    @Override
    public Object get(int index) {
        validateIndex(index);
        return array[index];
    }

    @Override
    public ImmutableArrayList remove(int index) {
        validateIndex(index);
        Object[] newData = new Object[array.length - 1];
        // Copy [0, index - 1]
        System.arraycopy(array, 0, newData, 0, index);
        int startIndex = index + 1;
        // If not so, we popped the last element
        if (startIndex < array.length) {
            System.arraycopy(array, index + 1, newData,
                    index, array.length - index - 1);
        }
        return new ImmutableArrayList(newData);
    }

    @Override
    public ImmutableArrayList set(int index, Object e) {
        validateIndex(index);
        Object[] newData = array.clone();
        newData[index] = e;
        return new ImmutableArrayList(newData);
    }

    @Override
    public int indexOf(Object e) {
        if (isEmpty()) {
            return -1;
        }
        for (int i = 0; i < array.length; ++i) {
            if (array[i] == e) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int size() {
        return array.length;
    }

    @Override
    public ImmutableArrayList clear() {
        return new ImmutableArrayList();
    }

    @Override
    public boolean isEmpty() {
        return array.length == 0;
    }

    @Override
    public Object[] toArray() {
        return array.clone();
    }

    @Override
    public String toString() {
        String newStr = Arrays.toString(toArray());
        return newStr.substring(1, newStr.length() - 1);
    }
}
