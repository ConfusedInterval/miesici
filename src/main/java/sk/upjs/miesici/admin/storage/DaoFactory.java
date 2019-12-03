package sk.upjs.miesici.admin.storage;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

public enum DaoFactory {
    INSTANCE;

    private CustomerDao customerDao;
    private EntryDao entryDao;

    public CustomerDao getCustomerDao() {
        if (customerDao == null) {
            customerDao = new MySQLCustomerDao(getJdbcTemplate());
        }
        return customerDao;
    }

    public EntryDao getEntryDao(){
        if (entryDao == null){
            entryDao = new MySQLEntryDao(getJdbcTemplate());
        }
        return entryDao;
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
