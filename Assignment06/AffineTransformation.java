package Assignment06;

import Matrix.Matrix;
import Matrix.MatrixAbstract;

public class AffineTransformation extends AffineTransformationAbstract{

    @Override
    public MatrixAbstract rotateX(double theta, VectorAbstract fixedpoint, MatrixAbstract data) {
        double [][] rotx = {{1, 0, 0, 0}, {0, Math.cos(theta), -Math.sin(theta), 0}, {0, Math.sin(theta), Math.cos(theta), 0}, {0, 0, 0, 1}};
        double[][] fpToOrigin = {{1, 0, 0, -fixedpoint.getX()}, {0, 1, 0, -fixedpoint.getY()}, {0, 0, 1, -fixedpoint.getZ()}, {0, 0, 0, 1}};
        double[][] originTofp = {{1, 0, 0, fixedpoint.getX()}, {0, 1, 0, fixedpoint.getY()}, {0, 0, 1, fixedpoint.getZ()}, {0, 0, 0, 1}};
        
        MatrixAbstract mrotx = new Matrix(rotx);
        //MatrixAbstract mfpToOrgin = new Matrix(fpToOrigin);
        MatrixAbstract mfpToOrgin = translate(fixedpoint.mult(-1), data);
        //MatrixAbstract moriginToFp = new Matrix(originTofp);
        MatrixAbstract mrotate = mrotx.mult(mfpToOrgin);
        //MatrixAbstract t = moriginToFp.mult(mrotx.mult(mfpToOrgin));
        //MatrixAbstract rotated = t.mult(data.transpose());
        MatrixAbstract rotated = translate(fixedpoint, mrotate);
        return rotated;

        // translate to origin, rotate, translate back
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
        MatrixAbstract rotated = t.mult(data.transpose());
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
        MatrixAbstract rotated = t.mult(data.transpose());
        return rotated;
    }

    @Override
    public MatrixAbstract translate(VectorAbstract transvec, MatrixAbstract data) {
        double x = transvec.getX();
        double y = transvec.getY();
        double z = transvec.getZ();
        double [][] translate = {{1, 0, 0, x},{0, 1, 0, y}, {0, 0, 1, z}, {0, 0, 0, 1}};
        MatrixAbstract mtrans = new Matrix(translate);
        MatrixAbstract translated = data.mult(mtrans);
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
        MatrixAbstract scaled = t.mult(data.transpose());
        return scaled;
    }
    
}
