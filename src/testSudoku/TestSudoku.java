package testSudoku;

import static org.junit.Assert.*;

import org.junit.*;
import sudoku.Sudoku;
import java.util.Random;

public class TestSudoku {
	private Sudoku mySudoku;
	
	@Before
	public void SetUp() throws Exception {
		mySudoku = new Sudoku();
	}
	
	@After
	public void TearDown() throws Exception {
		mySudoku = null;
	}
	
	@Test
	public void testConstructor() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				assertEquals("Sudoku should be empty after initialization", 0, mySudoku.getCell(i, j));
			}
		}
	}
	
	@Test
	public void testSetCell() {
		Random r = new Random();
		int randomRow = r.nextInt(9);
		int randomCol = r.nextInt(9);
		mySudoku.setCell(randomRow, randomCol, 9);
		assertEquals("Unexpected value after setting", 9, mySudoku.getCell(randomRow, randomCol));
		mySudoku.setCell(randomRow, randomCol, 2);
		assertEquals("Unexpected value after setting", 2, mySudoku.getCell(randomRow, randomCol));
		try {
			mySudoku.setCell(randomRow, randomCol, -1);
			fail("Cells should not be able to be populated by negative numbers");
		} catch (IllegalArgumentException e) {
			// passed
		}
		try {
			mySudoku.setCell(randomRow, randomCol, 10);
			fail("Cells should not be able to be populated by numbers larger than 9");
		} catch (IllegalArgumentException e) {
			// passed
		}
	}
	
	@Test
	public void testClearBoard() {
		Random r = new Random();
		for (int i = 0; i < 30; i++) {
			mySudoku.setCell(r.nextInt(9), r.nextInt(9), r.nextInt(9) + 1);
		}
		mySudoku.clearBoard();
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				assertEquals("Cells should be empty after clearing the board", 0, mySudoku.getCell(i, j));
			}
		}
	}
	
	@Test
	public void testSolve() {
		assertTrue("Solver should produce a solution from empty board", mySudoku.solve());

		mySudoku.clearBoard();
		mySudoku.setCell(0, 0, 5);
		mySudoku.setCell(0, 5, 5);
		assertFalse("Solver should not produce a solution with row collision", mySudoku.solve());

		mySudoku.clearBoard();
		mySudoku.setCell(0, 0, 5);
		mySudoku.setCell(5, 0, 5);
		assertFalse("Solver should not produce a solution with column collision", mySudoku.solve());
		
		mySudoku.clearBoard();
		mySudoku.setCell(0, 0, 5);
		mySudoku.setCell(1, 1, 5);
		assertFalse("Solver should not produce a solution with sub box collision", mySudoku.solve());
		
		mySudoku.clearBoard();
		mySudoku.setCell(0, 0, 1);
		mySudoku.setCell(0, 1, 2);
		mySudoku.setCell(0, 2, 3);
		mySudoku.setCell(1, 0, 4);
		mySudoku.setCell(1, 1, 5);
		mySudoku.setCell(1, 2, 6);
		mySudoku.setCell(2, 3, 7);
		assertFalse("Solver should not produce a solution with the current cells", mySudoku.solve());
		
		mySudoku.setCell(2, 3, 0);
		assertTrue("Solver should produce a solution with the current cells", mySudoku.solve());
		
		mySudoku.clearBoard();
		mySudoku.setCell(0, 2, 8); mySudoku.setCell(0, 5, 9); mySudoku.setCell(0, 7, 6);
		mySudoku.setCell(0, 8, 2); mySudoku.setCell(1, 8, 5); mySudoku.setCell(2, 0, 1);
		mySudoku.setCell(2, 2, 2); mySudoku.setCell(2, 3, 5); mySudoku.setCell(3, 3, 2);
		mySudoku.setCell(3, 4, 1); mySudoku.setCell(3, 7, 9); mySudoku.setCell(4, 1, 5);
		mySudoku.setCell(4, 6, 6); mySudoku.setCell(5, 0, 6); mySudoku.setCell(5, 7, 2);
		mySudoku.setCell(5, 8, 8); mySudoku.setCell(6, 0, 4); mySudoku.setCell(6, 1, 1);
		mySudoku.setCell(6, 3, 6); mySudoku.setCell(6, 5, 8); mySudoku.setCell(7, 0, 8);
		mySudoku.setCell(7, 1, 6); mySudoku.setCell(7, 4, 3); mySudoku.setCell(7, 6, 1);
		mySudoku.setCell(8, 6, 4);
		assertTrue("Solver should produce a solution with the current cells", mySudoku.solve());
		
	}
	
	@Test
	public void testRandomize() {
		mySudoku.randomize();
		int nbrOfCells = 0;
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (mySudoku.getCell(i, j) != 0) {
					nbrOfCells++;
				}
			}
		}
		assertEquals("Randomize without parameters should produce sudoku with 20 prefilled cells", 20, nbrOfCells);
		assertTrue("Randomized sudoku should be solvable", mySudoku.solve());
		
		mySudoku.clearBoard();
		try {
			mySudoku.randomize(-1);
			fail("Randomize should not be able to be called with negative number of cells");
		} catch (IllegalArgumentException e) {
			// passed
		}
		try {
			mySudoku.randomize(200);
			fail("Randomize should not be able to be called with number of cells exceeding 81");
		} catch (IllegalArgumentException e) {
			// passed
		}
	}
	
}
