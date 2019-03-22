package org.gsep.play;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;

import java.io.File;

/**
 * note shield game objects that appear at the bottom of the note highway that are
 * visible when a lane is activated by the controller
 *
 * @author orsbarkanyi
 */
public class NoteShieldIcon extends GameObject {
    private Image whiteImage;
    private Image blackImage;
    
    public NoteShieldIcon(Lane laneType) {
        NoteShield noteShieldType = NoteShield.valueOf(laneType.name());

        setBlackImage(noteShieldType.getBlackImageSource());
        setWhiteImage(noteShieldType.getWhiteImageSource());

        setImage(whiteImage);
        setWidthPreserveRatio(109);
        setPosition(laneType.getShieldPoint());
        setPositionOffset(new Point2D(0.5, 0.95));
        setVisible(false);
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
        super.setVisible(visible);
    }

    @Override
    public Boolean active() {
        return true;
    }
}
