package Coordinate;

/**
 * This extends the MouseCoordiantes polymorphism class where this sets the XY2 mouse value to double values
 */
public class XY2 extends MouseCoordinates {

    /**
     * Declaring double mouseX and mouseY
     */
    private double mouseX, mouseY;

    /**
     * Sets the mouse x2 and y2 based on the size of the canvas
     */
    public void setMouseXY(int x, int y, int width, int height) {
        mouseX = (double) x/width;
        mouseY = (double) y/height;
    }

    /**
     * Set mouse coordinate x2
     */
    public void setMousex(double mouseX) {
        this.mouseX = mouseX;
    }
    /**
     * Set mouse coordinate y2
     */
    public void setMousey(double mouseY) { this.mouseY = mouseY; }
    /**
     * Returns x2 coordinate
     */
    public double getX(){
        return  mouseX;
    }
    /**
     * Returns y2 coordinate
     */
    public double getY(){
        return  mouseY;
    }
}
