package sample;

import javafx.scene.Node;

public class Ship {
    private final int startX;
    private final int startY;
    private final int endX;
    private final int endY;
    private final int stamina;
    private int hits;
    private boolean destroyed;

    public Ship(int startX, int startY, int endX, int endY) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;

        if (endX - startX > endY - startY) {
            this.stamina = endX - startX + 1;
        }
        else {
            this.stamina = endY - startY + 1;
        }

        this.hits = 0;
        this.destroyed = false;
    }

    public boolean hit() {
        hits++;
        if (hits >= stamina) {
            destroyed = true;
        }
        return destroyed;
    }

    public int getStartX() {
        return startX;
    }

    public int getStartY() {
        return startY;
    }

    public int getEndX() {
        return endX;
    }

    public int getEndY() {
        return endY;
    }

    public int getHits() {
        return hits;
    }

    public int getStamina() {
        return stamina;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }
}
