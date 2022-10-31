import java.awt.Graphics;

public abstract class Sprite {
    int x, y, w, h;
    int previousX, previousY;

    Sprite(int spriteX, int spriteY, int spriteW, int spriteH) {
        x = spriteX;
        y = spriteY;
        w = spriteW;
        h = spriteH;
    }

    abstract void drawSelf(Graphics g, int scrollPos);
    abstract void update();
    abstract Json marshal();
    abstract void removeFromPipe(Sprite sprite);
    abstract void isOnFire();

    boolean isPipe() {
        return false;
    }

    boolean isMario() {
        return false;
    }

    boolean isGoomba() {
        return false;
    }

    boolean isFireBall() {
        return false;
    }

    boolean isGoombaFire() {
        return false;
    }
}