package sample;

import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Insets;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.stage.Screen;
import javafx.stage.Stage;
import sample.Controllers.DrawController;
import sample.Controllers.MatrixController;

import javax.xml.crypto.dsig.Transform;
import java.util.Arrays;

public class Main extends Application {

    /**
     * matrix of coordinates
     */
    private double[][] dotsMatrix2D = {
            {2, 1, 0},
            {12, 1, 0},
            {12, 3, 0},
            {8, 3, 0},
            {8, 10, 0},
            {6, 10, 0},
            {6, 6, 0},
            {6, 3, 0},
            {2, 3, 0},
    };
    private double[][] dotsMatrix = {
            {3, 3, 0, 1},
            {2, 4, 0, 1},
            {2, 6, 0, 1},
            {6, 6, 0, 1},
            {6, 13, 0, 1},
            {8, 13, 0, 1},
            {9, 12, 0, 1},
            {9, 6, 0, 1},
            {8, 6, 0, 1},
            {12, 6, 0, 1},
            {13, 5, 0, 1},
            {13, 3, 0, 1},
            {12, 4, 0, 1},
    };

    /**
     * matrix of peaks
     */
    private int[][] peaksMatrix = {
            {1, 2},
            {2, 3},
            {3, 4},
            {4, 5},
            {5, 6},
            {6, 7},
            {7, 8},
            {8, 9},
            {8, 10},
            {10, 11},
            {11, 12},
            {12, 13},
            {12, 1},
            {2, 13},
            {9, 6},
            {13, 10},
    };

    /**
     * Output coordinates data to GUI console
     */
    private final ScrollPane coordinatesChangeData = new ScrollPane();

    /**
     * Rotation transform
     */

    private double anchorX, anchorY;
    private double anchorAngleX = 0;
    private double anchorAngleY = 0;
    private final DoubleProperty angleX = new SimpleDoubleProperty(0);
    private final DoubleProperty angleY = new SimpleDoubleProperty(0);

    /**
     * Tools for object edit
     */
    private final Label rotateLabel = new Label("Поворот");
    private final Label rotateXLabel = new Label("Поворот по X");
    private final Label rotateYLabel = new Label("Поворот по Y");
    private final Label rotateZLabel = new Label("Поворот по Z");
    private final Label moveLabel = new Label("Переместить");
    private final Label moveLabelX = new Label("Перемещение по X");
    private final Label moveLabelY = new Label("Перемещение по Y");
    private final Label moveLabelZ = new Label("Перемещение по Z");
    private final Label stretchLabel = new Label("Растяжение/Сжатие");
    private final Label stretchLabelX = new Label("Растяжение/Сжатие по X");
    private final Label stretchLabelZ = new Label("Растяжение/Сжатие по Y");
    private final Label stretchLabelY = new Label("Растяжение/Сжатие по Z");
    private final Label inverseLabel = new Label("Отразить");
    private final TextField rotateX = new TextField();
    private final TextField rotateY = new TextField();
    private final TextField rotateZ = new TextField();
    private final TextField moveX = new TextField();
    private final TextField moveY = new TextField();
    private final TextField moveZ = new TextField();
    private final TextField stretchX = new TextField();
    private final TextField stretchY = new TextField();
    private final TextField stretchZ = new TextField();
    private final Button rotate = new Button("Поворот");
    private final Button inverse = new Button("Отразить");
    private final Button move = new Button("Переместить");
    private final Button stretch = new Button("Растянуть/Сжать");

    /**
     * Basic components of GUI
     */
    private final BorderPane root = new BorderPane();
    private final Scene scene = new Scene(root,
            Screen.getPrimary().getBounds().getWidth(),
            Screen.getPrimary().getBounds().getHeight());
    private final Group figure = new Group();
    private final Pane paneForFigure = new Pane();
    private final VBox subtool = new VBox(5);
    private final VBox tools = new VBox(15);
    private final VBox rotateGroup = new VBox(5);
    private final VBox moveGroup = new VBox(5);
    private final VBox stretchGroup = new VBox(5);
    private final VBox inverseGroup = new VBox(5);

    /**
     * Menu
     */
    private final MenuBar menuBar = new MenuBar();
    private final Menu menuItemExit = new Menu("Выход");


    /**
     * InverseMatrix
     */
    private final double[][] inverseMatrix = {
            {-1, 0, 0, 0},
            {0, 1, 0, 0},
            {0, 0, 1, 0},
            {0, 0, 0, 1}
    };

