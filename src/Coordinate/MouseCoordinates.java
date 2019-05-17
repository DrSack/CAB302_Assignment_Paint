package Coordinate;

/**
 * This it the MouseCoordinates encapsulation class that sets MouseXY coordinates from integers to double based on the
 * screen JPanel height of the program.
 */

public class MouseCoordinates {

    private double mouseX, mouseY;

    // Set XY value for the mouse coordinates and also convert to double
    public void setMouseXY(int x, int y, int width, int height) {
        mouseX = (double) x/width;
        mouseY = (double) y/height;
    }

    public void setMousex(double mouseX) {
        this.mouseX = mouseX;
    }

    public void setMousey(double mouseY) {
        this.mouseY = mouseY;
    }

    public double getX() { return  mouseX; }

    public double getY(){
        return  mouseY;
    }
}
