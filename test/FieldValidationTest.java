import com.company.Main;
import com.company.dataBase.DataBase;
import com.company.dataBase.managers.PersonManager;
import com.company.entitys.Person;
import com.company.util.CustomModel;
import com.company.util.Validation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.xml.crypto.Data;
import java.util.Comparator;
import java.util.Date;
import java.util.function.Predicate;

public class FieldValidationTest {

    Main main;

    @BeforeEach
    public void before(){
//        main = new Main();
    }

    @Test
    @DisplayName("Валидация даты")
    public void dateBadValue(){
        Date date = new Date();
        date.setMonth(4);
        Assertions.assertEquals(true, Validation.checkDateFields(date));
    }

//    @Test
//    @DisplayName("Валидация фамилии")
//    public void fioBadValue(){
//        Assertions.assertEquals(true, Validation.checkFIOFields("Поварывгш"));
//    }

    @Test
    @DisplayName("Проверка данных после сортировки")
    public void sortTest(){
        CustomModel<Person> model = new CustomModel<>(new String[] {""},Person.class,new PersonManager(new DataBase()).getAll());
        model.setComparator(new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                return o1.getId().compareTo(o2.getId());
            }
        });
        model.updateData();
        Boolean flag = true;
        Integer firstId = model.getFilteredData().get(0).getId();
        for(int i=1; i<model.getFilteredData().size();i++){
            if(firstId>model.getFilteredData().get(i).getId()) flag = false;
            firstId=model.getFilteredData().get(i).getId();
        }
        Assertions.assertEquals(true,flag);
    }

//    @Test
//    @DisplayName("Проверка живого поиска")
//    public void testLiveSearch(){
//        CustomModel<Person> model = new CustomModel<>(new String[] {""},Person.class,new PersonManager(new DataBase()).getAll());
//        String value = "иго";
//        model.getPredicates()[0] = new Predicate<Person>() {
//            @Override
//            public boolean test(Person person) {
//                if()
//            }
//        }
//    }
}
