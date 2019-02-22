import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class NoteHighwayView {
    private Canvas canvas = new Canvas (600, 500);
    private GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
    private int n = 0;
    NoteHighwayView(){

    }

    public Canvas getCanvas() {
        return canvas;
    }

    public void nextBeat(Note[] notes){

        for (int i = 0; i < notes.length; i++){
            Sprite s = new Sprite();
            s.setDim(10, 10);
            s.setPos(15*i,15*n);
            switch (notes[i]){
                case OPEN:
                    s.setFill(Color.WHEAT);
                    s.setStroke(Color.WHEAT);
                    System.out.println("open");
                    break;
                case BLACK:
                    s.setFill(Color.BLACK);
                    s.setStroke(Color.BLACK);
                    System.out.println("black");
                    break;
                case WHITE:
                    s.setFill(Color.WHEAT);
                    s.setStroke(Color.BLACK);
                    System.out.println("white");
            }
            s.render(graphicsContext);
        }
        n++;
    }

    private void render() {
    }
}
