package Coordinate;

/**
 * This it the MouseCoordinates Polymorphism class that sets MouseXY coordinates from integers to double based on the
 * screen JPanel height of the program.
 */
public class MouseCoordinates {

    /**
     * Declaring a double mouseX and mouseY.
     */
    private double mouseX, mouseY;

    /**
     * Set XY value for the mouse coordinates and also convert to double.
     *
     * @param x the current x coordinate of the MouseListener.
     * @param y the current y coordinate of the MouseListener.
     * @param width the JPanel width.
     * @param height the JPanel height.
     */
    public void setMouseXY(int x, int y, int width, int height) {
        mouseX = (double) x/width;
        mouseY = (double) y/height;
    }

    /**
     * Manually set the mouseX coordinate.
     *
     * @param mouseX pass through the double values.
     */
    public void setMousex(double mouseX) { }


    /**
     * Manually set the mouseY coordinate.
     *
     * @param mouseY pass through the double values.
     */
    public void setMousey(double mouseY) { }

    /**
     * Get mouseX coordinate.
     *
     * @return mouseX double coordinate.
     */
    public double getX() { return  mouseX; }

    /**
     * Get mouseY coordinate.
     *
     * @return mouseY double coordinate.
     */
    public double getY(){ return  mouseY; }
}
