import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.ArrayList;

class InvadersApplication extends JFrame implements Runnable, KeyListener
{
    // Member Data

    private static final Dimension WindowSize = new Dimension(800,600);
    private BufferStrategy strategy;
    private static boolean bulletOnField = false;
    private int frameCounter = 0;

    // Number of Aliens and Bullets

    private static int NumBullets = 0;
    private static final int NumAliens = 30;

    // Link to Images

    ImageIcon shipIcon = new ImageIcon("C:\\Users\\f14ch\\Downloads\\ct255-images\\player_ship.png");
    private Image ship = shipIcon.getImage();

    ImageIcon alienIcon1 = new ImageIcon("C:\\Users\\f14ch\\Downloads\\ct255-images\\alien_ship_1.png");
    private Image alien1 = alienIcon1.getImage();

    ImageIcon alienIcon2 = new ImageIcon("C:\\Users\\f14ch\\Downloads\\ct255-images\\alien_ship_2.png");
    private Image alien2 = alienIcon2.getImage();

    ImageIcon bulletIcon = new ImageIcon("C:\\Users\\f14ch\\Downloads\\ct255-images\\bullet.png");
    private Image bullet = bulletIcon.getImage();

    // Create Ship, Bullet Array and Alien Array

    private Alien [] AlienArray = new Alien[NumAliens];
    private ArrayList<PlayerBullet> BulletArrayList = new ArrayList<PlayerBullet>();
    private Spaceship PlayerShip = new Spaceship(ship, null);

    boolean gameInProgress;

    // High Score, Current Score, Alien Speed and Menu Message

    static int highScore = 0;

    static int currentScore = 0;

    static int alienSpeed = 2;

    static int menuMessage = 1;

    // Constructor

    public InvadersApplication()
    {

        // Window

        Dimension screensize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        int x = screensize.width / 2 - WindowSize.width / 2;
        int y = screensize.height / 2 - WindowSize.height / 2;
        this.setTitle("Space Invaders");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addKeyListener(this);
        setBounds(x, y, 800, 600);
        setVisible(true);
        createBufferStrategy(3);
        strategy = getBufferStrategy();

        int arrayPos = 0;

        // Loop through Alien Array

        for (int i=0; i<5; i++) {

            for (int j = 0; j < 6; j++) {

                // Create Alien

                AlienArray[arrayPos] = new Alien(alien1, alien2);

                arrayPos++;

            }

        }

        // Start New Game on Launch

        startNewGameMenu(1);
    }

    public void startNewGameMenu(int message){

        // Reset Speed, Score and Set Message and Game in Progress

        menuMessage = message;

        alienSpeed = 2;

        currentScore = 0;

        gameInProgress = false;

        repaint();

    }

    public void startNewWave(boolean firstWave, int speed){


        if (firstWave) {

            // Game Now In Progress

            gameInProgress = true;

            // Thread

            Thread thread = new Thread(this);
            thread.start();

            // Set Ship Position and Speed

            PlayerShip.setPosition(380, 550);
            PlayerShip.setXSpeed(10);

        }

        int arrayPos = 0;

        for (int i=0; i<5; i++){

            for (int j = 0; j <6; j++){

                // Set Alien Position and Speed

                AlienArray[arrayPos].setPosition( 40+(j*50), 90+(i*50));
                AlienArray[arrayPos].setXSpeed(speed);
                AlienArray[arrayPos].isDead = false;

                arrayPos++;

            }

        }


    }

    // Thread's Entry Point

    public void run()
    {
        while(gameInProgress) {
            try {

                // Count Frames

                frameCounter++;

                // Time Between Thread

                Thread.sleep(10);

                // Move Alien

                boolean edge = false;

                for (int i = 0; i < NumAliens; i++) {

                    if(AlienArray[i].move()){

                        edge = true;

                    }

                }


                // Reverse Direction if Edge is Hit

                if(edge){

                    for(int j = 0; j< NumAliens; j++){

                        AlienArray[j].reverseDirection();
                    }

                }

                // If a Bullet is on Field

                if(bulletOnField) {

                    for (int i = 0; i < NumBullets; i++) {

                        // Move The Bullet

                        BulletArrayList.get(i).move();

                        int deadAlienCounter = 0;

                        for(int j = 0; j < NumAliens; j++){

                            // If The Alien and Bullet Being Looked at Still Exist

                            if(!AlienArray[j].isDead && !BulletArrayList.get(i).hasHit){

                                // Check to see if Bullet and Alien Collide

                                if(collide(AlienArray[j].x, BulletArrayList.get(i).x, AlienArray[j].y, BulletArrayList.get(i).y, 50, 6, 32, 16)){

                                    // Kill Bullet and Alien

                                    AlienArray[j].isDead = true;
                                    BulletArrayList.get(i).hasHit = true;

                                    // Increment Current Score

                                    currentScore++;

                                    // If High Score is less than Current Score, Increment that too

                                    if(currentScore > highScore){

                                        highScore++;

                                    }

                                }

                            }

                            // If Alien being Looked at is Alive

                            if(!AlienArray[j].isDead){

                                // Check for Collision with Ship

                                 if(collide(AlienArray[j].x, PlayerShip.x, AlienArray[j].y, PlayerShip.y, 50, 54, 32,32)){

                                     // Start New Game if Collides

                                    startNewGameMenu(2);

                                  }

                            }
                            else{

                                // Count Dead Aliens

                                deadAlienCounter++;

                            }
                        }

                        // If All Aliens are Dead

                        if(deadAlienCounter == NumAliens){

                            alienSpeed = alienSpeed +2;

                            // Start a New Wave

                            startNewWave(false, alienSpeed);


                        }

                    }

                }

                repaint();

            } catch (InterruptedException e){

            }
        }
    }

