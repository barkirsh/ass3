/**
 * Collision info class is responsible on the collision information.
 */
public class CollisionInfo {
    private Point pointOfCollision;
    private Collidable shapeOfSecObject;

    public CollisionInfo(Point pointOfCollision, Collidable shapeOfSecObject) {
        this.pointOfCollision = pointOfCollision;
        this.shapeOfSecObject = shapeOfSecObject;
    }

    // the point at which the collision occurs.
    public Point collisionPoint() {
        return this.pointOfCollision;
    }

    // the collidable object involved in the collision.
    public Collidable collisionObject() {
        return this.shapeOfSecObject;
    }
}