package GameObjects.Entities;

import Codes.Handler;

import java.awt.*;

public class HandGunRound extends Shot {



    public HandGunRound(int x, int y, ID id, Handler handler) {
        super(x, y, id, handler);
        velocity = 20;
        size = 10;
        damage = 20;

    }

    @Override
    public void tick() {
        x += velX;
        y += velY;
        Collision();

    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect((int)x,(int)y, size, size);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int)x,(int)y, size, size);
    }

    public void Collision(){
        try {
            for (GameObject object : handler.objects) {
                if (object.getID() == ID.ENEMY) {
                    if (getBounds().intersects(object.getBounds())) {
                        handler.objects.remove(this);
                        RegularEnemy enemy = (RegularEnemy) object;
                        enemy.takeDamage(damage);
                        break;
                    }
                }
                else if (object.getID() == ID.BLOCK){
                    if (getBounds().intersects(object.getBounds())) {
                        handler.objects.remove(this);
                        break;
                    }
                }
            }
        }catch (Exception e){
            System.out.println("Collision Exception Detected");
        }
    }
}
