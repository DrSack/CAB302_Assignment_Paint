package Coordinate;

/**
 *
 * This it the MouseCoordinates encapsulation class that sets MouseXY coordinates from integers to double based on the
 * screen JPanel height of the program.
 */

public class MouseCoordinates {

    // Declare private double variables.
    private double mousex1, mousey1, mousex2, mousey2;

    // Set XY value for the mouse coordinates and also convert to double
    public void setMouseXY(int x, int y, int width, int height) {
        mousex1 = (double) x/width;
        mousey1 = (double) y/height;
    }

    public void setMouseXY2(int x, int y, int width, int height) {
        mousex2 = (double) x/width;
        mousey2 = (double) y/height;
    }

    public double getX1(){
        return mousex1;
    }

    public double getY1(){
        return mousey1;
    }

    public double getX2(){
        return  mousex2;
    }

    public double getY2(){
        return  mousey2;
    }
}
