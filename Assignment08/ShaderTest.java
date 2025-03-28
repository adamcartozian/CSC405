package Assignment08;

import java.io.IOException;

import Common.ReadWriteImage;
import Assignment06.VectorAbstract;
import Assignment06.Vector;
import Assignment06.Color;

public class ShaderTest {

	/*public static void main (String[] args) {
		int[][][] framebuffer = new int[3][512][512];
		// -- set the view point
		VectorAbstract viewpoint = new Vector(0, 0, -1, new Color(0, 0, 0));
		
		VectorAbstract v0 = new Vector(100, 100, 0, new Color(0, 0, 1));
		VectorAbstract v1 = new Vector(200, 100, 0, new Color(1, 0, 0));
		VectorAbstract v2 = new Vector(150, 200, 0, new Color(0, 1, 0));
		TriangleAbstract t = new Triangle(v0, v1, v2);
		t.render(framebuffer, true, ShaderAbstract.FILLSTYLE.SHADE, viewpoint);
		
		v0 = new Vector(250, 100, 0, new Color(0, 0, 1));
		v1 = new Vector(350, 100, 0, new Color(1, 0, 0));
		v2 = new Vector(300, 200, 0, new Color(0, 1, 0));
		t = new Triangle(v0, v1, v2);

		t.render(framebuffer, true, ShaderAbstract.FILLSTYLE.FILL, viewpoint);

		v0 = new Vector(200, 300, 0, new Color(0, 0, 1));
		v1 = new Vector(300, 300, 0, new Color(1, 0, 0));
		v2 = new Vector(250, 400, 0, new Color(0, 1, 0));
		t = new Triangle(v0, v1, v2);
		t.render(framebuffer, true, ShaderAbstract.FILLSTYLE.NONE, viewpoint);
		
		// -- this triangle should not be visible from the specified viewpoint
		v2 = new Vector(210, 310, 0, new Color(0, 0, 1));
		v1 = new Vector(310, 310, 0, new Color(1, 0, 0));
		v0 = new Vector(260, 410, 0, new Color(0, 1, 0));
		t = new Triangle(v0, v1, v2);
		t.render(framebuffer, true, ShaderAbstract.FILLSTYLE.NONE, viewpoint);

		try {
			ReadWriteImage.writeImage(framebuffer, "trianglefill.PNG");
		} catch (IOException e) {
			System.out.println(e);
		}
	}*/

	public static void main(String[] args) {
        int[][][] framebuffer = new int[3][512][512];
        
        // Create an array to hold frames
        int totalFrames = 10;  // You can adjust this to control the number of frames in the video
        int frameCount = 1;

        // Loop to generate frames
        for (int frame = 0; frame < totalFrames; frame++) {
            // -- set the view point (moving along the Z-axis to simulate camera movement)
            VectorAbstract viewpoint = new Vector(0, 0, -1 + frame * 0.02, new Color(0, 0, 0));
            
            // Clear the framebuffer for each new frame (or reset values)
            framebuffer = new int[3][512][512];
            
            // Render first triangle
            VectorAbstract v0 = new Vector(100, 100, 0, new Color(0, 0, 1));
            VectorAbstract v1 = new Vector(200, 100, 0, new Color(1, 0, 0));
            VectorAbstract v2 = new Vector(150, 200, 0, new Color(0, 1, 0));
            TriangleAbstract t = new Triangle(v0, v1, v2);
            t.render(framebuffer, true, ShaderAbstract.FILLSTYLE.SHADE, viewpoint);
            
            // Render second triangle
            v0 = new Vector(250, 100, 0, new Color(0, 0, 1));
            v1 = new Vector(350, 100, 0, new Color(1, 0, 0));
            v2 = new Vector(300, 200, 0, new Color(0, 1, 0));
            t = new Triangle(v0, v1, v2);
            t.render(framebuffer, true, ShaderAbstract.FILLSTYLE.FILL, viewpoint);

            // Render third triangle
            v0 = new Vector(200, 300, 0, new Color(0, 0, 1));
            v1 = new Vector(300, 300, 0, new Color(1, 0, 0));
            v2 = new Vector(250, 400, 0, new Color(0, 1, 0));
            t = new Triangle(v0, v1, v2);
            t.render(framebuffer, true, ShaderAbstract.FILLSTYLE.NONE, viewpoint);
            
            // Render fourth triangle (should not be visible)
            v2 = new Vector(210, 310, 0, new Color(0, 0, 1));
            v1 = new Vector(310, 310, 0, new Color(1, 0, 0));
            v0 = new Vector(260, 410, 0, new Color(0, 1, 0));
            t = new Triangle(v0, v1, v2);
            t.render(framebuffer, true, ShaderAbstract.FILLSTYLE.NONE, viewpoint);

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