    /**
     * @param primaryStage stage
     */
    @Override
    public void start(Stage primaryStage) {

        //Setting components
        rotateX.setText("0");
        rotateY.setText("0");
        rotateZ.setText("0");
        moveX.setText("0");
        moveY.setText("0");
        moveZ.setText("0");
        stretchX.setText("1");
        stretchY.setText("1");
        stretchZ.setText("1");
        menuBar.getMenus().add(menuItemExit);
        rotateGroup.getChildren().addAll(rotateLabel,
                rotateXLabel, rotateX,
                rotateYLabel, rotateY,
                rotateZLabel, rotateZ,
                rotate
        );
        moveGroup.getChildren().addAll(moveLabel,
                moveLabelX, moveX,
                moveLabelY, moveY,
                moveLabelZ, moveZ,
                move
        );
        stretchGroup.getChildren().addAll(
                stretchLabel,
                stretchLabelX, stretchX,
                stretchLabelY, stretchY,
                stretchLabelZ, stretchZ,
                stretch
        );
        inverseGroup.getChildren().addAll(inverseLabel, inverse);
        tools.getChildren().addAll(rotateGroup, inverseGroup, moveGroup, stretchGroup);
        tools.getChildren().add(subtool);
        root.setCenter(figure);
        root.setRight(tools);
        root.setBottom(coordinatesChangeData);
        root.setTop(menuBar);

        //Styles and properties
        inverseLabel.setStyle("-fx-font-size: 20px");
        rotateLabel.setStyle("-fx-font-size: 20px");
        moveLabel.setStyle("-fx-font-size: 20px");
        stretchLabel.setStyle("-fx-font-size: 20px");
        tools.setStyle("-fx-border-color: black");
        coordinatesChangeData.setStyle("-fx-border-color: black");
        tools.setPadding(new Insets(50, 30, 0, 30));
        coordinatesChangeData.setPadding(new Insets(10, 10, 10, 10));
        paneForFigure.setPadding(new Insets(50, 0, 0, 0));
        coordinatesChangeData.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        coordinatesChangeData.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        //Draw object
        DrawController.drawObject(dotsMatrix, peaksMatrix, figure, Color.RED, coordinatesChangeData, 10);

        //Events listeners
        menuItemExit.setOnAction(actionEvent -> {
            System.exit(0);
        });

        rotate.setOnAction(actionEvent -> {
            double rotateXRadians = Math.toRadians(Double.parseDouble(rotateX.getText()));
            double rotateYRadians = Math.toRadians(Double.parseDouble(rotateY.getText()));
            double rotateZRadians = Math.toRadians(Double.parseDouble(rotateZ.getText()));
            double[][] degreeMatrixX = {
                    {1, 0, 0, 1},
                    {0, Math.cos(rotateXRadians), Math.sin(rotateXRadians), 0},
                    {0, -Math.sin(rotateXRadians), Math.cos(rotateXRadians), 0},
                    {0, 0, 0, 1}
            };
            double[][] degreeMatrixY = {
                    {Math.cos(rotateYRadians), 0, -Math.sin(rotateYRadians), 0},
                    {0, 1, 0, 0},
                    {Math.sin(rotateYRadians), 0, Math.cos(rotateYRadians), 0},
                    {0, 0, 0, 1}
            };
            double[][] degreeMatrixZ = {
                    {Math.cos(rotateZRadians), Math.sin(rotateZRadians), 0, 0},
                    {-Math.sin(rotateZRadians), Math.cos(rotateZRadians), 0, 0},
                    {0, 0, 1, 0},
                    {0, 0, 0, 1}
            };

            DrawController.rotateMatrix(dotsMatrix, degreeMatrixX);
            DrawController.rotateMatrix(dotsMatrix, degreeMatrixY);
            DrawController.rotateMatrix(dotsMatrix, degreeMatrixZ);
            DrawController.drawObject(dotsMatrix, peaksMatrix, figure, Color.BLACK, coordinatesChangeData, 10);
        });

        inverse.setOnAction(actionEvent -> {
            dotsMatrix = MatrixController.multiplyByMatrix(dotsMatrix, inverseMatrix);
            DrawController.drawObject(dotsMatrix, peaksMatrix, figure, Color.BLACK, coordinatesChangeData, 10);
        });

        move.setOnAction(actionEvent -> {
            double moveXValue = Double.parseDouble(moveX.getText());
            double moveYValue = Double.parseDouble(moveY.getText());
            double moveZValue = Double.parseDouble(moveZ.getText());
            paneForFigure.getChildren().add(figure);
            root.setCenter(paneForFigure);
            double[][] moveMatrix = {
                    {1, 0, 0, moveXValue},
                    {0, 1, 0, moveYValue},
                    {0, 0, 1, moveZValue},
                    {0, 0, 0, 1},
            };
            dotsMatrix = MatrixController.multiplyByMatrix(dotsMatrix, moveMatrix);
            DrawController.drawObject(dotsMatrix, peaksMatrix, figure, Color.BLACK, coordinatesChangeData, 10);
        });

        stretch.setOnAction(actionEvent -> {
            double stretchXValue = Double.parseDouble(stretchX.getText());
            double stretchYValue = Double.parseDouble(stretchY.getText());
            double stretchZValue = Double.parseDouble(stretchZ.getText());
            double[][] stretchMatrix = {
                    {stretchXValue, 0, 0, 0},
                    {0, stretchYValue, 0, 0},
                    {0, 0, stretchZValue, 0},
                    {0, 0, 0, 1},
            };
            dotsMatrix = MatrixController.multiplyByMatrix(dotsMatrix,stretchMatrix);
            DrawController.drawObject(dotsMatrix, peaksMatrix, figure, Color.BLACK, coordinatesChangeData, 10);
        });

        //mouse control

//        initMouseControl(figure, scene);

        //Set default Scene settings
        primaryStage.setTitle("GraphLib");
        primaryStage.setMaximized(true);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void initMouseControl(Group group, Scene scene) {
        Rotate xRotate;
        Rotate yRotate;
        group.getTransforms().addAll(
                xRotate = new Rotate(0, Rotate.X_AXIS),
                yRotate = new Rotate(0, Rotate.Y_AXIS)
        );
        xRotate.angleProperty().bind(angleX);
        yRotate.angleProperty().bind(angleY);

        scene.setOnMousePressed(mouseEvent -> {
            anchorX = mouseEvent.getSceneX();
            anchorY = mouseEvent.getSceneY();
            anchorAngleX = angleX.get();
            anchorAngleY = angleY.get();
        });

        scene.setOnMouseDragged(mouseEvent -> {
            angleX.set(anchorAngleX - (anchorAngleY - mouseEvent.getSceneY()));
            angleY.set(anchorAngleY - (anchorAngleX - mouseEvent.getSceneX()));
            System.out.println(mouseEvent);
        });
    }

    /**
     * @param args args
     */
    public static void main(String[] args) {
        launch(args);
    }
}