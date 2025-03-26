package Assignment06;

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
    /*public void bresenham(int x0, int y0, int x1, int y1, ColorAbstract c0, ColorAbstract c1, int[][][] framebuffer)
            throws NullPointerException, ArrayIndexOutOfBoundsException {
        
        if (framebuffer == null) {
            throw new NullPointerException("Frame buffer is null.");
        }


        try {
            if (Math.abs(y1 - y0) < Math.abs(x1 - x0)){
                if (x0 > x1) {
                    bresenhamSlopeLT45(x1, y1, x0, y0, c1, c0, framebuffer);
                } else {
                    bresenhamSlopeLT45(x0, y0, x1, y1, c0, c1, framebuffer);
                }
            } else {
                if (y0 > y1) {
                    bresenhamSlopeGTE45(x1, y1, x0, y0, c1, c0, framebuffer);
                } else {
                    bresenhamSlopeGTE45(x0, y0, x1, y1, c0, c1, framebuffer);
                }
            }
        } catch (ArrayIndexOutOfBoundsException e){

        }
    }


    // -1 < m < 1
    private void bresenhamSlopeLT45(int x0, int y0, int x1, int y1, ColorAbstract c0, ColorAbstract c1, int[][][] framebuffer){
        int dx = x1 - x0;
        int dy = y1 - y0;
        int y = y0;
        
        int inc = 1;
        if (dy < 0){
            inc = -1;
            dy = -dy;
        }
        int d = (2 * dy) + dx;

        if (dy == 0) {
            d = -1;
        }

        for (int x = x0; x <= x1; x++){
            try {    
                double multiplier = (double)(x - x0) / (double)(x1 - x0);

                writePixel(x, y, framebuffer, linearlyInterpolate(c0, c1, multiplier));
            } catch (ArrayIndexOutOfBoundsException e) { }

            if (d > 0) {
                y += inc;
                d += (2 * (dy - dx));
            } else {
                d += (2 * dy);
            }
        }
    }


    // m >=1 || m <= -1
    private void bresenhamSlopeGTE45(int x0, int y0, int x1, int y1, ColorAbstract c0, ColorAbstract c1, int[][][] framebuffer) {
        int dx = x1 - x0;
        int dy = y1 - y0;
        int x = x0;
        
        int inc = 1;
        if (dx < 0){
            inc = -1;
            dx = -dx;
        }
        int d = (2 * dx) + dy;

        for (int y = y0; y <= y1; y++){
            try {    
                double multiplier = (double)(y - y0) / (double)(y1 - y0);

                writePixel(x, y, framebuffer, linearlyInterpolate(c0, c1, multiplier));
            } catch (ArrayIndexOutOfBoundsException e) { }

            if (d > 0) {
                x += inc;
                d += (2 * (dx - dy));
            } else {
                d += (2 * dx);
            }
        }
    }
    private void writePixel(int x, int y, int[][][] framebuffer, ColorAbstract color){
        int[] rgb = color.scale(255);

        framebuffer[0][y][x] = rgb[0];
        framebuffer[1][y][x] = rgb[1];
        framebuffer[2][y][x] = rgb[2];
    }

    private ColorAbstract linearlyInterpolate(ColorAbstract c0, ColorAbstract c1, double multiplier){
        double r = c0.getR();
        double g = c0.getG();
        double b = c0.getB();
        
        if (r > c1.getR()){
            r -= (multiplier * (r - c1.getR()));
        } else if (c1.getR() > r){
            r += (multiplier * (c1.getR() - r));
        }

        if (g > c1.getG()){
            g -= (multiplier * (g - c1.getG()));
        } else if (c1.getG() > g){
            g += (multiplier * (c1.getG() - g));
        }
        
        if (b > c1.getB()){
            b -= (multiplier * (b - c1.getB()));
        } else if (c1.getB() > b){
            b += (multiplier * (c1.getB() - b));
        }

        r = (r > 1.0) ? 1.0 : (r < 0.0) ? 0.0 : r;
        g = (g > 1.0) ? 1.0 : (g < 0.0) ? 0.0 : g;
        b = (b > 1.0) ? 1.0 : (b < 0.0) ? 0.0 : b;

        return new Color(r, g, b);
    }*/
    
}
