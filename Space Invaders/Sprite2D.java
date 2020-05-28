import java.awt.*;

public class Sprite2D {

    protected double x, y;
    protected double xSpeed = 1;
    protected Image image1;
    protected Image image2;
    protected Image myImage;
    private int imageCheck = 0;

    public Sprite2D(Image i1, Image i2) {

        // Set images

        image1 = i1;
        image2 = i2;

        myImage = image1;


    }

    public void switchImage(){

        if(imageCheck == 0){

            myImage = image1;
        }
        else{

            myImage = image2;
        }

        imageCheck = (imageCheck+1)%2;

    }



    public void setPosition(double xx, double yy){

        // Set x and y coordinates

        this.x = xx;
        this.y = yy;

    }

    public void setXSpeed(double dx){

        xSpeed = dx;

    }

    public void paint(Graphics g) {

        // Create image

        g.drawImage(myImage, (int)x, (int)y, null);

    }


}