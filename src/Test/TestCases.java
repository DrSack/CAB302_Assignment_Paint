package Test;

import Coordinate.MouseCoordinates;
import Coordinate.XY1;
import Coordinate.XY2;
import GUI.*;
import Tools.*;
import org.junit.jupiter.api.Test;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This is the TestCases class which is used to test the whole program, and to check whether there are problems
 * that arise when implementing new features.
 */

class TestCases extends JPanel {

    /**
     * Tests the GUI title
     */
    @Test
    void TestGUITitle() { // Test if the title returns the same thing
        GUI Test = new GUI( "Title");
        assertEquals("Title", Test.getTitle());
    }

    /**
     * Test to see if 1 shapes have been drawn
     */
    @Test
    void TestCanvasCounterIncrement() {
        DrawCanvas Test = new DrawCanvas();
        Test.SetCoordinateDrawingPlotting(0.0,0.0,0.0,0.0);
        assertEquals(1, Test.returnCounter());
    }
    /**
     * Test to see if 2 shapes have been drawn
     */
    @Test
    void TestCanvasCounterIncrement2() { // Test to see if 2 shapes have been drawn
        DrawCanvas Test = new DrawCanvas();
        Test.SetCoordinateDrawingPlotting(0.0,0.0,1.0,1.0);
        Test.SetCoordinateRectangle(0.0,0.0,0.0,0.0);
        assertEquals(2,Test.returnCounter());
    }
    /**
     * Tests undo
     */
    @Test
    void TestCanvasUndo() { // Test if undo has deleted 1 line
        DrawCanvas Test = new DrawCanvas();
        Test.setOpenCoordinates("LINE 0.0 0.0 1.0 1.0");
        Test.SetCoordinateDrawingPlotting(0.0,0.0,1.0,1.0);
        assertEquals("LINE 0.0 0.0 1.0 1.0", Test.returnFile());
        Test.undo();
        assertEquals("", Test.returnFile());
        assertEquals(0, Test.returnCounter());
    }

    /**
     * Tests multiple undo's
     */
    @Test
    void TestCanvasMultipleUndo(){// Test for multiple undos
        DrawCanvas Test = new DrawCanvas();
        Test.setOpenCoordinates("ELLIPSE 0.0 0.0 1.0 1.0");
        Test.SetCoordinateEllipse(0.0, 0.0, 1.0, 1.0);
        Test.setOpenCoordinates("LINE 2.0 2.0 4.0 4.0");
        Test.SetCoordinateDrawingPlotting(2.0, 2.0, 4.0, 4.0);
        Test.setOpenCoordinates("RECTANGLE 3.0 3.0 2.0 2.0");
        Test.SetCoordinateRectangle(3.0, 3.0, 2.0, 2.0);
        assertEquals("ELLIPSE 0.0 0.0 1.0 1.0LINE 2.0 2.0 4.0 4.0RECTANGLE 3.0 3.0 2.0 2.0",
                Test.returnFile());
        Test.undo();
        Test.undo();
        assertEquals("ELLIPSE 0.0 0.0 1.0 1.0", Test.returnFile());
        Test.undo();
        assertEquals("", Test.returnFile());
    }

    /**
     * Tests undo with colour
     */
    @Test
    void TestCanvasUndoColour() { // Test if undo has deletes 2 Lines
        DrawCanvas Test = new DrawCanvas();
        Test.setOpenCoordinates("PEN #000000");
        Test.setOpenCoordinates("LINE 0.0 0.0 1.0 1.0");
        Test.SetCoordinateDrawingPlotting(0.0,0.0,1.0,1.0);
        Test.undo();
        assertEquals("", Test.returnFile());
        assertEquals(0, Test.returnCounter());
    }


    /**
     * Test undo with fill
     */
    @Test
    void TestCanvasUndoColourFill() { // Test if undo has deletes 3 Lines
        DrawCanvas Test = new DrawCanvas();
        Test.setOpenCoordinates("FILL #000000");
        Test.setOpenCoordinates("PEN #000000");
        Test.setOpenCoordinates("LINE 0.0 0.0 1.0 1.0");
        Test.SetCoordinateDrawingPlotting(0.0,0.0,1.0,1.0);
        Test.undo();
        assertEquals("", Test.returnFile());
        assertEquals(0, Test.returnCounter());
    }

    /**
     * Test undo with fill
     */
    @Test
    void TestCanvasUndo4Commands() { // Test undo fail safe to undo all commands even if there are still remaining.
        DrawCanvas Test = new DrawCanvas();
        Test.setOpenCoordinates("FILL #000000");
        Test.setOpenCoordinates("PEN #000000");
        Test.setOpenCoordinates("FILL OFF");
        Test.setOpenCoordinates("LINE 0.0 0.0 1.0 1.0");
        Test.SetCoordinateDrawingPlotting(0.0,0.0,1.0,1.0);
        Test.undo();
        assertEquals("", Test.returnFile());
        assertEquals(0, Test.returnCounter());
    }

