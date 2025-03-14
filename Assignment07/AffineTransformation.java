package Assignment07;

import Matrix.Matrix;
import Matrix.MatrixAbstract;
import Assignment06.VectorAbstract;

public class AffineTransformation extends AffineTransformationAbstract{
    public static final double PRECISION = 0.000001;

    @Override
    public MatrixAbstract rotateX(double theta, VectorAbstract fixedpoint, MatrixAbstract data) {
        double [][] rotx = {{1, 0, 0, 0}, {0, Math.cos(theta), -Math.sin(theta), 0}, {0, Math.sin(theta), Math.cos(theta), 0}, {0, 0, 0, 1}};
        double[][] fpToOrigin = {{1, 0, 0, -fixedpoint.getX()}, {0, 1, 0, -fixedpoint.getY()}, {0, 0, 1, -fixedpoint.getZ()}, {0, 0, 0, 1}};
        double[][] originTofp = {{1, 0, 0, fixedpoint.getX()}, {0, 1, 0, fixedpoint.getY()}, {0, 0, 1, fixedpoint.getZ()}, {0, 0, 0, 1}};
        
        MatrixAbstract mrotx = new Matrix(rotx);
        MatrixAbstract mfpToOrgin = new Matrix(fpToOrigin);
        MatrixAbstract moriginToFp = new Matrix(originTofp);
        MatrixAbstract t = moriginToFp.mult(mrotx.mult(mfpToOrgin));
        MatrixAbstract rotated = t.mult(data.transpose()).transpose();
        return rotated;
    }

    @Override
    public MatrixAbstract rotateY(double theta, VectorAbstract fixedpoint, MatrixAbstract data) {
        double [][] roty = {{Math.cos(theta), 0, Math.sin(theta), 0}, {0, 1, 0, 0}, {-Math.sin(theta), 0, Math.cos(theta), 0}, {0, 0, 0, 1}};
        double[][] fpToOrigin = {{1, 0, 0, -fixedpoint.getX()}, {0, 1, 0, -fixedpoint.getY()}, {0, 0, 1, -fixedpoint.getZ()}, {0, 0, 0, 1}};
        double[][] originTofp = {{1, 0, 0, fixedpoint.getX()}, {0, 1, 0, fixedpoint.getY()}, {0, 0, 1, fixedpoint.getZ()}, {0, 0, 0, 1}};
        
        MatrixAbstract mroty = new Matrix(roty);
        MatrixAbstract mfpToOrgin = new Matrix(fpToOrigin);
        MatrixAbstract moriginToFp = new Matrix(originTofp);
        MatrixAbstract t = moriginToFp.mult(mroty.mult(mfpToOrgin));
        MatrixAbstract rotated = t.mult(data.transpose()).transpose();
        return rotated;
    }

    @Override
    public MatrixAbstract rotateZ(double theta, VectorAbstract fixedpoint, MatrixAbstract data) {
        double [][] rotz = {{Math.cos(theta), -Math.sin(theta), 0, 0}, {Math.sin(theta), Math.cos(theta), 0, 0}, {0, 0, 1, 0}, {0, 0, 0, 1}};
        double[][] fpToOrigin = {{1, 0, 0, -fixedpoint.getX()}, {0, 1, 0, -fixedpoint.getY()}, {0, 0, 1, -fixedpoint.getZ()}, {0, 0, 0, 1}};
        double[][] originTofp = {{1, 0, 0, fixedpoint.getX()}, {0, 1, 0, fixedpoint.getY()}, {0, 0, 1, fixedpoint.getZ()}, {0, 0, 0, 1}};
        
        MatrixAbstract mrotz = new Matrix(rotz);
        MatrixAbstract mfpToOrgin = new Matrix(fpToOrigin);
        MatrixAbstract moriginToFp = new Matrix(originTofp);
        MatrixAbstract t = moriginToFp.mult(mrotz.mult(mfpToOrgin));
        MatrixAbstract rotated = t.mult(data.transpose()).transpose();
        return rotated;
    }

    @Override
    public MatrixAbstract translate(VectorAbstract transvec, MatrixAbstract data) {
        double x = transvec.getX();
        double y = transvec.getY();
        double z = transvec.getZ();
        double [][] translate = {{1, 0, 0, x},{0, 1, 0, y}, {0, 0, 1, z}, {0, 0, 0, 1}};
        MatrixAbstract mtrans = new Matrix(translate);
        MatrixAbstract translated = mtrans.mult(data.transpose()).transpose();
        return translated;
    }

