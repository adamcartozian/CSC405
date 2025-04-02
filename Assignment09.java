import java.io.IOException;
import Common.ReadWriteImage;
import Assignment06.VectorAbstract;
import Assignment06.Vector;
import Assignment06.Color;
import Assignment08.TriangleAbstract;
import Assignment08.Triangle;
import Assignment08.ShaderAbstract;

public class Assignment09 {
    public static void main(String[] args) {
        int[][][] framebuffer = new int[3][512][512];
        
        int totalFrames = 50;  
        int frameCount = 1;
        double theta = 0;
        double multiplier = 0.9*(10.0/totalFrames);
        double midpoint;
        double framecount2 = 1;
       
        VectorAbstract viewpoint = new Vector(0, 0, -1, new Color(0, 0, 0));

        for (int frame = 0; frame < totalFrames; frame++) {
            VectorAbstract rotationaxis = new Vector(0, 0, 1, null);
            VectorAbstract scale = new Vector(frameCount*multiplier, frameCount*multiplier, frameCount*multiplier, null);
            
            // Clear the framebuffer for each new frame (or reset values)
            framebuffer = new int[3][501][501];
            for (int x = 0; x < 501; x++) {
                    for (int y = 0; y < 501; y++) {
                        framebuffer[0][x][y] = 255; // White background
                        framebuffer[1][x][y]= 255;
                        framebuffer[2][x][y] = 255;
                    }
                }
            
            //Triangle 1

            VectorAbstract v0 = new Vector(0 + (frame * (500/totalFrames)), 0 + (frame * (500/totalFrames)), 0, new Color(1, 0 + (frame *(1.0/totalFrames)), 0));
            VectorAbstract v1 = new Vector(50 + (frame * (500/totalFrames)), 0 + (frame * (500/totalFrames)), 0, new Color(1, 0, 0));
            VectorAbstract v2 = new Vector(50 + (frame * (500/totalFrames)), 50 + (frame * (500/totalFrames)), 0, new Color(1, 0,0 + (frame *(1.0/totalFrames))));
            
            TriangleAbstract t1 = new Triangle(v0, v1, v2);
            t1 = t1.rotateAxis(rotationaxis, t1.getCenter(), Math.toRadians(theta), t1);
            if(frame<(totalFrames/2)){
                t1 = t1.scale(scale, t1.getCenter(), t1);
                midpoint = frameCount;
                framecount2 = midpoint;
            }
            if(frame >= (totalFrames/2)){
                scale = new Vector((framecount2*multiplier),  (framecount2*multiplier), (framecount2*multiplier), null);
                t1 = t1.scale(scale, t1.getCenter(), t1);
            }
            t1.render(framebuffer, false, ShaderAbstract.FILLSTYLE.SHADE, viewpoint);

            System.out.println(t1.isVisible(viewpoint));

            //Triangle 2

            VectorAbstract v3 = new Vector(0 + (frame * (500/totalFrames)), 450 - (frame * (500/totalFrames)), 0, new Color(0, 0 + (frame *(1.0/totalFrames)), 1));
            VectorAbstract v4 = new Vector(50 + (frame * (500/totalFrames)), 450 - (frame * (500/totalFrames)), 0, new Color(0, 1, 0));
            VectorAbstract v5 = new Vector(50 + (frame * (500/totalFrames)), 500 - (frame * (500/totalFrames)), 0, new Color(0+ (frame *(1.0/totalFrames)), 0,1));
            
            TriangleAbstract t2 = new Triangle(v3, v4, v5);
            
            if(frame<(totalFrames/2)){
                t2 = t2.scale(scale, t2.getCenter(), t2);
            }
            if(frame >= (totalFrames/2) && frame<(totalFrames-1)){
                scale = new Vector((framecount2*multiplier),  (framecount2*multiplier), (framecount2*multiplier), null);
                t2 = t2.scale(scale, t2.getCenter(), t2);
            }
            t1.render(framebuffer, false, ShaderAbstract.FILLSTYLE.SHADE, viewpoint);
            t2 = t2.rotateAxis(rotationaxis, t2.getCenter(), Math.toRadians(theta), t2);
            t2.render(framebuffer, false, ShaderAbstract.FILLSTYLE.SHADE, viewpoint);

            System.out.println(t2.isVisible(viewpoint));

            //Triangle 3

            VectorAbstract v6 = new Vector(450 - (frame * (500/totalFrames)), 0 + (frame * (500/totalFrames)), 0, new Color(0 + (frame *(1.0/totalFrames)), 1, 0));
            VectorAbstract v7 = new Vector(500 - (frame * (500/totalFrames)), 0 + (frame * (500/totalFrames)), 0, new Color(1, 0, 0));
            VectorAbstract v8 = new Vector(450 - (frame * (500/totalFrames)), 50 + (frame * (500/totalFrames)), 0, new Color(0, 1,0 + (frame *(1.0/totalFrames))));
            
            TriangleAbstract t3 = new Triangle(v6, v7, v8);
            t3 = t3.rotateAxis(rotationaxis, t3.getCenter(), Math.toRadians(theta), t3);
            if(frame<(totalFrames/2)){
                t3 = t3.scale(scale, t3.getCenter(), t3);
            }
            if(frame >= (totalFrames/2) && frame<(totalFrames-1)){
                scale = new Vector((framecount2*multiplier),  (framecount2*multiplier), (framecount2*multiplier), null);
                t3 = t3.scale(scale, t3.getCenter(), t3);
            }
            t3.render(framebuffer, false, ShaderAbstract.FILLSTYLE.SHADE, viewpoint);

            System.out.println(t3.isVisible(viewpoint));

            //Triangle 4

            VectorAbstract v9 = new Vector(450 - (frame * (500/totalFrames)), 450 - (frame * (500/totalFrames)), 0, new Color(1, 0 + (frame *(1.0/totalFrames)), 0));
            VectorAbstract v10 = new Vector(500 - (frame * (500/totalFrames)), 450 - (frame * (500/totalFrames)), 0, new Color(1, 1, 0));
            VectorAbstract v11 = new Vector(450 - (frame * (500/totalFrames)), 500 - (frame * (500/totalFrames)), 0, new Color(0+ (frame *(1.0/totalFrames)), 1,1));
            
            TriangleAbstract t4 = new Triangle(v9, v10, v11);
            
            if(frame<(totalFrames/2)){
                t4 = t4.scale(scale, t4.getCenter(), t4);
            }
            if(frame >= (totalFrames/2) && frame<(totalFrames-1)){
                scale = new Vector((framecount2*multiplier),  (framecount2*multiplier), (framecount2*multiplier), null);
                t4 = t4.scale(scale, t4.getCenter(), t4);
                framecount2= framecount2 - 1.2 ;
            }
            t4 = t4.rotateAxis(rotationaxis, t4.getCenter(), Math.toRadians(theta), t4);
            t4.render(framebuffer, false, ShaderAbstract.FILLSTYLE.SHADE, viewpoint);

            System.out.println(t4.isVisible(viewpoint));
            
            theta = theta + 40;
            // Save the current frame to a file
            try {
                String filename = String.format("frame_%03d.png", frameCount++);
                ReadWriteImage.writeImage(framebuffer, filename);
                System.out.println("Saving frame: " + filename);
            } catch (IOException e) {
                System.out.println(e);
            }
        }
}
}