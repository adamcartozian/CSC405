package Assignment05;

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
                    double g = c0rgb[1];
                    double b = c0rgb[2];
                    int x = x0;
                    

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
                        if(x <= x1){
                            double multiplier = (double)(x - x0) / (double)(x1 - x0);
                            if (r > c1rgb[0]){
                                r -= (multiplier * (r - c1rgb[0]));
                            } else if (c1rgb[0] > r){
                                r += (multiplier * (c1rgb[0] - r));
                            }
                
                            if (g > c1rgb[1]){
                                g -= (multiplier * (g - c1rgb[1]));
                            } else if (c1rgb[1] > g){
                                g += (multiplier * (c1rgb[1] - g));
                            }
                            
                            if (b > c1rgb[2]){
                                b -= (multiplier * (b - c1rgb[2]));
                            } else if (c1rgb[2] > b){
                                b += (multiplier * (c1rgb[2] - b));
                            }
                
                            r = (r > 1.0) ? 1.0 : (r < 0.0) ? 0.0 : r;
                            g = (g > 1.0) ? 1.0 : (g < 0.0) ? 0.0 : g;
                            b = (b > 1.0) ? 1.0 : (b < 0.0) ? 0.0 : b;  
                            x++;
                        }
                        /*if (plottedcolor != c1rgb){
                            plottedcolor[0] = plottedcolor[0] - 10;
                            plottedcolor[1] = plottedcolor[1] + 10;
                            plottedcolor[2] = plottedcolor[2] + 10;
                        }*/
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
    
}
