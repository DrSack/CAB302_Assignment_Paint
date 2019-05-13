package GUI;

import Coordinate.*;
import Tools.*;
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
 * Down below is all the private variables for booleans, strings, arraylists, Color
 * variables and declaring encapsualtion classes.
 *
 */
public class DrawCanvas extends JPanel implements MouseListener, MouseMotionListener {
    private boolean drawingline = false;
    private boolean Filling = false;
    private boolean Pen = false;
    private int tempEx;
    private String colourtemp ="";
    private String pentemp = "";
    private String tempf;

    private ArrayList<ShapesDrawn> Draw = new ArrayList<ShapesDrawn>();//Setup arraylist for storing shape information.
    private ArrayList<String> polylines = new ArrayList<>();
    private ArrayList<String> commands = new ArrayList<>();//Store all commands here
    private ArrayList<Integer> ExCommands = new ArrayList<>();//the index value of every PEN and FILL in commands arraylist.

    private DecimalFormat df = new DecimalFormat("#.00"); // Updates double variables to 2 decimal places.
    private Color c = Color.black;
    private Color f;


    private int MouseIncrement = 0;

    private double currentX, currentY, oldX, oldY, startX, startY;

    private MouseCoordinates Mxy = new XY1();
    private MouseCoordinates Mxy2 = new XY2();
    TruthValues t = new TruthValues();

