package sample.Controllers;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Arrays;

public class MatrixController {


    public static double[][] multiplyByMatrix(double[][] m1, double[][] m2) {
        int m1ColLength = m1[0].length;
        int m2RowLength = m2.length;
        if (m1ColLength != m2RowLength) return null; // matrix multiplication is not possible
        int mRRowLength = m1.length;    // m result rows length
        int mRColLength = m2[0].length; // m result columns length
        double[][] mResult = new double[mRRowLength][mRColLength];
        for (int i = 0; i < mRRowLength; i++) {         // rows from m1
            for (int j = 0; j < mRColLength; j++) {     // columns from m2
                for (int k = 0; k < m1ColLength; k++) { // columns from m1
                    mResult[i][j] += m1[i][k] * m2[k][j];
                }
            }
        }
        return mResult;
    }

    public static double[][] changeMatrix(double[][] dotsMatrix, double[][] changeMatrix) {
        double[][] tempMatrix;

        for (int i = 0; i < dotsMatrix.length; i++) {
            double[][] helperMatrix = {
                    {dotsMatrix[i][0]},
                    {dotsMatrix[i][1]},
                    {dotsMatrix[i][2]},
                    {1},
            };

            tempMatrix = MatrixController.multiplyByMatrix(changeMatrix, helperMatrix);
            System.out.println(Arrays.deepToString(tempMatrix));

            if (tempMatrix != null) {
                dotsMatrix[i][0] = tempMatrix[0][0];
                dotsMatrix[i][1] = tempMatrix[1][0];
                dotsMatrix[i][2] = tempMatrix[2][0];
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка");
                alert.setContentText("Произошла ошибка. Источник: " + MatrixController.class.getName());
                alert.showAndWait().ifPresent(rs -> {
                    if (rs == ButtonType.OK) {
                        System.out.println("Pressed OK.");
                    }
                });
            }
        }

        return dotsMatrix;
    }
}
