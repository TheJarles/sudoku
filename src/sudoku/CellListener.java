package sudoku;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JTextField;


public class CellListener implements KeyListener {
	private SudokuController controller;
	private int row;
	private int col;
	
	public CellListener(SudokuController controller, int row, int col) {
		this.controller = controller;
		this.row = row;
		this.col = col;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		char c = e.getKeyChar();
		switch(c) {
			case '1':
				((JTextField) (e.getSource())).setText("1");
				controller.setCell(row, col, 1);
				e.consume();
				break;
			case '2':
				((JTextField) (e.getSource())).setText("2");
				controller.setCell(row, col, 2);
				e.consume();
				break;
			case '3':
				((JTextField) (e.getSource())).setText("3");
				controller.setCell(row, col, 3);
				e.consume();
				break;
			case '4':
				((JTextField) (e.getSource())).setText("4");
				controller.setCell(row, col, 4);
				e.consume();
				break;
			case '5':
				((JTextField) (e.getSource())).setText("5");
				controller.setCell(row, col, 5);
				e.consume();
				break;
			case '6':
				((JTextField) (e.getSource())).setText("6");
				controller.setCell(row, col, 6);
				e.consume();
				break;
			case '7':
				((JTextField) (e.getSource())).setText("7");
				controller.setCell(row, col, 7);
				e.consume();
				break;
			case '8':
				((JTextField) (e.getSource())).setText("8");
				controller.setCell(row, col, 8);
				e.consume();
				break;
			case '9':
				((JTextField) (e.getSource())).setText("9");
				controller.setCell(row, col, 9);
				e.consume();
				break;
			default:
				e.consume();
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int direction = e.getExtendedKeyCode();
		switch (direction) {
			case KeyEvent.VK_UP:
				controller.moveFocus(-1, 0, row, col);
				break;
			case KeyEvent.VK_DOWN:
				controller.moveFocus(1, 0, row, col);
				break;
			case KeyEvent.VK_LEFT:
				controller.moveFocus(0, -1, row, col);
				break;
			case KeyEvent.VK_RIGHT:
				controller.moveFocus(0, 1, row, col);
				break;
			case KeyEvent.VK_DELETE:
			case KeyEvent.VK_BACK_SPACE:
				((JTextField) (e.getSource())).setText(null);
				controller.setCell(row, col, 0);
			default:
				e.consume();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
