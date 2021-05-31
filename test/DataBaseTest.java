import com.company.Main;
import com.company.dataBase.DataBase;
import com.company.dataBase.managers.PersonManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

public class DataBaseTest {

    @Test
    @DisplayName("Проверка на подключение к бд")
    public void testDataBaseConnect(){
        DataBase dataBase = new DataBase();
        Exception exception = null;
        try (Connection c = dataBase.getConnection()){

        } catch (SQLException throwables) {
            exception = throwables;
        }

        Assertions.assertEquals(null,exception);
    }

    @Test
    @DisplayName("Проверка на кол-во записей, получаемых из бд")
    public void testDataBaseCount(){
        Assertions.assertEquals(33, new PersonManager(new DataBase()).getAll().size());
    }


}
