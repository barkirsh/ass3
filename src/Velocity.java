/**
 * Velocity specifies the change in position on the `x` and the `y` axes.
 */
public class Velocity {
    private double dx;
    private double dy;

    /**
     * constructor of class.
     *
     * @param dx - delta x
     * @param dy - delta y
     */
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * @return dy value of velocity
     */
    public double getDy() {
        return dy;
    }

    /**
     * re - set dy value of velocity.
     *
     * @param dy - dy value of velocity
     */
    public void setDy(double dy) {
        this.dy = dy;
    }

    /**
     * re - set dx value of velocity.
     *
     * @param dx - dx value of velocity
     */
    public void setDx(double dx) {
        this.dx = dx;
    }

    /**
     * @return dx value of velocity
     */
    public double getDx() {
        return dx;
    }

    /**
     * Take a point with position (x,y) and return a new point with position (x+dx, y+dy).
     *
     * @param p - point
     * @return point
     */
    public Point applyToPoint(Point p) {
        if (p == null) {
            return null;
        }
        double newX = p.getX() + this.dx;
        double newY = p.getY() + this.dy;
        return new Point(newX, newY);
    }

    /**
     * Take an angle and speed of velocity and apply to point then return the new point.
     *
     * @param angle - angle of velocity
     * @param speed - size of velocity
     * @return - point
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        double dx = Math.cos(angle * (Math.PI / 180)) * speed;
        double dy = Math.sin(angle * (Math.PI / 180)) * speed;
        return new Velocity(dx, dy);
    }
}
