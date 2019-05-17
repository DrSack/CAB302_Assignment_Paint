package Tools;

import GUI.DrawCanvas;

import java.awt.*;

public class Polygon extends ShapesDrawn {
    public Polygon(double x[], double y[],int width, int height, boolean Fill , Color c, Color f) {
        super(x, y, width,height,Fill,c,f);
    }

    @Override
    public void draw(Graphics g) { // Draws Polygon
        if(getFill()){ // If this particular shape has fill enabled fill the Polygon
            g.setColor(getFillC());
            g.fillPolygon(getXarray(), getYarray(), getXarray().length);
        }

        g.setColor(getPenC()); // Draw the outline
        g.drawPolygon(getXarray(), getYarray(), getXarray().length);
    }
}
