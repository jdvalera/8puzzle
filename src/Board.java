import java.util.ArrayList;

import edu.princeton.cs.algs4.Queue;

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
		int row = 0;
		int col = 0;
		int up = 0, right = 0, down = 0, left = 0;
		
		int[][] twin2d = new int[width][width];
		int[] twin1d = new int[board.length];
		
		// copy board to twin1d
		for (int i = 0; i < board.length; i++) {
			twin1d[i] = board[i];
		}
		
		// check for non zero block in twin1d
		// exchange non zero block with another non zero block
		for (int i = 0; i < twin1d.length; i++) {
			if (twin1d[i] != 0) {
				col = xyFrom1D(i)[0];
				row = xyFrom1D(i)[1];
						
				if (checkBoundary(row - 1, col)) {
					up = xyTo1D(row - 1, col);
					exch1D(twin1d, i, up);
				} else if (checkBoundary(row, col + 1)) {
					right = xyTo1D(row, col + 1);
					exch1D(twin1d, i, right);
				} else if (checkBoundary(row + 1, col)) {
					down = xyTo1D(row + 1, col);
					exch1D(twin1d, i, down);
				} else if (checkBoundary(row, col - 1)) {
					left = xyTo1D(row, col - 1);
					exch1D(twin1d, i, left);
				}
						
				break;
			}
		}
		
		// copy 1d twin to 2d twin
		twin2d = copy1DTo2D(twin1d, this.width);
				
		//System.out.println("Board position 4: " + board[4]);
		//System.out.println("Up: " + board[up]);
		//System.out.println("Right: " + board[right]);
		//System.out.println("Down: " + board[down]);
		//System.out.println("Left: " + board[left]);
		return new Board(twin2d);
		
	}
	
	// does this board equal y?
	public boolean equals(Object y) {
		if (y == this) return true;
		if (y == null) return false;
		if (y.getClass() != this.getClass()) return false;
		Board that = (Board) y;
		for (int i = 0; i < board.length; i ++) {
			if (this.board[i] != that.board[i])
				return false;
		}
		return true;
		
	}
	
	// all neighboring boards
	public Iterable<Board> neighbors() {
		Queue<Board> neighbors = new Queue<Board>();
		int emptyIndex;
		int row = 0;
		int col = 0;
		int up = 0, right = 0, down = 0, left = 0;
		ArrayList<Integer> blocks = new ArrayList<Integer>();
		
		// look for empty block position
		// get empty position and every position it can exchange with
		// add all positions into blocks list
		for (emptyIndex = 0; emptyIndex < board.length; emptyIndex++) {
			if (board[emptyIndex] == 0) {
				
				col = xyFrom1D(emptyIndex)[0];
				row = xyFrom1D(emptyIndex)[1];
				
				if (checkBoundary(row - 1, col)) {
					up = xyTo1D(row - 1, col);
					blocks.add(up);
				}
				
				if (checkBoundary(row, col + 1)) {
					right = xyTo1D(row, col + 1);
					blocks.add(right);
				}
				
				if (checkBoundary(row + 1, col)) {
					down = xyTo1D(row + 1, col);
					blocks.add(down);
				}
				
				if (checkBoundary(row, col - 1)) {
					left = xyTo1D(row, col - 1);
					blocks.add(left);
				}
				
				break;
			}
		}
		
		// create temp copy of board
		// exchange blocks and turn array into 2d
		// create Board and enqueue it
		for (int i = 0; i < blocks.size(); i++) {
			int[] temp1d;
			int[][] temp2d;
			temp1d = copy1DTo1D(board);
			exch1D(temp1d, emptyIndex, blocks.get(i));
			temp2d = copy1DTo2D(temp1d, this.width);
			Board tempBoard = new Board(temp2d);
			neighbors.enqueue(tempBoard);
		}
		
		return neighbors;
		
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
	private int xyTo1D(int row, int col) {
		return ((col) % width) + (width * (row));
	}
	
	// helper method for 1d to 2d array
	private int[] xyFrom1D(int i) {
		int [] xy = new int[2];
		xy[0] = i % width; // col
		xy[1] = i / width; // row
		return xy;
	}
	
	// exchange index values in an array
	private void exch1D(int[] arr, int i, int j) {
		int temp = 0;
		temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}
	
	private int[][] copy1DTo2D(int[] arr, int width) {
		int[][] arr2D = new int[width][width];
		for (int i = 0; i < arr.length; i++) {
			int x = xyFrom1D(i)[0];
			int y = xyFrom1D(i)[1];
			arr2D[y][x] = arr[i];
		}
		return arr2D;
	}
	
	private int[] copy1DTo1D(int[] a) {
		int[] b = new int[a.length];
		if (a.length != b.length)
			return null;
		for (int i = 0; i < a.length; i++) {
			b[i] = a[i];
		}
		
		return b;
	}
	
	private boolean checkBoundary(int row, int col) {
		if(row < 0 || row >= board.length || col < 0 || col >= width)
			return false;
		return true;
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
		int [] numbers = { 8, 1, 3, 4, 2, 0, 7, 6, 5};
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
		System.out.println();
		
		//Board twin;
		//twin = board.twin();
		//System.out.println("Twin");
		//System.out.println(twin);
		
		int n = 1;
		Iterable<Board> neighbors = board.neighbors();
		for (Board b : neighbors) {
			System.out.println("Neighbor " + n++);
			System.out.println(b);
		}
	}

}
