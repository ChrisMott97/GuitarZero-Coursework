package org.gsep.select;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import org.gsep.carousel.Carousel;
import org.gsep.carousel.Item;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SelectController {

    @FXML
    public Carousel carousel;

    public SelectController(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/SelectView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try{
            fxmlLoader.load();
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }

    public void initialize(){

    }

//    public void loadData(){
//        List<Item> items = new ArrayList<>();
//
//        for (int i = 0; i < 7; i++) {
//            items.add(new MusicItem("Song "+(i+1), "/songs/Song1/song1.jpg"));
//        }
////        Temporary Song population
//
//        try {
//            //TODO: Make loop for all song folders
//            //TODO: Correctly load images
////            ClassLoader classLoader = getClass().getClassLoader();
////            System.out.println(getClass().getResource("/songs/Song1/song1.png").getFile());
//
//            MusicItem i1 = new MusicItem("File Song 1", "/songs/Song1/song1.png");
//            items.add(i1);
//            MusicItem i2 = new MusicItem("File Song 2", "/songs/Song2/song2.png");
//            items.add(i2);
//
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//
//    }
}
