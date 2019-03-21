package org.gsep.manager;

import org.gsep.midi.guitarMIDI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class StoreManagerController4 implements ActionListener {

    private StoreManagerFrame view;
    private guitarMIDI gM;

    //Declare booleans used to validate file entry
    private static boolean empty;
    private static boolean invalid;

    //Initialise ArrayList to hold files and Array to hold file paths
    private ArrayList<File> files = new ArrayList<>();

    StoreManagerController4(StoreManagerFrame view){
        this.view = view;
        this.gM = new guitarMIDI();
    }
    public void actionPerformed(ActionEvent e) {
        String f1_path, f2_path, f3_path;

        //Create a array holding file paths
        f1_path = view.textField_1.getText();
        f2_path = view.textField_2.getText();
        f3_path = view.textField_3.getText();
        String[] filePaths = {f1_path, f2_path, f3_path};

        //Validation 1- Check fields contain a non-empty string
        for(int i=0; i< 3; i++){
            if(filePaths[i].length()==0) {
                System.out.println("Text field number " +(i+1) +" has been left empty");
                empty = true;
            }
        }
        //If field does contain an empty string, close application and give warning.
        if (empty) {
            System.out.println("This application has closed. Please next time ensure ALL fields contain a file");
            view.frame.dispose();
            return;
        }
        //Validation 2- Ensure files both exist and are of the required format
        checkF(f1_path, 1);
        checkF(f2_path, 2);
        checkF(f3_path, 3);

        //If files are invalid, break.
        if (invalid) {
            System.out.println("This application has closed. Please next time ensure all fields submit a VALID file.");
            view.frame.dispose();
            return;
        }

        //Add valid files to array list
        for(int i=0; i< 3; i++){
            files.add(new File(filePaths[i]));
        }

        //Create proprietry file from midi file and add that to list of files
        File noteFile = gM.convertMIDI(files.get(2).getAbsolutePath());
        files.add(noteFile);

        //Run method within Client
        try {
            StoreManagerModel storeManagerModel = new StoreManagerModel(files);

            storeManagerModel.sendFile();

        } catch (IOException e1) {
            System.out.println("There is an error with the uploaded files. Please try again and reupload them.");

        }

        //How to close the application
        System.exit(0);
        view.frame.dispose();
    }


    /**
     * Method validating the file submitted, within the first field, from the StoreManager application.
     * Ensures it is in the correct format
     * @param s: The file path to be checked
     */
    private static void checkF(String s, int Case) {
        File f = new File(s);

        //Switch statement
        switch(Case) {
            case 1:

                //Check file exists and is not a directory
                if(f.exists() && !f.isDirectory()) {
                    //Ensure suffix is of correct notation
                    if (!s.endsWith(".txt")) {
                        System.out.println("The first file must be of .txt format.");
                        invalid = true;
                        return;
                    }
                }

                else{
                    System.out.println("The first file does not exist");
                    invalid = true;
                    return;
                }
                break;
            case 2:
                //Check file exists and is not a directory
                if(f.exists() && !f.isDirectory()) {
                    //Ensure suffix is of correct notation
                    if (!(s.endsWith(".png") || s.endsWith(".jpg"))) {
                        System.out.println("The second submitted file must be of .png or of .jpg format.");
                        invalid = true;
                        return;
                    }
                }

                else{
                    System.out.println("The second submitted file does not exist");
                    invalid = true;
                    return;
                }
                break;

            case 3:
                //Check file exists and is not a directory
                if(f.exists() && !f.isDirectory()) {
                    //Ensure suffix is of correct notation
                    if (!s.endsWith(".mid")) {
                        System.out.println("The third submitted file must be of .mid format.");
                        invalid = true;
                        return;
                    }
                }

                else{
                    System.out.println("The third submitted file does not exist");
                    invalid = true;
                    return;
                }
                break;
        }
    }

    /**
     * Checks first line of file for error message
     * @param file
     * @return 
     */
    private static boolean checkNotes(File file) {
    	 	boolean noNotes = false;
	    	BufferedReader br;
			try {
				br = new BufferedReader(new FileReader(file));
			 	String text = br.readLine();
			 	
			 	
			 	if (text.equals("No guitar instrument available")) {
			 		noNotes = true;
			 	}
			} catch (FileNotFoundException e) {
				System.out.println("Your notes file does not exist");
			} catch (IOException e) {
				System.out.println("Your file contains invalid content");
			}
	   
			return noNotes;
    		
    }
}
