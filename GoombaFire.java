import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class GoombaFire extends Sprite {
    int px, py;
    int velocityVertical = 10;
    int velocityHorizontal = 5;
    int currentImage;
    BufferedImage imageGoombaFire;
    int jumpTime;
    boolean isOnFire = false;

    GoombaFire(int x, int y) {
        super(x, y, 32, 32);
        px = this.x;
        py = this.y;

        if(imageGoombaFire == null)
            imageGoombaFire = View.loadImage("goomba_fire.png");
    }

    public void setPreviousPosition() {
        px = x;
        py = y;
    }

    @Override
    void drawSelf(Graphics g, int scrollPos) {
        g.drawImage(imageGoombaFire, x - scrollPos, y, w, h, null);
    }

    @Override
    void update() {
        y += velocityVertical;
        x += velocityHorizontal;

        if (y > 400 - imageGoombaFire.getHeight()) {
            velocityVertical = 0;

            y = 400 - h;
        }
    }

    @Override
    Json marshal() {
        return null;
    }

    void removeFromPipe(Sprite sprite) {
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
            y += velocityVertical;
        }
    }

    @Override
    boolean isGoombaFire() {
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

    boolean checkCollisionFire(Sprite sprite) {
        int goombaTop = y;
        int goombaBottom = y + h;
        int goombaLeft = x;
        int goombaRight = x  + w;
        
        int fireTop = sprite.y;
        int fireBottom = sprite.y + sprite.h;
        int fireLeft = sprite.x;
        int fireRight = sprite.x + sprite.w;

        boolean returnVal;

        //If is not colliding
        if((goombaRight < fireLeft) || (goombaLeft > fireRight) || (goombaBottom < fireTop) || (goombaTop > fireBottom)) {
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
        // TODO Auto-generated method stub
        
    }
}
