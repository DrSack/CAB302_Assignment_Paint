package GUI;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.Timer;

/**
 * This is the GUI class which extends the JFrame, the point of this class is that it holds all
 * buttons and functions that the rest of the program will deliver. The DrawCanvas class is also called upon
 * within this class and that class extends a JPanel which will be used to draw the shapes.
 */
public class GUI extends JFrame implements ActionListener, KeyListener, ChangeListener, ComponentListener {

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
    private JButton gridButton;

    private JSlider gridSlider;
    private JLabel gridLabel;

    private javax.swing.Timer timer;

    /**
     * This is the constructor, the contents of the VEC file are passed through as a String, and the Title is also set.
     * The border itself is fixed, with a menu bar on the top, the buttons listed on the side, and the DrawCanvas class
     * is for the rest of the JFrame.
     *
     * @param title a Parameter that sets the Title of the JFrame based on the JFileChooser file source.
     */
    public GUI(String title) {
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setPreferredSize(new Dimension(840, 730));
        this.setMinimumSize(new Dimension(550,730));
        this.setLocation(new Point(250, 50));

        this.setTitle(title);
        this.setLayout(new BorderLayout(6, 0));

        canvasContainer = new JPanel(new GridLayout(1,1));
        canvasContainer.setBackground(Color.LIGHT_GRAY);
        canvasContainer.setSize(new Dimension(670, 670));

        canvas = new DrawCanvas();
        canvas.setBackground(Color.WHITE);
        canvas.setSize(new Dimension(670, 670));

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
        canvasContainer.add(canvas);

        // Display
        this.requestFocusInWindow();
        this.pack();
        this.setVisible(true);
        this.setFocusable(true);

        // Add listeners
        this.addKeyListener(this);
        this.addComponentListener(this);
    }

    /**
     * Add all tools to the shape JPanel.
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
     * as 2 color preview fields for those 2 buttons.
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
        outlineColor = new JButton("");
        fillColor = new JButton("");
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

    /**
     * Setup a JSlider to adjust the size of the Grid.
     */
    private void setupSize() {
        gridSlider = new JSlider(JSlider.VERTICAL, 50, 300, 100);
        gridSlider.setMajorTickSpacing(50);
        gridSlider.setValue(100);
        gridSlider.setSnapToTicks(true);
        gridSlider.setPaintTrack(true);
        gridSlider.setPaintTicks(true);
        gridSlider.setPaintLabels(true);

        gridButton = createButton("Grid");

        gridLabel = new JLabel();
        gridLabel.setText("Size (%)");

        gridSlider.addChangeListener(this);
    }

    /**
     * Setup all JPanels and their layouts.
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

        // Grid's button, slider and label
        gbc.weighty = 0.1;
        gbc.gridx = 0;
        gbc.gridy = 4;
        containerBoard.add(gridButton, gbc);

        gbc.weighty = 0.1;
        gbc.gridx = 0;
        gbc.gridy = 5;
        containerBoard.add(gridSlider, gbc);

        gbc.weighty = 0.1;
        gbc.gridx = 0;
        gbc.gridy = 6;
        containerBoard.add(gridLabel, gbc);
    }

    /**
     * Setup all colour buttons to make a color palette.
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
     * Create JMenuItem and return the object with action listener.
     *
     * @param title is a string that the user can the name of the JMenuItem.
     * @return the JMenuItem.
     */
    private JMenuItem createMenuItem(String title) {
        JMenuItem btn = new JMenuItem();
        btn.setText(title);
        btn.addActionListener(this);
        return btn;
    }

    /**
     * Create JButton and return the object with action listener.
     *
     * @param title is a string that the user can the name of the JButton.
     * @return the JButton.
     */
    private JButton createButton(String title) {
        JButton btn = new JButton();
        btn.setText(title);
        btn.addActionListener(this);
        return btn;
    }

    /**
     * Setup the buttons and associate the buttons created to the private variable JButtons.
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
     * Setup the MenuBar with 2 JMenus File and Edit and add their JMenuItems.
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
     * Set and create the Line or Plot object based on the parameters and DrawCanvas current draw settings.
     *
     * @param x1 is the x1 coordinate of the current VECFile line.
     * @param y1 is the y1 coordinate of the current VECFile line.
     * @param x2 is the x2 coordinate of the current VECFile line.
     * @param y2 is the y2 coordinate of the current VECFile line.
     */
    void parseLine(double x1, double y1, double x2, double y2) { canvas.SetCoordinateDrawingPlotting(x1,y1,x2,y2); }

