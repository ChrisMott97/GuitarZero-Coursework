package org.gsep.slash;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import org.gsep.SceneController;
import org.gsep.carousel.*;
import org.gsep.play.PlayModule;
import org.gsep.select.SelectModule;
import org.gsep.store.StoreModule;
import org.gsep.controller.ButtonEvent;
import org.gsep.controller.ButtonListener;
import org.gsep.controller.ButtonState;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * SlashController.
 *
 * @author  Chris Mott.
 * @author  Abigail Lilley
 * @version 2.00, March 2019.
 */
public class SlashController extends SceneController implements ButtonListener {

    @FXML
    protected Carousel carousel;

    private FXMLLoader fxmlLoader;

    private Scene nextScene;

    private ItemModel itemModel;
    private ItemContainerModel itemContainerModel;
    private SlashModule module;

    private static final String baseDir = "/menu/";
    private static final String indexFile = baseDir +"index.json";
    private static final String imgDir = baseDir +"img/";
    private static final String imgExt = ".png";
    private static final String defaultName = "default";

    /**
     * Constructor.
     *
     * @param itemModel the Item Model to be linked with the carousel.
     * @param itemContainerModel the Item Container Model to be linked with the carousel.
     * @param module the parent module to allow links back to change module etc...
     */
    public SlashController(ItemModel itemModel, ItemContainerModel itemContainerModel, SlashModule module){

        fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/SlashView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        this.itemModel = itemModel;
        this.itemContainerModel = itemContainerModel;
        this.module = module;

    }

    /**
     * Is called due to FXML Controller, after constructor.
     */
    public void initialize(){
        System.out.println("Slash mode initializing...");
        carousel.linkModels(this.itemModel, this.itemContainerModel);
        this.loadData();

    }

    /**
     * Handles input and triggers swapping scenes.
     *
     * @return the given scene.
     * @throws Exception
     */
    public Scene load() throws Exception{
        System.out.println("load() running");
        Scene scene = super.load(this.fxmlLoader, this.carousel);
        //Fallback to keyboard input
        scene.setOnKeyPressed(keyEvent -> {
            switch(keyEvent.getCode()){
                case RIGHT:
                    carousel.next();
                    break;
                case LEFT:
                    carousel.previous();
                    break;
                case SPACE:
                    //TODO: Make itemModel store enum reference so switch not necessary!
                    switch(itemModel.getIntended().getName()){
                        case "Select":
                            module.swapTo(SelectModule.getInstance());
                            break;
                        case "Store":
                            module.swapTo(StoreModule.getInstance());
                            break;
                        case "Play":
                            if(module.getMediator().getIntendedItem() == null){
                                module.swapTo(SelectModule.getInstance());
                                break;
                            }

                            module.swapTo(PlayModule.getInstance());
                            break;
                    }
                    break;
            }
        });
        return scene;
    }

    /**
     * Loads data from an index json which links metadata with images and other files.
     */
    //TODO: Reduce to parent method to prevent code duplication
    private void loadData(){
        ObjectMapper objectMapper = new ObjectMapper();
        List<MenuItem> items;

        File file = new File(getClass().getResource(indexFile).getFile());
        try{
            items = objectMapper.readValue(file, new TypeReference<List<MenuItem>>(){});
        }catch(IOException e){
            items = new ArrayList<>();
        }

        int itemId;
        for (Item item : items) {
            itemId = item.getId();
            try{
                item.setImageFile(new File(getClass().getResource(imgDir+itemId+imgExt).getFile()));
            }catch (NullPointerException e){
                System.out.println("Setting default image file");
                item.setImageFile(new File(getClass().getResource(imgDir+defaultName+imgExt).getFile()));
            }
        }
        
        this.carousel.ingest(items);
    }

    @Override
    public void stateReceived(String buttonName, ButtonEvent event) {
        System.out.println("State recieved :   "+event.state());
        //TODO implement for slash mode
        if (this.module == module.getMediator().getCurrentModule()) {
            if (event.state() == ButtonState.ON) {
                switch (buttonName) {
                    case "zeroPower":
                        //TODO: Make itemModel store enum reference so switch not necessary!
                        Platform.runLater( () -> {
                            // Update UI here.
                            switch (itemModel.getIntended().getName()) {
                                case "Select":
                                    module.swapTo(SelectModule.getInstance());
                                    break;
                                case "Store":
                                    module.swapTo(StoreModule.getInstance());
                                    break;
                                case "Play":
                                    module.swapTo(PlayModule.getInstance());
                                    break;
                            }
                        });
                        break;
                }
            } else if (event.state() == ButtonState.FORWARD) {
                carousel.next();
            } else if (event.state() == ButtonState.BACKWARD) {
                carousel.previous();
            }
        }


    }
}
