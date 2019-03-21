package org.gsep.mediator;

import javafx.scene.Scene;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import org.gsep.SceneController;
import org.gsep.controller.Button;
import org.gsep.select.MusicItem;

import java.io.IOException;
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

    private final static String[] BUTTONNAMES = {   "zeroPower",
            "strumBar",
            "escape"    };

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

<<<<<<< HEAD
//    public void linkGuitar( SceneController controller) {
//        System.out.println("Linking Guitar");
//        ControllerEnvironment cenv = ControllerEnvironment.getDefaultEnvironment();
//        Controller[] ctrls = cenv.getControllers();
//        int[] buttonNumbers;
//
//        try {
//            String osName = System.getProperty("os.name");
//            if (osName == null) {
//                throw new IOException("os.name not found");
//            }
//            osName = osName.toLowerCase(Locale.ENGLISH);
//            if (osName.contains("windows")) {
//                buttonNumbers = ButtonNumbers.WINDOWSNUMBERS.getNumbers();
//            } else if (osName.contains("linux")
//                    || osName.contains("mpe/ix")
//                    || osName.contains("freebsd")
//                    || osName.contains("irix")
//                    || osName.contains("digital unix")
//                    || osName.contains("unix")) {
//                buttonNumbers = ButtonNumbers.UNIXNUMBERS.getNumbers();
//            } else if (osName.contains("mac os")) {
//                buttonNumbers = ButtonNumbers.MACNUMBERS.getNumbers();
//                System.out.println("THIS IS A MAC");
//            } else {
//                throw new IOException("os.name not supported");
//            }
//
//            Button[] buttons = new Button[ buttonNumbers.length ];
//            for ( int i = 0; i < buttons.length; i = i + 1 ) {
//                buttons[ i ] = new Button( ButtonNames.BUTTONNAMES.getNames()[i], buttonNumbers[i]);
//                buttons[ i ].addButtonListener( controller );	/* Adding listeners to Buttons depending on the mode */
//                Thread buttonThread = new Thread(buttons[ i ]);
//                buttonThread.start();								            /* Starting a thread for each Button */
//            }
//
//        } catch (IOException ex) {
//            System.out.println("OS not identified, can't run game");
//            ex.getMessage();
//            ex.printStackTrace();
//            //TODO terminate game
//        }
//    }
//
//    public void linkGuitar( NoteHighwayController controller) {
//        System.out.println("Linking Guitar");
//        ControllerEnvironment cenv = ControllerEnvironment.getDefaultEnvironment();
//        Controller[] ctrls = cenv.getControllers();
//        GuitarEventHandler guitarEventHandler = new GuitarEventHandler(controller);
//        int[] buttonNumbers;
//
//        try {
//            String osName = System.getProperty("os.name");
//            if (osName == null) {
//                throw new IOException("os.name not found");
//            }
//            osName = osName.toLowerCase(Locale.ENGLISH);
//            if (osName.contains("windows")) {
//                buttonNumbers = ButtonNumbers.WINDOWSNUMBERS.getNumbers();
//            } else if (osName.contains("linux")
//                    || osName.contains("mpe/ix")
//                    || osName.contains("freebsd")
//                    || osName.contains("irix")
//                    || osName.contains("digital unix")
//                    || osName.contains("unix")) {
//                buttonNumbers = ButtonNumbers.UNIXNUMBERS.getNumbers();
//            } else if (osName.contains("mac os")) {
//                buttonNumbers = ButtonNumbers.MACNUMBERS.getNumbers();
//                System.out.println("THIS IS A MAC");
//            } else {
//                throw new IOException("os.name not supported");
//            }
//
//            Button[] buttons = new Button[ buttonNumbers.length ];
//            for ( int i = 0; i < buttons.length; i = i + 1 ) {
//                buttons[ i ] = new Button( ButtonNames.BUTTONNAMES.getNames()[i], buttonNumbers[i]);
//                buttons[ i ].addButtonListener( guitarEventHandler );	/* Adding listeners to Buttons depending on the mode */
//                Thread buttonThread = new Thread(buttons[ i ]);
//                buttonThread.start();								            /* Starting a thread for each Button */
//            }
//
//        } catch (IOException ex) {
//            System.out.println("OS not identified, can't run game");
//            ex.getMessage();
//            ex.printStackTrace();
//            //TODO terminate game
//        }
//    }
=======
    public void linkGuitar( SceneController controller) {
        System.out.println("Linking Guitar");
        ControllerEnvironment cenv = ControllerEnvironment.getDefaultEnvironment();
        Controller[] ctrls = cenv.getControllers();
        int[] buttonNums;
        final int[] windowsButtonNums = {8, 15, 10};
        final int[] unixButtonNums    = { 0, 1, 4};
        final int[] macButtonNums = { 8, 15, 10 };
        //TODO get values for different OS



        try {
            String osName = System.getProperty("os.name");
            if (osName == null) {
                throw new IOException("os.name not found");
            }
            osName = osName.toLowerCase(Locale.ENGLISH);
            if (osName.contains("windows")) {
                buttonNums = windowsButtonNums;
            } else if (osName.contains("linux")
                    || osName.contains("mpe/ix")
                    || osName.contains("freebsd")
                    || osName.contains("irix")
                    || osName.contains("digital unix")
                    || osName.contains("unix")) {
                buttonNums = unixButtonNums;
            } else if (osName.contains("mac os")) {
                buttonNums = macButtonNums;
                System.out.println("THIS IS A MAC");
            } else {
                throw new IOException("os.name not supported");
            }

            Button[] buttons = new Button[ BUTTONNAMES.length ];
            for ( int i = 0; i < buttons.length; i = i + 1 ) {
                buttons[ i ] = new Button( BUTTONNAMES[i], buttonNums[i]);
                buttons[ i ].addButtonListener( controller );			/* Adding listeners to Buttons depending on the mode */
                Thread buttonThread = new Thread(buttons[ i ]);
                buttonThread.start();								/* Starting a thread for each Button */
            }

        } catch (IOException ex) {
            System.out.println("OS not identified, can't run game");
            ex.getMessage();
            ex.printStackTrace();
            //TODO terminate game
        }
    }
>>>>>>> parent of b97017e... Merge branch 'feature-OS-compatible-input' into feature-link-guitar
}
