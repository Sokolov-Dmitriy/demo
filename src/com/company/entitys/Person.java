package com.company.entitys;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Date;

public class Person {
    //id, surname, name, patronymic, gender_code, birthdate, place_of_birth, photo_path
    private Integer id;
    private String surname;
    private String name;
    private String patronymic;
    private String genderCode;
    private String placeOfBirth;
    private String photoPath;
    private Date birthdate;
    private ImageIcon image;


    private void initImage() {
        URL url = null;
        if (Person.class.getClassLoader().getResource(photoPath)!=null) url = Person.class.getClassLoader().getResource(photoPath);
        else {
            try {

                url = new File(photoPath).toURL();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        if (url != null) {
            try {

                if (ImageIO.read(url) != null) {
                    Image scaled = ImageIO.read(url).getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                    image = new ImageIcon(scaled);
                }
            } catch (IOException e) {
                e.printStackTrace();
                image = null;
            }
        }
    }

    public Person(Integer id, String surname, String name, String patronymic, String genderCode, String placeOfBirth, String photoPath, Date birthdate) {
        this.id = id;
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.genderCode = genderCode;
        this.placeOfBirth = placeOfBirth;
        this.photoPath = photoPath;
        this.birthdate = birthdate;

        this.initImage();
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", surname='" + surname + '\'' +
                ", name='" + name + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", genderCode='" + genderCode + '\'' +
                ", placeOfBirth='" + placeOfBirth + '\'' +
                ", photoPath='" + photoPath + '\'' +
                ", birthdate=" + birthdate +
                ", image=" + image +
                '}';
    }

    public Person(String surname, String name, String patronymic, String genderCode, String placeOfBirth, String photoPath, Date birthdate) {
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.genderCode = genderCode;
        this.placeOfBirth = placeOfBirth;
        this.photoPath = photoPath;
        this.birthdate = birthdate;
        this.id = -1;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public void setGenderCode(String genderCode) {
        this.genderCode = genderCode;
    }

    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public void setImage(ImageIcon image) {
        this.image = image;
    }

    public Integer getId() {
        return id;
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public String getGenderCode() {
        return genderCode;
    }

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public ImageIcon getImage() {
        return image;
    }
}
