package Tools;

import java.awt.*;

/**
 This class extends the ShapesDrawn class to draw and fill rectangles
 */
public class Rectangle extends ShapesDrawn {

    public Rectangle(double x1 , double y1, double x2, double y2, int width, int height, boolean Fill ,Color c, Color f) {
        super(x1,y1,x2,y2,width,height,Fill,c,f);
    }

    @Override
    public void draw(Graphics g) { // Draw the Rectangle
        if(getFill()) { // If this particular shape has fill enabled fill the Rectangle
            g.setColor(getFillC());
            if (getX2() <= getX1() && getY2() <= getY1()) { // Both X2 and Y2 and behind X1 and Y1 then draw backwards.
                g.fillRect(getX2(),getY2(),getX1()-getX2(),getY1()-getY2());
            }
            else if (getX2() <= getX1()) { // If X2 goes behind X1 then draw backwards
                g.fillRect(getX2(),getY1(),getX1()-getX2(),getY2()-getY1());
            }
            else if (getY2() <= getY1()) { // If Y2 goes behind Y1 then draw backwards
                g.fillRect(getX1(),getY2(),getX2()-getX1(),getY1()-getY2());
            }
            else { // If operations are normal then draw normally
                g.fillRect(getX1(),getY1(),getX2()-getX1(),getY2()-getY1());
            }
        }

        g.setColor(getPenC()); // Draw the outline

        if (getX2() <= getX1() && getY2() <= getY1()) { // Both X2 and Y2 and behind X1 and Y1 then draw backwards
            g.drawRect(getX2(),getY2(),getX1()-getX2(),getY1()-getY2());
        }
        else if (getX2() <= getX1()) { // If X2 goes behind X1 then draw backwards
            g.drawRect(getX2(),getY1(),getX1()-getX2(),getY2()-getY1());
        }
        else if (getY2() <= getY1()) { // If Y2 goes behind Y1 then draw backwards
            g.drawRect(getX1(),getY2(),getX2()-getX1(),getY1()-getY2());
        }
        else { // If operations are normal then draw normally
            g.drawRect(getX1(),getY1(),getX2()-getX1(),getY2()-getY1());
        }
    }
}
