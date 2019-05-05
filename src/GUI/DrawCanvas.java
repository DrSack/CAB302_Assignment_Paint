package GUI;

import Coordinate.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class DrawCanvas extends JPanel implements MouseListener{
    private boolean EnableMouseTrack = true;
    boolean EnableOpen = false;

    boolean PlotTruth = false;
    boolean LineTruth = false;
    boolean RecTruth = false;
    boolean ElliTruth = false;
    boolean ClearTruth = false;

    private DecimalFormat df = new DecimalFormat("#.00");//Updates double variables to 2 decimal places.
    private Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    private Color c = Color.black;
    private String Title;

    private ArrayList<Double> arrayLine = new ArrayList<>();
    private ArrayList<String> Truth = new ArrayList<>();
    private ArrayList<Integer> Colourtrack = new ArrayList<>();
    private ArrayList<Color> Colour = new ArrayList<>();

    private int counter = 0;
    private int MouseIncrement = 0;
    private int width = 546;
    private int height = 540;

    private int x1Cor[], y1Cor[];

    private String vecFile = "";

    private MouseCoordinates Mousetrack = new MouseCoordinates();
    private XY_Coordinates XYtrack = new XY_Coordinates();

    public DrawCanvas(String vec){
        vecFile = vec;

        this.setVisible(true);
        addMouseListener(this);
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

    public void clearCanvas(){
        arrayLine.clear();
        Truth.clear();
        EnableMouseTrack = true;
        EnableOpen = false;
        PlotTruth = false;
        LineTruth = false;
        RecTruth = false;
        ElliTruth = false;
        ClearTruth = false;
        counter = 0;
        MouseIncrement = 0;
        vecFile ="";
    }

    public void SetColour(String hex){
        Colourtrack.add(counter);
        Colour.add(Color.decode(hex));
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

    public void SetCoordinatePolygon(double xP[], double yP[]){
        Truth.add("PolyTruth");
        int num = xP.length;
        double holderX;
        double holderY;
        //Initialize x1Cor and y1Cor
        x1Cor = new int[num];
        y1Cor = new int[num];
        //For loop to add coordinates into arrayLine
        for(int i = 0; i < num; i++)
        {
            holderX = xP[i] * this.width;
            x1Cor[i] = (int)holderX;
            for(int a = 0; a < 1; a++)
            {
                holderY = yP[i] * this.height;
                y1Cor[i] = (int)holderY;
            }
        }
        counter++;
    }

    public int parseArrayValue(int index, int dim){// pass index value, and dimensions to return the arraylist Coordinate.
        return (int) (arrayLine.get(index) * dim);
    }

    public int parseArrayIndex(int index){//pass an index, and sets each X,Y value within the XYtrack class. Return the index value.
        XYtrack.setX1(parseArrayValue(index, width));
        index++;
        XYtrack.setY1(parseArrayValue(index, height));
        index++;
        XYtrack.setX2(parseArrayValue(index, width));
        index++;
        XYtrack.setY2(parseArrayValue(index, height));
        index++;
        return index;
    }

    public void paint(Graphics g){
        super.paint(g);
        int x = 0;
        int i = 0;
        for(int o = 0; o < counter; o++) {

            if(i < Colourtrack.size()) {//Default is black if no colours are present
                c = Color.black;
            }

            if(i < Colourtrack.size()) {
                if (o == Colourtrack.get(i)) {// If colours are present switch the colour
                    c = Colour.get(i);
                    System.out.println(o);
                    i++;
                }
            }


            g.setColor(c);// Set colour
             if (Truth.get(o).equals("RecTruth")) {// If "RecTruth" is within the array lineup then draw a rectangle
                x = parseArrayIndex(x);
                g.drawLine(XYtrack.getX1(), XYtrack.getY1(), XYtrack.getX1(), XYtrack.getY2());//down
                g.drawLine(XYtrack.getX1(), XYtrack.getY1(), XYtrack.getX2(), XYtrack.getY1());//right
                g.drawLine(XYtrack.getX2(), XYtrack.getY1(), XYtrack.getX2(), XYtrack.getY2());//corner_down
                g.drawLine(XYtrack.getX2(), XYtrack.getY2(), XYtrack.getX1(), XYtrack.getY2());//corner_left
            }

            if (Truth.get(o).equals("LinePlotTruth")) {//If "LinePlotTruth" is within the array lineup then draw a Line or Plot
                x = parseArrayIndex(x);
                g.drawLine(XYtrack.getX1(), XYtrack.getY1(), XYtrack.getX2(), XYtrack.getY2());
            }

            if (Truth.get(o).equals("ElliTruth")){//If "ElliTruth" is within the array lineup then draw an Ellipse
                x = parseArrayIndex(x);
                g.drawOval(XYtrack.getX1(),XYtrack.getY1(), XYtrack.getX2(), XYtrack.getY2());

            }
            if (Truth.get(o).equals("PolyTruth")) {//If "PolyTruth" is within the array lineup then draw a Polygon
                g.drawPolygon(x1Cor, y1Cor, x1Cor.length);
            }
        }

    }


    @Override
    public void mouseClicked(MouseEvent e) {
        if(PlotTruth){
            Mousetrack.setMouseXY(e.getX(), e.getY(),this.width,this.height);
            SetCoordinateDrawingPlotting(Mousetrack.getX1(),Mousetrack.getY1(),Mousetrack.getX1(),Mousetrack.getY1());
            System.out.println(e.getX()+" "+e.getY());
            vecFile += "PLOT " + "0" + df.format(Mousetrack.getX1()) + " 0" + df.format(Mousetrack.getY1()) +"\n";
            this.repaint();
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {//If mouse is clicked do something.....
        DecimalFormat df = new DecimalFormat("#.00");//Updates double variables to 2 decimal places.

        double mx1; //Declare double variables
        double my1;
        double mx2;
        double my2;

        String x1;
        String y1;
        String x2;
        String y2;


        if(EnableOpen){// inserts a new line into vecFile if the intention of the user is to draw something.
            this.vecFile +="\n";
            EnableOpen = false;
        }

        if(!PlotTruth) {

            if (getMouseTrack() % 2 == 0) {// if the mouse is clicked once set the mouse X,Y values and INCREMENT mouse
                // value by 1.
                setMouseTrack();
                Mousetrack.setMouseXY(e.getX(), e.getY(), this.width, this.height);//setMouseXY converts ints to doubles
            } else if (getMouseTrack() % 2 == 1) { //if the mouse is clicked when an increment has occured
                // then INRCREMENT mouse again and set coordinates of mouse X2, Y2.
                setMouseTrack();
                Mousetrack.setMouseXY2(e.getX(), e.getY(), this.width, this.height);

                mx1 = Mousetrack.getX1(); // assign x,y's to variables.
                my1 = Mousetrack.getY1();
                mx2 = Mousetrack.getX2();
                my2 = Mousetrack.getY2();

                if (LineTruth) {// IF LINE was selected.
                    // Call method to save coordinates to array
                    SetCoordinateDrawingPlotting(mx1, my1, mx2, my2);
                    //Set String x,y's to 2 decimal places.
                    x1 = df.format(mx1);
                    y1 = df.format(my1);
                    x2 = df.format(mx2);
                    y2 = df.format(my2);
                    //add mouse coordinates to vecFile
                    vecFile += "LINE " + "0" + x1 + " 0" + y1 + " 0" + x2 + " 0" + y2 + "\n";
                }

                if (RecTruth) {
                    SetCoordinateRectangle(mx1, my1, mx2, my2);
                    x1 = df.format(mx1);
                    y1 = df.format(my1);
                    x2 = df.format(mx2);
                    y2 = df.format(my2);

                    //add mouse coordinates to vecFile
                    vecFile += "RECTANGLE " + "0" + x1 + " 0" + y1 + " 0" + x2 + " 0" + y2 + "\n";
                }

                if (ElliTruth) {
                    SetCoordinateEllipse(mx1, my1, mx2, my2);

                    x1 = df.format(mx1);
                    y1 = df.format(my1);
                    x2 = df.format(mx2);
                    y2 = df.format(my2);

                    //add mouse coordinates to vecFile
                    vecFile += "ELLIPSE " + "0" + x1 + " 0" + y1 + " 0" + x2 + " 0" + y2 + "\n";
                }
                //repaint the JFrame
                this.repaint();
            }
        }
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