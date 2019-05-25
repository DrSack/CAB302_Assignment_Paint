package Tools;

import GUI.DrawCanvas;

import java.awt.*;

/**
 * This class extends the ShapesDrawn class to draw and fill polygons.
 */
public class Polygon extends ShapesDrawn {

    /**
     * Pass all parameters into the super method to instantiate the LineOrPlot class.
     *
     * @param x the array of x double coordinates.
     * @param y the array of y double coordinates.
     * @param width the width of the JPanel.
     * @param height the height of the JPanel.
     * @param Fill if fill is true of not from the DrawCanvas Class.
     * @param c the pen colour.
     * @param f the fill colour.
     */
    public Polygon(double[] x, double[] y, int width, int height, boolean Fill , Color c, Color f) {
        super(x,y,width,height,Fill,c,f); // Pass the values through the super class
    }

    /**
     * Draw or fill the polygon.
     *
     * @param g the graphics parameter which is called from the paint method.
     */
    @Override
    public void draw(Graphics g) { // Draws Polygon
        if (getFill()) { // If this particular shape has fill enabled fill the Polygon
            g.setColor(getFillC());
            g.fillPolygon(getXarray(), getYarray(), getXarray().length);
        }

        g.setColor(getPenC()); // Draw the outline
        g.drawPolygon(getXarray(), getYarray(), getXarray().length);
    }
}
