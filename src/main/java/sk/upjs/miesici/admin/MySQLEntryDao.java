package sk.upjs.miesici.admin;

import org.springframework.jdbc.core.JdbcTemplate;
import java.util.List;

public class MySQLEntryDao implements EntryDao {
    private JdbcTemplate jdbcTemplate;

    public MySQLEntryDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<Entrance> getAll() {
        String sql = "SELECT id, kt.meno, kt.priezvisko, prichod, odchod " +
                "FROM vstup " +
                "JOIN klient AS kt " +
                "USING(klient_id)" ;
        List<Entrance> result = jdbcTemplate.query(sql, new EntranceResultSetExtractor());
        return result;
    }

    @Override
    public Entrance save(Entrance entrance) {
        return null;
    }


}
