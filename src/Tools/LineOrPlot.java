package Tools;

import java.awt.*;

public class LineOrPlot extends ShapesDrawn{

    public void Drawn(Graphics g, int x1 , int y1, int x2, int y2){
        g.drawLine(x1, y1, x2, y2);
    }
}
