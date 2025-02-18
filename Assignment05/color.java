package Assignment05;

public class color extends ColorAbstract {

    public color(double r, double g, double b) {
        if ((r < 0.0 || r > 1.0) || (g < 0.0 || g > 1.0) || (b < 0.0 || b > 1.0)){
            throw new IllegalArgumentException("Value(s) out of range");
        }
        else{
            this.setB(b);
            this.setR(r);
            this.setG(g);
        }
    }

    @Override
    public int[] scale(int s) {
        int newR = (int)(this.getR() * s);
        int newB = (int)(this.getB() * s);
        int newG = (int)(this.getG() * s);
        int[] scaled = {newR, newG, newB};
        return scaled;
    }
}
