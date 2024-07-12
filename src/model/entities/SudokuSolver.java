package model.entities;

public class SudokuSolver {

	private static final int size = 9;

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
			if (isSafeToPlace(board, i, j, num)) {
				board[i][j] = num;
				if (solve(board)) {
					return true;
				}
				// se no if acima retornar true, e a posição for null atribui 0
				board[i][j] = 0;
			}
		}
		return false;
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

	// gera uma coordenada (0x0) e um numero possivel de ser colocado nela
	public int[] getHint(int[][] board) {
		int[] emptyCell = findEmptyCell(board);
		if (emptyCell == null) {
			return null;
		}

		int i = emptyCell[0];
		int j = emptyCell[1];

		for (int num = 1; num <= size; num++) {
			if (isSafeToPlace(board, i, j, num)) {
				return new int[] { i, j, num };
			}
		}
		return null;
	}

	private boolean isSafeToPlace(int[][] board, int i, int j, int num) {
		return isUnusedInRow(board, i, num) && isUnusedInCol(board, j, num)
				&& isUnusedInBox(board, i - i % 3, j - j % 3, num);
	}

	private boolean isUnusedInBox(int[][] board, int rowStart, int colStart, int num) {
		for (int i = 0; i < 3; i++) { // itera na linha
			for (int j = 0; j < 3; j++) { // itera na coluna
				if (board[rowStart + i][colStart + j] == num) { // verifica se o num ja foi usado na box
					return false; // numero ja usado na box
				}
			}
		}
		return true; // numero nao usado na box
	}

	// verifica se a culuna nao tem esse numero
	private boolean isUnusedInCol(int[][] board, int j, int num) {
		for (int i = 0; i < size; i++) {
			if (board[i][j] == num) {
				return false;
			}
		}
		return true;
	}

	// verifica se a linha nao tem o numero
	private boolean isUnusedInRow(int[][] board, int i, int num) {
		for (int j = 0; j < size; j++) {
			if (board[i][j] == num) {
				return false;
			}
		}
		return true;
	}
}
