package Test;

import GUI.DrawCanvas;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DrawCanvasTest {

    @Test
    public void TestCanvas(){
        DrawCanvas Test = new DrawCanvas("Title", "VEC");
        assertEquals("Title", Test.getTitle());
    }

    @Test
    public void TestCanvasFileContents(){
        DrawCanvas Test = new DrawCanvas("Title", "VEC");
        assertEquals("VEC", Test.returnFile());
    }
}