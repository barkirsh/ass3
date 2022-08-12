import biuoop.DrawSurface;

import java.awt.Color;

/**
 * this Block class implements the Collidable interface.
 */
public class Block implements Collidable {
    private static final double ERROR = 0.001;

    private Rectangle rectangle;
    private Color color = Color.BLACK;

    /**
     * this is a constructor of class.
     *
     * @param rectangle - rectangle, block shape
     */
    public Block(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Point getCenter() {
        double x = (this.rectangle.getUpperLeft().getX() + this.rectangle.getWidth()) / 2;
        double y = (this.rectangle.getUpperLeft().getY() + this.rectangle.getHeight()) / 2;
        return new Point(x, y);
    }

    public double getSize() {
        return this.rectangle.getWidth() * this.rectangle.getHeight();
    }

    /**
     * @return rectangle, shape of block
     */
    public Rectangle getCollisionRectangle() {
        return this.rectangle;
    }

    public boolean edgeHit(Point collisionPoint) {
        Point upLeft, upRight, lowLeft, lowRight;
        upLeft = this.rectangle.getUpperLeft();
        upRight = new Point(this.rectangle.getUpperLeft().getX() + this.rectangle.getWidth(), upLeft.getY());
        lowLeft = new Point(upLeft.getX(), this.rectangle.getUpperLeft().getY() + this.rectangle.getHeight());
        lowRight = new Point(upRight.getX(), lowLeft.getY());

        if (upLeft.equals(collisionPoint) || upRight.equals(collisionPoint)
                || lowRight.equals(collisionPoint) || lowLeft.equals(collisionPoint)) {
            return true;
        }
        return false;
    }

    /**
     * this function returns the hit velocity after a collision.
     *
     * @param collisionPoint  - collision point
     * @param currentVelocity - pre hit velocity
     * @return post hit velocity
     */
    public Velocity hit(Point collisionPoint, Velocity currentVelocity) {
        // notifying the block that there is a collision
        //this.notify();
        if (this.edgeHit(collisionPoint)) {
            return new Velocity(-currentVelocity.getDx(), -currentVelocity.getDy());

        }
        if (Math.abs(this.rectangle.getUpperLeft().getY() - collisionPoint.getY())
                <= ERROR
                || Math.abs(this.rectangle.getUpperLeft().getY() + this.rectangle.getHeight() - collisionPoint.getY())
                <= ERROR) {
            //if (Math.abs(this.rectangle.getUpperLeft().getX() - collisionPoint.getX())
            //      <= ERROR
            //  || Math.abs(this.rectangle.getUpperLeft().getX() + this.rectangle.getWidth() - collisionPoint.getX())
            //    <= ERROR) {
            //return new Velocity(-currentVelocity.getDx(), -currentVelocity.getDy());

            //}

            // vertical collision
            return new Velocity(currentVelocity.getDx(), -currentVelocity.getDy());
        } else if (Math.abs(this.rectangle.getUpperLeft().getX() - collisionPoint.getX())
                <= ERROR
                || Math.abs(this.rectangle.getUpperLeft().getX() + this.rectangle.getWidth() - collisionPoint.getX())
                <= ERROR) {
            //horizontal collision
            return new Velocity(-currentVelocity.getDx(), currentVelocity.getDy());
        }
        return null;
    }


    /**
     * this function draw this block on the given DrawSurface.
     *
     * @param surface - given DrawSurface
     */
    public void drawOn(DrawSurface surface) {
        if (surface == null) {
            return;
        }
        surface.setColor(this.color);
        Point upperLeft = this.rectangle.getUpperLeft();
        double width = this.rectangle.getWidth();
        double height = this.rectangle.getHeight();
        surface.fillRectangle((int) upperLeft.getX(), (int) upperLeft.getY(), (int) width, (int) height);
    }
}
