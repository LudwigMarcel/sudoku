package model.entities;

import java.util.Random;

public class SudokuGenerator {

	private static final int size = 9;
	private Random random = new Random();
	private int[][] board;

	public SudokuGenerator() {
		board = new int[size][size];
		generateBoard();
	}

	public void generateBoard() {
		fillDiagonal(); // preencher as diagonais
		fillRemaining(0, 3); // preenche o restante do tabuleiro
		removeDigits(); // remove num de posição aleatoria
	}

	// itera na diagonal e preenche o box
	// - - - - - - 0 0 0
	// - - - - - - 0 0 0
	// - - - - - - 0 0 0
	// - - - 0 0 0 - - -
	// - - - 0 0 0 - - -
	// - - - 0 0 0 - - -
	// 0 0 0 - - - - - -
	// 0 5 0 - - - - - -
	// 3 7 6 - - - - - -
	private void fillDiagonal() {
		for (int i = 0; i < size; i += 3) {
			fillBox(i, i);
		}
	}

	// preenche uma celula com um numero random
	private void fillBox(int row, int col) {
		int num;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				do {
					num = random.nextInt(size) + 1;
				} while (!isUnusedInBox(row, col, num)); // gera numeros aleatorios ate a condição ser true ser negada
				board[row + i][col + j] = num; // sai do loop e atribui o valor
			}
		}
	}

	private boolean isUnusedInBox(int rowStart, int colStart, int num) {
		for (int i = 0; i < 3; i++) { // itera na linha
			for (int j = 0; j < 3; j++) { // itera na coluna
				if (board[rowStart + i][colStart + j] == num) { // verifica se o num ja foi usado na box
					return false; // numero ja usado na box
				}
			}
		}
		return true; // numero nao usado na box
	}

	private boolean fillRemaining(int i, int j) {
		if (j >= size && i < size - 1) { // verifica se o j chegou no final, col 9 e nao esta na ultima linha
			j = 0; // verifica se chegou na ultima linha
			i++; // se ambas true, zera a coluna e passa para proxima linha
		}
		if (i < 3) {
			if (j < 3) {
				j = 3; // pula a col da box preenchida (box 0x0 a 2x2)
			}
		} else if (i < size - 3) {
			if (j == (int) (i / 3) * 3) { // pula a col da segunda box ( 3x3 a 5x5)
				j += 3;
			}
		} else {
			if (j == size - 3) { // verifica se chegou na ultima box (6x6 a 9x9)
				i++; // volta para o inicio, na linha seguinte
				j = 0;
				if (i >= size) { // chegou na ultima linha
					return true;
				}
			}
		}

		for (int num = 1; num <= size; num++) {
			if (isSafeToPlace(i, j, num)) {
				board[i][j] = num;
				if (fillRemaining(i, j + 1)) {
					return true;
				}
				board[i][j] = 0;
			}
		}
		return false;

	}

	private boolean isSafeToPlace(int i, int j, int num) {
		return isUnusedInRow(i,num) && isUnusedInCol(j, num) && isUnusedInBox(i - i % 3, j - j % 3, num);
	}

	// verifica se a culuna nao tem esse numero
	private boolean isUnusedInCol(int j, int num) {
		for(int i = 0; i <size; i++) {
			if(board[i][j] == num) {
				return false;
			}
		}
		return true;
	}

	// verifica se a linha nao tem o numero
	private boolean isUnusedInRow(int i, int num) {
		for (int j = 0; j< size; j++) {
			if(board[i][j] == num) {
				return false;
			}
		}
		return true;
	}

	// 
	private void removeDigits() {
		int count = 20;
		while(count != 0) {
			int cellID = random.nextInt(size * size);
			int i = cellID/size;
			int j = cellID%size;
			if(j!=0) {
				j--;
			}
			
			if(board[i][j] != 0) {
				count--;
				board[i][j] = 0;
			}
		}
	}
	
	public int[][] getBoard(){
		return board;
	}
}
