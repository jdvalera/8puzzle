
public class Board {
	
	int[] board;
	int offset = 1;
	int width;
	
	// construct a board from an n-by-n array of blocks
	// (where blocks[i][j] = block in row i, column j)
	public Board(int[][] blocks) {
		
	}
	
	// board dimension n
	public int dimension() {
		return 0;
		
	}
	
	// number of blocks out of place
	public int hamming() {
		int sum = 0;
		for (int i = 0; i < board.length; i++) {
			if (board[i] != i+1)
				sum++;
		}
		return sum;
		
	}
	
	// sum of Manhattan distances between blocks and goal
	public int manhattan() {
		int sum = 0;
		int x1 = 0;
		int x2 = 0;
		int y1 = 0;
		int y2 = 0;
		for (int i = 0; i < board.length; i++) {
			if (board[i] != i+offset) {
				x1 = xyFrom1D(i)[0];
				y1 = xyFrom1D(i)[1];
				x2 = xyFrom1D(board[i] - offset)[0];
				y2 = xyFrom1D(board[i] - offset)[0];
				sum = sum + ( (x1-x2) + (y1-y2) );
			}
		}
		return 0;
		
	}
	
	// is this board the goal board?
	public boolean isGoal() {
		for (int i = 0; i < board.length; i++)
			if (board[i] != i+1)
				return false;
		return true;
		
	}
	
	// a board that is obtained by exchanging any pair of blocks
	public Board twin() {
		return null;
		
	}
	
	// does this board equal y?
	public boolean equals(Object y) {
		return false;
		
	}
	
	// all neighboring boards
	public Iterable<Board> neighbors() {
		return null;
		
	}
	
	// string representation of this board (in the output format specified below)
	public String toString() {
		return null;
		
	}
	
	// helper method for 2d to 1d array
	private int xyTo1D(int x, int y) {
		return ((x) % width) + (width * (y));
	}
	
	// helper method for 1d to 2d array
	private int[] xyFrom1D(int i) {
		int [] xy = new int[2];
		xy[0] = i / width;
		xy[1] = i % width;
		return xy;
	}
	
	
	// unit test
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
