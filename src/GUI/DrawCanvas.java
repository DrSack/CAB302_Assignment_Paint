package GUI;

import Coordinate.*;

import javax.sound.sampled.Line;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class DrawCanvas extends JFrame implements ActionListener, MouseListener, MouseMotionListener {
    private boolean EnableMouseTrack = true;
    private boolean PlotTruth = false;
    private boolean LineTruth = false;
    private boolean RecTruth = false;
    private boolean ElliTruth = false;
    private boolean ClearTruth = false;

    private Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    private  JPanel grid = new JPanel(new GridLayout(7,2));
    private String Title;

    private ArrayList<Double> arrayLine = new ArrayList<>();
    private ArrayList<String> Truth = new ArrayList<>();

    private int counter = 0;
    private int MouseIncrement = 0;
    private int width = 600;
    private int height = 600;

    private double x1;
    private double y1;
    private double x2;
    private double y2;

    private String vecFile = "";

    private JButton saveFile;
    private JButton PLOT, LINE, RECTANGLE, ELLIPSE, POLYGON;
    private JButton Clear;

    private MouseCoordinates Mousetrack = new MouseCoordinates();
    private XY_Coordinates XYtrack = new XY_Coordinates();

    public DrawCanvas(String title, String file){
        this.vecFile = file;
        this.Title = title;

        PlaceButtons();
        JFrame save = new JFrame("Paint Tool");
        save.add(grid);
        save.setSize(100, 500);
        save.setLocation(dim.width/4, dim.height/4);
        save.setVisible(true);

        this.setSize(width,height);
        this.setLocation(dim.width/3, dim.height/4);
        this.setVisible(true);
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    private int getMouseTrack(){// Return the mouseTrack incremented value
        return MouseIncrement;
    }

    private void setMouseTrack(){// Increment mouseTrack value
        MouseIncrement++;
    }

    private void inputLines(double X1, double Y1, double X2, double Y2){// Pass coordinates within the Arraylist.
        arrayLine.add(X1);
        arrayLine.add(Y1);
        arrayLine.add(X2);
        arrayLine.add(Y2);
    }

    public String returnFile(){//Return the vecFile String
        return vecFile;
    }
    private void PlaceButtons(){// Place buttons across the grid
        SetupButtons();
        grid.add(saveFile);
        grid.add(PLOT);
        grid.add(LINE);
        grid.add(RECTANGLE);
        grid.add(ELLIPSE);
        grid.add(POLYGON);
        grid.add(Clear);
    }

    private void SetupButtons(){// Setup the buttons on the second Jframe
        saveFile = createButton("Save VEC");
        PLOT = createButton("Plot");
        LINE = createButton("Line");
        RECTANGLE = createButton("Rectangle");
        ELLIPSE = createButton("Ellipse");
        POLYGON = createButton("Polygon");
        Clear = createButton("Clear");
    }

    private JButton createButton(String title){//Efficient create button method.
        JButton btn = new JButton();
        btn.setText(title);
        btn.addActionListener(this);
        return  btn;
    }

    //Set coordinates for Lines or Plotting
    public void SetCoordinateDrawingPlotting(double X1, double Y1, double X2, double Y2){
        Truth.add("LinePlotTruth");
        inputLines(X1, Y1, X2, Y2);
        counter++;
    }

    //Set coordinates for Rectangle
    public void SetCoordinateRectangle(double X1, double Y1, double X2, double Y2){
        Truth.add("RecTruth");
        inputLines(X1, Y1, X2, Y2);
        counter++;
    }

    //Set coordinates for Ellipse
    public void SetCoordinateEllipse(double X1, double Y1, double X2, double Y2){
        Truth.add("ElliTruth");
        inputLines(X1, Y1, X2, Y2);
        counter++;
    }

    public void SetCoordinatePolygon(int x[], int y[]){
        ElliTruth = true;
        Truth.add("PolyTruth");
        counter++;
    }

    public int parseArrayValue(int index, int dim){// pass index value, and dimensions to return the arraylist Coordinate.
        return (int) (arrayLine.get(index) * dim);
    }

    public int parseArrayIndex(int index){//pass an index, and sets each X,Y value within the XYtrack class. Return the index value.
        XYtrack.setX1(parseArrayValue(index, this.width));
        index++;
        XYtrack.setY1(parseArrayValue(index, this.height));
        index++;
        XYtrack.setX2(parseArrayValue(index, this.width));
        index++;
        XYtrack.setY2(parseArrayValue(index, this.height));
        index++;
        return index;
    }

    public void paint(Graphics g){
        int x = 0;

        if(ClearTruth){
            g.setColor(Color.white);
            g.fillRect(0,0 ,width,height);
            ClearTruth = false;
        }

        for(int o = 0; o < counter; o++) {
            if (Truth.get(o).equals("RecTruth")) {// If "RecTruth" is within the array lineup then draw a rectangle
                x = parseArrayIndex(x);
                g.drawLine(XYtrack.getX1(), XYtrack.getY1(), XYtrack.getX1(), XYtrack.getY2());//down
                g.drawLine(XYtrack.getX1(), XYtrack.getY1(), XYtrack.getX2(), XYtrack.getY1());//right
                g.drawLine(XYtrack.getX2(), XYtrack.getY1(), XYtrack.getX2(), XYtrack.getY2());//corner_down
                g.drawLine(XYtrack.getX2(), XYtrack.getY2(), XYtrack.getX1(), XYtrack.getY2());//corner_left
            }

            else if (Truth.get(o).equals("LinePlotTruth")) {//If "LinePlotTruth" is within the array lineup then draw a Line or Plot
                x = parseArrayIndex(x);
                g.drawLine(XYtrack.getX1(), XYtrack.getY1(), XYtrack.getX2(), XYtrack.getY2());
            }

            else if (Truth.get(o).equals("ElliTruth")){//If "ElliTruth" is within the array lineup then draw an Ellipse
                x = parseArrayIndex(x);
                    if(XYtrack.getX2() == width && XYtrack.getY2() == height){
                    g.drawOval(XYtrack.getX1()+8,XYtrack.getY1()+31, XYtrack.getX2()-17, XYtrack.getY2()-40);
                    }
                    else if(XYtrack.getX1() < width/2 && XYtrack.getY1() < height/2){
                        g.drawOval(XYtrack.getX1()+8,XYtrack.getY1()+31, XYtrack.getX2()-17, XYtrack.getY2()-31);
                    }
                    else{
                        g.drawOval(XYtrack.getX1()+8,XYtrack.getY1() , XYtrack.getX2()-17, XYtrack.getY2()-9);
                    }
            }
        }

    }

    //Whenever a button is called go here.
    @Override
    public void actionPerformed(ActionEvent e) {
        Object btnSrc = e.getSource();
        //Declare the btn object.

        if(btnSrc == saveFile){// If Save button is pressed
            final JFileChooser fcSave = new JFileChooser();
            fcSave.setCurrentDirectory( new File( "./") );// Set Directory to the src of the Program

            fcSave.setAcceptAllFileFilterUsed(false);// Filter out extensions except for .VEC
            FileNameExtensionFilter filter = new FileNameExtensionFilter("VEC files", "VEC");
            fcSave.addChoosableFileFilter(filter);

            final String ext = ".VEC";
            String filePathWithoutExt ="";
            int value = fcSave.showSaveDialog(fcSave);
            if ( value == JFileChooser.APPROVE_OPTION) {// Save button is clicked.
                // If the files name contains a .VEC replace it with nothing this is to prevent a double .VEC.VEC file
                if(fcSave.getSelectedFile().getAbsolutePath().contains(".VEC")){
                    filePathWithoutExt = fcSave.getSelectedFile().getAbsolutePath().replace(".VEC","");
                }
                //if there is no establish .VEC file set the file as is
                else{
                    filePathWithoutExt =fcSave.getSelectedFile().getAbsolutePath();
                }

                File file = new File(filePathWithoutExt + ".VEC");
                // if the save button is pressed save the file followed with the file name inputted the .VEC extension

                if(file.exists())//
                { //If the file already exist pop up a confirm Dialog panel.
                    value = JOptionPane.showConfirmDialog(this,
                            "Replace existing file?");// Asks if the user wants to replace the file.
                    if(value == JOptionPane.YES_OPTION) {
                        try {// if yes then replace the file with the current vecFile string.
                            FileWriter filewrite = new FileWriter(file);
                            filewrite.flush();
                            filewrite.write(returnFile());
                            filewrite.close();
                            file.createNewFile();
                            this.setTitle(fcSave.getSelectedFile().getAbsolutePath());
                        }catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }

                    if (value == JOptionPane.NO_OPTION)// if no then do nothing
                        return;
                }
                if(!file.exists()){//
                    try {// if the file doesn't already exist, create it and write the file with the current vecFile string.
                        FileWriter filewrite = new FileWriter(file);
                        filewrite.flush();
                        filewrite.write(returnFile());
                        filewrite.close();
                        file.createNewFile();
                        this.setTitle(fcSave.getSelectedFile().getAbsolutePath());
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }

            }// if cancel is selected on the JFileChooser do nothing and return to normal operations.
            else if (value==JFileChooser.CANCEL_OPTION){

            }

        }

        if(btnSrc == PLOT){// If PLOT tool is selected, draw ONLY plot and disable other tools.
            ClearTruth = false;
            PlotTruth = true;
            LineTruth = false;
            MouseIncrement = 0;


        }

        if(btnSrc == LINE){// If LINE tool is selected, draw ONLY lines and disable all other boolean paint tools.
            ClearTruth = false;
            PlotTruth = false;
            LineTruth = true;
            MouseIncrement = 0;

        }

        if(btnSrc == Clear){// If Clear is selected then "ClearTruth" boolean is set to true
                            //then reset EVERYTHING e.g. including all arrays then repaint.
            ClearTruth = true;
            MouseIncrement = 0;
            vecFile ="";
            counter = 0;
            arrayLine.clear();
            this.repaint();
        }
    }


    @Override
    public void mouseClicked(MouseEvent e) {




        }


    @Override
    public void mousePressed(MouseEvent e) {
        //If mouse is clicked do something.....
        DecimalFormat df = new DecimalFormat("#.00");//Updates double variables to 2 decimal places.

        double mx1; //Declare double variables
        double my1;
        double mx2;
        double my2;

        String x1;// Initialize stings for vecFile
        String y1;
        String x2;
        String y2;

        if(EnableMouseTrack){// inserts a new line into vecFile if the intention of the user is to draw something.
            this.vecFile +="\n";
            EnableMouseTrack = false;
        }

        if(PlotTruth){
            Mousetrack.setMouseXY(e.getX(),e.getY(),width,height);
        }

        if(LineTruth) {// IF LINE was selected.
        if(getMouseTrack()%2 == 0) {// if the mouse is clicked once set the mouse X,Y values and INCREMENT mouse
            // value by 1.
            setMouseTrack();
            Mousetrack.setMouseXY(e.getX(),e.getY(),this.width,this.height);
        }
        else if(getMouseTrack()%2 == 1) { //if the mouse is clicked when an increment has occured
            // then INRCREMENT mouse again and set coordinates of mouse X2, Y2.
            setMouseTrack();
            Mousetrack.setMouseXY2(e.getX(),e.getY(),this.width,this.height);
            mx1 = Mousetrack.getX1(); // assign x,y's to variables.
            my1 = Mousetrack.getY1();
            mx2 = Mousetrack.getX2();
            my2 = Mousetrack.getY2();

                // Call method to save coordinates to array
                SetCoordinateDrawingPlotting(mx1, my1, mx2, my2);

                //Set String x,y's to 2 decimal places.
                x1 = df.format(mx1);
                y1 = df.format(my1);
                x2 = df.format(mx2);
                y2 = df.format(my2);

                //add mouse coordinates to vecFile
                vecFile +="LINE "+"0"+x1+" 0"+y1+" 0"+x2+" 0"+y2+"\n";
            }
            //repaint the JFrame
            this.repaint();
        }
        }

    @Override
    public void mouseDragged(MouseEvent e) {
        DecimalFormat df = new DecimalFormat("#.00");//Updates double variables to 2 decimal places.

        String x1;// Initialize Strings for vecFile.
        String y1;
        String x2;
        String y2;

    if(PlotTruth){// If "PlotTruth" is true then plot of frame.
        Mousetrack.setMouseXY(e.getX(),e.getY(),width,height);
        double mx1 = Mousetrack.getX1(); //Declare double variables
        double my1 = Mousetrack.getY1();
        double mx2 = Mousetrack.getX1();
        double my2 = Mousetrack.getY1();


        SetCoordinateDrawingPlotting(mx1, my1, mx2, my2);
        vecFile +="PLOT "+mx1+" "+my1+"\n";
        this.repaint();

    }


    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }


}
