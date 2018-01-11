
public class Board {
	
	private int[] board;
	private int offset = 1;
	private int width;
	
	// construct a board from an n-by-n array of blocks
	// (where blocks[i][j] = block in row i, column j)
	public Board(int[][] blocks) {
		
		if (blocks == null)
			throw new java.lang.IllegalArgumentException();
		
		if (blocks.length != blocks[0].length)
			throw new java.lang.IllegalArgumentException();
		
		width = blocks.length;
		board = new int[width*width];
		
		for (int i = 0; i < blocks.length; i++) {
			for (int j = 0; j < blocks[0].length; j++) {
				board[xyTo1D(i,j)] = blocks[i][j];
			}
		}
		
	}
	
	// board dimension n
	public int dimension() {
		return width;
		
	}
	
	// number of blocks out of place
	public int hamming() {
		int sum = 0;
		for (int i = 0; i < board.length; i++) {
			if (board[i] != i + offset && board[i] != 0)
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
			if (board[i] != i + offset && board[i] != 0) {
				x1 = xyFrom1D(i)[0];
				y1 = xyFrom1D(i)[1];
				//System.out.println("Board Content: " + board[i]);
				x2 = xyFrom1D(board[i] - offset)[0];
				y2 = xyFrom1D(board[i] - offset)[1];
				//System.out.println("x1: " + x1 + " y1: " + y1 + " x2: " + x2 + " y2 " + y2);
				//System.out.println("Distance: " + (Math.abs(x1-x2) + Math.abs(y1-y2)));
				sum = sum + ( Math.abs(x1-x2) + Math.abs(y1-y2) );
			}
		}
		return sum;
		
	}
	
	// is this board the goal board?
	public boolean isGoal() {
		for (int i = 0; i < board.length - offset; i++)
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
		StringBuilder s = new StringBuilder();
	    s.append(width + "\n");
	    for (int i = 0; i < width; i++) {
	        for (int j = 0; j < width; j++) {
	            s.append(String.format("%2d ", board[xyTo1D(i,j)]));
	        }
	        s.append("\n");
	    }
	    return s.toString();
	}
	
	// helper method for 2d to 1d array
	private int xyTo1D(int x, int y) {
		return ((y) % width) + (width * (x));
	}
	
	// helper method for 1d to 2d array
	private int[] xyFrom1D(int i) {
		int [] xy = new int[2];
		xy[0] = i % width;
		xy[1] = i / width;
		return xy;
	}
	
	private static int[][] boardFromArray(int[] arr, int width) {
		int[][] test = new int[width][width];
		int idx = 0;
		
		for (int i = 0; i < test.length; i++) {
			for (int j = 0; j < test.length; j++) {
				test[i][j] = arr[idx++];
			}
		}
		
		return test;
	}
	
	// unit test
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int[][] test = new int[3][3];
		int[][] goalBoard = new int[3][3];
		int [] numbers = { 8, 1, 3, 4, 0, 2, 7, 6, 5};
		int [] goal = {1, 2, 3, 4, 5, 6, 7, 8, 0};
		int idx = 0;
		
		test = boardFromArray(numbers, 3);
		goalBoard = boardFromArray(goal, 3);
		
		/*
		for (int i = 0; i < test.length; i++) {
			for (int j = 0; j < test.length; j++) {
				System.out.print(test[i][j] + " ");
			}
			System.out.println();
		}*/
		
		Board board = new Board(test);
		System.out.println(board);
		
		System.out.println("Is it goal board? " + board.isGoal());
		System.out.println("Manhattan: " + board.manhattan());
		System.out.println("Hamming: " + board.hamming());

	}

}
