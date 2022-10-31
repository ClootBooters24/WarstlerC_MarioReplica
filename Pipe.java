//Clayton Warstler
//Progamming Paradigms Assignment 4
//10/6/2022

import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.awt.Graphics;

public class Pipe extends Sprite
{
    // int x, y, w, h;

    BufferedImage imagePipe;

    Pipe(int x, int y) {
        super(x, y, 55, 400);

        if(imagePipe == null)
            imagePipe = View.loadImage("pipe.png");
    }

    public Pipe(Json ob) {
        this((int)ob.getLong("x"), (int)ob.getLong("y"));
        }

    int detectPipe(int clickedX, int clickedY, ArrayList<Sprite> t) {
        int location = -1;

        for(int i = 0; i < t.size(); i++) {
            if(t.get(i).isPipe()){
                //Check every pipe to see if the click coords are within the boundaries 
                if (((clickedX >= t.get(i).x) && (clickedX <= t.get(i).x + w)) && ((clickedY >= t.get(i).y) && (clickedY <= t.get(i).y + h))) {
                    location = i;
                }
            }  
        }
        return location;
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

    Json unmarshal() {
        Json ob = Json.newObject();
        ob.add("x", x);
        ob.add("y", y);
        ob.add("h", h);
        ob.add("w", w);
        return ob;
    }

    public void drawSelf(Graphics g, int scrollPos) {
        g.drawImage(imagePipe, x - scrollPos, y, w, h, null);
    }

    @Override 
    public String toString()
    {
	    return "Pipe (x,y) = (" + x + ", " + y + "), width = " + w + ", height = " + h;
    }

    @Override
    boolean isPipe() {
        return true;
    }

    @Override
    void update() {
    }

    @Override
    void removeFromPipe(Sprite sprite) {
    }

    @Override
    void isOnFire() {
        // TODO Auto-generated method stub
        
    }
}
