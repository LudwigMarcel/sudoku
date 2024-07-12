package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.entities.SudokuGenerator;
import model.entities.SudokuSolver;

public class SudokuApp extends Application {
    private int[][] board;
    private SudokuGenerator generator = new SudokuGenerator();
    private SudokuSolver solver = new SudokuSolver();

    @Override
    public void start(Stage primaryStage) {
        GridPane grid = new GridPane();
        // Crie a grade 9x9 com TextFields ou Labels

        VBox vbox = new VBox();
        Button newGameButton = new Button("Novo Jogo");
        Button checkButton = new Button("Verificar");
        Button hintButton = new Button("Sugestão");
        Button solveButton = new Button("Resolver");

        vbox.getChildren().addAll(newGameButton, checkButton, hintButton, solveButton);

        Scene scene = new Scene(new VBox(grid, vbox), 300, 400);
        primaryStage.setTitle("Sudoku");
        primaryStage.setScene(scene);
        primaryStage.show();

        newGameButton.setOnAction(e -> startNewGame());
        checkButton.setOnAction(e -> checkBoard());
        hintButton.setOnAction(e -> giveHint());
        solveButton.setOnAction(e -> solveBoard());
    }

    private void startNewGame() {
        board = generator.getBoard();
        // Atualize a interface com o novo tabuleiro
    }

    private void checkBoard() {
        // Verifique se o tabuleiro está correto
    }

    private void giveHint() {
        int[] hint = solver.getHint(board);
        if (hint != null) {
            int row = hint[0];
            int col = hint[1];
            int num = hint[2];
            // Mostre a sugestão na interface
        }
    }

    private void solveBoard() {
        solver.solve(board);
        // Atualize a interface com a solução
    }

    public static void main(String[] args) {
        launch(args);
    }
}
