package Tools;

import java.awt.*;

/**
 This is the abstract class that's extended by Plot, Line, Rectangle and Ellipse classes
 *
 */
public abstract class ShapesDrawn {

    private int x1;
    private int y1;
    private int x2;
    private int y2;

    private double ox1,oy1,ox2,oy2;

    private Color pen = Color.BLACK;
    private Color filling = Color.white;

    private boolean Fill = false;

    private int height = 0;
    private int width = 0;

    public ShapesDrawn(double x1, double y1, double x2, double y2, int width, int height) {
        this.width = width;
        this.height = height;

        this.x1 = (int)(x1*this.width);
        this.y1 = (int)(y1*this.height);
        this.x2 = (int)(x2*this.width);
        this.y2 = (int)(y2*this.height);
    }

    public ShapesDrawn(double x1, double y1, double x2, double y2, int width, int height, Color pen) {
        this.width = width;
        this.height = height;
        this.pen = pen;

        ox1 = x1;
        oy1 = y1;
        ox2 = x2;
        oy2 = y2;

        this.x1 = (int)(x1*this.width);
        this.y1 = (int)(y1*this.height);
        this.x2 = (int)(x2*this.width);
        this.y2 = (int)(y2*this.height);
    }

    public ShapesDrawn(double x1, double y1, double x2, double y2, int width, int height, boolean Fill, Color pen, Color filling) {
        this.width = width;
        this.height = height;

        this.filling = filling;
        this.pen = pen;
        this.Fill = Fill;

        ox1 = x1;
        oy1 = y1;
        ox2 = x2;
        oy2 = y2;

        this.x1 = (int)(x1*this.width);
        this.y1 = (int)(y1*this.height);
        this.x2 = (int)(x2*this.width);
        this.y2 = (int)(y2*this.height);
    }

    public abstract void draw(Graphics g);

    public int getX1() {
        return x1;
    }

    public int getY1() {
        return y1;
    }

    public int getX2() {
        return x2;
    }

    public int getY2() {
        return y2;
    }

    public void resize(int width, int height) {
    this.x1 = (int)(ox1*width);
    this.y1 = (int)(oy1*height);
    this.x2 = (int)(ox2*width);
    this.y2 = (int)(oy2*height);
    }


    public Color getPenC(){
        return pen;
    }

    public Color getFillC(){
        return filling;
    }

    public boolean getFill(){
        return Fill;
    }
}
