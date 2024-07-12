package model.entities;

public class SudokuBoard {
	
	private int[][] board;

	public SudokuBoard() {
		board = new int[9][9];
	}

	public int[][] getBoard() {
		return board;
	}

	public void setBoard(int[][] board) {
		this.board = board;
	}
	
	public boolean isValidMove() {
		//TODO
		return true;
	
	}
	
	public boolean solve() {
		//TODO
		return true;
	}
	
	

}
