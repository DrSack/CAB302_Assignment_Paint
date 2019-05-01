package GUI;

import org.w3c.dom.events.MouseEvent;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DrawCanvas extends JFrame implements ActionListener{

    double arrayLines[] = new double[300];
    int counter = 0;
    int truecounter = 0;

    double x1 = 0;
    double y1 = 0;
    double x2 = 0;
    double y2 = 0;

    String vecFile = "";

    private JFrame save = new JFrame("Paint Tool");
    private  JPanel grid = new JPanel(new GridLayout(6,0));
    private JButton saveFile;

    private JButton PLOT, LINE, RECTANGLE, ELLIPSE, POLYGON;



    public DrawCanvas(String title, String file){
        vecFile = file;
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        PlaceButtons();
        save.add(grid);
        save.setSize(100, 500);
        save.setLocation(dim.width/4, dim.height/4);
        save.setVisible(true);

        this.setSize(500,500);
        this.setTitle(title);
        this.setLocation(dim.width/3, dim.height/4);
        this.setVisible(true);
    }

    private void PlaceButtons(){
        SetupButtons();
        grid.add(saveFile);
        grid.add(PLOT);
        grid.add(LINE);
        grid.add(RECTANGLE);
        grid.add(ELLIPSE);
        grid.add(POLYGON);
    }

    private void SetupButtons(){
        saveFile = createButton("Save VEC");
        PLOT = createButton("Plot");
        LINE = createButton("Line");
        RECTANGLE = createButton("Rectangle");
        ELLIPSE = createButton("Ellipse");
        POLYGON = createButton("Polygon");
    }

    private JButton createButton(String title){
        JButton btn = new JButton();
        btn.setText(title);
        btn.addActionListener(this);
        return  btn;
    }


    public void SetCoordinateDrawingPlotting(double X1, double Y1, double X2, double Y2){
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

    public void paint(Graphics g){
        int x = 0;
        for(int i = 0; i < counter; i++) {
            int real_x1 = (int) (arrayLines[x] * 500);
            x++;
            int real_y1 = (int) (arrayLines[x] * 500);
            x++;
            int real_x2 = (int) (arrayLines[x] * 500);
            x++;
            int real_y2 = (int) (arrayLines[x] * 500);
            x++;


            g.drawLine(real_x1, real_y1, real_x2, real_y2);
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
                        filewrite.write(vecFile);
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
    }
}
