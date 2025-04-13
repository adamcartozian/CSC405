package Assignment10;

import java.util.ArrayList;

import Assignment06.Color;
import Assignment06.ColorAbstract;
import Assignment06.VectorAbstract;
import Assignment06.Vector;


public class ScanConvertLine extends ScanConvertAbstract {

    @Override
    public void bresenham(int x0, int y0, int x1, int y1, ColorAbstract c0, ColorAbstract c1, int[][][] framebuffer)
            throws NullPointerException, ArrayIndexOutOfBoundsException {
                if (framebuffer == null) {
                    throw new NullPointerException("Frame buffer is null.");
                }
                
                
                try {
                    int[] c0rgb = c0.scale(255);
                    int[] c1rgb = c1.scale(255);
                    //int[] plottedcolor = c0rgb;

                    double r = c0rgb[0];
                    double g = c0rgb[2];
                    double b = c0rgb[1];
                    int y = y0;
                    double t = 0;
                    

                    int dx = Math.abs(x1 - x0);
                    int dy = Math.abs(y1 - y0);
                
                // Determine step direction
                    int sx = (x0 < x1) ? 1 : -1;
                    int sy = (y0 < y1) ? 1 : -1;
                
                // Initialize decision variable
                    int err = dx - dy;
                
                    while (true) {
                    // Plot the current point
                        
                        framebuffer[0][y0][(int)Math.round(x0)] = (int)r;
                        framebuffer[1][y0][(int)Math.round(x0)] = (int)b;
                        framebuffer[2][y0][(int)Math.round(x0)] = (int)g;


                    // increment color
                        if (r > c1rgb[0]){
                            r--;
                            r--;
                        } else if (c1rgb[0] > r){
                            r++;
                            r++;
                        }
            
                        if (g > c1rgb[2]){
                            g--;
                            g--;
                        } else if (c1rgb[2] > g){
                            g++;
                            g++;
                        }
                        
                        if (b > c1rgb[1]){
                            b--;
                            b--;
                        } else if (c1rgb[1] > b){
                            b++;
                            b++;
                        }
            
                        r = (r > 255) ? 255 : (r < 0.0) ? 0.0 : r;
                        g = (g > 255) ? 255 : (g < 0.0) ? 0.0 : g;
                        b = (b > 255) ? 255 : (b < 0.0) ? 0.0 : b;  
                    
                    // If we reach the endpoint, break the loop
                        if (x0 == x1 && y0 == y1) {
                        break;
                        }
                    
                    // Calculate the error term and adjust the position accordingly
                        int e2 = 2 * err;
                    
                        if (e2 > -dy) {
                            err -= dy;
                            x0 += sx;
                        }
                    
                        if (e2 < dx) {
                            err += dx;
                            y0 += sy;
                        }
                }
                } catch (ArrayIndexOutOfBoundsException e) {
        
                }
    }

    @Override
    public void bresenham(int x0, int y0, int x1, int y1, Color c0, Color c1, ArrayList<VectorAbstract> points) {
        if (points == null) {
            throw new NullPointerException("Points list is null.");
        }
    
        try {
            int dx = Math.abs(x1 - x0);
            int dy = Math.abs(y1 - y0);
    
            int sx = (x0 < x1) ? 1 : -1;
            int sy = (y0 < y1) ? 1 : -1;
    
            int err = dx - dy;
    
            int z = 0; // Default z-value, update this if needed for depth or other data
    
            while (true) {
                // Add the current 3D point to the list
                points.add(new Vector(x0, y0, z, null)); // Assuming Vector3D extends VectorAbstract
    
                if (x0 == x1 && y0 == y1) {
                    break;
                }
    
                int e2 = 2 * err;
    
                if (e2 > -dy) {
                    err -= dy;
                    x0 += sx;
                }
    
                if (e2 < dx) {
                    err += dx;
                    y0 += sy;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
