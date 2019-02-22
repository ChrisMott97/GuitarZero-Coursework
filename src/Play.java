import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Play extends Application{

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage){
        stage.setTitle("Play");

        //initialise scene
        Group root = new Group();
        Scene scene = new Scene(root);
        stage.setScene(scene);

        NoteHighwayModel model = new NoteHighwayModel();
        NoteHighwayView view = new NoteHighwayView();
        NoteHighwayController controller = new NoteHighwayController(model, view);


        root.getChildren().add(view.getCanvas());


        stage.show();
        controller.play();
    }
}