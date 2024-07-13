package model.entities;

public class SudokuRules {

	private static final int size = 9;	

	public boolean isSafeToPlace(int[][]board, int i, int j, int num) {
		return isUnusedInRow(board, i, j, num) && isUnusedInCol(board, i, j, num) && isUnusedInBox(board, i - i % 3, j - j % 3, i, j, num);
	}

	protected boolean isUnusedInBox(int[][]board, int rowStart, int colStart, int row, int col, int num) {
		for (int i = 0; i < 3; i++) { // itera na linha
			for (int j = 0; j < 3; j++) { // itera na coluna
				int currentRow = rowStart + i;
				int currentCol = colStart + j;
				// verificar tudo exeto onde eu quero colocar o numero
				if ((currentRow != row || currentCol != col) && board[currentRow][currentCol] == num) { 
					return false; // numero ja usado na box
				}
			}
		}
		return true; // numero nao usado na box
	}

	// verifica se a coluna nao tem esse numero
	private boolean isUnusedInCol(int[][]board, int row, int col, int num) {
		for (int r = 0; r < size; r++) {
			if (r != row && board[r][col] == num) {
				return false;
			}
		}
		return true;
	}

	// verifica se a linha nao tem o numero
	private boolean isUnusedInRow(int[][]board, int row, int col, int num) {
		for (int c = 0; c < size; c++) {
			if (c != col && board[row][c] == num) {
				return false;
			}
		}
		return true;
	}

}
