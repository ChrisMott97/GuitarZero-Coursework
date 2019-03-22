package org.gsep.play;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class ScoreCounter extends GameObject {
    public Integer score = 0;

    public ScoreCounter() {
        setLayer(2);
        setPositionOffset(new Point2D(0.5, 0.5));
        setPosition(new Point2D(120, 420));
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    @Override
    public void render(GraphicsContext gc) {
        Font font = Font.font("Arial");
        gc.setFont(font);
        gc.fillText("Score", getPosition().getX()-50, getPosition().getY()-5);
        font = Font.font("Arial", FontWeight.BOLD, 30);
        gc.setFont(font);
        gc.fillText(score.toString(), getPosition().getX(), getPosition().getY(), 18);
    }
}
