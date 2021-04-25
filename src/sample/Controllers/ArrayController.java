package sample.Controllers;

public class ArrayController {

    public static double[][] copyMatrix(double origin_Matrix[][]) {

        double copy[][] = new double[origin_Matrix.length][origin_Matrix[0].length];

        for (int i = 0; i < origin_Matrix.length; ++i) {
            System.arraycopy(origin_Matrix[i], 0, copy[i], 0, origin_Matrix[i].length);
        }

        return copy;
    }
}
