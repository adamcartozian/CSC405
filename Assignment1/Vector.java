package Assignment1;
//Adam Cartozian

public class Vector extends VectorAbstract {

    public Vector() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    public Vector(double xin, double yin, double zin){
        this.x = xin;
        this.y = yin;
        this.z = zin;
    }

    @Override
    public double angleBetween(VectorAbstract v2) {
        double magV1 = Math.sqrt((this.x*this.x) + (this.y*this.y) + (this.z*this.z));
        double magV2 = Math.sqrt((v2.getX()*v2.getX()) + (v2.getY()*v2.getY()) + (v2.getZ()*v2.getZ()));
        double angleBetween = Math.acos(dot(v2) / (magV1 * magV2));
        return angleBetween;
    }

    @Override
    public double dot(VectorAbstract v2) {
        double dotProduct = (this.x * v2.getX()) + (this.y * v2.getY()) + (this.z * v2.getZ());
        return dotProduct;
    }

    @Override
    public VectorAbstract cross(VectorAbstract v2) {
        double i = ((this.y * v2.getZ()) - (this.z * v2.getY()));
        double j = ((this.z * v2.getX()) - (this.x * v2.getZ()));
        double k = ((this.x * v2.getY()) - (this.y * v2.getX()));
        return new Vector(i, j, k);
    }

    @Override
    public VectorAbstract unit() {
        return new Vector((this.x/this.length()), (this.y / this.length()), (this.z / this.length()));
    }

    @Override
    public double length() {
        return Math.sqrt((this.x * this.x) + (this.y * this.y) + (this.z *this.z));
    }

    @Override
    public VectorAbstract add(VectorAbstract v2) {
        return new Vector(this.x + v2.getX(), this.y + v2.getY(), this.z + v2.getZ());
    }

    @Override
    public VectorAbstract sub(VectorAbstract v2) {
        return new Vector(this.x - v2.getX(), this.y - v2.getY(), this.z - v2.getZ());
    }

    @Override
    public VectorAbstract mult(double scale) {
        return new Vector(scale * this.x, scale * this.y, scale * this.z);
    }
}
