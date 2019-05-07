package GUI;

import Coordinate.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.util.ArrayList;


/**
 * This is the DrawCanvas class that is called upon within the GUI class, this is class extends JPanel and is
 * Responsible for creating the Shapes drawn on the screen, based on the XY values of the mouse.
 *
 * Down below is all the private variables for booleans, strings, arraylists, Color
 * variables and declaring encapsualtion classes.
 *
 */
public class DrawCanvas extends JPanel implements MouseListener, MouseMotionListener {
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

    private int[] x1Cor;
    private int[] y1Cor;

    private String vecFile = "";

    private MouseCoordinates Mousetrack = new MouseCoordinates();
    private XY_Coordinates XYtrack = new XY_Coordinates();


    /**
     * This is the contructor which passes the string vec parameter to vecFile, where the class will add new lines
     * based on the actions given from the GUI and users drawing motives.
     */
    DrawCanvas(String vec) {
        vecFile = vec;
        this.setVisible(true);
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    /**
     * This method is called from the GUI whether the button "fill" is enabled, and the colours picked also have
     * fill button enabled.
     */
    public void setFillClick(String hex) {
        if(Filling){// If the user hasn't drawn yet, remove the previous elements from the arrays.
            Filltrack.remove(Filltrack.size()-1);
            Fill.remove(Fill.size()-1);
        }
            //add the counter to the track array
            Filltrack.add(counter);
            Fill.add(Color.decode(hex));// add the colour to the Fill array
            colourtemp ="FILL "+hex.toUpperCase()+"\n"; // temporarily store the VEC command
            Filling = true;
    }

    /**
     * This method is called from the GUI whether the button "outline" is enabled, and the colours picked also have
     * outline button enabled.
     */
    public void setColourClick(String hex) {
        if(Pen){// if the users hasn't draw, remove the previous element.
            Colourtrack.remove(Colourtrack.size()-1);
            Colour.remove(Colour.size()-1);
        }
        //add the counter to the trackarray
        Colourtrack.add(counter);
        Colour.add(Color.decode(hex));//add colour to the Colour array
        pentemp = "PEN "+hex+"\n";// temporarily store the VEC command.
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

    // Clear the entire canvas and reset all values
    public void clearCanvas() {
        arrayLine.clear();
        Truth.clear();
        Colour.clear();
        Colourtrack.clear();
        offFill.clear();
        Filltrack.clear();
        Fill.clear();
        drawingline = false;
        FillTruth= false;
        Filling = false;
        PlotTruth = false;
        LineTruth = false;
        RecTruth = false;
        ElliTruth = false;
        PolyTruth = false;
        counter = 0;
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

    /**
     * Whether this.repaint the paint method will be run through.
     *
     *
     */
    public void paint(Graphics g) {
        super.paint(g);

        //Set counters to keep track of each part of the arrays listed
        int x = 0;// Keeps count how many times parseArrayIndex is called
        int p = 0;// Keeps track of the Fill array.
        int i = 0;// Keeps track of the Colour array.
        int z = 0;// Keeps track whether the Fill Off command is present.

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
            if (LineTruth) {// Temporarily draw a line
                g.drawLine(x1, y1, x2, y2);
            }
            if (RecTruth) {// Temporarily draw a rectangle
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
            if (ElliTruth){// Temporarily draw an Ellipse.
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
    public void mousePressed(MouseEvent e) { // If mouse is pressed do something.....
        Mousetrack.setMouseXY(e.getX(), e.getY(), this.getWidth(), this.getHeight());
        // Set the X1,Y1 coordinates to the Mousetrack class.

        if(PlotTruth) {// If only plotting then get the position, add command to string, then repaint.
            Mousetrack.setMouseXY(e.getX(), e.getY(), this.getWidth(), this.getHeight());
            SetCoordinateDrawingPlotting(Mousetrack.getX1(),Mousetrack.getY1(),Mousetrack.getX1(),Mousetrack.getY1());
            vecFile += "PLOT " + "0" + df.format(Mousetrack.getX1()) + " 0" + df.format(Mousetrack.getY1()) +"\n";
            this.repaint();
        }

    }

    @Override
    public void mouseDragged(MouseEvent e) { // Whenever the mouse is dragged get the X,Y2 coordinates then repaint
        //to temporarily see the shapes outline.
        Mousetrack.setMouseXY2(e.getX(), e.getY(), this.getWidth(), this.getHeight());
        drawingline = true;
        this.repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {//Where the mouse is released get the final X,Y2 coordinates and set the Mousetrack.
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

        //Convert the double formats to String with 2decimal places.
        String x1 = df.format(mx1);
        String y1 = df.format(my1);
        String x2 = df.format(mx2);
        String y2 = df.format(my2);

        if(Pen){//if the user decides to draw with a colour outline present
            vecFile += pentemp;//Add outline colour command
            vecFile += "FILL OFF" +"\n";// Disable fill
            Pen = false;//Deactivate to not add more string to vecFile.
        }

        if(Filling){//if the user decides to draw with a fill colour present
                vecFile += colourtemp;//add fill command to vecFile
                Filling = false;//Deactivate to not add more string to vecFile.
        }

        if(LineTruth) {//If Line tool is picked then draw a line
            SetCoordinateDrawingPlotting(mx1, my1, mx2, my2);
            vecFile += "LINE " + "0" + x1 + " 0" + y1 + " 0" + x2 + " 0" + y2 + "\n";
        }

        if (RecTruth) {//If Rectangle tool is picked then draw a rectangle.
            SetCoordinateRectangle(mx1, my1, mx2, my2);

            // Add mouse coordinates to vecFile
            vecFile += "RECTANGLE " + "0" + x1 + " 0" + y1 + " 0" + x2 + " 0" + y2 + "\n";
        }

        if (ElliTruth) {//If Ellipse tool is picked then draw an ellipse.
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