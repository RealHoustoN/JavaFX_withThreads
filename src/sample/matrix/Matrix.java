package sample.matrix;

import sample.enums.Direction;
import sample.thread.NumberThread;

/**
 * Created by HoustoN
 * Date: 6/1/2020
 */
public class Matrix {
    private Tile[][] matrix;
    private final int RADIUS = 8;


    public Matrix(int x, int y) {
        this.matrix = new Tile[y][x];
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                matrix[i][j] = new Tile(-1, null, i, j);

            }
        }
    }



    public Tile[][] getMatrix() {
        return matrix;
    }

    public synchronized int getValue(int y, int x){
        return matrix[y][x].getValue();
    }

    public synchronized boolean initThread(String ThreadName, int value, int Y, int X){
        try {
            if (matrix[Y][X].getValue() == -1) {
                matrix[Y][X].setValue(value);
                matrix[Y][X].setThreadName(ThreadName);
                return true;
            } else
                return false;
        }catch (ArrayIndexOutOfBoundsException e){
            return false;
        }
    }

    public synchronized boolean change(int fromY, int fromX, Direction direction){
        try {
            NumberThread currentThread = (NumberThread) Thread.currentThread();
            Tile targetTile = findSimpleTargetTile(matrix[fromY][fromX], direction);
            Tile startTile = matrix[fromY][fromX];
            if (targetTile != null) {
                if (currentThread.getValue() == 0) {
                    if (targetTile.getValue() % 2 == 0) {
//                            here i kill another thread
                        NumberThread.getAllStackTraces().forEach(
                                ((thread, stackTraceElements) -> {
                                    try {
                                        NumberThread numberThread = (NumberThread) thread;

                                        if (numberThread.getThreadName().equals(targetTile.getThreadName())) {
                                            numberThread.interrupt();
                                        }
                                    } catch (ClassCastException e) {
                                    }
                                })
                        );

                        makeSimpleMove(startTile, targetTile, currentThread);
                        return true;

                    } else if (targetTile.getValue() != -1) {
//                            here i change thread value ( + 1 )

                        Tile tempTile = new Tile(targetTile);

                        startTile.setValue(tempTile.getValue() + 1);
                        startTile.setThreadName(tempTile.getThreadName());

                        targetTile.setValue(currentThread.getValue());
                        targetTile.setThreadName(currentThread.getThreadName());
                        currentThread.setCoordinates(targetTile.getCoordY(), targetTile.getCoordX());

                        NumberThread.getAllStackTraces().forEach(
                                ((thread, stackTraceElements) -> {
                                    try {
                                        NumberThread numberThread = (NumberThread) thread;

                                        if (numberThread.getThreadName().equals(startTile.getThreadName())) {
                                            numberThread.setValue(numberThread.getValue() + 1);
                                            numberThread.setCoordinates(startTile.getCoordY(), startTile.getCoordX());
                                        }
                                    } catch (ClassCastException e) {
                                    }
                                })
                        );
                        return true;
                    } else {
//                            if targetTile is empty
                        makeSimpleMove(startTile, targetTile, currentThread);
                        return true;
                    }
                } else {
//                        another numbers
                    Tile zero = findZero();

                    if(zero != null){
                        if(isSeeZero(zero,startTile)){
                            if(isCorrectDirection(zero,startTile,targetTile)){
                                if(targetTile.getValue() == -1) {
                                    makeSimpleMove(startTile, targetTile, currentThread);
                                    return true;
                                }
                            }
                        }
                    }
                    else if (targetTile.getValue() == -1) {
                        makeSimpleMove(startTile, targetTile, currentThread);
                        return true;
                    }
                }
            }
        }catch(ArrayIndexOutOfBoundsException e){}
        return false;
    }

    private Tile findZero(){
        Tile zero = null;
            for (Tile[] tiles : matrix) {
                for (Tile tile : tiles) {
                    if(tile.getValue() == 0)
                        zero = tile;
                }
            }
        return zero;
    }

    private void makeSimpleMove(Tile start, Tile end, NumberThread numberThread){
            start.setThreadName(null);
            start.setValue(-1);

            end.setThreadName(numberThread.getThreadName());
            end.setValue(numberThread.getValue());
            numberThread.setCoordinates(end.getCoordY(),end.getCoordX());

    }

    private Tile findSimpleTargetTile(Tile start, Direction direction){
        Tile tempTile = null;
        try {
            switch (direction){
                case NORTH:
//                    y - 1
                    tempTile = matrix[start.getCoordY() - 1][start.getCoordX()];
                    break;
                case SOUTH:
//                    y + 1
                    tempTile = matrix[start.getCoordY() + 1][start.getCoordX()];
                    break;
                case WEST:
//                    x - 1
                    tempTile = matrix[start.getCoordY()][start.getCoordX() - 1];
                    break;
                case EAST:
//                    x + 1
                    tempTile = matrix[start.getCoordY()][start.getCoordX() + 1];
                    break;
            }

        }catch (ArrayIndexOutOfBoundsException e){

        }
        return tempTile;
    }

    private boolean isSeeZero(Tile zero, Tile start){
        int y = Math.abs(zero.getCoordY() - start.getCoordY());
        int x = Math.abs(zero.getCoordX() - start.getCoordX());
        return y <= RADIUS && x <= RADIUS;
    }

    private boolean isCorrectDirection(Tile zero, Tile start, Tile end){
        int startY = Math.abs(zero.getCoordY() - start.getCoordY());
        int startX = Math.abs(zero.getCoordX() - start.getCoordX());
        int endY = Math.abs(zero.getCoordY() - end.getCoordY());
        int endX = Math.abs(zero.getCoordX() - end.getCoordX());
        return endY < startY || endX < startX;
    }

}
