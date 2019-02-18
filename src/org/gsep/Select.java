package org.gsep;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Select extends Application {

    public void start (Stage stage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("scene.fxml"));

        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

        stage.setTitle("Select Mode");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);

        List<MusicItem> someList = new ArrayList<>();
        someList.add(new MusicItem("Song 1"));
        someList.add(new MusicItem("Song 2"));
        someList.add(new MusicItem("Song 3"));
        someList.add(new MusicItem("Song 4"));
        someList.add(new MusicItem("Song 5"));
        someList.add(new MusicItem("Song 6"));

        Carousel select = new Carousel<>(someList);


//        System.out.println(select.getAll());
//        System.out.println(select.getVisible());
//        System.out.println(select.getIntended());
//
//        Scanner in = new Scanner(System.in);
//
//        while(true){
//            String input = in.nextLine();
//            if(input.equals("next")){
//                select.next();
//            } else if(input.equals("previous")){
//                select.previous();
//            } else{
//                continue;
//            }
//            System.out.println(select.getAll());
//            System.out.println(select.getVisible());
//            System.out.println(select.getIntended());
//        }
    }
}
