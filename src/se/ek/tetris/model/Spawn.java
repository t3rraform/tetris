package se.ek.tetris.model;

import java.awt.Color;
import java.util.Random;
import se.ek.tetris.framework.Game;
import se.ek.tetris.framework.Handler;

/**
 * @author Eric Karlsson
 */
public final class Spawn {

	private Handler handler;
	private Random r = new Random();
	private int nextIndex;

	private int[][] nextShape;
	private Color nextShapeColor;

	private int[][] holdShape;
	private Color holdShapeColor;

	private boolean holdShapeUsedOnce;
	private boolean holdShapeExist;

	private boolean rotatable;
	private boolean holdShapeRotatable;

	private final int[][] LINE_SHAPE = { { 0, 0 }, { 1, 0 }, { 2, 0 }, { 3, 0 } };
	private final int[][] BOX_SHAPE = { { 0, 0 }, { 1, 0 }, { 0, 1 }, { 1, 1 } };
	private final int[][] J_SHAPE = { { 0, 0 }, { 1, 1 }, { 0, 1 }, { 2, 1 } };
	private final int[][] L_SHAPE = { { 0, 1 }, { 1, 1 }, { 2, 1 }, { 2, 0 } };
	private final int[][] S_SHAPE = { { 0, 1 }, { 1, 1 }, { 1, 0 }, { 2, 0 } };
	private final int[][] Z_SHAPE = { { 0, 0 }, { 1, 0 }, { 1, 1 }, { 2, 1 } };
	private final int[][] T_SHAPE = { { 0, 1 }, { 1, 1 }, { 1, 0 }, { 2, 1 } };
	private final int[][][] ALL_SHAPES = { LINE_SHAPE, BOX_SHAPE, J_SHAPE, L_SHAPE, S_SHAPE, Z_SHAPE, T_SHAPE };
	private final Color[] ALL_COLORS = { Color.cyan, Color.yellow, Color.blue, Color.orange, Color.green, Color.red,
			Color.magenta };

	public Spawn(Handler handler) {
		this.handler = handler;
	}

	public void spawnShape() {
		handler.addObject(new Shape(60, 0, Game.cloneArray(ALL_SHAPES[nextIndex]), ALL_COLORS[nextIndex], ID.Shape, this, handler, rotatable));
	}

	public void spawnHoldShape() {
		handler.addObject(new Shape(60, 0, Game.cloneArray(holdShape), holdShapeColor, ID.Shape, this, handler, holdShapeRotatable));
	}

	public void setNextShape() {
		nextIndex = r.nextInt(ALL_SHAPES.length);
		nextShape = ALL_SHAPES[nextIndex];
		nextShapeColor = ALL_COLORS[nextIndex];
		if (nextIndex == 1) {
			rotatable = false;
		} else {
			rotatable = true;
		}
	}

	public void setHoldShape(int[][] selectedShape, Color selectedColor) {
		holdShape = selectedShape;
		holdShapeColor = selectedColor;
	}

	public int[][] getNextShape() {
		return nextShape;
	}

	public Color getNextShapeColor() {
		return nextShapeColor;
	}

	public int[][] getHoldShape() {
		return holdShape;
	}

	public Color getHoldShapeColor() {
		return holdShapeColor;
	}

	public boolean isHoldShapeUsedOnce() {
		return holdShapeUsedOnce;
	}

	public void setHoldShapeUsedOnce(boolean holdShapeUsedOnce) {
		this.holdShapeUsedOnce = holdShapeUsedOnce;
	}

	public boolean doesHoldShapeExist() {
		return holdShapeExist;
	}

	public void setHoldShapeExist(boolean holdShapeExist) {
		this.holdShapeExist = holdShapeExist;
	}

	public boolean isHoldShapeRotatable() {
		return holdShapeRotatable;
	}

	public void setHoldShapeRotatable(boolean holdShapeRotatable) {
		this.holdShapeRotatable = holdShapeRotatable;
	}

}
