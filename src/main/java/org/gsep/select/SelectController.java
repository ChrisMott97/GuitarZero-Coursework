package org.gsep.select;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import org.gsep.SceneController;
import org.gsep.carousel.Carousel;
import org.gsep.carousel.ItemContainerModel;
import org.gsep.carousel.ItemModel;
import org.gsep.slash.SlashModule;
import org.gsep.controller.ButtonEvent;
import org.gsep.controller.ButtonListener;
import org.gsep.controller.ButtonState;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * SelectController.
 *
 * @author  Chris Mott.
 * @author  Abigail Lilley
 * @version 2.00, March 2019.
 */
public class SelectController extends SceneController implements ButtonListener {

    @FXML
    private Carousel carousel;

    private FXMLLoader fxmlLoader;

    private ItemModel itemModel;
    private ItemContainerModel itemContainerModel;
    private SelectModule module;

    private static final String defaultName = "default";
    private static final String baseDir = "/songs/";
    private static final String indexFile = baseDir +"index.json";
    private static final String imgDir = baseDir +"img/";
    private static final String imgExt = ".jpg";
    private static final String midiDir = baseDir +"midi/";
    private static final String midiExt = ".mid";
    private static final String notesDir = baseDir +"notes/";
    private static final String notesExt = ".txt";


    /**
     * Constructor.
     *
     * @param itemModel the Item model to be linked to the carousel.
     * @param itemContainerModel the Item container model to be linked to the carousel.
     * @param module the link back to the parent module to allow the controller to change to next scene.
     */
    public SelectController(ItemModel itemModel, ItemContainerModel itemContainerModel, SelectModule module){
        fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/SelectView.fxml"));
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
        System.out.println("Select mode initializing...");
        carousel.linkModels(itemModel, itemContainerModel);

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
        //Backup keyboard input
        scene.setOnKeyPressed(keyEvent -> {
            switch(keyEvent.getCode()){
                case RIGHT:
                    carousel.next();
                    break;
                case LEFT:
                    carousel.previous();
                    break;
                case SPACE:
                    if(itemModel.getIntended().getClass() == MusicItem.class){
                        module.setIntendedItem((MusicItem)itemModel.getIntended());
                    }
                case ESCAPE:
                    module.swapTo(SlashModule.getInstance());
                    break;
            }
        });
        return scene;
    }

    /**
     * Loads data from the index json file.
     */
    private void loadData() {
        ObjectMapper objectMapper = new ObjectMapper();
        List<MusicItem> items;
        String dir;

        File file = new File(getClass().getResource(indexFile).getFile());
        try{
            items = objectMapper.readValue(file, new TypeReference<List<MusicItem>>(){});
        }catch(IOException e){
            items = new ArrayList<>();
        }

        int itemId;
        for (MusicItem item : items) {
            itemId = item.getId();
            try{
                item.setMidiFile(new File(getClass().getResource(midiDir+itemId+midiExt).getFile()));
            }catch(NullPointerException e){
                System.out.println("Setting default midi file");
                item.setMidiFile(new File(getClass().getResource(midiDir+defaultName+midiExt).getFile()));
            }
            try{
                item.setNoteFile(new File(getClass().getResource(notesDir+itemId+notesExt).getFile()));
            }catch(NullPointerException e){
                System.out.println("Setting default notes file");
                item.setNoteFile(new File(getClass().getResource(notesDir+defaultName+notesExt).getFile()));
            }
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
        System.out.println("State received select:   "+event.state() + "  thread:  "+Thread.currentThread());
        //TODO implement for select mode
        if (this.module == module.getMediator().getCurrentModule()) {
            System.out.println("SELECT REACTING");
            Platform.runLater( () -> {
                if (event.state() == ButtonState.ON) {
                    switch (buttonName) {
                        case "zeroPower":
                            if (itemModel.getIntended().getClass() == MusicItem.class) {
                                module.setIntendedItem((MusicItem) itemModel.getIntended());
                            }
                        case "escape":
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
