// 327721544 Bar Kirshenboim

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

/**
 * paddle class defined the paddle in the game.
 */
public class Paddle implements Sprite, Collidable {
    private final biuoop.KeyboardSensor keyboard;

    private enum Region {
        LL, L, MID, R, RR, ERROR
    }

    private final Block block;
    //private GameEnvironment environment;
    private final double left = 0;
    private final double right = 800;
    private Region region = Region.MID;

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
    public Region getRegionUsingCollision(Point collisionPoint) {
        double x = collisionPoint.getX();
        double left = this.getCollisionRectangle().getUpperLeft().getX();
        double right = this.getCollisionRectangle().getUpperLeft().getX() + this.getCollisionRectangle().getWidth();
        double mid = (right + left) / 2;
        double midLeft = (mid + left) / 2;
        double midRight = (mid + right) / 2;
        if (x == mid) {
            return Region.MID;
        } else if (left <= x && x < midLeft) {
            return Region.LL;
        } else if (midLeft <= x && x < mid) {
            return Region.L;
        } else if (mid < x && x <= midRight) {
            return Region.R;
        } else if (midRight < x && x <= right) {
            return Region.RR;
        }
        System.out.println("not has a region!");
        return Region.ERROR;

    }

    /**
     * @param collisionPoint  collision point
     * @param currentVelocity the hit velocity
     * @return velocity after hit
     */
    public Velocity hit(Point collisionPoint, Velocity currentVelocity) {
        int angle;
        double speed = Math.sqrt(currentVelocity.getDx() * currentVelocity.getDx()
                + currentVelocity.getDy() * currentVelocity.getDy());
        this.region = getRegionUsingCollision(collisionPoint);
        if (this.region == Region.RR) {
            angle = 60;
        } else if (this.region == Region.R) {
            angle = 30;

        } else if (this.region == Region.MID) {
            return this.block.hit(collisionPoint, currentVelocity);
        } else if (this.region == Region.L) {
            angle = -30;
        } else if (this.region == Region.LL) {
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