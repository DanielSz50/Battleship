package sample;

class Ship {
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

        // Calculate ship stamina
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


    public boolean isDestroyed() {
        return destroyed;
    }
}
