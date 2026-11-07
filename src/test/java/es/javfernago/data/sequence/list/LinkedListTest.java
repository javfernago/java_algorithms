package es.javfernago.data.sequence.list;

import es.javfernago.data.Iterator;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LinkedListTest {

    @Test
    void newListIsEmpty() {
        LinkedList<String> list = new LinkedList<>();

        assertEquals(0, list.size());
        assertTrue(list.isEmpty());
    }

    @Test
    void addAppendsElementsAndPreservesTheirOrder() {
        LinkedList<String> list = new LinkedList<>();

        list.add("first");
        list.add("second");

        assertEquals(2, list.size());
        assertEquals("first", list.get(0));
        assertEquals("second", list.get(1));
        assertFalse(list.isEmpty());
    }

    @Test
    void addAtIndexInsertsAtBeginningMiddleAndEnd() {
        LinkedList<String> list = new LinkedList<>();
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
        LinkedList<String> list = new LinkedList<>();

        list.add(0, "first");

        assertEquals(1, list.size());
        assertEquals("first", list.get(0));
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 2})
    void addAtIndexRejectsPositionsOutsideZeroToSize(int index) {
        LinkedList<String> list = new LinkedList<>();
        list.add("unchanged");

        assertThrows(IndexOutOfBoundsException.class, () -> list.add(index, "new"));

        assertEquals(1, list.size());
        assertEquals("unchanged", list.get(0));
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 2})
    void getRejectsInvalidIndexes(int index) {
        LinkedList<String> list = listWithTwoElements();

        assertThrows(IndexOutOfBoundsException.class, () -> list.get(index));
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 2})
    void setRejectsInvalidIndexesWithoutChangingList(int index) {
        LinkedList<String> list = listWithTwoElements();

        assertThrows(IndexOutOfBoundsException.class, () -> list.set(index, "replacement"));

        assertEquals("first", list.get(0));
        assertEquals("second", list.get(1));
        assertEquals(2, list.size());
    }

    @Test
    void setReplacesElementWithoutChangingSizeOrInvalidatingIterator() {
        LinkedList<String> list = listWithTwoElements();
        Iterator<String> iterator = list.iterator();

        list.set(0, "replacement");

        assertEquals(2, list.size());
        assertEquals("replacement", iterator.next());
        assertEquals("second", iterator.next());
    }

    @Test
    void setReplacesElementAtIndexGreaterThanOne() {
        LinkedList<String> list = new LinkedList<>();
        list.add("first");
        list.add("second");
        list.add("third");
        list.add("fourth");

        list.set(2, "replacement");

        assertEquals("first", list.get(0));
        assertEquals("second", list.get(1));
        assertEquals("replacement", list.get(2));
        assertEquals("fourth", list.get(3));
        assertEquals(4, list.size());
    }

    @Test
    void setReplacesLastElementWithNull() {
        LinkedList<String> list = listWithTwoElements();

        list.set(1, null);

        assertEquals("first", list.get(0));
        assertNull(list.get(1));
        assertEquals(2, list.size());
    }

    @Test
    void removeAtIndexReturnsRemovedElementAndShiftsRemainingElements() {
        LinkedList<String> list = new LinkedList<>();
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
        LinkedList<String> list = new LinkedList<>();
        list.add("first");
        list.add("middle");
        list.add("last");

        assertEquals("first", list.remove(0));
        assertEquals("last", list.remove(list.size() - 1));

        assertEquals(1, list.size());
        assertEquals("middle", list.get(0));
    }

    @Test
    void removeLastElementAllowsAppendingAnotherElement() {
        LinkedList<String> list = new LinkedList<>();
        list.add("first");
        list.add("last");

        assertEquals("last", list.remove(1));
        list.add("new last");

        assertEquals(2, list.size());
        assertEquals("first", list.get(0));
        assertEquals("new last", list.get(1));
    }

    @Test
    void removeOnlyElementAllowsAppendingAnotherElement() {
        LinkedList<String> list = new LinkedList<>();
        list.add("only");

        assertEquals("only", list.remove(0));
        list.add("new only");

        assertEquals(1, list.size());
        assertEquals("new only", list.get(0));
    }

    @Test
    void removingAllElementsLeavesListEmptyAndReusable() {
        LinkedList<String> list = listWithTwoElements();

        assertEquals("first", list.remove(0));
        assertEquals("second", list.remove(0));

        assertTrue(list.isEmpty());
        assertEquals(0, list.size());

        list.add("again");

        assertEquals(1, list.size());
        assertEquals("again", list.get(0));
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 2})
    void removeAtIndexRejectsInvalidIndexesWithoutChangingList(int index) {
        LinkedList<String> list = listWithTwoElements();

        assertThrows(IndexOutOfBoundsException.class, () -> list.remove(index));

        assertEquals(2, list.size());
        assertEquals("first", list.get(0));
        assertEquals("second", list.get(1));
    }

    @Test
    void removeByElementRemovesFirstMatchingOccurrence() {
        LinkedList<String> list = new LinkedList<>();
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
        LinkedList<String> list = listWithTwoElements();

        assertFalse(list.remove("missing"));

        assertEquals(2, list.size());
        assertEquals("first", list.get(0));
        assertEquals("second", list.get(1));
    }

    @Test
    void removeByElementRemovesLastMatchAndAllowsAppending() {
        LinkedList<String> list = listWithTwoElements();

        assertTrue(list.remove("second"));
        list.add("new last");

        assertEquals(2, list.size());
        assertEquals("first", list.get(0));
        assertEquals("new last", list.get(1));
    }

    @Test
    void removeByElementUsesEqualityRatherThanIdentity() {
        LinkedList<String> list = new LinkedList<>();
        list.add(new String("value"));

        assertTrue(list.remove(new String("value")));
        assertTrue(list.isEmpty());
    }

    @Test
    void mixedOperationsKeepContentsAndSizeConsistent() {
        LinkedList<String> actual = new LinkedList<>();
        java.util.List<String> expected = new java.util.ArrayList<>();

        actual.add("first");
        expected.add("first");
        assertContentsMatch(expected, actual);

        actual.add(0, "new first");
        expected.add(0, "new first");
        assertContentsMatch(expected, actual);

        actual.add(1, "middle");
        expected.add(1, "middle");
        assertContentsMatch(expected, actual);

        actual.set(2, "updated");
        expected.set(2, "updated");
        assertContentsMatch(expected, actual);

        assertEquals(expected.remove(1), actual.remove(1));
        assertContentsMatch(expected, actual);

        assertEquals(expected.remove("first"), actual.remove("first"));
        assertContentsMatch(expected, actual);

        actual.clear();
        expected.clear();
        assertContentsMatch(expected, actual);

        actual.add("after clear");
        expected.add("after clear");
        assertContentsMatch(expected, actual);
    }

    @Test
    void containsAndIndexOfFindFirstDuplicateAndReturnNotFoundResult() {
        LinkedList<String> list = new LinkedList<>();
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
        LinkedList<String> list = new LinkedList<>();
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
        LinkedList<String> list = listWithTwoElements();

        list.clear();

        assertEquals(0, list.size());
        assertTrue(list.isEmpty());
        list.add("again");
        assertEquals("again", list.get(0));
    }

    @Test
    void clearOnEmptyListKeepsItEmpty() {
        LinkedList<String> list = new LinkedList<>();

        list.clear();

        assertEquals(0, list.size());
        assertTrue(list.isEmpty());
    }

    @Test
    void iteratorTraversesElementsInOrder() {
        LinkedList<String> list = listWithTwoElements();
        Iterator<String> iterator = list.iterator();

        assertTrue(iterator.hasNext());
        assertEquals("first", iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals("second", iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    void iteratorOnEmptyListHasNoNextElement() {
        Iterator<String> iterator = new LinkedList<String>().iterator();

        assertFalse(iterator.hasNext());
    }

    @Test
    void iteratorNextThrowsWhenNoElementsRemain() {
        Iterator<String> iterator = new LinkedList<String>().iterator();

        assertThrows(NoSuchElementException.class, iterator::next);
    }

    @Test
    void iteratorDetectsAdditionFromHasNextAndNext() {
        LinkedList<String> list = listWithTwoElements();
        Iterator<String> hasNextIterator = list.iterator();
        Iterator<String> nextIterator = list.iterator();
        list.add("third");

        assertThrows(ConcurrentModificationException.class, hasNextIterator::hasNext);
        assertThrows(ConcurrentModificationException.class, nextIterator::next);
    }

    @Test
    void iteratorDetectsRemoval() {
        LinkedList<String> list = listWithTwoElements();
        Iterator<String> iterator = list.iterator();
        list.remove(0);

        assertThrows(ConcurrentModificationException.class, iterator::hasNext);
    }

    @Test
    void iteratorDetectsRemovalAfterAdvancing() {
        LinkedList<String> list = listWithTwoElements();
        Iterator<String> iterator = list.iterator();
        assertEquals("first", iterator.next());

        list.remove(1);

        assertThrows(ConcurrentModificationException.class, iterator::next);
    }

    @Test
    void iteratorDetectsRemovalByElement() {
        LinkedList<String> list = listWithTwoElements();
        Iterator<String> iterator = list.iterator();

        assertTrue(list.remove("second"));

        assertThrows(ConcurrentModificationException.class, iterator::hasNext);
    }

    @Test
    void iteratorDetectsClearOfNonEmptyList() {
        LinkedList<String> list = listWithTwoElements();
        Iterator<String> iterator = list.iterator();
        list.clear();

        assertThrows(ConcurrentModificationException.class, iterator::hasNext);
    }

    @Test
    void clearOnEmptyListDoesNotInvalidateIterator() {
        LinkedList<String> list = new LinkedList<>();
        Iterator<String> iterator = list.iterator();

        list.clear();

        assertFalse(iterator.hasNext());
    }

    private LinkedList<String> listWithTwoElements() {
        LinkedList<String> list = new LinkedList<>();
        list.add("first");
        list.add("second");
        return list;
    }

    private void assertContentsMatch(java.util.List<String> expected, LinkedList<String> actual) {
        assertEquals(expected.size(), actual.size());
        assertEquals(expected.isEmpty(), actual.isEmpty());
        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i), actual.get(i));
        }
    }
}
