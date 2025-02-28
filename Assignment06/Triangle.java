package Assignment06;

import Assignment3.ScanConvertAbstract;
import Assignment3.ScanConvertLine;
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
    }


    @Override
    public VectorAbstract getCenter() {
        double newx = (v1.getX() + v2.getX() + v3.getX())/3;
        double newy = (v1.getY() + v2.getY() + v3.getY())/3;
        double newz = (v1.getZ() + v2.getZ() + v3.getZ())/3;
        return new Vector(newx, newy, newz);
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
        for(int i = 0; i < vertices.length; i++){
            try{
                int j = i + 1;
                sca.bresenham((int)vertices[i].getX(), (int)vertices[i].getY(), (int)vertices[j].getX(), (int)vertices[j].getY(), framebuffer);        
            }
            catch (ArrayIndexOutOfBoundsException e){
                sca.bresenham((int)vertices[i].getX(), (int)vertices[i].getY(), (int)vertices[0].getX(), (int)vertices[0].getY(), framebuffer); 
            }
        }

        if(shownormal == true){
            VectorAbstract Normal = getNormal().unit().mult(20);
            VectorAbstract Center = getCenter();
            Normal = Normal.add(Center);

            sca.bresenham((int)Center.getX(), (int)Center.getY(), (int)Normal.getX(), (int)Normal.getY(), framebuffer);
        }

    }

    @Override
    public TriangleAbstract rotateX(double theta, VectorAbstract fixedpoint, TriangleAbstract data) {
         VectorAbstract[] vertices = data.getVertices();
         VectorAbstract v1 = vertices[0];
         VectorAbstract v2 = vertices[1];
         VectorAbstract v3 = vertices[2];
         Color v1color = v1.getColor();
         Color v2color = v2.getColor();
         Color v3color = v3.getColor();

         double[][] v1cord= {{v1.getX(), 0, 0, 0}, {0, v1.getY(), 0, 0}, {0, 0, v1.getZ(), 0}, {0, 0, 0, 1}};
         MatrixAbstract mv1cord = new Matrix(v1cord);
         AffineTransformationAbstract rotV1 = new AffineTransformation();
         mv1cord = rotV1.rotateX(theta, fixedpoint, mv1cord);
         v1cord = mv1cord.getMatrix();
         VectorAbstract newv1 = new Vector(v1cord[0][0], v1cord[1][1], v1cord[2][2], v1color);

         double[][] v2cord= {{v2.getX(), 0, 0, 0}, {0, v2.getY(), 0, 0}, {0, 0, v2.getZ(), 0}, {0, 0, 0, 1}};
         MatrixAbstract mv2cord = new Matrix(v2cord);
         AffineTransformationAbstract rotV2 = new AffineTransformation();
         mv2cord = rotV2.rotateX(theta, fixedpoint, mv2cord);
         v2cord = mv2cord.getMatrix();
         VectorAbstract newv2 = new Vector(v2cord[0][0], v2cord[1][1], v2cord[2][2], v2color);

         double[][] v3cord= {{v3.getX(), 0, 0, 0}, {0, v3.getY(), 0, 0}, {0, 0, v3.getZ(), 0}, {0, 0, 0, 1}};
         MatrixAbstract mv3cord = new Matrix(v3cord);
         AffineTransformationAbstract rotV3 = new AffineTransformation();
         mv3cord = rotV3.rotateX(theta, fixedpoint, mv3cord);
         v3cord = mv3cord.getMatrix();
         VectorAbstract newv3 = new Vector(v3cord[0][0], v3cord[1][1], v3cord[2][2], v3color);
         
         TriangleAbstract rotated = new Triangle(newv1, newv2, newv3);

         return rotated;
    }

    @Override
    public TriangleAbstract rotateY(double theta, VectorAbstract fixedpoint, TriangleAbstract data) {
         VectorAbstract[] vertices = data.getVertices();
         VectorAbstract v1 = vertices[0];
         VectorAbstract v2 = vertices[1];
         VectorAbstract v3 = vertices[2];
         Color v1color = v1.getColor();
         Color v2color = v2.getColor();
         Color v3color = v3.getColor();

         double[][] v1cord= {{v1.getX(), 0, 0, 0}, {0, v1.getY(), 0, 0}, {0, 0, v1.getZ(), 0}, {0, 0, 0, 1}};
         MatrixAbstract mv1cord = new Matrix(v1cord);
         AffineTransformationAbstract rotV1 = new AffineTransformation();
         mv1cord = rotV1.rotateY(theta, fixedpoint, mv1cord);
         v1cord = mv1cord.getMatrix();
         VectorAbstract newv1 = new Vector(v1cord[0][0], v1cord[1][1], v1cord[2][2], v1color);

         double[][] v2cord= {{v2.getX(), 0, 0, 0}, {0, v2.getY(), 0, 0}, {0, 0, v2.getZ(), 0}, {0, 0, 0, 1}};
         MatrixAbstract mv2cord = new Matrix(v2cord);
         AffineTransformationAbstract rotV2 = new AffineTransformation();
         mv2cord = rotV2.rotateY(theta, fixedpoint, mv2cord);
         v2cord = mv2cord.getMatrix();
         VectorAbstract newv2 = new Vector(v2cord[0][0], v2cord[1][1], v2cord[2][2], v2color);
         
         double[][] v3cord= {{v3.getX(), 0, 0, 0}, {0, v3.getY(), 0, 0}, {0, 0, v3.getZ(), 0}, {0, 0, 0, 1}};
         MatrixAbstract mv3cord = new Matrix(v3cord);
         AffineTransformationAbstract rotV3 = new AffineTransformation();
         mv3cord = rotV3.rotateY(theta, fixedpoint, mv3cord);
         v3cord = mv3cord.getMatrix();
         VectorAbstract newv3 = new Vector(v3cord[0][0], v3cord[1][1], v3cord[2][2], v3color);
         
         TriangleAbstract rotated = new Triangle(newv1, newv2, newv3);

         return rotated;
    }

    @Override
    public TriangleAbstract rotateZ(double theta, VectorAbstract fixedpoint, TriangleAbstract data) {
         VectorAbstract[] vertices = data.getVertices();
         VectorAbstract v1 = vertices[0];
         VectorAbstract v2 = vertices[1];
         VectorAbstract v3 = vertices[2];
         Color v1color = v1.getColor();
         Color v2color = v2.getColor();
         Color v3color = v3.getColor();

         double[][] v1cord= {{v1.getX(), 0, 0, 0}, {0, v1.getY(), 0, 0}, {0, 0, v1.getZ(), 0}, {0, 0, 0, 1}};
         MatrixAbstract mv1cord = new Matrix(v1cord);
         AffineTransformationAbstract rotV1 = new AffineTransformation();
         mv1cord = rotV1.rotateZ(theta, fixedpoint, mv1cord);
         v1cord = mv1cord.getMatrix();
         VectorAbstract newv1 = new Vector(v1cord[0][0], v1cord[1][1], v1cord[2][2], v1color);

         double[][] v2cord= {{v2.getX(), 0, 0, 0}, {0, v2.getY(), 0, 0}, {0, 0, v2.getZ(), 0}, {0, 0, 0, 1}};
         MatrixAbstract mv2cord = new Matrix(v2cord);
         AffineTransformationAbstract rotV2 = new AffineTransformation();
         mv2cord = rotV2.rotateZ(theta, fixedpoint, mv2cord);
         v2cord = mv2cord.getMatrix();
         VectorAbstract newv2 = new Vector(v2cord[0][0], v2cord[1][1], v2cord[2][2], v2color);
         
         double[][] v3cord= {{v3.getX(), 0, 0, 0}, {0, v3.getY(), 0, 0}, {0, 0, v3.getZ(), 0}, {0, 0, 0, 1}};
         MatrixAbstract mv3cord = new Matrix(v3cord);
         AffineTransformationAbstract rotV3 = new AffineTransformation();
         mv3cord = rotV3.rotateZ(theta, fixedpoint, mv3cord);
         v3cord = mv3cord.getMatrix();
         VectorAbstract newv3 = new Vector(v3cord[0][0], v3cord[1][1], v3cord[2][2], v3color);
         
         TriangleAbstract rotated = new Triangle(newv1, newv2, newv3);

         return rotated;
    }

    @Override
    public TriangleAbstract translate(VectorAbstract transvec, TriangleAbstract data) {
         VectorAbstract[] vertices = data.getVertices();
         VectorAbstract v1 = vertices[0];
         VectorAbstract v2 = vertices[1];
         VectorAbstract v3 = vertices[2];
         Color v1color = v1.getColor();
         Color v2color = v2.getColor();
         Color v3color = v3.getColor();

         double[][] v1cord= {{v1.getX(), 0, 0, 0}, {0, v1.getY(), 0, 0}, {0, 0, v1.getZ(), 0}, {0, 0, 0, 1}};
         MatrixAbstract mv1cord = new Matrix(v1cord);
         AffineTransformationAbstract rotV1 = new AffineTransformation();
         mv1cord = rotV1.translate(transvec, mv1cord);
         v1cord = mv1cord.getMatrix();
         VectorAbstract newv1 = new Vector(v1cord[0][0], v1cord[1][1], v1cord[2][2], v1color);

         double[][] v2cord= {{v2.getX(), 0, 0, 0}, {0, v2.getY(), 0, 0}, {0, 0, v2.getZ(), 0}, {0, 0, 0, 1}};
         MatrixAbstract mv2cord = new Matrix(v2cord);
         AffineTransformationAbstract rotV2 = new AffineTransformation();
         mv2cord = rotV2.translate(transvec, mv2cord);
         v2cord = mv2cord.getMatrix();
         VectorAbstract newv2 = new Vector(v2cord[0][0], v2cord[1][1], v2cord[2][2], v2color);
         
         double[][] v3cord= {{v3.getX(), 0, 0, 0}, {0, v3.getY(), 0, 0}, {0, 0, v3.getZ(), 0}, {0, 0, 0, 1}};
         MatrixAbstract mv3cord = new Matrix(v3cord);
         AffineTransformationAbstract rotV3 = new AffineTransformation();
         mv3cord = rotV3.translate(transvec, mv3cord);
         v3cord = mv3cord.getMatrix();
         VectorAbstract newv3 = new Vector(v3cord[0][0], v3cord[1][1], v3cord[2][2], v3color);
         
         TriangleAbstract translated = new Triangle(newv1, newv2, newv3);

         return translated;
    }

    @Override
    public TriangleAbstract scale(VectorAbstract factor, VectorAbstract fixedpoint, TriangleAbstract data) {
        VectorAbstract[] vertices = data.getVertices();
         VectorAbstract v1 = vertices[0];
         VectorAbstract v2 = vertices[1];
         VectorAbstract v3 = vertices[2];
         Color v1color = v1.getColor();
         Color v2color = v2.getColor();
         Color v3color = v3.getColor();

         double[][] v1cord= {{v1.getX(), 0, 0, 0}, {0, v1.getY(), 0, 0}, {0, 0, v1.getZ(), 0}, {0, 0, 0, 1}};
         MatrixAbstract mv1cord = new Matrix(v1cord);
         AffineTransformationAbstract rotV1 = new AffineTransformation();
         mv1cord = rotV1.scale(factor, fixedpoint, mv1cord);
         v1cord = mv1cord.getMatrix();
         VectorAbstract newv1 = new Vector(v1cord[0][0], v1cord[1][1], v1cord[2][2], v1color);

         double[][] v2cord= {{v2.getX(), 0, 0, 0}, {0, v2.getY(), 0, 0}, {0, 0, v2.getZ(), 0}, {0, 0, 0, 1}};
         MatrixAbstract mv2cord = new Matrix(v2cord);
         AffineTransformationAbstract rotV2 = new AffineTransformation();
         mv2cord = rotV2.scale(factor, fixedpoint, mv2cord);
         v2cord = mv2cord.getMatrix();
         VectorAbstract newv2 = new Vector(v2cord[0][0], v2cord[1][1], v2cord[2][2], v2color);
         
         double[][] v3cord= {{v3.getX(), 0, 0, 0}, {0, v3.getY(), 0, 0}, {0, 0, v3.getZ(), 0}, {0, 0, 0, 1}};
         MatrixAbstract mv3cord = new Matrix(v3cord);
         AffineTransformationAbstract rotV3 = new AffineTransformation();
         mv3cord = rotV3.scale(factor, fixedpoint, mv3cord);
         v3cord = mv3cord.getMatrix();
         VectorAbstract newv3 = new Vector(v3cord[0][0], v3cord[1][1], v3cord[2][2], v3color);
         
         TriangleAbstract scaled = new Triangle(newv1, newv2, newv3);

         return scaled;
    }
}
