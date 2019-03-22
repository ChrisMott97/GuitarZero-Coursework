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
import java.util.ArrayList;
import java.util.Locale;

/*
 * SceneModule.
 *
 * @author  Chris Mott.
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
        //TODO: Check is already exists in the module
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

    public void setTitle(String title) {
        this.title = title;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public Scene getScene() {
        return scene;
    }

    public void init(){}

    public ButtonThreadMap linkGuitar(SceneController controller) {
        System.out.println("Linking Guitar");
        ControllerEnvironment cenv = ControllerEnvironment.getDefaultEnvironment();
        Controller[] ctrls = cenv.getControllers();
        Button[] buttons = new Button[ ButtonNames.BUTTONNAMES.getNames().length ];
        Thread[] threads = new Thread[ ButtonNames.BUTTONNAMES.getNames().length ];
        ButtonThreadMap map = new ButtonThreadMap();
        int[] buttonNums;

        try {
            String osName = System.getProperty("os.name");
            if (osName == null) {
                throw new IOException("os.name not found");
            }
            osName = osName.toLowerCase(Locale.ENGLISH);
            if (osName.contains("windows")) {
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
                System.out.println("THIS IS A MAC");
            } else {
                throw new IOException("os.name not supported");
            }

            for ( int i = 0; i < buttons.length; i = i + 1 ) {
                buttons[ i ] = new Button( ButtonNames.BUTTONNAMES.getNames()[i], buttonNums[i]);
                buttons[ i ].addButtonListener( controller );			/* Adding listeners to Buttons depending on the mode */
                Thread buttonThread = new Thread(buttons[ i ]);
                threads[ i ] = buttonThread;
                buttonThread.start();								/* Starting a thread for each Button */
            }

            map.setButtons(buttons);
            map.setThreads(threads);

        } catch (IOException ex) {
            System.out.println("OS not identified, can't run game");
            ex.getMessage();
            ex.printStackTrace();
            //TODO terminate game
        }
        return map;
    }

    public class ButtonThreadMap {
        private Button[] buttons = null;
        private Thread[] threads = null;

        public Button[] getButtons() {
            return buttons;
        }

        public Thread[] getThreads() {
            return threads;
        }

        public void setButtons(Button[] buttons) {
            this.buttons = buttons;
        }

        public void setThreads(Thread[] threads) {
            this.threads = threads;
        }
    }
}
