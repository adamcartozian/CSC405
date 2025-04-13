package Assignment11;

import java.io.IOException;

import Common.ReadWriteImage;

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

    public static void main(String[] args) {
        int[][][] framebuffer = new int[3][256][256];

        Mandelbrot.mandelbrotBW(framebuffer);

        try{
            ReadWriteImage.writeImage(framebuffer, "assignment11.PNG");
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }
}
