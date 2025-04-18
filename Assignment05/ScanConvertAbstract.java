package Assignment05;

public abstract class ScanConvertAbstract {
	
	/**
	 * Scan convert a line segment using Bresenham's algorithm 
	 * with start to end color interpolation
	 * @param x0 starting x coordinate
	 * @param y0 starting y coordinate
	 * @param x1 ending x coordinate
	 * @param y1 ending y coordinate
	 * @param color starting color
	 * @param color2 ending color
	 * @param framebuffer frame buffer to receive the scan conversion
	 * @throws NullPointerException thrown if the frame buffer is null
	 * @throws ArrayIndexOutOfBoundsException thrown if a coordinate goes out of range of the frame buffer
	 */	public abstract void bresenham(int x0, int y0, int x1, int y1, ColorAbstract color, ColorAbstract color2, int framebuffer[][][])  throws NullPointerException, ArrayIndexOutOfBoundsException;

}
