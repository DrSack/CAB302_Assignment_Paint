package GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.util.*;

public class GUI extends JFrame implements ActionListener {
    private Color c = Color.black;

    private String penC = "#000000";
    private String fillC = "#FFFFFF";

    private boolean OutlineOrFill = true;
    private JMenuBar menuBar;
    private JMenu file, edit;
    private JMenuItem create, open, saveAs, exit, undo, clear;

    private JPanel containerBoard, shapes, tools;
    private DrawCanvas canvas;

    private JButton toolPlot, toolLine, toolRect, toolEllipse, toolPolygon;
    private JButton outline, fill;

    // Colors
    private JPanel colors = new JPanel();
    private ArrayList<JButton> colorButtons = new ArrayList<JButton>();
    private JButton black = new JButton();
    private JButton gray = new JButton();
    private JButton lightGray = new JButton();
    private JButton white = new JButton();
    private JButton blue = new JButton();
    private JButton cyan = new JButton();
    private JButton green = new JButton();
    private JButton yellow = new JButton();
    private JButton orange = new JButton();
    private JButton pink = new JButton();
    private JButton magenta = new JButton();
    private JButton red = new JButton();

    private String vecFile = "";

    public GUI(String File, String title) {
        this.setTitle(title);
        this.setLayout(new BorderLayout(5, 5));

        // Canvas
        vecFile = File;
        canvas = new DrawCanvas(vecFile);
        canvas.setBackground(Color.WHITE);
        canvas.setPreferredSize(new Dimension(600, 600));

        setupMenuBar();
        setupButtons();
        setupPanels();
        setupColors();
        setupShapes();

        // Add the components to the frame
        this.setJMenuBar(menuBar);
        this.add(containerBoard, BorderLayout.WEST);
        this.add(canvas, BorderLayout.CENTER);

        // Display window
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setPreferredSize(new Dimension(750, 600));
        this.setLocation(new Point(100, 100));
        this.pack();
        this.setVisible(true);
    }

    public void setupShapes() {
        shapes.add(toolPlot);
        shapes.add(toolLine);
        shapes.add(toolRect);
        shapes.add(toolEllipse);
        shapes.add(toolPolygon);
    }

    public void setupPanels() {
        // Container board
        containerBoard = new JPanel(new GridBagLayout());
        containerBoard.setPreferredSize(new Dimension(150,600));
        GridBagConstraints gbc = new GridBagConstraints();

        // Shapes board
        shapes = new JPanel(new GridLayout(5,1));
        gbc.weighty = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        containerBoard.add(shapes, gbc);

        // Tools board
        tools = new JPanel(new FlowLayout());

         //Set font variable.
        outline = createButton("Outline");
        fill = createButton("Fill");

        Font f = outline.getFont();

        // Set outline to Bold
        tools.add(outline);
        outline.setFont(f.deriveFont(f.getStyle() | Font.BOLD));
        tools.add(fill);

        gbc.weighty = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        containerBoard.add(tools, gbc);

        // Colors board
        gbc.weighty = 1;
        gbc.gridx = 0;
        gbc.gridy = 2;
        containerBoard.add(colors, gbc);
    }

    private void setupColors() {
        black.setBackground(Color.BLACK);
        gray.setBackground(Color.GRAY);
        lightGray.setBackground(Color.LIGHT_GRAY);
        white.setBackground(Color.WHITE);
        blue.setBackground(Color.BLUE);
        cyan.setBackground(Color.CYAN);
        green.setBackground(Color.GREEN);
        yellow.setBackground(Color.YELLOW);
        orange.setBackground(Color.ORANGE);
        pink.setBackground(Color.PINK);
        magenta.setBackground(Color.MAGENTA);
        red.setBackground(Color.RED);

        colorButtons.addAll(new ArrayList<JButton>(Arrays.asList(black, gray, lightGray, white,
                blue, cyan, green, yellow, orange, pink, magenta, red)));

        colors.setLayout(new GridLayout(3, 4));
        for (JButton colorButton : colorButtons) {
            colorButton.setPreferredSize(new Dimension(25, 25));
            colorButton.addActionListener(this);
            colors.add(colorButton);
        }
    }

