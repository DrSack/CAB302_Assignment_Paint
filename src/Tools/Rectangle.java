package Tools;

import java.awt.*;

public class Rectangle extends ShapesDrawn{

    public void Drawn(Graphics g, int x1 , int y1, int x2, int y2){
        if(x2 <= x1 && y2 <= y1) { // Both X2 and Y2 and behind X1 and Y1 then draw backwards.
            g.drawRect(x2,y2,x1-x2,y1-y2);
        }
        else if(x2 <= x1) { // If X2 goes behind X1 then draw backwards
            g.drawRect(x2,y1,x1-x2,y2-y1);
        }
        else if(y2 <= y1) { // If Y2 goes behind Y1 then draw backwards
            g.drawRect(x1,y2,x2-x1,y1-y2);
        }
        else{ // If operations are normal then draw normally
            g.drawRect(x1,y1,x2-x1,y2-y1);
        }
    }


    public void Fill(Graphics g, int x1, int y1, int x2, int y2) {
        if(x2 <= x1 && y2 <= y1) { // Both X2 and Y2 and behind X1 and Y1 then draw backwards.
            g.fillRect(x2,y2,x1-x2,y1-y2);
        }
        else if(x2 <= x1) { // If X2 goes behind X1 then draw backwards
            g.fillRect(x2,y1,x1-x2,y2-y1);
        }
        else if(y2 <= y1) { // If Y2 goes behind Y1 then draw backwards
            g.fillRect(x1,y2,x2-x1,y1-y2);
        }
        else{ // If operations are normal then draw normally
            g.fillRect(x1,y1,x2-x1,y2-y1);
        }
    }
}
