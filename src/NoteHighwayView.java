import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NoteHighwayView {
    private int canvasWidth = 500;
    private int canvasHeight = 500;
    private Canvas canvas = new Canvas (canvasWidth, canvasHeight);
    private GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
    private List<Sprite> noteSprites = Collections.synchronizedList(new ArrayList<>());

    /**
     * Constructor for {@link NoteHighwayView} which starts rendering all the sprites within it
     */
    NoteHighwayView(){
        new AnimationTimer()
        {
            public void handle(long currentNanoTime)
            {
                if (noteSprites.size() > 1) {
                    graphicsContext.clearRect(0,0, canvasWidth, canvasHeight);
                    for (Sprite noteSprite : noteSprites) {
                        noteSprite.render(graphicsContext);
                        noteSprite.setPos(noteSprite.getPosX(), noteSprite.getPosY() + 2);
                    }
                }
            }
        }.start();
    }

    /**
     * gets the canvas
     *
     * @return the canvas
     */
    public Canvas getCanvas() {
        return canvas;
    }

    /**
     * Sends notes down the highway, determining what colour they are based on their type
     * and queueing them to be rendered
     *
     * @param notes the notes corresponding to each lane
     */
    public void sendNotes(Note[] notes){

        for (int i = 0; i < notes.length; i++){
            Sprite sprite = new Sprite();
            sprite.setDim(10, 10);
            sprite.setPos(canvasWidth/2 - 3*15/2 + 15*i,0);
            switch (notes[i]){
                case OPEN:
                    sprite.setFill(Color.LIGHTGRAY);
                    sprite.setStroke(Color.LIGHTGRAY);
                    break;
                case BLACK:
                    sprite.setFill(Color.BLACK);
                    sprite.setStroke(Color.BLACK);
                    break;
                case WHITE:
                    sprite.setFill(Color.LIGHTGRAY);
                    sprite.setStroke(Color.BLACK);
            }
            noteSprites.add(sprite);
        }
        if (noteSprites.size() > 8*3){
            noteSprites.remove(0);
            noteSprites.remove(0);
            noteSprites.remove(0);
        }
    }
}
