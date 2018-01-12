import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;

public class Solver {
	
	private Board initialBoard;
	private boolean solvable;
	
	// find a solution to the initial board (using the A* algorithm)
	public Solver(Board initial) {
		initialBoard = initial;
		int moves = 0;
		int twinMoves = 0;
		
		Queue<Board> neighbors;
		Queue<Board> twinNeighbors;
		
		MinPQ<SearchNode> searchNodes = new MinPQ<SearchNode>();
		MinPQ<SearchNode> twinNodes = new MinPQ<SearchNode>();
		
		SearchNode searchNode = new SearchNode(initial, moves, null);
		SearchNode twinSearchNode = new SearchNode(initial.twin(), twinMoves, null);

		twinNodes.insert(twinSearchNode);
		searchNodes.insert(searchNode);
		
		boolean solved = false;
		boolean twinSolved = false;
		//System.out.println("Get Board: " + searchNodes.delMin().getBoard());
		
		while (!solved && !twinSolved) {
			SearchNode current = searchNodes.delMin();
			SearchNode predecessor = current.getPredecessor();
			Board temp = current.getBoard();
			solved = temp.isGoal();
			
			SearchNode twinCurrent = twinNodes.delMin();
			SearchNode twinPredecessor = twinCurrent.getPredecessor();
			Board twinTemp = twinCurrent.getBoard();
			twinSolved = twinTemp.isGoal();
			
			neighbors = (Queue<Board>) temp.neighbors();
			twinNeighbors = (Queue<Board>) twinTemp.neighbors();
			System.out.println("Moves: " + moves + "\n" + temp);
			
			while(neighbors.size() > 0) {
				Board board = neighbors.dequeue();
				
				if (predecessor != null && predecessor.getBoard().equals(board))
					continue;
				
				SearchNode neighborNode = new SearchNode(board, moves, current);
				searchNodes.insert(neighborNode);
			}
			
			while(twinNeighbors.size() > 0) {
				Board board = twinNeighbors.dequeue();
				
				if (twinPredecessor != null && twinPredecessor.getBoard().equals(board))
					continue;
				
				SearchNode neighborNode = new SearchNode(board, twinMoves, twinCurrent);
				twinNodes.insert(neighborNode);
			}
			
			moves++;
			twinMoves++;
		}
		
		solvable = !twinSolved;
	}
	
	// is the initial board solvable?
	public boolean isSolvable() {
		return solvable;
		
	}
	
	// min number of moves to solve initial board; -1 if unsolvable
	public int moves() {
		return 0;
		
	}
	
	// sequence of boards in a shortest solution; null if unsolvable
	public Iterable<Board> solution() {
		return null;
		
	}
	
	private void solveBoard(Board initial) {
		
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
		int [] unsolvable = { 1, 2, 3, 4, 5, 6, 8, 7, 0 };
		int [] test = { 1, 2, 3, 4, 6, 5, 7, 8, 0 };
		int[][] blocks = new int[3][3];
		int[][] block2 = new int [2][2];
		int idx = 0;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				blocks[i][j] = unsolvable[idx++];
			}
		}
		
		int [] unsolvable2 = { 1, 0, 2, 3 };
		int k = 0;
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 2; j++) {
				block2[i][j] = unsolvable2[k++];
			}
		}
		
		Board board = new Board(blocks);
		System.out.println(board);
		System.out.println("TWIN: " + board.twin());
		
		Solver solver = new Solver(board);
		System.out.println("Is solvable? " + solver.isSolvable());
		
	}
}
