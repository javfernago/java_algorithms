package es.javfernago.data.sequence.list;

public interface List<E> extends es.javfernago.data.sequence.Sequence<E> {

    void add(E element);

    void add(int index, E element);

    E remove(int index);

    boolean remove(E element);
}
