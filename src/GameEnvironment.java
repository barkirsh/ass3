// 327721544 Bar Kirshenboim

import java.awt.Color;
import java.util.ArrayList;

/**
 * this is the game environment.
 */
public class GameEnvironment {
    private final java.util.List<Collidable> environment = new ArrayList<>();

    /**
     * add the given collidable to the environment.
     *
     * @param c - a new collidable item
     */
    public void addCollidable(Collidable c) {

        this.environment.add(c);
    }

    /**
     * Assume an object moving from line.start() to line.end().
     * If this object will not collide with any of the collidables
     * in this collection, return null. Else, return the information
     * about the closest collision that is going to occur.
     *
     * @param trajectory the trajectory
     * @return collision info
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        CollisionInfo collisionInfo;
        // setting the ball placement
        Ball object = new Ball(trajectory.start(), 1, Color.black);
        object.setVelocity(trajectory.end().getX() - trajectory.start().getX(),
                trajectory.end().getY() - trajectory.start().getY());
        object.setGameEnvironment(this);
        java.util.List<Collidable> tmp = this.environment;
        Point closestCollidable =
                trajectory.closestIntersectionToStartOfLine(tmp.get(0).getCollisionRectangle());
        //intilaizing collision info
        collisionInfo = new CollisionInfo(closestCollidable, tmp.get(0));
        for (Collidable collidable : tmp) {
            Point crossCollidableWithLine =
                    trajectory.closestIntersectionToStartOfLine(collidable.getCollisionRectangle());
            if (crossCollidableWithLine == null) {
                continue;
            } else if (closestCollidable == null) {
                closestCollidable = crossCollidableWithLine;
                collisionInfo = new CollisionInfo(closestCollidable, collidable);
                continue;

            }
            //checking if the i collide is colser to the ball
            if (trajectory.distancePointFromLine(crossCollidableWithLine)
                    <= trajectory.distancePointFromLine(closestCollidable)) {
                closestCollidable = crossCollidableWithLine;
                collisionInfo = new CollisionInfo(closestCollidable, collidable);
            }
        }
        return collisionInfo;

    }

}