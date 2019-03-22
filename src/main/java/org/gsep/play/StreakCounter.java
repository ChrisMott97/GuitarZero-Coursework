package org.gsep.play;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * streak counter game object
 *
 * @author orsbarkanyi
 */
public class StreakCounter extends GameObject {
    public Integer noteStreak = 0;

    public StreakCounter() {
        setLayer(2);
        setPositionOffset(new Point2D(0.5, 0.5));
        setPosition(new Point2D(660, 40));
    }

    public void setNoteStreak(Integer noteStreak) {
        this.noteStreak = noteStreak;
    }

    @Override
    public void render(GraphicsContext gc) {
        //render the label
        Font font = Font.font("Arial");
        gc.setFont(font);
        gc.fillText("Note\nStreak", getPosition().getX(), getPosition().getY());

        //render the streak number
        font = Font.font("Arial", FontWeight.BOLD, 30);
        gc.setFont(font);
        gc.fillText(noteStreak.toString(), getPosition().getX()+50, getPosition().getY()+12, 18);
    }
}
