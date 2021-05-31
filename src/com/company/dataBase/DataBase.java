package com.company.dataBase;

import com.mysql.cj.jdbc.MysqlDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DataBase {
    private String address = "127.0.0.1";
    private String userName = "root";
    private String dataBaseName = "demo1";
    private String dataBasePass = "1234";
    private Integer port = 3306;
    private String encoding = "UTF-8";
    private MysqlDataSource source;

    public Connection getConnection() throws SQLException {
        if(source == null){
            try {
                source = new MysqlDataSource();
                source.setServerName(address);
                source.setDatabaseName(dataBaseName);
                source.setUser(userName);
                source.setPassword(dataBasePass);
                source.setPort(port);
                source.setCharacterEncoding(encoding);
                return source.getConnection();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return source.getConnection();
    }


}
