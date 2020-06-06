package sample.Thread;

import sample.Matrix;

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

    public void setValue(int name) {
        this.value = name;
    }

    public void setCoordinates(int y, int x){
        this.startY = y;
        this.startX = x;
    }



    private void move(){

        while (true) {
            int z = (int)((Math.random()*5)+1);
            boolean bool = false;
                switch (z){
                    case 1:
                        bool = Tmatrix.change(startY, startX, value, name, startY + 1,startX);
                        break;
                    case 2:
                        bool = Tmatrix.change(startY, startX, value, name, startY - 1,startX);
                        break;
                    case 3:
                        bool = Tmatrix.change(startY, startX, value, name, startY,startX +1);
                        break;
                    case 4:
                        bool = Tmatrix.change(startY, startX, value, name, startY,startX -1);
                        break;
                    }
            if(bool)
                break;
            else {
                try {
                    Thread.sleep(ThreadLocalRandom.current().nextInt(500,1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.out.println(this.name + " Value: " + this.value);
                }
            }

        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                move();
                Thread.sleep(ThreadLocalRandom.current().nextInt(100,500));
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println(this.name + " Value: " + this.value);
        }

    }
}
