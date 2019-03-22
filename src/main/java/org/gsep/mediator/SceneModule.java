package org.gsep.mediator;

import javafx.scene.Scene;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import org.gsep.ButtonNames;
import org.gsep.ButtonNumbers;
import org.gsep.SceneController;
import org.gsep.controller.Button;
import org.gsep.select.MusicItem;

import java.io.IOException;
import java.util.Locale;

/**
 * SceneModule.
 *
 * @author  Chris Mott.
 * @author  Abigail Lilley.
 * @version 2.00, March 2019.
 */
public abstract class SceneModule {
    private String title;
    private ModuleMediator mediator;
    private Scene scene;


    /**
     * Constructor.
     *
     */
    public SceneModule(){ }

    public void setMediator(ModuleMediator mediator) {
        this.mediator = mediator;
        mediator.addModule(this);
    }

    /**
     * Gets the parent mediator.
     *
     * @return the parent mediator.
     */
    public ModuleMediator getMediator() {
        return mediator;
    }

    /**
     * Swaps the stage scene and current module to a given module and it's scene.
     *
     * @param module the new module to swap to.
     */
    public void swapTo(SceneModule module){
        mediator.setCurrentModule(module);
    }

    public void setIntendedItem(MusicItem musicItem){
        mediator.setIntendedItem(musicItem);
    }

    public String getTitle() {
        return title;
    }

    protected void setTitle(String title) {
        this.title = title;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public Scene getScene() {
        return scene;
    }

    public void init(){}

    /**
     * Connect to the guitar and start a thread to listen to each button.
     * Compatible across Macintosh, Unix, and Windows system.
     * Returns an array of objects that hold a Button and it's thread.
     * @author  Abigail Lilley.
     *
     * @param controller    controller for a specific mode
     * @return  ButtonThreadHolder object
     */
    protected ButtonThreadHolder linkGuitar(SceneController controller) {

        ControllerEnvironment cenv = ControllerEnvironment.getDefaultEnvironment();
        Controller[] ctrls = cenv.getControllers();
        Button[] buttons = new Button[ ButtonNames.BUTTONNAMES.getNames().length ];
        Thread[] threads = new Thread[ ButtonNames.BUTTONNAMES.getNames().length ];
        ButtonThreadHolder holder = new ButtonThreadHolder();
        int[] buttonNums;

        try {
            String osName = System.getProperty("os.name");
            if (osName == null) {
                throw new IOException("os.name not found");
            }
            osName = osName.toLowerCase(Locale.ENGLISH);
            if (osName.contains("windows")) {                           /* Set button numbers according to current OS */
                buttonNums = ButtonNumbers.WINDOWSNUMBERS.getNumbers();
            } else if (osName.contains("linux")
                    || osName.contains("mpe/ix")
                    || osName.contains("freebsd")
                    || osName.contains("irix")
                    || osName.contains("digital unix")
                    || osName.contains("unix")) {
                buttonNums = ButtonNumbers.UNIXNUMBERS.getNumbers();
            } else if (osName.contains("mac os")) {
                buttonNums = ButtonNumbers.MACNUMBERS.getNumbers();
            } else {
                throw new IOException("os.name not supported");
            }

            for ( int i = 0; i < buttons.length; i = i + 1 ) {
                buttons[ i ] = new Button( ButtonNames.BUTTONNAMES.getNames()[i], buttonNums[i]);
                buttons[ i ].addButtonListener( controller );	 /* Adding listeners to Buttons depending on the mode */
                Thread buttonThread = new Thread(buttons[ i ]);
                threads[ i ] = buttonThread;
                buttonThread.start();								             /* Starting a thread for each Button */
            }

            holder.setButtons(buttons);                        /* Customise ButtonThreadHolder for the current button */
            holder.setThreads(threads);

        } catch (IOException ex) {                                               /* OS not identified, can't run game */
            ex.getMessage();
            ex.printStackTrace();
            System.exit(1);
        }
        return holder;
    }

    /**
     * Inner class acts as a holder for a button and its corresponding thread.
     *
     * @author Abigail Lilley.
     */
    public class ButtonThreadHolder {
        private Button[] buttons = null;
        private Thread[] threads = null;

        public Button[] getButtons() {
            return buttons;
        }

        public Thread[] getThreads() {
            return threads;
        }

        void setButtons(Button[] buttons) {
            this.buttons = buttons;
        }

        void setThreads(Thread[] threads) {
            this.threads = threads;
        }
    }
}
