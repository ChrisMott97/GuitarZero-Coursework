package org.gsep.select;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import org.gsep.carousel.Carousel;
import org.gsep.carousel.Item;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SelectController extends StackPane {

    @FXML
    protected Carousel carousel;

    @FXML
    protected Button btnNext;

    @FXML
    protected Button btnPrevious;

    FXMLLoader fxmlLoader;

    public SelectController(){
        fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/SelectView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

    }

    public void initialize(){
        System.out.println("Select mode initializing...");

        // Event Listeners
//        btnNext.setOnAction(e -> {
//            carousel.next();
//        });
//
//        btnPrevious.setOnAction(e -> {
//            carousel.previous();
//        });

    }

    public void loadData(){
        ObjectMapper objectMapper = new ObjectMapper();
        List<Item> items;

        File file = new File(getClass().getResource("/songs/index.json").getFile());
        try{
            items = objectMapper.readValue(file, new TypeReference<List<MusicItem>>(){});
        }catch(IOException e){
            items = new ArrayList<>();
        }

        this.carousel.ingest(items);
    }
}
