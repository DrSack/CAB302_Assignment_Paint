package GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class NewDraftGUI extends JFrame implements ActionListener {

    private JMenuBar menuBar;
    private JMenu file;
    private JMenuItem create, open, save, saveAs, exit;

    private DrawCanvas canvas;

    private JMenu edit;
    private JMenuItem undo, clear;

    private JButton toolPlot, toolLine, toolRect, toolEllipse, toolPolygon;

    private String vecFile = "";


    public NewDraftGUI(String File) {
        vecFile = File;
        JFrame frame = new JFrame("Painting Tool");
        frame.setBackground(Color.PINK);
        frame.setLayout(new BorderLayout(5, 5));

        //Add the menubar
        addMenuBar();

        // Canvas
        canvas = new DrawCanvas();
        canvas.setBackground(Color.WHITE);

        // Main board
        JPanel board = new JPanel(new GridLayout(3, 1));

        // Shapes board
        JPanel shapes = new JPanel(new GridLayout(4,2));
        board.add(shapes);

        setupButtons();

        shapes.add(toolPlot);
        shapes.add(toolLine);
        shapes.add(toolRect);
        shapes.add(toolEllipse);
        shapes.add(toolPolygon);

        // Tools board
        JPanel tools = new JPanel(new FlowLayout());
        board.add(tools);

        JButton outline = new JButton("Outline");
        JButton fill = new JButton("Fill");

        tools.add(outline);
        tools.add(fill);

        // Color chooser


        // Add the components to the frame
        frame.setJMenuBar(menuBar);
        frame.add(board, BorderLayout.WEST);
        frame.add(canvas, BorderLayout.CENTER);

        // Display window
        frame.setResizable(false);
        frame.setPreferredSize(new Dimension(700, 600));
        frame.setLocation(new Point(100, 100));
        frame.pack();
        frame.setVisible(true);
    }

    public JMenuItem createMenuItem(String title){//Create Menu Item and return the object with action listener
        JMenuItem btn = new JMenuItem();
        btn.setText(title);
        btn.addActionListener(this);
        return  btn;
    }

    public JButton createButton(String title){//Create Button and return the object with action listener
        JButton btn = new JButton();
        btn.setText(title);
        btn.addActionListener(this);
        return  btn;
    }

    public void setupButtons(){
        toolPlot = createButton("Plot");
        toolLine = createButton("Line");
        toolRect = createButton("Rectangle");
        toolEllipse = createButton("Ellipse");
        toolPolygon = createButton("Polygon");
    }

    public void setupMenuItemsFile(){
        create = createMenuItem("New");
        open = createMenuItem("Open...");
        save = createMenuItem("Save");
        saveAs = createMenuItem("Save As...");
        exit = createMenuItem("Exit");
    }

    public void setupMenuItemsEdit(){
        undo = createMenuItem("Undo");
        clear = createMenuItem("Clear");
    }

    public void addMenuBar(){
        // Menu bar
        menuBar = new JMenuBar();

        // File menu
        file = new JMenu("File");
        setupMenuItemsFile();

        file.add(create);
        file.add(open);
        file.add(save);
        file.add(saveAs);
        file.addSeparator();
        file.add(exit);

        // Edit menu
        edit = new JMenu("Edit");
        setupMenuItemsEdit();

        edit.add(undo);
        edit.add(clear);

        // Add menus to menu bar
        menuBar.add(file);
        menuBar.add(edit);
    }

    //Pass coordinates to the Jpanel to Redraw the panel
    public void parseLine(double x1, double y1, double x2, double y2){
        canvas.SetCoordinateDrawingPlotting(x1,y1,x2,y2);
        canvas.repaint();
    }

    public void parseRect(double x1, double y1, double x2, double y2){
        canvas.SetCoordinateRectangle(x1,y1,x2,y2);
        canvas.repaint();
    }

    public void parseEllipse(double x1, double y1, double x2, double y2){
        canvas.SetCoordinateEllipse(x1,y1,x2,y2);
        canvas.repaint();
    }

    public void actionPerformed(ActionEvent e) {
        Object btnSrc = e.getSource();

        if(btnSrc == create) {
            file.isVisible();
            file.repaint();
            file.setVisible(true);
        }

        if(btnSrc == open) {
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
                        double x1 = 0, y1 = 0, x2 = 0, y2 = 0;
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
                        NewDraftGUI cool = new NewDraftGUI(getFile);
                        if(file.length() == 0){
                            canvas.setVisible(false);
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

                                cool.parseLine(x1,y1,x2,y2);

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

                                cool.parseLine(x1,y1,x1,y1);

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

                                cool.parseRect(x1,y1,x2,y2);

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

                                cool.parseEllipse(x1,y1,x2,y2);

                            }

                            data = reader.readLine();

                        }


                        canvas.revalidate();
                        canvas.repaint();
                        canvas.setVisible(true);
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
        JFrame.setDefaultLookAndFeelDecorated(true);
        new NewDraftGUI("");
    }
}
