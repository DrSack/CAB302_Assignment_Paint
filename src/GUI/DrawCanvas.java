package GUI;

import Coordinate.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class DrawCanvas extends JPanel implements MouseListener, MouseMotionListener {
    private boolean EnableOpen = false;
    private boolean drawingline = false;
    private  boolean FillTruth= false;
    private boolean Filling = false;
    private  boolean Pen = false;
    private String colourtemp ="";
    private String pentemp = "";

    boolean PlotTruth = false;
    boolean LineTruth = false;
    boolean RecTruth = false;
    boolean ElliTruth = false;
    boolean PolyTruth = false;
    boolean ClearTruth = false;

    private DecimalFormat df = new DecimalFormat("#.00"); // Updates double variables to 2 decimal places.
    private Color c = Color.black;
    private Color f;

    private ArrayList<Double> arrayLine = new ArrayList<>();
    private ArrayList<String> Truth = new ArrayList<>();

    private ArrayList<Integer> offFill = new ArrayList<>();
    private ArrayList<Integer> Filltrack = new ArrayList<>();
    private ArrayList<Color> Fill = new ArrayList<>();

    private ArrayList<Integer> Colourtrack = new ArrayList<>();
    private ArrayList<Color> Colour = new ArrayList<>();

    private int counter = 0;
    private int MouseIncrement = 0;

    private int x1Cor[], y1Cor[];

    private String vecFile = "";

    private MouseCoordinates Mousetrack = new MouseCoordinates();
    private XY_Coordinates XYtrack = new XY_Coordinates();

    DrawCanvas(String vec) {
        vecFile = vec;

        this.setVisible(true);
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    public void setFillClick(String hex) {
        if(Filling){
            Filltrack.remove(Filltrack.size()-1);
            Fill.remove(Fill.size()-1);
        }
            Filltrack.add(counter);
            Fill.add(Color.decode(hex));
            colourtemp ="FILL "+hex.toUpperCase()+"\n";

            Filling = true;
    }

    public void setColourClick(String hex) {
        if(Pen){
            Colourtrack.remove(Colourtrack.size()-1);
            Colour.remove(Colour.size()-1);
        }
        Colourtrack.add(counter);
        Colour.add(Color.decode(hex));
        pentemp = "PEN "+hex+"\n";
        Pen = true;
    }

    // Pass coordinates within the ArrayList
    private void inputLines(double X1, double Y1, double X2, double Y2) {
        arrayLine.add(X1);
        arrayLine.add(Y1);
        arrayLine.add(X2);
        arrayLine.add(Y2);
    }

    // Return the vecFile String
    public String returnFile() {
        return vecFile;
    }

    public void clearCanvas() {
        arrayLine.clear();
        Truth.clear();
        Colour.clear();
        Colourtrack.clear();
        offFill.clear();
        Filltrack.clear();
        Fill.clear();
        EnableOpen = false;
        drawingline = false;
        FillTruth= false;
        Filling = false;
        PlotTruth = false;
        LineTruth = false;
        RecTruth = false;
        ElliTruth = false;
        PolyTruth = false;
        ClearTruth = false;
        counter = 0;
        MouseIncrement = 0;
        vecFile ="";
        colourtemp ="";
    }

    // Add the hex code pen value to an ArrayList and add the counter value.
    public void SetColour(String hex) {
        Colourtrack.add(counter);//Add the counter to the track arraylist to trigger the colour switch
        Colour.add(Color.decode(hex));//Add the hex colour code to the arraylist
    }

    public void SetFill(String hex) {
        Filltrack.add(counter);//Add the counter if which to trigger the fill
        Fill.add(Color.decode(hex));//Add the Hex colour code to the Fill arraylist
    }

    public void offFill() {
        if(counter > 0){//if the counter is still 0 don't add the off fill
            offFill.add(counter);
        }
    }

    // Set coordinates for Lines or Plotting
    public void SetCoordinateDrawingPlotting(double X1, double Y1, double X2, double Y2) {
        Truth.add("LinePlotTruth");
        inputLines(X1, Y1, X2, Y2);
        counter++;
    }

    // Set coordinates for Rectangle
    public void SetCoordinateRectangle(double X1, double Y1, double X2, double Y2) {
        Truth.add("RecTruth");
        inputLines(X1, Y1, X2, Y2);
        counter++;
    }

    // Set coordinates for Ellipse
    public void SetCoordinateEllipse(double X1, double Y1, double X2, double Y2) {
        Truth.add("ElliTruth");
        inputLines(X1, Y1, X2, Y2);
        counter++;
    }

    public void SetCoordinatePolygon(double xP[], double yP[]) {
        Truth.add("PolyTruth");
        int num = xP.length;
        double holderX;
        double holderY;

        // Initialize x1Cor and y1Cor
        x1Cor = new int[num];
        y1Cor = new int[num];

        // For loop to add coordinates into arrayLine
        for(int i = 0; i < num; i++)
        {
            holderX = xP[i] * this.getWidth();
            x1Cor[i] = (int)holderX;
            for(int a = 0; a < 1; a++)
            {
                holderY = yP[i] * this.getHeight();
                y1Cor[i] = (int)holderY;
            }
        }
        counter++;
    }

    // Pass index value and dimensions to return the ArrayList Coordinate.
    public int parseArrayValue(int index, int dim) {
        return (int) (arrayLine.get(index) * dim);
    }

    // Pass an index and sets each X,Y value within the XYtrack class. Return the index value.
    public int parseArrayIndex(int index) {
        XYtrack.setX1(parseArrayValue(index, this.getWidth()));
        index++;
        XYtrack.setY1(parseArrayValue(index, this.getHeight()));
        index++;
        XYtrack.setX2(parseArrayValue(index, this.getWidth()));
        index++;
        XYtrack.setY2(parseArrayValue(index, this.getHeight()));
        index++;
        return index;
    }

    public void paint(Graphics g) {
        super.paint(g);

        int x = 0;
        int p = 0;
        int i = 0;
        int z = 0;

        int triggered = 0; // Keeps track if no pen commands are present.
        FillTruth = false;

        // Set current mouse X,Y coordinates
        int x1 = (int)(Mousetrack.getX1()* this.getWidth());
        int y1 = (int) (Mousetrack.getY1()* this.getHeight());
        int x2 = (int)(Mousetrack.getX2()* this.getWidth());
        int y2 = (int)(Mousetrack.getY2()* this.getHeight());

        for(int o = 0; o < counter; o++) {

            if(i < Colourtrack.size() && triggered == 0) { // Default is black if no colours are present
            c = Color.black;
            }

            if(i < Colourtrack.size()) {
                if (o == Colourtrack.get(i)) { // If colours are present switch the colour
                    triggered = 1;
                    c = Colour.get(i);
                    i++;
                }
            }
            if (p < Filltrack.size()) { // If fill is present set f to fill colour
                if (o == Filltrack.get(p)) {
                    FillTruth = true;
                    f = Fill.get(p);
                    p++;
                }
            }
            if (z < offFill.size()) { // Set FillTruth to false
                if(o == offFill.get(z)) {
                    FillTruth = false;
                    z++;
                }
            }

            g.setColor(c);
             if (Truth.get(o).equals("RecTruth")) { // If "RecTruth" is within the array lineup then draw a rectangle
                x = parseArrayIndex(x);

                 if (FillTruth) { // If true fill shape
                     g.setColor(f);
                     g.fillRect(XYtrack.getX1(), XYtrack.getY1(), XYtrack.getX2() - XYtrack.getX1(), XYtrack.getY2() - XYtrack.getY1());
                 }

                 g.setColor(c);// Set colour of the outline after fill is set
                 if(XYtrack.getX2() <= XYtrack.getX1() && XYtrack.getY2() <= XYtrack.getY1()) {//both X2 and Y2 and behind X1 and Y1 then draw backwards.
                     g.drawRect(XYtrack.getX2(),XYtrack.getY2(),XYtrack.getX1()-XYtrack.getX2(),XYtrack.getY1()-XYtrack.getY2());
                 }
                 else if(XYtrack.getX2() <= XYtrack.getX1()) {//if X2 goes behind X1 then draw backwards
                     g.drawRect(XYtrack.getX2(),XYtrack.getY1(),XYtrack.getX1()-XYtrack.getX2(),XYtrack.getY2()-XYtrack.getY1());
                 }
                 else if(XYtrack.getY2() <= XYtrack.getY1()) {//if Y2 goes behind Y1 then draw backwards
                     g.drawRect(XYtrack.getX1(),XYtrack.getY2(),XYtrack.getX2()-XYtrack.getX1(),XYtrack.getY1()-XYtrack.getY2());
                 }
                 else{// If operations are normal then draw normally
                     g.drawRect(XYtrack.getX1(),XYtrack.getY1(),XYtrack.getX2()-XYtrack.getX1(),XYtrack.getY2()-XYtrack.getY1());
                 }

            }

            if (Truth.get(o).equals("LinePlotTruth")) { // If "LinePlotTruth" is within the array lineup then draw a Line or Plot
                x = parseArrayIndex(x);
                g.drawLine(XYtrack.getX1(), XYtrack.getY1(), XYtrack.getX2(), XYtrack.getY2());//Draw a Line based on the XYtrack class coordinates
            }

            if (Truth.get(o).equals("ElliTruth")) { // If "ElliTruth" is within the array lineup then draw an Ellipse
                x = parseArrayIndex(x);

                if (FillTruth) { // If true fill shape
                    g.setColor(f);
                    if(XYtrack.getX2() <= XYtrack.getX1() && XYtrack.getY2() <= XYtrack.getY1()) {//both X2 and Y2 and behind X1 and Y1 then draw backwards.
                        g.fillOval(XYtrack.getX2(),XYtrack.getY2(),XYtrack.getX1()-XYtrack.getX2(),XYtrack.getY1()-XYtrack.getY2());
                    }
                    else if(XYtrack.getX2() <= XYtrack.getX1()) {//if X2 goes behind X1 then draw backwards
                        g.fillOval(XYtrack.getX2(),XYtrack.getY1(),XYtrack.getX1()-XYtrack.getX2(),XYtrack.getY2()-XYtrack.getY1());
                    }
                    else if(XYtrack.getY2() <= XYtrack.getY1()) {//if Y2 goes behind Y1 then draw backwards
                        g.fillOval(XYtrack.getX1(),XYtrack.getY2(),XYtrack.getX2()-XYtrack.getX1(),XYtrack.getY1()-XYtrack.getY2());
                    }
                    else{// If operations are normal then draw normally
                        g.fillOval(XYtrack.getX1(),XYtrack.getY1(),XYtrack.getX2()-XYtrack.getX1(),XYtrack.getY2()-XYtrack.getY1());
                    }
                }

                g.setColor(c);// Set colour of the outline after fill is set
                if(XYtrack.getX2() <= XYtrack.getX1() && XYtrack.getY2() <= XYtrack.getY1()) {//both X2 and Y2 and behind X1 and Y1 then draw backwards.
                    g.drawOval(XYtrack.getX2(),XYtrack.getY2(),XYtrack.getX1()-XYtrack.getX2(),XYtrack.getY1()-XYtrack.getY2());
                }
                else if(XYtrack.getX2() <= XYtrack.getX1()) {//if X2 goes behind X1 then draw backwards
                    g.drawOval(XYtrack.getX2(),XYtrack.getY1(),XYtrack.getX1()-XYtrack.getX2(),XYtrack.getY2()-XYtrack.getY1());
                }
                else if(XYtrack.getY2() <= XYtrack.getY1()) {//if Y2 goes behind Y1 then draw backwards
                    g.drawOval(XYtrack.getX1(),XYtrack.getY2(),XYtrack.getX2()-XYtrack.getX1(),XYtrack.getY1()-XYtrack.getY2());
                }
                else{// If operations are normal then draw normally
                    g.drawOval(XYtrack.getX1(),XYtrack.getY1(),XYtrack.getX2()-XYtrack.getX1(),XYtrack.getY2()-XYtrack.getY1());
                }

            }
            if (Truth.get(o).equals("PolyTruth")) { // If "PolyTruth" is within the array lineup then draw a Polygon
                g.drawPolygon(x1Cor, y1Cor, x1Cor.length);
            }
        }
        if (drawingline) { // If the user is still dragging the shapes, draw the shapes temporarily.
            g.setColor(c);
            if (LineTruth) {
                // 0.25 0.25 0.75 0.5
                g.drawLine(x1, y1, x2, y2);
            }
            if (RecTruth) {
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
            if (ElliTruth){
                if (x2 <= x1 && y2 <= y1) {
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
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) { // If mouse is clicked do something.....
        System.out.println(e.getX()+" "+e.getY());
        Mousetrack.setMouseXY(e.getX(), e.getY(), this.getWidth(), this.getHeight());

        if(EnableOpen) { // Inserts a new line into vecFile if the intention of the user is to draw something.
            this.vecFile +="\n";
            EnableOpen = false;
        }

        if(PlotTruth) {
            Mousetrack.setMouseXY(e.getX(), e.getY(), this.getWidth(), this.getHeight());
            SetCoordinateDrawingPlotting(Mousetrack.getX1(),Mousetrack.getY1(),Mousetrack.getX1(),Mousetrack.getY1());
            vecFile += "PLOT " + "0" + df.format(Mousetrack.getX1()) + " 0" + df.format(Mousetrack.getY1()) +"\n";
            this.repaint();
        }

    }

    @Override
    public void mouseDragged(MouseEvent e) { // Whenever the mouse is dragged get the X,Y2 coordinates then repaint
        Mousetrack.setMouseXY2(e.getX(), e.getY(), this.getWidth(), this.getHeight());
        drawingline = true;
        this.repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        DecimalFormat df = new DecimalFormat("#.00"); // Updates double variables to 2 decimal places.
        Mousetrack.setMouseXY2(e.getX(), e.getY(), this.getWidth(), this.getHeight());
        drawingline = false;

        // Assign x,y to variables.
        double mx1 = Mousetrack.getX1();
        double my1 = Mousetrack.getY1();
        double mx2 = Mousetrack.getX2();
        double my2 = Mousetrack.getY2();

        if (mx2 > this.getWidth()) {
            mx2 = this.getWidth();
        }

        if (my2 > this.getHeight()) {
            my2 = this.getHeight();
        }

        String x1 = df.format(mx1);
        String y1 = df.format(my1);
        String x2 = df.format(mx2);
        String y2 = df.format(my2);

        if(Pen){
            vecFile += pentemp;
            vecFile += "FILL OFF" +"\n";
            Pen = false;
        }

        if(Filling){
                vecFile += colourtemp;
                Filling = false;
        }

        if(LineTruth) {
            SetCoordinateDrawingPlotting(mx1, my1, mx2, my2);
            vecFile += "LINE " + "0" + x1 + " 0" + y1 + " 0" + x2 + " 0" + y2 + "\n";
        }

        if (RecTruth) {
            SetCoordinateRectangle(mx1, my1, mx2, my2);

            // Add mouse coordinates to vecFile
            vecFile += "RECTANGLE " + "0" + x1 + " 0" + y1 + " 0" + x2 + " 0" + y2 + "\n";
        }

        if (ElliTruth) {
            SetCoordinateEllipse(mx1, my1, mx2, my2);

            // Add mouse coordinates to vecFile
            vecFile += "ELLIPSE " + "0" + x1 + " 0" + y1 + " 0" + x2 + " 0" + y2 + "\n";
        }
        System.out.println(e.getX()+" "+e.getY());
        this.repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        this.repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}