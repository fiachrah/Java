import java.awt.*;

public class PlayerBullet extends Sprite2D {

    boolean hasHit = false;

    public PlayerBullet(Image i1, Image i2) {

        super(i1, i2);

    }

    public void move(){

        // Change Position

        setPosition(this.x, this.y  - xSpeed);


    }
}
