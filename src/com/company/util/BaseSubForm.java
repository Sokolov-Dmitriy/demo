package com.company.util;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class BaseSubForm<T extends BaseForm> extends BaseForm{
    private BaseForm form;
    public BaseSubForm(Integer width, Integer height, String title, BaseForm form) throws HeadlessException {
        super(width, height, title);
        this.form = form;

        form.setEnabled(false);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                closeForm();
            }
        });
        this.setVisible(true);
    }

    protected void closeForm(){
        form.setEnabled(true);
        form.setVisible(false);
        form.setVisible(true);
        this.dispose();

    }
}