    /**
     * Set and create the Rectangle object based on the parameters and DrawCanvas current draw settings.
     *
     * @param x1 is the x1 coordinate of the current VECFile line.
     * @param y1 is the y1 coordinate of the current VECFile line.
     * @param x2 is the x2 coordinate of the current VECFile line.
     * @param y2 is the y2 coordinate of the current VECFile line.
     */
    void parseRect(double x1, double y1, double x2, double y2) {
        canvas.SetCoordinateRectangle(x1,y1,x2,y2);
    }

    /**
     * Set and create the Ellipse object based on the parameters and DrawCanvas current draw settings.
     *
     * @param x1 is the x1 coordinate of the current VECFile line.
     * @param y1 is the y1 coordinate of the current VECFile line.
     * @param x2 is the x2 coordinate of the current VECFile line.
     * @param y2 is the y2 coordinate of the current VECFile line.
     */
    void parseEllipse(double x1, double y1, double x2, double y2) {
        canvas.SetCoordinateEllipse(x1,y1,x2,y2);
    }

    /**
     * Set and create the Polygon object based on the parameters and DrawCanvas current draw settings.
     *
     * @param xP The x coordinates of the polygon.
     * @param yP The y coordinates of the polygon.
     */
    void parsePolygon(double xP[], double yP[]) {
        canvas.SetCoordinatePolygon(xP, yP);
    }

    /**
     * Set the outline colour within the DrawCanvas class.
     *
     * @param colour The string value of the outline hex colour code from the current vecfile line.
     */
    void parseColour(String colour) {
        canvas.SetColour(colour);
    }

    /**
     * Set the fill colour within the DrawCanvas class.
     *
     * @param colour The string value of the fill hex colour code from the current vecfile line.
     */
    void parseFill(String colour){
            canvas.SetFill(colour);
    }

    /**
     * Turn off fill within the canvas
     */
    void parseFillOff() { canvas.offFill(); }

    /**
     * Set the outline colour of the shape that will be drawn.
     *
     * @param hex The string value of the hex colour code from the colour chosen by the user.
     */
    void ColourClick(String hex) { canvas.setColourClick(hex); }

    /**
     *  Set the visibility of the canvas
     * @param option Set the boolean value of setting whether the canvas is visible or not
     */
    void canvasVisible(Boolean option){ canvas.setVisible(option); }

    /**
     *  Repaint the Canvas
     */

    void canvasRepaint(){canvas.repaint();}
    /**
     * Set default drawCanvas drawing settings after opening a vec file.
     */
    void open() { canvas.open(); }

    /**
     * Call the undo function of the canvas.
     */
    void undo() { canvas.undo(); }

    /**
     * Returns drawingPoly for testing purposes.
     *
     * @return boolean value for drawing a polygon.
     */
    public boolean returnPoly() {
        return canvas.drawingPoly = true;
    }

    /**
     * Parse through each line of the vecFile and put it into an ArrayList within DrawCanvas.
     *
     * @param command The vecfile command line.
     */
     void readCommand(String command) { canvas.setOpenCoordinates(command); }

    /**
     * Set the fill colour of the canvas based on what the string value VECFile line.
     *
     * @param hex Pass through the vecfile hex colour string into the canvas fill method.
     */
    void FillClick(String hex) { canvas.setFillClick(hex); }

    /**
     * Reset the Foreground colour of the tool buttons.
     */
    void ToolColourReset() {
        toolPlot.setForeground(null);
        toolLine.setForeground(null);
        toolEllipse.setForeground(null);
        toolRect.setForeground(null);
        toolPolygon.setForeground(null);
    }

    /**
     * Return the vecFile string from the canvas class.
     *
     * @return The entire vecfile string.
     */
     String returnFile() {
        return canvas.returnFile();
    }

    /**
     * Calls undo and catches exceptions
     * @throws Exception The Nothing left to do exception message when an exception error occurs within undo()
     *          or Please finish drawing error message when the drawPoly boolean value is true.
     */
    public void CallUndo() throws Exception {
        if (!canvas.drawingPoly) {
            try {
                undo();
            } catch (Exception ex) {
                throw new Exception("Error: Nothing left to undo");
            }
        }
        else {
            throw new Exception("Error: Please finish drawing");
        }
    }

    /**
     * Calls clear and catches exceptions when drawing polygon.
     *
     * @throws Exception Polygon exception message.
     */
    public void DrawPolyClear() throws Exception{
        if (!canvas.drawingPoly) {
            canvas.clearCanvas();
            canvas.repaint();
        }
        else {
            throw new Exception("Error: Please finish drawing");
        }
    }

