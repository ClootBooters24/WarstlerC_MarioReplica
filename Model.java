//Clayton Warstler
//Progamming Paradigms Assignment 4
//10/6/2022

import java.util.ArrayList;
import java.util.Iterator;
 
class Model{
    ArrayList<Sprite> sprites;
    Mario mario;
    Goomba goombas;
    Pipe pipes;
    Fireball fireball;
    GoombaFire goombaFire;

    Model() {
        sprites = new ArrayList<Sprite>(); 
        mario = new Mario(200, 50);
        sprites.add(mario);
    }

    public void update() {
        for(int i = 0; i < sprites.size(); i++) {
            sprites.get(i).update();

            //Mario colliding with Pipe
            if(sprites.get(i).isPipe()) {
                if(mario.checkCollision(sprites.get(i)) == true)
                    mario.removeFromPipe(sprites.get(i));
            }

            //Goomba colliding with Pipe
             if(sprites.get(i).isGoomba()) {
                for(int j = 0; j < sprites.size(); j++) {
                    if(sprites.get(j).isPipe()) {
                        if(((Goomba)sprites.get(i)).checkCollision(sprites.get(i)));
                            sprites.get(i).removeFromPipe(sprites.get(j));
                    }       
                }

                if(((Goomba)sprites.get(i)).selfDescruct == true) {
                    sprites.remove(i);
                }
            }
            
            //Goomba colliding with Fireball
            else if(sprites.get(i).isFireBall()) {
                goombaFire = new GoombaFire(sprites.get(i).x, sprites.get(i).y);
                for(int j = 0; j < sprites.size(); j++) {
                    if(sprites.get(j).isGoomba()) {
                        if(goombaFire.checkCollisionFire(sprites.get(j))) {
                            // System.out.println("Collision 2");
                            ((Goomba)sprites.get(j)).isOnFire();
                        }
                    }       
                }
            }
        }
    }

    public void placePipe(int x, int y) {
        int j;
        Pipe t = new Pipe(x, y);
        for(int i = 0; i < sprites.size(); i++) {
            j = t.detectPipe(x, y, sprites);
            if(sprites.get(i).isPipe()) {
                if(j == -1) {
                    System.out.println("if loop");
                    sprites.add(t);
                    break;
                }
                else {
                    sprites.remove(j);
                    break;
                }
            }
        }

    }

    //Can add goomba but cannot remove
    public void placeGoomba(int x, int y) {
        Goomba t = new Goomba(x, y);
        Pipe p = new Pipe(x, y);
        for(int i = 0; i < sprites.size(); i++) {
            int j = p.detectPipe(x, y, sprites);
            if(sprites.get(i).isGoomba()) {
                if(j == -1) {
                    sprites.add(t);
                    break;
                }
                else {
                    sprites.remove(j);
                    break;
                }
            }
        }
    }

    //Fire does not collide with goombas
    void placeFire(int x, int y) {
        fireball = new Fireball(x, y);
        sprites.add(fireball);
    }

    Json marshal() {
        Json ob = Json.newObject();
        Json tmpListPipes = Json.newList();
        ob.add("pipes", tmpListPipes);
        Json tmpListGoombas = Json.newList();
        ob.add("goombas", tmpListGoombas);

        for(int i = 0; i < sprites.size(); i++) {
            if(sprites.get(i).isPipe())
                tmpListPipes.add(((Pipe)sprites.get(i)).marshal());
            if(sprites.get(i).isGoomba())
                tmpListGoombas.add(((Goomba)sprites.get(i)).marshal());
        }

        return ob;
    }

    void unmarshal(Json ob) {
        sprites = new ArrayList<Sprite>();
        sprites.add(mario);

        Json tmpListPipes = ob.get("pipes");
        for(int i = 0; i < tmpListPipes.size(); i++)
            sprites.add(new Pipe(tmpListPipes.get(i)));

        Json tmpListGoomba = ob.get("goombas");
        for(int i = 0; i < tmpListGoomba.size(); i++)
            sprites.add(new Goomba(tmpListGoomba.get(i)));
    }
}