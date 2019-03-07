package org.gsep.slash;

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

public class SlashController extends SceneController {

    @FXML
    protected Carousel carousel;

    @FXML
    protected Button btnNext;

    @FXML
    protected Button btnPrevious;

    private FXMLLoader fxmlLoader;

    private Scene nextScene;

    public SlashController(Stage stage){
        setStage(stage);

        fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/SlashView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

    }

    public void initialize(){
        System.out.println("Slash mode initializing...");
        this.loadData();

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
                case SPACE:
                    getStage().setScene(getNextScene());
            }
        });
        return scene;
    }

    //TODO: Reduce to parent method to prevent code duplication
    public void loadData(){
        ObjectMapper objectMapper = new ObjectMapper();
        List<Item> items;

        File file = new File(getClass().getResource("/menu/index.json").getFile());
        try{
            items = objectMapper.readValue(file, new TypeReference<List<MenuItem>>(){});
        }catch(IOException e){
            items = new ArrayList<>();
        }

        for (Item item :
                items) {
            item.setPrefix("menu");
            //TODO: Refactor into something nicer
        }
        
        this.carousel.ingest(items);
    }

    public Scene getNextScene() {
        return nextScene;
    }

    public void setNextScene(Scene nextScene) {
        this.nextScene = nextScene;
    }
}
