package sudoku;

import java.util.Random;

public class Main {
	public static void main(String[] args) {
		Sudoku board = new Sudoku();
		Random r = new Random();
		board.showBoard();
		System.out.println();
		
		// set 20 random values on the board
		int placed = 0;
		while (placed < 20) {
			int row = r.nextInt(9);
			int col = r.nextInt(9);
			int value = r.nextInt(9) + 1;
			if (board.possible(row, col, value)) {
				board.setCell(row, col, value);
				placed++;
			}
		}
		
		board.showBoard();
//		System.out.println();
//		board.clearBoard();
//		board.showBoard();
		System.out.println();
		board.solve();
		board.showBoard();
		
	}
}
