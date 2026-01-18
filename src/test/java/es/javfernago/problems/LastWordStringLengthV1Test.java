package es.javfernago.problems;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class LastWordStringLengthV1Test {
	
	private LastWordStringLenghtV1 lastWordStringLength = new LastWordStringLenghtV1();
	
	@ParameterizedTest
	@MethodSource("testStrings")
	void testLastWorStringLength (String input, int length) {
		int calculatedLength = lastWordStringLength.calculateLastWordLength(input);
		assertEquals(length, calculatedLength);
	}
	
	private static Stream<Arguments> testStrings() {
		return Stream.of(
					Arguments.of("", 0),
					Arguments.of("  ",0),
					Arguments.of("   one   ",3),
					Arguments.of("This is a longer string",6)
				);
	}

}
