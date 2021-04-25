package sample;

import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Insets;
import javafx.scene.Group;
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

import java.util.ArrayList;

public class Main extends Application {

    /**
     * matrix of coordinates
     */
    private final double[][] dotsMatrixConst = {
            {3, 3, 0, 1},//1
            {2, 4, 0, 1},//2
            {2, 6, 0, 1},//3
            {6, 6, 0, 1},//4
            {6, 13, 0, 1},//5
            {8, 13, 0, 1},//6
            {9, 12, 0, 1},//7
            {9, 6, 0, 1},//8
            {8, 6, 0, 1},//9
            {12, 6, 0, 1},//10
            {13, 5, 0, 1},//11
            {13, 3, 0, 1},//12
            {12, 4, 0, 1},//13

            {3, 3, 2, 1},//14
            {2, 4, 2, 1},//15
            {2, 6, 2, 1},//16
            {6, 6, 2, 1},//17
            {6, 13, 2, 1},//18
            {8, 13, 2, 1},//19
            {9, 12, 2, 1},//20
            {9, 6, 2, 1},//21
            {8, 6, 2, 1},//22
            {12, 6, 2, 1},//23
            {13, 5, 2, 1},//24
            {13, 3, 2, 1},//25
            {12, 4, 2, 1},//26
    };
    private double[][] dotsMatrix = {
            {3, 3, 0, 1},//1
            {2, 4, 0, 1},//2
            {2, 6, 0, 1},//3
            {6, 6, 0, 1},//4
            {6, 13, 0, 1},//5
            {8, 13, 0, 1},//6
            {9, 12, 0, 1},//7
            {9, 6, 0, 1},//8
            {8, 6, 0, 1},//9
            {12, 6, 0, 1},//10
            {13, 5, 0, 1},//11
            {13, 3, 0, 1},//12
            {12, 4, 0, 1},//13
            {3, 3, 2, 1},//14
            {2, 4, 2, 1},//15
            {2, 6, 2, 1},//16
            {6, 6, 2, 1},//17
            {6, 13, 2, 1},//18
            {8, 13, 2, 1},//19
            {9, 12, 2, 1},//20
            {9, 6, 2, 1},//21
            {8, 6, 2, 1},//22
            {12, 6, 2, 1},//23
            {13, 5, 2, 1},//24
            {13, 3, 2, 1},//25
            {12, 4, 2, 1},//26
    };

    /**
     * matrix of peaks
     */
    private final int[][] peaksMatrix = {
            {2, 3},
            {3, 4},
            {4, 5},
            {5, 6},
            {8, 9},
            {8, 10},
            {2, 13},
            {9, 6},
            {13, 10},
            //second letter
            {15, 16},
            {16, 17},
            {17, 18},
            {19, 22},
            {18, 19},
            {21, 22},
            {21, 23},
            {15, 26},
            {22, 21},
            {26, 23},
            //links letters
            {2, 15},
            {3, 16},
            {4, 17},
            {5, 18},
            {6, 19},
//            {7,20},
//            {8,21},
            {9, 22},
            {10, 23},
            {13, 26},


//            {1, 2},
//            {14, 5},
//            {14, 7},
//            {14, 15},
//            {15, 11},
//            {15, 16},
//            {16, 1},
//            {16, 3},
//            {6, 7},
//            {7, 8},
//            {10, 11},
//            {11, 12},
//            {12, 13},
//            {12, 1},

    };

    /**
     * Matrix change history
     */
    ArrayList<String> historyData = new ArrayList<>();

    /**
     * Output coordinates data to GUI console
     */
    private final ScrollPane coordinatesChangeData = new ScrollPane();

    /**
     * Rotation transform by mouse
     */

    private double anchorX, anchorY;
    private double anchorAngleX = 0;
    private double anchorAngleY = 0;
    private final DoubleProperty angleX = new SimpleDoubleProperty(0);
    private final DoubleProperty angleY = new SimpleDoubleProperty(0);


    /**
     * Perspective transform
     */
    private double focusPoint;

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
    private final Label stretchLabelY = new Label("Растяжение/Сжатие по Y");
    private final Label stretchLabelZ = new Label("Растяжение/Сжатие по Z");
    private final Label inverseLabel = new Label("Отразить");
    private final Label perspectiveLabel = new Label("Точка обзора");
    private final TextField rotateX = new TextField();
    private final TextField rotateY = new TextField();
    private final TextField rotateZ = new TextField();
    private final TextField moveX = new TextField();
    private final TextField moveY = new TextField();
    private final TextField moveZ = new TextField();
    private final TextField stretchX = new TextField();
    private final TextField stretchY = new TextField();
    private final TextField stretchZ = new TextField();
    private final TextField perspectiveField = new TextField();
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
    private final ScrollPane scrollTools = new ScrollPane();
    private final VBox rotateGroup = new VBox(5);
    private final VBox moveGroup = new VBox(5);
    private final VBox stretchGroup = new VBox(5);
    private final VBox inverseGroup = new VBox(5);
    private final VBox perspectiveGroup = new VBox(5);
    private final ScrollPane historyScrollPane = new ScrollPane();

    /**
     * Menu:restart, exit
     */
    private final MenuBar menuBar = new MenuBar();
    private final Menu menu = new Menu("Система");
    private final MenuItem menuItemExit = new MenuItem("Выход");
    private final MenuItem menuItemRestart = new MenuItem(" Перезапуск");

