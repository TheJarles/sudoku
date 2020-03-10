package sudoku;

public class Main {
	public static void main(String[] args) {
		Sudoku board = new Sudoku();
		SudokuController controller = new SudokuController(board);
	}
}
