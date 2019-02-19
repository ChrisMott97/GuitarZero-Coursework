package org.gsep;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MusicItemModel {
    private ObservableList<MusicItem> items = FXCollections.observableArrayList();
    private ObjectProperty<MusicItem> intended = new SimpleObjectProperty<>(null);

    public ObjectProperty<MusicItem> intendedProperty(){
        return intended;
    }

    public MusicItem getIntended() {
        return intended.get();
    }

    public void setIntended(MusicItem item){
        intended.set(item);
    }

    public ObservableList<MusicItem> getItems(){
        return items;
    }

    public void loadData(){
        //temporary data generation
        for (int i = 0; i < 6; i++) {
            items.add(new MusicItem("Song "+(i+1)));
        }
    }
}
