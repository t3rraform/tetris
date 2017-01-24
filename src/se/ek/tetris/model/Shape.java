package se.ek.tetris.model;

import java.awt.Color;
import java.awt.Graphics;

import se.ek.tetris.framework.Game;
import se.ek.tetris.framework.Handler;

/**
 * @author Eric Karlsson
 */
public final class Shape extends GameObject {

	private Spawn spawn;

	private boolean isActive = true;
	private boolean spawnNew = false;
	private boolean gameOver = false;
	private double baseInterval = Level.getBaseInterval();
	private double interval = baseInterval;
	private double timer = 0;
	private final int SQUARE_SIZE = Game.SQUARE_SIZE;
	private Handler handler;

	private int[][] selectedShape;
	private Color selectedColor;

	private double lockTime = 0;
	private double maxLockTime = Level.getMaxLockTime();
	private boolean rotatable;

	public Shape(float x, float y, int[][] shape, Color color, ID id, Spawn spawn, Handler handler, boolean rotatable) {
		super(x, y, id);
		this.spawn = spawn;
		this.handler = handler;
		this.selectedShape = shape;
		this.selectedColor = color;
		this.rotatable = rotatable;
		velY = 20;
	}

	@Override
	public void tick() {

		if (isActive) {

			if (checkCollision()) {
				velY = 0;
				if (lockTime >= maxLockTime) {
					lockShape();
				}
				lockTime++;
			} else {
				velY = 20;
			}

			if (!gameOver) {

				if (timer >= interval) {
					y += velY;
					timer = 0;
				}
				timer++;
			}
		}
	}

	public void move(int moveX) {

		if (canMove(moveX)) {
			x += moveX;
		}
	}

	private boolean canMove(int moveX) {

		int nextMoveX = (int) (moveX + x);

		int[][] board = Board.getBoard();

		for (int i = 0; i < selectedShape.length; i++) {

			int pieceX = (int) (selectedShape[i][0] * SQUARE_SIZE + nextMoveX);
			int pieceY = (int) (selectedShape[i][1] * SQUARE_SIZE + y);

			if (pieceX < 0 || pieceX > Board.GRID_WIDTH - SQUARE_SIZE || pieceY < 0
					|| pieceY > Board.GRID_HEIGHT - SQUARE_SIZE) {
				return false;
			} else if (board[pieceX / SQUARE_SIZE][pieceY / SQUARE_SIZE] == 1) {
				return false;
			}

		}
		return true;
	}

	public void rotate(double angle) {

		boolean canRotate = false;

		if (canRotateWithinBounds(angle)) {
			if (canRotateWithinShapes(angle)) {
				canRotate = true;
			}
		} else {
			int moveCount = rotationBlocksOutOfBoundsCount(angle);

			if (wallKick(moveCount, angle)) {
				x += moveCount;
				canRotate = true;
			}
		}

		if (canRotate && rotatable) {

			int centerX = selectedShape[1][0];
			int centerY = selectedShape[1][1];

			for (int i = 0; i < selectedShape.length; i++) {
				int pieceX = selectedShape[i][0] - centerX;
				int pieceY = selectedShape[i][1] - centerY;

				int newX = (int) Math.round(pieceX * Math.cos(angle) - pieceY * Math.sin(angle) + centerX);
				int newY = (int) Math.round(pieceX * Math.sin(angle) + pieceY * Math.cos(angle) + centerY);

				selectedShape[i][0] = newX;
				selectedShape[i][1] = newY;
			}
		}
	}

	private boolean wallKick(int tempX, double angle) {

		int[][] board = Board.getBoard();

		int centerX = selectedShape[1][0];
		int centerY = selectedShape[1][1];

		for (int i = 0; i < selectedShape.length; i++) {
			int pieceX = selectedShape[i][0] - centerX;
			int pieceY = selectedShape[i][1] - centerY;

			int newX = (int) Math.round(pieceX * Math.cos(angle) - pieceY * Math.sin(angle) + centerX);
			int newY = (int) Math.round(pieceX * Math.sin(angle) + pieceY * Math.cos(angle) + centerY);
			int newXCord = (int) (newX + (x + tempX) / SQUARE_SIZE);
			int newYCord = (int) (newY + y / SQUARE_SIZE);

			if (newYCord < 0 || newYCord >= Board.ROWS) {
				return false;
			}

			if (board[newXCord][newYCord] == 1) {
				return false;
			}
		}

		return true;
	}

	private int rotationBlocksOutOfBoundsCount(double angle) {

		int stepsOutOfBoundaries = 0;

		int centerX = selectedShape[1][0];
		int centerY = selectedShape[1][1];

		for (int i = 0; i < selectedShape.length; i++) {
			int pieceX = selectedShape[i][0] - centerX;
			int pieceY = selectedShape[i][1] - centerY;

			int newX = (int) Math.round(pieceX * Math.cos(angle) - pieceY * Math.sin(angle) + centerX);
			int newY = (int) Math.round(pieceX * Math.sin(angle) + pieceY * Math.cos(angle) + centerY);
			int newXCord = (int) (newX + x / SQUARE_SIZE);
			int newYCord = (int) (newY + y / SQUARE_SIZE);

			if (newXCord < 0) {
				stepsOutOfBoundaries += SQUARE_SIZE;
			}
			if (newXCord >= Board.COLS) {
				stepsOutOfBoundaries -= SQUARE_SIZE;
			}
		}

		return stepsOutOfBoundaries;
	}

