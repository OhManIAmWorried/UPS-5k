
public class DoubleCliff {
    double[][] array;

    public DoubleCliff(int steps, double leftRatio, double rightRatio) {
        array = new double[steps + 1][];
        int c = 1;
        boolean sw = false;
        for (int i = 0; i < steps + 1; i++) {
            array[i] = new double[i + c];
            if (i % 2 == 1) c = c - 1;
            if (i == 0) {
                array[0][0] = 1;
                //System.out.println("array[" + i + "][" + 0 + "] = " + array[i][0]);
            }
            else {
                if (i % 2 == 1) {
                    for (int j = 0; j < array[i].length; j++) {
                        if (j != 0) array[i][j] += array[i - 1][j - 1] * rightRatio;
                        if (j != array[i].length - 1) array[i][j] += array[i - 1][j] * leftRatio;
                        //System.out.println("array[" + i + "][" + j + "] = " + array[i][j]);
                    }
                }
                else {
                    for (int j = 0; j < array[i].length; j++) {
                        if (j != 0) array[i][j] += array[i - 1][j] * rightRatio;
                        if (j != array[i].length - 1) array[i][j] += array[i - 1][j + 1] * leftRatio;
                        //System.out.println("array[" + i + "][" + j + "] = " + array[i][j]);
                    }
                }
            }
        }
    }

    public double getProbability() {
        double sum = 0;
        for (int i = 0; i < array.length; i++) {
            if (i % 2 == 1) sum += array[i][0];
        }
        return sum;
    }
}
