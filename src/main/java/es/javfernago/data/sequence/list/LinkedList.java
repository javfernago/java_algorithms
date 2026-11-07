package es.javfernago.data.sequence.list;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
import java.util.Objects;

import es.javfernago.data.Iterator;

public class LinkedList<E> implements List<E> {

	private LinkedListNode head;
	private LinkedListNode last;
	private int size;
	private int changes;

	public LinkedList() {
		head = null;
		last = null;
		size = 0;
		changes = 0;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public E get(int index) {
		checkIndex(index);
		LinkedListNode node = head;
		for (int i = 0; i < index; i++) {
			node = node.getNext();
		}
		return node.getData();
	}

	@Override
	public void set(int index, E element) {
		checkIndex(index);
		LinkedListNode node = head;
		for (int i = 0; i < index; i++) {
			node = node.getNext();
		}
		node.setData(element);
	}

	@Override
	public boolean contains(E element) {
		return indexOf(element) != -1;
	}

	@Override
	public int indexOf(E element) {
		LinkedListNode node = head;
		int index = 0;

		while (node != null) {
			if (Objects.equals(element, node.getData())) {
				return index;
			}
			node = node.getNext();
			index++;
		}

		return -1;
	}

	@Override
	public void clear() {
		LinkedListNode node = head;
		LinkedListNode tmp = null;
		
		if (head == null) {
			return;
		}
		
		while (node.getNext() != null) {
			tmp = node;
			node = node.getNext();
			tmp.setNext(null);
			tmp.setData(null);
		}
		head = null;
		last = null;
		size = 0;
		changes++;
	}

	@Override
	public Iterator<E> iterator() {
		return new LinkedListIterator();
	}

	@Override
	public void add(E element) {
		LinkedListNode node = new LinkedListNode(element);

		if (head == null) {
			head = node;
		} else {
			last.setNext(node);
		}
		last = node;
		size ++;
		changes++;
	}

	@Override
	public void add(int index, E element) {
		checkAddIndex(index);
		if (index == size) {
			add(element);
		} else {
			LinkedListNode node = head;
			LinkedListNode previousNode = null;
			int temporalIndex = 0;
			while (temporalIndex < index) {
				previousNode = node;
				node = node.getNext();
				temporalIndex++;
			}

			LinkedListNode newNode = new LinkedListNode(element);
			if (previousNode != null) {
				previousNode.setNext(newNode);
			} else {
				head = newNode;
			}
			newNode.setNext(node);
			size++;
			changes++;
		}
	}

	@Override
	public E remove(int index) {
		checkIndex(index);

		// Inicialización
		LinkedListNode node = head;
		LinkedListNode previousNode = null;
		int temporalIndex = 0;
		while (temporalIndex < index) {
			previousNode = node;
			node = node.getNext();
			temporalIndex++;
		}

		return remove(previousNode, node);
	}

	@Override
	public boolean remove(E element) {
		// Inicialización
		LinkedListNode node = head;
		LinkedListNode previousNode = null;

		while ((node != null) && !Objects.equals(element, node.getData())) {
			previousNode = node;
			node = node.getNext();
		}

		if (node == null) {
			return false;
		}
		
		remove(previousNode, node);
		return true;
	}

	private E remove(LinkedListNode previousNode, LinkedListNode node) {
		if (previousNode != null) {
			previousNode.setNext(node.getNext());
		} else {
			head = node.getNext();
		}
		
		if (node == last) {
			last = previousNode;
		}
		
		E data = node.getData();
		node.setData(null);
		node.setNext(null);
		size--;
		changes++;
		return data;

	}

	private void checkIndex(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
		}
	}

	private void checkAddIndex(int index) {
		if (index < 0 || index > size) {
			throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
		}
	}

	private class LinkedListNode {
		private E data;
		private LinkedListNode next;
        
		public LinkedListNode(E data) {
			this.data = data;
		}

		public E getData() {
			return data;
		}

		public void setData(E data) {
			this.data = data;
		}

		public LinkedListNode getNext() {
			return next;
		}

		public void setNext(LinkedListNode next) {
			this.next = next;
		}
	}

	private class LinkedListIterator implements Iterator<E> {
		LinkedListNode node;
        private final int expectedChanges = changes;
		
		public LinkedListIterator() {
			node = head;
		}
		
		@Override
		public boolean hasNext() {
			checkForChanges();
			return node != null;
		}

		@Override
		public E next() {
			checkForChanges();
			if (!hasNext()) {
				throw new NoSuchElementException("No hay más elementos.");
			}
			E data = node.getData();
			node = node.getNext();
			return data;
		}
		
		private void checkForChanges() {
			if (expectedChanges != changes) {
                throw new ConcurrentModificationException();
			}
		}

	}

}
