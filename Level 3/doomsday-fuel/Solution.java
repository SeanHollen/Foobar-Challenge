import java.util.ArrayList;

public class Solution {
    public static int[] solution(int[][] m) {
        ArrayList<Integer> terminalStatesList = new ArrayList<>();
        for (int a = 0; a < m.length; a++) {
            int rowASum = rowSum(m[a]);
            if (rowASum == 0) {
                terminalStatesList.add(a);
                continue;
            }
            rowASum -= m[a][a];
            m[a][a] = 0;
            for (int b = 0; b < m.length; b++) {
                if (m[b][a] == 0) continue;
                for (int c = 0; c < m.length; c++) {
                    if (c == a) continue;
                    m[b][c] *= rowASum;
                    m[b][c] += m[a][c] * m[b][a];
                }
                m[b][a] = 0;
                simplify(m[b]);
            }
        }
        int[] result = new int[terminalStatesList.size() + 1];
        for (int i = 0; i < terminalStatesList.size(); i++) {
            result[i] = m[0][terminalStatesList.get(i)];
        }
        result[result.length - 1] = rowSum(m[0]);
        if (result[result.length - 1] == 0) {
            result[0] = 1;
            result[result.length - 1] = 1;
        }
        return result;
    }
    
    private static int rowSum(int[] m) {
        int total = 0;
        for (int item : m) {
            total += item;
        }
        return total;
    }

    private static void simplify(int[] m) {
        int result = m[0];
        for (int i = 1; i < m.length; i++) {
            result = gcd(result, m[i]);
        }
        for (int i = 0; i < m.length; i++) {
            m[i] /= result;
        }
    }

    private static int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }
}