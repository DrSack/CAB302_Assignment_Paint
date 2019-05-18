package GUI;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.util.*;

/**
 * This is the GUI class which extends the JFrame, the point of this class is that it holds all
 * buttons and functions that the rest of the program will deliver. The DrawCanvas class is also called upon
 * within this class and that class extends a JPanel which will be used to draw the shapes.
 */
public class GUI extends JFrame implements ActionListener, KeyListener {
    private Color c = Color.BLACK;

    private String penC = "#000000";
    private String fillC = "#FFFFFF";

    private boolean OutlineOrFill = true;
    private JMenuBar menuBar;
    private JMenu file, edit;
    private JMenuItem create, open, saveAs, exit, undo, clear;

    private JPanel containerBoard, shapes, tools;
    private JPanel canvasContainer;
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
    private JButton extraColors;

    private JSlider sizeSlider;
    private JLabel sizeLabel;

    /**
     * This is the constructor, the contents of the VEC file are passed through as a String, and the Title is also set.
     * The border itself is fixed, with a menu bar on the top, the buttons listed on the side, and the DrawCanvas class
     * is for the rest of the JFrame.
     *
     * @param title a Parameter that sets the Title of the JFrame based on the JFileChooser file source.
     */
    public GUI(String title) {
        // Setup the JFrame
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setPreferredSize(new Dimension(810, 700));
        this.setMinimumSize(new Dimension(550,700));
        this.setLocation(new Point(100, 100));

        this.setTitle(title);
        this.setLayout(new BorderLayout(6, 0));

        canvasContainer = new JPanel(new BorderLayout());
        canvasContainer.setBackground(Color.LIGHT_GRAY);
        canvasContainer.setSize(new Dimension(640, 640));

        canvas = new DrawCanvas();
        canvas.setBackground(Color.WHITE);
        canvas.setSize(new Dimension(640, 640));

        // Setup components
        setupMenuBar();
        setupButtons();
        setupShapes();
        setupTools();
        setupSize();
        setupColors();
        setupPanels();

        // Add the components to the frame
        this.setJMenuBar(menuBar);
        this.add(containerBoard, BorderLayout.WEST);
        this.add(canvasContainer, BorderLayout.CENTER);
        canvasContainer.add(canvas, BorderLayout.CENTER);

        // Display
        this.requestFocusInWindow();
        this.pack();
        this.setVisible(true);
        this.setFocusable(true);
        detectResize();
        this.addKeyListener(this);
    }

