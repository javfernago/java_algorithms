package es.javfernago.data.sequence;

import es.javfernago.data.Iterable;

public interface Sequence<E> extends Iterable<E> {

    int size();

    boolean isEmpty();

    E get(int index);

    void set(int index, E element);

    boolean contains(E element);

    int indexOf(E element);

    void clear();
}
