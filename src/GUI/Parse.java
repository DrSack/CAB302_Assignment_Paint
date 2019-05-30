package GUI;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Parse {
    private GUI frame;
    private File file;
    private BufferedReader reader;

    /**Construct a new GUI and parse through vecfile commands.
     *
     * @param file The filechooser file
     * @param title The current directory of where the file was taken.
     */
    public Parse(File file, String title){
        frame = new GUI(title);
        this.file = file;
    }

    /**
     * Initialize the buffer reader in order to put through the data to pass vecfile commands
     * @throws Exception Catch unknown vecfile commands
     */
    public void Parsing() throws Exception {
        reader = new BufferedReader((new FileReader(file)));//Set buffer reader.
        String data = reader.readLine();// set data string to the current vecfile line
        Open(data);
    }

    /**
     * Call this method when there is no file.
     * @throws Exception Whenever no file is found call this exception
     */
    public static void NoFile() throws Exception{
        throw new Exception("Error: File not found");
    }

    /**
     * Parse through each vecfile command and set the canvas based on what was given
     * @param data The current vecfile line
     * @throws Exception Throw error messages based on unknown commands
     */
     public void Open(String data) throws Exception {
        int Line = 0;
         double x1, y1, x2, y2;// initialize coordiantes
        if (file.length() == 0) {
            this.frame.canvasVisible(false);
        }
        while (data != null) { // If there is data keep reading each line
            Line++;
            this.frame.readCommand(data+"\n");
            if (data.startsWith("LINE")) {
                data = data.replace("LINE ", ""); // Replaces LINE with nothing

                // Splits params into an array
                String param[] = data.split(" ");
                x1 = Double.parseDouble(param[0]);
                y1 = Double.parseDouble(param[1]);
                x2 = Double.parseDouble(param[2]);
                y2 = Double.parseDouble(param[3]);
                this.frame.parseLine(x1, y1, x2, y2);
            }

            else if (data.startsWith("PLOT")) {
                data = data.replace("PLOT ", ""); // Replaces PLOT with nothing

                // Splits params into an array
                String param[] = data.split(" ");
                x1 = Double.parseDouble(param[0]);
                y1 = Double.parseDouble(param[1]);
                this.frame.parseLine(x1, y1, x1, y1);
            }

            else if (data.startsWith("RECTANGLE")) {
                data = data.replace("RECTANGLE ", ""); // Replaces RECTANGLE with nothing

                // Splits params into an array
                String param[] = data.split(" ");
                x1 = Double.parseDouble(param[0]);
                y1 = Double.parseDouble(param[1]);
                x2 = Double.parseDouble(param[2]);
                y2 = Double.parseDouble(param[3]);
                this.frame.parseRect(x1, y1, x2, y2);
            }

            else if (data.startsWith("ELLIPSE")) {
                data = data.replace("ELLIPSE ", ""); // Replaces ELLIPSE with nothing

                // Splits params into an array
                String param[] = data.split(" ");
                x1 = Double.parseDouble(param[0]);
                y1 = Double.parseDouble(param[1]);
                x2 = Double.parseDouble(param[2]);
                y2 = Double.parseDouble(param[3]);
                this.frame.parseEllipse(x1, y1, x2, y2);
            }

            else if (data.startsWith("POLYGON")) {
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

                this.frame.parsePolygon(xP, yP);
            }

            else if (data.startsWith("PEN") || data.startsWith("pen")) { // If the line contains pen
                data = data.replace("PEN ", "");
                data = data.replace("pen ", "");
                if(data.contains("#")) {
                    this.frame.parseColour(data); // Set the colour on the JPanel
                }
                else{
                    this.frame.dispose();
                    throw new Exception("Error: Unknown pen command: '"+data+"' Line: "+Line);
                }
            }

            else if (data.startsWith("FILL") || data.startsWith("fill")) { // If the line contains Fill
                data = data.replace("FILL ", "");
                data = data.replace("fill ", "");

                if (data.contains("OFF")) {
                    this.frame.parseFillOff(); // Set the colour on the JPanel
                } else {
                    if(data.contains("#")){
                        this.frame.parseFill(data); // Set the colour on the JPanel
                    }
                    else{
                        this.frame.dispose();
                        throw new Exception("Error: Unknown fill command: '"+data+"' Line: "+Line);
                    }
                }
            }

            else if (data!=null) {
                this.frame.dispose(); // Close the window that has an error
                throw new Exception("Error: Unknown command: '"+data+"' Line: "+Line);


            }

            data = this.reader.readLine();
        }

        // Redraw the canvas and display shapes/lines
         this.frame.open();
         this.frame.canvasRepaint();
         this.frame.canvasVisible(true);
    }
}
