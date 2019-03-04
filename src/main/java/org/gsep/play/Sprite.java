package org.gsep.play;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Sprite {
    private Color fill;
    private Color stroke;
    private double posX;
    private double posY;
    private double width;
    private double height;
    private Note noteColour;
    private Image image;
    private double noteSize = 40;
    private int position;
    
    /**
     * @author humzahmalik
     * Gets size of notes falling
     *
     * @param fill fill colour
     * @return 
     */
    public double getNoteSizeIncrease() {
		return noteSize;
    	}
    
    /**
     * @author humzahmalik
     * Sets size of notes falliing
     *
     * @param fill fill colour
     */
    public void setNoteSize(double noteSize) {
    		this.noteSize=noteSize;
    }
    
    /**
     * @author humzahmalik
     * Sets size of notes falliing
     *
     * @param position
     */
    public void setNoteID(int position) {
    		this.position=position;
    }
    
    /**
     * @author humzahmalik
     * Get size of notes falliing
     * @return 
     *
     * 
     */
    public int getNoteID() {
    		return position;
    }
    
    /**
     * @author Ors 
     * Sets fill colour of sprite
     *
     * @param fill fill colour
     */
    public void identifyNoteColour(Note noteColour) {
        this.noteColour = noteColour;
    }

  
    /**
     * gets the x position of the top left corner of the sprite within the canvas
     *
     * @return X-position
     */
    public double getPosX() {
        return posX;
    }

    /**
     * gets the y position of the top left corner of the sprite within the canvas
     *
     * @return Y-position
     */
    public double getPosY() {
        return posY;
    }

    /**
     * sets the x and y positions of the top left corner of the sprite within the canvas
     *
     * @param posX X-position pixel value
     * @param posY Y-position pixel value
     */
    public void setPos(double posX, double posY) {
        this.posX = posX;
        this.posY = posY;
    }


    /**
     * renders the sprite with the current settings for its appearance and location
     *
     * @param gc the GraphicsContext of the canvas to render the sprite to
     */
    
    public void render(GraphicsContext gc) {
        //HM- Image flow
    		switch(noteColour){
    		
    		case OPEN:
   		     
   		     break;
   		     
    		 case WHITE:
    		     image= new Image("White.png", noteSize, noteSize, false, false);
    		     break;
    		 
    		 case BLACK:
    			 image= new Image("Black.png", noteSize, noteSize, false, false);
    			 break;
    		}
   
        gc.drawImage(image, posX, posY);
        
    }
}
