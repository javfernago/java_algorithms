package es.javfernago.problems;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LastWordLengthTest {

    private final LastWordLength lastWordLength = new LastWordLength();

    @Test
    void returnsLengthOfLastWordInSentence() {
        assertEquals(5, lastWordLength.lengthOfLastWord("Hello World"));
    }

    @Test
    void returnsLengthOfOnlyWord() {
        assertEquals(4, lastWordLength.lengthOfLastWord("Java"));
    }

    @Test
    void ignoresLeadingAndRepeatedSpaces() {
        assertEquals(4, lastWordLength.lengthOfLastWord("  fly   me  to the moon"));
    }

    @Test
    void ignoresTrailingSpaces() {
        assertEquals(4, lastWordLength.lengthOfLastWord("Hello Java   "));
    }

    @Test
    void returnsZeroForEmptyString() {
        assertEquals(0, lastWordLength.lengthOfLastWord(""));
    }
}
