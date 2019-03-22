package org.gsep.play;

import javafx.geometry.Point2D;

public class CurrencyCounter extends GameObject {
    private int currency = 0;

    public CurrencyCounter() {
        setImage("/Store/currency.jpg");
        setWidthPreserveRatio(20);
        setPosition(new Point2D(100, 600));
    }

    public void add(){
        currency+=1;
    }
}
