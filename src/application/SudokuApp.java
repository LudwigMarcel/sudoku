package application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.entities.SudokuBoard;
import model.entities.SudokuRules;
import model.entities.SudokuSolver;

public class SudokuApp extends Application {
	private static final int SIZE = 9;
	private TextField[][] cells = new TextField[SIZE][SIZE];
	private int[][] board;
	private SudokuBoard sudokuBoard;
	private SudokuSolver solver;
	private SudokuRules rules;

	@Override
	public void start(Stage primaryStage) {
		sudokuBoard = new SudokuBoard();
		solver = new SudokuSolver();
		rules = new SudokuRules();
		board = sudokuBoard.getBoard();

		GridPane grid = createGrid();

		HBox buttonBox = new HBox(10);
		Button newGameButton = new Button("Novo Jogo");
		Button checkButton = new Button("Verificar");
		Button hintButton = new Button("Dica");
		Button solveButton = new Button("Resolver");

		newGameButton.setOnAction(e -> startNewGame());
		checkButton.setOnAction(e -> checkBoard());
		hintButton.setOnAction(e -> giveHint());
		solveButton.setOnAction(e -> solveBoard());

		buttonBox.getChildren().addAll(newGameButton, checkButton, hintButton, solveButton);

		VBox vbox = new VBox(10, grid, buttonBox);
		vbox.setPadding(new Insets(10));

		Scene scene = new Scene(vbox, 400, 500);
		primaryStage.setTitle("Sudoku");
		primaryStage.setResizable(false);
		primaryStage.setScene(scene);
		primaryStage.show();

		updateBoard();
	}

	private GridPane createGrid() {
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10));
		grid.setHgap(5);
		grid.setVgap(5);

		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				TextField cell = new TextField();
				cell.setPrefSize(40, 40);
				cell.setStyle("-fx-alignment: center;");
				cells[i][j] = cell;
				grid.add(cell, j, i);
			}
		}

		return grid;
	}

	// travando o sistema, para de responder
	private void startNewGame() {
		sudokuBoard.clearBoard();
		sudokuBoard.generateBoard();
		board = sudokuBoard.getBoard();
		updateBoard();
	}

	private boolean checkBoard() {
		boolean isCorrect = true;
		int[][] tempBoard = new int[SIZE][SIZE];
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				String text = cells[i][j].getText();
				if (!text.isEmpty()) {
					tempBoard[i][j] = Integer.parseInt(text);
				} else {
					tempBoard[i][j] = 0;
				}
			}
		}
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				String txt = cells[i][j].getText();
				if (!txt.isEmpty()) {
					int num = Integer.parseInt(txt);
					if (rules.isSafeToPlace(tempBoard, i, j, num)) {
						cells[i][j].setStyle("-fx-background-color: green;");
					} else {
						cells[i][j].setStyle("-fx-background-color: red;");
						isCorrect = false;
					}
				}
			}
		}
		if (isCorrect) {
			showMessage("Tabuleiro esta correto!");			
		} else {
			showMessage("Algo esta errado!");
		}
		return isCorrect;
	}

	private void giveHint() {
		int[] hint = solver.getHint(board);
		if (hint != null) {
			int row = hint[0];
			int col = hint[1];
			int num = hint[2];
			cells[row][col].setText(String.valueOf(num));
			cells[row][col].setStyle("-fx-background-color: lightgreen;");
		} else {
			showMessage("Nenhuma sugestão disponível.");
		}
	}

	private void solveBoard() {
		solver.solve(board);
		updateBoard();
	}

	private void updateBoard() {
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				if (board[i][j] != 0) {
					cells[i][j].setText(String.valueOf(board[i][j]));
					cells[i][j].setEditable(false);
				} else {
					cells[i][j].setText("");
					cells[i][j].setEditable(true);
				}
			}
		}
	}

	private void showMessage(String message) {
		Stage messageStage = new Stage();
		VBox vbox = new VBox(10);
		vbox.setPadding(new Insets(10));
		vbox.getChildren().add(new Label(message));
		Scene scene = new Scene(vbox, 200, 100);
		messageStage.setScene(scene);
		messageStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
