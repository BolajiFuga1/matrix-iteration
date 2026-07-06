import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Iterative matrix program using Z1–Z16 patterns and horizontal arithmetic.
 */
public class MatrixIteration {

    private static final char Y = 'Y';
    private static final char X = 'X';

    /** 4×4 matrix; each row uses one character from the Z definition. */
    static final class Matrix {
        private final char[][] cells = new char[4][4];

        Matrix(char row0, char row1, char row2, char row3) {
            fillRow(0, row0);
            fillRow(1, row1);
            fillRow(2, row2);
            fillRow(3, row3);
        }

        Matrix(Matrix other) {
            for (int r = 0; r < 4; r++) {
                System.arraycopy(other.cells[r], 0, cells[r], 0, 4);
            }
        }

        private void fillRow(int row, char value) {
            for (int c = 0; c < 4; c++) {
                cells[row][c] = value;
            }
        }

        static Matrix flipHorizontal(Matrix source) {
            Matrix result = new Matrix(source);
            for (int r = 0; r < 4; r++) {
                for (int c = 0; c < 2; c++) {
                    char temp = result.cells[r][c];
                    result.cells[r][c] = result.cells[r][3 - c];
                    result.cells[r][3 - c] = temp;
                }
            }
            return result;
        }

        static char addCells(char a, char b) {
            if (a == Y && b == Y) {
                return Y;
            }
            if (a == X && b == X) {
                return Y;
            }
            return X;
        }

        static Matrix add(Matrix left, Matrix right) {
            Matrix result = new Matrix(left);
            for (int r = 0; r < 4; r++) {
                for (int c = 0; c < 4; c++) {
                    result.cells[r][c] = addCells(left.cells[r][c], right.cells[r][c]);
                }
            }
            return result;
        }

        static Matrix addChain(Matrix... matrices) {
            Matrix result = new Matrix(matrices[0]);
            for (int i = 1; i < matrices.length; i++) {
                result = add(result, matrices[i]);
            }
            return result;
        }

        boolean equals(Matrix other) {
            for (int r = 0; r < 4; r++) {
                for (int c = 0; c < 4; c++) {
                    if (cells[r][c] != other.cells[r][c]) {
                        return false;
                    }
                }
            }
            return true;
        }

        int countMatchingPositions(Matrix other) {
            int matches = 0;
            for (int r = 0; r < 4; r++) {
                for (int c = 0; c < 4; c++) {
                    if (cells[r][c] == other.cells[r][c]) {
                        matches++;
                    }
                }
            }
            return matches;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (int r = 0; r < 4; r++) {
                for (int c = 0; c < 4; c++) {
                    if (c > 0) {
                        sb.append(' ');
                    }
                    sb.append(cells[r][c]);
                }
                if (r < 3) {
                    sb.append('\n');
                }
            }
            return sb.toString();
        }
    }

    private static final Matrix[] Z = {
        new Matrix(X, X, X, X), // Z1
        new Matrix(Y, X, Y, X), // Z2
        new Matrix(X, Y, X, X), // Z3
        new Matrix(Y, X, X, Y), // Z4
        new Matrix(Y, Y, Y, X), // Z5
        new Matrix(Y, Y, X, Y), // Z6
        new Matrix(Y, X, X, X), // Z7
        new Matrix(Y, Y, X, X), // Z8
        new Matrix(X, X, Y, Y), // Z9
        new Matrix(X, X, X, Y), // Z10
        new Matrix(Y, X, Y, Y), // Z11
        new Matrix(X, Y, Y, Y), // Z12
        new Matrix(X, Y, Y, X), // Z13
        new Matrix(X, X, Y, X), // Z14
        new Matrix(X, Y, X, Y), // Z15
        new Matrix(Y, Y, Y, Y)  // Z16
    };

    private static final int[] C20_ALLOWED = {0, 5, 9, 14}; // Z1, Z6, Z10, Z15
    private static final int[] C15_RESTART = {0, 6, 10};    // Z1, Z7, Z11

    private static boolean matchesAnyZ(Matrix matrix, int[] zIndices) {
        for (int index : zIndices) {
            if (matrix.equals(Z[index])) {
                return true;
            }
        }
        return false;
    }

    private static int zLabel(Matrix matrix) {
        for (int i = 0; i < Z.length; i++) {
            if (matrix.equals(Z[i])) {
                return i + 1;
            }
        }
        return -1;
    }

    private static String zName(Matrix matrix) {
        int label = zLabel(matrix);
        return label > 0 ? "Z" + label : matrix.toString().replace('\n', '/');
    }

    private static void printMatrixBlock(String name, Matrix matrix) {
        System.out.println(name);
        System.out.println(matrix);
        System.out.println();
    }

