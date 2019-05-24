package GUI;

/**
 * An Encapsulation class for setting and getting all boolean values whether a specific tool has been activated
 */

public class TruthValues {

    //Set all boolean values to false by default.
    /**
     * Boolean set to truth when Plot tool is clicked
     */
    private boolean PlotTruth = false;
    /**
     * Boolean set to truth when Line tool is clicked
     */
    private boolean LineTruth = false;
    /**
     * Boolean set to truth when Rectangle tool is clicked
     */
    private boolean RecTruth = false;
    /**
     * Boolean set to truth when Ellipse tool is clicked
     */
    private boolean ElliTruth = false;
    /**
     * Boolean set to truth when Polygon tool is clicked
     */
    private boolean PolyTruth = false;
    /**
     * Boolean set to truth when Grid tool is clicked
     */
    private boolean GridTruth = false;

    /**
     * Returns ElliTruth value
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
     * Returns LineTruth value
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
     * Returns PlotTruth value
     * @return PlotTruth value
     */
    public boolean isPlotTruth() { return PlotTruth; }

    /**
     * Set PlotTruth to true
     */
    public void setPlotTruth() { PlotTruth = true; }

    /**
     * Returns PolyTruth value
     * @return PolyTruth value
     */
    public boolean isPolyTruth() { return PolyTruth; }

    /**
     * Set PolyTruth to true
     */
    public void setPolyTruth() { PolyTruth = true; }

    /**
     * Returns RecTruth value
     * @return RecTruth value
     */
    public boolean isRecTruth() { return RecTruth; }
    /**
     * Set RecTruth to true
     */
    public void setRecTruth() { RecTruth = true; }

    /**
     * Returns GridTruth value
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
