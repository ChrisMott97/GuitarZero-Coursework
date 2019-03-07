import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import java.util.ArrayList;

public class ADayInTheLife {
    final static String GUITAR_HERO = "Guitar Hero"; /* Identifier       */
    final static int DELAY = 50; 
    public static void main( String [] args ) {
		
	    
		
        //Button button1 = new Button();
        //ButtonListener  playMode   = new PlayMode();				//IMPORTANTTTTTTTTTTT
        //button1.addButtonListener( playMode );
        
        // System.out.println( "Press button1:" );
//         button1.buttonOn();
//
//         System.out.println( "\nRelease button1:\n" );
//         button1.buttonOff();


ControllerEnvironment cenv = ControllerEnvironment.getDefaultEnvironment();
        Controller[] ctrls = cenv.getControllers();

        for (Controller ctrl : ctrls) {
            if (ctrl.getName().contains(GUITAR_HERO)) {				

				String[] buttonNames = { "fret1_white",
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
						 				 "whammy"	    };
				int[] buttonNums = { 0, 1, 4, 2, 5, 3, 8, 15, 10, 12, 13, 17};
				

				Component[] cmps = ctrl.getComponents();
				Button[] 	 buttons = new Button[ buttonNames.length ];
				float[]      oldVals = new float[  buttonNames.length ]; 
				float[]      newVals = new float[  buttonNames.length ];
			 	ButtonListener  playMode   = new PlayMode();
				
				for ( int i = 0; i < buttons.length; i = i + 1 ) {
					buttons[ i ] = new Button( buttonNames[i], buttonNums[i]);
					buttons[ i ].addButtonListener( playMode );
					//System.out.println( buttons[i].getName() + " " + buttons[i].getNum());
				}

                
				 				
				 for ( int i = 0; i < buttons.length; i = i + 1 ) { /* store */
					 int j = buttons[ i ].getNum();
				 		oldVals[ i ] = cmps[j].getPollData();
						//System.out.println( oldVals[i]);
				 	    }
						//System.out.println( "\n");
						
				
				
				
				//Button fret1_white = new Button( "fret1_white" );
				//buttons.add(fret1_white);
				
				
				// Button fret1_black = (buttons[ 1 ]).setName( fret1_black );
				// Button fret2_white = buttons[ 4 ].setName( fret1_white );
				// Button fret2_black = buttons[ 2 ].setName( fret1_black );
				// Button fret3_white = buttons[ 5 ].setName( fret1_white );
				// Button fret3_black = buttons[ 3 ].setName( fret1_black );
				// Button zeroPower   = buttons[ 8 ].setName( zeroPower );
				// Button strumBar    = buttons[ 15 ].setName( strumBar );
				// Button escape      = buttons[ 10 ].setName( escape );
				// Button power       = buttons[ 12 ].setName( power );
				// Button bender      = buttons[ 13 ].setName( bender );
				// Button whammy	   = buttons[ 17 ].setName( whammy );
						
						
				 

						
				

				        while (true) {
				            if (ctrl.poll()) {

								for ( int i = 0; i < buttons.length; i = i + 1 ) { /* store */
									int j = buttons[ i ].getNum();
									
									
									newVals[ i ] = cmps[ j ].getPollData();
									//System.out.println( newVals[i]);
										  
		  				                if (oldVals[i] != newVals[i]) {

		  				                    if (newVals[i] != 0.0) {
		  				                        buttons[ i ].buttonOn();
		  				                    } else if (newVals[i] == 0.0) {
		  								        buttons[ i ].buttonOff();
		  				                    } else {
		  								        System.out.println( "Something wroonngggg :"+newVals[i] );
		                    	
		  				                    }

		  				                    oldVals[i] = newVals[i];
		  				                }

		  				                try { /* delay */
		  				                    Thread.sleep(DELAY);
		  				                } catch (Exception exn) {
		  				                    System.out.println(exn);
		  				                    System.exit(1);
		  				                }
								        }
										
				                
				            }
				        }


            }
        }

        System.out.println(GUITAR_HERO + " controller not found");
        System.exit(1);



		
		
		

        
    }
    
}