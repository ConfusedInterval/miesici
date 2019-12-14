package sk.upjs.miesici.klient.storage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

public class TrainingResultSetExtractor implements ResultSetExtractor<List<Training>> {

	@Override
	public List<Training> extractData(ResultSet rs) throws SQLException, DataAccessException {
		List<Training> result = new ArrayList<Training>();
		Training training = null;
		while(rs.next()) {
			long id = rs.getLong("id");
			if (training == null || id != training.getId()) {
				training = new Training();
				training.setId(id);
				training.setClientId(rs.getLong("klient_id"));
				training.setName(rs.getString("nazov"));
				training.setDate(rs.getString("datum"));
				training.setDayofTheWeek(rs.getInt("den"));
				training.setNote(rs.getString("poznamka"));
			}
			result.add(training);
		}
		return result;
	
	}
	
	

}
