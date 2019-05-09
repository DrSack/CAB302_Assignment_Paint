package Coordinate;

public class XY1 extends MouseCoordinates{

    private double mousex, mousey;

    public void setMouseXY(int x, int y, int width, int height) {
        mousex = (double) x/width;
        mousey = (double) y/height;
    }

    public double getX(){ return  mousex; }

    public double getY(){
        return  mousey;
    }
}
