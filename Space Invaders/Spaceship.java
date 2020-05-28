import java.awt.*;

public class Spaceship extends Sprite2D {

    public Spaceship(Image i1, Image i2) {

        super(i1, i2);

    }

    public void move(char code){

        // If left arrow clicked

        if(code == 'l') {

            setPosition(this.x - xSpeed, this.y);

            if (this.x < 0) {

                setPosition(0, this.y);

            }
        }

        // If right arrow clicked

        if(code == 'r') {

            setPosition(this.x + xSpeed, this.y);

            if (this.x > 750){

                setPosition(this.x = 750, this.y);

            }
        }
    }
}
