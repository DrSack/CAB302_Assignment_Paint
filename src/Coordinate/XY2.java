package Coordinate;

public class XY2 extends MouseCoordinates {

    private double mouseX, mouseY;

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

    public double getX(){
        return  mouseX;
    }

    public double getY(){
        return  mouseY;
    }
}
