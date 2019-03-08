package org.gsep.controller;
import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;

/**
 * Connects to plastic guitar controller and links the listeners to useful input.
 *
 *   $ CLASSPATH=jinput-2.0.9.jar:.
 *   $ export CLASSPATH
 *   $ javac GuitarController.java
 *   $ java -Djava.library.path=. GuitarController
 *
 * @author  Abigail Lilley
 * @version 1.0, March 2019.
 */
public class GuitarController {

	private final static String[] BUTTONNAMES = { 	"fret1_white",
													"fret1_black",
													"fret2_white",
													"fret2_black",
													"fret3_white",
													"fret3_black",
													"zeroPower",
													"strumBar",
													"escape",
													"power",
													"bender",
													"whammy"	    	};

	private final static int[] BUTTONNUMS = { 0, 1, 4, 2, 5, 3, 8, 15, 10, 12, 13, 17};
					/* Index of the component in the component array. Order corresponds to the names in BUTTONNAMES */

    public static void main( String [] args ) {

        ControllerEnvironment cenv = ControllerEnvironment.getDefaultEnvironment();
        Controller[] ctrls = cenv.getControllers();

		Button[] 	 buttons = new Button[ BUTTONNAMES.length ];
		ButtonListener  playMode   = new PlayMode();									/* Creating ButtonListeners */
		ButtonListener slashMode   = new SlashMode();

		for ( int i = 0; i < buttons.length; i = i + 1 ) {
			buttons[ i ] = new Button( BUTTONNAMES[i], BUTTONNUMS[i]);
			if ( i==6 || i==7 || i==8 ) {
				buttons[ i ].addButtonListener ( slashMode );
			}
			buttons[ i ].addButtonListener( playMode );			/* Adding listeners to Buttons depending on the mode */
			Thread buttonThread = new Thread(buttons[ i ]);
			buttonThread.start();								/* Starting a thread for each Button */
		}
    }
}