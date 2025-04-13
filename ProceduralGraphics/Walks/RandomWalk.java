package ProceduralGraphics.Walks;

import Assignment06.VectorAbstract;
import Common.ReadWriteImage;
import Assignment06.Vector;
import Assignment06.ColorAbstract;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import Assignment06.ScanConvertAbstract;
import Assignment06.ScanConvertLine;
import Assignment06.Color;
import Assignment06.ColorAbstract;

public class RandomWalk {
    public static void Walk(int steps, ArrayList<VectorAbstract> path){

        int n =steps;
        Random rn = new Random();
        int currentx = 0;
        int currenty = 0;
        int currentz = 0;
        
        for (int i=0; i < n; ++i){
            int xdir = rn.nextInt(3) - 1;
            int ydir = rn.nextInt(3) - 1;
            int zdir = rn.nextInt(3) - 1;
            currentx += xdir;
            currenty += ydir;
            currentz += zdir;
            VectorAbstract va = new Vector(currentx, currenty, currentz, null);
            path.add(va);
        }
    }

    public void render(ArrayList<VectorAbstract> path, int[][][] framebuffer){
        for (VectorAbstract va : path){
            try{
                int centerx = framebuffer[0][0].length/2;
                int centery = framebuffer[0].length/2;
                framebuffer[0][(int)va.getY() + centery][(int)va.getX() + centerx] = 255;
                framebuffer[1][(int)va.getY() + centery][(int)va.getX() + centerx] = 255;
                framebuffer[2][(int)va.getY() + centery][(int)va.getX() + centerx] = 255;
            }
            catch(ArrayIndexOutOfBoundsException e){

            }
        }
    }
    public static void main(String[] args) {
        ColorAbstract color = new Color(0, 1, 1);
        RandomWalk rw = new RandomWalk();
        ScanConvertAbstract scl = new ScanConvertLine();
        int[][][] framebuffer = new int[3][512][512];
        ArrayList<VectorAbstract> path = new ArrayList<VectorAbstract>();
        int centerx = framebuffer[0][0].length/2;
        int centery = framebuffer[0].length/2;
        rw.Walk(10000, path);
        VectorAbstract start = path.get(0);
        for (int i = 1; i<path.size(); i++){
            VectorAbstract end = path.get(i);
            scl.bresenham((int)start.getX() * 5 + centerx, (int)start.getY() * 5 + centery, (int)end.getX()* 5 + centerx, (int)end.getY() * 5 + centery, color, color, framebuffer);
            start = end;
        }
        try{
            ReadWriteImage.writeImage(framebuffer, "randomwalk.PNG");
        }
        catch (IOException e) {
			System.out.println(e);
		}
    }
}
