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
    private ArrayList<Rectangle> rectangleShips; // store ships in array for convenience
    private int layoutReset; // useful for resetting ships position
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

        // Initialize grids for the players
        player1Grid.initGrid();
        player2Grid.initGrid();

        // Hide labels until the game starts
        player1Label.setVisible(false);
        player2Label.setVisible(false);

        // Enable start button
        startButton.setDisable(false);
        startButton.setVisible(true);

        // Disable ships until the game starts
        setDisablePlayer1Ships(true);
        setDisablePlayer2Ships(true);

        // Install buttons
        installStartButton(startButton);
        installPlayer1Button(player1Button);
        installPlayer2Button(player2Button);

        // Install grid cell listeners
        installGridCellListeners(player1Grid);
        installGridCellListeners(player2Grid);

        // Install ship listeners and reset layout
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

        // Make sure ships are visible after resetting the game
        setVisiblePlayer1Ships(true);
        setVisiblePlayer2Ships(true);

        // Disable boards until ships are placed
        player1DisplayBoard.setDisable(true);
        player2DisplayBoard.setDisable(true);
    }

    private void resetShipLayout(Rectangle ship) {
        int shipSpacing = 40;

        ship.setLayoutX(48 + layoutReset);
        ship.setLayoutY(349);

        // Rotate back the ship
        if (ship.getWidth() != initialShipWidth) {
            ship.setHeight(ship.getWidth());
            ship.setWidth(initialShipWidth);
        }

        layoutReset += shipSpacing;
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

    // Useful for tracking the ship drag
    class Delta {
        double x, y;
    }

    ///////////////////////
    // INSTALL LISTENERS //
    ///////////////////////

    private void installShipListeners(Rectangle ship) {

        // Check which player's ship is being dragged
        int player;
        if (ship.getLayoutX() < 400) {
            player = 1;
        }
        else {
            player = 2;
        }

        int shipHeight = (int) ship.getHeight();
        final Delta dragDelta = new Delta();

        // Track the ship movement here
        ship.setOnMousePressed(mouseEvent -> {
            dragDelta.x = ship.getLayoutX() - mouseEvent.getSceneX();
            dragDelta.y = ship.getLayoutY() - mouseEvent.getSceneY();
            ship.setCursor(Cursor.MOVE);
        });

        ship.setOnMouseDragged(mouseEvent -> {

            // Do nothing if the player is trying to drag a ship with the right button since it's being used for rotating the ship
            if (mouseEvent.getButton() == MouseButton.SECONDARY) {
                return;
            }

            // translate variable is not useful now, but it helps if you decide to add ships with different width to the game
            int translate = 0;
            if (ship.getWidth() == shipHeight) {
                translate = (initialShipWidth - cellSize) / 2;
            }

            if (player == 1) {

                // Calculate the row and the column for a new ship layout
                int col = (int) (mouseEvent.getSceneX() + dragDelta.x - 42) / cellSize;
                double x = col * cellSize + 53 - translate; // 53 = distance from grid to left border

                int row = (int) (mouseEvent.getSceneY() + dragDelta.y - shipHeight / 6) / cellSize;
                double y = row * cellSize + 28 + translate; // 28 = distance from grid to top border

                ///////////////////////////////////////////////
                /// HANDLE DRAGGING OUTSIDE THE GIVEN SPACE ///

                if (x > 400 - ship.getWidth()) {
                    x = 400 - ship.getWidth();
                }

                if (x < 0) {
                    x = 0;
                }

                if (y < 0) {
                    y = 0;
                }

                if (y > 520 - ship.getHeight()) {
                    y = 520 - ship.getHeight();
                }

                // DONE //
                /////////

                ship.setLayoutX(x);
                ship.setLayoutY(y);
            }

            if (player == 2) {

                // Calculate the row and the column for a new ship layout
                int col = (int) (mouseEvent.getSceneX() + dragDelta.x - 440) / cellSize;
                double x = col * cellSize + 447 - translate; // 447 = distance from grid to left border

                int row = (int) (mouseEvent.getSceneY() + dragDelta.y - shipHeight / 6) / cellSize;
                double y = row * cellSize + 28 + translate; // 28 = distance from grid to top border

                ///////////////////////////////////////////////
                /// HANDLE DRAGGING OUTSIDE THE GIVEN SPACE ///

                if (x < 400) {
                    x = 400;
                }

                if (x > 800 - ship.getWidth()) {
                    x = 800 - ship.getWidth();
                }

                if (y < 0) {
                    y = 0;
                }

                if (y > 520 - ship.getHeight()) {
                    y = 520 - ship.getHeight();
                }

                // DONE //
                /////////

                ship.setLayoutX(x);
                ship.setLayoutY(y);
            }
        });

        // Detect collision after the ship placement
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

        // Rotate ship on left click by reversing its dimensions
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


            // Detect collision after ship rotation
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

        ship.setOnMouseEntered(mouseEvent -> ship.setCursor(Cursor.HAND));
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
                        }
                        else {
                            player1DisplayBoard.setDisable(false);
                            player2DisplayBoard.setDisable(true);
                        }
                    }

                    // Reset if the game is over
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

            // Highlight cell in green on mouse hover
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

    ////////////////////////
    // INSTALLING BUTTONS //
    ////////////////////////

    // Install button for starting the game
    private void installStartButton(Button button) {
        button.setOnAction(actionEvent -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Battleship alert");
            alert.setHeaderText(null);
            alert.setContentText("Place your ships on the left board. \nPress OK button if you have finished. \nRight click on the ship to rotate.");

            alert.showAndWait();

            setDisablePlayer1Ships(false);
            startButton.setDisable(true);
            startButton.setVisible(false);
            player1Button.setDisable(false);
            player1Button.setVisible(true);
        });
    }

    // Install button that allows player 1 to set up his ships
    private void installPlayer1Button(Button button) {
        button.setOnAction(actionEvent -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            if (!checkCollision() && checkShipPlacement(player1Grid)) {
                setupPlayer1Ships();

                alert.setTitle("Battleship alert");
                alert.setHeaderText(null);
                alert.setContentText("Place your ships on the right board. \nPress OK button if you have finished. \nRight click on the ship to rotate.");

                alert.showAndWait();
                player1Button.setDisable(true);
                player1Button.setVisible(false);
                player2Button.setDisable(false);
                player2Button.setVisible(true);
            }
            else {
                alert.setTitle("Battleship alert");
                alert.setHeaderText(null);
                alert.setContentText("You need to move the ships highlighted in red or ships that are outside the board!");
                alert.showAndWait();
            }
        });
    }

    // Install button that allows player 2 to set up his ships
    private void installPlayer2Button(Button button) {
        button.setOnAction(actionEvent -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            if (!checkCollision() && checkShipPlacement(player2Grid)) {
                setupPlayer2Ships();

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
                alert.setContentText("You need to move the ships highlighted in red or ships that are outside the board!");
                alert.showAndWait();
            }
        });
    }

    @FXML
    private void handleExitButton(InputEvent e) {
        final Node source = (Node) e.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }



    // Setup ships placed by first player
    private void setupPlayer1Ships() {
        Rectangle r;
        Ship ship;

        for (int i = 0; i < 8; i++) {
            r = rectangleShips.get(i);

            int []ok = shipLayoutToGrid(r.getLayoutX(), r.getLayoutY(), r.getLayoutX() + r.getWidth() - 1, r.getLayoutY() + r.getHeight() - 1);
            ship = new Ship(ok[0], ok[1], ok[2], ok[3]);

            player1Grid.addShip(ship);
        }

        setVisiblePlayer1Ships(false);
        setDisablePlayer2Ships(false);
    }

    // Setup ships placed by second player
    private void setupPlayer2Ships() {
        Rectangle r;
        Ship ship;

        for (int i = 0; i < 8; i++) {
            r = rectangleShips.get(i + 8);

            int []ok = shipLayoutToGrid(r.getLayoutX(), r.getLayoutY(), r.getLayoutX() + r.getWidth() - 1, r.getLayoutY() + r.getHeight() - 1);
            ship = new Ship(ok[0], ok[1], ok[2], ok[3]);

            player2Grid.addShip(ship);
        }

        setVisiblePlayer2Ships(false);
        setDisablePlayer2Ships(true);

        player1DisplayBoard.setDisable(false);
    }

    // Convert ship layout to the location on the grid
    private int[] shipLayoutToGrid(double x1, double y1, double x2, double y2) {
        int []rectangle = new int[4];

        if (x1 < 400) {
            rectangle[0] = (int) (x1 - 53) / cellSize;
            rectangle[1] = (int) (y1 - 28) / cellSize;
            rectangle[2] = (int) (x2 - 53) / cellSize;
            rectangle[3] = (int) (y2 - 28) / cellSize;
        }
        else {
            rectangle[0] = (int) (x1 - 447) / cellSize;
            rectangle[1] = (int) (y1 - 28) / cellSize;
            rectangle[2] = (int) (x2 - 447) / cellSize;
            rectangle[3] = (int) (y2 - 28) / cellSize;
        }

        return rectangle;
    }

    // Check if any ships are colliding with each other
    private boolean checkCollision() {
        for (int i = 0; i < 16; i++) {
            for (int j = i + 1; j < 16; j++) {
                if (rectangleShips.get(i).getBoundsInParent().intersects(rectangleShips.get(j).getBoundsInParent())) {
                    if (rectangleShips.get(i) != rectangleShips.get(j)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    // Check if all of the ships are on the board
    private boolean checkShipPlacement(Grid grid)  {

        if (grid == player1Grid) {
            for (int i = 0; i < 8; i++) {

                if (rectangleShips.get(i).getLayoutY() < 28 || rectangleShips.get(i).getLayoutY() > 328 - rectangleShips.get(i).getHeight()) {
                    return false;
                }

                if (rectangleShips.get(i).getLayoutX() < 53 || rectangleShips.get(i).getLayoutX() > 353 - rectangleShips.get(i).getWidth()) {
                    return false;
                }
            }
        }
        else {
            for (int i = 8; i < 16; i++) {

                if (rectangleShips.get(i).getLayoutY() < 28 || rectangleShips.get(i).getLayoutY() > 328 - rectangleShips.get(i).getHeight()) {
                    return false;
                }

                if (rectangleShips.get(i).getLayoutX() < 447 || rectangleShips.get(i).getLayoutX() > 747 - rectangleShips.get(i).getWidth()) {
                    return false;
                }
            }
        }

        return true;
    }

    /////////////////
    // SET METHODS //
    /////////////////

    private void setVisiblePlayer1Ships(boolean set) {
        for (int i = 0; i < 8; i++) {
            rectangleShips.get(i).setVisible(set);
        }
    }

    private void setVisiblePlayer2Ships(boolean set) {
        for (int i = 0; i < 8; i++) {
            rectangleShips.get(i+8).setVisible(set);
        }
    }

    private void setDisablePlayer1Ships(boolean set) {
        for (int i = 0; i < 8; i++) {
            rectangleShips.get(i).setDisable(set);
        }
    }

    private void setDisablePlayer2Ships(boolean set) {
        for (int i = 0; i < 8; i++) {
            rectangleShips.get(i+8).setDisable(set);
        }
    }
}
