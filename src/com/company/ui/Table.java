package com.company.ui;

import com.company.Main;
import com.company.entitys.Person;
import com.company.util.BaseForm;
import com.company.util.CustomModel;
import com.company.util.DialogMessage;
import com.mysql.cj.xdevapi.Column;
import org.jdatepicker.impl.*;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableColumn;
import javax.swing.text.DateFormatter;
import javax.swing.text.DefaultFormatter;
import javax.swing.text.InternationalFormatter;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.*;
import java.sql.PreparedStatement;
import java.text.ParseException;
import java.util.*;
import java.util.function.Predicate;

public class Table extends BaseForm {
    private JTable table;
    private JPanel mainPanel;
    private JTextField search;
    private JButton idSortBut;
    private JComboBox comboBoxFilter;
    private JLabel labelDate;
    private JButton addNewBut;
    private JButton aboutUsBut;
    private JLabel countLabel;
    private JComboBox placeBComBox;
    private JButton authDialog;
    private JButton warnDialog;
    private JButton infDialog;
    private JButton askDialog;
    private JButton delPredBut;
    //    private JPanel datePanel;
    public CustomModel<Person> model;
    private Boolean idSortFlag = true;
    private Table self;
    //id, surname, name, patronymic, gender_code, birthdate, place_of_birth, photo_path
    private String[] headers = new String[]{"id", "Фамилия", "Имя", "Отчество", "Пол", "Место рождения", "photo_path", "Дата рождения", "Фото"};

    public Table(Integer width, Integer height, String title) throws HeadlessException {
        super(width, height, title);
        this.model = new CustomModel<>(headers, Person.class, Main.instance.getManager().getAll()) {
            @Override
            public void onUpdateCustomEvent() {
                countLabel.setText("Кол-во записей в таблице:" + model.getRowCount());
//                initPlaceBComBox();
            }
        };
        this.table.setModel(model);
        this.setContentPane(mainPanel);
        this.initPredicate();
        this.initSearchTextField();
        this.initIdSortBut();
        this.initComboBoxFilter();
        this.initDeleteFunction();
        this.initAddNewBut();
        initAboutUsBut();
        initPlaceBComBox();
        initDialog();
        initSkipPredBut();



        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getTableHeader().setReorderingAllowed(false);
        table.setRowHeight(100);
        TableColumn column = table.getColumn("Фото");
        column.setPreferredWidth(100);
        column.setMaxWidth(100);
        column.setWidth(100);
        countLabel.setText("Кол-во записей в таблице:" + model.getRowCount());


        TableColumn column1 = table.getColumn("photo_path");
        column1.setMinWidth(0);
        column1.setMaxWidth(0);
        column1.setPreferredWidth(0);




        this.self = this;


        UtilDateModel model = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Сегодня");
        model.setSelected(true);
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateComponentFormatter());
        labelDate.setLayout(new BorderLayout());
        labelDate.add(datePicker, BorderLayout.CENTER);
        labelDate.setMinimumSize(new Dimension(100, 26));


