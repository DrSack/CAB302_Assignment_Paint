package Coordinate;

/**
 * This it the MouseCoordinates encapsulation class that sets MouseXY coordinates from integers to double based on the
 * screen JPanel height of the program.
 */

public class MouseCoordinates {

    private double mousex, mousey;

    // Set XY value for the mouse coordinates and also convert to double
    public void setMouseXY(int x, int y, int width, int height) {
        mousex = (double) x/width;
        mousey = (double) y/height;
    }

    public void setMousex(double mousex) {
        this.mousex = mousex;
    }

    public void setMousey(double mousey) {
        this.mousey = mousey;
    }

    public double getX() { return  mousex; }

    public double getY(){
        return  mousey;
    }
}
