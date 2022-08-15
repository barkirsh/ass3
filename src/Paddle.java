// 327721544 Bar Kirshenboim

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

/**
 * paddle class defined the paddle in the game.
 */
public class Paddle implements Sprite, Collidable {
    private final biuoop.KeyboardSensor keyboard;
    private final Block block;
    //private GameEnvironment environment;
    private double left = 0;
    private double right = 800;
    private int regiun = 3;

    /**
     * constructor of paddle.
     *
     * @param block          the block
     * @param keyboardSensor the sensor
     */
    public Paddle(Block block, KeyboardSensor keyboardSensor) {
        this.block = block;
        this.keyboard = keyboardSensor;
    }

    /**
     * move the paddle left.
     */
    public void moveLeft() {
        double leftMovement = this.getCollisionRectangle().getUpperLeft().getX() - 10;
        this.getCollisionRectangle().setWidth(this.getCollisionRectangle().getWidth());
        this.getCollisionRectangle().setHeight(this.getCollisionRectangle().getHeight());
        this.getCollisionRectangle().getUpperLeft().setY(this.getCollisionRectangle().getUpperLeft().getY());
        if (leftMovement < this.left) {
            this.getCollisionRectangle().getUpperLeft().setX(this.left);
            return;
        }
        this.getCollisionRectangle().getUpperLeft().setX(leftMovement);

    }

    /**
     * move paddle to right.
     */
    public void moveRight() {
        double rightMovement = this.getCollisionRectangle().getUpperLeft().getX() + 10;
        this.getCollisionRectangle().setWidth(this.getCollisionRectangle().getWidth());
        this.getCollisionRectangle().setHeight(this.getCollisionRectangle().getHeight());
        this.getCollisionRectangle().getUpperLeft().setY(this.getCollisionRectangle().getUpperLeft().getY());

        if (rightMovement + this.getCollisionRectangle().getWidth() > this.right) {
            this.getCollisionRectangle().getUpperLeft().setX(this.right - this.getCollisionRectangle().getWidth());
            return;
        }
        this.getCollisionRectangle().getUpperLeft().setX(rightMovement + 10);

    }

    /**
     * notifying time passed and moving the paddle according to his click.
     */
    public void timePassed() {
        if (this.keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
            this.moveLeft();
        } else if (this.keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
            this.moveRight();
        }

    }

    /**
     * drawing paddle.
     *
     * @param d drawer
     */
    public void drawOn(DrawSurface d) {
        this.block.drawOn(d);
    }

    /**
     * @return paddle shape
     */
    public Rectangle getCollisionRectangle() {
        return this.block.getCollisionRectangle();
    }

    /**
     * @param collisionPoint the collision point
     * @return region to collision with paddle
     */
    public int getRegiunUsingCollision(Point collisionPoint) {
        double x = collisionPoint.getX();
        double leftX = this.getCollisionRectangle().getUpperLeft().getX();
        double rightX = this.getCollisionRectangle().getUpperLeft().getX() + this.getCollisionRectangle().getWidth();
        double midX = (rightX + leftX) / 2;
        double midLeft = (midX + leftX) / 2;
        double midRight = (midX + rightX) / 2;
        if (x == midX) {
            return 3;
        } else if (leftX <= x && x < midLeft) {
            return 1;
        } else if (midLeft <= x && x < midX) {
            return 2;
        } else if (midX < x && x <= midRight) {
            return 4;
        } else if (midRight < x && x <= rightX) {
            return 5;
        }
        return 0;

    }
// public boolean inPaddle(Point p) {
//     double xLeft = this.getCollisionRectangle().getUpperLeft().getX();
//     double xRight = xLeft + this.getCollisionRectangle().getWidth();
//     double yUp = this.getCollisionRectangle().getUpperLeft().getY();
//     double yLow = yUp + this.getCollisionRectangle().getHeight();
//
//     double centerX = p.getX();
//     double centerY = p.getY();
//     return (centerX < xRight && centerX > xLeft) && (centerY < yLow && centerY > yUp);
// }

    /**
     * @param collisionPoint  collision point
     * @param currentVelocity the hit velocity
     * @return velocity after hit
     */
    public Velocity hit(Point collisionPoint, Velocity currentVelocity) {
        int angle = 0;
        double speed = Math.sqrt(currentVelocity.getDx() * currentVelocity.getDx()
                + currentVelocity.getDy() * currentVelocity.getDy());
        this.regiun = getRegiunUsingCollision(collisionPoint);
        if (this.regiun == 5) {
            angle = 60;
        } else if (this.regiun == 4) {
            angle = 30;

        } else if (this.regiun == 3) {
            return this.block.hit(collisionPoint, currentVelocity);
        } else if (this.regiun == 2) {
            angle = -30;
        } else if (this.regiun == 1) {
            angle = -60;
        } else {
            return this.block.hit(collisionPoint, currentVelocity);
        }
        currentVelocity = Velocity.fromAngleAndSpeed(angle, speed);
        return this.block.hit(collisionPoint, currentVelocity);
    }

    /**
     * Add this paddle to the game.
     *
     * @param g game
     */
    public void addToGame(Game g) {
        g.addCollidable(this);
        g.addSprite(this);
    }
}