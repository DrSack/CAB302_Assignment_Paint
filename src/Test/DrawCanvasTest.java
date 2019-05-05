package Test;

import GUI.*;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class DrawCanvasTest extends JPanel {

    @Test
    public void TestCanvas(){
        GUI Test = new GUI("VEC", "Title");
        assertEquals("Title", Test.getTitle());
    }

    @Test
    public void TestCanvasFileContents(){
        GUI Test = new GUI("VEC", "Title");
        assertEquals("VEC", Test.returnFile());
    }

}
