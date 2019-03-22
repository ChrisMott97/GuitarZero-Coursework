package org.gsep.play;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

public class CurrencyCounter extends GameObject {
    private int currency = 0;

    public CurrencyCounter() {
        setImage("/Store/currency.png");
        setWidthPreserveRatio(30);
        setPosition(new Point2D(80, 370));
        setLayer(2);
        setPositionOffset(new Point2D(0.5, 0.5));
    }

    public void setCurrency(int currency){
        this.currency = currency;
    }

    @Override
    public void render(GraphicsContext gc) {
        for (int i = 0; i < currency; i++){
            gc.drawImage(getImage(), getPosition().getX()+i*getWidth(), getPosition().getY(), getWidth(), getHeight());
        }
    }
}
