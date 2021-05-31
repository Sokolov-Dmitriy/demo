package com.company;

import com.company.dataBase.DataBase;
import com.company.dataBase.managers.PersonManager;
import com.company.ui.AddClient;
import com.company.ui.Remind;
import com.company.ui.Table;
import com.company.util.FontUtil;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.util.Enumeration;

public class Main {

    public static Main instance;
    private DataBase dataBase;
    private PersonManager manager;
    private String pass = "1234";

    public PersonManager getManager() {
        return manager;
    }

    public static void main(String[] args) {
        System.out.println(AddClient.class.getClassLoader().getResource("images/"));
	// write your code here
        new Main();
        instance.manager.getAll();
    }

    public Main() {
        instance = this;
        dataBase = new DataBase();
        manager = new PersonManager(dataBase);
//        Enumeration keys = UIManager.getDefaults().keys();
//        while(keys.hasMoreElements()){
//            Object key = keys.nextElement();
//            Object value = UIManager.get(key);
//            if(value instanceof FontUIResource)
//            UIManager.put(key,new FontUIResource("Comic Sans MS", Font.TRUETYPE_FONT, 12));
//        }
        FontUtil.changeAllFonts(new FontUIResource("Comic Sans MS", Font.TRUETYPE_FONT, 12));
        if(JOptionPane.showInputDialog(
                null,
                "Ввдите пароль админа если знаете",
                "Режим админа",
                JOptionPane.QUESTION_MESSAGE
        ).equals(pass)) {
            new Table(1000, 600, "ITMO");
        }
//        new Remind(500,200,"тест");

    }
}
