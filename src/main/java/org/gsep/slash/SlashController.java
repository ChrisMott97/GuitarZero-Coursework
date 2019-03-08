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
import org.gsep.carousel.*;
import org.gsep.mediator.Mediator;

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

    private ItemModel itemModel;
    private ItemContainerModel itemContainerModel;
    private SlashModule module;

    public SlashController(ItemModel itemModel, ItemContainerModel itemContainerModel, SlashModule module){

        fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/SlashView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        this.itemModel = itemModel;
        this.itemContainerModel = itemContainerModel;
        this.module = module;

    }

    public void initialize(){
        System.out.println("Slash mode initializing...");
        carousel.linkModels(this.itemModel, this.itemContainerModel);
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
                    //TODO: Replace indexing with a getByName poss. using dictionaries
                    switch(itemModel.getIntended().getName()){
                        //TODO: Use enums for modes or include functionality in a Model
                        case "Select":
                            module.swapTo(module.getMediator().getModules().get(1));
                            break;
                    }
                    break;
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
