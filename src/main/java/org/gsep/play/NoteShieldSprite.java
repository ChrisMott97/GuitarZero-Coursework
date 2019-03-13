package org.gsep.play;

import javafx.scene.canvas.GraphicsContext;

public class NoteShieldSprite extends Sprite{
    private Boolean visible = false;
    
    public NoteShieldSprite(Lane lane, Note note) {

    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    @Override
    public void render(GraphicsContext gc) {
        if (visible){
            super.render(gc);
        }
    }
}
