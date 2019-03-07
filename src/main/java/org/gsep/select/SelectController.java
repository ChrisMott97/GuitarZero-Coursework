package org.gsep.select;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.gsep.SceneController;
import org.gsep.carousel.Carousel;
import org.gsep.carousel.Item;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SelectController extends SceneController {

    @FXML
    private Carousel carousel;

    private FXMLLoader fxmlLoader;

    public SelectController(Stage stage){
        setStage(stage);
        fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/SelectView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

    }

    public void initialize(){
        System.out.println("Select mode initializing...");
        // Event handling/listeners go here!
        loadData();

    }

    public Scene load() throws Exception{
        Scene scene = super.load(this.fxmlLoader, this.carousel);
        scene.setOnKeyPressed(keyEvent -> {
            switch(keyEvent.getCode()){
                case RIGHT:
                    carousel.next();
                    break;
                case LEFT:
                    carousel.previous();
                    break;
            }
        });
        return scene;
    }

    private void loadData(){
        ObjectMapper objectMapper = new ObjectMapper();
        List<Item> items;

        File file = new File(getClass().getResource("/songs/index.json").getFile());
        try{
            items = objectMapper.readValue(file, new TypeReference<List<MusicItem>>(){});
        }catch(IOException e){
            items = new ArrayList<>();
        }

        for (Item item :
                items) {
            item.setPrefix("songs");
        }

        this.carousel.ingest(items);
    }
}
