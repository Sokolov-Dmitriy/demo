package com.company.dataBase.managers;

import com.company.dataBase.DataBase;
import com.company.entitys.Person;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class PersonManager {
    DataBase dataBase;

    public PersonManager(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    //id, surname, name, patronymic, gender_code, birthdate, place_of_birth, photo_path
    public List getAll(){
        List persons = new ArrayList<Person>();
        try(Connection connection = dataBase.getConnection()) {
            String query = "SELECT * FROM Person";
            Statement statement  = connection.createStatement();
            ResultSet set = statement.executeQuery(query);
            while (set.next()){
                persons.add(new Person(
                        set.getInt("id"),
                        set.getString("surname"),
                        set.getString("name"),
                        set.getString("patronymic"),
                        set.getString("gender_code"),
                        set.getString("place_of_birth"),
                        set.getString("photo_path"),
                        set.getDate("birthdate")
                ));
            }
            return persons;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return persons;
    }

    public Boolean deleteById(Integer id){
        deleteByIdFromHistory(id);
        try(Connection c = dataBase.getConnection()){
            String query = "DELETE FROM Person WHERE id = ?";
            PreparedStatement statement = c.prepareStatement(query);
            statement.setInt(1,id);
            return statement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public Boolean deleteByIdFromHistory(Integer id){
        try(Connection c = dataBase.getConnection()){
            String query = "DELETE FROM History WHERE person_id = ?";
            PreparedStatement statement = c.prepareStatement(query);
            statement.setInt(1,id);
            return statement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public Boolean deleteByObject(Person person){
        return deleteById(person.getId());
    }


//    private String surname;
//    private String name;
//    private String patronymic;
//    private String genderCode;
//    private String placeOfBirth;
//    private String photoPath;
//    private Date birthdate;
//    private ImageIcon image;
    public void addNewPerson(Person person){
        try(Connection connection = dataBase.getConnection()){
            String query = "INSERT INTO Person (surname, name, patronymic, gender_code, birthdate, place_of_birth, photo_path) VALUES (?,?,?,?,?,?,?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1,person.getSurname());
            statement.setString(2,person.getName());
            statement.setString(3,person.getPatronymic());
            statement.setString(4,person.getGenderCode());
            statement.setTimestamp(5,new Timestamp(person.getBirthdate().getTime()));
            statement.setString(6,person.getPlaceOfBirth());
            statement.setString(7,person.getPhotoPath());
            statement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
