import biuoop.DrawSurface;

import java.awt.Color;


/**
 * defining Ball class with center point, radius and color.
 */
public class Ball {
    private static final double ERROR = 1;
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
     * this function moves the ball one step by applying velocity on ball.
     */
    public void moveOneStep() {
        double dx = this.velocity.getDx();
        double dy = this.velocity.getDy();
        // calculating the wanted trajectory of ball
        Line trajectory = new Line(this.center, this.velocity.applyToPoint(this.center));
        CollisionInfo collisionInfo = this.gameEnvironment.getClosestCollision(trajectory);
        // checking if there isnt a collision through the trajectory and the ball can go with no distrubs
        if (collisionInfo == null) {
            this.center = this.velocity.applyToPoint(this.center);
        } else {
            Point collisionPoint = collisionInfo.collisionPoint();
            if (collisionPoint == null) {
                this.center = this.velocity.applyToPoint(this.center);
                return;

            }
            //getting the ball almost to collision point
            double dxAlmostCollide = collisionPoint.getX() - this.center.getX();
            double dyAlmostCollide = collisionPoint.getY() - this.center.getY();
            Velocity getToCollision = new Velocity(dxAlmostCollide, dyAlmostCollide);

            this.center = getToCollision.applyToPoint(this.center);
            //updating velocity with hit function
            this.setVelocity(collisionInfo.collisionObject().hit(collisionPoint, this.velocity));
            this.center = this.velocity.applyToPoint(this.center);

        }

    }

}
