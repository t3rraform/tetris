package se.ek.tetris.window;

import java.awt.Dimension;
import javax.swing.JFrame;
import se.ek.tetris.framework.Game;
/**
 * @author Eric Karlsson
 */
public final class Window {

	public Window(int width, int height, String title, Game game) {

		JFrame frame = new JFrame(title);
		frame.setPreferredSize(new Dimension(width, height));
		frame.setMaximumSize(new Dimension(width, height));
		frame.setMinimumSize(new Dimension(width, height));
		frame.setVisible(true);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.add(game);
		game.start();
	}
}