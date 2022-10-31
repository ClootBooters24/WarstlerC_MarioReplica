 //Clayton Warstler
 //Progamming Paradigms Assignment 5
 //10/27/2022

 import java.awt.image.BufferedImage;
 import java.util.ArrayList;
 import java.awt.Graphics;
 
public class Goomba extends Sprite {
    int px, py;
    int velocityVertical = 10;
    int velocityHorizontal = 5;
    int currentImage;
    BufferedImage[] imageGoomba;
    int time = 0;
    boolean isOnFire = false;
    boolean selfDescruct = false;    

    public Goomba(int x, int y) {
        super(x, y, 32, 32);
        px = this.x;
        py = this.y;
        currentImage = 0;
        imageGoomba = new BufferedImage[3];

        for(int i = 0; i < 2; i++)
            imageGoomba[i] = View.loadImage("goomba" + (i + 1) + ".png");

        imageGoomba[2] = View.loadImage("goomba_fire.png");
    }

    public Goomba(Json ob) {
        this((int)ob.getLong("x"), (int)ob.getLong("y"));
    }

    @Override
    void drawSelf(Graphics g, int scrollPos) {
        g.drawImage(imageGoomba[currentImage], x - scrollPos, y, w, h, null);
    }

    @Override
    void update() {
        velocityVertical += 10;
        y += velocityVertical;
        x += velocityHorizontal;

        changeImageState();

        if (y > 400 - imageGoomba[0].getHeight()) {
            y = 400 - h;
        }

        if(isOnFire == true && time < 10) {
            time++;

        }
        if(isOnFire == true && time == 10) {
            selfDescruct = true;
        }
    }

    public void removeFromPipe(Sprite sprite) {
        //Coming from left moving right
        if(((x + w) >= sprite.x) && ((px + w) <= sprite.x)) {
            x = sprite.x - w;
            velocityHorizontal = -5;
        }
        //Coming from right moving left
        if ((x <= (sprite.x + sprite.w)) && (px >= (sprite.x + sprite.w))) {
            x = sprite.x + sprite.w;
            velocityHorizontal = 5;
        }
        //Coming from above moving down
        if(((y + h) >= sprite.y) && ((py + h) <= sprite.y)) {
            y = sprite.y - h;
        }
        //Coming from below moving up
        //Will not be able to jump
        if((y <= (sprite.y + sprite.h)) && (py >= (sprite.y + sprite.h))) {  
            y = (sprite.y + sprite.h);
            velocityVertical += 10;
            y   += velocityVertical;
        }
    }

    void changeImageState() {
        currentImage++;
        if(currentImage > 1) {
            currentImage = 0;
        }

        if(isOnFire) {
            currentImage = 2;
        }
    }

    @Override 
    public String toString()
    {
	    return "Mario (x,y) = (" + x + ", " + y + "), width = " + w + ", height = " + h;
    }

    public void setPreviousPosition() {
        px = x;
        py = y;
    }

    @Override
    Json marshal() {
        Json ob = Json.newObject();
        ob.add("x", x);
        ob.add("y", y);
        ob.add("h", h);
        ob.add("w", w);
        return ob;     
    }

    @Override
    boolean isGoomba() {
        return true;
    }
    
    boolean checkCollision(Sprite sprite) {
        int goombaTop = y;
        int goombaBottom = y + h;
        int goombaLeft = x;
        int goombaRight = x  + w;
        
        int pipeTop = sprite.y;
        int pipeBottom = sprite.y + sprite.h;
        int pipeLeft = sprite.x;
        int pipeRight = sprite.x + sprite.w;

        boolean returnVal;

        //If is not colliding
        if((goombaRight < pipeLeft) || (goombaLeft > pipeRight) || (goombaBottom < pipeTop) || (goombaTop > pipeBottom)) {
            returnVal = false;
        }
        //If it is colliding
        else {
            //System.out.println("collision");
            returnVal = true;
        }

        return returnVal;
    }
    
    @Override
    void isOnFire() {
        isOnFire = true;
        System.out.println(isOnFire);
    }
}