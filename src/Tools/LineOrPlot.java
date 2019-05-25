package Tools;

import java.awt.*;

/**
 * This class extends the ShapesDrawn class to draw plots.
 */
public class LineOrPlot extends ShapesDrawn {

    /** Pass all parameters into the super method to instantiate the LineOrPlot class.
     *
     * @param x1 the x1 double coordinate.
     * @param y1 the y1 double coordinate.
     * @param x2 the x2 double coordinate.
     * @param y2 the y2 double coordinate.
     * @param width the width of the JPanel.
     * @param height the height of the JPanel.
     * @param Fill if fill is true of not from the DrawCanvas Class.
     * @param c the pen colour.
     * @param f the fill colour.
     */
    public LineOrPlot(double x1 , double y1, double x2, double y2, int width, int height,boolean Fill, Color c, Color f) {
        super(x1,y1,x2,y2,width,height,Fill,c,f); // Pass the values through the super class.
    }

    /**
     * Draw line or plot.
     *
     * @param g the graphics parameter which is called from the paint method.
     */
    @Override
    public void draw(Graphics g) { // Draw the line or plot
        g.setColor(getPenC());
        g.drawLine(getX1(),getY1(),getX2(),getY2());
    }
}
