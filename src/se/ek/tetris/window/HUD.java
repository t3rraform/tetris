package se.ek.tetris.window;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import se.ek.tetris.framework.Game;
import se.ek.tetris.model.Level;
import se.ek.tetris.model.Spawn;

/**
 * @author Eric Karlsson
 */
public final class HUD {

	private final int SQUARE_SIZE = Game.SQUARE_SIZE;

	private Level level;
	private Spawn spawn;

	public HUD(Level level, Spawn spawn) {
		this.level = level;
		this.spawn = spawn;
	}

	public void tick() {

	}

	public void render(Graphics g) {

		Font font = new Font("arial", 1, 18);
		g.setFont(font);
		g.setColor(Color.white);

		g.drawString("Level: " + level.getLevel(), 240, 50);
		g.drawString("Score: " + level.getScore(), 240, 80);

		g.drawString("Next:", 240, 130);

		for (int i = 0; i < spawn.getNextShape().length; i++) {

			g.setColor(spawn.getNextShapeColor());
			g.fillRect((int) 240 + spawn.getNextShape()[i][0] * SQUARE_SIZE, (int) 160 + spawn.getNextShape()[i][1] * SQUARE_SIZE,
					SQUARE_SIZE, SQUARE_SIZE);

			g.setColor(Color.gray);
			g.drawRect((int) 240 + spawn.getNextShape()[i][0] * SQUARE_SIZE, (int) 160 + spawn.getNextShape()[i][1] * SQUARE_SIZE,
					SQUARE_SIZE, SQUARE_SIZE);
		}

		g.setColor(Color.white);
		g.drawString("Hold:", 240, 270);

		if (spawn.getHoldShape() != null) {
			for (int i = 0; i < spawn.getHoldShape().length; i++) {

				g.setColor(spawn.getHoldShapeColor());
				g.fillRect((int) 240 + spawn.getHoldShape()[i][0] * SQUARE_SIZE, (int) 300 + spawn.getHoldShape()[i][1] * SQUARE_SIZE,
						SQUARE_SIZE, SQUARE_SIZE);

				g.setColor(Color.gray);
				g.drawRect((int) 240 + spawn.getHoldShape()[i][0] * SQUARE_SIZE, (int) 300 + spawn.getHoldShape()[i][1] * SQUARE_SIZE,
						SQUARE_SIZE, SQUARE_SIZE);
			}
		}

	}


}
