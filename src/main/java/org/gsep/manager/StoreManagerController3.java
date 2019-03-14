package org.gsep.manager;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StoreManagerController3 implements ActionListener {


    private StoreManagerFrame view;

    StoreManagerController3(StoreManagerFrame view){
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //Open file chooser
        JFileChooser jf3 = new JFileChooser();
        //File can only be in midi format
        FileNameExtensionFilter filter3 = new FileNameExtensionFilter("midi", "mid");
        jf3.setFileFilter(filter3);

        int aa = jf3.showOpenDialog(null);
        //If chosen file if off allowed format
        if (aa == JFileChooser.APPROVE_OPTION) {
            view.f3 = jf3.getSelectedFile();
            //Set contents of textfield to the path of the file
            view.textField_3.setText(view.f3.getAbsolutePath());
        }
    }
}
