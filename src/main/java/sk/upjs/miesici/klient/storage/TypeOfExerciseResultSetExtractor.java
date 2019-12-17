package sk.upjs.miesici.klient.storage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

public class TypeOfExerciseResultSetExtractor implements ResultSetExtractor<List<TypeOfExercise>> {

	@Override
	public List<TypeOfExercise> extractData(ResultSet rs) throws SQLException, DataAccessException {
		TypeOfExercise typeOfExercise = null;
		List<TypeOfExercise> result = new ArrayList<TypeOfExercise>();
		while (rs.next()) {
			Long id = rs.getLong("id");
			if (typeOfExercise == null || id != typeOfExercise.getId()) {
				typeOfExercise = new TypeOfExercise();
				typeOfExercise.setId(id);
				typeOfExercise.setName(rs.getString("nazov"));
				typeOfExercise.setClientId(rs.getLong("klient_id"));
			}
			result.add(typeOfExercise);
		}
		return result;
	}

}