    /**
     * Tests undo with no shapes, pops up error
     */
    @Test
    void TestUndoNoShapes(){
        GUI Undo = new GUI("untitled");
        Exception thrown = assertThrows(Exception.class, ()->{
            Undo.CallUndo();
        });
        assertEquals("Error: Nothing left to undo", thrown.getMessage());
    }

    /**
     * Tests undo with a shape and again after when there's nothing, pops up error
     */
    @Test
    void TestUndoNoShapes2(){
        GUI Undo = new GUI("untitled");
        DrawCanvas Test = new DrawCanvas();
        Test.setOpenCoordinates("ELLIPSE 0.0 0.0 1.0 1.0");
        Test.SetCoordinateEllipse(0.0, 0.0, 1.0, 1.0);
        Test.undo();
        Exception thrown = assertThrows(Exception.class, ()->{
            Undo.CallUndo();
        });
        assertEquals("Error: Nothing left to undo", thrown.getMessage());
    }

    /**
     * Tests Undo while drawing polygon, pops up error
     */
    @Test
    void TestUndoDrawingPoly(){
        DrawCanvas Test = new DrawCanvas();
        GUI Undo = new GUI("untitled");
        Undo.returnPoly();//Used to mimic drawing a polygon shape
        Test.setOpenCoordinates("ELLIPSE 0.0 0.0 1.0 1.0");
        Test.SetCoordinateEllipse(0.0, 0.0, 1.0, 1.0);
        Exception thrown = assertThrows(Exception.class, ()->{
            Undo.CallUndo();
        });
        assertEquals("Error: Please finish drawing", thrown.getMessage());
    }

    /**
     * Tests clear while drawing polygon, pops up error
     */
    @Test
    void TestClearDrawingPoly(){
        DrawCanvas Test = new DrawCanvas();
        GUI Undo = new GUI("untitled");
        Undo.returnPoly();//Used to mimic drawing a polygon shape
        Test.setOpenCoordinates("ELLIPSE 0.0 0.0 1.0 1.0");
        Test.SetCoordinateEllipse(0.0, 0.0, 1.0, 1.0);
        Exception thrown = assertThrows(Exception.class, ()->{
            Undo.DrawPolyClear();
        });
        assertEquals("Error: Please finish drawing", thrown.getMessage());
    }

    /**
     * Tests returning the vecFile
     */
    @Test
    void TestCanvasReturnVecFILE() { // Test to see if returnFile returns what is listed.
        DrawCanvas Test = new DrawCanvas();
        Test.setOpenCoordinates("PEN #000000");
        assertEquals("PEN #000000", Test.returnFile());
    }

    /**
     * Test coordinates and colours
     */
    @Test
    void TestCanvasColourShapes() { // Test coordinates
        DrawCanvas test = new DrawCanvas();
        test.SetColour("#00FF00");
        test.SetFill("#FF0000");
        test.SetCoordinateRectangle(0,0,300,300);
        assertEquals(Color.decode("#00FF00"), test.returnColour());
        assertEquals(Color.decode("#FF0000"),test.returnFill());
        assertTrue(test.returnFilltruth());
        assertEquals(Color.decode("#00FF00"),test.Draw.get(0).getPenC());
        assertEquals(Color.decode("#FF0000"),test.Draw.get(0).getFillC());
    }

    /**
     * Tests the Polygon shape tool
     */
    @Test
    void TestCanvasCoorPolygonAll() { // Test all for polygon
        DrawCanvas test = new DrawCanvas();
        test.setSize(600,600);
        double[] xp = {0, 0};
        double[] yp = {1, 0};
        int[] ixp = {(int) (xp[0] * 600), (int) (xp[1] * 600)};
        int[] iyp = {(int) (yp[0] * 600), (int) (yp[1] * 600)};
        test.SetColour("#00FF00");
        test.SetFill("#FF0000");
        test.SetCoordinatePolygon(xp,yp);
        assertEquals(Color.decode("#00FF00"), test.returnColour());
        assertEquals(Color.decode("#FF0000"),test.returnFill());
        assertTrue(test.returnFilltruth());
        assertTrue(test.Draw.get(0).getFill());
        test.offFill();
        assertFalse(test.returnFilltruth());
        assertTrue(test.Draw.get(0).getFill());// Will still be true as the object has been created
        assertEquals(Color.decode("#00FF00"),test.Draw.get(0).getPenC());//Rest click colour
        assertEquals(Color.decode("#FF0000"),test.Draw.get(0).getFillC());//Test fill colour
        assertArrayEquals(ixp, test.Draw.get(0).getXarray());//Return all x array coordinates
        assertArrayEquals(iyp, test.Draw.get(0).getYarray());//Return all y array coordiantes
        assertEquals(1, test.returnCounter());
        test.clearCanvas();
        assertEquals(0, test.returnCounter());
    }

