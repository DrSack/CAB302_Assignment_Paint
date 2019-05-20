package Test;

import Coordinate.MouseCoordinates;
import Coordinate.XY1;
import Coordinate.XY2;
import GUI.*;
import Tools.Grid;
import Tools.LineOrPlot;
import Tools.Rectangle;
import Tools.ShapesDrawn;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import java.awt.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This is the TestCases class which is used to test the whole program, and to check whether there are problems
 * that arise when implementing new features.
 */

class TestCases extends JPanel {

    @Test
    void TestGUITitle() { // Test if the title returns the same thing
        GUI Test = new GUI( "Title");
        assertEquals("Title", Test.getTitle());
    }

    @Test
    void TestCanvasCounterIncrement() {
        DrawCanvas Test = new DrawCanvas();
        Test.SetCoordinateDrawingPlotting(0.0,0.0,0.0,0.0);
        assertEquals(1, Test.returnCounter());
    }

    @Test
    void TestCanvasCounterIncrement2() { // Test to see if 2 shapes have been drawn
        DrawCanvas Test = new DrawCanvas();
        Test.SetCoordinateDrawingPlotting(0.0,0.0,1.0,1.0);
        Test.SetCoordinateRectangle(0.0,0.0,0.0,0.0);
        assertEquals(2,Test.returnCounter());
    }

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

    @Test
    void TestCanvasReturnVecFILE() { // Test to see if returnFile returns what is listed.
        DrawCanvas Test = new DrawCanvas();
        Test.setOpenCoordinates("PEN #000000");
        assertEquals("PEN #000000", Test.returnFile());
    }

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

    @Test
    void TestShapesColour() { // Set colour of ShapesDrawn class and test to see if its the correct one
        ArrayList<ShapesDrawn> Draw = new ArrayList<>();
        Draw.add(new LineOrPlot(1.0,1.0,1.0,1.0,300,300,true, Color.black,Color.WHITE));
        String hex = "#"+Integer.toHexString(Draw.get(0).getFillC().getRGB()).substring(2);
        String hex2 = "#"+Integer.toHexString(Draw.get(0).getPenC().getRGB()).substring(2);
        assertEquals("#000000", hex2.toUpperCase());
        assertEquals("#FFFFFF", hex.toUpperCase());
    }

    @Test
    void TestShapesCoordinates() { // Test if all coordinates return the same value
        ArrayList<ShapesDrawn> Draw = new ArrayList<>();
        Draw.add(new LineOrPlot(1.0,1.0,1.0,1.0,300,300,true, Color.black,Color.WHITE));
        assertEquals(300,Draw.get(0).getX1());
        assertEquals(300,Draw.get(0).getY1());
        assertEquals(300,Draw.get(0).getX2());
        assertEquals(300,Draw.get(0).getY2());
    }

    @Test
    void TestShapesFillTruth() { // Test if getFill returns true
        ArrayList<ShapesDrawn> Draw = new ArrayList<>();
        Draw.add(new LineOrPlot(1.0,1.0,1.0,1.0,300,300,true, Color.black,Color.WHITE));
        assertTrue(Draw.get(0).getFill());
    }

    @Test
    void TestShapesResizeLine() { // Test case for resizing the window and checking if the dimensions of the shape changes
        int w = 150;
        int h = 150;
        ArrayList<ShapesDrawn> Draw = new ArrayList<>();
        Draw.add(new LineOrPlot(1.0,1.0,1.0,1.0,300,300,true, Color.black,Color.WHITE));
        Draw.get(0).resize(w,h);
        assertEquals(w,Draw.get(0).getX1());
        assertEquals(h,Draw.get(0).getY1());
        assertEquals(w,Draw.get(0).getX2());
        assertEquals(h,Draw.get(0).getY2());
    }
    

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
}
