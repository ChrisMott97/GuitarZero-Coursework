package org.gsep;

import javafx.animation.Animation;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/*
 * SelectController.
 *
 * @author  Chris Mott.
 * @version 1.00, January 2019.
 */
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

    /**
     * Acts like a constructor and runs when the controller is created.
     */
    public void initialize() {
        containers.add(icOne);
        containers.add(icTwo);
        containers.add(icThree);
        containers.add(icFour);
        containers.add(icFive);
        for (int i = 0; i < containers.size(); i++) {
            containers.get(i).setInitialPosition(i+1);
        }


        //Event Handlers
        btnNext.setOnAction(e ->
                next()
        );

        btnPrevious.setOnAction(e ->
                previous()
        );
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
        this.loadData();

    }

    /**
     * The point of ingestion for data from files.
     */
    private void loadData(){
        List<MusicItem> items = new ArrayList<>();

        for (int i = 0; i < 7; i++) {
            items.add(new MusicItem("Song "+(i+1), "file:res/Song1/song1.png"));
        }
//        Temporary Song population

        try {
            //TODO: Make loop for all song folders
            //TODO: Correctly load images
            File song1 = new File("res/Song1/meta.xml");
            File song2 = new File("res/Song2/meta.xml");

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document song1doc = builder.parse(song1);
            Document song2doc = builder.parse(song2);

            Element el1 = song1doc.getDocumentElement();
            MusicItem i1 = new MusicItem(el1.getElementsByTagName("Name").item(0).getTextContent(), "file:res/Song1/song1.png");
            items.add(i1);
            Element el2 = song2doc.getDocumentElement();
            MusicItem i2 = new MusicItem(el2.getElementsByTagName("Name").item(0).getTextContent(), "file:res/Song2/song2.png");
            items.add(i2);

        }catch(Exception e){
            e.printStackTrace();
        }

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
