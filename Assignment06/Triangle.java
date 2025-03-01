package Assignment06;

import Matrix.Matrix;
import Matrix.MatrixAbstract;

public class Triangle extends TriangleAbstract {
    private VectorAbstract v1;
    private VectorAbstract v2;
    private VectorAbstract v3;

    public Triangle() {
        this.v1 = null;
        this.v2 = null;
        this.v3 = null;
        this.vertices = null;
    }
    
    public Triangle (VectorAbstract v01, VectorAbstract v02, VectorAbstract v03){
        this.v1 = v01;
        this.v2 = v02;
        this.v3 = v03;
        this.vertices = new VectorAbstract[3];
        vertices[0] = v01;
        vertices[1] = v02;
        vertices[2] = v03;

        Color white = new Color(1.0, 1.0, 1.0);

        if (v01.getColor() == null) {
            v01.setColor(white);
        }

        if (v02.getColor() == null) {
            v02.setColor(white);
        }

        if (v03.getColor() == null) {
            v03.setColor(white);
        }
    }


    @Override
    public VectorAbstract getCenter() {
        double newx = (v1.getX() + v2.getX() + v3.getX())/3;
        double newy = (v1.getY() + v2.getY() + v3.getY())/3;
        double newz = (v1.getZ() + v2.getZ() + v3.getZ())/3;
        //Color color = v1.getColor();
        return new Vector(newx, newy, newz, null);
    }

    @Override
    public double getPerimeter() {
        double disV1V2 = Math.sqrt(Math.pow((v2.getX() - v1.getX()), 2)+Math.pow((v2.getY() - v1.getY()), 2) + Math.pow((v2.getZ() - v1.getZ()), 2));
        double disV2V3 = Math.sqrt(Math.pow((v3.getX() - v2.getX()), 2)+Math.pow((v3.getY() - v2.getY()), 2) + Math.pow((v3.getZ() - v2.getZ()), 2));
        double disV3V1 = Math.sqrt(Math.pow((v3.getX() - v1.getX()), 2)+Math.pow((v3.getY() - v1.getY()), 2) + Math.pow((v3.getZ() - v1.getZ()), 2));
        return (disV1V2 + disV2V3 + disV3V1);
    }

    @Override
    public double getArea() {
        VectorAbstract a = v1.sub(v2);
        VectorAbstract b = v1.sub(v3);
        VectorAbstract c = a.cross(b);
        double magC = c.length();
        return (magC / 2);
    }

    @Override
    public VectorAbstract getNormal() {
        VectorAbstract a = v2.sub(v1);
        VectorAbstract b = v3.sub(v1);
        VectorAbstract c = a.cross(b);
        return c.unit();
    }

    @Override
    public void render(int[][][] framebuffer, boolean shownormal) {
       ScanConvertAbstract sca = new ScanConvertLine();
       ColorAbstract white = new Color(1.0, 1.0, 1.0);
        for(int i = 0; i < vertices.length; i++){
            try{
                int j = i + 1;
                sca.bresenham((int)vertices[i].getX(), (int)vertices[i].getY(), (int)vertices[j].getX(), (int)vertices[j].getY(), vertices[i].getColor(), vertices[j].getColor(), framebuffer);          
            }
            catch (ArrayIndexOutOfBoundsException e){
                sca.bresenham((int)vertices[i].getX(), (int)vertices[i].getY(), (int)vertices[0].getX(), (int)vertices[0].getY(), vertices[i].getColor(), vertices[0].getColor(), framebuffer); 
            }
        }

        if(shownormal == true){
            VectorAbstract Normal = getNormal().unit().mult(20);
            VectorAbstract Center = getCenter();
            Normal = Normal.add(Center);

            sca.bresenham((int)Center.getX(), (int)Center.getY(), (int)Normal.getX(), (int)Normal.getY(), white, white, framebuffer);
        }

    }

    @Override
    public TriangleAbstract rotateX(double theta, VectorAbstract fixedpoint, TriangleAbstract data) {
        AffineTransformationAbstract a = new AffineTransformation();

        VectorAbstract[] vertices = data.getVertices();

        double[][] datapoints = {{vertices[0].getX(), vertices[0].getY(), vertices[0].getZ(), 1.0},
                             {vertices[1].getX(), vertices[1].getY(), vertices[1].getZ(), 1.0},
                             {vertices[2].getX(), vertices[2].getY(), vertices[2].getZ(), 1.0}
                            };

        MatrixAbstract mdatapoints = new Matrix(datapoints);

        MatrixAbstract rotateX = a.rotateX(theta, fixedpoint, mdatapoints);

        double[][] result = rotateX.getMatrix();

        for (int i = 0; i < vertices.length; i++){
            vertices[i].setX(result[i][0]);
            vertices[i].setY(result[i][1]);
            vertices[i].setZ(result[i][2]);
        }
        
        Triangle rotated = new Triangle(vertices[0], vertices[1], vertices[2]);

        return rotated;
    }

