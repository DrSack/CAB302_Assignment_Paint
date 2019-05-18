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
    private boolean drawingLine = false;
    private boolean Filling = false;
    private boolean Pen = false;
    private int tempEx; // Store the last known integer to keep track of ExCommands
    private String colourtemp ="";
    private String pentemp = "";
    private String tempf;
    private String polystr;

    private ArrayList<ShapesDrawn> Draw = new ArrayList<ShapesDrawn>(); // Setup arrayList for storing shape information.
    private ArrayList<String> commands = new ArrayList<>(); // Store all commands here
    private ArrayList<Double> polylines = new ArrayList<>();
    private ArrayList<Integer> ExCommands = new ArrayList<>(); // The index value of every PEN and FILL in commands arrayList.

    private DecimalFormat df = new DecimalFormat("#.00"); // Updates double variables to 2 decimal places.
    private Color c = Color.black;
    private Color f;

    private int MouseIncrement = 0;
    private double[] xP, yP;

    private double currentX, currentY, oldX, oldY, startX, startY;

    private MouseCoordinates Mxy = new XY1();
    private MouseCoordinates Mxy2 = new XY2();
    TruthValues t = new TruthValues();
    Grid grid = new Grid();

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
        if (command.contains("PEN") || command.contains("FILL")) {
            if (commands.size() > 0) {
                ExCommands.add(commands.size());
            }
        }
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
        pentemp = "PEN #000000"+"\n"; // Temporarily store the VEC command.
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
        System.out.println(commands.size());

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
        if (ExCommands.size() > 0) { // Deletes extra commands if it is a PEN or FILL Colour
            int i = ExCommands.get(ExCommands.size()-1);
            if (i == commands.size()-1) { // If FILL or PEN is behind said command then only delete itself
                System.out.println("found");
                commands.remove(commands.size()-1);
                ExCommands.remove(ExCommands.size()-1);
                if (ExCommands.size() > 0) {
                    i = ExCommands.get(ExCommands.size()-1);
                    if (commands.size()-1 == i) { // If there is another command even if its the last string, delete it
                        commands.remove(commands.size()-1);
                        ExCommands.remove(ExCommands.size()-1);
                    }
                }
            }

            else if (i == commands.size()) { // If FILL or PEN is in front of said command then delete the command and itself
                System.out.println("found");
                commands.remove(commands.size()-1);
                commands.remove(commands.size()-1);
                ExCommands.remove(ExCommands.size()-1);
            }
        }

        Draw.remove(Draw.size()-1); // Delete the regular command.
        commands.remove(commands.size()-1);
        this.repaint();

        String vecFile = "";
        System.out.println("Commands left: "+commands.size());
        for (String command : commands) {
            vecFile += command;
        }
        System.out.println(vecFile);
    }

    /**
     * Clear the entire canvas and reset values
     */
    public void clearCanvas() {
        Draw.clear();
        ExCommands.clear();
        commands.clear();
        offFill();
        t.resetTruth();
        colourtemp ="";
        pentemp = "";
        tempEx = 0;
        tempf = "";
        drawingLine = false;
    }

    /**
     * This method is called from the GUI whether the button "outline" is enabled, and the colours picked also have
     * outline button enabled.
     *
     * @param hex passes the hex string and puts it into the colour array
     */
    public void setColourClick(String hex) {
        c = (Color.decode(hex));
        pentemp = "PEN "+hex.toUpperCase()+"\n"; // Temporarily store the VEC command.
        offFill();
        Pen = true;
    }

    /**
     * Get the hex colour code from the VEC file and set that colour to the current color c variable and
     * add the hex code pen value to an ArrayList and add the counter value.
     *
     * @param hex pass through the hex colour code into c.
     */
    void SetColour(String hex) {
        c = (Color.decode(hex));
    }

    /**
     * This method is called from the GUI whether the button "fill" is enabled, and the colours picked also have
     * fill button enabled.
     *
     * @param hex passes the hex string and puts it into the Color f variable.
     */
    public void setFillClick(String hex) {
        // Add the counter to the track array
        f = (Color.decode(hex));
        colourtemp ="FILL "+hex.toUpperCase()+"\n"; // Temporarily store the VEC command
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
        f = (Color.decode(hex)); //Add the Hex colour code to f.
    }

    /**
     * Turn off the fill boolean value so that Shape Objects wont inherit the ability to draw.
     */
    public void offFill() {
        Filling = false;
    }

    /**
     * Next 3 methods all have the same functionality but instead fill out each constructor with different parameter values
     * and the drawing method is slightly different after each method.
     *
     * @param X1 Pass though the double X1 value
     * @param Y1 Pass though the double Y1 value
     * @param X2 Pass though the double X2 value
     * @param Y2 Pass though the double Y2 value
     */

    // Set coordinates for Lines or Plotting
    public void SetCoordinateDrawingPlotting(double X1, double Y1, double X2, double Y2) {
        Draw.add(new LineOrPlot(X1,Y1,X2,Y2, this.getWidth(), this.getHeight(),Filling,c, f));
    }

    // Set coordinates for Rectangle
    public void SetCoordinateRectangle(double X1, double Y1, double X2, double Y2) {
        Draw.add(new Rectangle(X1,Y1,X2,Y2, this.getWidth(), this.getHeight(),Filling,c,f));
    }

    // Set coordinates for Ellipse
    public void SetCoordinateEllipse(double X1, double Y1, double X2, double Y2) {
        Draw.add(new Ellipse(X1,Y1,X2,Y2, this.getWidth(), this.getHeight(),Filling,c,f));
    }

    public void SetCoordinatePolygon(double xP[], double yP[]) {
        Draw.add(new Polygon(xP, yP, this.getWidth(), this.getHeight(), Filling, c,f));
    }

    public void SetGrid(double setting){
        grid.setSetting((int)setting, this.getHeight(),this.getWidth());
    }

    /**
     * Whether this.repaint the paint method will be run through.
     */
    public void paint(Graphics g) {
        super.paint(g);
        // Set current mouse X,Y coordinates
        int x1 = (int)(Mxy.getX()* this.getWidth());
        int y1 = (int)(Mxy.getY()* this.getHeight());
        int x2 = (int)(Mxy2.getX()* this.getWidth());
        int y2 = (int)(Mxy2.getY()* this.getHeight());

        if(t.isGridTruth()){
            double s = 0;
            for(int i = 0; i < grid.getSetting(); i++){
                g.setColor(Color.black);
                g.drawLine(0,((int)(s*this.getHeight())),this.getWidth(), ((int)(s*this.getHeight())));
                g.drawLine(((int)(s*this.getWidth())),0,((int)(s*this.getWidth())), this.getHeight());
                s+=(1.0/grid.getSetting());
            }
        }

        for(ShapesDrawn s : Draw) { // For loop for redrawing all shapes
            s.resize(this.getWidth(),this.getHeight());
            s.draw(g);
        }

        if (drawingLine) { // If the user is still dragging the shapes, draw the shapes temporarily.
            TemporaryDraw Temp = new TemporaryDraw();
            Temp.temporary(g,x1,y1,x2,y2,c,f,t.isLineTruth(),t.isRecTruth(),t.isElliTruth(),t.isPolyTruth(), Filling);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    /**
     * The mouse pressed method essentially stores the X1 and Y2 coordinates for all shapes and tools.
     *
     * @param e is the mouseEvent parameter where it will be used to call e.getX and e.getY to return the current mouse
     * x and y coordinates.
     */
    @Override
    public void mousePressed(MouseEvent e) { // If mouse is pressed do something
        // Set the X1,Y1 coordinates to the MouseTrack class.
        Boolean DoubleC = false;
        Mxy.setMouseXY(e.getX(), e.getY(), this.getWidth(), this.getHeight());
        Mxy2.setMouseXY(e.getX(),e.getY(), this.getWidth(), this.getHeight());
        double sx = 0.0;
        double sy = 0.0;
        int mx;
        int my;
        if(t.isGridTruth()){
            for(int y = 0; y < grid.getSetting()+1; y++) {
                for (int x = 0; x < grid.getSetting()+1; x++) {
                    my = (int)(sy*this.getHeight());
                    mx = (int)(sx*this.getWidth());
                    if(e.getY() > my-25 && e.getY() < my+25 && e.getX() > mx-25 && e.getX() < mx+25 ) {
                        Mxy.setMouseXY(mx, my, this.getWidth(),this.getHeight());
                        drawingLine = true;
                        this.repaint();
                    }
                    sx += (1.0/grid.getSetting());
                }
                sx = 0.0;
                sy += (1.0/grid.getSetting());

            }
        }
        if (Filling && !t.isPolyTruth()) { // If the user decides to draw with a fill colour present
            if (Pen) {
                DoubleC = true;
            }

            if (colourtemp != tempf || ExCommands.size() < tempEx) {
                tempf = colourtemp;
                commands.add(colourtemp); // Add fill command to commands arrayList
                ExCommands.add(commands.size());
                if (ExCommands.size() < tempEx) {
                    Pen = true;
                    DoubleC = true;
                }
                tempEx = ExCommands.size();
            }
        }

        if (Pen && !t.isPolyTruth()) { // If the user decides to draw with a colour outline present
            commands.add(pentemp); //Add outline colour command
            ExCommands.add(commands.size());
            tempEx = commands.size();
            if (!DoubleC) {
                commands.add("FILL OFF" +"\n");
                ExCommands.add(commands.size());
                tempEx = commands.size();
            }
            Pen = false; // Deactivate to not add more string to commands arrayList.
        }

        if (t.isPlotTruth()) { // If only plotting then get the position, add command to string, then repaint.
            Mxy.setMouseXY(e.getX(), e.getY(), this.getWidth(), this.getHeight());
            SetCoordinateDrawingPlotting(Mxy.getX(), Mxy.getY(), Mxy.getX(), Mxy.getY());
            commands.add("PLOT " + "0" + df.format(Mxy.getX()) + " 0" + df.format(Mxy.getY()) + "\n");
            this.repaint();
        }

        // If polygon button is selected, able to draw polygon
        if (t.isPolyTruth()) {
            // First left click gets position, adds string into polystr and adds coordinates into polylines arrays,
            //placeholders for x1 and y1 coordinates named realX and realY, also increments the mouseIncrement every click
            if (SwingUtilities.isLeftMouseButton(e) && MouseIncrement == 0) {
                MouseIncrement++;
                Mxy.setMouseXY(e.getX(), e.getY(), this.getWidth(), this.getHeight());
                String x1 = df.format(Mxy.getX());
                String y1 = df.format(Mxy.getY());
                double realX = Mxy.getX();
                double realY = Mxy.getY();
                this.currentX = Mxy.getX();
                this.currentY = Mxy.getY();
                this.startX = Mxy.getX();
                this.startY = Mxy.getY();
                polystr = ("POLYGON " + "0" + x1 + " 0" + y1);
                polylines.add(realX);
                polylines.add(realY);
            }
            // Second left click and every other click draws line, increments mouseIncrement every click, adds coordinates
            //polyLines array, repaints the program every click to show line.
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
                double realX = Mxy.getX();
                double realY = Mxy.getY();
                polystr += (" 0" + x2 + " 0" + y2);
                polylines.add(realX);
                polylines.add(realY);
                this.repaint();
            }

            // Right click draws from x2 and y2 of the latest line to the start
            if (SwingUtilities.isRightMouseButton(e) && MouseIncrement > 0) {
                SetCoordinateDrawingPlotting(oldX, oldY, startX, startY);
                xP = new double[polylines.size()/2];
                yP = new double[polylines.size()/2];
                for(int i = 0; i<polylines.size()/2; i++) { //Access the polylines array and setting x and y coordinates
                    xP[i] = polylines.get(2*i);
                    yP[i] = polylines.get(2*i+1);
                }
                for(int i = 0; i<MouseIncrement; i++) { // Remove all the lines created based on how many mouse clicks
                    Draw.remove(Draw.size()-1);
                }
                polystr += "\n";
                polylines.clear(); //Clears polylines to create another polygon shape
                MouseIncrement = 0; //Sets mouseIncrement to 0 to create another polygon shape

                if (Filling) { // If the user decides to draw with a fill colour present
                    if (Pen) {
                        DoubleC = true;
                    }

                    if (colourtemp != tempf || ExCommands.size() < tempEx) {
                        tempf = colourtemp;
                        commands.add(colourtemp); // Add fill command to commands arrayList
                        ExCommands.add(commands.size());
                        if (ExCommands.size() < tempEx) {
                            Pen = true;
                            DoubleC = true;
                        }
                        tempEx = ExCommands.size();
                    }
                }

                if(Pen){
                    commands.add(pentemp); //Add outline colour command
                    ExCommands.add(commands.size());
                    tempEx = commands.size();
                    if (!DoubleC) {
                        commands.add("FILL OFF" +"\n");
                        ExCommands.add(commands.size());
                        tempEx = commands.size();
                    }
                }

                commands.add(polystr); // Adds polystr into the command list
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
        double sx = 0.0;
        double sy = 0.0;
        int mx;
        int my;

        if(t.isGridTruth()){
            for(int y = 0; y < grid.getSetting()+1; y++) {
                for (int x = 0; x < grid.getSetting()+1; x++) {
                    my = (int)(sy*this.getHeight());
                    mx = (int)(sx*this.getWidth());
                    if(e.getY() > my-25 && e.getY() < my+25 && e.getX() > mx-25 && e.getX() < mx+25 ) {
                        Mxy2.setMouseXY(mx, my, this.getWidth(),this.getHeight());
                        drawingLine = true;
                        this.repaint();
                    }
                    sx += (1.0/grid.getSetting());
                }
                sx = 0.0;
                sy += (1.0/grid.getSetting());

            }
        }

        drawingLine = true;
        this.repaint();

    }

    @Override
    /**
     * Where the mouse is released get the final X,Y2 coordinates and set the MouseTrack
     */
    public void mouseReleased(MouseEvent e) {
        Mxy2.setMouseXY(e.getX(), e.getY(), this.getWidth(), this.getHeight());
        drawingLine = false;

        // Assign x,y to variables.
        double mx1 = Mxy.getX();
        double my1 = Mxy.getY();
        double mx2 = Mxy2.getX();
        double my2 = Mxy2.getY();

        double sx = 0.0;
        double sy = 0.0;
        int mx;
        int my;

        if(t.isGridTruth()){
            for(int y = 0; y < grid.getSetting()+1; y++) {
                for (int x = 0; x < grid.getSetting()+1; x++) {
                    my = (int)(sy*this.getHeight());
                    mx = (int)(sx*this.getWidth());
                    if(e.getY() > my-25 && e.getY() < my+25 && e.getX() > mx-25 && e.getX() < mx+25 ) {
                        Mxy2.setMouseXY(mx, my, this.getWidth(),this.getHeight());
                        mx2 = Mxy2.getX();
                        my2 = Mxy2.getY();
                        drawingLine = true;
                        this.repaint();
                    }
                    sx += (1.0/grid.getSetting());
                }
                sx = 0.0;
                sy += (1.0/grid.getSetting());

            }
        }

        if ((Mxy2.getX())< 0.0) { // If the x value is behind 0 set mx2 as 0
            mx2 = 0.0;
        }
        if ((Mxy2.getX())> 1.0) { // If the x value is in front of the JPanel screen set mx2 to the maximum coordinate
            mx2 = 1.0;
        }
        if (Mxy2.getY() < 0.0) { // If the y value is behind 0 set my2 as 0
            my2 = 0.0;
        }
        if (Mxy2.getY() > 1.0) { // If the y value is in front of the JPanel screen set my2 to the maximum coordinate
            my2 = 1.0;
        }

        // Convert the double formats to String with 2decimal places.
        String x1 = df.format(mx1);
        String y1 = df.format(my1);
        String x2 = df.format(mx2);
        String y2 = df.format(my2);

        if (t.isLineTruth()) { // If Line tool is picked then draw a line
            SetCoordinateDrawingPlotting(mx1, my1, mx2, my2);
            commands.add("LINE " + "0" + x1 + " 0" + y1 + " 0" + x2 + " 0" + y2 + "\n");
        }

        if (t.isRecTruth()) { // If Rectangle tool is picked then draw a rectangle.
            SetCoordinateRectangle(mx1, my1, mx2, my2);
            // Add mouse coordinates to commands arrayList
            commands.add("RECTANGLE " + "0" + x1 + " 0" + y1 + " 0" + x2 + " 0" + y2 + "\n");
        }

        if (t.isElliTruth()) { // If Ellipse tool is picked then draw an ellipse.
            SetCoordinateEllipse(mx1, my1, mx2, my2);
            // Add mouse coordinates to commands arrayList
            commands.add("ELLIPSE " + "0" + x1 + " 0" + y1 + " 0" + x2 + " 0" + y2 + "\n");
        }
        this.repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // Used to temporarily draw polygon lines
        if (MouseIncrement >=1) {
            Mxy.setMouseXY(e.getX(), e.getY(), this.getWidth(), this.getHeight());
            drawingLine = true;
        }
        else drawingLine = false;
        this.repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}