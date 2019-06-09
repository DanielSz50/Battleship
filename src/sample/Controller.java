package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.InputEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private int initialShipWidth = 30;
    private ArrayList<Rectangle> rectangleShips;
    private int layoutReset;
    private Grid player1Grid;
    private Grid player2Grid;
    private int cellSize = 30;

    @FXML
    private Button startButton;
    @FXML
    private Button player1Button;
    @FXML
    private Button player2Button;
    @FXML
    private GridPane player1DisplayBoard;
    @FXML
    private GridPane player2DisplayBoard;
    @FXML
    private Label player1Label;
    @FXML
    private Label player2Label;
    @FXML
    private Rectangle ship1_p1;
    @FXML
    private Rectangle ship2_p1;
    @FXML
    private Rectangle ship3_p1;
    @FXML
    private Rectangle ship4_p1;
    @FXML
    private Rectangle ship5_p1;
    @FXML
    private Rectangle ship6_p1;
    @FXML
    private Rectangle ship7_p1;
    @FXML
    private Rectangle ship8_p1;
    @FXML
    private Rectangle ship1_p2;
    @FXML
    private Rectangle ship2_p2;
    @FXML
    private Rectangle ship3_p2;
    @FXML
    private Rectangle ship4_p2;
    @FXML
    private Rectangle ship5_p2;
    @FXML
    private Rectangle ship6_p2;
    @FXML
    private Rectangle ship7_p2;
    @FXML
    private Rectangle ship8_p2;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        player1Grid = new Grid(player1DisplayBoard);
        player2Grid = new Grid(player2DisplayBoard);
        this.rectangleShips = new ArrayList<>();
        addShipsToArray();
        reset();
    }

    private void reset() {

        player1Grid.initGrid();
        player2Grid.initGrid();

        player1Label.setVisible(false);
        player2Label.setVisible(false);

        startButton.setDisable(false);
        startButton.setVisible(true);

        setDisablePlayer1Ships(true);
        setDisablePlayer2Ships(true);

        installStartButton(startButton);
        installPlayer1Button(player1Button);
        installPlayer2Button(player2Button);

        layoutReset = 0;
        resetShipLayout(ship1_p1);
        installShipListeners(ship1_p1);
        resetShipLayout(ship2_p1);
        installShipListeners(ship2_p1);
        resetShipLayout(ship3_p1);
        installShipListeners(ship3_p1);
        resetShipLayout(ship4_p1);
        installShipListeners(ship4_p1);
        resetShipLayout(ship5_p1);
        installShipListeners(ship5_p1);
        resetShipLayout(ship6_p1);
        installShipListeners(ship6_p1);
        resetShipLayout(ship7_p1);
        installShipListeners(ship7_p1);
        resetShipLayout(ship8_p1);
        installShipListeners(ship8_p1);

        layoutReset = 394;
        resetShipLayout(ship8_p2);
        installShipListeners(ship8_p2);
        resetShipLayout(ship7_p2);
        installShipListeners(ship7_p2);
        resetShipLayout(ship6_p2);
        installShipListeners(ship6_p2);
        resetShipLayout(ship5_p2);
        installShipListeners(ship5_p2);
        resetShipLayout(ship4_p2);
        installShipListeners(ship4_p2);
        resetShipLayout(ship3_p2);
        installShipListeners(ship3_p2);
        resetShipLayout(ship2_p2);
        installShipListeners(ship2_p2);
        resetShipLayout(ship1_p2);
        installShipListeners(ship1_p2);

        setVisiblePlayer1Ships(true);
        setVisiblePlayer2Ships(true);

        player1DisplayBoard.setDisable(true);
        player2DisplayBoard.setDisable(true);
    }

    private void resetShipLayout(Rectangle ship) {
        ship.setLayoutX(48 + layoutReset);
        ship.setLayoutY(349);
        if (ship.getWidth() != initialShipWidth) {
            ship.setHeight(ship.getWidth());
            ship.setWidth(initialShipWidth);
        }

        layoutReset += 40;
    }

    class Delta {
        double x, y;
    }

    private void addShipsToArray() {
        rectangleShips.add(ship1_p1);
        rectangleShips.add(ship2_p1);
        rectangleShips.add(ship3_p1);
        rectangleShips.add(ship4_p1);
        rectangleShips.add(ship5_p1);
        rectangleShips.add(ship6_p1);
        rectangleShips.add(ship7_p1);
        rectangleShips.add(ship8_p1);

        rectangleShips.add(ship1_p2);
        rectangleShips.add(ship2_p2);
        rectangleShips.add(ship3_p2);
        rectangleShips.add(ship4_p2);
        rectangleShips.add(ship5_p2);
        rectangleShips.add(ship6_p2);
        rectangleShips.add(ship7_p2);
        rectangleShips.add(ship8_p2);
    }

    private void installShipListeners(Rectangle ship) {

        int player;
        if (ship.getLayoutX() < 400) {
            player = 1;
        }
        else {
            player = 2;
        }

        int shipHeight = (int) ship.getHeight();
        final Delta dragDelta = new Delta();

        ship.setOnMousePressed(mouseEvent -> {
            dragDelta.x = ship.getLayoutX() - mouseEvent.getSceneX();
            dragDelta.y = ship.getLayoutY() - mouseEvent.getSceneY();
            ship.setCursor(Cursor.MOVE);
        });

        ship.setOnMouseDragged(mouseEvent -> {

            int translate = 0;

            if (mouseEvent.getButton() == MouseButton.SECONDARY) {
                return;
            }

            if (ship.getWidth() == shipHeight) {
                translate = (initialShipWidth - cellSize) / 2;
            }

            if (player == 1) {

                int col = (int) (mouseEvent.getSceneX() + dragDelta.x - 42) / cellSize;
                int x = col * cellSize + 53 - translate; // 53 = distance from grid to left border
                int row = (int) (mouseEvent.getSceneY() + dragDelta.y - shipHeight / 6) / cellSize;
                int y = row * cellSize + 28 + translate; // 28 = distance from grid to top border

                ///////////////////////////////////////////////
                /// HANDLE DRAGGING OUTSIDE THE GIVEN SPACE ///
                if (ship.getHeight() == shipHeight) {
                    if (x > 370) {
                        x = 370;
                    }

                    if (x < 0) {
                        x = 0;
                    }

                    if (y < 0) {
                        y = 0;
                    }

                    if (y > 520 - shipHeight) {
                        y = 520 - shipHeight;
                    }
                }
                else {
                    if (x > 400 - shipHeight) {
                        x = 400 - shipHeight;
                    }

                    if (x < 0) {
                        x = 0;
                    }

                    if (y < 0) {
                        y = 0;
                    }

                    if (y > 490) {
                        y = 490;
                    }
                }
                // DONE //
                /////////

                ship.setLayoutX(x);
                ship.setLayoutY(y);
            }

            if (player == 2) {
                int col = (int) (mouseEvent.getSceneX() + dragDelta.x - 440) / cellSize;
                int x = col * cellSize + 447 - translate; // 447 = distance from grid to left border
                int row = (int) (mouseEvent.getSceneY() + dragDelta.y - shipHeight / 6) / cellSize;
                int y = row * cellSize + 28 + translate; // 28 = distance from grid to top border

                ///////////////////////////////////////////////
                /// HANDLE DRAGGING OUTSIDE THE GIVEN SPACE ///
                if (ship.getHeight() == shipHeight) {
                    if (x < 400) {
                        x = 400;
                    }

                    if (x > 770) {
                        x = 770;
                    }

                    if (y < 0) {
                        y = 0;
                    }

                    if (y > 520 - shipHeight) {
                        y = 520 - shipHeight;
                    }
                }
                else {
                    if (x < 400) {
                        x = 400;
                    }

                    if (x > 800 - shipHeight) {
                        x = 800 - shipHeight;
                    }

                    if (y < 0) {
                        y = 0;
                    }

                    if (y > 490) {
                        y = 490;
                    }
                }
                // DONE //
                /////////

                ship.setLayoutX(x);
                ship.setLayoutY(y);
            }
        });

        ship.setOnMouseReleased(mouseEvent -> {
            ArrayList<Integer> painted = new ArrayList<>();

            for (int i = 0; i < 16; i++) {
                for (int j = i + 1; j < 16; j++) {
                    if (rectangleShips.get(i).getBoundsInParent().intersects(rectangleShips.get(j).getBoundsInParent())) {
                        rectangleShips.get(i).setFill(Color.RED);
                        rectangleShips.get(j).setFill(Color.RED);
                        painted.add(i);
                        painted.add(j);
                        System.out.println("Collision Detected");
                    }

                }
            }

            for (int i = 0; i < 16; i++) {
                if (!painted.contains(i)) {
                    rectangleShips.get(i).setFill(Color.BLACK);
                }
            }

            painted.clear();
        });

        ship.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.SECONDARY) {
                if (ship.getHeight() == shipHeight) {
                    ship.setHeight(initialShipWidth);
                    ship.setWidth(shipHeight);
                    ship.setLayoutX(ship.getLayoutX());
                    ship.setLayoutY(ship.getLayoutY());

                }
                else {
                    ship.setHeight(shipHeight);
                    ship.setWidth(initialShipWidth);
                    ship.setLayoutX(ship.getLayoutX());
                    ship.setLayoutY(ship.getLayoutY());
                }
            }
        });

        ship.setOnMouseEntered(mouseEvent -> ship.setCursor(Cursor.HAND));
    }

    private boolean checkCollision() {
        for (Rectangle r : rectangleShips) {
            for (Rectangle ship : rectangleShips) {
                if (ship.getBoundsInParent().intersects(r.getBoundsInParent())) {
                    if (ship != r) {
                        return true;
                    }
                }
            }
        }

        for (Rectangle r : rectangleShips) {
            r.setFill(Color.BLACK);
        }

        return false;
    }


    private void placePlayer1Ships() {

        Rectangle r;
        Ship ship;
        int shipHeight;
        int shipWidth;

        for (int i = 1; i <= 8; i++)  {
            switch (i) {
                case 1:
                    r = ship1_p1;
                    break;
                case 2:
                    r = ship2_p1;
                    break;
                case 3:
                    r = ship3_p1;
                    break;
                case 4:
                    r = ship4_p1;
                    break;
                case 5:
                    r = ship5_p1;
                    break;
                case 6:
                    r = ship6_p1;
                    break;
                case 7:
                    r = ship7_p1;
                    break;
                case 8:
                    r = ship8_p1;
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + i);
            }

            if (r.getHeight() == initialShipWidth) {
                shipHeight = initialShipWidth - 1;
                shipWidth = (int) r.getWidth() - 1;
            } else {
                shipWidth = initialShipWidth - 1;
                shipHeight = (int) r.getHeight() - 1;
            }

            int []ok = layoutToGridCell((int) r.getLayoutX(), (int) r.getLayoutY(), (int) r.getLayoutX() + shipWidth, (int) r.getLayoutY() + shipHeight);
            ship = new Ship(ok[0], ok[1], ok[2], ok[3]);

            player1Grid.addShip(ship);
            r.setVisible(false);
            setDisablePlayer2Ships(false);
        }
    }

    private void placePlayer2Ships() {

        Rectangle r;
        Ship ship;
        int shipHeight;
        int shipWidth;

        for (int i = 1; i <= 8; i++)  {
            switch (i) {
                case 1:
                    r = ship1_p2;
                    break;
                case 2:
                    r = ship2_p2;
                    break;
                case 3:
                    r = ship3_p2;
                    break;
                case 4:
                    r = ship4_p2;
                    break;
                case 5:
                    r = ship5_p2;
                    break;
                case 6:
                    r = ship6_p2;
                    break;
                case 7:
                    r = ship7_p2;
                    break;
                case 8:
                    r = ship8_p2;
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + i);
            }

            if (r.getHeight() == initialShipWidth) {
                shipHeight = initialShipWidth - 1;
                shipWidth = (int) r.getWidth() - 1;
            } else {
                shipWidth = initialShipWidth - 1;
                shipHeight = (int) r.getHeight() - 1;
            }

            int []ok = layoutToGridCell((int) r.getLayoutX(), (int) r.getLayoutY(), (int) r.getLayoutX() + shipWidth, (int) r.getLayoutY() + shipHeight);
            ship = new Ship(ok[0], ok[1], ok[2], ok[3]);

            player2Grid.addShip(ship);
            r.setVisible(false);
            player1DisplayBoard.setDisable(false);

            installGridCellListeners(player1Grid);
            installGridCellListeners(player2Grid);
        }
    }

    private int[] layoutToGridCell(int x1, int y1, int x2, int y2) {
        int []rectangle = new int[4];

        if (x1 < 400) {
            rectangle[0] = (x1 - 53) / cellSize;
            rectangle[1] = (y1 - 28) / cellSize;
            rectangle[2] = (x2 - 53) / cellSize;
            rectangle[3] = (y2 - 28) / cellSize;
        }
        else {
            rectangle[0] = (x1 - 447) / cellSize;
            rectangle[1] = (y1 - 28) / cellSize;
            rectangle[2] = (x2 - 447) / cellSize;
            rectangle[3] = (y2 - 28) / cellSize;
        }

        return rectangle;
    }

    private void installStartButton(Button button) {
        button.setOnAction(actionEvent -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Battleship alert");
            alert.setHeaderText(null);
            alert.setContentText("Place your ships on the left board. \nPress OK button if you have finished");

            alert.showAndWait();

            setDisablePlayer1Ships(false);
            startButton.setDisable(true);
            startButton.setVisible(false);
            player1Button.setDisable(false);
            player1Button.setVisible(true);
        });
    }

    @FXML
    private void handleExitButton(InputEvent e) {
        final Node source = (Node) e.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    private void installPlayer1Button(Button button) {
        button.setOnAction(actionEvent -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            if (!checkCollision()) {
                placePlayer1Ships();

                alert.setTitle("Battleship alert");
                alert.setHeaderText(null);
                alert.setContentText("Place your ships on the right board. \nPress OK button if you have finished");

                alert.showAndWait();
                player1Button.setDisable(true);
                player1Button.setVisible(false);
                player2Button.setDisable(false);
                player2Button.setVisible(true);
            }
            else {
                alert.setTitle("Battleship alert");
                alert.setHeaderText(null);
                alert.setContentText("You need to move the ships highlighted in red!");
                alert.showAndWait();
            }
        });
    }


    private void installPlayer2Button(Button button) {
        button.setOnAction(actionEvent -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            if (!checkCollision()) {
                placePlayer2Ships();

                alert.setTitle("Battleship alert");
                alert.setHeaderText(null);
                alert.setContentText("Okay! Player 1's turn.");

                alert.showAndWait();
                player1Label.setVisible(true);
                player2Label.setVisible(true);
                player2Button.setDisable(true);
                player2Button.setVisible(false);
            }
            else {
                alert.setTitle("Battleship alert");
                alert.setHeaderText(null);
                alert.setContentText("You need to move the ships highlighted in red!");
                alert.showAndWait();
            }
        });
    }

    private void setVisiblePlayer1Ships(boolean set) {
        ship1_p1.setVisible(set);
        ship2_p1.setVisible(set);
        ship3_p1.setVisible(set);
        ship4_p1.setVisible(set);
        ship5_p1.setVisible(set);
        ship6_p1.setVisible(set);
        ship7_p1.setVisible(set);
        ship8_p1.setVisible(set);
    }

    private void setVisiblePlayer2Ships(boolean set) {
        ship1_p2.setVisible(set);
        ship2_p2.setVisible(set);
        ship3_p2.setVisible(set);
        ship4_p2.setVisible(set);
        ship5_p2.setVisible(set);
        ship6_p2.setVisible(set);
        ship7_p2.setVisible(set);
        ship8_p2.setVisible(set);
    }

    private void setDisablePlayer1Ships(boolean set) {
        ship1_p1.setDisable(set);
        ship2_p1.setDisable(set);
        ship3_p1.setDisable(set);
        ship4_p1.setDisable(set);
        ship5_p1.setDisable(set);
        ship6_p1.setDisable(set);
        ship7_p1.setDisable(set);
        ship8_p1.setDisable(set);
    }

    private void setDisablePlayer2Ships(boolean set) {
        ship1_p2.setDisable(set);
        ship2_p2.setDisable(set);
        ship3_p2.setDisable(set);
        ship4_p2.setDisable(set);
        ship5_p2.setDisable(set);
        ship6_p2.setDisable(set);
        ship7_p2.setDisable(set);
        ship8_p2.setDisable(set);
    }

    private void installGridCellListeners(Grid grid) {
        final Color hitSetFill = Color.DARKORCHID;
        final Color missSetFill = Color.DARKGRAY;
        final Color destroyedSetFill = Color.DARKKHAKI;

        for (Node node : grid.getGridPane().getChildren()) {
            Rectangle rect = (Rectangle) node;

            rect.setOnMouseClicked(e -> {

                if (rect.getFill() != hitSetFill && rect.getFill() != missSetFill && rect.getFill() != destroyedSetFill) {
                    Rectangle r = (Rectangle) e.getSource();

                    int colIndex = GridPane.getColumnIndex(r);
                    int rowIndex = GridPane.getRowIndex(r);

                    if (!grid.guess(colIndex, rowIndex)) {
                        if (grid.getGridPane() == player1DisplayBoard) {
                            player1DisplayBoard.setDisable(true);
                            player2DisplayBoard.setDisable(false);
                        } else {
                            player1DisplayBoard.setDisable(false);
                            player2DisplayBoard.setDisable(true);
                        }
                    }

                    if (grid.isGameOver()) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Battleship alert");
                        alert.setHeaderText(null);

                        if (grid.getGridPane() == player1DisplayBoard) {
                            alert.setContentText("The Player 2 won!");
                        }
                        else {
                            alert.setContentText("The Player 1 won!");
                        }

                        alert.showAndWait();
                        reset();
                    }
                }
            });

            rect.setOnMouseEntered(mouseEvent -> {
                if (rect.getFill() != hitSetFill && rect.getFill() != missSetFill && rect.getFill() != destroyedSetFill) {
                    rect.setFill(Color.GREEN);
                }
            });

            rect.setOnMouseExited(mouseEvent -> {
                if (rect.getFill() != hitSetFill && rect.getFill() != missSetFill && rect.getFill() != destroyedSetFill) {
                    rect.setFill(Color.BLACK);
                }
            });
        }
    }
}