    /**
     * Check if clearing the canvas clears resets all values
     */
    @Test
    void TestClearingCanvas() { //  Check if clearing the canvas clears resets all values
        DrawCanvas Test = new DrawCanvas();
        Test.setOpenCoordinates("RECTANGLE 1.0 1.0 1.0 1.0");
        Test.setFillClick("#FF00FF");
        Test.setColourClick("#FF00FF");
        Test.SetCoordinateRectangle(1.0,1.0,1.0,1.0);
        Test.SetCoordinateDrawingPlotting(0.0,0.0,1.0,1.0);
        Test.clearCanvas();
        assertEquals("", Test.returnFile());
        assertEquals(0, Test.returnCounter());
    }

    /**
     * Set colour of ShapesDrawn class and test to see if its the correct one
     */
    @Test
    void TestShapesColour() { // Set colour of ShapesDrawn class and test to see if its the correct one
        ArrayList<ShapesDrawn> Draw = new ArrayList<>();
        Draw.add(new LineOrPlot(1.0,1.0,1.0,1.0,300,300,true, Color.black,Color.WHITE));
        String hex = "#"+Integer.toHexString(Draw.get(0).getFillC().getRGB()).substring(2);
        String hex2 = "#"+Integer.toHexString(Draw.get(0).getPenC().getRGB()).substring(2);
        assertEquals("#000000", hex2.toUpperCase());
        assertEquals("#FFFFFF", hex.toUpperCase());
    }

    /**
     * Tests if all coordinates return the same value
     */
    @Test
    void TestShapesCoordinates() { // Test if all coordinates return the same value
        ArrayList<ShapesDrawn> Draw = new ArrayList<>();
        Draw.add(new LineOrPlot(1.0,1.0,1.0,1.0,300,300,true, Color.black,Color.WHITE));
        assertEquals(300,Draw.get(0).getX1());
        assertEquals(300,Draw.get(0).getY1());
        assertEquals(300,Draw.get(0).getX2());
        assertEquals(300,Draw.get(0).getY2());
    }

    /**
     * Tests if getFill returns true
     */
    @Test
    void TestShapesFillTruth() { // Test if getFill returns true
        ArrayList<ShapesDrawn> Draw = new ArrayList<>();
        Draw.add(new LineOrPlot(1.0,1.0,1.0,1.0,300,300,true, Color.black,Color.WHITE));
        assertTrue(Draw.get(0).getFill());
    }

    /**
     * Test case for resizing the window and checking if the dimensions of the shape changes and fits within the screen.
     */
    @Test
    void TestShapesResizeMaximum() { // Test case for resizing the window and checking if 1.0 x,y commands -1 to fit on screen.
        int w = 150;
        int h = 150;
        ArrayList<ShapesDrawn> Draw = new ArrayList<>();
        Draw.add(new LineOrPlot(1.0,1.0,1.0,1.0,300,300,true, Color.black,Color.WHITE));
        Draw.get(0).refit(w,h);
        assertEquals(w-1,Draw.get(0).getX1());
        assertEquals(h-1,Draw.get(0).getY1());
        assertEquals(w-1,Draw.get(0).getX2());
        assertEquals(h-1,Draw.get(0).getY2());
    }

    /**
     * Test case for resizing the window and checking if the dimensions of the shape changes and with normal dimensions
     */
    @Test
    void TestShapesResizeLine() { // Test case for resizing the window and checking for < 1.0 x,y commands
        int w = 150;
        int h = 150;
        ArrayList<ShapesDrawn> Draw = new ArrayList<>();
        Draw.add(new LineOrPlot(0.5,0.5,0.5,0.5,300,300,true, Color.black,Color.WHITE));
        Draw.get(0).refit(w,h);
        assertEquals(75,Draw.get(0).getX1());
        assertEquals(75,Draw.get(0).getY1());
        assertEquals(75,Draw.get(0).getX2());
        assertEquals(75,Draw.get(0).getY2());
    }

