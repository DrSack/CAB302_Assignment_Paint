package Tools;

import java.awt.*;

/**
 This class extends the ShapesDrawn class to draw plots
 *
 */
public class LineOrPlot extends ShapesDrawn{

    public void Drawn(Graphics g, int x1 , int y1, int x2, int y2) {
        g.drawLine(x1, y1, x2, y2);
    }
}
