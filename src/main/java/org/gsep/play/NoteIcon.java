package org.gsep.play;

import javafx.geometry.Point2D;

/**
 * The note icon game object that runs down the note highway
 *
 * @author orsbarkanyi
 */
public class NoteIcon extends GameObject {
    private Lane laneType;
    private final int tickPosition;
    private double spawnTime;
    private double initialSize = 45;
    private double finalSize = 109;
    private double progress = 0;
    private boolean caught = false;

    public NoteIcon(Note noteType, Lane laneType, int tickPosition){
        this.laneType = laneType;
        this.tickPosition = tickPosition;
        this.spawnTime = System.currentTimeMillis();

        setImage(noteType.getImageSource());
        setWidthPreserveRatio(initialSize);
        setPositionOffset(new Point2D(0.5, 0.95));
    }

    /**
     * update the position of the object on the canvas during stages of its life
     * 1 - flowing down the highway
     * 2 - caught or uncaught
     *
     * @param currentTimeMillis the time of rendering
     */
    @Override
    public void updatePosition(double currentTimeMillis) {
        progress = (currentTimeMillis-spawnTime)/NoteHighwayView.noteHighwayPeriod;

        if (!caught){
            Point2D source = laneType.getSpawnPoint();
            Point2D destination = laneType.getShieldPoint();

            Point2D vector = destination.subtract(source);

            double wait = NoteHighwayModel.scoreTickRange/(double)2/NoteHighwayModel.noteHighwayLength;

            Point2D newPosition;
            Double newWidth;

            if (progress < 1){
                newPosition = source.add(vector.multiply(progress));
                newWidth = initialSize + (finalSize-initialSize)*progress;
            } else if (progress >= 1 && progress < 1+wait){
                newPosition = destination;
                newWidth = finalSize;
                setLayer(1);
            } else{
                newPosition = source.add(vector.multiply(progress-wait));
                newWidth = initialSize + (finalSize-initialSize)*(progress-wait);
                setLayer(1);
            }

            setPosition(newPosition);
            setWidthPreserveRatio(newWidth);
        } else {
            Point2D source = laneType.getShieldPoint();

            Point2D vector = new Point2D(0,-1);

            Point2D newpos = source.add(vector.multiply((progress-1)*300));
            setPosition(newpos);
            setOpacity(getOpacity()*0.97);
        }

        //mark for disposal if past its lifetime
        if (progress > 2){
            setActive(false);
        }
    }

    public void caught(){
        this.caught = true;
    }

    public Integer getTickPosition() {
        return tickPosition;
    }
}
