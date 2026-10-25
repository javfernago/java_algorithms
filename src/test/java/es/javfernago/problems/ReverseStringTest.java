package es.javfernago.problems;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReverseStringTest {

    private final ReverseString reverseString = new ReverseString();

    @ParameterizedTest
    @MethodSource("stringCases")
    void reversesStringWithArray(String input, String expected) {
        assertEquals(expected, reverseString.reverseStringWithArray(input));
    }

    @ParameterizedTest
    @MethodSource("stringCases")
    void reversesStringWithStringBuilder(String input, String expected) {
        assertEquals(expected, reverseString.reverseStringWithStringBuilder(input));
    }

    private static Stream<Arguments> stringCases() {
        return Stream.of(
                Arguments.of("", ""),
                Arguments.of("a", "a"),
                Arguments.of("ab", "ba"),
                Arguments.of("abc", "cba"),
                Arguments.of("level", "level"),
                Arguments.of("hello world", "dlrow olleh"),
                Arguments.of("Java, 123!", "!321 ,avaJ")
        );
    }
}
