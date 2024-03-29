package GUI;

import Coordinate.*;
import Tools.*;
import Tools.Polygon;
import Tools.Rectangle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * This is the DrawCanvas class that is called upon within the GUI class, this is class extends JPanel and is
 * Responsible for creating the Shapes drawn on the screen, based on the XY values of the mouse.
 *
 * Down below is all the private variables for booleans, strings, arrayLists, Color
 * variables and declaring encapsulation classes.
 */
public class DrawCanvas extends JPanel implements MouseListener, MouseMotionListener {
    /**
     * Boolean set to true to enable temporary draw
     */
    private boolean drawingLine = false;

    /**
     * Boolean set to true to enable filling when drawing
     */
    private boolean Filling = false;

    /**
     * Boolean set to true to enable outline
     */
    private boolean Pen = false;

    /**
     * Boolean set to true whenever drawing a polygon shape until finished
     */
    boolean drawingPoly = false;

    /**
     * A string to hold colors and the hex
     */
    private String colourTemp = "";

    /**
     * A string to hold pen and the hex
     */
    private String penTemp = "";

    /**
     * A string to hold temporarily
     */
    private String tempF;

    /**
     * A string to hold the polygon coordinates then add into commands
     */
    private String polyStr;

    /**
     * An ArrayList for storing shape information
     */
    public ArrayList<ShapesDrawn> Draw = new ArrayList<>(); // Setup arrayList for storing shape information.

    /**
     * An ArrayList to store all commands here
     */
    private ArrayList<String> commands = new ArrayList<>(); // Store all commands here

    /**
     * An ArrayList to store polygon coordinates to draw
     */
    private ArrayList<Double> polylines = new ArrayList<>(); // Store polygon coordinates to draw

    /**
     * Making a format to update String double variables to 5 decimal places
     */
    private DecimalFormat df = new DecimalFormat("#0.00000"); // Updates double variables to 5 decimal places.

    /**
     * Set the outline color to default color black
     */
    private Color c = Color.black;

    /**
     * Set the Color f used for fill
     */
    private Color f;

    /**
     * An integer to count every click when drawing polygon
     */
    private int MouseIncrement = 0;

    /**
     * To hold the x and y coordinates of polygon
     */
    private double[] xP, yP;

    /**
     * Making a xy1 object
     */
    private MouseCoordinates Mxy = new XY1();

    /**
     * Making a xy2 object
     */
    private MouseCoordinates Mxy2 = new XY2();

    /**
     * Making a TruthValues object
     */
    public TruthValues t = new TruthValues();

    /**
     * Making a Grid object
     */
    private Grid grid = new Grid();

