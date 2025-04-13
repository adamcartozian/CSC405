package Assignment10;

import java.io.IOException;
import java.util.ArrayList;

import Assignment06.Color;
import Assignment06.Vector;
import Assignment06.VectorAbstract;
import Common.ReadWriteImage;

public class CurvesTest {
    public static void main(String[] args) {
        int width = 800;
        int height = 800;

        // Framebuffer: RGB layers
        int[][][] framebuffer = new int[3][height][width];

        // Clear to black
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                framebuffer[0][y][x] = 0;
                framebuffer[1][y][x] = 0;
                framebuffer[2][y][x] = 0;
            }
        }

        ScanConvertLine drawer = new ScanConvertLine(); // Your Bresenham implementation
        Curves curves = new Curves();
        
        VectorAbstract b0 = new Vector(-175, -200, 0, new Color(1, 1, 0)); // Yellow
        VectorAbstract b1 = new Vector(-300, 0, 0, new Color(1, 1, 0));
        VectorAbstract b2 = new Vector(300, 0, 0, new Color(1, 1, 0));
        VectorAbstract b3 = new Vector(175, -200, 0, new Color(1, 1, 0));
        ArrayList<VectorAbstract> bezierPoints = new ArrayList<>();

        curves.drawBezierCurve(b0, b1, b2, b3, drawer, bezierPoints);
        curves.render(bezierPoints, framebuffer, new Color(1, 1, 0)); // Yellow


        // --- Draw Hermite Curve ---
        VectorAbstract t0 = new Vector(-125, -225, 0, new Color(1, 0, 1)); // Magenta
        VectorAbstract t1 = new Vector(125, -225, 0, new Color(1, 0, 1));
        VectorAbstract p0 = new Vector(175, -200, 0, new Color(1, 0, 1));
        VectorAbstract p1 = new Vector(-175, -200, 0, new Color(1, 0, 1));
        ArrayList<VectorAbstract> hermitePoints = new ArrayList<>();

        curves.drawHermiteCurve(p0, p1, t0, t1, drawer, hermitePoints);
        curves.render(hermitePoints, framebuffer, new Color(1, 0, 1)); // Magenta

        // --- Draw Bézier Curve ---
        try{
            ReadWriteImage.writeImage(framebuffer, "curves.PNG");
        }
        catch (IOException e) {
			System.out.println(e);
		}
    }
}
