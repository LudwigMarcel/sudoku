package application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
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
		sudokuBoard.removeDigits(20);
		
		GridPane grid = createGrid();

		HBox buttonBox = new HBox(10);
		Button checkButton = new Button("Verificar");
		Button hintButton = new Button("Dica");
		Button solveButton = new Button("Resolver");
		ComboBox<String> difficultyComboBox = new ComboBox<>();
		difficultyComboBox.getItems().addAll("Fácil", "Médio", "Difícil");
		difficultyComboBox.setPromptText("Dificuldade");

	
		checkButton.setOnAction(e -> checkBoard(primaryStage));
		hintButton.setOnAction(e -> giveHint());
		solveButton.setOnAction(e -> solveBoard());

		difficultyComboBox.setOnAction(e -> {
			String selectedDifficulty = difficultyComboBox.getValue();
			switch (selectedDifficulty) {
			case "Fácil":
				sudokuBoard.clearBoard();
				sudokuBoard.generateBoard();
				sudokuBoard.removeDigits(20);
				updateBoard();
				break;
			case "Médio":
				sudokuBoard.clearBoard();
				sudokuBoard.generateBoard();
				sudokuBoard.removeDigits(35);
				updateBoard();
				break;
			case "Difícil":
				sudokuBoard.clearBoard();
				sudokuBoard.generateBoard();
				sudokuBoard.removeDigits(45);
				updateBoard();
				break;
			}
		});

		buttonBox.getChildren().addAll(checkButton, hintButton, solveButton);
		
		HBox hbox = new HBox(difficultyComboBox);
		VBox vbox = new VBox(10,buttonBox, grid, hbox);
		vbox.setPadding(new Insets(10));

		Scene scene = new Scene(vbox, 450, 500);
		primaryStage.setTitle("Sudoku");
		primaryStage.setResizable(false);
		primaryStage.setScene(scene);
		primaryStage.show();
		
		showMessage("Teste suas habilidades lógicas!\n\nDificuldade padrão 'Fácil'", primaryStage);
	
		updateBoard();
	}

	private GridPane createGrid() {
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10));
		grid.setHgap(5);
		grid.setVgap(5);

		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				TextField cell = createNumberTextField();
				cell.setPrefSize(40, 40);
				cell.setStyle("-fx-alignment: center;");
				cells[i][j] = cell;
				grid.add(cell, j, i);
			}
		}

		return grid;
	}
	 private TextField createNumberTextField() {
	        TextField textField = new TextField();
	        
	        // Configuração para aceitar apenas um dígito numérico
	        textField.setTextFormatter(new TextFormatter<>(change -> {
	            if (change.getControlNewText().matches("[0-9]?")) {
	                return change;
	            }
	            return null;
	        }));
	      
	        return textField;
	    }

	private boolean checkBoard(Stage primaryStage) {
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
			showMessage("Tabuleiro esta correto!", primaryStage);
		} else {
			showMessage("Algo esta errado!", primaryStage);
		}
		return isCorrect;
	}

	// verificar, numero repetido
	private void giveHint() {
		boolean flag = false;
		for (int i = 0; i < SIZE && !flag; i++) {
			for (int j = 0; j < SIZE && !flag; j++) {
				String text = cells[i][j].getText();
				if (text.isEmpty()) {
					for (int num = 1; num <= SIZE; num++) {
						if (rules.isSafeToPlace(board, i, j, num)) {
							cells[i][j].setText(String.valueOf(num));
							cells[i][j].setStyle("-fx-background-color: lightgreen;");
							flag = true;
							break;
						}
					}
				}
			}
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
					cells[i][j].setStyle("-fx-background-color: white;");
				} else {
					cells[i][j].setText("");
					cells[i][j].setEditable(true);
					cells[i][j].setStyle("-fx-background-color: white;");
				}
			}
		}
	}

	private void showMessage(String message, Stage primaryStage) {
		Stage messageStage = new Stage();
		VBox vbox = new VBox(20);
		vbox.setPadding(new Insets(20));
		vbox.getChildren().add(new Label(message));
		Scene scene = new Scene(vbox, 250, 100);
		messageStage.initOwner(primaryStage);
		messageStage.initModality(Modality.WINDOW_MODAL);
		messageStage.setScene(scene);
		messageStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
