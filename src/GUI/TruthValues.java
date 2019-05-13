package GUI;

public class TruthValues {
    private boolean PlotTruth = false;
    private boolean LineTruth = false;
    private boolean RecTruth = false;
    private boolean ElliTruth = false;
    private boolean PolyTruth = false;

    public boolean isElliTruth() { return ElliTruth; }
    public void setElliTruth() {
        ElliTruth = true;
    }

    public boolean isLineTruth() { return LineTruth; }
    public void setLineTruth() {
        LineTruth = true;
    }

    public boolean isPlotTruth() { return PlotTruth; }
    public void setPlotTruth() {
        PlotTruth = true;
    }

    public boolean isPolyTruth() { return PolyTruth; }
    public void setPolyTruth() {
        PolyTruth = true;
    }

    public boolean isRecTruth() { return RecTruth; }
    public void setRecTruth() {
        RecTruth = true;
    }

    public void resetTruth(){
        PlotTruth = false;
        LineTruth = false;
        RecTruth = false;
        ElliTruth = false;
        PolyTruth = false;
    }
}
