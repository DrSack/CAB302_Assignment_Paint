package GUI;

import Coordinate.*;
import Tools.Ellipse;
import Tools.LineOrPlot;
import Tools.Rectangle;
import Tools.ShapesDrawn;

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
    private int SumPolygons = 0;


    boolean PlotTruth = false;
    boolean LineTruth = false;
    boolean RecTruth = false;
    boolean ElliTruth = false;
    boolean PolyTruth = false;

    private boolean opened = false;

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

    private ArrayList<String> polylines = new ArrayList<>();

    private int counter = 0;
    private int realcounter = 0;
    private int MouseIncrement = 0;

    private int[] x1Cor;
    private int[] y1Cor;
    private double currentX, currentY, oldX, oldY, startX, startY;

    private String vecFile = "";
    private ArrayList<String> commands = new ArrayList<>();

    private MouseCoordinates Mxy = new XY1();
    private MouseCoordinates Mxy2 = new XY2();

    private XY_Coordinates XYtrack = new XY_Coordinates();


    /**
     * This is the contructor which passes the string vec parameter to vecFile, where the class will add new lines
     * based on the actions given from the GUI and users drawing motives.
     *
     * @param vec pass the parameter and set it as the current vecFile string.
     */
    public DrawCanvas(String vec) {
        vecFile = vec;
        this.setVisible(true);
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    public int returnDraw() {
        return realcounter;
    }

    public int returnCounter(){return counter;}

    public void undo(){
        if (realcounter > 0) {
            if (Filltrack.size() > 0){
                if (Filltrack.get(Filltrack.size() - 1) == counter) {
                    commands.remove(commands.size() - 1);
                }
            }

            if (Colourtrack.size() > 0){
                if (Colourtrack.get(Colourtrack.size() - 1) == counter){
                    commands.remove(commands.size() - 1);
                }
            }

             if (Truth.get(counter - 1).equals("LinePlotTruth")) {
                 commands.remove(commands.size() - 1);
                Truth.remove(counter- 1 );
                removelines();

            }
            else if (Truth.get(counter - 1).equals("RecTruth")) {
                 commands.remove(commands.size() - 1);
                Truth.remove(counter- 1 );
                removelines();
            }
            else if (Truth.get(counter - 1).equals("ElliTruth")) {
                 commands.remove(commands.size() - 1);
                Truth.remove(counter- 1 );
                removelines();
            }
            this.repaint();
            counter--;
            realcounter--;
        }
    }

    /**
     * This method is called from the GUI whether the button "fill" is enabled, and the colours picked also have
     * fill button enabled.
     *
     * @param hex passes the hex string and puts it into the Fill array
     */
    public void setFillClick(String hex) {
        if(Filling){ //  If the user hasn't drawn yet, remove the previous elements from the arrays.
            Filltrack.remove(Filltrack.size()-1);
            Fill.remove(Fill.size()-1);
        }
            //add the counter to the track array
            Filltrack.add(counter);
            Fill.add(Color.decode(hex)); // add the colour to the Fill array
            colourtemp ="FILL "+hex.toUpperCase()+"\n"; // temporarily store the VEC command
            Filling = true;
    }

    /**
     * This method is called from the GUI whether the button "outline" is enabled, and the colours picked also have
     * outline button enabled.
     *
     * @param hex passes the hex string and puts it into the colour array
     */
    public void setColourClick(String hex) {
        if(Pen){ //  if the users hasn't draw, remove the previous element.
            Colourtrack.remove(Colourtrack.size()-1);
            Colour.remove(Colour.size()-1);
        }
        //add the counter to the trackarray
        Colourtrack.add(counter);
        Colour.add(Color.decode(hex)); //add colour to the Colour array
        pentemp = "PEN "+hex+"\n"; // temporarily store the VEC command.
        Pen = true;
    }

    // Pass coordinates within the ArrayList
    private void inputLines(double X1, double Y1, double X2, double Y2) {
        arrayLine.add(X1);
        arrayLine.add(Y1);
        arrayLine.add(X2);
        arrayLine.add(Y2);
    }

    public void open(){
        if(Pen){ //  if the users hasn't draw, remove the previous element.
            Colourtrack.remove(Colourtrack.size()-1);
            Colour.remove(Colour.size()-1);
        }
        //add the counter to the trackarray
        Colourtrack.add(counter);
        Colour.add(Color.BLACK); //add colour to the Colour array
        pentemp = "PEN #000000"+"\n"; // temporarily store the VEC command.
        Pen = true;
        offFill.add(counter);
    }

    // Return the vecFile String
    public String returnFile() {
        realcounter = 0;
        System.out.println(commands.size());
        for(int i = 0 ;i < commands.size(); i++){
            vecFile += commands.get(i);
        }
        commands.clear();
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
        Colourtrack.add(counter); //Add the counter to the track arraylist to trigger the colour switch
        Colour.add(Color.decode(hex)); //Add the hex colour code to the arraylist
    }

    public void SetFill(String hex) {
        Filltrack.add(counter); //Add the counter if which to trigger the fill
        Fill.add(Color.decode(hex)); //Add the Hex colour code to the Fill arraylist
    }

    public void offFill() {
        if(counter > 0){ // if the counter is still 0 don't add the off fill
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

    public void removelines(){
        arrayLine.remove(arrayLine.size()-1);
        arrayLine.remove(arrayLine.size()-1);
        arrayLine.remove(arrayLine.size()-1);
        arrayLine.remove(arrayLine.size()-1);
    }

    public void SetCoordinatePolygon(double xP[], double yP[]) {
        Truth.add("PolyTruth");
        int num = xP.length;

        double holderX;
        double holderY;

        // Initialize x1Cor and y1Cor
        x1Cor = new int[num];
        y1Cor = new int[num];
        String str = "";

        // For loop to add coordinates into arrayLine
        for (int i = 0; i < num; i++) {
            holderX = (xP[i] * this.getWidth());
            x1Cor[i] = (int) holderX;
            str += x1Cor[i] + " ";
            for (int a = 0; a < 1; a++) {
                holderY = (yP[i] * this.getHeight());
                y1Cor[i] = (int) holderY;
                str += y1Cor[i] + " ";
            }
        }
        polylines.add(str);
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
        int x = 0; // Keeps count how many times parseArrayIndex is called
        int p = 0; // Keeps track of the Fill array.
        int i = 0; // Keeps track of the Colour array.
        int z = 0; // Keeps track whether the Fill Off command is present.

        int triggered = 0; // Keeps track if no pen commands are present.
        FillTruth = false;

        // Set current mouse X,Y coordinates
        int x1 = (int)(Mxy.getX()* this.getWidth());
        int y1 = (int)(Mxy.getY()* this.getHeight());
        int x2 = (int)(Mxy2.getX()* this.getWidth());
        int y2 = (int)(Mxy2.getY()* this.getHeight());

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
                ShapesDrawn drawn = new Rectangle();
                 if (FillTruth) { // If true fill shape
                     g.setColor(f);
                     drawn.Fill(g,XYtrack.getX1(),XYtrack.getY1(),XYtrack.getX2(),XYtrack.getY2());
                 }
                 g.setColor(c); // Set colour of the outline after fill is set
                 drawn.Drawn(g,XYtrack.getX1(),XYtrack.getY1(),XYtrack.getX2(),XYtrack.getY2());
            }

            if (Truth.get(o).equals("LinePlotTruth")) { // If "LinePlotTruth" is within the array lineup then draw a Line or Plot
                x = parseArrayIndex(x);
                ShapesDrawn drawn = new LineOrPlot();
                drawn.Drawn(g,XYtrack.getX1(), XYtrack.getY1(), XYtrack.getX2(), XYtrack.getY2());//Draw a Line based on the XYtrack class coordinates
            }

            if (Truth.get(o).equals("ElliTruth")) { // If "ElliTruth" is within the array lineup then draw an Ellipse
                x = parseArrayIndex(x);
                ShapesDrawn drawn = new Ellipse();
                if (FillTruth) { // If true fill shape
                    g.setColor(f);

                    drawn.Fill(g,XYtrack.getX1(),XYtrack.getY1(),XYtrack.getX2(),XYtrack.getY2());
                }
                g.setColor(c); // Set colour of the outline after fill is set
                drawn.Drawn(g,XYtrack.getX1(),XYtrack.getY1(),XYtrack.getX2(),XYtrack.getY2());

            }
            if (Truth.get(o).equals("PolyTruth")) { // If "PolyTruth" is within the array lineup then draw a Polygon
                int holderX;
                int holderY;
                for (int b = 0; b < polylines.size(); b++) {
                    String zz = polylines.get(b);
                    String param[] = zz.split(" ");
                    x1Cor = new int[param.length / 2];
                    y1Cor = new int[param.length / 2];
                    for (int c = 0; c < param.length / 2; c++) {
                        holderX = Integer.parseInt(param[2 * c]);
                        x1Cor[c] = holderX;
                        holderY = Integer.parseInt(param[2 * c + 1]);
                        y1Cor[c] = holderY;
                    }
                    g.drawPolygon(x1Cor, y1Cor, x1Cor.length);
                }
            }
        }
        if (drawingline) { // If the user is still dragging the shapes, draw the shapes temporarily.
            g.setColor(c);
            ShapesDrawn Line = new LineOrPlot();
            ShapesDrawn Rectangle = new Rectangle();
            ShapesDrawn Ellipse = new Ellipse();

            if (LineTruth) { // Temporarily draw a line
                Line.Drawn(g, x1, y1, x2, y2);
            }
            if (RecTruth) { // Temporarily draw a rectangle
                Rectangle.Drawn(g, x1, y1, x2, y2);
            }
            if (ElliTruth){ // Temporarily draw an Ellipse.
                Ellipse.Drawn(g, x1, y1, x2, y2);
            }
            if (PolyTruth){
                g.drawLine(x1, y1, x2, y2);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) { // If mouse is pressed do something.....
        // Set the X1,Y1 coordinates to the Mousetrack class.
        Mxy.setMouseXY(e.getX(), e.getY(), this.getWidth(), this.getHeight());
        if(Pen){ // If the user decides to draw with a colour outline present
            commands.add(pentemp); //Add outline colour command
            commands.add("FILL OFF" +"\n"); // Disable fill
            Pen = false; //Deactivate to not add more string to vecFile.
        }

        if(Filling){ // If the user decides to draw with a fill colour present
            commands.add(colourtemp); //add fill command to vecFile
            Filling = false; //Deactivate to not add more string to vecFile.
        }

        if (PlotTruth) { // If only plotting then get the position, add command to string, then repaint.
            Mxy.setMouseXY(e.getX(), e.getY(), this.getWidth(), this.getHeight());
            SetCoordinateDrawingPlotting(Mxy.getX(), Mxy.getY(), Mxy.getX(), Mxy.getY());
            realcounter++;
            commands.add("PLOT " + "0" + df.format(Mxy.getX()) + " 0" + df.format(Mxy.getY()) + "\n");
            this.repaint();
        }

        // If polygon button is selected, able to draw polygon
        if (PolyTruth) {
            //** First left click gets position, adds string into vecFile, placeholders for x1 and y1 coordinates **//
            //**Increments the mouse clicked**//
            if (SwingUtilities.isLeftMouseButton(e) && MouseIncrement == 0) {
                MouseIncrement++;
                Mxy.setMouseXY(e.getX(), e.getY(), this.getWidth(), this.getHeight());
                String x1 = df.format(Mxy.getX());
                String y1 = df.format(Mxy.getY());
                this.currentX = Mxy.getX();
                this.currentY = Mxy.getY();
                this.startX = Mxy.getX();
                this.startY = Mxy.getY();
                commands.add("POLYGON " + "0" + x1 + " 0" + y1);
            }
            //** Second left click and every other click draws line **//
            else if (SwingUtilities.isLeftMouseButton(e) && MouseIncrement > 0) {
                MouseIncrement++;
                Mxy2.setMouseXY(e.getX(), e.getY(), this.getWidth(), this.getHeight());
                this.oldX = Mxy2.getX();
                this.oldY = Mxy2.getY();
                SetCoordinateDrawingPlotting(currentX, currentY, oldX, oldY);
                currentX = oldX;
                currentY = oldY;
                String x2 = df.format(Mxy2.getX());
                String y2 = df.format(Mxy2.getY());
               commands.add(" 0" + x2 + " 0" + y2);
                this.repaint();
            }

            //** Right click draws from x2 and y2 of the latest line to the start**//
            if (SwingUtilities.isRightMouseButton(e) && MouseIncrement > 2) {
                SetCoordinateDrawingPlotting(oldX, oldY, startX, startY);
                commands.add("\n");
                MouseIncrement = 0;
            }
            this.repaint();
        }
    }

    // Whenever the mouse is dragged get the X,Y2 coordinates then repaint to temporarily see the shapes outline.
    @Override
    public void mouseDragged(MouseEvent e) {
        Mxy2.setMouseXY(e.getX(), e.getY(), this.getWidth(), this.getHeight());
        drawingline = true;
        this.repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) { // Where the mouse is released get the final X,Y2 coordinates and set the Mousetrack.
        Mxy2.setMouseXY(e.getX(), e.getY(), this.getWidth(), this.getHeight());
        drawingline = false;

        // Assign x,y to variables.
        double mx1 = Mxy.getX();
        double my1 = Mxy.getY();
        double mx2 = Mxy2.getX();
        double my2 = Mxy2.getY();

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
        
//        if(Pen){ // If the user decides to draw with a colour outline present
//            vecFile += "\n" + pentemp; //Add outline colour command
//            vecFile += "FILL OFF" +"\n"; // Disable fill
//            Pen = false; //Deactivate to not add more string to vecFile.
//        }
//
//        if(Filling){ // If the user decides to draw with a fill colour present
//                vecFile += colourtemp; //add fill command to vecFile
//                Filling = false; //Deactivate to not add more string to vecFile.
//        }

        if(LineTruth) { // If Line tool is picked then draw a line
            SetCoordinateDrawingPlotting(mx1, my1, mx2, my2);
            realcounter++;
            commands.add("LINE " + "0" + x1 + " 0" + y1 + " 0" + x2 + " 0" + y2 + "\n");
        }

        if (RecTruth) { // If Rectangle tool is picked then draw a rectangle.
            SetCoordinateRectangle(mx1, my1, mx2, my2);
            realcounter++;
            // Add mouse coordinates to vecFile
            commands.add("RECTANGLE " + "0" + x1 + " 0" + y1 + " 0" + x2 + " 0" + y2 + "\n");
        }

        if (ElliTruth) { // If Ellipse tool is picked then draw an ellipse.
            SetCoordinateEllipse(mx1, my1, mx2, my2);
            realcounter++;
            // Add mouse coordinates to vecFile
            commands.add("ELLIPSE " + "0" + x1 + " 0" + y1 + " 0" + x2 + " 0" + y2 + "\n");
        }

        System.out.println(e.getX()+" "+e.getY());
        this.repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if(MouseIncrement >=1) {
            Mxy.setMouseXY(e.getX(), e.getY(), this.getWidth(), this.getHeight());
            drawingline = true;
        }
        else drawingline = false;
        this.repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}