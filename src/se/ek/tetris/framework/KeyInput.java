package se.ek.tetris.framework;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import se.ek.tetris.model.ID;
import se.ek.tetris.model.Shape;
/**
 * @author Eric Karlsson
 */
public final class KeyInput extends KeyAdapter {

	private Handler handler;

	public KeyInput(Handler handler) {
		this.handler = handler;
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		for (int i = 0; i < handler.object.size(); i++) {
            Shape tempObject = (Shape) handler.object.get(i);
            if(tempObject.getId() == ID.Shape && tempObject.isActive()){
            	if(key == KeyEvent.VK_LEFT) tempObject.move(-20);
    			if(key == KeyEvent.VK_RIGHT) tempObject.move(20);
    			if(key == KeyEvent.VK_DOWN) tempObject.setInterval(tempObject.getBaseInterval() * 0.5);
    			if(key == KeyEvent.VK_X) tempObject.rotate(Math.PI / 2);
    			if(key == KeyEvent.VK_Z) tempObject.rotate(Math.PI / 2 * -1);
    			if(key == KeyEvent.VK_SHIFT) tempObject.holdShape();
    			if(key == KeyEvent.VK_SPACE){
    				tempObject.setInterval(0); 
    				tempObject.setLockTime(tempObject.getMaxLockTime());
    			}
            }
		}
		if(key == KeyEvent.VK_ESCAPE) System.exit(1);
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		
		for (int i = 0; i < handler.object.size(); i++) {
			Shape tempObject = (Shape) handler.object.get(i);
			if(tempObject.getId() == ID.Shape && tempObject.isActive()){
				if(key == KeyEvent.VK_LEFT) tempObject.setVelX(0);
				if(key == KeyEvent.VK_RIGHT) tempObject.setVelX(0);
				if(key == KeyEvent.VK_DOWN) tempObject.setInterval(tempObject.getBaseInterval());
			}
		}
	}

}
