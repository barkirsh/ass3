//327721544 Bar Kirshenboim

/**
 * this class defines a line with start & end points.
 */
public class Line {
    private static final double ERROR = 0.001;
    private final Point startPoint;
    private final Point endPoint;

    // constructors

    /**
     * this is a constructor function that initialized a line.
     *
     * @param start - start point of line
     * @param end   - end point of line
     */
    public Line(Point start, Point end) {
        this.endPoint = end;
        this.startPoint = start;
    }

    /**
     * @param x1 - x of start point of line
     * @param y1 - y of start point of line
     * @param x2 - x of end point of line
     * @param y2 - y of end point of line
     */
    public Line(double x1, double y1, double x2, double y2) {
        this.startPoint = new Point(x1, y1);
        this.endPoint = new Point(x2, y2);
    }

    /**
     * @return start point of this line
     */
    public Point start() {
        return this.startPoint;
    }

    /**
     * @return end point of this line
     */
    public Point end() {
        return this.endPoint;
    }

    /**
     * this function calculates and returns the slope of this line.
     *
     * @return slope of this line
     */
    public Double lineM() {
        double deltaX, deltaY;
        deltaX = this.endPoint.getX() - this.startPoint.getX();
        deltaY = this.endPoint.getY() - this.startPoint.getY();
        if (Math.abs(deltaX) <= ERROR) {
            return null;
        } else if (Math.abs(deltaY) <= ERROR) {
            return 0.0;
        }
        return deltaY / deltaX;
    }

    /**
     * @return the const part of line
     */
    public Double lineB() {
        double b;
        if (this.lineM() == null) {
            return null;
        }
        b = this.startPoint.getY() - this.lineM() * this.startPoint.getX();
        return b;
    }

    /**
     * @return the length of this line
     */
    public double length() {
        return this.startPoint.distance(this.endPoint);
    }

    /**
     * @return middle point of line
     */
    public Point middle() {
        double midX = (this.startPoint.getX() + this.endPoint.getX()) / 2;
        double midY = (this.startPoint.getY() + this.endPoint.getY()) / 2;
        return new Point(midX, midY);
    }

    /**
     * @param other - other line
     * @return true if lines intersecting and false otherwise
     */
    public boolean isIntersecting(Line other) {
        if (other == null) {
            return false;
        } else if (this.equals(other)) {
            return false;
        } else if (this.lineM().equals(other.lineM())) { // checking if the lines are equal in some part
            return this.start().isOnLine(other.start(), other.end())
                    || this.end().isOnLine(other.start(), other.end());
        } else { //checking if there is a cross points when slopes unequals
            return this.intersectionWith(other) != null;
        }
    }

    /**
     * Returns the intersection point if the lines intersect,
     * and null otherwise.
     *
     * @param other - other line
     * @return cross point
     */
    public Point intersectionWith(Line other) {
        if (other == null) {
            System.out.println("null exception intersectionWith");
            return null;
        }
        Double m1;
        Double m2;
        Double b1, b2;
        m1 = this.lineM();
        m2 = other.lineM();
        b1 = this.lineB();
        b2 = other.lineB();
        double x, y;
        // if the lines has the same M parameter then we can't find cross point
        if (m1 == null && m2 == null) {
            return null;
        } else if (m1 == null || m2 == null) { // if one of the lines slope is null
            if (m1 == null) {
                x = this.startPoint.getX();
                y = m2 * x + b2;
            } else {
                x = other.startPoint.getX();
                y = m1 * x + b1;
            }
        } else if (m1.equals(m2)) {
            return null;
        } else if (Math.abs(m1) <= ERROR || Math.abs(m2) <= ERROR) { // if one of the lines slopes is 0
            if (Math.abs(m1) <= ERROR) {
                y = b1;
                x = (b1 - b2) / m2; // math sol to find x val
            } else {
                y = b2;
                x = (b2 - b1) / m1; // math sol to find x val
            }
        } else { // calculation of x value of cross point - math solution
            x = (b2 - b1) / (m1 - m2);
            y = m1 * x + b1;
        }


        Point point = new Point(x, y);
        // making sure the cross point is in the range of the 2 lines
        if (point.isOnLine(this.start(), this.end()) && point.isOnLine(other.start(), other.end())) {
            return point;
        }
        return null;
    }

    /**
     * equals -- return true is the lines are equal, false otherwise.
     *
     * @param other - other line
     * @return true/false based on result
     */
    public boolean equals(Line other) {
        if (this.startPoint.equals(other.startPoint)) { //checking if the start points are equals
            // making sure that the end points are equal as well
            return this.endPoint.equals(other.endPoint);
        } else if (this.startPoint.equals(other.endPoint)) { //the case that one line is the exact opposite ot  2nd
            return this.endPoint.equals(other.startPoint);
        }
        //}
        return false;
    }

    /**
     * from here are the new function that were added to this specific ex
     */
    /**
     * @param p point needed to be checked
     * @return true if point is on y range, false otherwise
     */
    public boolean isOnRangeY(Point p) {
        if (p == null) {
            return false;
        }
        return (p.getY() <= this.start().getY() && p.getY() >= this.end().getY())
                || (p.getY() <= this.end().getY() && p.getY() >= this.start().getY());
    }

    /**
     * @param p point needed to be checked
     * @return true if point is on x range, false otherwise
     */
    public boolean isOnRangeX(Point p) {
        return (p.getX() <= this.start().getX() && p.getX() >= this.end().getX())
                || (p.getX() <= this.end().getX() && p.getX() >= this.start().getX());
    }

    /**
     * @param p point
     * @return distance of given point to this line using math formulas
     */
    public double distancePointFromLine(Point p) {
        // default def for distance point from line
        Double slope = this.lineM();
        Double lineConst = this.lineB();
        if (p == null) {
            System.out.println("null point in distance from line func(line)");
            return -1;
        }
        if (slope == null) { // this line is parallel to y.
            if (this.isOnRangeY(p)) {
                return Math.abs(p.getX() - this.startPoint.getX());
            }
        } else if (slope <= ERROR) { // this line is parallel to x.
            if (this.isOnRangeX(p)) {
                return Math.abs(p.getY() - this.startPoint.getY());
            }
        } else if (this.isOnRangeX(p) || this.isOnRangeY(p)) { // the point is on range for calculation
            // distance calculation using math formulas
            return Math.abs((slope * p.getX() - p.getY() + lineConst) / Math.sqrt(slope * slope + 1));
        }

        return this.middle().distance(p);
    }

    /**
     * If this line does not intersect with the rectangle, return null.
     * Otherwise, return the closest intersection point to the start of the line.
     *
     * @param rect rectangle
     * @return closest intersection point to the start of line
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        java.util.List<Point> pointList = rect.intersectionPoints(this);
        Point[] points = pointList.toArray(new Point[2]);
        if (pointList == null) {
            return null;
        } else if (points[1] == null) {
            return points[0];
        } else {
            if (this.distancePointFromLine(points[0]) - this.distancePointFromLine(points[1]) <= ERROR) {
                return points[0];
            }
            return points[1];
        }
    }


}
