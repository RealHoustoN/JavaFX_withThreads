package sample.thread;

import sample.enums.Direction;
import sample.matrix.Matrix;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by HoustoN
 * Date: 6/3/2020
 */
public class NumberThread extends Thread {
    private String name;
    private int value;
    private int startX;
    private int startY;
    private Matrix Tmatrix;


    public NumberThread(int name, Matrix matrix) {
        this.name = "Tread-10"+name;
        this.value = name;
        this.Tmatrix = matrix;
    }

    public void initialization(){
        while (true){
            this.startX = (int)(Math.random()*13);
            this.startY = (int)(Math.random()*13);
            boolean b = false;
            b = Tmatrix.initThread(this.name, value, startY, startX);
            if(b)
//                System.out.println(this.name);
                break;
        }
    }



    public String getThreadName() {
        return name;
    }

    public int getValue() {
        return value;
    }

//    =======================================================

    public void setValue(int name) {
        this.value = name;
    }

    public void setCoordinates(int y, int x){
        this.startY = y;
        this.startX = x;
    }



    private void move(){

        while (true) {
            Direction direction = Direction.values()[new Random().nextInt(Direction.values().length)];

            if(Tmatrix.change(this.startY, this.startX, direction))
                break;
            else {
                try {
                    Thread.sleep(ThreadLocalRandom.current().nextInt(500,1000));
                } catch (InterruptedException e) {
//                    e.printStackTrace();
                    System.out.println("Oh, zero killed me, i was so young....");
                    System.out.println(this.name + " Value: " + this.value);
//                    fatality if he still alive
                    Thread.currentThread().stop();

                }
            }

        }
    }

    @Override
    public synchronized void start() {
        if(this.value == 0){
            try {
                Thread.sleep(500);
                System.out.println("I am a big evil and I wake up");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        super.start();
    }

    @Override
    public void run() {
        try {
            while (true) {
                move();
                Thread.sleep(ThreadLocalRandom.current().nextInt(100,500));
            }

        } catch (InterruptedException e) {
//            e.printStackTrace();
            System.out.println("its a duplicate");
            System.out.println(this.name + " Value: " + this.value);
//            fatality if he still alive
            Thread.currentThread().stop();
        }

    }
}
