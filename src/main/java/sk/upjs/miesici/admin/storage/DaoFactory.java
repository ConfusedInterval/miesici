package sk.upjs.miesici.admin.storage;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

public enum DaoFactory {
    INSTANCE;

    private CustomerDao customerDao;
    private EntranceDao entranceDao;
    private TrainingDao trainingDao;

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
            trainingDao = new MysqlTrainingDao(getJdbcTemplate());
        }
        return trainingDao;
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
