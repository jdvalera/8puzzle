import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;

public class Solver {
	
	// find a solution to the initial board (using the A* algorithm)
	public Solver(Board initial) {
		int moves = 0;
		Queue<Board> neighbors;
		
		MinPQ<SearchNode> searchNodes = new MinPQ<SearchNode>();
		SearchNode searchNode = new SearchNode(initial, moves, null);
		searchNodes.insert(searchNode);
		
		boolean solved = false;
		//System.out.println("Get Board: " + searchNodes.delMin().getBoard());
		
		while (!solved) {
			SearchNode current = searchNodes.delMin();
			SearchNode predecessor = current.getPredecessor();
			Board temp = current.getBoard();
			solved = temp.isGoal();
			
			moves++;
			neighbors = (Queue<Board>) temp.neighbors();
			System.out.println(temp);
			while(neighbors.size() > 0) {
				Board board = neighbors.dequeue();
				
				if (predecessor != null && predecessor.getBoard().equals(board))
					continue;
				
				SearchNode neighborNode = new SearchNode(board, moves, current);
				searchNodes.insert(neighborNode);
			}
		}
		
		//System.out.println(searchNodes.min().getBoard());
		//System.out.println(neighbors.dequeue());
		//System.out.println(neighbors.size());
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
	
	private class SearchNode implements Comparable<SearchNode> {
		
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
		
		public Board getBoard() {
			Board temp = current;
			return temp;
		}
		
		public int getMoves() {
			return moves;
		}
		
		public SearchNode getPredecessor() {
			SearchNode temp = predecessor;
			return temp;
		}

		@Override
		public int compareTo(SearchNode o) {
			if (this.priority > o.getPriority())
				return 1;
			else if (this.priority < o.getPriority())
				return -1;
			else
				return 0;
		}
	}
	
	// solve a slider puzzle
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int [] numbers = { 0, 1, 3, 4, 2, 5, 7, 8, 6 };
		int [] goal = {1, 2, 3, 4, 5, 6, 7, 8, 0};
		int[][] blocks = new int[3][3];
		int idx = 0;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				blocks[i][j] = numbers[idx++];
			}
		}
		Board board = new Board(blocks);
		
		Solver solver = new Solver(board);
		
	}
}
