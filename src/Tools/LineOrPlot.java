package Tools;

import java.awt.*;

/**
 This class extends the ShapesDrawn class to draw plots
 *
 */
public class LineOrPlot extends ShapesDrawn{

    public LineOrPlot(double x1 , double y1, double x2, double y2, int width, int height, Color c){
        super(x1,y1,x2,y2,width,height,c);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(getPenC());
        g.drawLine(getX1(),getY1(),getX2(),getY2());
    }
}
