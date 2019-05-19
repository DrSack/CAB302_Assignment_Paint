package Test;

import Coordinate.MouseCoordinates;
import Coordinate.XY1;
import Coordinate.XY2;
import GUI.*;
import Tools.Grid;
import Tools.LineOrPlot;
import Tools.ShapesDrawn;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import java.awt.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

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
        Test.undo();
        assertEquals("", Test.returnFile());
        assertEquals(0, Test.returnCounter());
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
    void TestShapesResize() { // Test case for resizing the window and checking if the dimensions of the shape changes
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
    void TestGrid() { // Test coordinates
        Grid test = new Grid();
        test.setSetting(6,0,0);
        assertEquals(6,test.getSetting());
    }
}