    /**
     * This is the constructor which passes the string vec parameter to commands arrayList, where the class will add new lines
     * based on the actions given from the GUI and users drawing motives.
     */
    public DrawCanvas() {
        this.setVisible(true);
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    /**
     * Add VEC file commands being read by the JFileChooser.
     *
     * @param command Whenever a new string of commands is read, pass it through here and add it into the
     * commands arrayList
     */
    public void setOpenCoordinates(String command) {
        commands.add(command);
    }

    /**
     * This returns the Draw.size() arrayList
     *
     * @return The Amount of shapes there are in the list
     */
    public int returnCounter() {
        return Draw.size();
    }

    /**
     *If a VEC file was open, reset all values back to its default state after the file parsing section of the GUI
     */
    void open() {
        penTemp = "PEN #000000" + "\n"; // Temporarily store the VEC command.
        c = Color.black;
        offFill();
        Pen = true;
    }

    /**
     * Return the vecFile to the GUI where it will be turned into a .VEC file
     *
     * @return The vecFile string that was just created from piecing all the commandline elements
     */
    public String returnFile() {
        String vecFile = "";
        for (String command : commands) {
            vecFile += command;
        }
        return vecFile;
    }

    /**
     * Whenever undo is called either delete the next element of the commands arrayList OR delete extra commands
     * if there is PEN and or FILL before or after the current command line.
     */
    public void undo() {
        if (!drawingPoly) {
                String current = commands.get(commands.size()-1);// Get the current command String
                if(!current.contains("FILL") || !current.contains("PEN")){// If it isn't a FILL or PEN command
                    commands.remove(commands.size()-1);// Remove the shape and its coordinates from the commands arraylist.
                    Draw.remove(Draw.size() - 1);// Remove the shape from the Canvas
                    if(commands.size() != 0){// Check whenever commands is still over 0.
                        current = commands.get(commands.size()-1);//Set current command String to the latest array index
                    }
                }
                    if((current.contains("FILL") || current.contains("fill")) && commands.size() > 0){//If it is a FILL command
                        if(current.contains("OFF")){//If it is FILL OFF
                            Pen = true;//Set pen to true
                            commands.remove(commands.size()-1);//remove the command
                            if(commands.size() != 0){// Check whenever commands is still over 0.
                                current = commands.get(commands.size()-1);//Set current command String to the latest array index
                            }
                            if((current.contains("PEN") || current.contains("pen")) && commands.size() > 0){//If there is a pen command after fill
                                Pen = true;//Set pen to true
                                commands.remove(commands.size()-1);//remove the command
                            }
                        }
                        else{
                            tempF = "";//Clear tempF
                            commands.remove(commands.size()-1);//remove the command
                        }
                        if(commands.size() != 0){// Check whenever commands is still over 0.
                            current = commands.get(commands.size()-1);//Set current command String to the latest array index
                        }

                    }
                    if((current.contains("PEN") || current.contains("pen")) && commands.size() > 0){//If it is a PEN command
                        Pen = true;//set Pen true
                        commands.remove(commands.size()-1);//remove the command
                        if(commands.size() != 0){// Check whenever commands is still over 0.
                            current = commands.get(commands.size()-1);//Set current command String to the latest array index
                        }
                        if((current.contains("fill")||current.contains("FILL"))&& commands.size() > 0) {//If it is a FILL command
                            tempF = "";//Clear tempF
                            commands.remove(commands.size()-1);//remove the command
                        }
                    }

            this.repaint();
            if(Draw.size() == 0){
                commands.clear();
            }


            String vecFile = "";
            System.out.println("Commands left: " + commands.size());
            for (String command : commands) {
                vecFile += command;
            }

            System.out.println(vecFile);
        }
    }

    /**
     * Clear the entire canvas and reset values
     */
    public void clearCanvas() {
        Draw.clear();
        commands.clear();
        tempF = "";
        Pen = true;
        drawingLine = false;
    }

    /**
     * This method is called from the GUI whether the button "outline" is enabled, and the colours picked also have
     * outline button enabled.
     *
     * @param hex passes the hex string and puts it into the colour array.
     */
    public void setColourClick(String hex) {
        c = (Color.decode(hex));
        penTemp = "PEN " + hex.toUpperCase() + "\n"; // Temporarily store the VEC command.
        offFill();
        Pen = true;
    }

    /**
     * Get the hex colour code from the VEC file and set that colour to the current color c variable and
     * add the hex code pen value to an ArrayList and add the counter value.
     *
     * @param hex pass through the hex colour code into c.
     */
    public void SetColour(String hex) {
        c = (Color.decode(hex));
    }

    /**
     * Get the pen outline color.
     *
     * @return the pen outline colour.
     */
    public Color returnColour() { return c; }

    /**
     * This method is called from the GUI whether the button "fill" is enabled, and the colours picked also have
     * fill button enabled.
     *
     * @param hex passes the hex string and puts it into the Color f variable.
     */
    public void setFillClick(String hex) {
        // Add the counter to the track array
        f = (Color.decode(hex));
        colourTemp = "FILL " + hex.toUpperCase() + "\n"; // Temporarily store the VEC command
        Filling = true;
    }

    /**
     * Whenever Fill is called set the boolean value to true so that the Shape objects will inherit Fill colour
     * abilities.
     *
     * @param hex pass the hex string into f after decoding the hex colour code.
     */
    public void SetFill(String hex) {
        Filling = true;
        f = (Color.decode(hex)); // Add the Hex colour code to f
    }

    /**
     * Turn off the fill boolean value so that Shape Objects wont inherit the ability to draw.
     */
    public void offFill() {
        Filling = false;
    }

    /**
     * Get the fill color.
     *
     * @return Return the fill color.
     */
    public Color returnFill() { return f; }

    /**
     * Get the Filling current boolean value.
     *
     * @return the Filling current boolean value.
     */
    public boolean returnFilltruth() {return Filling;}

    /**
     * Pass through the dimensions of each coordinate into Drawing Plotting which is responsible for creating object
     * within the ShapesDraw class and storing them into the Draw ArrayList. This method is used for both line and plot.
     *
     * @param X1 Pass though the double X1 value
     * @param Y1 Pass though the double Y1 value
     * @param X2 Pass though the double X2 value
     * @param Y2 Pass though the double Y2 value
     */
    public void SetCoordinateDrawingPlotting(double X1, double Y1, double X2, double Y2) {
        Draw.add(new LineOrPlot(X1,Y1,X2,Y2, this.getWidth(), this.getHeight(),Filling,returnColour(), returnFill()));
    }

    /**
     * Pass through the dimensions of each coordinate into drawing a rectangle. This creates a new object and stores it inside
     * the Draw ArrayList.
     *
     * @param X1 Pass though the double X1 value
     * @param Y1 Pass though the double Y1 value
     * @param X2 Pass though the double X2 value
     * @param Y2 Pass though the double Y2 value
     */
    public void SetCoordinateRectangle(double X1, double Y1, double X2, double Y2) {
        Draw.add(new Rectangle(X1,Y1,X2,Y2,this.getWidth(),this.getHeight(),Filling,returnColour(),returnFill()));
    }

    /**
     * Pass through the dimensions of each coordinate into drawing a Ellipse. This creates a new object and stores it inside
     * the Draw ArrayList.
     *
     * @param X1 Pass though the double X1 value
     * @param Y1 Pass though the double Y1 value
     * @param X2 Pass though the double X2 value
     * @param Y2 Pass though the double Y2 value
     */
    public void SetCoordinateEllipse(double X1, double Y1, double X2, double Y2) {
        Draw.add(new Ellipse(X1,Y1,X2,Y2,this.getWidth(),this.getHeight(),Filling,returnColour(),returnFill()));
    }

    /**
     * Pass through the dimensions of each coordinate within 2 arrays into drawing a Polygon.
     * This creates a new object and stores it inside the Draw ArrayList.
     *
     * @param xP passes the x coordinates array.
     * @param yP passes the y coordinates array.
     */
    public void SetCoordinatePolygon(double[] xP, double[] yP) {
        Draw.add(new Polygon(xP, yP, this.getWidth(), this.getHeight(), Filling, returnColour(), returnFill()));
    }

    /**
     * Set the number value for the variable value within the grid class, as this is used to draw the total amount
     * of line grids.
     *
     * @param setting pass the double value of initiating the setting
     */
    void SetGrid(double setting) {
        grid.setSetting((int)setting);
    }

    /**
     * Makes it so that all drawings are within the canvas itself.
     *
     * @param M Pass the MouseCoordinates class object
     */
    private void SetBoundaries(MouseCoordinates M) {
        if ((M.getX())< 0.0) { // If the x value is behind 0 set mx2 as 0
            M.setMousex(0.0);
        }
        if ((M.getX())> 1.0) { // If the x value is in front of the JPanel screen set mx2 to the maximum coordinate
            M.setMousex(1.0);
        }
        if (M.getY() < 0.0) { // If the y value is behind 0 set my2 as 0
            M.setMousey(0.0);
        }
        if (M.getY() > 1.0) { // If the y value is in front of the JPanel screen set my2 to the maximum coordinate
            M.setMousey(1.0);
        }
    }

    /**
     *This sets up the boundaries of each grid intersection, and latches the shape being drawn to the intersections
     * of whatever grid intersection is being onto.
     *
     * @param xm passes the MouseListener x coordinate
     * @param xy passes the MouseListener y coordinate
     * @param M pass the MouseCoordinates class object
     */
    private void SetGridBoundaries(int xm, int xy, MouseCoordinates M) {
        double sx = 0.0;
        double sy = 0.0;
        int mx;
        int my;

        if (t.isGridTruth()) {
            for (int y = 0; y < grid.getSetting() + 1; y++) {
                for (int x = 0; x < grid.getSetting() + 1; x++) {
                    my = (int)(sy*this.getHeight());
                    mx = (int)(sx*this.getWidth());
                    if (xy > my-25 && xy < my+25 && xm > mx-25 && xm < mx+25 ) {
                        M.setMouseXY(mx, my, this.getWidth(),this.getHeight());
                        drawingLine = true;
                        this.repaint();
                    }
                    sx += (1.0/grid.getSetting());
                }
                sx = 0.0;
                sy += (1.0/grid.getSetting());
            }
        }
    }

    /**
     * Whether this.repaint the paint method will be run through.
     */
    public void paint(Graphics g) {
        super.paint(g);

        // Set current mouse X,Y coordinates
        int x1 = (int)(Mxy.getX() * this.getWidth());
        int y1 = (int)(Mxy.getY() * this.getHeight());
        int x2 = (int)(Mxy2.getX() * this.getWidth());
        int y2 = (int)(Mxy2.getY() * this.getHeight());

        // If the coordinates of the shape are at maximum, reduce by 1 to fit in the screen
        if (x1 == this.getWidth()) {
            x1--;
        }
        if (y1 == this.getWidth()) {
            y1--;
        }
        if (x2 == this.getWidth()) {
            x2--;
        }
        if (y2 == this.getWidth()) {
            y2--;
        }

        if (t.isGridTruth()) { // If the grid is active then draw it here
            double s = 0;
            for (int i = 0; i < grid.getSetting(); i++) { // Use the settings number to dictate how many lines are to be drawn.
                g.setColor(Color.LIGHT_GRAY);
                g.drawLine(0,((int) (s*this.getHeight())), this.getWidth(), ((int) (s*this.getHeight())));
                g.drawLine(((int) (s*this.getWidth())),0,((int) (s*this.getWidth())), this.getHeight());
                s += (1.0/grid.getSetting());
            }
        }

        for (ShapesDrawn s : Draw) { // For loop for redrawing all shapes
            s.refit(this.getWidth(), this.getHeight());
            s.draw(g);
        }

        if (drawingLine) { // If the user is still dragging the shapes, draw the shapes temporarily.
            TemporaryDraw Temp = new TemporaryDraw();
            Temp.temporary(g,x1,y1,x2,y2,c,f,t.isLineTruth(),t.isRecTruth(),t.isElliTruth(),t.isPolyTruth(), returnFilltruth());
        }
    }

    /**
     * Used for nothing.
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        // Does nothing
    }

    /**
     * The mouse pressed method essentially stores the X1 and Y2 coordinates for all shapes and tools.
     *
     * @param e is the mouseEvent parameter where it will be used to call e.getX and e.getY to return the current mouse
     * x and y coordinates.
     */
    @Override
    public void mousePressed(MouseEvent e) { // If mouse is pressed do something
        double mx1 = Mxy.getX();
        double my1 = Mxy.getY();
        double mx2 = Mxy2.getX();
        double my2 = Mxy2.getY();

        // Set the X1,Y1 coordinates to the MouseTrack class.
        boolean DoubleC = false;
        Mxy.setMouseXY(e.getX(), e.getY(), this.getWidth(), this.getHeight());
        Mxy2.setMouseXY(e.getX(),e.getY(), this.getWidth(), this.getHeight());

        // Set the grid boundaries whether the grid is triggered.
        SetGridBoundaries(e.getX(),e.getY(),Mxy);

        if (Filling && !t.isPolyTruth() && !t.isPlotTruth() && !t.isLineTruth()) { // If the user decides to draw with a fill colour present
            if (Pen) {
                DoubleC = true;
            }

            if (colourTemp != tempF) {
                tempF = colourTemp;
                commands.add(colourTemp); // Add fill command to commands arrayList
            }
        }

        if (Pen && !t.isPolyTruth()) { // If the user decides to draw with a colour outline present
            commands.add(penTemp); // Add outline colour command
            if (!DoubleC && (!t.isLineTruth() && !t.isPlotTruth())) {
                commands.add("FILL OFF" + "\n");
            }
            Pen = false; // Deactivate to not add more string to commands arrayList.
        }

        if (t.isPlotTruth()) { // If only plotting then get the position, add command to string, then repaint.
            Mxy.setMouseXY(e.getX(), e.getY(), this.getWidth(), this.getHeight());
            SetCoordinateDrawingPlotting(Mxy.getX(), Mxy.getY(), Mxy.getX(), Mxy.getY());
            commands.add("PLOT "  + df.format(Mxy.getX()) + " " + df.format(Mxy.getY()) + "\n");
            this.repaint();
        }

        // If polygon button is selected, able to draw polygon
        if (t.isPolyTruth()) {
            /*
             * First left click gets position, adds string into polyStr and adds coordinates into polylines arrays,
             * increments the mouseIncrement every click. Also makes drawingPoly into true so you can't undo until finished.
             */
            if (SwingUtilities.isLeftMouseButton(e) && MouseIncrement == 0) {
                drawingPoly = true; // Sets drawingPoly to true, if true cannot undo or clear
                MouseIncrement++;

                mx1 = Mxy.getX(); // Gets x coordinate
                my1 = Mxy.getY(); // Gets y coordinate

                String x1 = df.format(mx1); // Formats the x coordinate into a string
                String y1 = df.format(my1); // Formats the y coordinate into a string

                polyStr = ("POLYGON " + x1 + " " + y1); //polyStr will be used to store into commands after right click
                polylines.add(Mxy.getX()); // Adds coordinate into polylines used later to draw
                polylines.add(Mxy.getY()); // Adds coordinate into polylines used later to draw
            }

            /*
             * Second left click and every other click draws line, increments mouseIncrement every click, adds
             * coordinates into polyLines array, repaints the program every click to show line.
             */
            else if (SwingUtilities.isLeftMouseButton(e) && MouseIncrement > 0) {
                MouseIncrement++;
                Mxy2.setMouseXY(e.getX(), e.getY(), this.getWidth(), this.getHeight()); // Sets the x2 and y2
                SetCoordinateDrawingPlotting(mx1, my1, mx2, my2); //Draws a line depending on mouse clicked

                String x2 = df.format(mx2);
                String y2 = df.format(my2);

                polyStr += (" " + x2 + " " + y2);
                polylines.add(Mxy.getX());
                polylines.add(Mxy.getY());
                this.repaint(); // Used to display the drawing
            }

            // If right click without any lines drawn, don't draw polygon
            else if (SwingUtilities.isRightMouseButton(e) && MouseIncrement == 1) {
                drawingPoly = false;
                MouseIncrement = 0;
                polylines.clear();
            }

            // Right click draws from x2 and y2 of the latest line to the start
            else if (SwingUtilities.isRightMouseButton(e) && MouseIncrement > 0) {
                drawingPoly = false; // Make it into false to be able to undo the entire shape or clear
                xP = new double[polylines.size()/2];
                yP = new double[polylines.size()/2];

                for (int i = 0; i<polylines.size()/2; i++) { // Access the polylines array and setting x and y coordinates another set of arrays
                    xP[i] = polylines.get(2*i);
                    yP[i] = polylines.get(2*i+1);
                }

                for (int i = 0; i<MouseIncrement-1; i++) { // Remove all the lines created based on how many mouse clicks
                    Draw.remove(Draw.size()-1);
                }

                polyStr += "\n";
                polylines.clear(); // Clears polylines to create another polygon shape
                MouseIncrement = 0; // Sets mouseIncrement to 0 to be able to create another polygon shape

                if (Filling) { // If the user decides to draw with a fill colour present
                    if (Pen) {
                        DoubleC = true;
                    }
                    if (colourTemp != tempF) {
                        tempF = colourTemp;
                        commands.add(colourTemp); // Add fill command to commands arrayList
                    }
                }
                if (Pen) {
                    commands.add(penTemp); // Add outline colour command
                    if (!DoubleC) {
                        commands.add("FILL OFF" + "\n");
                    }
                    Pen = false;
                }

                commands.add(polyStr); // Adds polyStr into the command list
                SetCoordinatePolygon(xP, yP); //Redraws the polygon by calling this function
            }
        }

        this.repaint();
    }

    /**
     * Whenever the mouse is dragged get the X,Y2 coordinates then repaint to temporarily see the shapes outline.
     *
     * @param e Is an MouseEvent Listener where this is to find e.getX, e.getY
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        Mxy2.setMouseXY(e.getX(), e.getY(), this.getWidth(), this.getHeight());
        SetGridBoundaries(e.getX(),e.getY(),Mxy2);
        SetBoundaries(Mxy2);
        drawingLine = true;
        this.repaint();
    }

    /**
     * When the mouse is released get the final X,Y2 coordinates and set the MouseTrack.
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        Mxy2.setMouseXY(e.getX(), e.getY(), this.getWidth(), this.getHeight());
        drawingLine = false;

        // Assign x,y to variables.
        double mx1 = Mxy.getX();
        double my1 = Mxy.getY();
        double mx2;
        double my2;

        // Latch onto the nearest grid intersection.
        SetGridBoundaries(e.getX(),e.getY(),Mxy2); // Set what is being drawn to be latched onto the nearest grid intersection.
        mx2 = Mxy2.getX(); // Set mx2 to the current mxy2 X coordinate.
        my2 = Mxy2.getY(); // Set mx2 to the current mxy2 Y coordinate.

        // Set mx2 and mxy to be within the canvas
        SetBoundaries(Mxy2); // Only permits shapes to be drawn within the drawing canvas.
        mx2 = Mxy2.getX(); // Set mx2 to the current mxy2 X coordinate.
        my2 = Mxy2.getY(); // Set mx2 to the current mxy2 Y coordinate.

        // Convert the double formats to String with 2decimal places.
        String x1 = df.format(mx1);
        String y1 = df.format(my1);
        String x2 = df.format(mx2);
        String y2 = df.format(my2);

        if (t.isLineTruth()) { // If Line tool is picked then draw a line
            SetCoordinateDrawingPlotting(mx1, my1, mx2, my2);
            commands.add("LINE " + x1 + " " + y1 + " " + x2 + " " + y2 + "\n");
        }

        if (t.isRecTruth()) { // If Rectangle tool is picked then draw a rectangle.
            SetCoordinateRectangle(mx1, my1, mx2, my2);
            commands.add("RECTANGLE " + x1 + " " + y1 + " " + x2 + " " + y2 + "\n");
        }

        if (t.isElliTruth()) { // If Ellipse tool is picked then draw an ellipse.
            SetCoordinateEllipse(mx1, my1, mx2, my2);
            commands.add("ELLIPSE "  + x1 + " " + y1 + " " + x2 + " " + y2 + "\n");
        }

        this.repaint();
    }

    /**
     * This is used to temporarily draw the polygon lines.
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        // Used to temporarily draw polygon lines
        if (MouseIncrement > 0) {
            Mxy2.setMouseXY(e.getX(), e.getY(), this.getWidth(), this.getHeight());
            SetGridBoundaries(e.getX(), e.getY(), Mxy2);
            SetBoundaries(Mxy2);
            drawingLine = true;
        }
        else {
            drawingLine = false;
        }

        this.repaint();
    }

    /**
     * Used for nothing.
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        // Does nothing
    }

    /**
     * Used for nothing.
     */
    @Override
    public void mouseExited(MouseEvent e) {
        // Does nothing
    }
}