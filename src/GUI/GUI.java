package GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.util.*;

/**
This is the GUI class which extends the JFrame, the point of this class is that it holds all
 buttons and functions that the rest of the program will deliver. The DrawCanvas class is also called upon
 within this class and that class extends a JPanel which will be used to draw the shapes.
 *
 */
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
    private JButton outlineColor, fillColor;

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

    /**
     This is the constructor, the contents of the VEC file are passed through as a String, and the Title is also set.
     The border itself is fixed, with a menu bar on the top, the buttons listed on the side, and the DrawCanvas class
     is for the rest of the JFrame.
     * @param File is a string parameter that sets the vecFile string.
     * @param title a Parameter that sets the Title of the JFrame based on the JFileChooser file source.
     */
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
        setupShapes();
        setupTools();
        setupPanels();
        setupColors();

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

    /**
     * Add all tools to the shape JPanel
     */
    public void setupShapes() {
        shapes = new JPanel(new GridLayout(5,1));
        shapes.add(toolPlot);
        shapes.add(toolLine);
        shapes.add(toolRect);
        shapes.add(toolEllipse);
        shapes.add(toolPolygon);
    }

    /**
     * Setup the tool panel that contains the Fill and Outline tool buttons as well
     * as 2 color preview fields for those 2 buttons
     */
    public void setupTools() {
        tools = new JPanel(new GridBagLayout());
        GridBagConstraints tc = new GridBagConstraints();

        // Set font variable
        outline = createButton("Outline");
        fill = createButton("Fill");
        outline.setPreferredSize(new Dimension(80, 25));
        fill.setPreferredSize(new Dimension(80, 25));

        // Color preview
        outlineColor = new JButton();
        fillColor = new JButton();
        outlineColor.setEnabled(false);
        fillColor.setEnabled(false);
        outlineColor.setBackground(Color.BLACK);
        fillColor.setBackground(Color.WHITE);
        outlineColor.setPreferredSize(new Dimension(25, 25));
        fillColor.setPreferredSize(new Dimension(25, 25));

        Font f = outline.getFont();

        // Add buttons to the tools panel
        tc.gridy = 0;
        tc.gridx = 0;
        tools.add(outline, tc);

        tc.gridy = 0;
        tc.gridx = 1;
        tools.add(outlineColor, tc);
        outline.setFont(f.deriveFont(f.getStyle() | Font.BOLD));

        tc.gridy = 1;
        tc.gridx = 0;
        tools.add(fill, tc);

        tc.gridy = 1;
        tc.gridx = 1;
        tools.add(fillColor, tc);
    }

    /**
     * Setup all panels
     */
    public void setupPanels() {
        // Container board
        containerBoard = new JPanel(new GridBagLayout());
        containerBoard.setPreferredSize(new Dimension(150,600));
        GridBagConstraints gbc = new GridBagConstraints();

        // Shapes board
        gbc.weighty = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        containerBoard.add(shapes, gbc);

        // Tools board
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

    /**
     * Setup all colours
     */
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

    /**
     * Create JMenuItem and return the object with action listener
     * @param title is a string that the user can the name of the JMenuItem
     * @return the JMenuItem
     */
    private JMenuItem createMenuItem(String title) {
        JMenuItem btn = new JMenuItem();
        btn.setText(title);
        btn.addActionListener(this);
        return btn;
    }

    /**
     * Create JButton and return the object with action listener
     * @param title is a string that the user can the name of the JButton
     * @return the JButton
     */
    private JButton createButton(String title) {
        JButton btn = new JButton();
        btn.setText(title);
        btn.addActionListener(this);
        return btn;
    }

    /**
     * Setup the buttons and associate the buttons created to the private variable Jbuttons.
     */
    private void setupButtons() {
        toolPlot = createButton("Plot");
        toolLine = createButton("Line");
        toolRect = createButton("Rectangle");
        toolEllipse = createButton("Ellipse");
        toolPolygon = createButton("Polygon");
    }

    /**
     * Setup the buttons and associate the buttons created to the private variable JMenuItems.
     */
    private void setupMenuItemsFile() {
        create = createMenuItem("New");
        open = createMenuItem("Open...");
        saveAs = createMenuItem("Save As...");
        exit = createMenuItem("Exit");
    }

    /**
     * Setup the buttons and associate the buttons created to the private variable JMenuItems.
     */
    private void setupMenuItemsEdit() {
        undo = createMenuItem("Undo");
        clear = createMenuItem("Clear");
    }

    /**
     * Setup the MenuBar
     */
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

    /**
     *
     * @param x1 is the x1 coordinate of what the current VECfile line
     * @param y1 is the y1 coordinate of what the current VECfile line
     * @param x2 is the x2 coordinate of what the current VECfile line
     * @param y2 is the y2 coordinate of what the current VECfile line
     *
     * All the parse methods below pass through the
     * xy1 amd xy2 coordinates to the canvas class in which it will add it onto an array to keep track of the
     * shapes drawn,
     *
     * Fill and Colour click methods also have an array that keeps track of whether they have been
     * picked
     */

    private void parseLine(double x1, double y1, double x2, double y2) {
        canvas.SetCoordinateDrawingPlotting(x1,y1,x2,y2);
    }

    private void parseRect(double x1, double y1, double x2, double y2) {
        canvas.SetCoordinateRectangle(x1,y1,x2,y2);
    }

    private void parseEllipse(double x1, double y1, double x2, double y2) {
        canvas.SetCoordinateEllipse(x1,y1,x2,y2);
    }

    //This uses arrays where the vecFile x,y coordinates exceed past the typical 4.
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

    public String returnFile() { // Return the vecFile string from the canvas class.
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
            parseFillOff();
        }

        if (btnSrc == fill) { // Fill in Shape and set font to BOLD
            Font f = outline.getFont();
            Font f2 = fill.getFont();
            OutlineOrFill= false;
            fill.setFont(f.deriveFont(Font.BOLD));
            outline.setFont(f2.deriveFont(~Font.BOLD));
            FillClick(fillC);
        }

        for (JButton colorButton : colorButtons) { // For every button within the colorButtons array
            if (btnSrc == colorButton) { // If a particular button within that array is clicked
                c = colorButton.getBackground(); // get the colour of the background.

                // If the outline button is clicked, set the color of the pen and the color preview
                if (OutlineOrFill) {
                    penC = "#" + Integer.toHexString(c.getRGB()).substring(2);
                    ColourClick(penC);
                    outlineColor.setBackground(c);
                }

                // If the fill button is on, set the color of the pen and the color preview
                if (!OutlineOrFill) {
                    fillC = "#" + Integer.toHexString(c.getRGB()).substring(2);
                    FillClick(fillC);
                    fillColor.setBackground(c);
                }
            }
        }

        if (btnSrc == exit) { // If exit is clicked dispose the current JFrame
            this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
            this.dispose();
        }

        if (btnSrc == clear) { // If clear is clicked on clear the canvas and repaint.
            canvas.clearCanvas();
            canvas.repaint();
        }

        /**
         * With the following other if statements below, these set the boolean values of the each tool
         * to either true or false, whether a specific button is clicked.
         */

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

        if (btnSrc == create) { // Create a new JFrame Window.
            new GUI("","untitled");
        }

        /**
         * The saveAs button when pressed essentially opens up the JFileChooser, and based on where the location
         * you pick, you can set the name of the VEC file and it will pass through the string vecFile through
         * FileWriter and save it with the name you have given it + .VEC
         *
         */

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
                    try { // If the file doesn't already exist, create it and write the file with the current vecFile string.
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

        /**
         * The open button essentially reads the .VEC passed through and depeding on the text put forth,
         * this will function differently, depending on the commands given by the VEC file opened.
         *
         */

        if (btnSrc == open) {
            BufferedReader reader;
            BufferedReader readerT;
            BufferedReader readerChoose;

            final JFileChooser fc = new JFileChooser();
            fc.setCurrentDirectory( new File( "./") ); // Set Directory to its root directory

            fc.setAcceptAllFileFilterUsed(false);
            FileNameExtensionFilter filter = new FileNameExtensionFilter("VEC files", "VEC");
            fc.addChoosableFileFilter(filter); // Filter out all extensions except for .VEC

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
                        String getFile =""; // Declare empty string

                        int counter = 0; // Amount of lines count
                        int counterSTOP = 0; // Stop to notify the loop when to stop adding "\n"
                        while(VECfileTEST != null) { // This checks the amount of line the File has
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
                                // Parsing numbers into array
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
            } else if (returnVal==JFileChooser.CANCEL_OPTION) { // Do nothing/return to normal operations.

            }
        }
    }

    // Main class, run GUI.
    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        new GUI("", "untitled");
    }
}