    /**
     * Inverse Matrix
     */
    private final double[][] inverseMatrix = {
            {-1, 0, 0, 0},
            {0, 1, 0, 0},
            {0, 0, 1, 0},
            {0, 0, 0, 1}
    };

    /**
     * Perspective data
     */
    public static double perspectiveConst = 3;

    /**
     * @param primaryStage stage
     */
    @Override
    public void start(Stage primaryStage) {

        //Setting components
        menu.getItems().addAll(menuItemRestart);
        menu.getItems().addAll(menuItemExit);
        menuBar.getMenus().add(menu);
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
        perspectiveGroup.getChildren().addAll(
                perspectiveLabel,
                perspectiveField);
        inverseGroup.getChildren().addAll(inverseLabel, inverse);
        tools.getChildren().addAll(perspectiveGroup, rotateGroup, inverseGroup, moveGroup, stretchGroup);
        tools.getChildren().add(subtool);
        scrollTools.setContent(tools);
        paneForFigure.getChildren().add(figure);
        root.setCenter(figure);
        root.setRight(scrollTools);
        root.setBottom(historyScrollPane);
        root.setTop(menuBar);

        //Styles and properties
        rotateX.setText("0");
        rotateY.setText("0");
        rotateZ.setText("0");
        moveX.setText("0");
        moveY.setText("0");
        moveZ.setText("0");
        stretchX.setText("1");
        stretchY.setText("1");
        stretchZ.setText("1");
        inverseLabel.setStyle("-fx-font-size: 20px");
        rotateLabel.setStyle("-fx-font-size: 20px");
        moveLabel.setStyle("-fx-font-size: 20px");
        stretchLabel.setStyle("-fx-font-size: 20px");
        tools.setStyle("-fx-border-color: black");
        historyScrollPane.setStyle("-fx-border-color: black");
        tools.setPadding(new Insets(50, 30, 0, 30));
        historyScrollPane.setMinHeight(100);
        historyScrollPane.setPadding(new Insets(10, 10, 10, 10));
        paneForFigure.setPadding(new Insets(50, 0, 0, 0));
        historyScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        historyScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        //Draw object
        DrawController.drawObject(dotsMatrix, peaksMatrix, figure, Color.RED, coordinatesChangeData, 10,perspectiveConst);

        //Events listeners
        //Menu
        menuItemExit.setOnAction(actionEvent -> System.exit(0));

        menuItemRestart.setOnAction(actionEvent -> {
            dotsMatrix = dotsMatrixConst;
            DrawController.drawObject(dotsMatrix, peaksMatrix, figure,
                    Color.BLACK, coordinatesChangeData, 10,perspectiveConst);
            root.setCenter(null);
            root.setCenter(figure);

        });
        //Tools
        //Rotate object, rotate func is separate function
        rotate.setOnMousePressed(actionEvent -> {
            double rotateXRadians = Math.toRadians(Double.parseDouble(rotateX.getText()));
            double rotateYRadians = Math.toRadians(Double.parseDouble(rotateY.getText()));
            double rotateZRadians = Math.toRadians(Double.parseDouble(rotateZ.getText()));
            focusPoint = Double.parseDouble(perspectiveField.getText());
            double[][] degreeMatrixX = {
                    {1, 0, 0, 0},
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
            root.setCenter(null);
            root.setCenter(figure);
            DrawController.outputHistory(historyScrollPane, historyData);
            DrawController.drawObject(dotsMatrix, peaksMatrix, figure,
                    Color.BLACK, coordinatesChangeData, 10,focusPoint);
        });

        //Inverse object by YZ
        inverse.setOnAction(actionEvent -> {
            MatrixController.changeMatrix(dotsMatrix, inverseMatrix);
            root.setCenter(null);
            root.setCenter(figure);
            focusPoint = Double.parseDouble(perspectiveField.getText());
            DrawController.outputHistory(historyScrollPane, historyData);
            DrawController.drawObject(dotsMatrix, peaksMatrix, figure,
                    Color.BLACK, coordinatesChangeData, 10,focusPoint);
        });

        //Move obj, z not move, but coordinates change
        move.setOnAction(actionEvent -> {
            double moveXValue = Double.parseDouble(moveX.getText());
            double moveYValue = Double.parseDouble(moveY.getText());
            double moveZValue = Double.parseDouble(moveZ.getText());
            double[][] moveMatrix = {
                    {1, 0, 0, moveXValue},
                    {0, 1, 0, moveYValue},
                    {0, 0, 1, moveZValue},
                    {0, 0, 0, 1},
            };
            MatrixController.changeMatrix(dotsMatrix, moveMatrix);
            changeCenter();
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
            MatrixController.changeMatrix(dotsMatrix, stretchMatrix);
            changeCenter();
        });

        //Set default Scene settings
//        initMouseControl(figure,scene);
        primaryStage.setTitle("GraphLib");
        primaryStage.setMaximized(true);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void changeCenter() {
        root.setCenter(null);
        paneForFigure.getChildren().clear();
        paneForFigure.getChildren().add(figure);
        root.setCenter(paneForFigure);
        focusPoint = Double.parseDouble(perspectiveField.getText());
        DrawController.outputHistory(historyScrollPane, historyData);
        DrawController.drawObject(dotsMatrix, peaksMatrix, figure,
                Color.BLACK, coordinatesChangeData, 10,focusPoint);
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
        });
    }

    /**
     * @param args args
     */
    public static void main(String[] args) {
        launch(args);
    }

}