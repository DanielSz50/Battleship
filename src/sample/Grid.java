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
    private boolean gameOver;

    Grid(GridPane gridPane) {
        this.gridPane = gridPane;
        this.gameBoard = new ArrayList<>(10);
        this.ships = new ArrayList<>();
    }

    void initGrid() {
        gameOver = false;
        gameBoard.clear();

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

    private void resetGridPane() {
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

    void addShip(Ship ship) {
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

    private boolean gameOver() {
        for(Ship s : ships) {
            if (!s.isDestroyed()) {
                return false;
            }
        }
        return true;
    }

    boolean guess(int x, int y) {
        final Color hitSetFill = Color.DARKORCHID;
        final Color missSetFill = Color.DARKGRAY;
        final Color destroyedSetFill = Color.DARKKHAKI;

        Ship ship = gameBoard.get(x).get(y).getShip();
        Rectangle r = (Rectangle) this.getNode(x, y);
        boolean hit = false;

        if (gameBoard.get(x).get(y).isClicked() == false) {
            if (ship != null) {
                if (ship.hit()) {
                    for (int i = ship.getStartX(); i <= ship.getEndX(); i++) {
                        for (int j = ship.getStartY(); j <= ship.getEndY(); j++) {
                            r = (Rectangle) this.getNode(i, j);
                            r.setFill(destroyedSetFill);
                        }
                    }

                    if (this.gameOver()) {
                        gameOver = true;
                    }
                } else {
                    r.setFill(hitSetFill);
                    System.out.println("HIT");
                }
                hit = true;
            } else {
                r.setFill(missSetFill);
                System.out.println(":(");
            }
            gameBoard.get(x).get(y).setClicked(true);
        }

        return hit;
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

    GridPane getGridPane() {
        return this.gridPane;
    }

    public ArrayList<ArrayList<GridCell>> getGameBoard() {
        return gameBoard;
    }

    boolean isGameOver() {
        return gameOver;
    }
}
