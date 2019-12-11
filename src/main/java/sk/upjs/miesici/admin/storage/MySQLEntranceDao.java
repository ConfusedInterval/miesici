package sk.upjs.miesici.admin.storage;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MySQLEntranceDao implements EntranceDao {
    private JdbcTemplate jdbcTemplate;
    public static long idOfEntrance = 0;

    public MySQLEntranceDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<Entrance> getAll() {
        String sql = "SELECT id, klient_id, kt.meno, kt.priezvisko, prichod, odchod " +
                "FROM vstup " +
                "JOIN klient AS kt " +
                "USING(klient_id)";
        List<Entrance> result = jdbcTemplate.query(sql, new EntranceResultSetExtractor());
        return result;
    }
    
    @Override
    public List<Entrance> getByCustomerId(long id) {
    	List<Entrance> entries = getAll();
    	List<Entrance> entriesById = new ArrayList<Entrance>();
    	for(Entrance entry: entries) {
    		if(entry.getKlient_id() == id) {
    			entriesById.add(entry);
    		}
    	}
    	return entriesById;
    }

    @Override
    public Entrance saveArrival(Entrance entrance) {
        idOfEntrance = 1;
        List<Entrance> list = getAll();
        for (Entrance entrance1 : list) {
            if (entrance1.getKlient_id().equals(entrance.getKlient_id()) && entrance1.getExit() == null && entrance1.getArrival() != null) {
                idOfEntrance = 0;
                break;
            }
        }

        if (entrance.getId() == null && idOfEntrance != 0) {
            SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate);
            insert.withTableName("vstup").usingGeneratedKeyColumns("id");

            Map<String, Object> values = new HashMap<String, Object>();
            values.put("klient_id", entrance.getKlient_id());
            values.put("meno", entrance.getName());
            values.put("priezvisko", entrance.getSurname());
            values.put("prichod", entrance.getArrival());
            Number key = insert.executeAndReturnKey(new MapSqlParameterSource(values));
            entrance.setId(key.longValue());
        }
        return entrance;
    }

    private Connection connect() {
        String url = "jdbc:mysql://localhost/mydb?serverTimezone=Europe/Bratislava";
        String name = "root";
        String password = "root";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, name, password);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }


    @Override
    public void saveExit(Entrance entrance) {
        idOfEntrance = 0;
        List<Entrance> list = getAll();
        for (Entrance entrance1 : list) {
            if (entrance1.getKlient_id().equals(entrance.getKlient_id()) && entrance1.getExit() == null && entrance1.getArrival() != null) {
                idOfEntrance = entrance1.getId();
            }
        }

        if (entrance.getKlient_id() != null) {
            String sql = "UPDATE vstup SET odchod = ?  "
                    + "WHERE id = ?;";
            try (Connection conn = this.connect()) {
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, entrance.getExit());
                pstmt.setLong(2, idOfEntrance);
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

        }
    }
}
