package Tools;

import java.awt.*;

/**
 This is the abstract class that's extended by LineOrPlot, Rectangle and Ellipse classes
 *
 */
public abstract class ShapesDrawn {//Initialize variables
    private int x1, y1, x2, y2;
    private double ox1,oy1,ox2,oy2;

    private Color pen;
    private Color filling;

    private boolean Fill;


    /**Set all variables of the particular shape
     *
     * @param x1 Set x1 coordinate
     * @param y1 Set y1 coordinate
     * @param x2 Set x2 coordinate
     * @param y2 Set y2 coordinate
     * @param width Scale shape with the current width of the JPanel
     * @param height Scale shape with the current height of the JPanel
     * @param Fill Set if a shape should be filled
     * @param pen Set the pen colour
     * @param filling Set fill colour
     */
    ShapesDrawn(double x1, double y1, double x2, double y2, int width, int height, boolean Fill, Color pen, Color filling) {
        this.filling = filling;
        this.pen = pen;
        this.Fill = Fill;

        ox1 = x1;
        oy1 = y1;
        ox2 = x2;
        oy2 = y2;

        this.x1 = (int)(x1*width);
        this.y1 = (int)(y1*height);
        this.x2 = (int)(x2*width);
        this.y2 = (int)(y2*height);
    }

    public abstract void draw(Graphics g);

    int getX1() {// return x1
        return x1;
    }

    int getY1() {// return y1
        return y1;
    }

    int getX2() {// return x2
        return x2;
    }

    int getY2() {// return y2
        return y2;
    }

    //Everytime the window is resized scale the shape to the JPanels new dimensions
    public void resize(int width, int height) {
    this.x1 = (int)(ox1*width);
    this.y1 = (int)(oy1*height);
    this.x2 = (int)(ox2*width);
    this.y2 = (int)(oy2*height);
    }

    //Get penColour
    Color getPenC(){
        return pen;
    }
    //Get fillColour
    Color getFillC(){
        return filling;
    }

    //Return if the shape is filled or not
    boolean getFill(){
        return Fill;
    }
}
