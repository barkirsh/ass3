// 327721544 Bar Kirshenboim

import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;


import java.awt.Color;
import java.util.Random;

/**
 * defined the game.
 */
public class Game {
    private final GUI gui = new GUI("first test", 800, 600);
    private final Sleeper sleeper = new Sleeper();


    private SpriteCollection sprites;
    private GameEnvironment environment;

    /**
     * add to this environment a collidable.
     *
     * @param c collidable
     */
    public void addCollidable(Collidable c) {
        environment.addCollidable(c);
    }

    /**
     * add sprite to sprites colection.
     *
     * @param s sprite
     */
    public void addSprite(Sprite s) {
        sprites.addSprite(s);
    }

    /**
     * Initialize a new game: create the Blocks and Ball(and Paddle) and add them to the game.
     */
    public void initialize() {
        Point paddleUpperLeft = new Point(350, 585);
        Point upBorder = new Point(0, -1);
        Point leftBorder = new Point(-1, 0);
        Point lowBorder = new Point(0, 600);
        Point rightBorder = new Point(800, 0);
        Block paddle = new Block(new Rectangle(paddleUpperLeft, 85, 15));

        Block upWall = new Block(new Rectangle(upBorder, 800, 1));
        Block rightWall = new Block(new Rectangle(rightBorder, 1, 600));
        Block lowWall = new Block(new Rectangle(lowBorder, 800, 1));
        Block leftWall = new Block(new Rectangle(leftBorder, 1, 600));

        this.environment = new GameEnvironment();
        this.sprites = new SpriteCollection();
        this.environment.addCollidable(paddle);
        this.environment.addCollidable(upWall);
        this.environment.addCollidable(rightWall);
        this.environment.addCollidable(lowWall);
        this.environment.addCollidable(leftWall);

        biuoop.KeyboardSensor keyboard = this.gui.getKeyboardSensor();
        Paddle player = new Paddle(paddle, keyboard);

        for (int i = 0; i < 2; i++) {
            Ball ball = new Ball(300 + i, 300 + i, 5, Color.RED);
            ball.setGameEnvironment(this.environment);
            ball.setVelocity(5 + i, 5 + i);
            ball.addToGame(this);
        }
        paddle.setColor(Color.GRAY);
        player.addToGame(this);

        Random rand = new Random();

        Point p;
        for (int j = 1; j < 6; j++) {
            Color color = new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat());
            for (int i = 0; i < 12 - j; i++) {
                p = new Point(740 - i * 60, 50 + j * 30);
                Block block = new Block(new Rectangle(p, 60, 30));
                block.setColor(color);
                block.addToGame(this);
            }
        }

    }

    /**
     * Run the game -- start the animation loop.
     */
    public void run() {

        int framesPerSecond = 60;
        int millisecondsPerFrame = 1000 / framesPerSecond;
        while (true) {
            long startTime = System.currentTimeMillis(); // timing

            DrawSurface d = this.gui.getDrawSurface();
            this.sprites.drawAllOn(d);
            this.gui.show(d);
            this.sprites.notifyAllTimePassed();

            // timing
            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                this.sleeper.sleepFor(milliSecondLeftToSleep);
            }
        }
    }
}