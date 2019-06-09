package sample;

public class GridCell {
    private boolean clicked;
    private Ship ship;

    public GridCell() {
        this.clicked = false;
        this.ship = null;
    }

    public void setClicked(boolean clicked) {
        this.clicked = clicked;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public boolean isClicked() {
        return clicked;
    }

    public Ship getShip() {
        return ship;
    }
}
