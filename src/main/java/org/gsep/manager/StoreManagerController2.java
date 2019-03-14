package org.gsep.manager;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StoreManagerController2 implements ActionListener {

    private StoreManagerFrame view;

    StoreManagerController2(StoreManagerFrame view){
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //Open file chooser
        JFileChooser jf2 = new JFileChooser();
        //File can only be in image format
        FileNameExtensionFilter filter2 = new FileNameExtensionFilter("png", "jpg");
        jf2.setFileFilter(filter2);

        int aa = jf2.showOpenDialog(null);
        //If chosen file if off allowed format
        if (aa == JFileChooser.APPROVE_OPTION) {
            view.f2 = jf2.getSelectedFile();
            //Set contents of textfield to the path of the file
            view.textField_2.setText(view.f2.getAbsolutePath());

        }
    }
}
