package GUI;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class DrawCanvas extends JFrame implements ActionListener, MouseListener {
    //Testing 4
    private boolean LinePlotTruth = false;
    private boolean RecTruth = false;
    private boolean ElliTruth = false;
    private boolean ClearTruth = false;
    private Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

    private String Title;


    private double arrayLines[] = new double[300];
    private String Truth[] = new String[50];
    private int counter = 0;
    private int truecounter = 0;

    double mousex1, mousey1, mousex2, mousey2;
    private int mouseTrack = 0;

    private int width = 600;
    private int height = 600;


    private double x1 = 0;
    private double y1 = 0;
    private double x2 = 0;
    private double y2 = 0;

    String vecFile = "";

    private JFrame save = new JFrame("Paint Tool");
    private  JPanel grid = new JPanel(new GridLayout(7,2));
    private JButton saveFile;

    private JButton PLOT, LINE, RECTANGLE, ELLIPSE, POLYGON;

    private JButton Clear;



    public DrawCanvas(String title, String file){
        vecFile = file;
        Title = title;

        PlaceButtons();
        save.add(grid);
        save.setSize(100, 500);
        save.setLocation(dim.width/4, dim.height/4);
        save.setVisible(true);

        this.setSize(width,height);
        this.setLocation(dim.width/3, dim.height/4);
        this.setVisible(true);
    }


    public Boolean getLinePlotTruth(){
        return LinePlotTruth;
    }

    public int getMouseTrack(){
        return mouseTrack;
    }

    public void setMouseTrack(){
        mouseTrack++;
    }

    public void setMouseXY(int x, int y){
        mousex1 = (double) x/600;
        mousey1 = (double) y/600;

    }

    public void setMouseXY2(int x, int y){
        mousex2 = (double) x/600;
        mousey2 = (double) y/600;
    }



    public String returnFile(){
        return vecFile;
    }
    private void PlaceButtons(){
        SetupButtons();
        grid.add(saveFile);
        grid.add(PLOT);
        grid.add(LINE);
        grid.add(RECTANGLE);
        grid.add(ELLIPSE);
        grid.add(POLYGON);
        grid.add(Clear);
    }

    private void SetupButtons(){
        saveFile = createButton("Save VEC");
        PLOT = createButton("Plot");
        LINE = createButton("Line");
        RECTANGLE = createButton("Rectangle");
        ELLIPSE = createButton("Ellipse");
        POLYGON = createButton("Polygon");
        Clear = createButton("Clear");
    }

    private JButton createButton(String title){
        JButton btn = new JButton();
        btn.setText(title);
        btn.addActionListener(this);
        return  btn;
    }


    public void SetCoordinateDrawingPlotting(double X1, double Y1, double X2, double Y2){
        Truth[counter] = "LinePlotTruth";

        int i = truecounter;

        this.x1 = X1;
        arrayLines[i] = X1;
        i++;

        this.y1 = Y1;
        arrayLines[i] = Y1;
        i++;

        this.x2 = X2;
        arrayLines[i] = X2;
        i++;

        this.y2 = Y2;
        arrayLines[i] = Y2;
        i++;

        truecounter = i;

        counter++;
    }

    public void SetCoordinateRectangle(double X1, double Y1, double X2, double Y2){ int i = truecounter;
        RecTruth = true;
        Truth[counter] = "RecTruth";

        this.x1 = X1;
        arrayLines[i] = X1;
        i++;

        this.y1 = Y1;
        arrayLines[i] = Y1;
        i++;

        this.x2 = X2;
        arrayLines[i] = X2;
        i++;

        this.y2 = Y2;
        arrayLines[i] = Y2;
        i++;

        truecounter = i;

        counter++;
    }

    public void SetCoordinateEllipse(double X1, double Y1, double X2, double Y2){ int i = truecounter;
        ElliTruth = true;
        Truth[counter] = "ElliTruth";

        this.x1 = X1;
        arrayLines[i] = X1;
        i++;

        this.y1 = Y1;
        arrayLines[i] = Y1;
        i++;

        this.x2 = X2;
        arrayLines[i] = X2;
        i++;

        this.y2 = Y2;
        arrayLines[i] = Y2;
        i++;

        truecounter = i;

        counter++;
    }

    public void SetCoordinatePolygon(int x[], int y[]){
        ElliTruth = true;
        Truth[counter] = "PolyTruth";

        for(int i = 0; i < x.length; i++)
        {
            for(int a = 0; a < 1; a++){
                arrayLines[a] = y[a];
                truecounter += 1;
            }
            arrayLines[i] = x[i];
            truecounter += 1;

        }

        counter++;
    }

    public void paint(Graphics g){
        int x = 0;
        if(ClearTruth == true){
            g.setColor(Color.white);
            g.fillRect(0,0 ,width,height);
        }

        for(int o = 0; o < counter; o++) {
            if (Truth[o] == "RecTruth") {
                    int real_x1 = (int) (arrayLines[x] * width);
                    x++;
                    int real_y1 = (int) (arrayLines[x] * height);
                    x++;
                    int real_x2 = (int) (arrayLines[x] * width);
                    x++;
                    int real_y2 = (int) (arrayLines[x] * height);
                    x++;

                    g.drawLine(real_x1, real_y1, real_x1, real_y2);//down
                    g.drawLine(real_x1, real_y1, real_x2, real_y1);//right
                    g.drawLine(real_x2, real_y1, real_x2, real_y2);//corner_down
                    g.drawLine(real_x2, real_y2, real_x1, real_y2);//corner_left

            }

            else if (Truth[o] == "LinePlotTruth") {
                    int real_x1 = (int) (arrayLines[x] * width);
                    x++;
                    int real_y1 = (int) (arrayLines[x] * height);
                    x++;
                    int real_x2 = (int) (arrayLines[x] * width);
                    x++;
                    int real_y2 = (int) (arrayLines[x] * height);
                    x++;


                    g.drawLine(real_x1, real_y1, real_x2, real_y2);

            }

            else if (Truth[o] == "ElliTruth"){
                    int real_x1 = (int) (arrayLines[x] * width);
                    x++;
                    int real_y1 = (int) (arrayLines[x] * height);
                    x++;
                    int real_x2 = (int) (arrayLines[x] * width);
                    x++;
                    int real_y2 = (int) (arrayLines[x] * height);
                    x++;

                    if(real_x2 == width && real_y2 == height){
                    g.drawOval(real_x1+8,real_y1+31, real_x2-17, real_y2-40);
                    }
                    else if(real_x1 < width/2 && real_y1 < height/2){
                        g.drawOval(real_x1+8,real_y1+31, real_x2-17, real_y2-31);
                    }
                    else{
                        g.drawOval(real_x1+8,real_y1 , real_x2-17, real_y2-9);
                    }


            }
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object btnSrc = e.getSource();

        if(btnSrc == saveFile){
            final JFileChooser fcSave = new JFileChooser();
            fcSave.setCurrentDirectory( new File( "./") );

            fcSave.setAcceptAllFileFilterUsed(false);
            FileNameExtensionFilter filter = new FileNameExtensionFilter("VEC files", "VEC");
            fcSave.addChoosableFileFilter(filter);

            int value = fcSave.showSaveDialog(fcSave);
            if ( value == JFileChooser.APPROVE_OPTION) {
                File file = new File(fcSave.getSelectedFile() + ".VEC");
                if(file.exists())
                {
                    value = JOptionPane.showConfirmDialog(this,
                            "Replace existing file?");
                    // may need to check for cancel option as well
                    if (value == JOptionPane.NO_OPTION)
                        return;
                }
                if(!file.exists()){
                    try {
                        FileWriter filewrite = new FileWriter(file);
                        filewrite.flush();
                        filewrite.write(returnFile());
                        filewrite.close();
                        file.createNewFile();

                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }

            }
            else if (value==JFileChooser.CANCEL_OPTION){

            }

        }

        if(btnSrc == LINE){
            ClearTruth = false;
            LinePlotTruth = true;
        }

        if(btnSrc == Clear){
            ClearTruth = true;
            vecFile ="";
            counter = 0;
            truecounter = 0;
            for(int i = 0; i < arrayLines.length; i++){
                arrayLines[i]=0;
            }
            this.repaint();
        }
    }


    @Override
    public void mouseClicked(MouseEvent e) {

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
