package sk.upjs.miesici;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EntranceResultSetExtractor implements ResultSetExtractor<List<Entrance>> {
    @Override
    public List<Entrance> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<Entrance> result = new ArrayList<Entrance>();
        Entrance entrance = null;
        while(rs.next()) {
            long id = rs.getLong("id");
            if (entrance == null || entrance.getId() != id) {
                entrance = new Entrance();
                entrance.setId(id);
                entrance.setName(rs.getString("meno"));
                entrance.setSurname(rs.getString("priezvisko"));
                entrance.setArrival(rs.getString("prichod"));
                entrance.setExit(rs.getString("odchod"));
                result.add(entrance);
            }
        }
        return result;
    }
}
