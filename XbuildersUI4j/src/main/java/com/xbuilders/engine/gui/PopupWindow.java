/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xbuilders.engine.gui;

import com.xbuilders.engine.utils.ResourceUtils;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author zipCoder933
 */
public class PopupWindow {


    public static void createPopupWindow(String title, String str) {

        final JFrame parent = new JFrame();
        JLabel label = new JLabel("");
        label.setText("<html><body style='padding:5px;'>" + str.replace("\n", "<br>") + "</body></html>");
        label.setFont(new Font("Arial", 0, 12));
        label.setVerticalAlignment(JLabel.TOP);
        parent.add(label);
        parent.pack();
        parent.getContentPane().setBackground(Color.white);
        parent.setVisible(true);
        parent.pack();
        List<Image> icons = new ArrayList<Image>();
        icons.add(new ImageIcon(ResourceUtils.resourcePath("logo.png")).getImage());
//        icons.add(new ImageIcon(FileManager.getProgramPath() + "icon20.png").getImage());
//        icons.add(new ImageIcon(FileManager.getProgramPath() + "icon32.png").getImage());
//        icons.add(new ImageIcon(FileManager.getProgramPath() + "icon40.png").getImage());
        parent.setIconImages(icons);
        parent.setTitle(title);
        parent.setLocationRelativeTo(null);
        parent.setAlwaysOnTop(true);
        parent.setVisible(true);
        parent.setSize(380, 240);
    }
}
