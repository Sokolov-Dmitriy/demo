package com.company.ui;

import com.company.util.BaseForm;
import org.jdatepicker.AbstractDateModel;
import org.jdatepicker.DateModel;
import org.jdatepicker.JDatePicker;
import org.jdatepicker.impl.DateComponentFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.SqlDateModel;

import javax.swing.*;
import javax.swing.text.InternationalFormatter;
import java.awt.*;
import java.util.Calendar;
import java.util.Properties;

public class Remind extends BaseForm {
    private JLabel dateLabel;
    private JPanel mainPanel;

    public Remind(Integer width, Integer height, String title) throws HeadlessException {
        super(width, height, title);
        DateModel dateModel = new SqlDateModel();
        Properties p = new Properties();
        p.put("text.today","Сегодня");
        JDatePanelImpl jDatePanel = new JDatePanelImpl(dateModel,p);
        JDatePickerImpl datePicker = new JDatePickerImpl(jDatePanel,new DateComponentFormatter());
        dateModel.setSelected(true);
        dateLabel.setLayout(new BorderLayout());
        dateLabel.add(datePicker,BorderLayout.CENTER);
        dateLabel.setMinimumSize(new Dimension(100,28));
        this.setContentPane(mainPanel);
        this.setVisible(true);
    }
}
