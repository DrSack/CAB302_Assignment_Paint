package Tools;

import java.awt.*;

/**
 * This class extends the ShapesDrawn class to draw and fill ellipses
 */
public class Ellipse extends ShapesDrawn {

    /** Pass all parameters into the super method to instantiate the Ellipse class
     *
     * @param x1 the x1 double coordinate
     * @param y1 the y1 double coordinate
     * @param x2 the x2 double coordinate
     * @param y2 the y2 double coordinate
     * @param width the width of the JPanel
     * @param height the height of the JPanel
     * @param Fill if fill is true of not from the DrawCanvas Class
     * @param c the pen colour
     * @param f the fill colour.
     */
    public Ellipse(double x1 , double y1, double x2, double y2, int width, int height, boolean Fill ,Color c, Color f) {
        super(x1,y1,x2,y2,width,height,Fill,c,f);//pass the values through the super class.
    }
    /**
     * Draw this particular shape
     * @param g the graphics parameter which is called from the paint method.
     */
    @Override
    public void draw(Graphics g) { // Draw the Ellipse
        if (getFill()) { // If this particular shape has fill enabled fill the oval
            g.setColor(getFillC());
            if (getX2() <= getX1() && getY2() <= getY1()) { // Both X2 and Y2 and behind X1 and Y1 then draw backwards.
                g.fillOval(getX2(),getY2(),getX1()-getX2(),getY1()-getY2());
            }
            else if (getX2() <= getX1()) { // If X2 goes behind X1 then draw backwards
                g.fillOval(getX2(),getY1(),getX1()-getX2(),getY2()-getY1());
            }
            else if (getY2() <= getY1()) { // If Y2 goes behind Y1 then draw backwards
                g.fillOval(getX1(),getY2(),getX2()-getX1(),getY1()-getY2());
            }
            else { // If operations are normal then draw normally
                g.fillOval(getX1(),getY1(),getX2()-getX1(),getY2()-getY1());
            }

        }

        g.setColor(getPenC()); // Draw the outline

        if (getX2() <= getX1() && getY2() <= getY1()) { // Both X2 and Y2 and behind X1 and Y1 then draw backwards.
            g.drawOval(getX2(),getY2(),getX1()-getX2(),getY1()-getY2());
        }
        else if (getX2() <= getX1()) { // If X2 goes behind X1 then draw backwards
            g.drawOval(getX2(),getY1(),getX1()-getX2(),getY2()-getY1());
        }
        else if (getY2() <= getY1()) { // If Y2 goes behind Y1 then draw backwards
            g.drawOval(getX1(),getY2(),getX2()-getX1(),getY1()-getY2());
        }
        else { // If operations are normal then draw normally
            g.drawOval(getX1(),getY1(),getX2()-getX1(),getY2()-getY1());
        }
    }
}