	private boolean canRotateWithinBounds(double angle) {

		int centerX = selectedShape[1][0];
		int centerY = selectedShape[1][1];

		for (int i = 0; i < selectedShape.length; i++) {
			int pieceX = selectedShape[i][0] - centerX;
			int pieceY = selectedShape[i][1] - centerY;

			int newX = (int) Math.round(pieceX * Math.cos(angle) - pieceY * Math.sin(angle) + centerX);
			int newY = (int) Math.round(pieceX * Math.sin(angle) + pieceY * Math.cos(angle) + centerY);
			int newXCord = (int) (newX + x / SQUARE_SIZE);
			int newYCord = (int) (newY + y / SQUARE_SIZE);

			// Will only return true if within boundaries and no block collision
			if (newXCord < 0 || newXCord > (Board.GRID_WIDTH - SQUARE_SIZE) / SQUARE_SIZE || newYCord < 0
					|| newYCord > (Board.GRID_HEIGHT - SQUARE_SIZE) / SQUARE_SIZE) {
				return false;
			}
		}

		return true;
	}

	private boolean canRotateWithinShapes(double angle) {

		int[][] board = Board.getBoard();

		int centerX = selectedShape[1][0];
		int centerY = selectedShape[1][1];

		for (int i = 0; i < selectedShape.length; i++) {
			int pieceX = selectedShape[i][0] - centerX;
			int pieceY = selectedShape[i][1] - centerY;

			int newX = (int) Math.round(pieceX * Math.cos(angle) - pieceY * Math.sin(angle) + centerX);
			int newY = (int) Math.round(pieceX * Math.sin(angle) + pieceY * Math.cos(angle) + centerY);
			int newXCord = (int) (newX + x / SQUARE_SIZE);
			int newYCord = (int) (newY + y / SQUARE_SIZE);

			if (board[newXCord][newYCord] == 1) {
				return false;
			}

		}
		return true;
	}

	private boolean checkCollision() {

		int[][] board = Board.getBoard();

		for (int i = 0; i < selectedShape.length; i++) {
			int pieceX = (int) (selectedShape[i][0] + x / SQUARE_SIZE);
			int pieceY = (int) (selectedShape[i][1] + y / SQUARE_SIZE);

			if (pieceY + 1 >= Board.ROWS || board[pieceX][pieceY + 1] == 1) {
				return true;
			}
		}
		return false;
	}

	private void lockShape() {

		velY = 0;
		int[][] board = Board.getBoard();
		for (int i = 0; i < selectedShape.length; i++) {
			int pieceXBoard = (int) x / SQUARE_SIZE + selectedShape[i][0];
			int pieceYBoard = (int) y / SQUARE_SIZE + selectedShape[i][1];

			if (pieceYBoard <= 0) {
				gameOver = true;
			}
			if (pieceYBoard >= 0 && isActive) {
				board[pieceXBoard][pieceYBoard] = 1;
			}

			if (!spawnNew && !gameOver) {
				spawn.spawnShape();
				spawn.setNextShape();
				spawn.setHoldShapeUsedOnce(false);
				spawnNew = true;
			}
		}
		isActive = false;
	}

	public void holdShape() {

		if (!spawn.isHoldShapeUsedOnce()) {

			if (spawn.doesHoldShapeExist()) {
				// Exchange hold
				spawn.spawnHoldShape();
				spawn.setHoldShape(selectedShape, selectedColor);
				spawn.setHoldShapeRotatable(rotatable);
			} else {
				// Set and spawn next
				spawn.setHoldShape(selectedShape, selectedColor);
				spawn.setHoldShapeExist(true);
				spawn.setHoldShapeRotatable(rotatable);
				spawn.spawnShape();
				spawn.setNextShape();
			}
			spawn.setHoldShapeUsedOnce(true);
			handler.removeObject(this);
		}

	}

	@Override
	public void render(Graphics g) {

		g.setColor(selectedColor);

		for (int i = 0; i < selectedShape.length; i++) {
			g.fillRect((int) x + selectedShape[i][0] * SQUARE_SIZE, (int) y + selectedShape[i][1] * SQUARE_SIZE,
					SQUARE_SIZE, SQUARE_SIZE);
		}
	}

	public boolean isActive() {
		return isActive;
	}

	public int[][] getSelectedShape() {
		return selectedShape;
	}

	public void setSelectedShape(int[][] selectedShape) {
		this.selectedShape = selectedShape;
	}

	public double getBaseInterval() {
		return baseInterval;
	}

	public void setBaseInterval(double baseInterval) {
		this.baseInterval = baseInterval;
	}

	public double getInterval() {
		return interval;
	}

	public void setInterval(double interval) {
		this.interval = interval;
	}

	public double getLockTime() {
		return lockTime;
	}

	public void setLockTime(double lockTime) {
		this.lockTime = lockTime;
	}

	public double getMaxLockTime() {
		return maxLockTime;
	}

	public void setMAX_LOCKTIME(int maxLockTime) {
		this.maxLockTime = maxLockTime;
	}
	
}
