package org.gsep.manager;
import org.gsep.midi.guitarMIDI;
import org.gsep.store.Store;

import java.awt.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.io.*;
import java.util.ArrayList;
import java.awt.event.*;

/*
 * StoreManagerFrame
 *
 * @author Humzah Malik
 * @version 1.0
 *
 */
public class StoreManagerFrame {

	JFrame frame;
	///Declaring fields holding file paths as a String and files as a File object
	JTextField textField_1, textField_2, textField_3 = null;
	File f1, f2, f3;


	/**
	 * Method calling create(), which in turn invokes the JFrame.
	 */
	//Launch application
	public static void create() {

		EventQueue.invokeLater(() -> {
			try {
				StoreManagerFrame window = new StoreManagerFrame();
				window.frame.setVisible(true);
			} catch (Exception e) {
				System.out.println("Error in starting the application. Please try again.");
			}
		});
	}

	/**
	 * Method that invokes the initializing of the JFrame.
	 */
	public StoreManagerFrame() {
		initialize();
	}

	/**
	 * Method containing information on the structure of the JFrame and its functionality. Method also dynamically calls Client class. 
	 */
	private void initialize() {
		//Setting bounds of JFrame
		frame = new JFrame("Store Manager");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		//Creating text fields to hold file paths
		textField_1 = new JTextField();
		textField_1.setBounds(96, 41, 219, 26);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);

		textField_2 = new JTextField();
		textField_2.setBounds(96, 97, 215, 26);
		frame.getContentPane().add(textField_2);
		textField_2.setColumns(10);

		textField_3 = new JTextField();
		textField_3.setBounds(96, 148, 219, 26);
		frame.getContentPane().add(textField_3);
		textField_3.setColumns(10);

		//Create first button to browse for .txt file
		JButton nameButton = new JButton("Browse");
		nameButton.addActionListener(new StoreManagerController1(this));
		nameButton.setBounds(332, 42, 112, 26);
		frame.getContentPane().add(nameButton);

		//Create second button to browse for .txt file
		JButton coverButton = new JButton("Browse");
		coverButton.addActionListener(new StoreManagerController2(this));
		coverButton.setBounds(327, 97, 117, 29);
		frame.getContentPane().add(coverButton);

		//Create third button to browse for .txt file
		JButton musicButton = new JButton("Browse");
		musicButton.addActionListener(new StoreManagerController3(this));
		musicButton.setBounds(327, 148, 117, 29);
		frame.getContentPane().add(musicButton);

		//Create labels for text fields
		JLabel lblTitle = new JLabel("Title");
		lblTitle.setBounds(22, 46, 61, 16);
		frame.getContentPane().add(lblTitle);

		JLabel lblCover = new JLabel("Cover Art");
		lblCover.setBounds(22, 102, 61, 16);
		frame.getContentPane().add(lblCover);

		JLabel lblMusic = new JLabel("Music");
		lblMusic.setBounds(22, 153, 61, 16);
		frame.getContentPane().add(lblMusic);

		//Create submit button
		JButton btnSave = new JButton("Save");

		btnSave.addActionListener(new StoreManagerController4(this));
		btnSave.setBounds(149, 212, 117, 29);
		frame.getContentPane().add(btnSave);

	}
}