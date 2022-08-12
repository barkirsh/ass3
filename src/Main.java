import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;

import java.awt.Color;
import java.util.List;

// handle situation of edges
public class Main {
    public static void main(String[] args) {
        GUI gui = new GUI("first test", 800, 600);
        DrawSurface d = gui.getDrawSurface();
        GameEnvironment gameEnvironment = new GameEnvironment();
        Ball ball = new Ball(300, 300, 5, Color.RED);
        ball.setVelocity(10, -10);
        Point upperLeft = new Point(375, 350);
        Point upWallPoint = new Point(0, 0);
        Point lowLeft = new Point(30, 570);
        Point upRight = new Point(770, 0);
        Block paddle = new Block(new Rectangle(upperLeft, 70, 35));
        Block upWall = new Block(new Rectangle(upWallPoint, 800, 30));
        Block rightWall = new Block(new Rectangle(upRight, 30, 600));
        Block lowWall = new Block(new Rectangle(lowLeft, 800, 30));
        Block leftWall = new Block(new Rectangle(upWallPoint, 30, 570));
        leftWall.setColor(Color.cyan);

        Sleeper sleeper = new Sleeper();


        gameEnvironment.addCollidable(paddle);
        gameEnvironment.addCollidable(upWall);
        gameEnvironment.addCollidable(rightWall);
        gameEnvironment.addCollidable(lowWall);
        gameEnvironment.addCollidable(leftWall);

        ball.setGameEnvironment(gameEnvironment);


        while (true) {
            d = gui.getDrawSurface();
            leftWall.drawOn(d);
            lowWall.drawOn(d);
            rightWall.drawOn(d);
            upWall.drawOn(d);
            paddle.drawOn(d);
            //System.out.println("point of ball" + ball.getX() + ", " + ball.getY());

            ball.moveOneStep();
            //System.out.println("point of ball" + ball.getX() + ", " + ball.getY());

            ball.drawOn(d);
            gui.show(d);
            sleeper.sleepFor(45);  // wait for 50 milliseconds.
        }

    }
}
