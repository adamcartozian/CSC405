package Matrix;

public class Matrix extends MatrixAbstract {

    public Matrix(double[][] m) {
        this.setMatrix(m);
    }

    @Override
    public MatrixAbstract mult(MatrixAbstract m) throws IllegalArgumentException {
        double b[][] = m.getMatrix();
        double a[][] = this.getMatrix();
        int bRows = b.length;
        int bColumns = b[0].length;
        int aRows = a.length;
        int aColumns = a[0].length;

        if (aColumns != bRows){
            throw new IllegalArgumentException("Matricies are not able to be multiplied.");
        }

        double c[][] = new double[aRows][bColumns];
    
        for (int i = 0; i < aRows; i++) {
            for (int j = 0; j < bColumns; j++) {
                for (int k = 0; k < bRows; k++) {
                    c[i][j] += a[i][k] * b[k][j];
                }
            }
        }
        return new Matrix(c);
    }

    @Override
    public MatrixAbstract invert() throws ArithmeticException, IllegalArgumentException {
        double m[][] = this.getMatrix();
        int n = m.length;
        double augmentedM[][] = new double[n][2*n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                augmentedM[i][j] = m[i][j];
                augmentedM[i][j + n] = (i == j) ? 1 : 0;
            }
        }

        for (int i = 0; i < n; i++) {
            double pivot = augmentedM[i][i];
            if(pivot == 0){
                throw new IllegalArgumentException("It is not invertable");
            }

            for (int j = 0; j < 2 * n; j++) {
                augmentedM[i][j] /= pivot;
            }

            for (int j = 0; j < n; j++) {
                if (i != j) {
                    double factor = augmentedM[j][i];
                    for (int k = 0; k < 2 * n; k++) {
                        augmentedM[j][k] -= factor * augmentedM[i][k];
                    }
                }
            }
        }

        double[][] inverse = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                inverse[i][j] = augmentedM[i][j + n];
            }
        }
        
        return new Matrix(inverse);
    }

    @Override
    public MatrixAbstract transpose() {
        double m[][] = this.getMatrix();
        int rows = m.length;
        int columns = m[0].length;
        double[][] transposed = new double[columns][rows];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                transposed[j][i] = m[i][j];
            }
        }
        return new Matrix(transposed);
    }

    @Override
    public MatrixAbstract scale(double s) {
        double m[][] = this.getMatrix();
        int rows = m.length;
        int columns = m[0].length;
        double[][] scaled = new double[rows][columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                scaled[i][j] = m[i][j] * s;
            }
        }
        return new Matrix(scaled);
    }
    
}
