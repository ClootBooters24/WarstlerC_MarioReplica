import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.awt.Graphics;

public class Fireball extends Sprite {
    BufferedImage imageFireball;
    double velocityVertical = 3;
    double velocityHorizontal = 3;

    public Fireball(int x, int y) {
        super(x, y, 32, 32);
        if(imageFireball == null)
            imageFireball = View.loadImage("fireball.png");
    }

    void update() {
        x += velocityHorizontal;
        y -= velocityVertical;

        if(y > 300)
            y += velocityVertical;
    }

    void drawSelf(Graphics g, int scrollPos) {
        g.drawImage(imageFireball, x - scrollPos, y, w, h, null);
    }

    public void removeFromPipe(Sprite sprite) {
        //Coming from left moving right
        if(((x + w) >= sprite.x) && ((previousX + w) <= sprite.x)) {
            x = sprite.x - w;
            velocityVertical = -1;
        }
        //Coming from right moving left
        if ((x <= (sprite.x + sprite.w)) && (previousX >= (sprite.x + sprite.w))) {
            x = sprite.x + sprite.w;
            velocityVertical = 1;
        }
        //Coming from above moving down
        if(((y + h) >= sprite.y) && ((previousY + h) <= sprite.y)) {
            y = sprite.y - h;
        }
        //Coming from below moving up
        if((y <= (sprite.y + sprite.h)) && (previousY >= (sprite.y + sprite.h))) {  
            y = (sprite.y + sprite.h);
            //velocityVertical += 10;
            //y += velocityVertical;
        }

    }

    @Override
    boolean isFireBall() {
        return true;
    }
    
    void setPreviousPosition() {
        previousX = x;
        previousY = y;
    }

    @Override
    Json marshal() {
        return null;
    }

    public boolean checkCollision(Sprite sprite) {
        int fireballTop = y;
        int fireballBottom = y + h;
        int fireballLeft = x;
        int fireballRight = x  + w;
            
        int pipeTop = sprite.y;
        int pipeBottom = sprite.y + sprite.h;
        int pipeLeft = sprite.x;
        int pipeRight = sprite.x + sprite.w;
    
        boolean returnVal;
    
        //If is not colliding
        if((fireballRight < pipeLeft) || (fireballLeft > pipeRight) || (fireballBottom < pipeTop) || (fireballTop > pipeBottom)) {
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
