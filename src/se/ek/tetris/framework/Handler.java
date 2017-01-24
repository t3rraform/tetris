package se.ek.tetris.framework;

import java.awt.Graphics;
import java.util.LinkedList;

import se.ek.tetris.model.GameObject;
/**
 * @author Eric Karlsson
 */
public class Handler {

	public LinkedList<GameObject> object = new LinkedList<>();

	public void tick() {
		for (int i = 0; i < object.size(); i++) {
			object.get(i).tick();
		}
	}

	public void render(Graphics g) {
		for (int i = 0; i < object.size(); i++) {
			object.get(i).render(g);
		}
	}

	public void addObject(GameObject object) {
		this.object.add(object);
	}

	public void removeObject(GameObject object) {
		this.object.remove(object);
	}

}