    /**
     * Used for nothing.
     */
    @Override
    public void keyTyped(KeyEvent e) {

    }

    /**
     * If Ctrl+Z is pressed run the undo method, if the method doesn't catch the exception then do operations
     * normally, but if it catches it then display an error message.
     *
     * @param e will be used to get the key code for both ctrl and z.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_Z) { // Can only ctrl+z when not drawing polygon
            try {
                CallUndo();
            }
            catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        }
    }

    /**
     * Used for nothing.
     */
    @Override
    public void keyReleased(KeyEvent e) {

    }

    /**
     * Handle resize events so that the canvas only resize after the user is done resizing the JFrame.
     */
    @Override
    public void componentResized(ComponentEvent e) {
        if (this.timer == null) { // Initiate timer and wait for delay
            this.timer = new Timer(200, this);
            this.timer.start();
        }
        else { // Restart the timer if there are still resizing events
            this.timer.restart();
        }
    }

    /**
     * Resize the size of the canvas based on the size of the JFrame window.
     */
    public void resizeCanvas() {
        Component c = this.getContentPane();

        // Set the size of the canvas based on the height of the window if the height of the window is changed
        canvas.setSize(new Dimension(c.getSize().height, c.getSize().height));

        // Set the size of the canvas based on the width of the window if the window's width + the toolbar's width is smaller than the window's height
        if (c.getWidth() - 100 <= (c.getHeight())) {
            canvas.setSize(new Dimension(c.getWidth() - 160, c.getWidth() - 160));
        }
    }

    /**
     * Used for nothing.
     */
    @Override
    public void componentMoved(ComponentEvent e) {

    }
    /**
     * Used for nothing.
     */
    @Override
    public void componentShown(ComponentEvent e) {

    }
    /**
     * Used for nothing.
     */
    @Override
    public void componentHidden(ComponentEvent e) {

    }
    /**
     * Used when the state of the grid is changed.
     */
    @Override
    public void stateChanged(ChangeEvent e) {
        this.requestFocusInWindow();
        double amount;
        if (canvas.t.isGridTruth()) {
            if (gridSlider.getValue()%50 == 0) {
                amount = (gridSlider.getValue()/100.0)*6.0;
                canvas.SetGrid(amount);
                canvas.repaint();
            }
            else if (gridSlider.getValue()%50 == 1) {
                amount =  (gridSlider.getValue()/100.0)*6.0;
                canvas.SetGrid(amount);
                canvas.repaint();
            }
        }
    }

