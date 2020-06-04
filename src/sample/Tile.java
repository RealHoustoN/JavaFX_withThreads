package sample;

/**
 * Created by HoustoN
 * Date: 6/4/2020
 */
public class Tile {
    private int value;
    private String threadName;
    private int coordY;
    private int coordX;

    public Tile(int value, String threadName, int coordY, int coordX) {
        this.value = value;
        this.threadName = threadName;
        this.coordY = coordY;
        this.coordX = coordX;
    }

    public int getValue() {
        return value;
    }

    public String getThreadName() {
        return threadName;
    }

    public int getCoordY() {
        return coordY;
    }

    public int getCoordX() {
        return coordX;
    }

    //    ================================================


    public void setValue(int value) {
        this.value = value;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }
}
