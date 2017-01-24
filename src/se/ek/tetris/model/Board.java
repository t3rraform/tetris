package se.ek.tetris.model;

import java.awt.Color;
import java.awt.Graphics;
import se.ek.tetris.framework.Game;
import se.ek.tetris.framework.Handler;

/**
 * @author Eric Karlsson
 */
public final class Board {

	public static final int COLS = 10;
	public static final int ROWS = 20;
	public static final int SQUARE_SIZE = Game.SQUARE_SIZE;
	public static final int GRID_WIDTH = COLS * SQUARE_SIZE, GRID_HEIGHT = ROWS * SQUARE_SIZE;
	private static int[][] board = new int[COLS][ROWS];

	private Handler handler;
	private Level level;

	public Board(Handler handler, Level level) {
		this.handler = handler;
		this.level = level;
		initialize();
	}

	public void tick() {
		checkLines();
	}

	public void render(Graphics g) {
		
		for (int i = 0; i < COLS; i++) {
			for (int j = 0; j < ROWS; j++) {

				g.setColor(Color.gray);
				g.drawRect(i * SQUARE_SIZE, j * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
			}
		}

	}

	private void initialize() {
		for (int i = 0; i < COLS; i++) {
			for (int j = 0; j < ROWS; j++) {
				board[i][j] = 0;
			}
		}
	}

	private void checkLines() {
		int comboCount = 0;
		int[] comboCountArray = new int[ROWS];

		for (int y = 0; y < ROWS; y++) {
			for (int x = 0; x < COLS; x++) {

				if (board[x][y] == 1) {
					comboCount++;
				}
				if (x == COLS - 1) {
					comboCountArray[y] = comboCount;
					comboCount = 0;
				}
			}
		}

		clearLine(comboCountArray);
	}

	private void clearLine(int[] comboCountArray) {

		for (int y = 0; y < ROWS; y++) {

			if (comboCountArray[y] == COLS) {

				for (int x = 0; x < COLS; x++) {

					removeBlocksFromRow(x, y);
					board[x][y] = 0;
				}
				level.addScore();
				moveAllShapesDown(y);
				break;
			}
		}
	}

	private void removeBlocksFromRow(int x, int y) {

		for (int i = 0; i < handler.object.size(); i++) {
			Shape shape = (Shape) handler.object.get(i);

			int[][] selectedShape = shape.getSelectedShape();

			for (int j = 0; j < selectedShape.length; j++) {
				int pieceX = (int) (selectedShape[j][0] + (shape.getX() / SQUARE_SIZE));
				int pieceY = (int) (selectedShape[j][1] + (shape.getY() / SQUARE_SIZE));

				if (pieceX == x && pieceY == y) {

					if (selectedShape.length > 1) {
						selectedShape = RemoveOneBlockFromShape(selectedShape, j);
					} else {
						handler.removeObject(shape);
					}
				}
			}
			shape.setSelectedShape(selectedShape);

		}

	}

	private int[][] RemoveOneBlockFromShape(int[][] selectedShape, int removeBlock) {

		int[][] newArray = new int[selectedShape.length - 1][2];

		int k = 0;
		for (int i = 0; i < selectedShape.length; i++) {

			if (i == removeBlock) {
				continue;
			}
			k++;

			for (int j = 0; j < selectedShape[0].length; j++) {
				newArray[k - 1][j] = selectedShape[i][j];
			}
		}
		return newArray;
	}

	private void moveAllShapesDown(int clearedRow) {

		for (int i = 0; i < handler.object.size(); i++) {
			Shape shape = (Shape) handler.object.get(i);

			if (!shape.isActive()) {
				int[][] selectedShape = shape.getSelectedShape();
				for (int j = 0; j < selectedShape.length; j++) {

					int pieceX = (int) (selectedShape[j][0] + (shape.getX() / SQUARE_SIZE));
					int pieceY = (int) (selectedShape[j][1] + (shape.getY() / SQUARE_SIZE));

					if (pieceY < clearedRow) {
						board[pieceX][pieceY] = 0;
					}

				}
				for (int j = 0; j < selectedShape.length; j++) {

					int pieceX = (int) (selectedShape[j][0] + (shape.getX() / SQUARE_SIZE));
					int pieceY = (int) (selectedShape[j][1] + (shape.getY() / SQUARE_SIZE));

					if (pieceY < clearedRow) {
						board[pieceX][pieceY + 1] = 1;

						selectedShape[j][1] = selectedShape[j][1] + 1;
					}
				}
				shape.setSelectedShape(selectedShape);
			}

		}
	}

	public static int[][] getBoard() {
		return board;
	}
}
