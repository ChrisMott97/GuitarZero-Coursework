package org.gsep.select;

import javafx.scene.Scene;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import org.gsep.carousel.ItemContainerModel;
import org.gsep.carousel.ItemModel;
import org.gsep.controller.Button;
import org.gsep.mediator.Mediator;
import org.gsep.mediator.SceneModule;

import java.io.IOException;
import java.util.Locale;

/**
 * SelectModule.
 *
 * @author  Chris Mott.
 * @author  Abigail Lilley
 * @version 2.00, March 2019.
 */
public class SelectModule extends SceneModule {
    private SelectController controller;
    private ItemModel itemModel;
    private ItemContainerModel itemContainerModel;

    private static SelectModule instance;

    private final static String[] BUTTONNAMES = {   "zeroPower",
            "strumBar",
            "escape"    };

    private SelectModule(){
        itemModel = new ItemModel();
        itemContainerModel = new ItemContainerModel();
        controller = new SelectController(itemModel, itemContainerModel, this);
        linkGuitar();

        try{
            setScene(controller.load());
        }catch(Exception e){
            System.out.println("Select controller could not load.");
        }
        setTitle("Select Mode");
    }

    public static SelectModule getInstance(){
        if(instance == null){
            synchronized (SelectModule.class){
                if(instance == null){
                    instance = new SelectModule();
                }
            }
        }
        return instance;
    }

    private void linkGuitar() {
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

}
