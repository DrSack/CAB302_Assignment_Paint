package GUI;

/**
 * An Encapsulation class for setting and getting all boolean values whether a specific tool has been activated
 */

public class TruthValues {

    //Set all boolean values to false by default.
    private boolean PlotTruth = false;
    private boolean LineTruth = false;
    private boolean RecTruth = false;
    private boolean ElliTruth = false;
    private boolean PolyTruth = false;
    private boolean GridTruth = false;

    /**
     * @return ElliTruth value
     */
    public boolean isElliTruth() { return ElliTruth; }

    /**
     * Set ElliTruth to true
     */
    public void setElliTruth() {
        ElliTruth = true;
    }

    /**
     * @return LineTruth value
     */
    public boolean isLineTruth() { return LineTruth; }

    /**
     * Set LineTruth to true
     */
    public void setLineTruth() {
        LineTruth = true;
    }

    /**
     * @return PlotTruth value
     */
    public boolean isPlotTruth() { return PlotTruth; }

    /**
     * Set PlotTruth to true
     */

    public void setPlotTruth() { PlotTruth = true; }

    /**
     * @return PolyTruth value
     */
    public boolean isPolyTruth() { return PolyTruth; }

    /**
     * Set PolyTruth to true
     */
    public void setPolyTruth() { PolyTruth = true; }

    /**
     * @return RecTruth value
     */
    public boolean isRecTruth() { return RecTruth; }
    /**
     * Set RecTruth to true
     */
    public void setRecTruth() { RecTruth = true; }

    /**
     * @return GridTruth value
     */

    public boolean isGridTruth() { return GridTruth; }

    /**
     * Set GridTruth to false
     */
    public void setGridFalse() { GridTruth = false; }

    /**
     * Set GridTruth to true
     */
    public void setGridTruth() { GridTruth = true; }

    /**
     * Reset all values apart from GridTruth to false.
     */
    public void resetTruth() {
        PlotTruth = false;
        LineTruth = false;
        RecTruth = false;
        ElliTruth = false;
        PolyTruth = false;
    }
}
