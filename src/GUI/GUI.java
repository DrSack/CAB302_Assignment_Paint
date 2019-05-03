package GUI;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class GUI extends JFrame implements ActionListener{

    private JPanel ButtonsWindow = new JPanel();
    private JPanel container = new JPanel();


    private JButton openFile, newFile;


    public GUI(String Title){
        super(Title);
        createAndShowGUI();
    }

    private void createAndShowGUI() {
        openFile = createButton("OPEN VEC");
        newFile = createButton("NEW VEC");

        setcoloursJPanel();
        setupButtons();
        JPanelContainer();

        this.setSize(375, 100);
        this.add(container);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private JButton createButton(String title){
        JButton btn = new JButton();
        btn.setText(title);
        btn.addActionListener(this);
        return  btn;
    }

    private void JPanelContainer(){
        container.setLayout(new GridLayout(1,0));
        container.add(ButtonsWindow);
    }

    private void setcoloursJPanel(){
        ButtonsWindow.setBackground(Color.GRAY);
        ButtonsWindow.setOpaque(true);
    }

    private void setupButtons(){
        newFile.setPreferredSize(new Dimension(100,25));
        openFile.setPreferredSize(new Dimension(100,25));
        ButtonsWindow.add(openFile);
        ButtonsWindow.add(newFile);

    }




    public void run(){
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object btnSrc = e.getSource();
        if(btnSrc == newFile) {
            DrawCanvas file = new DrawCanvas("Untitled", "");
        }

        if(btnSrc == openFile) {
            BufferedReader reader;
            BufferedReader readerT;
            BufferedReader readerChoose;


            final JFileChooser fc = new JFileChooser();
            fc.setCurrentDirectory( new File( "./") );

            fc.setAcceptAllFileFilterUsed(false);
            FileNameExtensionFilter filter = new FileNameExtensionFilter("VEC files", "VEC");
            fc.addChoosableFileFilter(filter);

            int returnVal = fc.showOpenDialog(this);
            if(returnVal==JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                String filename = file.getAbsolutePath();
                try {
                    reader = new BufferedReader((new FileReader(file)));
                    readerT = new BufferedReader((new FileReader(file)));
                    readerChoose = new BufferedReader((new FileReader(file)));

                    String VECfile = readerChoose.readLine();
                    String VECfileTEST = readerT.readLine();
                    String data = reader.readLine();

                    if(file.exists()){
                        double x1 = 0, y1 = 0, x2 = 0, y2;
                        int x[], y[];
                        String getFile ="";

                        int counter = 0;
                        int counterSTOP = 0;
                        while(VECfileTEST != null){
                            VECfileTEST = readerT.readLine();
                            counter++;
                        }
                        while(VECfile != null){
                            getFile += VECfile;
                            if(counterSTOP < counter-1){
                                getFile += "\n";
                            }
                            counterSTOP++;
                            VECfile = readerChoose.readLine();
                        }
                        System.out.println(getFile);
                        DrawCanvas cool = new DrawCanvas(filename, getFile);

                        if(file.length() == 0){
                            cool.setVisible(false);
                        }

                        while(data != null) {
                            if (data.contains("LINE")) {

                                data = data.replace("LINE ", "");
                                for(int i = 0; i < 3; i++){
                                    String arr[] = data.split(" ", 2);
                                    String firstWord = arr[0];
                                    String theRest = arr[1];
                                    if(i == 2){
                                        x2 = Double.parseDouble(firstWord);
                                    }
                                    if(i == 1){
                                        y1 = Double.parseDouble(firstWord);
                                    }
                                    if(i == 0){
                                        x1 = Double.parseDouble(firstWord);
                                    }
                                    data = theRest;
                                }
                                y2 = Float.parseFloat(data);

                                cool.SetCoordinateDrawingPlotting(x1,y1,x2,y2);

                                cool.repaint();
                                cool.revalidate();
                                cool.setVisible(true);
                            }

                            if (data.contains("PLOT")) {
                                data = data.replace("PLOT ", "");
                                for(int i = 0; i < 1; i++){
                                    String arr[] = data.split(" ", 2);
                                    String firstWord = arr[0];
                                    String theRest = arr[1];
                                    if(i == 0){
                                        x1 = Double.parseDouble(firstWord);
                                    }
                                    data = theRest;
                                }
                                y1 = Float.parseFloat(data);

                                cool.SetCoordinateDrawingPlotting(x1,y1,x1,y1);

                            }

                            if (data.contains("RECTANGLE")) {
                                data = data.replace("RECTANGLE ", "");
                                for(int i = 0; i < 3; i++){
                                    String arr[] = data.split(" ", 2);
                                    String firstWord = arr[0];
                                    String theRest = arr[1];
                                    if(i == 2){
                                        x2 = Double.parseDouble(firstWord);
                                    }
                                    if(i == 1){
                                        y1 = Double.parseDouble(firstWord);
                                    }
                                    if(i == 0){
                                        x1 = Double.parseDouble(firstWord);
                                    }
                                    data = theRest;
                                }
                                counter++;
                                y2 = Float.parseFloat(data);

                                cool.SetCoordinateRectangle(x1,y1,x2,y2);

                            }

                            if (data.contains("ELLIPSE")) {
                                data = data.replace("ELLIPSE ", "");
                                for(int i = 0; i < 3; i++){
                                    String arr[] = data.split(" ", 2);
                                    String firstWord = arr[0];
                                    String theRest = arr[1];
                                    if(i == 2){
                                        x2 = Double.parseDouble(firstWord);
                                    }
                                    if(i == 1){
                                        y1 = Double.parseDouble(firstWord);
                                    }
                                    if(i == 0){
                                        x1 = Double.parseDouble(firstWord);
                                    }
                                    data = theRest;
                                }
                                counter++;
                                y2 = Float.parseFloat(data);

                                cool.SetCoordinateEllipse(x1,y1,x2,y2);

                            }

                            data = reader.readLine();

                        }

                        cool.repaint();
                        cool.revalidate();
                        cool.setVisible(true);
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            } else if(returnVal==JFileChooser.CANCEL_OPTION) {

            }


        }


    }

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        GUI Tool = new GUI("Paint Tool");
        Tool.run();

    }

}
