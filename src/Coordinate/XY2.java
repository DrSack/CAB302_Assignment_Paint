package Coordinate;

/**
 * This extends the MouseCoordinates polymorphism class where this sets the XY2 mouse value to double values.
 */
public class XY2 extends MouseCoordinates {

    /**
     * Declaring double mouseX and mouseY.
     */
    private double mouseX, mouseY;

    /**
     * Sets the mouse x2 and y2 based on the size of the canvas.
     */
    public void setMouseXY(int x, int y, int width, int height) {
        mouseX = (double) x/width;
        mouseY = (double) y/height;
    }

    /**
     * Set mouse x2.
     *
     * @param mouseX pass through double values.
     */
    public void setMousex(double mouseX) {
        this.mouseX = mouseX;
    }

    /**
     * Set mouse y2.
     *
     * @param mouseY pass through double values.
     */
    public void setMousey(double mouseY) {
        this.mouseY = mouseY;
    }

    /**
     * Get the x2 coordinate.
     *
     * @return mouseX coordinate.
     */
    public double getX() { return  mouseX; }

    /**
     * Get the y2 coordinate.
     *
     * @return mouseY coordinate.
     */
    public double getY(){
        return  mouseY;
    }
}
