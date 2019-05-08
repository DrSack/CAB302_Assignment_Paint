package Coordinate;

/**
 * This it the XY_Coordinates encapsulation class that gets and sets each X and Y coordinate that
 * is set from the DrawCanvas class.
 */

public class XY_Coordinates {

    private int x1, y1, x2, y2;// Declare X,Y variables

    public void setX1(int real_x1) { this.x1 = real_x1; }

    public void setY1(int real_y1) { this.y1 = real_y1; }

    public void setX2(int real_x2) { this.x2 = real_x2; }

    public void setY2(int real_y2) { this.y2 = real_y2; }

    public int getX1() { return x1; }

    public int getY1() { return y1; }

    public int getX2() { return x2; }

    public int getY2() { return y2; }
}
