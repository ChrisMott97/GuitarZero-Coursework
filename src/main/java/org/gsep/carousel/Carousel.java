package org.gsep.carousel;

import javafx.animation.Animation;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.TilePane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
 * Carousel.
 *
 * @author  Chris Mott.
 * @version 1.00, January 2019.
 */
public class Carousel extends TilePane{

    private static final int amountContainers = 5;

    private ItemModel iModel;
    private ItemContainerModel icModel;

    @FXML
    protected Button btnNext;

    @FXML
    protected Button btnPrevious;

    @FXML
    protected ItemContainer icOne;

    @FXML
    protected ItemContainer icTwo;

    @FXML
    protected ItemContainer icThree;

    @FXML
    protected ItemContainer icFour;

    @FXML
    protected ItemContainer icFive;

    private List<ItemContainer> containers = new ArrayList<>();

    public Carousel(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/CarouselView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try{
            fxmlLoader.load();
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }

    /**
     * Acts like a constructor and runs when the controller is created.
     */
    public void initialize() {
        System.out.println("Constructed!");

        double tileWidth = (this.getPrefWidth()/amountContainers);
        this.setPrefTileWidth(tileWidth);
        this.setPrefTileHeight(tileWidth);

        for (Node node :
                this.getChildren()) {
            ItemContainer ic = (ItemContainer) node;
            containers.add(ic);
            int ind = containers.indexOf(ic);
            containers.get(ind).setInitialPosition(ind+1);
        }

        //Event Handlers
//        btnNext.setOnAction(e ->
//                next()
//        );
//
//        btnPrevious.setOnAction(e ->
//                previous()
//        );
    }

    /**
     * Injects the models needed into the controller.
     *
     * @param iModel the Item Model used for manipulating Items.
     * @param icModel the Item Container Model used for manipulating Item Containers.
     */
    public void linkModels(ItemModel iModel, ItemContainerModel icModel){
        if(this.icModel != null || this.iModel != null)
            throw new IllegalStateException("Models can only be linked once!");

        this.iModel = iModel;
        this.icModel = icModel;
    }

    public void ingest(List<Item> items){
        System.out.println(iModel);
        iModel.loadData(items);
        icModel.loadData(containers);
        icModel.map(items);
    }

    /**
     * Cycles all item and item container lists to the next intended item.
     * Only works if there are no current animations running.
     */
    public void next(){
        if(icOne.getStatus() == Animation.Status.RUNNING)
            return;
        iModel.next();
        icModel.next();
        icModel.map(iModel.getVisible());
    }

    /**
     * Cycles all item and item container lists to the previous intended item.
     * Only works if there are no current animations running.
     */
    public void previous(){
        if(icOne.getStatus() == Animation.Status.RUNNING)
            return;
        iModel.previous();
        icModel.previous();
        icModel.map(iModel.getVisible());
    }

}
