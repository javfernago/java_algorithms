package es.javfernago.problems;

public class LastWordStringLenghtV2 {
	
	public int calculateLastWordLength(String input ) {
		
		int length = 0;
		int index = input.length()-1;
		
		System.out.println(index);
		
		while (index >= 0 && input.charAt(index) == ' ') index--;
		
		while (index >= 0 && input.charAt(index) != ' ') {
			index--;
			length++;
		}
		
		return length;
	}

}
