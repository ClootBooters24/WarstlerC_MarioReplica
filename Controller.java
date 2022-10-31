//Clayton Warstler
//Progamming Paradigms Assignment 5
//10/27/2022

import java.awt.event.MouseListener;

import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

class Controller implements ActionListener, MouseListener, KeyListener
{
	View view;
	Model model;
	Fireball fireball;
	boolean editMode = false;
	boolean editGoombaMode = false;
	boolean editPipeMode = false;

	boolean keyLeft;
	boolean keyRight;
	boolean keyUp;
	boolean keyDown;

	boolean keyQ;
	boolean keyEscape;
	boolean keyS;
	boolean keyL;
	
	boolean keySpace;
	boolean keyE;

	boolean keyG;
	boolean keyCtrl;
	boolean keyP;

	Controller(Model m) {
		model = m;
	}

	void setView(View v) {
		view = v;
	}

	public void actionPerformed(ActionEvent e) {
	}

	public void mousePressed(MouseEvent e) {
		if(editMode == true && editPipeMode == true) {
			model.placePipe(e.getX() + view.scrollPos, e.getY());
		}
		if(editMode == true && editGoombaMode == true) {
			model.placeGoomba(e.getX() + view.scrollPos, e.getY());
		}
	}

	public void mouseReleased(MouseEvent e) {    }
	public void mouseEntered(MouseEvent e) {    }
	public void mouseExited(MouseEvent e) {    }
	public void mouseClicked(MouseEvent e) {    }

	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
			case KeyEvent.VK_RIGHT: keyRight = true; break;
			case KeyEvent.VK_LEFT: keyLeft = true; break;
			case KeyEvent.VK_UP: keyUp = true; break;
			case KeyEvent.VK_DOWN: keyDown = true; break;
			
			case KeyEvent.VK_SPACE: keySpace = true; break;
			case KeyEvent.VK_E: keyE = false; break;
		}
	}

	public void keyReleased(KeyEvent e) {
		switch(e.getKeyCode()) {
			case KeyEvent.VK_RIGHT: keyRight = false; break;
			case KeyEvent.VK_LEFT: keyLeft = false; break;
			case KeyEvent.VK_UP: keyUp = false; break;
			case KeyEvent.VK_DOWN: keyDown = false; break;

			case KeyEvent.VK_Q:
				System.exit(0);
				break;

			case KeyEvent.VK_ESCAPE: 
				System.exit(0);
				break;

			case KeyEvent.VK_S: keyS = true; break;
			case KeyEvent.VK_L: keyL = true; break;

			case KeyEvent.VK_SPACE: keySpace = false; break;
			case KeyEvent.VK_E: 
				editMode = !editMode;
				editPipeMode = true;
				System.out.println(editMode +" "+ editPipeMode + " " + editGoombaMode);
				break;

			case KeyEvent.VK_G:
				if(editMode) {
					editPipeMode = false;
					editGoombaMode = !editGoombaMode;
					System.out.println(editMode +" "+ editPipeMode + " " + editGoombaMode);
				}
				break;

			case KeyEvent.VK_P:
				if(editMode) {
					editGoombaMode = false;
					editPipeMode = !editPipeMode;
					System.out.println(editMode +" "+ editPipeMode + " " + editGoombaMode);
				}
				break;

			case KeyEvent.VK_CONTROL:
				
				model.placeFire(model.mario.x, model.mario.y);
				break;
		}

		if(keyS) {
			Json saveObject = model.marshal();
			saveObject.save("pipeMap.json");
		}

		if(keyL) {
			Json loadObject = Json.load("pipeMap.json");

			if(loadObject == null)
				System.exit(0);

			model.unmarshal(loadObject);
		}
	}

	public void keyTyped(KeyEvent e) {
	}

	void update() {
		model.mario.setPreviousPosition();

		if(keyRight) {
			model.mario.rightFacing = true;
			model.mario.x += 8;
			model.mario.changeImageState();
		}

		if(keyLeft) {
			model.mario.rightFacing = false;
			model.mario.x -= 8;
			model.mario.changeImageState();
    	}

		if(keySpace || keyUp) {
			// System.out.println("Space");
			if(model.mario.jumpTime < 5) {
				model.mario.velocityVertical = -40;
			}
		}
	}
}
