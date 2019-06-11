package sample;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

class Grid {
    private GridPane gridPane;
    private ArrayList<ArrayList<GridCell>> gameBoard;
    private ArrayList<Ship> ships;
    private boolean gameOver;

    Grid(GridPane gridPane) {
        this.gridPane = gridPane;
        this.gameBoard = new ArrayList<>(10);
        this.ships = new ArrayList<>();
    }

    // Initialize the grid
    void initGrid() {
        gameOver = false;
        gameBoard.clear();

        ArrayList<GridCell> row;
        for (int i = 0; i < 10; i++) {
            row = new ArrayList(10);
            for (int j = 0; j < 10; j++) {
                row.add(new GridCell());
            }
            gameBoard.add(row);
        }

        ships.clear();
        resetGridPane();
    }

    // Reset the grid
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

    // Add ship to the gameBoard
    void addShip(Ship ship) {
        ships.add(ship);

        for (int i = ship.getStartX(); i <= ship.getEndX(); i++) {
            for (int j = ship.getStartY(); j <= ship.getEndY(); j++) {
                try {
                    gameBoard.get(i).get(j).setShip(ship);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Check if the game is over
    private boolean gameOver() {
        for(Ship s : ships) {
            if (!s.isDestroyed()) {
                return false;
            }
        }
        return true;
    }

    // Guess the grid cell
    boolean guess(int x, int y) {
        final Color hitSetFill = Color.DARKORCHID;
        final Color missSetFill = Color.DARKGRAY;
        final Color destroyedSetFill = Color.DARKKHAKI;

        Ship ship = gameBoard.get(x).get(y).getShip();
        Rectangle r = (Rectangle) this.getNode(x, y);
        boolean hit = false;

        if (!gameBoard.get(x).get(y).isClicked()) {
            if (ship != null) {
                if (ship.hit()) {
                    for (int i = ship.getStartX(); i <= ship.getEndX(); i++) {
                        for (int j = ship.getStartY(); j <= ship.getEndY(); j++) {
                            r = (Rectangle) this.getNode(i, j);
                            if (r != null) {
                                r.setFill(destroyedSetFill);
                            }
                        }
                    }

                    if (this.gameOver()) {
                        gameOver = true;
                    }
                }
                else {
                    if (r != null) {
                        r.setFill(hitSetFill);
                    }

                    System.out.println("HIT");
                }

                hit = true;
            }
            else {
                if (r != null) {
                    r.setFill(missSetFill);
                }

                System.out.println(":(");
            }

            gameBoard.get(x).get(y).setClicked(true);
        }

        return hit;
    }


    private Node getNode(int col, int row) {
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

    boolean isGameOver() {
        return gameOver;
    }
}
