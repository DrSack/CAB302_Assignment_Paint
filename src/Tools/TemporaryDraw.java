package Tools;

import java.awt.*;

public class TemporaryDraw {

    public void temporary(Graphics g, int x1, int y1, int x2,int y2, Color c,Color f, boolean Line, boolean Rec, boolean Ell, boolean Poly, boolean Fill){

        g.setColor(c);
        if (Line) { // Temporarily draw a line
            g.drawLine(x1, y1, x2, y2);
        }
        if (Rec) {
            if(Fill){//If this has fill enabled fill the Rectangle
                g.setColor(f);
                if (x2 <= x1 && y2 <= y1) {
                    g.fillRect(x2,y2,x1-x2,y1-y2);
                }
                else if (x2 <= x1) {
                    g.fillRect(x2,y1,x1-x2,y2-y1);
                }
                else if (y2 <= y1) {
                    g.fillRect(x1,y2,x2-x1,y1-y2);
                }
                else {
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
        if (Ell){
            if(Fill){//If this has fill enabled fill the Ellipse
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
        if (x2 <= x1 && y2 <= y1) {// Temporarily draw an Ellipse.
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
        if (Poly){
            g.drawLine(x1, y1, x2, y2);
        }
    }
}
