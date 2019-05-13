package Coordinate;

public class XY2 extends MouseCoordinates{

    private double mousex, mousey;

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

    public double getX(){
        return  mousex;
    }

    public double getY(){
        return  mousey;
    }
}
