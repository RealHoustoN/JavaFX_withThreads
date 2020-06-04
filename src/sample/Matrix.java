package sample;

import sample.Thread.NumberThread;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by HoustoN
 * Date: 6/1/2020
 */
public class Matrix {
    private Tile[][] matrix;
    private ArrayList<String> toStop;
    private ArrayList<Tile> toRename;

    public Matrix(int x, int y) {
        this.matrix = new Tile[y][x];
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                matrix[i][j] = new Tile(-1, null, i, j);

            }
        }
        this.toStop = new ArrayList<>();
        this.toRename = new ArrayList<>();
    }

    public Tile[][] getMatrix() {
        return matrix;
    }

    public synchronized int getValue(int y, int x){
        return matrix[y][x].getValue();
    }

    public synchronized boolean initT(String Tname, int value, int Y, int X){
        try {
            if (matrix[Y][X].getValue() == -1) {
                matrix[Y][X].setValue(value);
                matrix[Y][X].setThreadName(Tname);
                return true;
            } else
                return false;
        }catch (ArrayIndexOutOfBoundsException e){
            return false;
        }
    }

    public synchronized boolean change(int fromY, int fromX, int value, String Tname, int toY, int toX){
        try {
            NumberThread numberCorrntThread = (NumberThread) Thread.currentThread();
                if(toStop.contains(Tname) && numberCorrntThread.getValue() != 0){
                    numberCorrntThread.interrupt();
                    System.out.println(numberCorrntThread.getThreadName());
                    return true;
                }else {
                    AtomicBoolean bool = new AtomicBoolean(false);
                    toRename.forEach(tile -> {
                        if(tile.getThreadName().equals(Tname)){
                            numberCorrntThread.setCoordinates(tile.getCoordY(),tile.getCoordX());
                            numberCorrntThread.setValue(tile.getValue());
                            bool.set(true);
                        }
                    });
                    if(bool.get()) {
                        Thread.yield();
                        return true;
                    }
                }
                    if (value == 0) {
                        if (matrix[toY][toX].getValue() % 2 == 0) {

                            toStop.add(matrix[toY][toX].getThreadName());

                            matrix[fromY][fromX].setValue(-1);
                            matrix[fromY][fromX].setThreadName(null);

                            matrix[toY][toX].setValue(value);
                            matrix[toY][toX].setThreadName(Tname);

                            numberCorrntThread.setCoordinates(toY,toX);
                            notify();
                            return true;
                        }else if(matrix[toY][toX].getValue() != -1){
                            Tile tempTile = matrix[toY][toX];

                            matrix[fromY][fromX].setValue(tempTile.getValue() + 1);
                            matrix[fromY][fromX].setThreadName(tempTile.getThreadName());

                            matrix[toY][toX].setValue(value);
                            matrix[toY][toX].setThreadName(Tname);

                            toRename.add(matrix[fromY][fromX]);

                            numberCorrntThread.setCoordinates(toY,toX);

                            notify();
                            return true;
                        }else {
                            matrix[fromY][fromX].setValue(-1);
                            matrix[fromY][fromX].setThreadName(null);

                            matrix[toY][toX].setValue(value);
                            matrix[toY][toX].setThreadName(Tname);

                            numberCorrntThread.setCoordinates(toY,toX);
                            notify();
                            return true;
                        }
                    } else {
                        if (matrix[toY][toX].getValue() == -1) {
                            matrix[fromY][fromX].setValue(-1);
                            matrix[fromY][fromX].setThreadName(null);

                            matrix[toY][toX].setValue(value);
                            matrix[toY][toX].setThreadName(Tname);

                            numberCorrntThread.setCoordinates(toY,toX);
                            notify();
                            return true;
                        }
                    }


        }catch (ArrayIndexOutOfBoundsException e){}
        notify();
        return false;
    }
}
