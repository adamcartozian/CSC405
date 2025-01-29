public class Lines extends LineAbstract {
    
    @Override
    public void twoPointForm(int x0, int y0, int x1, int y1, int[][][] framebuffer)
            throws NullPointerException, ArrayIndexOutOfBoundsException {
        // TODO Auto-generated method stub
        // make sure to cast to double for division
        // for slopes greater than 1, we swap the roles of x and y
        // because m is a floating-point number
        // and we must round it to find the appropriate pixel
        throw new UnsupportedOperationException("Unimplemented method 'twoPointForm'");
    }

    @Override
    public void parametricForm(int x0, int y0, int x1, int y1, int[][][] framebuffer)
            throws NullPointerException, ArrayIndexOutOfBoundsException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'parametricForm'");
    }
    
}
