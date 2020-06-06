package sample;

import sample.Thread.NumberThread;

import java.util.ArrayList;

/**
 * Created by HoustoN
 * Date: 6/1/2020
 */
public class Matrix {
    private Tile[][] matrix;
    private ArrayList<String> toStop;
    private ArrayList<String> toRename;


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

    public synchronized boolean initThread(String Tname, int value, int Y, int X){
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
            NumberThread currentThread = (NumberThread) Thread.currentThread();

                    if (value == 0) {
                        if (matrix[toY][toX].getValue() % 2 == 0) {
                            NumberThread.getAllStackTraces().forEach(
                                    ((thread , stackTraceElements) -> {
                                        try{
                                            NumberThread numberThread = (NumberThread) thread;

                                            if(numberThread.getThreadName().equals(matrix[toY][toX].getThreadName())){
                                                numberThread.interrupt();
                                            }
                                        }catch (ClassCastException e){
//                                            System.out.println(e.getStackTrace().toString());
                                        }
                                    })
                            );

                            matrix[fromY][fromX].setValue(-1);
                            matrix[fromY][fromX].setThreadName(null);

                            matrix[toY][toX].setValue(value);
                            matrix[toY][toX].setThreadName(Tname);

                            currentThread.setCoordinates(toY, toX);



                            return true;
                        } else if (matrix[toY][toX].getValue() != -1) {
                            Tile tempTile = matrix[toY][toX];

                            matrix[fromY][fromX].setValue(tempTile.getValue() + 1);
                            matrix[fromY][fromX].setThreadName(tempTile.getThreadName());

                            matrix[toY][toX].setValue(value);
                            matrix[toY][toX].setThreadName(Tname);

                            NumberThread.getAllStackTraces().forEach(
                                    ((thread , stackTraceElements) -> {
                                        try{
                                        NumberThread numberThread = (NumberThread) thread;

                                        if(numberThread.getThreadName().equals(matrix[fromY][fromX].getThreadName())){
                                            numberThread.setValue(numberThread.getValue() + 1);
                                            numberThread.setCoordinates(fromY,fromX);
                                        }
                                        }catch (ClassCastException e){
//                                            System.out.println(e.getStackTrace());
                                        }
                                    })
                            );

                            currentThread.setCoordinates(toY, toX);

                            return true;
                        } else {
                            matrix[fromY][fromX].setValue(-1);
                            matrix[fromY][fromX].setThreadName(null);

                            matrix[toY][toX].setValue(value);
                            matrix[toY][toX].setThreadName(Tname);

                            currentThread.setCoordinates(toY, toX);

                            return true;
                        }
                    } else {
//                        another numbers

                        if (matrix[toY][toX].getValue() == -1) {
                            matrix[fromY][fromX].setValue(-1);
                            matrix[fromY][fromX].setThreadName(null);

                            matrix[toY][toX].setValue(value);
                            matrix[toY][toX].setThreadName(Tname);

                            currentThread.setCoordinates(toY, toX);

                            return true;
                        }
                    }

        }catch (ArrayIndexOutOfBoundsException e){}

        return false;
    }

    public void gggggg(int fromY, int fromX){
        Tile zero;
            for (Tile[] tiles : matrix) {
                for (Tile tile : tiles) {
                    if(tile.getValue() == 0)
                        zero = tile;
                }
            }

    }
}
