package es.javfernago.problems;

public class LastWordStringLenghtV1 {
	
	public int calculateLastWordLength(String input ) {
		String reverse = new StringBuffer(input).reverse().toString();
		
		int length = 0;
		int index = 0;
		
		while (index < reverse.length() && reverse.charAt(index) == ' ') index++;
		
		while (index < reverse.length() && reverse.charAt(index) != ' ') {
			index++;
			length++;
		}
		
		return length;
	}

}
