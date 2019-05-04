package GUI;

import java.awt.*;
import javax.swing.*;

public class NewDraftGUI extends JFrame {

    public NewDraftGUI() {
        JFrame frame = new JFrame("Painting Tool");
        frame.setBackground(Color.PINK);
        frame.setLayout(new BorderLayout(5, 5));

        // Menu bar
        JMenuBar menuBar = new JMenuBar();

        // File menu
        JMenu file = new JMenu("File");

        JMenuItem create = new JMenuItem("New");
        JMenuItem open = new JMenuItem("Open...");
        JMenuItem save = new JMenuItem("Save");
        JMenuItem saveAs = new JMenuItem("Save As...");
        JMenuItem exit = new JMenuItem("Exit");

        file.add(create);
        file.add(open);
        file.add(save);
        file.add(saveAs);
        file.addSeparator();
        file.add(exit);

        // Edit menu
        JMenu edit = new JMenu("Edit");
        JMenuItem undo = new JMenuItem("Undo");
        JMenuItem clear = new JMenuItem("Clear");

        edit.add(undo);
        edit.add(clear);

        // Add menus to menu bar
        menuBar.add(file);
        menuBar.add(edit);

        // Canvas
        JPanel canvas = new JPanel();
        canvas.setSize(200, 200);
        canvas.setBackground(Color.WHITE);

        // Main board
        JPanel board = new JPanel(new GridLayout(3, 1));

        // Shapes board
        JPanel shapes = new JPanel(new GridLayout(4,2));
        board.add(shapes);

        JButton toolPlot = new JButton("Plot");
        JButton toolLine = new JButton("Line");
        JButton toolRect = new JButton("Rectangle");
        JButton toolEllipse = new JButton("Ellipse");
        JButton toolPolygon = new JButton("Polygon");

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
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setPreferredSize(new Dimension(700, 600));
        frame.setLocation(new Point(100, 100));
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        JFrame.setDefaultLookAndFeelDecorated(true);
        new NewDraftGUI();
    }
}
