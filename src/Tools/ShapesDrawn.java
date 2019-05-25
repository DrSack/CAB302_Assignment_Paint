package Tools;

import java.awt.*;

/**
 * This is the abstract class that's extended by LineOrPlot, Rectangle and Ellipse classes
 */
public abstract class ShapesDrawn { // Initialize variables

    /**
     * Declaring integers x1, y1, x2 and y2
     */
    private int x1, y1, x2, y2;

    /**
     * Declaring doubles ox1, oy1, ox2 and oy2
     */
    private double ox1,oy1,ox2,oy2;

    /**
     * Declaring an integer array that will hold x coordinates for polygon
     */
    private int[] x;

    /**
     * Declaring an integer array that will hold y coordinates for polygon
     */
    private int[] y;

    /**
     * Declaring an integer array that will temporarily hold x coordinates for polygon
     */
    private double[] Ax;

    /**
     * Declaring an integer array that will temporarily hold y coordinates for polygon
     */
    private double[] Ay;

    /**
     * Used to change the color of pen
     */
    private Color pen;

    /**
     * Used to change the color of fill
     */
    private Color filling;

    /**
     * Used to decide whether or not to fill
     */
    private boolean Fill;

    /**
     * Used to decide whether or not to change polygon coordinates
     */
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
     * Set all variables of the particular shape, especially for Polygons as it requires arrays to draw.
     *
     * @param x Set x coordinate into array
     * @param y Set y coordinate into array
     */
    ShapesDrawn(double[] x, double[] y, int width, int height, boolean Fill, Color pen, Color filling) {
        this.filling = filling;
        this.pen = pen;
        this.Fill = Fill;

        this.x = new int[x.length];
        this.y = new int[y.length];
        this.Ay = new double[y.length];
        this.Ax = new double[x.length];

        for (int i = 0; i< x.length; i++){
            Ax[i] = x[i];
            Ay[i] = y[i];
            this.x[i] = (int)(x[i]*width);
            this.y[i] = (int)(y[i]*height);
        }
        Polygon = true;
    }

    /**
     * @param g the graphics parameter which is called from the paint method.
     */
    public abstract void draw(Graphics g);

    /**
     * Every  time the window is resized scale the shape to the JPanels new dimensions.
     *
     * @param width The width of the JPanel
     * @param height The height of the JPanel
     */
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

    /**
     * Get the x1 coordinate.
     *
     * @return the x1 coordinate
     */
    public int getX1() {// return x1
        return x1;
    }

    /**
     * Get the y1 coordinate.
     *
     * @return the y1 coordinate
     */
    public int getY1() {// return y1
        return y1;
    }

    /**
     * Get the x2 coordinate.
     *
     * @return the x2 coordinate.
     */
    public int getX2() {// return x2
        return x2;
    }

    /**
     * Get the y2 coordinate.
     *
     * @return the y2 coordinate.
     */
    public int getY2() { return y2; }

    /**
     * Get the Polygon x array coordinates.
     *
     * @return the Polygon x array coordinates.
     */
    public int[] getXarray(){// return all X coordinates in the array
        return x;
    }

    /**
     * Get the Polygon y array coordinates.
     *
     * @return the Polygon y array coordinates.
     */
    public int[] getYarray(){// return all Y coordinates in the array
        return y;
    }

    /**
     * Get the pen colour.
     *
     * @return Get pen colour.
     */
    public Color getPenC(){
        return pen;
    }

    /**
     * Get the fill colour.
     *
     * @return Get fill colour.
     */

    public Color getFillC(){
        return filling;
    }

    /**
     * Check if the shape is filled or not.
     *
     * @return Return if the shape is filled or not.
     */
    public boolean getFill(){
        return Fill;
    }
}
