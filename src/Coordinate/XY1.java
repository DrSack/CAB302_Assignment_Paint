package Coordinate;

/**
 * This extends the MouseCoordinates polymorphism class where this sets the XY1 mouse value to double values.
 */
public class XY1 extends MouseCoordinates {

    /**
     * Declaring a double mouseX and mouseY.
     */
    private double mouseX, mouseY;

    /**
     * Sets the mouse x1 and y1 based on the size of the canvas.
     */
    public void setMouseXY(int x, int y, int width, int height) {
        mouseX = (double) x/width;
        mouseY = (double) y/height;
    }

    /**
     * Set mouse x1.
     *
     * @param mouseX pass through double values.
     */
    public void setMousex(double mouseX) {
        this.mouseX = mouseX;
    }

    /**
     * Set mouse y1.
     *
     * @param mouseY pass through double values.
     */
    public void setMousey(double mouseY) {
        this.mouseY = mouseY;
    }

    /**
     * Get x1.
     *
     * @return mouseX coordinate.
     */
    public double getX() { return  mouseX; }

    /**
     * Get y1.
     *
     * @return mouseY coordinate.
     */
    public double getY(){
        return  mouseY;
    }
}
