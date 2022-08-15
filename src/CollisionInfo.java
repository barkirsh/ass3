// 327721544 Bar Kirshenboim

/**
 * Collision info class is responsible on the collision information.
 */
public class CollisionInfo {
    private final Point pointOfCollision;
    private final Collidable shapeOfSecObject;

    /**
     * setter to collision information.
     *
     * @param pointOfCollision the collision point
     * @param shapeOfSecObject the other object shape
     */
    public CollisionInfo(Point pointOfCollision, Collidable shapeOfSecObject) {
        this.pointOfCollision = pointOfCollision;
        this.shapeOfSecObject = shapeOfSecObject;
    }

    /**
     * get the point at which the collision occurs.
     *
     * @return point of collision
     */
    public Point collisionPoint() {
        return this.pointOfCollision;
    }

    /**
     * get the collidable object involved in the collision.
     *
     * @return collision sec object
     */
    public Collidable collisionObject() {
        return this.shapeOfSecObject;
    }
}