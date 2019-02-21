package org.gsep;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.TilePane;

import java.util.ArrayList;
import java.util.List;


public class SelectController {

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

    public void initialize() {
        containers.add(icOne);
        containers.add(icTwo);
        containers.add(icThree);
        containers.add(icFour);
        containers.add(icFive);
        for (int i = 0; i < containers.size(); i++) {
            containers.get(i).setInitialPosition(i+1);
        }

        btnNext.setOnAction(e ->
                next()
        );

        btnPrevious.setOnAction(e ->
                previous()
        );
    }

    public void linkModels(ItemModel iModel, ItemContainerModel icModel){
        if(this.icModel != null || this.iModel != null)
            throw new IllegalStateException("Model can only be linked once!");

        this.iModel = iModel;
        this.icModel = icModel;
        this.loadData();

    }

    private void loadData(){
        List<MusicItem> items = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            items.add(new MusicItem("Song "+(i+1)));
        }
        iModel.loadData(items);
        icModel.loadData(containers);
        icModel.map(items);
    }

    public void next(){
        iModel.next();
        icModel.next();
        icModel.map(iModel.getVisible());
    }

    public void previous(){
        iModel.previous();
        icModel.previous();
        icModel.map(iModel.getVisible());
    }

}
