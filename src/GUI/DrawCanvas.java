package GUI;

import Coordinate.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class DrawCanvas extends JPanel implements MouseListener{
    private boolean EnableMouseTrack = true;
    private boolean PlotTruth = false;
    private boolean LineTruth = false;
    private boolean RecTruth = false;
    private boolean ElliTruth = false;
    private boolean ClearTruth = false;

    private Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    private String Title;

    private ArrayList<Double> arrayLine = new ArrayList<>();
    private ArrayList<String> Truth = new ArrayList<>();

    private int counter = 0;
    private int MouseIncrement = 0;
    private int width = 515;
    private int height = 538;

    private double x1;
    private double y1;
    private double x2;
    private double y2;

    private String vecFile = "";

    private MouseCoordinates Mousetrack = new MouseCoordinates();
    private XY_Coordinates XYtrack = new XY_Coordinates();


    public DrawCanvas(){
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
                g.drawOval(XYtrack.getX1(),XYtrack.getY1(), XYtrack.getX2(), XYtrack.getY2());

            }
        }

    }


    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println(e.getX()+" "+e.getY());
    }

    @Override
    public void mousePressed(MouseEvent e) {

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