package com.company.ui;

import com.company.Main;
import com.company.entitys.Person;
import com.company.util.BaseForm;
import com.company.util.BaseSubForm;
import com.company.util.CustomModel;
import com.company.util.DialogMessage;
import org.jdatepicker.impl.DateComponentFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Properties;
import java.util.regex.Pattern;

public class EditClient extends BaseSubForm {
    private JTextField nameField;
    private JTextField sernameField;
    private JPanel mainPanel;
    private JTextField patField;
    private JCheckBox maleCheck;
    private JCheckBox femaleCheck;
    private JTextField placeBField;
    private JLabel dateLabel;
    private JButton addPhotoBut;
    private JTextField pathField;
    private JButton addBut;
    private JButton cancelBut;
    private File file;
    private UtilDateModel model;
    private CustomModel customModel;

    public EditClient(Integer width, Integer height, String title, BaseForm form, CustomModel model) throws HeadlessException {
        super(width, height, title, form);
        this.customModel = model;
        this.setContentPane(mainPanel);
        this.initAddPhotoBut();
        this.initCancelBut();
        this.initAddBut();
        this.checkBoxes();
        initDatePicker();



        this.pathField.setEnabled(false);




    }

    private void initAddPhotoBut(){
        addPhotoBut.addActionListener(e->{
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.showOpenDialog(this);
            pathField.setText(fileChooser.getSelectedFile()!=null?fileChooser.getSelectedFile().toString():"");
            file = fileChooser.getSelectedFile();
        });
    }

    private void initCancelBut(){
        cancelBut.addActionListener(e->{
            this.closeForm();
        });

    }

    private void initAddBut(){
        addBut.addActionListener(e->{

            String stringPat = "[а-яА-Я]+";


            if(!Pattern.compile(stringPat).matcher(sernameField.getText()).matches()){
                DialogMessage.showWarn(this,"Неправильно написана фамилия!");

            }else if(!Pattern.compile(stringPat).matcher(nameField.getText()).matches()){
                DialogMessage.showWarn(this,"Неправильно написано имя!");

            }else if(!Pattern.compile(stringPat).matcher(patField.getText()).matches() && !patField.getText().isEmpty()){
                DialogMessage.showWarn(this,"Неправильно написано отчество!");

            }else if(new Date().compareTo(model.getValue())!=1){
                DialogMessage.showWarn(this,"Ошибка в дате рождения!");

            }else if(placeBField.getText().isEmpty()) {
                DialogMessage.showWarn(this,"Ошибка в месте рождения!");
            }else {
                //String surname, String name, String patronymic, String genderCode, String placeOfBirth, String photoPath, Date birthdate



                Main.instance.getManager().addNewPerson(new Person(
                        sernameField.getText(),
                        nameField.getText(),
                        patField.getText(),
                        maleCheck.isSelected()?"м":"ж",
                        placeBField.getText(),
//                        !pathField.getText().isEmpty()?System.getProperty("user.dir").replace("\\","/")+"/"+file.getName():" ",
                        !pathField.getText().isEmpty()?pathField.getText().replace("\\","/"):" ",
                        model.getValue()
                ));
//                copyPhoto();
                customModel.setData(Main.instance.getManager().getAll());
                customModel.updateData();
                closeForm();




                System.out.println();
                System.out.println(new Date().compareTo(model.getValue()));
                System.out.println();
                System.out.println(sernameField.getText());
                System.out.println(nameField.getText());
                System.out.println(patField.getText());
                System.out.println(maleCheck.isSelected()?"Мужской":"Женский");
                System.out.println(placeBField.getText());
                System.out.println(model.getValue());
//                System.out.println(System.getProperty("user.dir").replace("\\","/")+"/"+file.getName());




            }







        });
    }

    private void copyPhoto(){
        if(file!=null){
            try {
//                System.out.println(AddClient.class.getClassLoader().getResource("images/"));
                Files.copy(file.toPath(),
                        Paths.get(AddClient.class.getClassLoader().getResource("images/").getPath().replace("/C","C")+file.getName()));

//                Files.copy(file.toPath(),
//                        Paths.get(System.getProperty("user.dir").replace("\\","/")+"/"+file.getName()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void checkBoxes(){
        maleCheck.setSelected(true);
        femaleCheck.setSelected(false);
        ButtonGroup group = new ButtonGroup();
        group.add(maleCheck);
        group.add(femaleCheck);
    }

    private void initDatePicker(){
        model = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today","Сегодня");
        model.setSelected(true);
        JDatePanelImpl datePanel = new JDatePanelImpl(model,p);
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateComponentFormatter());
        dateLabel.setLayout(new BorderLayout());
        dateLabel.add(datePicker, BorderLayout.CENTER);
        dateLabel.setMinimumSize(new Dimension(100,26));

    }



}
