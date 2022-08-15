// 327721544 Bar Kirshenboim

import biuoop.DrawSurface;

/**
 * defining sprite interface.
 */
public interface Sprite {
    /**
     * draw the sprite to the screen.
     *
     * @param d drawer
     */
    void drawOn(DrawSurface d);

    /**
     * notify the sprite that time has passed.
     */
    void timePassed();
}