    @Override
    public MatrixAbstract scale(VectorAbstract factor, VectorAbstract fixedpoint, MatrixAbstract data) {
        double x = factor.getX();
        double y = factor.getY();
        double z = factor.getZ();
        double [][] scale = {{x, 0, 0, 0}, {0, y, 0, 0}, {0, 0, z, 0}, {0, 0, 0, 1}};
        double[][] fpToOrigin = {{1, 0, 0, -fixedpoint.getX()}, {0, 1, 0, -fixedpoint.getY()}, {0, 0, 1, -fixedpoint.getZ()}, {0, 0, 0, 1}};
        double[][] originTofp = {{1, 0, 0, fixedpoint.getX()}, {0, 1, 0, fixedpoint.getY()}, {0, 0, 1, fixedpoint.getZ()}, {0, 0, 0, 1}};
        
        MatrixAbstract mscale = new Matrix(scale);
        MatrixAbstract mfpToOrgin = new Matrix(fpToOrigin);
        MatrixAbstract moriginToFp = new Matrix(originTofp);
        MatrixAbstract t = moriginToFp.mult(mscale.mult(mfpToOrgin));
        MatrixAbstract scaled = t.mult(data.transpose()).transpose();
        return scaled;
    }

    @Override
    public MatrixAbstract rotateAxis(VectorAbstract axis, VectorAbstract fixedpoint, double arads,
            MatrixAbstract data) {
                axis = axis.unit();

                if (Math.abs(axis.getY()) < PRECISION && Math.abs(axis.getZ()) < PRECISION) {
                    return rotateX(arads, fixedpoint, data);
                }
        
                double d = Math.sqrt(Math.pow(axis.getY(), 2) + Math.pow(axis.getZ(), 2));
                double[][] fpToOrigin = {{1, 0, 0, -fixedpoint.getX()}, {0, 1, 0, -fixedpoint.getY()}, {0, 0, 1, -fixedpoint.getZ()}, {0, 0, 0, 1}};
                double[][] rXp = {{1, 0, 0, 0}, {0, axis.getZ() / d, -axis.getY() / d, 0}, {0, axis.getY() / d, axis.getZ() / d, 0}, {0, 0, 0, 1}};
                double[][] rYp = {{d, 0, -axis.getX(), 0}, {0, 1, 0, 0}, {axis.getX(), 0, d, 0}, {0, 0, 0, 1}};
                double[][] rotateZ = {{Math.cos(arads), -Math.sin(arads), 0, 0}, {Math.sin(arads), Math.cos(arads), 0, 0}, {0, 0, 1, 0}, {0, 0, 0, 1}};
                double[][] rYn = {{d, 0, axis.getX(), 0}, {0, 1, 0, 0}, {-axis.getX(), 0, d, 0}, {0, 0, 0, 1}};
                double[][] rXn = {{1, 0, 0, 0}, {0, axis.getZ() / d, axis.getY() / d, 0}, {0, -axis.getY() / d, axis.getZ() / d, 0}, {0, 0, 0, 1}};
                double[][] originTofp = {{1, 0, 0, fixedpoint.getX()}, {0, 1, 0, fixedpoint.getY()},  {0, 0, 1, fixedpoint.getZ()}, {0, 0, 0, 1}};
        
                MatrixAbstract mfpToOriginMatrix = new Matrix(fpToOrigin);
                MatrixAbstract mrXpMatrix = new Matrix(rXp);
                MatrixAbstract mrYpMatrix = new Matrix(rYp);
                MatrixAbstract mrotateZMatrix = new Matrix(rotateZ);
                MatrixAbstract mrYnMatrix = new Matrix(rYn);
                MatrixAbstract mrXnMatrix = new Matrix(rXn);
                MatrixAbstract moriginToFpMatrix = new Matrix(originTofp);
                MatrixAbstract rotatedAxis = moriginToFpMatrix.mult(mrXnMatrix.mult(mrYnMatrix.mult(mrotateZMatrix.mult(mrYpMatrix.mult(mrXpMatrix.mult(mfpToOriginMatrix))))));
            
                return rotatedAxis.mult(data.transpose()).transpose();
            }
    
}
