package sample.matrix;

/**
 * Created by HoustoN
 * Date: 6/4/2020
 */
public class Tile {
    private int value;
    private String threadName;
    private final int coordY;
    private final int coordX;

    public Tile(int value, String threadName, int coordY, int coordX) {
        this.value = value;
        this.threadName = threadName;
        this.coordY = coordY;
        this.coordX = coordX;
    }

    public Tile(Tile tile) {
        this.value = tile.getValue();
        this.threadName = tile.getThreadName();
        this.coordY = tile.getCoordY();
        this.coordX = tile.getCoordX();
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
