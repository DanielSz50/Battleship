package sample;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class Grid {
    private GridPane gridPane;
    private ArrayList<ArrayList<GridCell>> gameBoard;
    private ArrayList<Ship> ships;

    Grid(GridPane gridPane) {
        this.gridPane = gridPane;
        this.gameBoard = new ArrayList<ArrayList<GridCell>>(10);
        this.ships = new ArrayList<Ship>();
    }

    public void initGrid() {
        for (int i = 0; i < 10; i++) {
            ArrayList<GridCell> row = new ArrayList(10);
            for (int j = 0; j < 10; j++) {
                row.add(new GridCell());
            }
            gameBoard.add(row);
        }

        ships.clear();
        resetGridPane();
    }

    public void resetGridPane() {
        GridPane gridPane = this.gridPane;
        for (Node node : gridPane.getChildren()) {
            Integer x = GridPane.getColumnIndex(node);
            Integer y = GridPane.getRowIndex(node);

            if (x == null) {
                GridPane.setColumnIndex(node, 0);
            }

            if (y == null) {
                GridPane.setRowIndex(node, 0);
            }

            ((Rectangle)node).setFill(Color.BLACK);
        }
    }

    public void addShip(Ship ship) {
        ships.add(ship);
        for (int i = ship.getStartX(); i <= ship.getEndX(); i++) {
            for (int j = ship.getStartY(); j <= ship.getEndY(); j++) {

                // TODO: HANDLE ERROR DIFFERENTLY, OKK?
                if (gameBoard.get(i).get(j).getShip() == null) {
                    gameBoard.get(i).get(j).setShip(ship);
                }
                else {
                    System.out.println("okk");
                }
            }
        }
    }

    public boolean gameOver() {
        for(Ship s : ships) {
            if (!s.isDestroyed()) {
                return false;
            }
        }
        return true;
    }

    public void guess(int x, int y) {
        Ship ship = gameBoard.get(x).get(y).getShip();
        Node node;
        if (ship != null) {
            if (ship.hit()) {
                for (int i = ship.getStartX(); i <= ship.getEndX(); i++) {
                    for (int j = ship.getStartY(); j <= ship.getEndY(); j++) {
                        node = this.getNode(x, y);
                        ((Rectangle)node).setFill(Color.GREEN);
                    }
                }
            }
            else {
                node = this.getNode(x, y);
                ((Rectangle)node).setFill(Color.YELLOW);
            }
        }
        gameBoard.get(x).get(y).setClicked(true);
    }

    Node getNode(int col, int row) {
        GridPane gridPane = this.gridPane;
        for (Node node : gridPane.getChildren()) {
            if (node == null) {
                return null;
            }
            if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                return node;
            }
        }
        return null;
    }
}
