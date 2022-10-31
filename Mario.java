//Clayton Warstler
//Progamming Paradigms Assignment 5
//10/27/2022

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.awt.Graphics;

public class Mario extends Sprite {
    // int x, y, w, h;
    int px, py;
    int velocityVertical = 10;
    int currentImage;
    BufferedImage[] imageMario;
    BufferedImage defaultImage;
    int jumpTime;

    boolean rightFacing = true;

    public Mario(int x, int y) {
        super(x, y, 16, 16);
        px = this.x;
        py = this.y;
        currentImage = 0;
        imageMario = new BufferedImage[5];
        jumpTime = 0;

        for(int i = 0; i < imageMario.length; i++)
            imageMario[i] = View.loadImage("mario" + (i + 1) + ".png");

        h = imageMario[0].getHeight();
        w = imageMario[0].getWidth();
    }

    void update() {
        velocityVertical += 10;
        y += velocityVertical;

        jumpTime++;

        if (y > 400 - imageMario[0].getHeight()) {
            velocityVertical = 0;
            jumpTime = 0;

            y = 400 - h;
        }
    }

    public void removeFromPipe(Sprite sprite) {
        //Coming from left moving right
        if(((x + w) >= sprite.x) && ((px + w) <= sprite.x)) {
            x = sprite.x - w;
        }
        //Coming from right moving left
        if ((x <= (sprite.x + sprite.w)) && (px >= (sprite.x + sprite.w))) {
            x = sprite.x + sprite.w;
        }

        //Coming from above moving down
        if(((y + h) >= sprite.y) && ((py + h) <= sprite.y)) {
            y = sprite.y - h;
        }
        //Coming from below moving up
        if((y <= (sprite.y + sprite.h)) && (py >= (sprite.y + sprite.h))) {  
            y = (sprite.y + sprite.h);
            velocityVertical += 10;
            y += velocityVertical;
        }
    }

    void changeImageState() {
        currentImage++;
        if(currentImage > 4) {
            currentImage = 0;
        }
    }

    @Override 
    public String toString()
    {
	    return "Mario (x,y) = (" + x + ", " + y + "), width = " + w + ", height = " + h;
    }

    void drawSelf(Graphics g, int scrollPos) {
		if(rightFacing) {
			g.drawImage(imageMario[currentImage], x - scrollPos, y, w, h, null);
		}

		else {
			g.drawImage(imageMario[currentImage], x - scrollPos + w, y, - w, h, null);
		}    
    }

    @Override
    Json marshal() {
        return null;
    }

    void setPreviousPosition() {
        px = this.x;
        py = this.y;
    }

    @Override
    boolean isMario() {
        return true;
    }

    boolean checkCollision(Sprite sprite) {
        int marioTop = y;
        int marioBottom = y + h;
        int marioLeft = x;
        int marioRight = x  + w;
        
        int pipeTop = sprite.y;
        int pipeBottom = sprite.y + sprite.h;
        int pipeLeft = sprite.x;
        int pipeRight = sprite.x + sprite.w;

        boolean returnVal;

        //If is not colliding
        if((marioRight < pipeLeft) || (marioLeft > pipeRight) || (marioBottom < pipeTop) || (marioTop > pipeBottom)) {
            returnVal = false;
        }
        //If it is colliding
        else {
            returnVal = true;
        }

        return returnVal;
    }

    @Override
    void isOnFire() {
        // TODO Auto-generated method stub
        
    }
}