public class Solution {
    public static int solution(int n) {
        // int[num of blocks remaining][max height of next step]
        int[][] staircaseDp = new int[n+1][n+1];
        for (int i = 0; i <= n; i++) {
            staircaseDp[0][i] = 1;
        }
        for (int i = 1; i <= n; i++) {
            int possibilities = 0;
            for (int h = 1; h <= n; h++) {
                if (h <= i) {
                    possibilities += staircaseDp[i-h][h-1];
                }
                staircaseDp[i][h] = possibilities;
            }
        }
        // staircase with size of 1 step not allowed; sub 1
        return staircaseDp[n][n] - 1;
    }
}