    // Three Keyboard Event-Handler Functions

    public void keyPressed(KeyEvent e){

        if(!gameInProgress)
        {
            // Start New Wave on any button click if Game Not In Progress

            startNewWave(true, alienSpeed);
        }
        else if(KeyEvent.VK_LEFT == e.getKeyCode())
        {
            PlayerShip.move('l');
        }
        else if(KeyEvent.VK_RIGHT == e.getKeyCode())
        {
            PlayerShip.move('r');
        }

    }

    public void keyTyped(KeyEvent e){ }

    public void keyReleased(KeyEvent e){


        if((KeyEvent.VK_SPACE == e.getKeyCode()))
        {

            // Bullet on Field

            bulletOnField = true;

            // Create Bullet and Set Position and Speed

            BulletArrayList.add(new PlayerBullet(bullet, null));
            BulletArrayList.get(NumBullets).setPosition( (PlayerShip.x)+23, (PlayerShip.y)-20);
            BulletArrayList.get(NumBullets).setXSpeed(5);

            // Increment Bullets

            NumBullets++;

        }


    }

    // Application's Paint Method

    public void paint(Graphics g)
    {
        if(gameInProgress) {
            g = strategy.getDrawGraphics();

            // Paint Window

            g.setColor(Color.BLACK);
            g.fillRect(0, 0, 800, 600);

            // Scores

            g.setColor(Color.WHITE);

            g.setFont(new Font( "Times", Font.PLAIN, 30 ));
            g.drawString("Score: "+currentScore, 250, 70);

            g.setFont(new Font( "Times", Font.PLAIN, 30 ));
            g.drawString("Best: "+highScore, 450, 70);

            PlayerShip.paint(g);

            for (int i = 0; i < NumAliens; i++) {

                // Paint Aliens if not Dead

                if (!AlienArray[i].isDead) {

                    // Change Alien Image every 50 Frames

                    if ((frameCounter % 50) == 0) {

                        AlienArray[i].switchImage();
                    }

                    AlienArray[i].paint(g);


                }
            }

            // Check at least One bullet is on Field

            if(bulletOnField) {

                // Loop through Bullets

                for (int i = 0; i < NumBullets; i++) {

                    // Paint Bullet if Not Hit

                    if (!BulletArrayList.get(i).hasHit) {

                        BulletArrayList.get(i).paint(strategy.getDrawGraphics());

                    }
                }

            }

            strategy.show();
        }

        else{

            // Window

            g.setColor(Color.BLACK);
            g.fillRect(0, 0, 800, 600);

            // Menu Writing

            g.setFont(new Font( "Times", Font.PLAIN, 60 ));
            g.setColor(Color.WHITE);

            if(menuMessage == 1){

                g.drawString("SPACE INVADERS", 150, 150);

            }
            else{

                g.drawString("GAME OVER", 220, 150);

            }

            g.setFont(new Font( "Times", Font.PLAIN, 30 ));
            g.drawString("Press any key to play", 250, 250);

            g.setFont(new Font( "Times", Font.PLAIN, 20 ));
            g.drawString("[Arrow keys to move, space to fire]", 240, 320);


        }
    }

    public boolean collide(double x1, double x2, double y1, double y2, int w1, int w2,  int h1, int h2) {

        // Collision Formula

        if (((x1 < x2 && x1 + w1 > x2) || (x2 < x1 && x2 + w2 > x1)) && ((y1 < y2 && y1 + h1 > y2) || (y2 < y1 && y2 + h2 > y1))) {

            // If Collides

            return true;
        }

        return false;

    }

    // Application Entry Point

    public static void main(String[] args) {

        InvadersApplication invApp = new InvadersApplication();

    }
}