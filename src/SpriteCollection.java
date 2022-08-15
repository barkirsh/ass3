// 327721544 Bar Kirshenboim

import biuoop.DrawSurface;

import java.util.ArrayList;

/**
 * sprites class.
 */
public class SpriteCollection {
    private final java.util.List<Sprite> spriteList = new ArrayList<>();

    /**
     * adding sprite.
     *
     * @param s sprite
     */
    public void addSprite(Sprite s) {
        this.spriteList.add(s);
    }

    /**
     * call timePassed() on all sprites.
     */
    public void notifyAllTimePassed() {
        for (Sprite sprite : this.spriteList) {
            sprite.timePassed();
        }
    }

    /**
     * call drawOn(d) on all sprites.
     *
     * @param d drawer
     */
    public void drawAllOn(DrawSurface d) {
        for (Sprite sprite : this.spriteList) {
            sprite.drawOn(d);
        }
    }
}