    /**
     * Change the size of the canvas based on the size of the window
     */
    private void detectResize() {
        this.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                Component c = e.getComponent();

                // Set the size of the canvas based on the height of the window if the height of the window is changed
                canvas.setSize(new Dimension(c.getSize().height - 60, c.getSize().height - 60));

                // Set the size of the canvas based on the width of the window if the window's width + the toolbar's width is smaller than the window's height
                if (c.getWidth() - 100 <= (c.getHeight())) {
                    canvas.setSize(new Dimension(c.getWidth() - 170, c.getWidth() - 170));
                }

                System.out.println("Canvas: " + canvas.getWidth() + "x" + canvas.getHeight());
                System.out.println("Window: " + c.getWidth() + "x" + c.getHeight());
            }
        });
    }

    /**
     * Add all tools to the shape JPanel
     */
    private void setupShapes() {
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
    private void setupTools() {
        tools = new JPanel(new GridBagLayout());
        GridBagConstraints tc = new GridBagConstraints();

        // Tools buttons
        outline = createButton("Outline");
        outline.setForeground(Color.blue);
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

        // Add buttons to the tools panel
        tc.gridy = 0;
        tc.gridx = 0;
        tools.add(outline, tc);

        tc.gridy = 0;
        tc.gridx = 1;
        tools.add(outlineColor, tc);

        tc.gridy = 1;
        tc.gridx = 0;
        tools.add(fill, tc);

        tc.gridy = 1;
        tc.gridx = 1;
        tools.add(fillColor, tc);
    }

    // Setup a JSlider to adjust
    private void setupSize() {
        sizeSlider = new JSlider(JSlider.VERTICAL, 50, 300, 100);
        sizeSlider.setMajorTickSpacing(50);
        sizeSlider.setPaintTrack(true);
        sizeSlider.setPaintTicks(true);
        sizeSlider.setPaintLabels(true);

        sizeLabel = new JLabel();
        sizeLabel.setText("Size (%)");

        sizeSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) { }
        });
    }

    /**
     * Setup all JPanels and their layouts
     */
    private void setupPanels() {
        // Container board
        containerBoard = new JPanel(new GridBagLayout());
        containerBoard.setPreferredSize(new Dimension(150,600));
        GridBagConstraints gbc = new GridBagConstraints();

        // Shapes board
        gbc.weighty = 0.5;
        gbc.gridx = 0;
        gbc.gridy = 0;
        containerBoard.add(shapes, gbc);

        // Tools board
        gbc.weighty = 0.5;
        gbc.gridx = 0;
        gbc.gridy = 1;
        containerBoard.add(tools, gbc);

        // Colors board
        gbc.weighty = 1;
        gbc.gridx = 0;
        gbc.gridy = 2;
        containerBoard.add(colors, gbc);

        // Extra colors
        gbc.weighty = 0.1;
        gbc.gridx = 0;
        gbc.gridy = 3;
        containerBoard.add(extraColors, gbc);

        // Size slider and label
        gbc.weighty = 0.1;
        gbc.gridx = 0;
        gbc.gridy = 4;
        containerBoard.add(sizeSlider, gbc);

        gbc.weighty = 0.1;
        gbc.gridx = 0;
        gbc.gridy = 5;
        containerBoard.add(sizeLabel, gbc);
    }

    /**
     * Setup all colour buttons to make a color palette
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
        extraColors = createButton("More Colors...");
    }

    /**
     * Create JMenuItem and return the object with action listener
     *
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
     *
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
     * Setup the buttons and associate the buttons created to the private variable JButtons
     */
    private void setupButtons() {
        toolPlot = createButton("Plot");
        toolLine = createButton("Line");
        toolRect = createButton("Rectangle");
        toolEllipse = createButton("Ellipse");
        toolPolygon = createButton("Polygon");
    }

    /**
     * Setup the buttons and associate the buttons created to the private variable JMenuItems
     */
    private void setupMenuItemsFile() {
        create = createMenuItem("New");
        open = createMenuItem("Open...");
        saveAs = createMenuItem("Save As...");
        exit = createMenuItem("Exit");
    }

    /**
     * Setup the buttons and associate the buttons created to the private variable JMenuItems
     */
    private void setupMenuItemsEdit() {
        undo = createMenuItem("Undo");
        clear = createMenuItem("Clear");
    }

    /**
     * Setup the MenuBar with 2 JMenus File and Edit and add their JMenuItems
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
     * All the parse methods below pass through the
     * xy1 amd xy2 coordinates to the canvas class in which it will add it onto an array to keep track of the
     * shapes drawn,
     *
     * Fill and Colour click methods also have an array that keeps track of whether they have been
     * picked
     *
     * @param x1 is the x1 coordinate of what the current VECfile line
     * @param y1 is the y1 coordinate of what the current VECfile line
     * @param x2 is the x2 coordinate of what the current VECfile line
     * @param y2 is the y2 coordinate of what the current VECfile line
     */

    private void parseLine(double x1, double y1, double x2, double y2) { canvas.SetCoordinateDrawingPlotting(x1,y1,x2,y2); }

    private void parseRect(double x1, double y1, double x2, double y2) {
        canvas.SetCoordinateRectangle(x1,y1,x2,y2);
    }

    private void parseEllipse(double x1, double y1, double x2, double y2) {
        canvas.SetCoordinateEllipse(x1,y1,x2,y2);
    }

    // This uses arrays where the vecFile x,y coordinates exceed past the typical 4.
    private void parsePolygon(double xP[], double yP[]) {
        canvas.SetCoordinatePolygon(xP, yP);
    }

    private void parseColour(String colour) {
        canvas.SetColour(colour);
    }

    private void parseFill(String colour) { canvas.SetFill(colour); }

    private void parseFillOff() { canvas.offFill(); }

    private void ColourClick(String hex) { canvas.setColourClick(hex); }

    private void open() { canvas.open(); }

    private void undo() { canvas.undo(); }

    private void readCommand(String command) { canvas.setOpenCoordinates(command); }

    private void FillClick(String hex) { canvas.setFillClick(hex); }

    public String returnFile() { // Return the vecFile string from the canvas class.
        return canvas.returnFile();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    /**
     * If Ctrl+Z is pressed run the undo method, if the returnCounter of the canvas is above 0 then do operations
     * normally, but if it is below then display an error message.
     *
     * @param e will be used to get the key code for both ctrl and z
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_Z) {
            if (canvas.returnCounter() > 0) {
                undo();
            }
            else {
                JOptionPane.showMessageDialog(null, "Error: Nothing left to undo", "Empty", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    /**
     * Find the source of the button clicks from users to do different things
     *
     * @param e this parameter is the ActionEvent that's detected from the users' mouse click
     */
    public void actionPerformed(ActionEvent e) {
        this.requestFocusInWindow();
        Object btnSrc = e.getSource();

        if (btnSrc == undo) {
            if (canvas.returnCounter() > 0) {
                undo();
            }
            else {
                JOptionPane.showMessageDialog(null, "Error: Nothing left to undo", "Empty", JOptionPane.INFORMATION_MESSAGE);
            }
        }

        if (btnSrc == outline && !OutlineOrFill) { // Change outline colour of Shape
            OutlineOrFill= true;
            ColourClick(penC);
            parseFillOff();
            fill.setForeground(null);
            outline.setForeground(Color.BLUE);
        }

        if (btnSrc == fill) { // Fill in Shape
            OutlineOrFill= false;
            outline.setForeground(null);
            fill.setForeground(Color.BLUE);
            FillClick(fillC);
        }

        for (JButton colorButton : colorButtons) { // For every button within the colorButtons array
            if (btnSrc == colorButton) { // If a particular button within that array is clicked
                c = colorButton.getBackground(); // Get the colour of the background.

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

        if (btnSrc == extraColors) {
            c = JColorChooser.showDialog(null, "Pick a color", c);

            // Change the pen color back to white if the user doesn't pick anything or clicks cancel
            if (c == null) {
                c = Color.WHITE;
            }

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

        if (btnSrc == exit) { // If exit is clicked dispose the current JFrame
            this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
            this.dispose();
        }

        if (btnSrc == clear) { // If clear is clicked on clear the canvas and repaint.
            fill.setForeground(null);
            outline.setForeground(null);
            canvas.clearCanvas();
            canvas.repaint();
        }

        /**
         * With the following other if statements below, these set the boolean values of the each tool
         * to either true or false, whether a specific button is clicked.
         */
        if (btnSrc == toolPlot) {
            canvas.t.resetTruth();
            canvas.t.setPlotTruth();
        }

        if (btnSrc == toolLine) {
            canvas.t.resetTruth();
            canvas.t.setLineTruth();
        }

        if (btnSrc == toolRect) {
            canvas.t.resetTruth();
            canvas.t.setRecTruth();
        }

        if (btnSrc == toolEllipse) {
            canvas.t.resetTruth();
            canvas.t.setElliTruth();
        }

        if (btnSrc == toolPolygon) {
            canvas.t.resetTruth();
            canvas.t.setPolyTruth();
        }

        if (btnSrc == create) { // Create a new JFrame Window.
            new GUI("untitled");
        }

        /**
         * The saveAs button when pressed essentially opens up the JFileChooser, and based on where the location
         * you pick, you can set the name of the VEC file and it will pass through the string vecFile through
         * FileWriter and save it with the name you have given it + .VEC
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
         */
        if (btnSrc == open) {
            final JFileChooser fc = new JFileChooser();
            fc.setCurrentDirectory( new File( "./") ); // Set Directory to its root directory

            fc.setAcceptAllFileFilterUsed(false);
            FileNameExtensionFilter filter = new FileNameExtensionFilter("VEC files", "VEC");
            fc.addChoosableFileFilter(filter); // Filter out all extensions except for .VEC

            int returnVal = fc.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                try { // Give out an exception error if a file doesnt exist
                    if (file.exists()) { //If the file does exist
                        BufferedReader reader = new BufferedReader((new FileReader(file)));
                        BufferedReader readerT = new BufferedReader((new FileReader(file)));
                        BufferedReader readerChoose = new BufferedReader((new FileReader(file)));

                        String VECfile = readerChoose.readLine();
                        String VECfileTEST = readerT.readLine();
                        String data = reader.readLine();

                        double x1, y1, x2, y2;
                        int counter = 0; // Amount of lines count
                        int counterSTOP = 0; // Stop to notify the loop when to stop adding "\n"

                        while (VECfileTEST != null) { // This checks the amount of line the File has
                            VECfileTEST = readerT.readLine();
                            counter++;
                        }

                        GUI cool = new GUI(fc.getSelectedFile().getAbsolutePath());

                        while (VECfile != null) {
                            String hold = VECfile;
                            if (counterSTOP < counter) { // If the file isn't at the end add another line
                                hold += "\n";
                            }

                            cool.readCommand(hold);// Add command into arrayList
                            counterSTOP++;
                            VECfile = readerChoose.readLine();
                        }
                        if (file.length() == 0) {
                            canvas.setVisible(false);
                        }

                        while (data != null) { // If there is data keep reading each line
                            if (data.contains("LINE")) {
                                // Replaces PLOT with nothing
                                data = data.replace("LINE ", "");

                                // Splits params into an array
                                String param[] = data.split(" ");
                                x1 = Double.parseDouble(param[0]);
                                y1 = Double.parseDouble(param[1]);
                                x2 = Double.parseDouble(param[2]);
                                y2 = Double.parseDouble(param[3]);
                                cool.parseLine(x1, y1, x2, y2);
                            }

                            if (data.contains("PLOT")) {
                                // Replaces PLOT with nothing
                                data = data.replace("PLOT ", "");

                                // Splits params into an array
                                String param[] = data.split(" ");
                                x1 = Double.parseDouble(param[0]);
                                y1 = Double.parseDouble(param[1]);
                                cool.parseLine(x1, y1, x1, y1);
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
                                cool.parseRect(x1, y1, x2, y2);
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
                                cool.parseEllipse(x1, y1, x2, y2);
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

                            if (data.contains("PEN") || data.contains("pen")) { // If the line contains pen
                                data = data.replace("PEN ", "");
                                data = data.replace("pen ", "");
                                cool.parseColour(data); // Set the colour on the JPanel
                            }

                            if (data.contains("FILL") || data.contains("fiil")) { // If the line contains Fill
                                data = data.replace("FILL ", "");
                                data = data.replace("fill ", "");
                                if (data.contains("OFF")) {
                                    cool.parseFillOff(); // Set the colour on the JPanel
                                } else {
                                    cool.parseFill(data); // Set the colour on the JPanel
                                }
                            }
                            data = reader.readLine();
                        }

                        // Redraw the canvas and display shapes/lines.
                        cool.open();
                        canvas.repaint();
                        canvas.setVisible(true);
                    }

                    else { // Give an error message if the file does not exist.
                        JOptionPane.showMessageDialog(null, "Error: File not found", "Error", JOptionPane.INFORMATION_MESSAGE);
                    }

                } catch (IOException e1) { // Catch IOException
                    e1.printStackTrace();
                }
            } else if (returnVal == JFileChooser.CANCEL_OPTION) { // Do nothing return to normal operations.

            }
        }
    }

    // Main class, run GUI.
    public static void main(String[] args) {
        new GUI("untitled");
    }
}
