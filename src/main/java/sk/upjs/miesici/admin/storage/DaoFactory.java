package sk.upjs.miesici.admin.storage;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import sk.upjs.miesici.klient.storage.MySQLTrainingDao;
import sk.upjs.miesici.klient.storage.MySQLTypeOfExerciseDao;
import sk.upjs.miesici.klient.storage.TrainingDao;
import sk.upjs.miesici.klient.storage.TypeOfExerciseDao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public enum DaoFactory {
    INSTANCE;

    private CustomerDao customerDao;
    private EntranceDao entranceDao;
    private TrainingDao trainingDao;
    private TypeOfExerciseDao typeOfExerciseDao;

    public CustomerDao getCustomerDao() {
        if (customerDao == null) {
            customerDao = new MySQLCustomerDao(getJdbcTemplate());
        }
        return customerDao;
    }

    public EntranceDao getEntranceDao(){
        if (entranceDao == null){
            entranceDao = new MySQLEntranceDao(getJdbcTemplate());
        }
        return entranceDao;
    }
    
    public TrainingDao getTrainingDao(){
        if (trainingDao == null){
            trainingDao = new MySQLTrainingDao(getJdbcTemplate());
        }
        return trainingDao;
    }
    
    public TypeOfExerciseDao getTypeOfExerciseDao(){
        if (typeOfExerciseDao == null){
            typeOfExerciseDao = new MySQLTypeOfExerciseDao(getJdbcTemplate());
        }
        return typeOfExerciseDao;
    }

    private JdbcTemplate getJdbcTemplate() {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUser("root");
        dataSource.setPassword("root");
        dataSource.setUrl(
                "jdbc:mysql://localhost/mydb?serverTimezone=Europe/Bratislava");
        return new JdbcTemplate(dataSource);
    }
}
