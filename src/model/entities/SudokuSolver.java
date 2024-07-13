package model.entities;

import java.util.Random;

public class SudokuSolver {

	private static final int size = 9;
	private SudokuRules rules;
	
	public SudokuSolver() {
		this.rules = new SudokuRules();
	}

	// resolve o jogo inteiro
	public boolean solve(int[][] board) {
		int[] emptyCell = findEmptyCell(board);
		if (emptyCell == null) {
			return true;
		}
		int i = emptyCell[0];
		int j = emptyCell[1];

		// recebe um emptuCell, verifica, e atribui um numero, e chama o solve
		for (int num = 1; num <= size; num++) {
			if (rules.isSafeToPlace(board, i, j, num)) {
				board[i][j] = num;
				if (solve(board)) {
					return true;
				}
				// se no if acima retornar false, e a posição for null atribui 0
				board[i][j] = 0;
			}
		}
		return false;
	}

	// gera uma coordenada (0x0) e um numero possivel de ser colocado nela
	public int[] getHint(int[][] board) {
		int[] emptyCell = findEmptyCell(board);
		if (emptyCell == null) {
			return null;
		}

		int i = emptyCell[0];
		int j = emptyCell[1];

		for (int num = 1; num <= size; num++) {
			if (rules.isSafeToPlace(board, i, j, num)) {
				return new int[] { i, j, num };
			}
		}
		return null;
	}

	// localiza uma celula vazia
	public int[] findEmptyCell(int[][] board) {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (board[i][j] == 0) {
					return new int[] { i, j };
				}
			}
		}
		return null;
	}
	
	// localiza uma celula vazia
	// gera um i, j random
		public int[] findRandomEmptyCell(int[][] board) {
			Random random = new Random();
			int iR = random.nextInt(size);
			int jR = random.nextInt(size);
			for (int i = iR; i < size; i++) {
				for (int j = jR; j < size; j++) {
					if (board[i][j] == 0) {
						return new int[] { i, j };
					}
				}
			}
			return null;
		}

}