    /**
     * This is the contructor which passes the string vec parameter to commands arraylist, where the class will add new lines
     * based on the actions given from the GUI and users drawing motives.
     *
     */
    public DrawCanvas() {
        this.setVisible(true);
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    public void setOpenCoordinates(String command){
        commands.add(command);
        if(command.contains("PEN") || command.contains("FILL")){
            if(commands.size() > 0) {
                ExCommands.add(commands.size());
            }
        }
    }

    public int returnCounter(){
        return Draw.size();
    }

    public void undo(){
        if(ExCommands.size() > 0){//Deletes extra commands if it is a PEN or FILL Colour
            int i = ExCommands.get(ExCommands.size()-1);
            System.out.println(i);
            System.out.println(commands.size()-1);
            if(i == commands.size()-1){//If FILL or PEN is behind said command then only delete itself
                System.out.println("found");
                commands.remove(commands.size()-1);
                ExCommands.remove(ExCommands.size()-1);
                if(ExCommands.size() > 0){
                    i = ExCommands.get(ExCommands.size()-1);
                if(commands.size()-1 == i){//IF there is another command even if its the last string, delete it.
                    commands.remove(commands.size()-1);
                    ExCommands.remove(ExCommands.size()-1);
                }
                }
            }

            else if(i == commands.size()){//If FILL or PEN is infront of said command then delete the command and itself.
                System.out.println("found");
                commands.remove(commands.size()-1);
                commands.remove(commands.size()-1);
                ExCommands.remove(ExCommands.size()-1);
            }
        }

        Draw.remove(Draw.size()-1);// Delete the regular command.
        commands.remove(commands.size()-1);

        String vecFile = "";
        System.out.println("Commands left: "+commands.size());
        for (String command : commands) {
            vecFile += command;
        }
        System.out.println(vecFile);


        this.repaint();
    }

    public void open(){
        pentemp = "PEN #000000"+"\n"; // temporarily store the VEC command.
        c = Color.black;
        f = Color.white;
        offFill();
        Pen = true;
    }

    // Return the vecFile String
    public String returnFile() {
        String vecFile = "";
        System.out.println(commands.size());

        for(int i = 0 ;i < commands.size(); i++){
            vecFile += commands.get(i);
        }
        return vecFile;
    }

    // Clear the entire canvas and reset all values
    public void clearCanvas() {
        Draw.clear();
        ExCommands.clear();
        commands.clear();
        offFill();
        t.resetTruth();
        colourtemp ="";
        drawingline = false;
    }

    /**
     * This method is called from the GUI whether the button "outline" is enabled, and the colours picked also have
     * outline button enabled.
     *
     * @param hex passes the hex string and puts it into the colour array
     */
    public void setColourClick(String hex) {
        c = (Color.decode(hex));
        pentemp = "PEN "+hex.toUpperCase()+"\n"; // temporarily store the VEC command.
        offFill();
        Pen = true;
    }

    // Add the hex code pen value to an ArrayList and add the counter value.
    public void SetColour(String hex) {
        System.out.println(hex);
        c = (Color.decode(hex));
    }
    /**
     * This method is called from the GUI whether the button "fill" is enabled, and the colours picked also have
     * fill button enabled.
     *
     * @param hex passes the hex string and puts it into the Fill array
     */
    public void setFillClick(String hex) {
        //add the counter to the track array
        f = (Color.decode(hex));
        colourtemp ="FILL "+hex.toUpperCase()+"\n"; // temporarily store the VEC command
        Filling = true;
    }

    public void SetFill(String hex) {
        Filling = true;
        f = (Color.decode(hex)); //Add the Hex colour code to the Fill arraylist
    }

    public void offFill(){
        Filling = false;
    }

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
        int num = xP.length;

        double holderX;
        double holderY;

        // Initialize x1Cor and y1Cor
        int[] x1Cor = new int[num];
        int[] y1Cor = new int[num];
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
    }
    /**
     * Whether this.repaint the paint method will be run through.
     *
     *
     */
    public void paint(Graphics g) {
        super.paint(g);

        // Set current mouse X,Y coordinates
        int x1 = (int)(Mxy.getX()* this.getWidth());
        int y1 = (int)(Mxy.getY()* this.getHeight());
        int x2 = (int)(Mxy2.getX()* this.getWidth());
        int y2 = (int)(Mxy2.getY()* this.getHeight());

        for(ShapesDrawn s : Draw){
            s.resize(this.getWidth(),this.getHeight());
            s.draw(g);
        }

        if (drawingline) { // If the user is still dragging the shapes, draw the shapes temporarily.
            TemporaryDraw Temp = new TemporaryDraw();
            Temp.temporary(g,x1,y1,x2,y2,c,f,t.isLineTruth(),t.isRecTruth(),t.isElliTruth(),t.isPolyTruth(), Filling);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) { // If mouse is pressed do something.....
        // Set the X1,Y1 coordinates to the Mousetrack class.
        Boolean DoubleC = false;
        Mxy.setMouseXY(e.getX(), e.getY(), this.getWidth(), this.getHeight());

        if(Filling){ // If the user decides to draw with a fill colour present
            if(Pen){
                DoubleC = true;
            }
            if(colourtemp != tempf || ExCommands.size() < tempEx){
                tempf = colourtemp;
                commands.add(colourtemp); //add fill command to commands arraylist
                ExCommands.add(commands.size());
                if(ExCommands.size() < tempEx){
                    Pen = true;
                    DoubleC = true;
                }
                tempEx = ExCommands.size();
            }
        }

        if(Pen){ // If the user decides to draw with a colour outline present
            commands.add(pentemp); //Add outline colour command
            ExCommands.add(commands.size());
            tempEx = commands.size();
            if(Filling){
                if(!DoubleC){
                    commands.add("FILL OFF" +"\n");
                    ExCommands.add(commands.size());
                    tempEx = commands.size();
                }

            }
            Pen = false; //Deactivate to not add more string to commands arraylist.
        }

        if (t.isPlotTruth()) { // If only plotting then get the position, add command to string, then repaint.
            Mxy.setMouseXY(e.getX(), e.getY(), this.getWidth(), this.getHeight());
            SetCoordinateDrawingPlotting(Mxy.getX(), Mxy.getY(), Mxy.getX(), Mxy.getY());
            commands.add("PLOT " + "0" + df.format(Mxy.getX()) + " 0" + df.format(Mxy.getY()) + "\n");
            this.repaint();
        }

        // If polygon button is selected, able to draw polygon
        if (t.isPolyTruth()) {
            //** First left click gets position, adds string into commands arraylist, placeholders for x1 and y1 coordinates **//
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
        if ((Mxy2.getX())< 0.0) {
            Mxy2.setMousex(0.0);
        }
        if ((Mxy2.getX())> 1.0) {
            Mxy2.setMousex(1.0);
        }
        if (Mxy2.getY() < 0.0) {
            Mxy2.setMousey(0.0);
        }
        if (Mxy2.getY() > 1.0) {
            Mxy2.setMousey(1.0);
        }
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

        if ((Mxy2.getX())< 0.0) {
            mx2 = 0.0;
        }
        if ((Mxy2.getX())> 1.0) {
            mx2 = 1.0;
        }
        if (Mxy2.getY() < 0.0) {
            my2 = 0.0;
        }
        if (Mxy2.getY() > 1.0) {
            my2 = 1.0;
        }

        //Convert the double formats to String with 2decimal places.
        String x1 = df.format(mx1);
        String y1 = df.format(my1);
        String x2 = df.format(mx2);
        String y2 = df.format(my2);

        if(t.isLineTruth()) { // If Line tool is picked then draw a line
            SetCoordinateDrawingPlotting(mx1, my1, mx2, my2);
            commands.add("LINE " + "0" + x1 + " 0" + y1 + " 0" + x2 + " 0" + y2 + "\n");
        }

        if (t.isRecTruth()) { // If Rectangle tool is picked then draw a rectangle.
            SetCoordinateRectangle(mx1, my1, mx2, my2);
            // Add mouse coordinates to commands arraylist
            commands.add("RECTANGLE " + "0" + x1 + " 0" + y1 + " 0" + x2 + " 0" + y2 + "\n");
        }

        if (t.isElliTruth()) { // If Ellipse tool is picked then draw an ellipse.
            SetCoordinateEllipse(mx1, my1, mx2, my2);
            // Add mouse coordinates to commands arraylist
            commands.add("ELLIPSE " + "0" + x1 + " 0" + y1 + " 0" + x2 + " 0" + y2 + "\n");
        }
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