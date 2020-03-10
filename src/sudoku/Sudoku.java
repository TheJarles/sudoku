package sudoku;

import java.util.ArrayList;

/**
 * Sudoku represents a sudoku grid and has methods for generating, modifying
 * and solving games.
 * 
 * @author Johannes Skogman
 *
 */

public class Sudoku {
	private ArrayList<ArrayList<Integer>> board;

	/**
	 * Class constructor. Initializes the board with empty cells.
	 */
	public Sudoku() {
		board = new ArrayList<ArrayList<Integer>>();
		for (int i = 0; i < 9; i++) {
			board.add(new ArrayList<Integer>());
			for (int j = 0; j < 9; j++) {
				board.get(i).add(0);
			}
		}
	}
	
	/**
	 * Getter method for accessing specific cells on the board.
	 * 
	 * @param row the row of the cell
	 * @param col the column of the cell
	 * @return the value of the cell at (row, col) on the board
	 */
	public int getCell(int row, int col) {
		return board.get(row).get(col);
	}
	
	/**
	 * Setter method for setting the value of a cell.
	 * 
	 * @param row the row of the cell
	 * @param col the column of the cell
	 * @param value the value assigned to the cell
	 */
	public void setCell(int row, int col, int value) {
		if (value < 0 || value > 9) {
			throw new IllegalArgumentException("Illegal value for cell: " + value);
		}
		board.get(row).set(col, value);
	}
	
	/**
	 * Method for clearing all cell values on the board.
	 */
	public void clearBoard() {
		for (ArrayList<Integer> row : board) {
			for (int i = 0; i < 9; i++) {
				row.set(i, 0);
			}
		}
	}
	
	/**
	 * Generates a random, solvable configuration of the board.
	 * This method can potentially take a long time to execute.
	 * 
	 * @param nbrOfCells the number of non-empty cells left on the board
	 */
	public void randomize(int nbrOfCells) {
		if (nbrOfCells < 0 || nbrOfCells > 81) {
			throw new IllegalArgumentException("Illegal number of cells: " + nbrOfCells);
		}
		clearBoard();
		int placed = 0;
		// seed the board with 20 random values
		while (placed < 20) {
			java.util.Random r = new java.util.Random();
			int row = r.nextInt(9);
			int col = r.nextInt(9);
			int value = r.nextInt(9) + 1;
			if (possible(row, col, value) && getCell(row, col) == 0) {
				setCell(row, col, value);
				placed++;
			}
		}
		/* if solvable, remove random values from solution until nbrOfCells remain
		 * otherwise restart
		 */
		if (!solve()) {
			randomize(nbrOfCells);
		} else {
			int removed = 0;
			while (removed < 81 - nbrOfCells) {
				java.util.Random r = new java.util.Random();
				int row = r.nextInt(9);
				int col = r.nextInt(9);
				if(getCell(row, col) != 0) {
					setCell(row, col, 0);
					removed++;
				}
			}
		}
	}
	
	/**
	 * Generates a random, solvable configuration of the board with 20 non-empty cells.
	 * See {@link randomize(int)}.
	 */
	public void randomize() {
		randomize(20);
	}
	
	/**
	 * Generates a solution for the board if a solution exists, otherwise leaves
	 * the board as is. The solver can potentially take a long time to execute.
	 * 
	 * @return boolean value indicating if a solution exists or not
	 */
	public boolean solve() {
		if (solvable()) {
			return solve(0, 0);
		} else {
			return false;
		}
	}
	/*
	 * Helper method for checking if the current board state is solvable
	 * according to the rules of sudoku.
	 */
	private boolean solvable() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				int currentValue = getCell(i, j);
				if (currentValue != 0) {
					setCell(i, j, 0);
					if (!possible(i, j, currentValue)) {
						setCell(i, j, currentValue);
						return false;
					}
					setCell(i, j, currentValue);
				}
			}
		}
		return true;
	}

	private boolean solve(int r, int c) {
		if (c == 9) {
			return solve(r + 1, 0);
		}
		if(r == 9) {
			return true;
		}
		if (getCell(r, c) != 0) {
			return solve(r, c + 1);
		}
		for (int n = 1; n < 10; n++) {
			if (possible(r, c, n) && getCell(r, c) == 0) {
				setCell(r, c, n);
				if (solve(r, c + 1)) {
					return true;
				}
				setCell(r, c, 0);
			}
		}
		return false;
	}
	
	private boolean possible(int row, int col, int value) {
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
	
	/**
	 * Prints the current board state to the console. Empty cells are
	 * represented by "-".
	 */
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
