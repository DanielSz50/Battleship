package sample;

public class GridCell {
    private boolean clicked;
    private Ship ship;

    // CONSTRUCTORS
    public GridCell() {
        this.clicked = false;
        this.ship = null;
    }

    public GridCell(Ship ship) {
        this.ship = ship;
    }

    // SET METHODS
    public void setClicked(boolean clicked) {
        this.clicked = clicked;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    // RETURN METHODS
    public boolean isClicked() {
        return clicked;
    }

    public Ship getShip() {
        return ship;
    }
}
