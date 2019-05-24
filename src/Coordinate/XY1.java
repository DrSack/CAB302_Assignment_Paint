package Coordinate;

/**
 * This extends the MouseCoordinates polymorphism class where this sets the XY1 mouse value to double values
 */
public class XY1 extends MouseCoordinates {

    /**
     * Declaring a double mouseX and mouseY
     */
    private double mouseX, mouseY;

    /**
     * Sets the mouse x1 and y1 based on the size of the canvas
     */
    public void setMouseXY(int x, int y, int width, int height) {
        mouseX = (double) x/width;
        mouseY = (double) y/height;
    }
    /**
     * Set mouse x1
     */
    public void setMousex(double mouseX) {
        this.mouseX = mouseX;
    }

    /**
     * Set mouse y1
     */
    public void setMousey(double mouseY) {
        this.mouseY = mouseY;
    }

    /**
     * Returns x1
     */
    public double getX() { return  mouseX; }

    /**
     * Returns y1
     */
    public double getY(){
        return  mouseY;
    }
}
