package Tools;

import java.awt.*;

/**
 * The TemporaryDraw class draws shapes temporarily, whether a particular tool is present and the mouse is clicked on the screen
 * this class will call the draw function that is specific to the tool and update the screen with a new drawing each time the mouse is moved.
 */
public class TemporaryDraw {

    /**
     * Passes parameters into temporary drawing the current tool selected and drawing based on its current listed coordinate
     *
     * @param g the graphics parameter which originates from the paint method.
     * @param x1 the mouse x1 coordinate
     * @param y1 the mouse y1 coordinate
     * @param x2 the mouse x2 coordinate
     * @param y2 the mouse y2 coordinate
     * @param c the current pen colour of DrawCanvas
     * @param f the current fill colour of DrawCanvas
     * @param Line the boolean value of LineTruth
     * @param Rec  the boolean value of RecTruth
     * @param Ell the boolean value of ElliTruth
     * @param Poly the boolean value of PolyTruth
     * @param Fill the boolean value whether fill is selected.
     */
    public void temporary(Graphics g, int x1, int y1, int x2,int y2, Color c,Color f, boolean Line, boolean Rec, boolean Ell, boolean Poly, boolean Fill) {
        g.setColor(c);
        if (Line) { // Temporarily draw a line
            g.drawLine(x1, y1, x2, y2);
        }

        if (Rec) {
            if (Fill) { // If this has fill enabled fill the Rectangle
                g.setColor(f);
                if (x2 <= x1 && y2 <= y1) { // Both X2 and Y2 and behind X1 and Y1 then draw backwards.
                    g.fillRect(x2,y2,x1-x2,y1-y2);
                }
                else if (x2 <= x1) { //If X2 goes behind X1 then draw backwards
                    g.fillRect(x2,y1,x1-x2,y2-y1);
                }
                else if (y2 <= y1) { // If Y2 goes behind Y1 then draw backwards
                    g.fillRect(x1,y2,x2-x1,y1-y2);
                }
                else { // If operations are normal then draw normally
                    g.fillRect(x1,y1,x2-x1,y2-y1);
                }
            }

            g.setColor(c);

            // Temporarily draw a rectangle
            if (x2 <= x1 && y2 <= y1) {
                g.drawRect(x2,y2,x1-x2,y1-y2);
            }
            else if (x2 <= x1) {
                g.drawRect(x2,y1,x1-x2,y2-y1);
            }
            else if (y2 <= y1) {
                g.drawRect(x1,y2,x2-x1,y1-y2);
            }
            else {
                g.drawRect(x1,y1,x2-x1,y2-y1);
            }
        }

        if (Ell) {
            if (Fill) { // If this has fill enabled fill the Ellipse
                g.setColor(f);
                if (x2 <= x1 && y2 <= y1) {
                    g.fillOval(x2,y2,x1-x2,y1-y2);
                }
                else if (x2 <= x1) {
                    g.fillOval(x2,y1,x1-x2,y2-y1);
                }
                else if (y2 <= y1) {
                    g.fillOval(x1,y2,x2-x1,y1-y2);
                }
                else {
                    g.fillOval(x1,y1,x2-x1,y2-y1);
                }
            }

            g.setColor(c);

            if (x2 <= x1 && y2 <= y1) { // Temporarily draw an Ellipse.
                g.drawOval(x2,y2,x1-x2,y1-y2);
            }
            else if (x2 <= x1) {
                g.drawOval(x2,y1,x1-x2,y2-y1);
            }
            else if (y2 <= y1) {
                g.drawOval(x1,y2,x2-x1,y1-y2);
            }
            else {
                g.drawOval(x1,y1,x2-x1,y2-y1);
            }
        }

        if (Poly) {
            g.drawLine(x1, y1, x2, y2);
        }
    }
}
