package org.gsep.store;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import org.gsep.SceneController;
import org.gsep.carousel.Carousel;
import org.gsep.carousel.ItemContainerModel;
import org.gsep.carousel.ItemModel;
import org.gsep.controller.ButtonEvent;
import org.gsep.controller.ButtonListener;
import org.gsep.controller.ButtonState;
import org.gsep.slash.SlashModule;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * SelectController.
 *
 * @author  Chris Mott.
 * @author  Abigail Lilley.
 * @author Niha Gummakonda
 * @version 2.00, March 2019.
 */
public class StoreController extends SceneController implements ButtonListener {

    @FXML
    private Carousel carousel;

    private FXMLLoader fxmlLoader;

    private ItemModel itemModel;
    private ItemContainerModel itemContainerModel;
    private StoreModule module;
    private Store store;

    private static final String defaultName = "default";
    private static final String baseDir = "/cache/";
    private static final String indexFile = baseDir +"index.json";
    private static final String imgDir = baseDir +"img/";
    private static final String imgExt = ".jpg";


    /**
     * Constructor.
     *
     * @param itemModel the Item model to be linked to the carousel.
     * @param itemContainerModel the Item container model to be linked to the carousel.
     * @param module the link back to the parent module to allow the controller to change to next scene.
     */
    StoreController(ItemModel itemModel, ItemContainerModel itemContainerModel, StoreModule module){
        fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/StoreView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        this.itemModel = itemModel;
        this.itemContainerModel = itemContainerModel;
        this.module = module;

    }

    /**
     * Is called as a result of loading as an FXML Controller.
     */
    public void initialize(){
        carousel.linkModels(itemModel,itemContainerModel);
        loadData();

    }

    /**
     * Manages input and returns the scene.
     *
     * @return the associated scene.
     * @throws Exception
     */
    public Scene load() throws Exception{
        Scene scene = super.load(this.fxmlLoader, this.carousel);

        scene.setOnKeyPressed(keyEvent -> {                                         /* Fallback to keyboard input */
            switch(keyEvent.getCode()){
                case RIGHT:
                    carousel.next();
                    break;
                case LEFT:
                    carousel.previous();
                    break;
                case ESCAPE:
                    module.swapTo(SlashModule.getInstance());
                    break;
                case SPACE:
                    int id = itemModel.getIntended().getId();
                    String name = itemModel.getIntended().getName();
                    downloadData(id, name);
                    break;

            }
        });
        return scene;
    }

    /**
     * Loads data from the index json file.
     */
    private void loadData() {
        try {
            this.store = new Store();
            store.getImages();
            this.store = new Store();
            store.getJSON();
            this.store = new Store();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ObjectMapper objectMapper = new ObjectMapper();
        List<StoreItem> items;

        File file = new File(getClass().getResource("/cache/index.json").getFile());
        try{
            items = objectMapper.readValue(file, new TypeReference<List<StoreItem>>(){});
        }catch(IOException e){
            items = new ArrayList<>();
        }

        int itemId;
        for (StoreItem item : items) {
            itemId = item.getId();
            try{
                item.setImageFile(new File(getClass().getResource(imgDir+itemId+imgExt).getFile()));
            }catch (NullPointerException e){
                item.setImageFile(new File(getClass().getResource(imgDir+defaultName+imgExt).getFile()));
            }
        }
        this.carousel.ingest(items);
    }

    /**
     * When user purchases, the midi and text files get transferred to the game contents
     * @param id
     * @param fileName
     */
    private void downloadData(int id, String fileName){

        try {
            store.getFile(id);
            System.out.println(fileName + " has been successfully bought.");
            store.updateJSON(fileName);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Handles guitar input according the specification of Store Mode
     * @author Abigail Lilley
     *
     * @param buttonName assigned name of the button to make the code more readable and intuitive.
     *                   Implementations process the event depending on the button, identified by this name.
     * @param event event triggered. The Button's state can be found from this.
     */
    @Override
    public void stateReceived(String buttonName, ButtonEvent event) {

        if (this.module == module.getMediator().getCurrentModule()) {

            Platform.runLater( () -> {
                if (event.state() == ButtonState.ON) {

                    switch (buttonName) {
                        case "zeroPower":
                            int id = itemModel.getIntended().getId();
                            String name = itemModel.getIntended().getName();
                            downloadData(id, name);
                            break;
                        case "escape":                                                       /* Return to Slash Mode */
                            Platform.runLater(() -> {
                                module.swapTo(SlashModule.getInstance());
                            });
                            break;
                    }
                } else if (event.state() == ButtonState.FORWARD) {
                    carousel.next();
                } else if (event.state() == ButtonState.BACKWARD) {
                    carousel.previous();
                }
            });
        }
    }
}
