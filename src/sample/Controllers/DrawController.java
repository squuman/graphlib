package sample.Controllers;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Screen;

import java.awt.*;
import java.text.SimpleDateFormat;
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
                                  double increaseMultiplier) {
        group.getChildren().clear();

        for (int i = 0; i < peaks.length; i++) {
            Line line = new Line();
            System.out.println(peaks[i][0]);
            line.setStartX(coordinates[peaks[i][0] - 1][0] * increaseMultiplier);
            line.setEndX(coordinates[peaks[i][1] - 1][0] * increaseMultiplier);
            line.setStartY(coordinates[peaks[i][0] - 1][1] * increaseMultiplier);
            line.setEndY(coordinates[peaks[i][1] - 1][1] * increaseMultiplier);

            line.setStroke(color);
            line.setStrokeWidth(1);
            group.getChildren().add(line);
        }

        String pattern = "H:mm:s";
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        group.setLayoutX(screenSize.getWidth() / 3);
        group.setLayoutY(screenSize.getHeight() / 3);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(new Date());
        output.setContent(new Label(date + ": " + Arrays.deepToString(coordinates)));
    }

    public static double[][] rotateMatrix(double[][] dotsMatrix, double[][] degreeMatrix) {
        double[][] helper = new double[4][1];
        System.out.println(Arrays.deepToString(dotsMatrix));
        for (int i = 0; i < dotsMatrix.length; i++) {
            helper[0][0] = dotsMatrix[i][0];
            helper[1][0] = dotsMatrix[i][1];
            helper[2][0] = dotsMatrix[i][2];
            helper[3][0] = 1;
            double[][] temp = MatrixController.multiplyByMatrix(degreeMatrix, helper);
            System.out.println(Arrays.deepToString(temp));
            dotsMatrix[i][0] = temp[0][0];
            dotsMatrix[i][1] = temp[1][0];
            dotsMatrix[i][2] = temp[2][0];
        }

        return dotsMatrix;
    }

}
