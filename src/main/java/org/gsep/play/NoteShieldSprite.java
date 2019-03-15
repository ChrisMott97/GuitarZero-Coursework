package org.gsep.play;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.File;

public class NoteShieldSprite extends Sprite{
    private Boolean visible = false;
    private Image whiteImage;
    private Image blackImage;
    
    public NoteShieldSprite(Lane laneType) {
        NoteShield noteShieldType = NoteShield.valueOf(laneType.name());

        setBlackImage(noteShieldType.getBlackImageSource());
        setWhiteImage(noteShieldType.getWhiteImageSource());

        //TODO workaround for setting dims until I figure out a better way
        setImage(whiteImage);
        setWidthPreserveRatio(109);
        setPosition(laneType.getShieldPoint());
    }

    private void setBlackImage(String url){
        File file = new File(getClass().getResource(url).getFile());
        this.blackImage = new Image(file.toURI().toString());
    }

    private void setWhiteImage(String url){
        File file = new File(getClass().getResource(url).getFile());
        this.whiteImage = new Image(file.toURI().toString());
    }

    public void setVisible(Boolean visible, Note noteType) {
        switch (noteType){
            case WHITE:
                setImage(whiteImage);
                break;
            case BLACK:
                setImage(blackImage);
                break;
        }
        this.visible = visible;
    }

    public Boolean getVisible() {
        return visible;
    }

    @Override
    public Boolean active() {
        return true;
    }

    @Override
    public void render(GraphicsContext gc) {
        //TODO could be achieved with progress instead
        if (visible){
            super.render(gc);
        }
    }
}
