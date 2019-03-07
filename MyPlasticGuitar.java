import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
/*
 * Plastic guitar test (Sony PS3).
 *
 * @author  David Wakeling
 * @version 1.00, January 2019.
 *
 *   $ CLASSPATH=jinput-2.0.9.jar:.
 *   $ export CLASSPATH
 *   $ javac PlasicGuitar.java
 *   $ java -Djava.library.path=. PlasicGuitar
 */
public class MyPlasticGuitar {
    final static String GUITAR_HERO = "Guitar Hero"; /* Identifier       */
    final static int DELAY = 50;            /* 20th of a second */
    static int mode = 1 ;
            /*
            1 = slash
            2 = select
            3 = store
            4 = play
             */


//    private static JButton[] buttons;
//
//    /*
//     * Make a frame of buttons for controller components.
//     */
//    private static JFrame makeFrame( Controller ctrl ) {
//        JFrame frm = new JFrame();
//        JPanel pan = new JPanel( new GridLayout( 0, 2 ) );
//
//        Component[] cmps = ctrl.getComponents();
//        buttons = new JButton[ cmps.length ];
//
//        for ( int i = 0; i < buttons.length; i = i + 1 ) {
//            JButton button = new JButton();
//            button.setPreferredSize( new Dimension( 100, 40 ) );
//            buttons[ i ] = button;
//            pan.add( button );
//        }
//
//        frm.add( pan );
//        frm.pack();
//
//        return frm;
//    }

    /*
     * Poll forever, and storing and displaying values of components.
     */
    private static void pollForever(Controller ctrl) {
        Component[] cmps = ctrl.getComponents();
        float[] vals = new float[cmps.length];
        float oldCarouselVal = 0.0f;

        while (true) {
            if (ctrl.poll()) {

                if (mode == 1 || mode == 2 || mode == 3) {

                    /*Carousel (Strum Bar*/
                    float newCarouselVal = cmps[15].getPollData();
                    if (oldCarouselVal != newCarouselVal) {

                        if (newCarouselVal == 1.0) {
                            System.out.println("Forward");
                        } else if (newCarouselVal == -1.0) {
                            System.out.println("Backward");
                        }

                        oldCarouselVal = newCarouselVal;
                    }

                    /*Escape Button*/
                    float escape = cmps[10].getPollData();
                    if (escape == 1.0) {
                        mode = 1;
                    }

                    /*Zero Power Buttom*/
                    float zeroPower = cmps[8].getPollData();
                    if ( zeroPower == 1.0 ) {
                        if (mode == 1) {        //slash
                            System.out.println("Item in middle of carousel selected (assuming its play mode)");
                            mode = 4;
                        } else if (mode == 2) {   //select
                            System.out.println("Item selected, returning to slash mode");
                            mode = 1;
                        } else {                  //store
                            System.out.println("Check user has enough in game currency\n if enough, download \n return to slash mode");
                            mode = 1;
                        }
                    }
                }

                if (mode == 4) {       //play mode
                    for (int i = 0; i < cmps.length; i = i + 1) { /* store */
                        vals[i] = cmps[i].getPollData();
                    }

                    /*Escape Button*/
                    float escape = vals[10];
                    if (escape == 1.0) {
                        mode = 1;
                    }

                    for (int i = 0; i < 6; i = i + 1) { /* display */
                        //System.out.println("check     " + i);

                        float val = vals[i];
                        if (val == 1.0 && vals[13] != 0.0) {
                            System.out.println("Button " + i + " strumed");
                        }
                    }
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


    /*
     * Poll forever, registering input only from the strum bar scrolling the carousel forward/backward.
     */
    private static void carousel(Controller ctrl) {
        Component[] cmps = ctrl.getComponents();
        float oldVal = 0.0f;

        while (true) {
            if (ctrl.poll()) {

                float newVal = cmps[15].getPollData();

                if (oldVal != newVal) {

                    if (newVal == 1.0) {
                        System.out.println("Forward");
                    } else if (newVal == -1.0) {
                        System.out.println("Backward");
                    }

                    oldVal = newVal;
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


    /**
     * @param args the command line arguments
     */
    public static void main(String[] argv) {
        ControllerEnvironment cenv = ControllerEnvironment.getDefaultEnvironment();
        Controller[] ctrls = cenv.getControllers();

        for (Controller ctrl : ctrls) {
            if (ctrl.getName().contains(GUITAR_HERO)) {
//                JFrame frm = makeFrame( ctrl );
//                frm.setVisible( true );

                /*slash or select mode*/
                pollForever(ctrl);


            }
        }

        System.out.println(GUITAR_HERO + " controller not found");
        System.exit(1);
    }
}
