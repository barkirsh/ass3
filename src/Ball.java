// 327721544 Bar Kirshenboim

import biuoop.DrawSurface;

import java.awt.Color;

/**
 * defining Ball class with center point, radius and color.
 */
public class Ball implements Sprite {
    private Color color;
    private Point center;
    private int radius;
    private Velocity velocity = new Velocity(0, 0);
    private GameEnvironment gameEnvironment = null;

    /**
     * constructor of ball.
     *
     * @param center - center point of ball
     * @param r      - radius of ball
     * @param color  - color of ball
     */
    public Ball(Point center, int r, Color color) {
        this.center = center;
        this.color = color;
        this.radius = Math.abs(r);
    }

    /**
     * contractor of ball with x,y values.
     *
     * @param x     - x value of center point
     * @param y     - y value of center point
     * @param r     - radius of point
     * @param color - color of point
     */
    public Ball(int x, int y, int r, Color color) {
        this.center = new Point(x, y);
        this.radius = Math.abs(r);
        this.color = color;
    }

    /**
     * return x value of  ball.
     *
     * @return center x value
     */
    public int getX() {
        return (int) this.center.getX();
    }

    /**
     * return y value of  ball.
     *
     * @return center y value
     */
    public int getY() {
        return (int) this.center.getY();
    }

    /**
     * this function return this ball radius.
     *
     * @return this radius
     */
    public int getSize() {
        return this.radius;
    }

    /**
     * this function return this ball color.
     *
     * @return this color
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * this function return this ball velocity.
     *
     * @return velocity
     */
    public Velocity getVelocity() {
        return this.velocity;
    }

    /**
     * @return this game environment
     */
    public GameEnvironment getGameEnvironment() {
        return gameEnvironment;
    }

    /**
     * setter to game environment.
     *
     * @param gameEnvironment - this environment
     */
    public void setGameEnvironment(GameEnvironment gameEnvironment) {
        this.gameEnvironment = gameEnvironment;
    }


    /**
     * this function re - set this ball color.
     *
     * @param color - new color of this ball
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * this function re - set this ball radius.
     *
     * @param radius - new radius of this ball
     */
    public void setRadius(int radius) {
        this.radius = radius;
    }

    /**
     * this function re - set this ball center point.
     *
     * @param center - new center of this ball
     */
    public void setCenter(Point center) {
        this.center = center;
    }

    /**
     * this function draw this ball on the given DrawSurface.
     *
     * @param surface - given DrawSurface
     */
    public void drawOn(DrawSurface surface) {
        if (surface == null) {
            return;
        }
        surface.setColor(this.color);
        int r = this.radius;
        int centerX = this.getX();
        int centerY = this.getY();
        surface.fillCircle(centerX, centerY, r);
    }

    @Override
    public void timePassed() {
        this.moveOneStep();
    }

    /**
     * this function set this ball velocity.
     *
     * @param v - new velocity
     */
    public void setVelocity(Velocity v) {
        this.velocity = v;
    }

    /**
     * this function re-set this ball velocity given the dx,dy values.
     *
     * @param dx - delta x
     * @param dy - delta y
     */
    public void setVelocity(double dx, double dy) {
        this.velocity = new Velocity(dx, dy);
    }

    /**
     * check if ball is stuck inside a collidable object.
     *
     * @param collidable the object the ball collide with
     * @return true if ball inside it and false otherwise
     */
    public boolean inCollidable(Collidable collidable) {
        double xLeft = collidable.getCollisionRectangle().getUpperLeft().getX() + this.radius;
        double xRight = xLeft + collidable.getCollisionRectangle().getWidth() - this.radius;
        double yUp = collidable.getCollisionRectangle().getUpperLeft().getY() + this.radius;
        double yLow = yUp + collidable.getCollisionRectangle().getHeight() - this.radius;

        double centerX = this.center.getX();
        double centerY = this.center.getY();
        return (centerX < xRight && centerX > xLeft) && (centerY < yLow && centerY > yUp);
    }

    /**
     * this function moves the ball one step by applying velocity on ball.
     */

    public void moveOneStep() {
        // calculating the wanted trajectory of ball
        Line trajectory = new Line(this.center, this.velocity.applyToPoint(this.center));
        CollisionInfo collisionInfo = this.gameEnvironment.getClosestCollision(trajectory);


        // if there is a collision then we want to change the velocity
        if (collisionInfo != null && collisionInfo.collisionPoint() != null) {
            // if ball inside a collidable
            if (this.inCollidable(collisionInfo.collisionObject())) {
                double x = collisionInfo.collisionObject().getCollisionRectangle().getCenter().getX();
                double y = collisionInfo.collisionObject().getCollisionRectangle().getUpperLeft().getY();
                this.center = new Point(x, y);
                this.setVelocity(this.velocity.getDx(), -Math.abs(this.velocity.getDy()));
                this.center = (this.velocity.applyToPoint(this.center));
                return;
            }
            Velocity newVal = collisionInfo.collisionObject().hit(collisionInfo.collisionPoint(), this.velocity);
            this.setVelocity(newVal.getDx(), newVal.getDy());
            double dx = this.velocity.getDx();
            double dy = this.velocity.getDy();
            Velocity outOfCollision = new Velocity(dx / Math.abs(dx), dy / Math.abs(dy));
            this.center = outOfCollision.applyToPoint(this.center);
        } else { // if nothing disturbing the trajectory then move the ball
            this.center = trajectory.end();
        }

    }

    /**
     * this function is adding the ball to the sprites objects of game.
     *
     * @param g game
     */
    public void addToGame(Game g) {
        g.addSprite(this);
    }

}
