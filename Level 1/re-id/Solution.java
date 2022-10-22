import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solution{
    public static String solution(int i) {
        // the Nth prime is about N*ln(N)
        int sizeFactor = (i+5) * Math.max(5, (int)(Math.log(i+5)));
        List<Integer> primes = primes(sizeFactor);
        String result = "";
        for (int len = 0, idx = 0; len < i + 5; idx++) {
          String nextPrime = String.valueOf(primes.get(idx));
          if (len + nextPrime.length() > i) {
            if (len < i) {
              result += nextPrime.substring(i - len, nextPrime.length());
            } else {
              result += nextPrime;
            }
          }
          len += nextPrime.length();
        }
        return result.substring(0, 5);
    }
    
    private static List<Integer> primes(int n) {
        boolean[] prime = new boolean[n + 1];
        Arrays.fill(prime, true);
        for (int p = 2; p * p <= n; p++) {
          if (prime[p]) {
            for (int i = p * 2; i <= n; i += p) {
              prime[i] = false;
            }
          }
        }
        List<Integer> primeNumbers = new ArrayList<>();
        for (int i = 2; i <= n; i++) {
          if (prime[i]) {
            primeNumbers.add(i);
          }
        }
        return primeNumbers;
      }
}

// Spymaster's logs
// Cool, calculating the primes is one of the first programming 
// things I ever did.

// I got help from a certain "google" search engine for the
// sieve of eratosthenes.

// Trickier: how long do I need to calculate primes?