    /**
     * Find the source of the button clicks from users to do different things.
     *
     * @param e this parameter is the ActionEvent that's detected from the users' mouse click.
     */
    public void actionPerformed(ActionEvent e) {
        this.requestFocusInWindow();
        Object btnSrc = e.getSource();

        // If timer is finished, stop and clear the timer and resize the canvas
        if (e.getSource() == this.timer) {
            this.timer.stop();
            this.timer = null;
            this.resizeCanvas();
        }

        if (btnSrc == gridButton) {
            if (!canvas.t.isGridTruth()) {
                double amount;
                gridButton.setForeground(Color.BLUE);
                canvas.t.setGridTruth();
                if (gridSlider.getValue()%50 == 0) {
                    amount = (double) (gridSlider.getValue()/100.0)*6.0;
                    canvas.SetGrid(amount);
                    canvas.repaint();
                }
                else if (gridSlider.getValue()%50 == 1) {
                    amount = (double) (gridSlider.getValue()/100.0)*6.0;
                    canvas.SetGrid(amount);
                    canvas.repaint();
                }
            }
            else {
                gridButton.setForeground(null);
                canvas.t.setGridFalse();
                canvas.repaint();
            }
        }

        if (btnSrc == undo) {
            try {
                CallUndo();
            }
            catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
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
            Color a = JColorChooser.showDialog(null, "Pick a color", c, false);

            // If the outline button is clicked, set the color of the pen and the color preview
            if (OutlineOrFill) {
                if (a != null) {
                    penC = "#" + Integer.toHexString(a.getRGB()).substring(2);
                    ColourClick(penC);
                    outlineColor.setBackground(a);
                }
            }

            // If the fill button is on, set the color of the pen and the color preview
            if (!OutlineOrFill) {
                if (a != null) {
                    fillC = "#" + Integer.toHexString(a.getRGB()).substring(2);
                    FillClick(fillC);
                    fillColor.setBackground(a);
                }
            }
        }

        if (btnSrc == exit) { // If exit is clicked dispose the current JFrame
            this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
            this.dispose();
        }

        if (btnSrc == clear) { // If clear is clicked on clear the canvas and repaint
            try {
                DrawPolyClear();
            }
            catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        }

        /*
         * With the following other if statements below, these set the boolean values of the each tool
         * to either true or false, whether a specific button is clicked
         */
        if (btnSrc == toolPlot) {
            if (!canvas.drawingPoly) {
                ToolColourReset();
                toolPlot.setForeground(Color.BLUE);
                canvas.t.resetTruth();
                canvas.t.setPlotTruth();
            }
            else {
                JOptionPane.showMessageDialog(null, "Error: Please finish drawing", "Error", JOptionPane.INFORMATION_MESSAGE);
            }
        }

        if (btnSrc == toolLine) {
            if (!canvas.drawingPoly) {
                ToolColourReset();
                toolLine.setForeground(Color.BLUE);
                canvas.t.resetTruth();
                canvas.t.setLineTruth();
            } else {
                JOptionPane.showMessageDialog(null, "Error: Please finish drawing", "Error", JOptionPane.INFORMATION_MESSAGE);
            }
        }

        if (btnSrc == toolRect) {
            if (!canvas.drawingPoly) {
                ToolColourReset();
                toolRect.setForeground(Color.BLUE);
                canvas.t.resetTruth();
                canvas.t.setRecTruth();
            }
            else {
                JOptionPane.showMessageDialog(null, "Error: Please finish drawing", "Error", JOptionPane.INFORMATION_MESSAGE);
            }
        }

        if (btnSrc == toolEllipse) {
            if (!canvas.drawingPoly) {
                ToolColourReset();
                toolEllipse.setForeground(Color.BLUE);
                canvas.t.resetTruth();
                canvas.t.setElliTruth();
            }
            else {
                JOptionPane.showMessageDialog(null, "Error: Please finish drawing", "Error", JOptionPane.INFORMATION_MESSAGE);
            }
        }

        if (btnSrc == toolPolygon) {
            ToolColourReset();
            toolPolygon.setForeground(Color.BLUE);
            canvas.t.resetTruth();
            canvas.t.setPolyTruth();
        }

        if (btnSrc == create) { // Create a new JFrame Window
            new GUI("untitled");
        }

        /*
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

            final String ext = ".VEC";//Extension
            String filePathWithoutExt = "";

            int value = fcSave.showSaveDialog(fcSave);
            if (value == JFileChooser.APPROVE_OPTION) { // Save button is clicked
                // If the files name contains a .VEC replace it with nothing this is to prevent a double .VEC.VEC file
                if (fcSave.getSelectedFile().getAbsolutePath().contains(ext)) {
                    filePathWithoutExt = fcSave.getSelectedFile().getAbsolutePath().replace(ext, "");
                }

                // If there is no established .VEC file set the file as is
                else {
                    filePathWithoutExt = fcSave.getSelectedFile().getAbsolutePath();
                }

                File file = new File(filePathWithoutExt + ext);

                // If the save button is pressed save the file followed with the file name inputted the .VEC extension
                if (file.exists()) { // If the file already exist pop up a confirm Dialog panel
                    value = JOptionPane.showConfirmDialog(this,
                            "Replace existing file?"); // Asks if the user wants to replace the file
                    if (value == JOptionPane.YES_OPTION) {
                        try { // if yes then replace the file with the current vecFile string
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
                    try { // If the file doesn't already exist, create it and write the file with the current vecFile string
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
            }

            // If cancel is selected on the JFileChooser do nothing and return to normal operations.
            else if (value == JFileChooser.CANCEL_OPTION) {

            }
        }

        /*
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
                try { // Give out an exception error if a file doesn't exist
                    if (file.exists()) { // If the file does exist
                        Parse Open = new Parse(file,fc.getSelectedFile().getAbsolutePath());
                        Open.Parsing();
                    }
                    else { // Give an error message if the file does not exist
                        Parse.NoFile();
                    }
                }
                catch (IOException e1) { // Catch IOException
                    e1.printStackTrace();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.INFORMATION_MESSAGE);
                }


            } else if (returnVal == JFileChooser.CANCEL_OPTION) {
                // Do nothing return to normal operations
            }
        }
    }

    /**
     * Main class to run GUI.
     *
     * @param args main argument parameter.
     */
    public static void main(String[] args) {
        new GUI("untitled");
    }


}