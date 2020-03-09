package sudoku;

import java.util.ArrayList;

public class Sudoku {
	private ArrayList<ArrayList<Integer>> board;
	
	
	
	public Sudoku() {
		board = new ArrayList<ArrayList<Integer>>();
		for (int i = 0; i < 9; i++) {
			board.add(new ArrayList<Integer>());
			for (int j = 0; j < 9; j++) {
				board.get(i).add(0);
			}
		}
	}
	
	public Integer getCell(int row, int col) {
		return board.get(row).get(col);
	}
	
	public void setCell(int row, int col, int value) {
		// soft error, change later
		if (value < 0 || value > 9) {
			System.out.println("Illegal value for cell: " + value);
			return;
		}
		board.get(row).set(col, value);
	}
	
	public void clearBoard() {
		for (ArrayList<Integer> row : board) {
			for (int i = 0; i < 9; i++) {
				row.set(i, 0);
			}
		}
	}
	
	public boolean solve() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (getCell(i, j) == 0) {
					for (int n = 1; n < 10; n++) {
						if (possible(i, j, n)) {
							setCell(i, j, n);
							if (solve()) {
								return true;
							}
							setCell(i, j, 0);
						}
					}
					return false;
				}
			}
		}
		return true;
	}
	
	public boolean possible(int row, int col, int value) {
		// check row
		for (int i = 0; i < 9; i++) {
			if (getCell(row, i) == value) {
				return false;
			}
		}
		// check col
		for (int i = 0; i < 9; i++) {
			if (getCell(i, col) == value) {
				return false;
			}
		}
		// check box
		int boxRow = (row / 3) * 3;
		int boxCol = (col / 3) * 3;
		
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (getCell(boxRow + i, boxCol + j) == value) {
					return false;
				}
			}
		}
		return true;
	}
	
	
	public void showBoard() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				Integer cell = getCell(i, j);
				if (cell == 0) {
					System.out.print("-");
				} else {
					System.out.print(cell);
				}
				if ((j + 1) % 3 == 0) {
					System.out.print(" ");
				}
			}
			System.out.println();
			if (((i + 1) % 3) == 0) {
				System.out.println();
			}
		}
	}
}
