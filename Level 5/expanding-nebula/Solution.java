import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

public class Solution {
    
    private static HashMap<Integer, HashMap<Integer, List<Integer>>> checked;
    
    public static int solution(boolean[][] g) {
        checked = new HashMap<>();
        int bitLen = g.length;
        int[] rows = transposedInts(g);
        HashMap<Integer, Integer> currentOldRowsCnts = new HashMap<>();
        for (int i = 0; i < 1<<(bitLen+1); i++) {
            currentOldRowsCnts.put(i, 1);
        }
        for (int currentRow : rows) {
            HashMap<Integer, Integer> nextOldRowsCnts = new HashMap<>();
            for (int oldRow : currentOldRowsCnts.keySet()) {
                for (int row : getPossibleNextRows(oldRow, currentRow, bitLen)) {
                    nextOldRowsCnts.putIfAbsent(row, 0);
                    nextOldRowsCnts.put(row, nextOldRowsCnts.get(row) + currentOldRowsCnts.get(oldRow));
                }
            }
            currentOldRowsCnts = nextOldRowsCnts;
        }
        int sum = 0;
        for (int key : currentOldRowsCnts.keySet()) {
            sum += currentOldRowsCnts.get(key);
        }
        return sum;
    }
    
    private static int[] transposedInts(boolean[][] g) {
        int[] rows = new int[g[0].length];
        for (int i = 0; i < g[0].length; i++) {
            boolean[] booleanRow = new boolean[g[0].length];
            for (int j = 0; j < g.length; j++) {
                booleanRow[j] = g[j][i];
            }
            rows[i] = asInt(booleanRow);
        }
        return rows;
    }
    
    private static int asInt(boolean[] booleans) {
        int sum = 0;
        for (int i = 0; i < booleans.length; i++) {
            if (booleans[i]) sum += 1<<i; // 1<<i is 2^i
        }
        return sum;
    }

    private static List<Integer> getPossibleNextRows(int rowA, int resultantRow, int bitLen) {
        checked.putIfAbsent(rowA, new HashMap<>());
        if (!checked.get(rowA).containsKey(resultantRow)) {
            checked.get(rowA).put(resultantRow, computePossibleNextRows(rowA, resultantRow, bitLen));
        }
        return checked.get(rowA).get(resultantRow);
    }

    private static List<Integer> computePossibleNextRows(int rowA, int resultantRow, int bitLen) {
        List<Integer> oldList = new ArrayList<>();
        oldList.add(0);
        oldList.add(1);
        for (int i = 0; i < bitLen; i++) {
            List<Integer> newList = new ArrayList<>();
            boolean r1 = (resultantRow & 1<<i) == 1<<i;
            boolean a1 = (rowA & 1<<i) == 1<<i;
            boolean a2 = (rowA & 2<<i) == 2<<i;
            boolean c1;
            boolean c2CanBeT, c2CanBeF;
            for (int prev : oldList) {
                c1 = (prev & 1<<i) == 1<<i;
                c2CanBeT = false;
                c2CanBeF = false;
                int cntXs = 0;
                cntXs += a1 ? 1 : 0;
                cntXs += a2 ? 1 : 0;
                cntXs += c1 ? 1 : 0;
                if (r1) {
                    if (cntXs == 0) {
                        c2CanBeT = true;
                    } else if (cntXs == 1) {
                        c2CanBeF = true;
                    }
                } else {
                    if (cntXs == 0) {
                        c2CanBeF = true;
                    } else if (cntXs == 1) {
                        c2CanBeT = true;
                    } else {
                        c2CanBeT = true;
                        c2CanBeF = true;
                    }
                }
                if (c2CanBeT) {
                    newList.add(prev|(2<<i));
                }
                if (c2CanBeF) {
                    newList.add(prev);
                }
            }
            oldList = newList;
        }
        return oldList;
    }
}