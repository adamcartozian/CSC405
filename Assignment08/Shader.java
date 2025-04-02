package Assignment08;

import Assignment06.Color;
import Assignment06.ScanConvertLine;
import Assignment06.VectorAbstract;


public class Shader extends ShaderAbstract{

   

    @Override
    public void solidFill(TriangleAbstract tri, int[][][] framebuffer) {
        ScanConvertLine sc = new ScanConvertLine();
        VectorAbstract[] vertices = tri.getVertices();
        Color ColorFill = vertices[0].getColor();
        int[][][] sb = ShadeBuffer(framebuffer.length, framebuffer[0].length, framebuffer[0][0].length);

        for (int i = 0; i < vertices.length; i++) {
            try {
                int j = i + 1;

                sc.bresenham((int)vertices[i].getX(), (int)vertices[i].getY(), 
                            (int)vertices[j].getX(), (int)vertices[j].getY(),
                            ColorFill, ColorFill, 
                            sb);        
            } catch (IndexOutOfBoundsException e) {
                sc.bresenham((int)vertices[i].getX(), (int)vertices[i].getY(), 
                            (int)vertices[0].getX(), (int)vertices[0].getY(),
                            ColorFill, ColorFill, 
                            sb);    
            }
        }

        for (int i = 0; i < sb[0].length; i++){
            int leftside = 0;
            int rightside = sb[0][0].length - 1;

            try {
                while (sb[0][i][leftside] == -1){
                    leftside++;
                }

                while (sb[0][i][rightside] == -1){
                    rightside--;
                }
            } catch (IndexOutOfBoundsException e){
                continue;
            }

            if (leftside > rightside) { continue; }

            sc.bresenham(leftside, i, rightside, i, ColorFill, ColorFill, sb);
        }

        CopyToFB(sb, framebuffer);
    }

    @Override
    public void shadeFill(TriangleAbstract tri, int[][][] framebuffer) {
        ScanConvertLine sc = new ScanConvertLine();
        VectorAbstract[] vertices = tri.getVertices();
        int[][][] sb = ShadeBuffer(framebuffer.length, framebuffer[0].length, framebuffer[0][0].length);

        for (int i = 0; i < vertices.length; i++) {
            try {
                int j = i + 1;

                sc.bresenham((int)vertices[i].getX(), (int)vertices[i].getY(), 
                            (int)vertices[j].getX(), (int)vertices[j].getY(),
                            vertices[i].getColor(), vertices[j].getColor(), 
                            sb);        
            } catch (IndexOutOfBoundsException e) {
                sc.bresenham((int)vertices[i].getX(), (int)vertices[i].getY(), 
                            (int)vertices[0].getX(), (int)vertices[0].getY(),
                            vertices[i].getColor(), vertices[0].getColor(), 
                            sb);    
            }
        }
        for (int i = 0; i < sb[0].length; i++){
            int leftside = 0;
            int rightside = sb[0][0].length - 1;

            try {
                while (sb[0][i][leftside] == -1){
                    leftside++;
                }

                while (sb[0][i][rightside] == -1){
                    rightside--;
                }
            } catch (IndexOutOfBoundsException e){
                continue;
            }

            if (leftside > rightside) { continue; }

            sc.bresenham(leftside, i, rightside, i, 
                        new Color((double)sb[0][i][leftside] / 255.0,(double)sb[1][i][leftside] / 255.0, (double)sb[2][i][leftside] / 255.0), 
                        new Color((double)sb[0][i][rightside] / 255.0, (double)sb[1][i][rightside] / 255.0, (double)sb[2][i][rightside] / 255.0), 
                        sb);
        }

        CopyToFB(sb, framebuffer);
    }

    private int[][][] ShadeBuffer(int layers, int rows, int cols){
        int[][][] shadeBuffer = new int[layers][rows][cols];

        for (int i = 0; i < layers; i++){
            for (int j = 0; j < rows; j++){
                for (int k = 0; k < cols; k++){
                    shadeBuffer[i][j][k] = -1;
                }
            }
        }

        return shadeBuffer;
    }

    private void CopyToFB(int[][][] sb, int[][][] fb){
        for (int i = 0; i < sb.length; i++){
            for (int j = 0; j < sb[0].length; j++){
                for (int k = 0; k < sb[0].length; k++){
                    if (sb[i][j][k] != -1) {
                        fb[i][j][k] = sb[i][j][k];
                    }
                }
            }
        }
    } 
    
}
