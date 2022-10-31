 //Clayton Warstler
 //Progamming Paradigms Assignment 4
 //10/6/2022

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.Color;

class View extends JPanel
{
	// BufferedImage imagePipe;
	Model model;
	int scrollPos;
	BufferedImage imageFloor;
	Sprite sprite;
	//BufferedImage marioDefault;

	View(Controller c, Model model) {
		scrollPos = 0;
		c.setView(this);
		this.model = model;
		//imagePipe = loadImage("pipe.png");
		imageFloor = loadImage("marioFloor.png");
		//marioDefault = loadImage("marioDefault.png");
	}

	public void paintComponent(Graphics g) {
		scrollPos = model.mario.x - 150;
		g.setColor(new Color(128, 255, 255));
		g.fillRect(0, 0, this.getWidth(), this.getHeight());

		g.drawImage(imageFloor, 0, 400 ,null);

		for(int i = 0; i < model.sprites.size(); i++) {
			model.sprites.get(i).drawSelf(g, scrollPos);
		}
	}

	static BufferedImage loadImage(String filename) {
		BufferedImage img = null;

		try {
			img = ImageIO.read(new File(filename));
		}
		catch(Exception e) {
			e.printStackTrace(System.err);
			System.exit(1);
		}
		
		return img;
	}
}