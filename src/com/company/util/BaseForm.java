package com.company.util;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.URL;

public class BaseForm extends JFrame {
    private Integer width;
    private Integer height;
    private String title;

    public BaseForm(Integer width, Integer height, String title) throws HeadlessException {
        this.width = width;
        this.height = height;
        this.title = title;
        this.initBaseFormSetting();
    }

    private void initBaseFormSetting(){
        this.setTitle(title);
        this.setMinimumSize(new Dimension(this.width,this.height));
        this.setLocation(
                Toolkit.getDefaultToolkit().getScreenSize().width/2-this.width/2,
                Toolkit.getDefaultToolkit().getScreenSize().height/2-this.height/2
        );
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        URL url = BaseForm.class.getClassLoader().getResource("favicon.png");
        try {
            this.setIconImage(ImageIO.read(url).getScaledInstance(100,100,Image.SCALE_SMOOTH));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
