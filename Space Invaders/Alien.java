import java.awt.*;

public class Alien extends Sprite2D {

    boolean isDead = false;

    public Alien(Image i1, Image i2) {

        super(i1, i2);

    }

    public boolean move(){

        // Set Position

        setPosition(this.x + xSpeed, this.y);

        // Test for Edges if alien is not Dead

        if((!this.isDead) && (this.x > 750 || this.x < 0)){

            return true;

        }

        return false;

    }

    public void reverseDirection(){

        // Reverse direction

        setXSpeed(xSpeed*-1);

        // Move Down Row

        this.y += 40;



    }
}
