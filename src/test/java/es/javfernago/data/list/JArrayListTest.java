package es.javfernago.data.list;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.security.InvalidParameterException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JArrayListTest {

	private JList<String> list;

	@BeforeEach
	void setUp() {
		list = new JArrayList<>();
	}

	// --- add(T element) ---
	@Test
	void testAddElementToEmptyList() {
		list.add("One");

		// Asserts
		assertEquals(1, list.size());
		assertEquals("One", list.get(0));
		assertThrows(ArrayIndexOutOfBoundsException.class, () -> list.get(1));
	}

	@Test
	void testAddMultipleElements() {
		fillList(100);

		// Asserts
		assertEquals(100, list.size());
		assertEquals("String 0", list.get(0));
		assertEquals("String 99", list.get(99));
		assertThrows(ArrayIndexOutOfBoundsException.class, () -> list.get(100));

	}

	@Test
	void testAddNullElement() {
		list.add(null);

		// Asserts
		assertEquals(1, list.size());
		assertNull(list.get(0));
		assertThrows(ArrayIndexOutOfBoundsException.class, () -> list.get(1));

	}

	@Test
	void testAddBeyondInitialCapacity() {
		fillList(10000);

		// Asserts
		assertEquals(10000, list.size());
		assertEquals("String 0", list.get(0));
		assertEquals("String 9999", list.get(9999));
		assertThrows(ArrayIndexOutOfBoundsException.class, () -> list.get(10000));
	}

	// --- add(int index, T element) ---
	@Test
	void testAddAtIndexZero() {
		fillList(10);

		list.add(0, "New string");

		// Asserts
		assertEquals(11, list.size());
		assertEquals("New string", list.get(0));
		assertEquals("String 0", list.get(1));
		assertEquals("String 9", list.get(10));

	}

	@Test
	void testAddAtMiddleIndex() {
		fillList(10);

		list.add(5, "New string");

		// Asserts
		assertEquals(11, list.size());
		assertEquals("New string", list.get(5));
		assertEquals("String 4", list.get(4));
		assertEquals("String 5", list.get(6));
	}

	@Test
	void testAddAtEndIndex() {
		fillList(10);

		list.add(9, "New string");

		// Asserts
		assertEquals(11, list.size());
		assertEquals("New string", list.get(9));
		assertEquals("String 9", list.get(10));
	}

	@Test
	void testAddAtNextIndex() {
		fillList(10);

		list.add(10, "New string");

		// Asserts
		assertEquals(11, list.size());
		assertEquals("New string", list.get(10));
		assertEquals("String 9", list.get(9));
	}

	@Test
	void testAddAtInvalidIndex() {
		// Asserts
		assertThrows(ArrayIndexOutOfBoundsException.class, () -> list.add(100, "New String"));
	}

	// --- clear() ---
	@Test
	void testClearNonEmptyList() {
		fillList(100);
		assertEquals(100, list.size());
		list.clear();
		// Asserts
		assertEquals(0, list.size());
	}

	@Test
	void testClearEmptyList() {
		list.clear();
		// Asserts
		assertEquals(0, list.size());
	}

	// --- contains(T element) ---
	@Test
	void testContainsExistingElement() {
		fillList(10);
		// Asserts
		assertTrue(list.contains("String 0"));
		assertTrue(list.contains("String 1"));
	}

	@Test
	void testContainsNonExistingElement() {
		fillList(20);
		// Asserts
		assertFalse(list.contains("String X"));
		assertFalse(list.contains("String"));
	}

	@Test
	void testContainsNullElement() {
		fillList(20);
		list.add(null);
		// Asserts
		assertTrue(list.contains(null));
	}

	// --- get(int index) ---
	@Test
	void testGetValidIndex() {
		fillList(20);

		String element = list.get(0);
		assertEquals("String 0", element);
		element = list.get(10);
		assertEquals("String 10", element);
	}

	@Test
	void testGetInvalidIndex() {
		fillList(20);
		// Asserts
		assertThrows(ArrayIndexOutOfBoundsException.class, () -> list.get(100));
	}

	// --- isEmpty() ---
	@Test
	void testIsEmptyOnNewList() {
		assertTrue(list.isEmpty());
	}

	@Test
	void testIsEmptyAfterAdd() {
		fillList(10);
		assertFalse(list.isEmpty());
	}

	@Test
	void testIsEmptyAfterClear() {
		fillList(10);
		assertFalse(list.isEmpty());
		list.clear();
		assertTrue(list.isEmpty());
	}

	// --- remove(int index) ---
	@Test
	void testRemoveAtValidIndex() {
		fillList(10);
		list.remove(5);

		// Asserts
		assertEquals(9, list.size());
		assertEquals("String 6", list.get(5));
	}

	@Test
	void testRemoveFirstElement() {
		fillList(10);
		list.remove(0);

		// Asserts
		assertEquals(9, list.size());
		assertEquals("String 1", list.get(0));
	}

	@Test
	void testRemoveLastElement() {
		fillList(10);
		list.remove(9);

		// Asserts
		assertEquals(9, list.size());
		assertEquals("String 8", list.get(8));
	}

	@Test
	void testRemoveInvalidIndex() {
		fillList(20);
		// Asserts
		assertThrows(ArrayIndexOutOfBoundsException.class, () -> list.remove(100));
	}

	// --- remove(T element) ---
	@Test
	void testRemoveExistingElement() {
		fillList(20);
		boolean result = list.remove("String 10");
		// Asserts
		assertTrue(result);
		assertEquals(19, list.size());
		assertEquals("String 11", list.get(10));
	}

	@Test
	void testRemoveNonExistingElement() {
		fillList(20);
		boolean result = list.remove("String 200");
		// Asserts
		assertFalse(result);
		assertEquals(20, list.size());
	}

	@Test
	void testRemoveNullElement() {
		list.add("String 0");
		list.add(null);
		list.add("String 1");
		boolean result = list.remove(null);
		// Asserts
		assertTrue(result);
		assertEquals(2, list.size());
		assertEquals("String 0", list.get(0));
		assertEquals("String 1", list.get(1));
	}

	@Test
	void testRemoveUntilEmpty() {
		fillList(10);
		for (int i = 0; i < 10; i++) {
			boolean result = list.remove("String " + i);
			assertTrue(result);
		}
		// Asserts
		assertTrue(list.isEmpty());

	}

	// --- set(int index, T element) ---
	@Test
	void testSetValidIndex() {
		fillList(10);
		list.set(5, "New String 5");

		// Asserts
		assertEquals(10, list.size());
		assertEquals("New String 5", list.get(5));
	}

	@Test
	void testSetInvalidIndex() {
		fillList(10);
		assertThrows(ArrayIndexOutOfBoundsException.class, () -> list.set(10, "New String"));
	}

	@Test
	void testSetWithNull() {
		fillList(10);
		list.set(5, null);

		// Asserts
		assertEquals(10, list.size());
		assertNull(list.get(5));
	}

	// --- size() ---
	@Test
	void testSizeOnNewList() {
		assertEquals(0, list.size());
	}

	@Test
	void testSizeAfterAddAndRemove() {
		fillList(10);
		assertEquals(10, list.size());
		list.add("New String");
		list.remove("String 3");
		list.remove("String 5");
		assertEquals(9, list.size());
	}

	// --- sublist(int fromIndex, int toIndex) ---
	@Test
	void testSublistValidRange() {
		fillList(10);
		JList<String> sublist = list.sublist(3, 6);

		// Asserts
		assertEquals(3, sublist.size());
		assertEquals("String 3", sublist.get(0));
		assertEquals("String 4", sublist.get(1));
		assertEquals("String 5", sublist.get(2));

		sublist.add("New String");
		// Asserts
		assertEquals(4, sublist.size());
		assertEquals("New String", sublist.get(3));

	}

	@Test
	void testSublistEmptyRange() {
		fillList(10);
		JList<String> sublist = list.sublist(3, 3);

		// Asserts
		assertEquals(0, sublist.size());
		assertTrue(sublist.isEmpty());

		sublist.add("New String");
		assertEquals(1, sublist.size());
		assertFalse(sublist.isEmpty());
		assertEquals("New String", sublist.get(0));

	}

	@Test
	void testSublistInvalidRange() {
		fillList(10);
		assertThrows(InvalidParameterException.class, () -> list.sublist(6, 3));
		assertThrows(ArrayIndexOutOfBoundsException.class, () -> list.sublist(-1, 3));
		assertThrows(ArrayIndexOutOfBoundsException.class, () -> list.sublist(1, 30));
	}

	// --- toArray() ---
	@Test
	void testToArrayEmptyList() {
		String[] array = new String[0];
		array = list.toArray(array);

		// Asserts
		assertEquals(0, array.length);

	}

	@Test
	void testToArrayWithElements() {
		fillList(10);
		String[] array = new String[0];
		array = list.toArray(array);

		// Asserts
		assertEquals(10, array.length);
		assertEquals("String 0", array[0]);
		assertEquals("String 9", array[9]);
	}

	// Util methods
	private void fillList(int size) {
		for (int i = 0; i < size; i++) {
			list.add("String " + i);
		}

	}

}
