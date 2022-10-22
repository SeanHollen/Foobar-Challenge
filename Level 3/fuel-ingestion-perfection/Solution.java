import java.util.HashMap;

public class Solution {
    
    private static HashMap<String, Integer> seen = new HashMap<>();
    
    public static int solution(String x) {
        if (x.equals("1")) return 0;
        if (seen.containsKey(x)) return seen.get(x);
        boolean isEven = isEven(x);
        int work = 1;
        if (!isEven) work++;
        int a = solution(half(isEven ? x : minusOne(x)));
        int b = solution(half(isEven ? x : plusOne(x)));
        int answer = work + Math.min(a, b);
        seen.put(x, answer);
        return answer;
    }
    
    private static boolean isEven(String s) {
        return Character.getNumericValue(s.charAt(s.length() - 1)) % 2 == 0;
    }
    
    private static String half(String s) {
        StringBuilder b = new StringBuilder();
        boolean carry = false;
        for (int i = 0; i < s.length(); i++) {
            int n = Character.getNumericValue(s.charAt(i));
            boolean isOdd = n % 2 != 0;
            if (isOdd) n--;
            n /= 2;
            if (carry) n += 5;
            carry = isOdd;
            b.append(n);
        }
        return withoutLeading0(b.toString());
    }
    
    private static String minusOne(String s) {
        StringBuilder b = new StringBuilder();
        int i = s.length() - 1;
        while (i >= 0) {
            int n = Character.getNumericValue(s.charAt(i));
            if (n != 0) {
                b.append(n - 1);
                break;
            } else {
                b.append("9");
            }
            i--;
        }
        if (i == -1) i = 0;
        String answer = s.substring(0, i).concat(b.reverse().toString());
        return withoutLeading0(answer);
    }
    
    private static String plusOne(String s) {
        StringBuilder b = new StringBuilder();
        int i = s.length() - 1;
        while (i >= 0) {
            int n = Character.getNumericValue(s.charAt(i));
            if (n != 9) {
                b.append(n + 1);
                break;
            } else {
                b.append("0");
            }
            i--;
        }
        if (i == -1) i = 0;
        String end = b.reverse().toString();
        String answer = s.substring(0, i).concat(end);
        String growth = answer.charAt(0) == '0' ? "1" : "";
        return growth + answer;
    }
    
    private static String withoutLeading0(String s) {
        if (s.charAt(0) == '0') {
            return s.substring(1, s.length());
        } else {
            return s;
        }
    }
}