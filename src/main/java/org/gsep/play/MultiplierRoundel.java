package org.gsep.play;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * the multiplier roundel game object
 *
 * @author orsbarkanyi
 */
public class MultiplierRoundel extends GameObject {
    private Integer multiplier = 1;

    public MultiplierRoundel() {
        setWidth(100);
        setHeight(100);
        setLayer(2);
        setPositionOffset(new Point2D(0.5, 0.5));
        setPosition(new Point2D(130, 280));
    }

    public void setMultiplier(Integer multiplier) {
        this.multiplier = multiplier;
    }

    @Override
    public void render(GraphicsContext gc) {
        //render the circle
        gc.setStroke(Color.DARKCYAN);
        gc.setLineWidth(15);
        gc.strokeOval(getPosition().getX(), getPosition().getY() , getWidth(), getHeight());

        //render the multiplier
        Font font = Font.font("Arial", FontWeight.BOLD, 50);
        gc.setFont(font);
        gc.fillText("x"+multiplier.toString(), getPosition().getX()+25, getPosition().getY()+65);
    }
}
