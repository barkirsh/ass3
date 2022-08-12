/**
 * this class defines a point.
 */
public class Point {
    private static final double ERROR = 0.001;
    private double x;
    private double y;

    // constructor

    /**
     * this is a constructor function to create and initialized a new point.
     *
     * @param x - x value of this point
     * @param y - y value of this point
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * this function calculates the distance of this one point to the other point.
     *
     * @param other - point
     * @return the distance between the 2 points
     */
    public double distance(Point other) {
        if (other == null) {
            System.out.println("DISTANCE: other null ! ");
            return -1;
        }
        double deltaX, deltaY, powX, powY;
        deltaX = (this.getX() - other.getX());
        //System.out.println("deltaX: "+ deltaX);

        deltaY = (this.getY() - other.getY());
        //System.out.println("deltaY: "+ deltaY);

        powX = deltaX * deltaX;
        powY = deltaY * deltaY;
        double underSqrt = powX + powY;
        return Math.sqrt(underSqrt);
    }

    /**
     * this function check and return true if this point and other are equal, else return false.
     *
     * @param other - point
     * @return if the points are equal - true, else - false
     */
    public boolean equals(Point other) {
        if (other == null) {
            System.out.println("EQUALS: other is null ! ");
            return false;
        }
        return Math.abs(this.x - other.x) <= ERROR && Math.abs(this.y - other.y) <= ERROR;
    }

    /**
     * this function returns the x value of this point.
     *
     * @return x value of this point
     */
    public double getX() {
        return this.x;
    }

    /**
     * this function returns the y value of this point.
     *
     * @return y value of this point
     */
    public double getY() {
        return this.y;
    }

    /**
     * this function let us make x value change.
     *
     * @param x - x value of this point
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * this function let us make y value change.
     *
     * @param y - y value of this point
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * this function checks if this point is on the line between start point and end point.
     *
     * @param start - start point of line
     * @param end   - end point of line
     * @return true if this point is on line, else - false
     */
    public boolean isOnLine(Point start, Point end) {
        if (start == null || end == null) {
            System.out.println("oops something went wrong in ISONLINE func ");
            return false;
        }
        double x = this.getX();
        double y = this.getY();

        if (x > start.getX() && x > end.getX()) {
            return false;
        } else if (x < start.getX() && x < end.getX()) {
            return false;
        } else if (y > start.getY() && y > end.getY()) {
            return false;
        } else if (y < start.getY() && y < end.getY()) {
            return false;
        }
        return true;

    }
}

