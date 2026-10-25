package es.javfernago.problems;

public class ReverseString {

    public String reverseStringWithArray(String s) {
        char[] chars = s.toCharArray();
        int start = 0;
        int end = chars.length -1;
        while (start < end) {
            char temp = chars[start];
            chars[start] = chars[end];
            chars[end] = temp;
            start ++;
            end --;
        }

        return new String(chars);
    }

    public String reverseStringWithStringBuilder(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = s.length() -1; i >=0; i--) {
            sb.append(s.charAt(i));
        }

        return sb.toString();
    }
}
