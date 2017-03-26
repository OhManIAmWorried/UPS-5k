import java.util.Random;
import java.util.Scanner;

/**
 * Created by Oly on 24.03.2017.
 */
public class MainFrame {

    public static void main(String[] args) {
        Scanner inpScanner = new Scanner(System.in);
        int a =  0;
        int karma = 0;
        while (true) {
            System.out.println("Menu:");
            System.out.println(" 0. Exit");
            System.out.println(" 1. A cliff problem");
            System.out.println(" 2. A path problem");
            separate();
            System.out.print("Choice: ");
            a = inpScanner.nextInt();
            separate();
            switch (a) {
                case 0: {
                    System.out.printf("With karma level of %s the user is leaving us.%n",karma);
                    System.out.println("Bye-bye!");
                    return;
                }
                case 1: {
                    karma++;
                    doCliff();
                    break;
                }
                case 2: {
                    karma++;
                    doPath();
                    break;
                }
                default: {
                    karma--;
                    System.out.println("No such choice!");
                }
            }
        }
    }

    private static double getDouble(String input) {
        String[] array = input.split("/");
        if (array.length == 1) return Double.parseDouble(array[0]);
        else {
            return Double.parseDouble(array[0]) / Double.parseDouble(array[1]);
        }
    }

    private static void separate() {
        System.out.println("-----------------------------------------------------------------------------------------");
    }

    public static void doPath() {
        Scanner inpScanner = new Scanner(System.in);
        System.out.print("i: ");
        double iVal = inpScanner.nextDouble();
        System.out.print("j: ");
        double jVal = inpScanner.nextDouble();
        System.out.print("k: ");
        double kVal = inpScanner.nextDouble();

        /*probabilities*/
        double[][] arrayDou = new double[5][];
        arrayDou[0] = new double[]{1};
        arrayDou[1] = new double[]{iVal/(iVal+jVal+kVal), (jVal+kVal)/(iVal+jVal+kVal)};
        arrayDou[2] = new double[]{kVal/(iVal+jVal+kVal), iVal/(iVal+jVal+kVal), jVal/(iVal+jVal+kVal)};
        arrayDou[3] = new double[]{iVal/(iVal+jVal+kVal), (jVal+kVal)/(iVal+jVal+kVal)};
        arrayDou[4] = new double[]{1};

        /*indexes*/
        int[][] arrayInt = new int[5][];
        arrayInt[0] = new int[]{0};
        arrayInt[1] = new int[]{0,2};
        arrayInt[2] = new int[]{2,1,3};
        arrayInt[3] = new int[]{2,4};
        arrayInt[4] = new int[]{4};

        System.out.print("Iterations amount: ");
        int n = inpScanner.nextInt();

        /*
        for (int i = 0; i < arrayDou.length; i++) {
            for (int j = 0; j < arrayDou[i].length; j++)
                System.out.print(arrayDou[i][j] + " " + arrayInt[i][j] + " ");
            System.out.println();
        }
        */

        sort(arrayDou,arrayInt);

        /*
        for (int i = 0; i < arrayDou.length; i++) {
            for (int j = 0; j < arrayDou[i].length; j++)
                System.out.print(arrayDou[i][j] + " " + arrayInt[i][j] + " ");
            System.out.println();
        }
        */

        /*array of counters*/
        int[] arrayK = new int[arrayInt.length];

        double rand;
        double diff;
        int tempCurrent = 0;
        int current = 2; /*Change it later, probably*/
        final int currentFin = current;
        boolean sw = false;
        long counter = 0;

        for (int i = 0; i < n; i++) { /*Iterations*/
            ++arrayK[current];
            while (true) {
                rand = Math.random();
                diff = 0; /*not possible, right*/
                sw = false;
                for (int j = 0; j < arrayDou[current].length; j++) {
                    if ((arrayDou[current][j] >= rand) && (rand > diff)) {
                        diff += arrayDou[current][j];
                        tempCurrent = arrayInt[current][j];
                        sw = true;
                    }
                }
                if (sw) {
                    arrayK[tempCurrent]++;
                    counter++;
                    current = tempCurrent;
                    if (current == 0) {
                        System.out.println("#" + i + " sank in the river");
                        break;
                    }
                    if (current == 4) {
                        System.out.println("#" + i + " fell from the cliff");
                        break;
                    }
                }
                //TODO: find out what should we output as a result, test it
            }
            /*
            for (int k = 0; k < arrayK.length; k++) {
                System.out.println("node#" + k + ": " + arrayK[k]);
                arrayK[k] = 0;
            }
            */

            current = currentFin;
        }
        separate();
        System.out.println("Average = " + ((double)counter)/n);
    }

    private static void sort(double[][] arr1, int[][] arr2) {
        double min;
        int minj;
        int mini;
        for (int ii = 0; ii < arr1.length; ii++) {
            for (int i = 0; i < arr1[ii].length - 1; i++) {
                min = arr1[ii][i];
                minj = i;
                mini = arr2[ii][i];
                for (int j = i+1; j < arr1[ii].length; j++) {
                    if (arr1[ii][j] < min) {
                        min = arr1[ii][j];
                        minj = j;
                        mini = arr2[ii][j];
                    }
                }
                for (int j = minj; j > i; j--) {
                    arr1[ii][j] = arr1[ii][j - 1];
                    arr2[ii][j] = arr2[ii][j - 1];
                }
                arr1[ii][i] = min;
                arr2[ii][i] = mini;
            }
        }
    }

    public static void doCliff() {
        Scanner inpScanner = new Scanner(System.in);
        System.out.print("Amount of steps: ");
        int n = inpScanner.nextInt();
        System.out.print("Probability of going left: ");
        double probL = getDouble(inpScanner.next());
        System.out.print("Probability of going right: ");
        double probR = getDouble(inpScanner.next());
        separate();
        System.out.println("The probability of falling after " + n + " steps is " + new DoubleCliff(n,probL,probR).getProbability());
        separate();
    }
}
