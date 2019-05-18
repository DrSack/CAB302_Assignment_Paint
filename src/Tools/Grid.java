package Tools;

public class Grid {

    private int total;
    private int h;
    private int w;

    public void setSetting(int setting, int height, int width) {
        this.total = setting;
        this.h = height;
        this.w = width;
    }

    public int getSetting(){ return this.total; }
}
