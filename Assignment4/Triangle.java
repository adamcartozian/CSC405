package Assignment4;

import Assignment1.VectorAbstract;
import Assignment1.Vector;

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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'render'");
    }
}