        this.setVisible(true);
    }

    private void initSearchTextField() {
        search.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                model.updateData();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                model.updateData();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                model.updateData();
            }
        });
    }

    private void initPredicate() {
        model.getPredicates()[0] = person -> {
            if (search.getText().isEmpty() || search == null) return true;
            String string = (person.getSurname() + " " + person.getName() + " " + person.getPatronymic()).toLowerCase();
            return string.contains(search.getText().toLowerCase());
        };
    }

    private void initIdSortBut() {
        idSortBut.addActionListener(e -> {
            if (idSortFlag) {
                model.setComparator(Comparator.comparingInt(Person::getId));
                idSortFlag = false;
            } else {
                model.setComparator((o1, o2) -> Integer.compare(o2.getId(), o1.getId()));
                idSortFlag = true;
            }
            model.updateData();
        });
    }

    private void initComboBoxFilter() {
        this.comboBoxFilter.addItem("Оба пола");
        this.comboBoxFilter.addItem("Женский");
        this.comboBoxFilter.addItem("Мужской");
        comboBoxFilter.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                int index = comboBoxFilter.getSelectedIndex();
                if (index == 0) model.getPredicates()[1] = null;
                else if (index == 1) {
                    model.getPredicates()[1] = person -> person.getGenderCode().equals("ж");
                } else if (index == 2) {
                    model.getPredicates()[1] = person -> person.getGenderCode().equals("м");
                }
                model.updateData();
            }
        });
    }

    private void initDeleteFunction() {
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (table.getSelectedRow() != -1 && e.getClickCount() == 2) {
                    if (JOptionPane.showOptionDialog(null, "Подтвердите удаление", " Подтверждение",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            new String[]{"Подтверждение", "Отмена"},
                            "Подтверждение") == JOptionPane.YES_OPTION) {

                        Main.instance.getManager().deleteById((Integer) model.getValueAt(table.getSelectedRow(), 0));
                        JOptionPane.showMessageDialog(self, "Удаление успешено выполнено!", " Информация", JOptionPane.INFORMATION_MESSAGE);
                        model.getData().removeIf(new Predicate<Person>() {
                            @Override
                            public boolean test(Person person) {
                                return person.getId() == model.getValueAt(table.getSelectedRow(), 0);
                            }
                        });

                        model.updateData();
                        initPlaceBComBox();

                    }
                }
            }
        });
    }

    private void initAddNewBut() {
        addNewBut.addActionListener(e -> {
            new AddClient(500, 500, "sdfsds", self, model);

//            new EditClient(500,500,"sdfsds",self,model);
        });
    }

    private void initAboutUsBut() {
        aboutUsBut.addActionListener(e -> {
            DialogMessage.showInfo("gerwefwfwefw efgrewef werfgwefwe wegerfwe wef efwefwerf");
        });
    }

    private void initPlaceBComBox() {
        placeBComBox.removeAllItems();
        placeBComBox.addItem("Все варианты");
        Set<String> set = new HashSet<>();
        for (Person person : model.getFilteredData()) {
            set.add(person.getPlaceOfBirth());
        }
        for (String s : set) {
            placeBComBox.addItem(s);
        }

        placeBComBox.addItemListener(e->{
            if(e.getStateChange()==ItemEvent.SELECTED){
                String string = (String) e.getItem();
                model.getPredicates()[2] = new Predicate<Person>() {
                    @Override
                    public boolean test(Person person) {
                        if(string.equals("Все варианты")) return true;
                        return person.getPlaceOfBirth().equals(string);
                    }
                };
            }
            model.updateData();
        });


    }

    private void initDialog(){
        this.authDialog.addActionListener(e->{
            if(JOptionPane.showInputDialog(null,"Введите пароль, если знаете","Авторизация",JOptionPane.QUESTION_MESSAGE).equals("0000")){
                System.out.println("да");
            }
        });
        this.askDialog.addActionListener(e->{
            if(JOptionPane.showConfirmDialog(null,"Вы точно хотите удалить?","Подтверждение удаления",JOptionPane.YES_NO_OPTION)==JOptionPane.OK_OPTION){
                System.out.println("да");
            }
        });

        this.warnDialog.addActionListener(e->{
            JOptionPane.showMessageDialog(null,"Ошибка в данных","Предупреждение",JOptionPane.WARNING_MESSAGE);
        });

        this.infDialog.addActionListener(e->{
            JOptionPane.showMessageDialog(null,"какая-то информация","Информация",JOptionPane.INFORMATION_MESSAGE);
        });
    }

    public void initSkipPredBut(){
        this.delPredBut.addActionListener(e->{
            model.setComparator(null);
            search.setText("");
            for(Predicate<Person> predicate : model.getPredicates()){
                predicate=null;
            }
            placeBComBox.setSelectedIndex(0);
            comboBoxFilter.setSelectedIndex(0);
            model.updateData();
            initPlaceBComBox();
        });
    }


}

