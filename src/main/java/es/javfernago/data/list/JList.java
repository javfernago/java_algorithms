package es.javfernago.data.list;

public interface JList<T> {
	
	void add(T element);
	void add(int index, T element);
	void clear();
	boolean contains(T element);
	T get(int index);
	boolean isEmpty();
	T remove(int index);
	boolean remove (T element);
	void set(int index, T element);
	int size();
	JList<T> sublist(int fromIndex, int toIndex);
	T[] toArray(T[] array);
}
