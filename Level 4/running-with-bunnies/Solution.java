import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Arrays;

public class Solution {
    
    public static int[] solution(int[][] times, int times_limit) {
        LinkedList<State> statesQueue = new LinkedList<>();
        ArrayList<LinkedList<State>> statesByLocation = new ArrayList<>();
        for (int i = 0; i < times.length; i++) {
            statesByLocation.add(new LinkedList<>());
        }
        statesQueue.add(new State(times_limit, new boolean[times.length - 2], 0));
        while (!statesQueue.isEmpty()) {
            State currState = statesQueue.pop();
            if (currState.isDeleted) {
                continue;
            }
            if (currState.isPerfectState()) {
                return asSolution(currState);
            }
            for (int to = 0; to < times.length; to++) {
                int newTimeLeft = currState.timeLeft - times[currState.currentLoc][to];
                boolean[] newCarriedBunnies = Arrays.copyOf(currState.carriedBunnies,
                        currState.carriedBunnies.length);
                if (to != 0 && to != times.length - 1) {
                    newCarriedBunnies[to - 1] = true;
                }
                State newState = new State(newTimeLeft, newCarriedBunnies, to);
                LinkedList<State> statesHere = statesByLocation.get(to);
                if (newState.isDominatedBy(statesHere)) {
                    continue;
                }
                List<State> newlyDominated = newState.getDominated(statesHere);
                statesHere.removeAll(newlyDominated);
                for (State s : newlyDominated) {
                    s.isDeleted = true;
                }
                statesHere.add(newState);
                statesQueue.add(newState);
            }
        }
        State bestState = bestStateOf(statesByLocation.get(times.length - 1));
        return asSolution(bestState);
    }
    
    // Note: If 2 states are equally good, returns first in list
    private static State bestStateOf(List<State> states) {
        State bestState = null;
        int bestStateBunniesCount = -1;
        for (State state : states) {
            if (state.timeLeft < 0) {
                continue;
            }
            int bunniesCount = state.bunniesCount();
            if (bunniesCount > bestStateBunniesCount) {
                bestState = state;
                bestStateBunniesCount = bunniesCount;
            }
        }
        return bestState;
    }
    
    private static int[] asSolution(State state) {
        int[] solution = new int[state.bunniesCount()];
        for (int index = 0, i = 0; i < state.carriedBunnies.length; i++) {
            if (state.carriedBunnies[i]) {
                solution[index] = i;
                index++;
            }
        }
        return solution;
    }
    
    private static class State {

        int timeLeft;
        boolean[] carriedBunnies;
        int currentLoc;
        boolean isDeleted;

        State(int timeLeft, boolean[] carriedBunnies, int currentLoc) {
            this.timeLeft = timeLeft;
            this.carriedBunnies = carriedBunnies;
            this.currentLoc = currentLoc;
            this.isDeleted = false;
        }

        int bunniesCount() {
            int total = 0;
            for (boolean b : carriedBunnies) {
                if (b) total++;
            }
            return total;
        }

        boolean isPerfectState() {
            boolean isAtBulkhead = currentLoc == carriedBunnies.length + 1;
            boolean allBunniesCarried = bunniesCount() == carriedBunnies.length;
            boolean hasTimeLeft = this.timeLeft >= 0;
            return isAtBulkhead && allBunniesCarried && hasTimeLeft;
        }

        boolean isDominatedBy(List<State> others) {
            for (State other: others) {
                if (other.strictlyDominates(this)) return true;
            }
            return false;
        }

        List<State> getDominated(List<State> others) {
            List<State> dominated = new ArrayList<>();
            for (State other : others) {
                if (this.strictlyDominates(other)) {
                    dominated.add(other);
                }
            }
            return dominated;
        }

        // States that are "strictly dominated" or "strictly dominant" is a concept from game theory.
        // State A strictly dominates State B if A is better than B in some way and worse in no way.
        // Note: This implementation also returns true if they are identical. 
        private boolean strictlyDominates(State other) {
            if (this.timeLeft < other.timeLeft) return false;
            for (int i = 0; i < carriedBunnies.length; i++) {
                if (other.carriedBunnies[i] && !this.carriedBunnies[i]) return false;
            }
            return true;
        }

    }
}