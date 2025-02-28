package Matrix;
public class Translation {     public static void main(String[] args) {
        double [][] pt = {{1, 2}, {2, 4}, {3, 6}, {1, 1}};
        MatrixAbstract mpt = new Matrix(pt);
        double [][] scale = {{2, 0, 0, 0}, {0,2,0,0}, {0,0,2,0}, {0,0,0,1}};
        double [][] translation = {{1, 0, 0, 5}, {0, 1, 0, 6}, {0, 0, 1, 7}, {0, 0, 0, 1}};
        //double [][] center = {(pt[0][0] + pt[1][0] + pt[2][0] + pt[3][0])/2};
        double t = Math.toRadians(45);
        double [][] rotatex = {{1, 0, 0, 0}, {0, Math.cos(t), -1* Math.sin(t), 0}, {0, Math.sin(t), Math.cos(t), 0}, {0, 0, 0, 1}};
        double [][] roty = {{Math.cos(t), 0, Math.sin(t), 0}, {0, 1, 0, 0}, {-1* Math.sin(t), 0, Math.cos(t), 0}, {0, 0, 0, 1}};
        double [][] rotz = {{Math.cos(t), -1*Math.sin(t), 0, 0}, {Math.sin(t), Math.cos(t), 0, 0}, {0, 0, 1, 0}, {0, 0, 0, 1}};
        MatrixAbstract mrotx = new Matrix(rotatex);
        MatrixAbstract mroty = new Matrix(roty);
        MatrixAbstract mrotz = new Matrix(rotz);
        MatrixAbstract mtrans = new Matrix(translation);
        MatrixAbstract mscale = new Matrix(scale);
        System.out.println(mscale.mult(mpt));
        System.out.println(mtrans.mult(mtrans));
        System.out.println(mrotx.mult(mpt));
        System.out.println(mroty.mult(mpt));
        System.out.println(mrotz.mult(mpt));
    }
}
