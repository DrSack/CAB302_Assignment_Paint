package Test;

import GUI.*;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

class DrawCanvasTest extends JPanel {

    @Test
    void TestCanvas(){
        GUI Test = new GUI("VEC", "Title");
        assertEquals("Title", Test.getTitle());
    }

    @Test
    void TestCanvasFileContents(){
        GUI Test = new GUI("VEC", "Title");
        assertEquals("VEC", Test.returnFile());
    }

    @Test
    void TestCanvasCounterIncrement(){
        DrawCanvas Test = new DrawCanvas("");
        Test.SetCoordinateDrawingPlotting(0.0,0.0,1.0,1.0);
        assertEquals(1,Test.returnCounter());
    }
}
