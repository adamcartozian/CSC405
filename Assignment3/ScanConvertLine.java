package Assignment3;

//https://www.sanfoundry.com/java-program-bresenham-line-algorithm/

import java.awt.Color;

public class ScanConvertLine extends ScanConvertAbstract{
    Color color = new Color(255,255,255);

    @Override
    public void digitaldifferentialanalyzer(int x0, int y0, int x1, int y1, int[][][] framebuffer)
            throws NullPointerException, ArrayIndexOutOfBoundsException {
                int intx;
                int inty;
                
                if (framebuffer == null) {
                    throw new NullPointerException("Framebuffer is null");
                }
                
                try {
                    if(x1-x0 == 0){
                        if(y0>y1){
                            intx = x0;
                            inty = y0;
                            x0 = x1;
                            y0 = y1;
                            x1 = intx;
                            y1 = inty;
                        }
                        for(int y = y0; y<=y1; y++){
                            framebuffer[0][y][(int)Math.round(x0)] = color.getRed();
                            framebuffer[1][y][(int)Math.round(x0)] = color.getBlue();
                            framebuffer[2][y][(int)Math.round(x0)] = color.getGreen();
                        }
                    }
                    else{
                        double m = (double)(y1-y0)/(x1-x0);
                        if ((m>=0 && m<=1) || (m>= -1 && m<=0)){
                            if(x0>x1){
                                intx = x0;
                                inty = y0;
                                x0 = x1;
                                y0 = y1;
                                x1 = intx;
                                y1 = inty;
                            }
                            double y = y0;
                            for (int x=x0; x <= x1; x++){
                                y+=m;
                                framebuffer[0][(int)Math.round(y)][x] = color.getRed();
                                framebuffer[1][(int)Math.round(y)][x] = color.getBlue();
                                framebuffer[2][(int)Math.round(y)][x] = color.getGreen();
                            }
                        }
                        else if(m>1 || m<-1){
                            if(y0>y1){
                                intx = x0;
                                inty = y0;
                                x0 = x1;
                                y0 = y1;
                                x1 = intx;
                                y1 = inty;
                            }
                            double x = x0;
                            for (int y=y0; y <= y1; y++){
                                x+=(1/m);
                                framebuffer[0][y][(int)Math.round(x)] = color.getRed();
                                framebuffer[1][y][(int)Math.round(x)] = color.getBlue();
                                framebuffer[2][y][(int)Math.round(x)] = color.getGreen();
                            }
                        }
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    
                }
    }

    @Override
    public void bresenham(int x0, int y0, int x1, int y1, int[][][] framebuffer)
            throws NullPointerException, ArrayIndexOutOfBoundsException {
         if (framebuffer == null) {
            throw new NullPointerException("Frame buffer is null.");
        }
        
        
        try {
            int dx = Math.abs(x1 - x0);
            int dy = Math.abs(y1 - y0);
        
        // Determine step direction
            int sx = (x0 < x1) ? 1 : -1;
            int sy = (y0 < y1) ? 1 : -1;
        
        // Initialize decision variable
            int err = dx - dy;
        
            while (true) {
            // Plot the current point
                framebuffer[0][y0][(int)Math.round(x0)] = color.getRed();
                framebuffer[1][y0][(int)Math.round(x0)] = color.getBlue();
                framebuffer[2][y0][(int)Math.round(x0)] = color.getGreen();
            
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
