package sudoku;
import javax.swing.*;
import javax.swing.border.Border;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class SudokuController {
	private JTextField[][] cells;
	private Sudoku sudoku;
	
	
	public SudokuController(Sudoku sudoku) {
		this.sudoku = sudoku;
		SwingUtilities.invokeLater(() -> createWindow(sudoku, "3p1c sud0ku pwn3r 9001", 800, 600));
	}
	
	public void createWindow(Sudoku sudoku, String title, int width, int height) {
		// initialize variables
		JFrame frame = new JFrame(title);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container pane = frame.getContentPane();
		Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
		Border gridBorder = BorderFactory.createLineBorder(Color.BLACK, 2);
		Font font = new Font("Courier", Font.BOLD, 25);
		JPanel gridPanel = new JPanel();
		gridPanel.setLayout(new GridLayout(3, 3));
		JPanel[][] subBoxes = new JPanel[3][3];
		
		// Create custom focus listener for text fields
		FocusListener highlighter = new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				Component field = e.getComponent();
				Color currentColor = field.getBackground();
				Color newColor = new Color(currentColor.getRed() - 25,
					currentColor.getGreen() - 25, currentColor.getBlue() - 25);
				field.setBackground(newColor);
				((JTextField) (field)).setCaretColor(newColor);
			}
			@Override
			public void focusLost(FocusEvent e) {
				Component field = e.getComponent();
				Color currentColor = field.getBackground();
				field.setBackground(new Color(currentColor.getRed() + 25,
					currentColor.getGreen() + 25, currentColor.getBlue() + 25));
			}
		};
		
		// Populate subBoxes array, used when coloring text fields
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				JPanel subBox = new JPanel();
				subBox.setLayout(new GridLayout(3, 3));
				subBox.setBorder(border);
				gridPanel.add(subBox);
				subBoxes[i][j] = subBox;
			}
		}
		
		// create text fields and populate the cells array
		cells = new JTextField[9][9];
		for (int i = 0; i < 9; i ++) {
			for (int j = 0; j < 9; j++) {
				JTextField cell = new JTextField();
				cell.setFont(font);
				cell.setHorizontalAlignment(JTextField.CENTER);
				cell.setBorder(border);
				cell.setPreferredSize(new Dimension(50, 50));
				cell.addKeyListener(new CellListener(this, i, j));
				cell.addFocusListener(highlighter);
				int subBoxRow = i / 3;
				int subBoxCol = j / 3;
				if (subBoxRow % 2 == 0 && subBoxCol % 2 == 1 ||
					subBoxRow % 2 == 1 && subBoxCol % 2 == 0) {
					cell.setBackground(Color.LIGHT_GRAY);
				}
				int cellContent = sudoku.getCell(i, j);
				String cellText = (cellContent == 0) ? null : Integer.toString(cellContent);
				cell.setText(cellText);
				subBoxes[subBoxRow][subBoxCol].add(cell);
				cells[i][j] = cell;
			}
		}

		gridPanel.setLayout(new GridLayout(3, 3));
		gridPanel.setBorder(gridBorder);
		
		// create the buttons
		JPanel buttonPanel = new JPanel();
		JButton clear = new JButton("Clear");
		clear.addActionListener(event -> {
			sudoku.clearBoard();
			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 9; j++) {
					cells[i][j].setText(null);
				}
			}
		});
		JButton randomize = new JButton("Randomize");
		randomize.addActionListener(event -> {
			int result = JOptionPane.showConfirmDialog(pane, "Randomizing could potentially take a LONG time. Proceed?",
				"Warning", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
			if (result == JOptionPane.OK_OPTION) {
				sudoku.randomize();
				for (int i = 0; i < 9; i++) {
					for (int j = 0; j < 9; j++) {
						int cellContent = sudoku.getCell(i, j);
						String cellText = (cellContent == 0) ? null : Integer.toString(cellContent);
						cells[i][j].setText(cellText);
					}
				}
			}
		});
		JButton solve = new JButton("Solve");
		solve.addActionListener(event -> {
			if (sudoku.solve()) {
				for (int i = 0; i < 9; i++) {
					for (int j = 0; j < 9; j++) {
						int cellContent = sudoku.getCell(i, j);
						String cellText = (cellContent == 0) ? null : Integer.toString(cellContent);
						cells[i][j].setText(cellText);
					}
				}
			} else {
				JOptionPane.showMessageDialog(pane, "No solutions found",
					"No Solution", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		buttonPanel.add(clear); buttonPanel.add(randomize); buttonPanel.add(solve);
		
		// Add panels to frame
		pane.setLayout(new BorderLayout());
		pane.add(gridPanel, BorderLayout.NORTH);
		pane.add(buttonPanel, BorderLayout.SOUTH);
		frame.pack();
		frame.setVisible(true);
		frame.setResizable(false);
	}
	
	public void setCell(int row, int col, int value) {
		sudoku.setCell(row, col, value);
	}
	
	public void moveFocus(int deltaRow, int deltaCol, int row, int col) {
		cells[(9 + row + deltaRow) % 9][(9 + col + deltaCol) % 9].requestFocus();
	}

}
