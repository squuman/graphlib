package sample.Controllers;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class DrawController {
    /**
     * @param coordinates = {{x1,y1},{x2,y2},...,etc}
     * @param group       group layout where object in
     * @param color       color of lines
     */
    public static void drawObject(double[][] coordinates,
                                  int[][] peaks,
                                  Group group,
                                  Color color,
                                  ScrollPane output,
                                  double increaseMultiplier, double focusPoint) {
        group.getChildren().clear();

        double[][] coordinatesCopy = {};

        System.out.println(Arrays.deepToString(coordinates));
        coordinatesCopy = perspectiveChanger(ArrayController.copyMatrix(coordinates),focusPoint);
        System.out.println(Arrays.deepToString(coordinates));

        for (int i = 0; i < peaks.length; i++) {
            Line line = new Line();
            line.setStartX(coordinatesCopy[peaks[i][0] - 1][0] * increaseMultiplier);
            line.setEndX(coordinatesCopy[peaks[i][1] - 1][0] * increaseMultiplier);
            line.setStartY(coordinatesCopy[peaks[i][0] - 1][1] * increaseMultiplier);
            line.setEndY(coordinatesCopy[peaks[i][1] - 1][1] * increaseMultiplier);

            line.setStroke(color);
            line.setStrokeWidth(1);
            group.getChildren().add(line);
        }

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        group.setLayoutX(screenSize.getWidth() / 3);
        group.setLayoutY(screenSize.getHeight() / 3);
    }

    public static double[][] rotateMatrix(double[][] dotsMatrix, double[][] degreeMatrix) {
        double[][] helper = new double[4][1];
        for (int i = 0; i < dotsMatrix.length; i++) {
            helper[0][0] = dotsMatrix[i][0];
            helper[1][0] = dotsMatrix[i][1];
            helper[2][0] = dotsMatrix[i][2];
            helper[3][0] = 1;
            double[][] temp = MatrixController.multiplyByMatrix(degreeMatrix, helper);
            dotsMatrix[i][0] = temp[0][0];
            dotsMatrix[i][1] = temp[1][0];
            dotsMatrix[i][2] = temp[2][0];
        }

        return dotsMatrix;
    }

    public static double[][] perspectiveChanger(double[][] dotsMatrix,double focusPointValue) {

        double[][] perspectiveMatrix = {
                {1, 0, 0, 0},
                {0, 1, 0, 0},
                {0, 0, 1, 0},
                {0, 0, -1 / focusPointValue, 1}
        };
        double[][] result = dotsMatrix;

        for(int i = 0;i< result.length;i++) {
            result[i][0] = result[i][0] / (1 - (result[i][2]/focusPointValue));
            result[i][1] = result[i][1] / (1 - (result[i][2]/focusPointValue));
        }
        MatrixController.multiplyMatrixV2(result,perspectiveMatrix);

        return result;
    }

    public static void outputHistory(ScrollPane scrollPane, ArrayList<String> coordinatesHistory) {
        VBox historyGroup = new VBox();
        String pattern = "H:mm:s";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(new Date());

        for (int i = 0; i < coordinatesHistory.size(); i++) {
            historyGroup.getChildren().add(new Label(date + ": " + coordinatesHistory.get(i)));
        }
        scrollPane.setContent(null);
        scrollPane.setContent(historyGroup);
    }

}
