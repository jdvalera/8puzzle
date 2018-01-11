import edu.princeton.cs.algs4.MinPQ;

public class Solver {
	
	// find a solution to the initial board (using the A* algorithm)
	public Solver(Board initial) {
		Iterable<Board> neighbors = initial.neighbors();
		Board predecessor = null;
		Board searchNode = initial;
		
		
	}
	
	// is the initial board solvable?
	public boolean isSolvable() {
		return false;
		
	}
	
	// min number of moves to solve initial board; -1 if unsolvable
	public int moves() {
		return 0;
		
	}
	
	// sequence of boards in a shortest solution; null if unsolvable
	public Iterable<Board> solution() {
		return null;
		
	}
	
	private class SearchNode implements Comparable<Board> {
		
		private SearchNode predecessor = null;
		private Board current = null;
		private int moves = 0;
		private int priority = 0;
		
		public SearchNode(Board initial, int m, SearchNode pred) {
			predecessor = pred;
			moves = m;
			current = initial;
			
			priority = m + initial.manhattan();
		}
		
		public int getPriority() {
			return priority;
		}

		@Override
		public int compareTo(Board o) {
			// TODO Auto-generated method stub
			return 0;
		}
	}
	
	// solve a slider puzzle
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