    @Override
    public TriangleAbstract rotateY(double theta, VectorAbstract fixedpoint, TriangleAbstract data) {
         AffineTransformationAbstract a = new AffineTransformation();

        VectorAbstract[] vertices = data.getVertices();

        double[][] datapoints = {{vertices[0].getX(), vertices[0].getY(), vertices[0].getZ(), 1.0},
                             {vertices[1].getX(), vertices[1].getY(), vertices[1].getZ(), 1.0},
                             {vertices[2].getX(), vertices[2].getY(), vertices[2].getZ(), 1.0}
                            };

        MatrixAbstract mdatapoints = new Matrix(datapoints);

        MatrixAbstract rotateX = a.rotateY(theta, fixedpoint, mdatapoints);

        double[][] result = rotateX.getMatrix();

        for (int i = 0; i < vertices.length; i++){
            vertices[i].setX(result[i][0]);
            vertices[i].setY(result[i][1]);
            vertices[i].setZ(result[i][2]);
        }
        
        Triangle rotated = new Triangle(vertices[0], vertices[1], vertices[2]);

        return rotated;
    }

    @Override
    public TriangleAbstract rotateZ(double theta, VectorAbstract fixedpoint, TriangleAbstract data) {
        AffineTransformationAbstract a = new AffineTransformation();

        VectorAbstract[] vertices = data.getVertices();

        double[][] datapoints = {{vertices[0].getX(), vertices[0].getY(), vertices[0].getZ(), 1.0},
                             {vertices[1].getX(), vertices[1].getY(), vertices[1].getZ(), 1.0},
                             {vertices[2].getX(), vertices[2].getY(), vertices[2].getZ(), 1.0}
                            };

        MatrixAbstract mdatapoints = new Matrix(datapoints);

        MatrixAbstract rotateX = a.rotateZ(theta, fixedpoint, mdatapoints);

        double[][] result = rotateX.getMatrix();

        for (int i = 0; i < vertices.length; i++){
            vertices[i].setX(result[i][0]);
            vertices[i].setY(result[i][1]);
            vertices[i].setZ(result[i][2]);
        }
        
        Triangle rotated = new Triangle(vertices[0], vertices[1], vertices[2]);

        return rotated;
    }

    @Override
    public TriangleAbstract translate(VectorAbstract transvec, TriangleAbstract data) {
        AffineTransformationAbstract a = new AffineTransformation();

        VectorAbstract[] vertices = data.getVertices();

        double[][] datapoints = {{vertices[0].getX(), vertices[0].getY(), vertices[0].getZ(), 1.0},
                             {vertices[1].getX(), vertices[1].getY(), vertices[1].getZ(), 1.0},
                             {vertices[2].getX(), vertices[2].getY(), vertices[2].getZ(), 1.0}
                            };

        MatrixAbstract mdatapoints = new Matrix(datapoints);

        MatrixAbstract mtranslation = a.translate(transvec, mdatapoints);

        double[][] result = mtranslation.getMatrix();

        for (int i = 0; i < vertices.length; i++){
            vertices[i].setX(result[i][0]);
            vertices[i].setY(result[i][1]);
            vertices[i].setZ(result[i][2]);
        }
        
        Triangle p = new Triangle(vertices[0], vertices[1], vertices[2]);

        return p;
    }

    @Override
    public TriangleAbstract scale(VectorAbstract factor, VectorAbstract fixedpoint, TriangleAbstract data) {
        AffineTransformationAbstract a = new AffineTransformation();

        VectorAbstract center = data.getCenter();
        VectorAbstract centering = new Vector(fixedpoint.getX() - center.getX(),
                                              fixedpoint.getY() - center.getY(),
                                              fixedpoint.getZ() - center.getZ(),
                                              null);

        VectorAbstract uncentering = new Vector(-centering.getX(), -centering.getY(), -centering.getZ(), null);

        VectorAbstract[] vertices = data.getVertices();

        double[][] datapoints = {{vertices[0].getX(), vertices[0].getY(), vertices[0].getZ(), 1.0},
                             {vertices[1].getX(), vertices[1].getY(), vertices[1].getZ(), 1.0},
                             {vertices[2].getX(), vertices[2].getY(), vertices[2].getZ(), 1.0}
                            };

        MatrixAbstract mdatapoints = new Matrix(datapoints);

        MatrixAbstract toFP = a.translate(centering, mdatapoints);
        MatrixAbstract scale = a.scale(factor, fixedpoint, toFP);
        MatrixAbstract toOriginalLocation = a.translate(uncentering, scale);

        double[][] result = toOriginalLocation.getMatrix();

        for (int i = 0; i < vertices.length; i++){
            vertices[i].setX(result[i][0]);
            vertices[i].setY(result[i][1]);
            vertices[i].setZ(result[i][2]);
        }
        
        Triangle p = new Triangle(vertices[0], vertices[1], vertices[2]);

        return p;
    }

    public VectorAbstract[] getVertices() {
		VectorAbstract[] v = {((Vector)this.vertices[0]).copy(),
							  ((Vector)this.vertices[1]).copy(),
							  ((Vector)this.vertices[2]).copy()};

		return v;
	}
}