    /**
     * Test if setting all truth values to true are really true
     */
    @Test
    void TestTruthValues() { // Test if setting all truth values to true are really true
        TruthValues Test = new TruthValues();
        Test.setElliTruth();
        Test.setRecTruth();
        Test.setPlotTruth();
        Test.setLineTruth();
        Test.setPolyTruth();
        assertTrue(Test.isElliTruth());
        assertTrue(Test.isRecTruth());
        assertTrue(Test.isPlotTruth());
        assertTrue(Test.isLineTruth());
        assertTrue(Test.isPolyTruth());
    }
    /**
     * Test by checking if all truth values are false by setting all to true then reseting
     */
    @Test
    void TestTruthResetValues() { // Test by checking if all truth values are false by setting all to true then reseting
        TruthValues Test = new TruthValues();
        Test.setElliTruth();
        Test.setRecTruth();
        Test.setPlotTruth();
        Test.setLineTruth();
        Test.setPolyTruth();
        Test.resetTruth();
        assertFalse(Test.isElliTruth());
        assertFalse(Test.isRecTruth());
        assertFalse(Test.isPlotTruth());
        assertFalse(Test.isLineTruth());
        assertFalse(Test.isPolyTruth());
    }
    /**
     * Test coordinates
     */
    @Test
    void TestMouseCoor() { // Test coordinates
        int w = 300;
        int h = 300;
        MouseCoordinates Test1 = new XY1();
        MouseCoordinates Test2 = new XY2();
        Test1.setMouseXY(150,150,w,h);
        Test2.setMouseXY(150,150,w,h);
        assertEquals(0.5, Test1.getX());
        assertEquals(0.5, Test1.getY());
        assertEquals(0.5, Test2.getX());
        assertEquals(0.5, Test2.getY());
    }
    /**
     * Test mouse coordinates then place within the ShapesDrawn class and return its values
     */
    @Test
    void TestMouseCoorToDraw() { // Test mouse coordinates then place within the ShapesDrawn class and return its values
        int w = 300;
        int h = 300;
        MouseCoordinates Test1 = new XY1();
        MouseCoordinates Test2 = new XY2();
        ArrayList<ShapesDrawn> Draw = new ArrayList<>();
        Test1.setMouseXY(0,0,w,h);
        Test2.setMouseXY(250,250,w,h);
        Draw.add(new LineOrPlot(Test1.getX(), Test1.getY(), Test2.getX(), Test2.getY(), w, h, false, Color.BLACK, Color.white));
        assertEquals(0,Draw.get(0).getX1());
        assertEquals(0,Draw.get(0).getY1());
        assertEquals(250,Draw.get(0).getX2());
        assertEquals(250,Draw.get(0).getY2());
    }
    /**
     * Test Grid values
     */
    @Test
    void TestGrid() { // Test Grid values
        Grid test = new Grid();
        TruthValues test2 = new TruthValues();
        test.setSetting(6);
        test2.setGridTruth();
        assertEquals(6,test.getSetting());//Test if the setting number is the same
        assertTrue(test2.isGridTruth());//Test if true
        test2.setGridFalse();//turn false
        assertFalse(test2.isGridTruth());//Test if false
    }

    /**
     * Test parsing through a normal command
     */
    @Test
    void TestWorkingOpen() { // Test Grid values
        File file = new File("test");
        Parse test = new Parse(file, "Title");
        Exception thrown = assertThrows(Exception.class, ()->{
            test.Open("PEN #000000");
        });
        assertEquals(null, thrown.getMessage());
    }

    /**
     * Test parsing an incorrect pen command
     */
    @Test
    void TestFalsePenOpen() { // Test Grid values
        File file = new File("test");
        Parse test = new Parse(file, "Title");
        String error = "000000";
        Exception thrown = assertThrows(Exception.class, ()->{
            test.Open("PEN "+error);
        });
        assertEquals("Error: Unknown pen command: '"+error+"' Line: 1", thrown.getMessage());
    }

    /**
     * Test parsing an incorrect fill command
     */
    @Test
    void TestFalseFillOpen() { // Test Grid values
        File file = new File("test");
        Parse test = new Parse(file, "Title");
        String error = "000000";
        Exception thrown = assertThrows(Exception.class, ()->{
            test.Open("FILL "+error);
        });
        assertEquals("Error: Unknown fill command: '"+error+"' Line: 1", thrown.getMessage());
    }

    /**
     * Test parsing an unknown command
     */
    @Test
    void TestUnknownOpen() { // Test Grid values
        File file = new File("test");
        Parse test = new Parse(file, "Title");
        String error = "Nice";
        Exception thrown = assertThrows(Exception.class, ()->{
            test.Open(error);
        });
        assertEquals("Error: Unknown command: '"+error+"' Line: 1", thrown.getMessage());
    }

    /**
     * Test parsing nothing
     */
    @Test
    void TestOpenNothing() { // Test Grid values
        Exception thrown = assertThrows(Exception.class, ()->{
            Parse.NoFile();
        });
        assertEquals("Error: File not found", thrown.getMessage());
    }
}