    // Create Menu Item and return the object with action listener
    private JMenuItem createMenuItem(String title) {
        JMenuItem btn = new JMenuItem();
        btn.setText(title);
        btn.addActionListener(this);
        return btn;
    }

    // Create Button and return the object with action listener
    private JButton createButton(String title) {
        JButton btn = new JButton();
        btn.setText(title);
        btn.addActionListener(this);
        return btn;
    }

    private void setupButtons() {
        toolPlot = createButton("Plot");
        toolLine = createButton("Line");
        toolRect = createButton("Rectangle");
        toolEllipse = createButton("Ellipse");
        toolPolygon = createButton("Polygon");
    }

    private void setupMenuItemsFile() {
        create = createMenuItem("New");
        open = createMenuItem("Open...");
        saveAs = createMenuItem("Save As...");
        exit = createMenuItem("Exit");
    }

    private void setupMenuItemsEdit() {
        undo = createMenuItem("Undo");
        clear = createMenuItem("Clear");
    }

    private void setupMenuBar() {
        // Menu bar
        menuBar = new JMenuBar();

        // File menu
        file = new JMenu("File");
        setupMenuItemsFile();

        file.add(create);
        file.add(open);
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

    //Pass coordinates to the JPanel to Redraw the panel
    private void parseLine(double x1, double y1, double x2, double y2) {
        canvas.SetCoordinateDrawingPlotting(x1,y1,x2,y2);
    }

    private void parseRect(double x1, double y1, double x2, double y2) {
        canvas.SetCoordinateRectangle(x1,y1,x2,y2);
    }

    private void parseEllipse(double x1, double y1, double x2, double y2) {
        canvas.SetCoordinateEllipse(x1,y1,x2,y2);
    }

    private void parsePolygon(double xP[], double yP[]) {
        canvas.SetCoordinatePolygon(xP, yP);
    }

    private void parseColour(String colour) {
        canvas.SetColour(colour);
    }

    private void parseFill(String colour) { canvas.SetFill(colour); }

    private void parseFillOff() { canvas.offFill(); }

    private void ColourClick(String hex) { canvas.setColourClick(hex);}

    private void FillClick(String hex) {canvas.setFillClick(hex);}

    public String returnFile() {
        return canvas.returnFile();
    }

    public void actionPerformed(ActionEvent e) {
        Object btnSrc = e.getSource();

        if (btnSrc == outline) { // Change outline colour of Shape and set font to BOLD
            Font f = outline.getFont();
            Font f2 = fill.getFont();
            OutlineOrFill= true;
            outline.setFont(f.deriveFont(Font.BOLD));
            fill.setFont(f2.deriveFont(~Font.BOLD));
            ColourClick(penC);
        }

        if (btnSrc == fill) { // Fill in Shape and set font to BOLD
            Font f = outline.getFont();
            Font f2 = fill.getFont();
            OutlineOrFill= false;
            fill.setFont(f.deriveFont(Font.BOLD));
            outline.setFont(f2.deriveFont(~Font.BOLD));
            FillClick(fillC);
        }

        for (JButton colorButton : colorButtons) {
            if (btnSrc == colorButton) {
                c = colorButton.getBackground();

                if (OutlineOrFill) {
                    penC = "#" + Integer.toHexString(c.getRGB()).substring(2);
                    ColourClick(penC);
                }
                if (!OutlineOrFill) {
                    fillC = "#" + Integer.toHexString(c.getRGB()).substring(2);
                    FillClick(fillC);
                }
            }
        }

        if (btnSrc == create) {
            file.isVisible();
            file.repaint();
            file.setVisible(true);
        }

        if (btnSrc == exit) {
            this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
            this.dispose();
        }

        if (btnSrc == clear) {
            canvas.clearCanvas();
            canvas.repaint();
        }

        if (btnSrc == toolPlot) {
            canvas.PlotTruth = true;
            canvas.LineTruth = false;
            canvas.RecTruth = false;
            canvas.ElliTruth = false;
            canvas.PolyTruth = false;

        }

        if (btnSrc == toolLine) {
            canvas.PlotTruth = false;
            canvas.LineTruth = true;
            canvas.RecTruth = false;
            canvas.ElliTruth = false;
            canvas.PolyTruth = false;
        }

        if (btnSrc == toolRect) {
            canvas.PlotTruth = false;
            canvas.LineTruth = false;
            canvas.RecTruth = true;
            canvas.ElliTruth = false;
            canvas.PolyTruth = false;
        }

        if (btnSrc == toolEllipse){
            canvas.PlotTruth = false;
            canvas.LineTruth = false;
            canvas.RecTruth = false;
            canvas.ElliTruth = true;
            canvas.PolyTruth = false;
        }

        if(btnSrc == toolPolygon){
            canvas.PlotTruth = false;
            canvas.LineTruth = false;
            canvas.RecTruth = false;
            canvas.ElliTruth = false;
            canvas.PolyTruth = true;

        }

        if (btnSrc == create) {
            GUI NewWindow = new GUI("","untitled");
        }

        if (btnSrc == saveAs) { // If Save button is pressed
            final JFileChooser fcSave = new JFileChooser();
            fcSave.setCurrentDirectory(new File("./")); // Set Directory to the src of the Program

            fcSave.setAcceptAllFileFilterUsed(false); // Filter out extensions except for .VEC
            FileNameExtensionFilter filter = new FileNameExtensionFilter("VEC files", "VEC");
            fcSave.addChoosableFileFilter(filter);

            final String ext = ".VEC";
            String filePathWithoutExt = "";
            int value = fcSave.showSaveDialog(fcSave);
            if (value == JFileChooser.APPROVE_OPTION) { // Save button is clicked.
                // If the files name contains a .VEC replace it with nothing this is to prevent a double .VEC.VEC file
                if (fcSave.getSelectedFile().getAbsolutePath().contains(".VEC")) {
                    filePathWithoutExt = fcSave.getSelectedFile().getAbsolutePath().replace(".VEC", "");
                }

                // If there is no establish .VEC file set the file as is
                else {
                    filePathWithoutExt = fcSave.getSelectedFile().getAbsolutePath();
                }

                File file = new File(filePathWithoutExt + ".VEC");

                // If the save button is pressed save the file followed with the file name inputted the .VEC extension
                if (file.exists())
                { // If the file already exist pop up a confirm Dialog panel.
                    value = JOptionPane.showConfirmDialog(this,
                            "Replace existing file?"); // Asks if the user wants to replace the file.
                    if (value == JOptionPane.YES_OPTION) {
                        try { // if yes then replace the file with the current vecFile string.
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

                    if (value == JOptionPane.NO_OPTION) // If no then do nothing
                        return;
                }
                if (!file.exists()) {
                    try { // if the file doesn't already exist, create it and write the file with the current vecFile string.
                        FileWriter filewrite = new FileWriter(file);
                        filewrite.flush();
                        filewrite.write(returnFile());
                        filewrite.close();
                        file.createNewFile();
                        this.setTitle(fcSave.getSelectedFile().getAbsolutePath()+".VEC");
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }

            } // If cancel is selected on the JFileChooser do nothing and return to normal operations.
            else if (value == JFileChooser.CANCEL_OPTION) {

            }
        }

        if (btnSrc == open) {
            BufferedReader reader;
            BufferedReader readerT;
            BufferedReader readerChoose;

            final JFileChooser fc = new JFileChooser();
            fc.setCurrentDirectory( new File( "./") );

            fc.setAcceptAllFileFilterUsed(false);
            FileNameExtensionFilter filter = new FileNameExtensionFilter("VEC files", "VEC");
            fc.addChoosableFileFilter(filter);

            int returnVal = fc.showOpenDialog(this);
            if (returnVal==JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                try {
                    reader = new BufferedReader((new FileReader(file)));
                    readerT = new BufferedReader((new FileReader(file)));
                    readerChoose = new BufferedReader((new FileReader(file)));

                    String VECfile = readerChoose.readLine();
                    String VECfileTEST = readerT.readLine();
                    String data = reader.readLine();

                    if (file.exists()) {
                        double x1 = 0, y1 = 0, x2 = 0, y2 = 0;
                        String getFile ="";

                        int counter = 0;
                        int counterSTOP = 0;
                        while(VECfileTEST != null) {
                            VECfileTEST = readerT.readLine();
                            counter++;
                        }
                        while(VECfile != null) {
                            getFile += VECfile;
                            if (counterSTOP < counter-1) { // If the file isn't at the end add another line
                                getFile += "\n";
                            }
                            counterSTOP++;
                            VECfile = readerChoose.readLine();
                        }
                        System.out.println(getFile); // Parse file through constructor and open new JFrame.
                        GUI cool = new GUI(getFile+"\n",fc.getSelectedFile().getAbsolutePath());

                        if (file.length() == 0) {
                            canvas.setVisible(false);
                        }

                        while(data != null) {
                            if (data.contains("LINE")) {
                                // Replaces PLOT with nothing
                                data = data.replace("LINE ", "");

                                // Splits params into an array
                                String param[] = data.split(" ");
                                x1 = Double.parseDouble(param[0]);
                                y1 = Double.parseDouble(param[1]);
                                x2 = Double.parseDouble(param[2]);
                                y2 = Double.parseDouble(param[3]);
                                cool.parseLine(x1,y1,x2,y2);
                            }

                            if (data.contains("PLOT")) {
                                // Replaces PLOT with nothing
                                data = data.replace("PLOT ", "");

                                // Splits params into an array
                                String param[] = data.split(" ");
                                x1 = Double.parseDouble(param[0]);
                                y1 = Double.parseDouble(param[1]);
                                cool.parseLine(x1,y1,x1,y1);
                            }

                            if (data.contains("RECTANGLE")) {
                                // Replaces RECTANGLE with nothing
                                data = data.replace("RECTANGLE ", "");

                                // Splits params into an array
                                String param[] = data.split(" ");
                                x1 = Double.parseDouble(param[0]);
                                y1 = Double.parseDouble(param[1]);
                                x2 = Double.parseDouble(param[2]);
                                y2 = Double.parseDouble(param[3]);
                                cool.parseRect(x1,y1,x2,y2);
                            }

                            if (data.contains("ELLIPSE")) {
                                // Replaces ELLIPSE with nothing
                                data = data.replace("ELLIPSE ", "");

                                // Splits params into an array
                                String param[] = data.split(" ");
                                x1 = Double.parseDouble(param[0]);
                                y1 = Double.parseDouble(param[1]);
                                x2 = Double.parseDouble(param[2]);
                                y2 = Double.parseDouble(param[3]);
                                cool.parseEllipse(x1,y1,x2,y2);
                            }

                            if (data.contains("POLYGON")) {
                                int numbers;

                                // Replaces POLYGON with nothing
                                data = data.replace("POLYGON ", "");

                                // Splits params into an array
                                String param[] = data.split(" ");
                                numbers = param.length;

                                // Initializing xP and yP
                                double xP[] = new double[numbers / 2];
                                double yP[] = new double[numbers / 2];
                                //Parsing numbers into array
                                for (int i = 0; i < numbers / 2; i++) {
                                    xP[i] = Double.parseDouble(param[2 * i]);
                                    yP[i] = Double.parseDouble(param[2 * i + 1]);
                                }
                                cool.parsePolygon(xP, yP);
                            }

                            if (data.contains("PEN")) { // If the line contains pen
                                data = data.replace("PEN ", "");
                                cool.parseColour(data); // Set the colour on the JPanel
                            }

                            if (data.contains("FILL")) { // If the line contains Fill
                                data = data.replace("FILL ", "");
                                if (data.contains("OFF")) {
                                    System.out.println("OFF");
                                    cool.parseFillOff(); // Set the colour on the JPanel
                                }
                                else{
                                    System.out.println(data);
                                    cool.parseFill(data); // Set the colour on the JPanel
                                }
                            }
                            data = reader.readLine();
                        }

                        // Redraw the canvas and display shapes/lines.
                        canvas.revalidate();
                        canvas.repaint();
                        canvas.setVisible(true);
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            } else if (returnVal==JFileChooser.CANCEL_OPTION) {

            }
        }
    }

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        new GUI("", "untitled");
    }
}
