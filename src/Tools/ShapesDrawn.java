package Tools;

import java.awt.*;

/**
 This is the abstract class that's extended by LineOrPlot, Rectangle and Ellipse classes
 */
public abstract class ShapesDrawn { // Initialize variables
    private int x1, y1, x2, y2;
    private double ox1,oy1,ox2,oy2;
    private int x[], y[];
    private double Ax[], Ay[];

    private Color pen;
    private Color filling;

    private boolean Fill;
    private boolean Polygon;

    /**
     * Set all variables of the particular shape
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

    /**
     * Set all variables of the particular shape
     * @param x Set x coordinate into array
     * @param y Set y coordinate into array
     */
    // Specifically for Polygons as it requires arrays to draw
    ShapesDrawn(double x[], double y[],int width, int height, boolean Fill, Color pen, Color filling) {
        this.filling = filling;
        this.pen = pen;
        this.Fill = Fill;

        this.x = new int[x.length];
        this.y = new int[y.length];
        this.Ay = new double[y.length];
        this.Ax = new double[x.length];


        for(int i = 0; i< x.length; i++){
            Ax[i] = x[i];
            Ay[i] = y[i];
            this.x[i] = (int)(x[i]*width);
            this.y[i] = (int)(y[i]*height);
        }
        Polygon = true;
    }

    public abstract void draw(Graphics g);

    // Every  time the window is resized scale the shape to the JPanels new dimensions
    public void resize(int width, int height) {

        if (Polygon) {
            for (int i = 0; i < x.length; i++) {
                this.x[i] = (int) (Ax[i] * width);
                this.y[i] = (int) (Ay[i] * height);
            }
        }

        else {
            this.x1 = (int) (ox1 * width);
            this.y1 = (int) (oy1 * height);
            this.x2 = (int) (ox2 * width);
            this.y2 = (int) (oy2 * height);
        }
    }

    public int getX1() {// return x1
        return x1;
    }

    public int getY1() {// return y1
        return y1;
    }

    public int getX2() {// return x2
        return x2;
    }

    public int getY2() {// return y2
        return y2;
    }

    public int[] getXarray(){// return all X coordinates in the array
        return x;
    }

    public int[] getYarray(){// return all Y coordinates in the array
        return y;
    }

    //Get penColour
    public Color getPenC(){
        return pen;
    }

    //Get fillColour
    public Color getFillC(){
        return filling;
    }

    //Return if the shape is filled or not
    public boolean getFill(){
        return Fill;
    }

    public Color SetColor(){
        return pen;
    }
}
