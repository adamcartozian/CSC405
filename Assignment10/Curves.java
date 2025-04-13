package Assignment10;

import java.io.IOException;
import java.util.ArrayList;

import Assignment05.color;
import Assignment06.VectorAbstract;
import Common.ReadWriteImage;
import Assignment06.Vector;
import Assignment06.Color;
import Assignment06.ColorAbstract;

public class Curves {
    public void drawHermiteCurve(VectorAbstract p0, VectorAbstract p1, VectorAbstract t0, VectorAbstract t1,
                                  ScanConvertLine drawer, ArrayList<VectorAbstract> points) {
        if (points == null) {
            throw new NullPointerException("Points list is null.");
        }
        int steps = 100;
        ArrayList<VectorAbstract> curveSamples = new ArrayList<>();

        for (int i = 0; i <= steps; i++) {
            double t = (double) i / steps;

            double h00 = 2 * t * t * t - 3 * t * t + 1;
            double h10 = t * t * t - 2 * t * t + t;
            double h01 = -2 * t * t * t + 3 * t * t;
            double h11 = t * t * t - t * t;

            double x = h00 * p0.getX() + h10 * t0.getX() + h01 * p1.getX() + h11 * t1.getX();
            double y = h00 * p0.getY() + h10 * t0.getY() + h01 * p1.getY() + h11 * t1.getY();
            double z = h00 * p0.getZ() + h10 * t0.getZ() + h01 * p1.getZ() + h11 * t1.getZ();

            curveSamples.add(new Vector(x, y, z, p0.getColor()));
        }

        // Connect the sample points using Bresenham
        for (int i = 0; i < curveSamples.size() - 1; i++) {
            VectorAbstract a = curveSamples.get(i);
            VectorAbstract b = curveSamples.get(i + 1);

            /*drawer.bresenham((int) Math.round(a.getX()), (int) Math.round(a.getY()),
                             (int) Math.round(b.getX()), (int) Math.round(b.getY()),
                             new Color(0, 0, 0), new Color(0, 0, 0), points);*/
            drawer.bresenham((int) Math.round(a.getX()), (int) Math.round(a.getY()),
                             (int) Math.round(b.getX()), (int) Math.round(b.getY()),
                             a.getColor(), b.getColor(), points);
        }
    }
    public void render(ArrayList<VectorAbstract> path, int[][][] framebuffer, ColorAbstract color){
        for (VectorAbstract va : path){
            try{
                int centerx = framebuffer[0][0].length/2;
                int centery = framebuffer[0].length/2;
                ColorAbstract Color2 = va.getColor();
                framebuffer[0][(int)va.getY() + centery][(int)va.getX() + centerx] = (int) color.getR()*255;
                framebuffer[1][(int)va.getY() + centery][(int)va.getX() + centerx] = (int) color.getG()*255;
                framebuffer[2][(int)va.getY() + centery][(int)va.getX() + centerx] = (int) color.getB()*255;
                //framebuffer[0][(int)va.getY() + centery][(int)va.getX() + centerx] = (int) Color2.getR()*255;
                //framebuffer[1][(int)va.getY() + centery][(int)va.getX() + centerx] = (int) Color2.getG()*255;
                //framebuffer[2][(int)va.getY() + centery][(int)va.getX() + centerx] = (int) Color2.getB()*255;
            }
            catch(ArrayIndexOutOfBoundsException e){

            }
        }
    }

    public void drawBezierCurve(VectorAbstract p0, VectorAbstract p1, VectorAbstract p2, VectorAbstract p3,
                                 ScanConvertLine drawer, ArrayList<VectorAbstract> points) {
        if (points == null) {
            throw new NullPointerException("Points list is null.");
        }
        int steps = 100;
        ArrayList<VectorAbstract> bezierSamples = new ArrayList<>();

        for (int i = 0; i <= steps; i++) {
            double t = (double) i / steps;

            double u = 1 - t;

            double x = u * u * u * p0.getX()
                     + 3 * u * u * t * p1.getX()
                     + 3 * u * t * t * p2.getX()
                     + t * t * t * p3.getX();

            double y = u * u * u * p0.getY()
                     + 3 * u * u * t * p1.getY()
                     + 3 * u * t * t * p2.getY()
                     + t * t * t * p3.getY();

            double z = u * u * u * p0.getZ()
                     + 3 * u * u * t * p1.getZ()
                     + 3 * u * t * t * p2.getZ()
                     + t * t * t * p3.getZ();

            bezierSamples.add(new Vector(x, y, z, p0.getColor()));
        }

        // Connect points with Bresenham lines
        for (int i = 0; i < bezierSamples.size() - 1; i++) {
            VectorAbstract a = bezierSamples.get(i);
            VectorAbstract b = bezierSamples.get(i + 1);

            drawer.bresenham((int) Math.round(a.getX()), (int) Math.round(a.getY()),
                             (int) Math.round(b.getX()), (int) Math.round(b.getY()),
                             new Color(0, 0, 0), new Color(0, 0, 0), points);
        }
    }

}
