package Assignment12;

import java.io.IOException;
import java.util.ArrayList;

import Assignment10.ScanConvertLine;
import Assignment06.Color;
import Common.ReadWriteImage;
import Assignment11.ComplexNumber;
import Assignment06.VectorAbstract;
import Assignment06.Vector;
import Assignment10.Curves;

public class Mandelbrot {
    public static void mandelbrotBW(int [][][] framebuffer){
        ComplexNumber cnum = new ComplexNumber(-2.5, -2.0);
        double crealGap = 4.0/framebuffer[0].length; 
        double cimagGap = 4.0/framebuffer[0][0].length; 
        for (int i = 0; i < framebuffer[0].length; i++){
            for (int j = 0; j < framebuffer[0][0].length; j++){
                ComplexNumber z = new ComplexNumber(0, 0);
                int count = 0;

                while (z.mag() < 2.0 && count < 255){
                    z = z.mult(z).add(cnum);
                    count++;
                }
                framebuffer[0][i][j] = count;
                framebuffer[1][i][j] = count;
                framebuffer[2][i][j] = count;

                cnum.setImag(cnum.getImag() + cimagGap);;
            }

            cnum.setReal(cnum.getReal() + crealGap);
            cnum.setImag(-2.0);
        }
    }

    public static void mandelbrotRandomColor(int[][][] framebuffer) {
        ReadWriteImage.setLUT(); // instantiates LUT w/ random color

        ComplexNumber c = new ComplexNumber(-2.5, -2.0);
        double crGap = 4.0/framebuffer[0].length; // -2.5 to 1.5
        double ciGap = 4.0/framebuffer[0][0].length; // -2.0 to 2.0
        
        
        for (int i = 0; i < framebuffer[0].length; i++){
            for (int j = 0; j < framebuffer[0][0].length; j++){
                ComplexNumber z = new ComplexNumber(0, 0);
                int count = 0;

                while (z.mag() < 2.0 && count < 255){
                    z = z.mult(z).add(c);
                    count++;
                }
                framebuffer[0][i][j] = ReadWriteImage.LUT[count][0];
                framebuffer[1][i][j] = ReadWriteImage.LUT[count][1];
                framebuffer[2][i][j] = ReadWriteImage.LUT[count][2];

                c.setImag(c.getImag() + ciGap);;
            }

            c.setReal(c.getReal() + crGap);
            c.setImag(-2.0);
        }
    }

    public static void mandelbrotBezierColor(int[][][] framebuffer) {
        int[][] LUT = new int[256][3];

        Curves curve = new Curves();
        VectorAbstract v1 = new Vector(0, 0, 0, new Color(0, 0, 0));
        VectorAbstract v2 = new Vector(75, 255, 0, new Color(0, 0, 0));
        VectorAbstract v3 = new Vector(175, 255, 0, new Color(0, 0, 0));
        VectorAbstract v4 = new Vector(255, 0, 0, new Color(0, 0, 0));

        ArrayList<VectorAbstract> r = new ArrayList<>();
        ScanConvertLine drawer = new ScanConvertLine();
        curve.drawBezierCurve(v1, v2, v3, v4, drawer, r);
        curve.render(r, new int[3][256][256], new Color(0, 0, 0));

        ArrayList<VectorAbstract> g = new ArrayList<>();

        v1 = new Vector(0, 0, 0, new Color(0, 0, 0));
        v2 = new Vector(75, 100, 0, new Color(0, 0, 0));
        v3 = new Vector(175, 100, 0, new Color(0, 0, 0));
        v4 = new Vector(255, 0, 0, new Color(0, 0, 0));
        curve.drawBezierCurve(v1, v2, v3, v4, drawer, g);
        curve.render(g, new int[3][256][256], new Color(0, 0, 0));
        
        ArrayList<VectorAbstract> b = new ArrayList<>();
        curve.render(b, new int[3][256][256], new Color(0, 0, 0));


        for (VectorAbstract v : r){
            try {
                LUT[(int)v.getX()][0] = (int) v.getY();
            } catch (IndexOutOfBoundsException e){}
        }

        for (VectorAbstract v : g){
            try {
                LUT[(int)v.getX()][1] = (int) v.getY();
            } catch (IndexOutOfBoundsException e){}
        }

        for (VectorAbstract v : b){
            try {
                LUT[(int)v.getX()][2] = (int) v.getY();
            } catch (IndexOutOfBoundsException e){}
        }


    // Step 2: Mandelbrot generation
    ComplexNumber c = new ComplexNumber(-2.5, -2.0);
        double crGap = 4.0/framebuffer[0].length; // -2.5 to 1.5
        double ciGap = 4.0/framebuffer[0][0].length; // -2.0 to 2.0
        
        
        for (int i = 0; i < framebuffer[0].length; i++){
            for (int j = 0; j < framebuffer[0][0].length; j++){
                ComplexNumber z = new ComplexNumber(0, 0);
                int count = 0;

                while (z.mag() < 2.0 && count < 255){
                    z = z.mult(z).add(c);
                    count++;
                }
                framebuffer[0][i][j] = LUT[count][0];
                framebuffer[1][i][j] = LUT[count][1];
                framebuffer[2][i][j] = LUT[count][2];

                c.setImag(c.getImag() + ciGap);;
            }

            c.setReal(c.getReal() + crGap);
            c.setImag(-2.0);
        }
}

    public static void main(String[] args) {
        int[][][] framebuffer = new int[3][256][256];


        Mandelbrot.mandelbrotRandomColor(framebuffer);

        try{
            ReadWriteImage.writeImage(framebuffer, "assignment12_randomcolor.PNG");
        }
        catch (IOException e) {
            System.out.println(e);
        }
        framebuffer = new int[3][256][256];


        Mandelbrot.mandelbrotBezierColor(framebuffer);

        try{
            ReadWriteImage.writeImage(framebuffer, "assignment12_breziercolor.PNG");
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }
}
