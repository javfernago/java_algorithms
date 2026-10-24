package es.javfernago.data.sequence.list;

import es.javfernago.data.Iterator;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ArrayListTest {

    @Test
    void newListIsEmpty() {
        ArrayList<String> list = new ArrayList<>();

        assertEquals(0, list.size());
        assertTrue(list.isEmpty());
    }

    @Test
    void addAppendsElementsAndPreservesTheirOrder() {
        ArrayList<String> list = new ArrayList<>();

        list.add("first");
        list.add("second");

        assertEquals(2, list.size());
        assertEquals("first", list.get(0));
        assertEquals("second", list.get(1));
        assertFalse(list.isEmpty());
    }

    @Test
    void addResizesAndPreservesElements() {
        ArrayList<Integer> list = new ArrayList<>();

        for (int i = 0; i < 25; i++) {
            list.add(i);
        }

        assertEquals(25, list.size());
        for (int i = 0; i < 25; i++) {
            assertEquals(i, list.get(i));
        }
    }

    @Test
    void addAtIndexInsertsAtBeginningMiddleAndEnd() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("c");

        list.add(0, "start");
        list.add(2, "middle");
        list.add(list.size(), "end");

        assertEquals(5, list.size());
        assertEquals("start", list.get(0));
        assertEquals("a", list.get(1));
        assertEquals("middle", list.get(2));
        assertEquals("c", list.get(3));
        assertEquals("end", list.get(4));
    }

    @Test
    void addAtIndexWorksOnEmptyList() {
        ArrayList<String> list = new ArrayList<>();

        list.add(0, "first");

        assertEquals(1, list.size());
        assertEquals("first", list.get(0));
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 2})
    void addAtIndexRejectsPositionsOutsideZeroToSize(int index) {
        ArrayList<String> list = new ArrayList<>();
        list.add("unchanged");

        assertThrows(IndexOutOfBoundsException.class, () -> list.add(index, "new"));

        assertEquals(1, list.size());
        assertEquals("unchanged", list.get(0));
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 2})
    void getRejectsInvalidIndexes(int index) {
        ArrayList<String> list = listWithTwoElements();

        assertThrows(IndexOutOfBoundsException.class, () -> list.get(index));
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 2})
    void setRejectsInvalidIndexesWithoutChangingList(int index) {
        ArrayList<String> list = listWithTwoElements();

        assertThrows(IndexOutOfBoundsException.class, () -> list.set(index, "replacement"));

        assertEquals("first", list.get(0));
        assertEquals("second", list.get(1));
        assertEquals(2, list.size());
    }

    @Test
    void setReplacesElementWithoutChangingSizeOrInvalidatingIterator() {
        ArrayList<String> list = listWithTwoElements();
        Iterator<String> iterator = list.iterator();

        list.set(0, "replacement");

        assertEquals(2, list.size());
        assertEquals("replacement", iterator.next());
        assertEquals("second", iterator.next());
    }

    @Test
    void removeAtIndexReturnsRemovedElementAndShiftsRemainingElements() {
        ArrayList<String> list = new ArrayList<>();
        list.add("first");
        list.add("middle");
        list.add("last");

        assertEquals("middle", list.remove(1));

        assertEquals(2, list.size());
        assertEquals("first", list.get(0));
        assertEquals("last", list.get(1));
    }

    @Test
    void removeAtFirstAndLastIndexesWorks() {
        ArrayList<String> list = new ArrayList<>();
        list.add("first");
        list.add("middle");
        list.add("last");

        assertEquals("first", list.remove(0));
        assertEquals("last", list.remove(list.size() - 1));

        assertEquals(1, list.size());
        assertEquals("middle", list.get(0));
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 2})
    void removeAtIndexRejectsInvalidIndexesWithoutChangingList(int index) {
        ArrayList<String> list = listWithTwoElements();

        assertThrows(IndexOutOfBoundsException.class, () -> list.remove(index));

        assertEquals(2, list.size());
        assertEquals("first", list.get(0));
        assertEquals("second", list.get(1));
    }

    @Test
    void removeByElementRemovesFirstMatchingOccurrence() {
        ArrayList<String> list = new ArrayList<>();
        list.add("duplicate");
        list.add("other");
        list.add("duplicate");

        assertTrue(list.remove("duplicate"));

        assertEquals(2, list.size());
        assertEquals("other", list.get(0));
        assertEquals("duplicate", list.get(1));
    }

    @Test
    void removeByElementReturnsFalseWhenElementIsAbsent() {
        ArrayList<String> list = listWithTwoElements();

        assertFalse(list.remove("missing"));

        assertEquals(2, list.size());
        assertEquals("first", list.get(0));
        assertEquals("second", list.get(1));
    }

    @Test
    void containsAndIndexOfFindFirstDuplicateAndReturnNotFoundResult() {
        ArrayList<String> list = new ArrayList<>();
        list.add("duplicate");
        list.add("other");
        list.add("duplicate");

        assertTrue(list.contains("duplicate"));
        assertEquals(0, list.indexOf("duplicate"));
        assertFalse(list.contains("missing"));
        assertEquals(-1, list.indexOf("missing"));
    }

    @Test
    void supportsNullElementsInSearchAndRemoval() {
        ArrayList<String> list = new ArrayList<>();
        list.add(null);
        list.add("value");
        list.add(null);

        assertTrue(list.contains(null));
        assertEquals(0, list.indexOf(null));
        assertTrue(list.remove(null));
        assertEquals(2, list.size());
        assertEquals("value", list.get(0));
        assertEquals(1, list.indexOf(null));
    }

    @Test
    void clearEmptiesListAndAllowsAddingAgain() {
        ArrayList<String> list = listWithTwoElements();

        list.clear();

        assertEquals(0, list.size());
        assertTrue(list.isEmpty());
        list.add("again");
        assertEquals("again", list.get(0));
    }

    @Test
    void clearOnEmptyListKeepsItEmpty() {
        ArrayList<String> list = new ArrayList<>();

        list.clear();

        assertEquals(0, list.size());
        assertTrue(list.isEmpty());
    }

    @Test
    void iteratorTraversesElementsInOrder() {
        ArrayList<String> list = listWithTwoElements();
        Iterator<String> iterator = list.iterator();

        assertTrue(iterator.hasNext());
        assertEquals("first", iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals("second", iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    void iteratorOnEmptyListHasNoNextElement() {
        Iterator<String> iterator = new ArrayList<String>().iterator();

        assertFalse(iterator.hasNext());
    }

    @Test
    void iteratorNextThrowsWhenNoElementsRemain() {
        Iterator<String> iterator = new ArrayList<String>().iterator();

        assertThrows(NoSuchElementException.class, iterator::next);
    }

    @Test
    void iteratorDetectsAdditionFromHasNextAndNext() {
        ArrayList<String> list = listWithTwoElements();
        Iterator<String> hasNextIterator = list.iterator();
        Iterator<String> nextIterator = list.iterator();
        list.add("third");

        assertThrows(ConcurrentModificationException.class, hasNextIterator::hasNext);
        assertThrows(ConcurrentModificationException.class, nextIterator::next);
    }

    @Test
    void iteratorDetectsRemoval() {
        ArrayList<String> list = listWithTwoElements();
        Iterator<String> iterator = list.iterator();
        list.remove(0);

        assertThrows(ConcurrentModificationException.class, iterator::hasNext);
    }

    @Test
    void iteratorDetectsClearOfNonEmptyList() {
        ArrayList<String> list = listWithTwoElements();
        Iterator<String> iterator = list.iterator();
        list.clear();

        assertThrows(ConcurrentModificationException.class, iterator::hasNext);
    }

    @Test
    void clearOnEmptyListDoesNotInvalidateIterator() {
        ArrayList<String> list = new ArrayList<>();
        Iterator<String> iterator = list.iterator();

        list.clear();

        assertFalse(iterator.hasNext());
    }

    private ArrayList<String> listWithTwoElements() {
        ArrayList<String> list = new ArrayList<>();
        list.add("first");
        list.add("second");
        return list;
    }
}