    private static void runIteration(int iteration, Random random) {
        Matrix c1;
        Matrix c2;
        Matrix c3;
        Matrix c4;
        Matrix c5;
        Matrix c6;
        Matrix c7;
        Matrix c8;
        Matrix c9;
        Matrix c10;
        Matrix c11;
        Matrix c12;
        Matrix c13;
        Matrix c14;
        Matrix c15;
        Matrix c16;
        Matrix c18;
        Matrix c20;

        while (true) {
            // START: randomly select four distinct patterns from Z1–Z16
            List<Integer> indices = new ArrayList<>();
            for (int i = 0; i < Z.length; i++) {
                indices.add(i);
            }
            Collections.shuffle(indices, random);

            c1 = new Matrix(Z[indices.get(0)]);
            c2 = new Matrix(Z[indices.get(1)]);
            c3 = new Matrix(Z[indices.get(2)]);
            c4 = new Matrix(Z[indices.get(3)]);

            System.out.println("--- Iteration " + iteration + " ---");
            System.out.println("Selected: C1=" + zName(c1) + ", C2=" + zName(c2)
                + ", C3=" + zName(c3) + ", C4=" + zName(c4));
            System.out.println();

            System.out.println("Arrangement (C4  C3  C2  C1):");
            printCombinedRow("Row 1", c4, c3, c2, c1, 0);
            printCombinedRow("Row 2", c4, c3, c2, c1, 1);
            printCombinedRow("Row 3", c4, c3, c2, c1, 2);
            printCombinedRow("Row 4", c4, c3, c2, c1, 3);
            System.out.println();

            // C5–C8: copy C1–C4 horizontally from right to left
            c5 = Matrix.flipHorizontal(c1);
            c6 = Matrix.flipHorizontal(c2);
            c7 = Matrix.flipHorizontal(c3);
            c8 = Matrix.flipHorizontal(c4);

            c9 = Matrix.add(c1, c2);
            c10 = Matrix.add(c3, c4);
            c11 = Matrix.add(c5, c6);
            c12 = Matrix.add(c7, c8);
            c13 = Matrix.add(c9, c10);
            c14 = Matrix.add(c11, c12);
            c15 = Matrix.add(c13, c14);
            c16 = Matrix.add(c15, c1);

            if (matchesAnyZ(c15, C15_RESTART)) {
                System.out.println("C15 equals " + zName(c15) + " -> restarting iteration "
                    + iteration + " from START.");
                System.out.println();
                continue;
            }

            c18 = Matrix.addChain(c1, c4, c7, c10);
            c20 = Matrix.addChain(c3, c7, c11, c15);

            if (matchesAnyZ(c20, C20_ALLOWED)) {
                System.out.println("C20 = " + zName(c20));
                System.out.println(c20);
            } else {
                System.out.println("C20 = Nil");
            }
            System.out.println();

            Matrix[] allC = {
                c1, c2, c3, c4, c5, c6, c7, c8,
                c9, c10, c11, c12, c13, c14, c15, c16
            };

            List<String> matches = new ArrayList<>();
            for (int i = 0; i < allC.length; i++) {
                int matchingPlaces = c18.countMatchingPositions(allC[i]);
                if (matchingPlaces >= 4) {
                    matches.add("C" + (i + 1) + " (" + matchingPlaces + " places)");
                }
            }

            if (!matches.isEmpty()) {
                System.out.println("C18 matches in four or more places (iteration " + iteration + "):");
                System.out.println("C18:");
                System.out.println(c18);
                System.out.println();
                System.out.printf("%-12s%s%n", "Iteration", "Matching C values");
                System.out.printf("%-12s%s%n", "---------", "------------------");
                System.out.printf("%-12d%s%n", iteration, String.join(", ", matches));
            }

            break;
        }
    }

    private static void printCombinedRow(String label, Matrix c4, Matrix c3, Matrix c2, Matrix c1, int row) {
        System.out.printf("%-6s %c %c %c %c | %c %c %c %c | %c %c %c %c | %c %c %c %c%n",
            label,
            c4.cells[row][0], c4.cells[row][1], c4.cells[row][2], c4.cells[row][3],
            c3.cells[row][0], c3.cells[row][1], c3.cells[row][2], c3.cells[row][3],
            c2.cells[row][0], c2.cells[row][1], c2.cells[row][2], c2.cells[row][3],
            c1.cells[row][0], c1.cells[row][1], c1.cells[row][2], c1.cells[row][3]);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        System.out.print("Enter the number of iterations (L): ");
        int L = scanner.nextInt();
        System.out.println("Number of iterations: " + L);
        System.out.println();

        for (int iteration = 1; iteration <= L; iteration++) {
            runIteration(iteration, random);
            System.out.println();
        }

        scanner.close();
        System.out.println("END.");
    }
}
