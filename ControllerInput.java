//import java.awt.Color;
//import java.awt.Dimension;
//import java.awt.GridLayout;
//import javax.swing.JFrame;
//import javax.swing.JButton;
//import javax.swing.JPanel;
//import net.java.games.input.Component;
//import net.java.games.input.Controller;
//import net.java.games.input.ControllerEnvironment;
///*
// * Plastic guitar input processing (Sony PS3).
// *
// * @author  Abigail Lilley
// * @version 1.00, February 2019.
// *
// *   $ CLASSPATH=jinput-2.0.9.jar:.
// *   $ export CLASSPATH
// *   $ javac PlasicGuitar.java
// *   $ java -Djava.library.path=. PlasicGuitar
// */
//public abstract class ControllerInput {
//    final static String GUITAR_HERO = "Guitar Hero"; /* Identifier       */
//    final static int DELAY = 50*20;            /* 5th of a second */  //TODO change this poll rate
//
//    /*
//     * Poll forever, and storing and displaying values of components.
//     */
//    private static void pollForever(Controller ctrl) {
//        Component[] cmps = ctrl.getComponents();
//        float[] newVals = new float[cmps.length];
//        float[] oldVals = new float[cmps.length];
//
//        int help = 0;
//        //TODO delete this
//
//        while (true) {
//            if (ctrl.poll()) {
//                System.out.println("-----------------------------    " + help); //TODO delete this
//                for (int i = 0; i < cmps.length; i = i + 1) { /* store */
//                    newVals[i] = cmps[i].getPollData();
//                }
//
//                for (int i = 0; i < cmps.length; i = i + 1) { /* process input */
//                    float newVal = newVals[i];
//                    float oldVal = oldVals[i];
//                    //System.out.println("Value of input " + i + ": " + val);
//
//                    if newVal != oldVal {
//                        switch (i) {
//                            //TODO in each of these put the name of a method relevant to a given button
//                            // get a note if you press fret and strum bar at same time..
//                            case 1:
//                                    break;
//                            case 2:
//                                    break;
//                            case 3:
//                                    break;
//                            case 4:
//                                    break;
//                            case 5:
//                                    break;
//                            case 6:
//                                    break;
//                            case 7:
//                                    break;
//                            case 8:
//                                    break;
//                            case 9:
//                                    break;
//                            case 10:
//                                    break;
//                            case 11:
//                                    break;
//                            case 12:
//                                    break;
//                            case 13:
//                                    break;
//                            case 14:
//                                    break;
//                            case 15:
//                                    break;
//                            case 16:
//                                    break;
//                            case 17:
//                                    break;
//                        }
//
//                    }
//
//                }
//
//                try { /* delay */
//                    Thread.sleep(DELAY);
//                } catch (Exception exn) {
//                    System.out.println(exn);
//                    System.exit(1);
//                }
//            } help++;                               //TODO delete this
//        }
//    }
//
//    /**
//     * @param args the command line arguments
//     */
//    public static void main(String[] args) {
//        ControllerEnvironment cenv = ControllerEnvironment.getDefaultEnvironment();
//        Controller[] ctrls = cenv.getControllers();
//
//        for (Controller ctrl : ctrls) {
//            if (ctrl.getName().contains(GUITAR_HERO)) {
//
//                pollForever(ctrl);
//            }
//        }
//
//        System.out.println(GUITAR_HERO + " controller not found");
//        System.exit(1);
//    }
//}
//
//
///*
//HELLO THIS IS A NOTE TO SELF BC I CAN"T HOLD THOUGHTS IN MA BRAIN
//
//this should be an abstract class, with a static variable array holding the float values
//
//extended by each of the classes for play mode etc.
//
//volatile too??
//
//abstract method called.. ButtonOutput?
//
//
//method instead of print, do a if __ != 0, call abstract method button1 pressed
//
// */