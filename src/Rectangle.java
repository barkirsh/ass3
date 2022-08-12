import java.util.ArrayList;

public class Rectangle {
    private Point upperLeft;
    private double width;
    private double height;


    /**
     * Create a new rectangle with location and width/height.
     *
     * @param upperLeft location of rectangle
     * @param width     width of rectangle
     * @param height    height of rectangle
     */
    public Rectangle(Point upperLeft, double width, double height) {
        this.upperLeft = upperLeft;
        this.height = Math.abs(height);
        this.width = Math.abs(width);
    }

    //getters and setters

    /**
     * @return the width of the rectangle.
     */
    public double getWidth() {
        return this.width;
    }

    /**
     * @return the height of the rectangle.
     */
    public double getHeight() {
        return this.height;
    }

    /**
     * @return the upper-left point of the rectangle.
     */
    public Point getUpperLeft() {
        return this.upperLeft;
    }

    //place for setters if we need some

    /**
     * @param line to check with intersection points
     * @return a (possibly empty) List of intersection points with the specified line.
     */
    public java.util.List<Point> intersectionPoints(Line line) {
        // setting rectangle points for cleaner code
        Point upperRight = new Point(this.upperLeft.getX() + width, this.upperLeft.getY());
        Point lowerLeft = new Point(this.upperLeft.getX(), this.upperLeft.getY() + height);
        Point lowerRight = new Point(this.upperLeft.getX() + width, this.upperLeft.getY() + height);
        // setting rectangle's lines for cleaner code
        Line upLine = new Line(this.upperLeft, upperRight);
        Line rightLine = new Line(upperRight, lowerRight);
        Line leftLine = new Line(this.upperLeft, lowerLeft);
        Line lowerLine = new Line(lowerLeft, lowerRight);
        // array of rectangle lines
        Line[] recLines = {upLine, leftLine, rightLine, lowerLine};

        java.util.List<Point> crossPoints = new ArrayList<>(); //creating the intersectionPoints list
        Point cross = null;
        for (int i = 0; i < recLines.length; i++) {
            cross = line.intersectionWith(recLines[i]);
            if (cross != null) {
                crossPoints.add(cross);
            }
        }
        return crossPoints;
    }

}