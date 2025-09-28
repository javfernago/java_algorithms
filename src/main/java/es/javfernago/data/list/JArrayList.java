package es.javfernago.data.list;

import java.security.InvalidParameterException;
import java.util.Arrays;

public class JArrayList<T> implements JList<T> {

	private Object[] data;
	private int size;
	private int blockSize;

	public static final int DEFAULT_SIZE = 10;

	// constructors

	public JArrayList() {
		this(DEFAULT_SIZE);
	}

	public JArrayList(int initialSpace) {
		blockSize = initialSpace;
		data = new Object[initialSpace];
		size = 0;
	}
	
	private JArrayList(T[] array) {
		this.data = array;
		size = array.length;
		blockSize = DEFAULT_SIZE;
	}

	// public methods

	/**
	 * Adds an element to the Array List
	 * 
	 * @param element Element to add to the list
	 */
	@Override
	public void add(T element) {
		if (size == data.length) {
			extend(blockSize);
		}
		data[size] = element;
		size++;
	}

	/**
	 * Adds an element in the position specified by the parameter. Moves the
	 * elements to the end of the list
	 * 
	 * @param index   Position where the element is inserted
	 * @param element Element to insert in the list
	 * @throws IndexOutOfBoundsException if the index is smaller that 0 or bigger or
	 *                                   equal that the size of the array
	 */
	@Override
	public void add(int index, T element) {
		validateAdd(index);
		add(element);
		System.arraycopy(data, index, data, index + 1, size - index - 1);
		data[index] = element;
	}

	/**
	 * Returns the element stored in the position specified as parameter
	 * 
	 * @param index Position of the element
	 * @return Element stored in the index position
	 * @throws IndexOutOfBoundsException if the index is smaller that 0 or bigger or
	 *                                   equal than the size of the array
	 */
	@SuppressWarnings("unchecked")
	@Override
	public T get(int index) {
		validate(index);
		return (T) data[index];
	}

	/**
	 * Change the element in position index
	 * 
	 * @param index   Position of the element to change
	 * @param element New element to set
	 * @throws IndexOutOfBoundsException if the index is smaller that 0 or bigger or
	 *                                   equal than the size of the array
	 */
	@Override
	public void set(int index, T element) {
		validate(index);
		data[index] = element;
	}

	/**
	 * Remove the element in the position index
	 * 
	 * @param index Position of the element to remove
	 */
	@SuppressWarnings("unchecked")
	@Override
	public T remove(int index) {
		validate(index);
		T value = (T) data[index];
		System.arraycopy(data, index + 1, data, index, size - index - 1);
		size--;
		return value;
	}

	/**
	 * Remove the element passed as parameter
	 * 
	 * @param element Element to remove
	 * @return True if the element exists in the array list and it´s removed, false
	 *         otherwise
	 */
	@Override
	public boolean remove(T element) {
		int index = indexOf(element);
		if (index == -1) {
			return false;
		}
		remove(index);
		return true;

	}

	/**
	 * Returns the current size of the JArrayList
	 * 
	 * @return The current size of the JArrayList
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * Checks if the JArrayList is empty
	 * 
	 * @return true is the JArrayList is empty, false otherwise
	 */
	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * Clear the JArrayList.
	 */
	@Override
	public void clear() {
		data = new Object[blockSize];
		size = 0;
	}

	/**
	 * Check if the element is in the JArrayList
	 * 
	 * @param element Element to check
	 * @return true if the element is in the JArrayList, false otherwise
	 */
	@Override
	public boolean contains(T element) {
		for (Object o : data) {
			if (element != null && element.equals(o)) {
				return true;
			} else if (o == null) {
				return true;
			}
			
		}
		return false;
	}

	/**
	 * Returns the index of the element in the JArrayList
	 * 
	 * @param element Element to look for
	 * @return Index of the element if it´s in the JArrayList, -1 otherwise
	 */
	public int indexOf(T element) {
		for (int i = 0; i < size; i++) {
			if (element != null && element.equals(data[i])) {
				return i;
			} else if (data[i] == null) {
				return i;
			}
		}
		return -1;

	}

	@Override
	@SuppressWarnings("unchecked")
	public JList<T> sublist(int fromIndex, int toIndex) {
		validate(fromIndex);
		validate(toIndex);
		if (fromIndex > toIndex) {
			throw new InvalidParameterException("The parameter fromIndex must be smaller than the toIndex");
		}

		Object[] sub = Arrays.copyOfRange(data, fromIndex, toIndex);
		// Convertimos el Object[] a T[] de forma segura
		T[] subArray = (T[]) sub;

		return new JArrayList<T>(subArray);
	}

	@Override
	@SuppressWarnings("unchecked")
	public T[] toArray(T[] array) {
	    if (array.length < size) {
	        return (T[]) Arrays.copyOf(data, size, array.getClass());
	    }
	    System.arraycopy(data, 0, array, 0, size);
	    if (array.length > size) {
	        array[size] = null; 
	    }
	    return array;
	}

	// private methods
	private void extend(int size) {
		Object[] newData = new Object[data.length + size];
		System.arraycopy(data, 0, newData, 0, data.length);
		data = newData;
	}

	private void validate(int index) {
		if ((index < 0) || (index > size - 1)) {
			throw new ArrayIndexOutOfBoundsException("Index out of bound exception. Array size: " + data.length);
		}
	}

	private void validateAdd(int index) {
		if ((index < 0) || (index > size )) {
			throw new ArrayIndexOutOfBoundsException("Index out of bound exception. Array size: " + data.length);
		}
	}
}
