package Coordinate;

public class MouseCoordinates {

    private double mousex1, mousey1, mousex2, mousey2;


    public void setMouseXY(int x, int y, int width, int height){// Set XY value for the mouse coordiantes and also convert to double
        mousex1 = (double) x/width;
        mousey1 = (double) y/height;

    }

    public void setMouseXY2(int x, int y, int width, int height){
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
