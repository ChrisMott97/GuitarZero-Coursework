package org.gsep.store;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.gsep.SceneController;
import org.gsep.carousel.Carousel;
import org.gsep.carousel.Item;
import org.gsep.carousel.ItemContainerModel;
import org.gsep.carousel.ItemModel;
import org.gsep.slash.SlashModule;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
 * SelectController.
 *
 * @author  Chris Mott.
 * @version 2.00, March 2019.
 */
public class StoreController extends SceneController {

    @FXML
    private Carousel carousel;
    @FXML
    private ImageView imageView;

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

    int currency;

    /**
     * Constructor.
     *
     * @param itemModel the Item model to be linked to the carousel.
     * @param itemContainerModel the Item container model to be linked to the carousel.
     * @param module the link back to the parent module to allow the controller to change to next scene.
     */
    public StoreController(ItemModel itemModel, ItemContainerModel itemContainerModel, StoreModule module){
        fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/StoreView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        this.itemModel = itemModel;
        this.itemContainerModel = itemContainerModel;
        this.module = module;
        this.store = new Store();

    }

    /**
     * Is called as a result of loading as an FXML Controller.
     */
    public void initialize(){
        System.out.println("Store mode initializing...");
        carousel.linkModels(itemModel,itemContainerModel);
        File file = new File("src/main/resources/Store/currency.jpg");
        Image image = new Image(file.toURI().toString());
        imageView.setImage(image);
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

        scene.setOnKeyPressed(keyEvent -> {
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
                    if(currency > 1) {
                        int id = itemModel.getIntended().getId();
                        String name = itemModel.getIntended().getName();
                        downloadData(id, name);
                        currency = currency - 1;
                    }else{
                        System.out.println("Don't have enough currency to purchase song");
                    }
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
            store.getImages();
            store.getJSON();
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
                System.out.println("Setting default image file");
                item.setImageFile(new File(getClass().getResource(imgDir+defaultName+imgExt).getFile()));
            }
        }
        this.carousel.ingest(items);
    }

    private void downloadData(int id, String fileName){

        try {
            store.getFile(id);
            System.out.println(fileName + " has been successfully bought.");
            store.updateJSON(fileName);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
