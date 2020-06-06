package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import sample.Thread.NumberThread;

public class Main extends Application {

    public void start(Stage primaryStage) throws Exception
    {

        final int size = 12;
        final int length = size;
        final int width = size;
        Matrix matrix = new Matrix(length, width);
        GridPane root = new GridPane();
        draw(length, width, root, matrix);
        for (int i = 0; i < 31; i++) {
            NumberThread thread1 = new NumberThread(i, matrix);
            if(i==0){
                thread1.setDaemon(true);
                thread1.setPriority(8);
            }
            thread1.initialization();
            thread1.start();
        }


        Scene scene = new Scene(root, 600, 600);
            primaryStage.setTitle("Welcome to the Matrix!");

        // long running operation runs on different thread
        Thread drawMaster = new Thread(new Runnable() {

            @Override
            public void run() {
                Runnable updater = new Runnable() {

                    @Override
                    public void run() {
                        draw(length, width, root, matrix);
                    }
                };

                while (true) {
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException ex) {
                    }

                    // UI update is run on the Application thread
                    Platform.runLater(updater);
                }
            }

        });
        // don't let thread prevent JVM shutdown
        drawMaster.setDaemon(true);
        drawMaster.start();

            primaryStage.setScene(scene);
            primaryStage.show();
    }

    private synchronized void draw(int length, int width, GridPane root, Matrix matrix){
            root.getChildren().clear();
            for (int y = 0; y < length; y++){
                for (int x = 0; x < width; x++){
                    int tempValue = matrix.getValue(y,x);
                    String s;
                    if(matrix.getValue(y,x) == -1){
                        s = "";
                    }else {
                        s = Integer.toString(tempValue);
                    }
                    TextField text = new TextField(s);
                    if(tempValue == 0)
                        text.setStyle("-fx-background-color: red;");
                    text.setPrefHeight(50);
                    text.setPrefWidth(50);
                    text.setAlignment(Pos.CENTER);
                    text.setEditable(false);

                    GridPane.setRowIndex(text, y);
                    GridPane.setColumnIndex(text, x);
                    root.getChildren().add(text);
                }
        }
    }
}
