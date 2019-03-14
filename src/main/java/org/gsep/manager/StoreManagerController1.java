package org.gsep.manager;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StoreManagerController1 implements ActionListener {

    private StoreManagerFrame view;
    StoreManagerController1(StoreManagerFrame view){
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //Open file chooser
        JFileChooser jf1 = new JFileChooser();
        //File can only be in text format
        FileNameExtensionFilter filter1 = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
        jf1.setFileFilter(filter1);

        int aa = jf1.showOpenDialog(null);
        //If chosen file if off allowed format
        if(aa==JFileChooser.APPROVE_OPTION) {
            view.f1=jf1.getSelectedFile();
            //Set contents of textfield to the path of the file
            view.textField_1.setText(view.f1.getAbsolutePath());
        }
    }
}
