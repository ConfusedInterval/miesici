package sk.upjs.miesici.klient.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

public class MySQLTrainingDao implements TrainingDao {

	private JdbcTemplate jdbcTemplate;

	public MySQLTrainingDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<Training> getAll() {
		String sql = "SELECT id, klient_id, nazov, datum, dayofweek(datum) as den, poznamka " + "FROM trening "
				+ "JOIN klient " + "USING (klient_id)";
		List<Training> result = jdbcTemplate.query(sql, new TrainingResultSetExtractor());
		return result;
	}

	@Override
	public Training saveTraining(Training training) {
		if (training.getId() == null) {
			SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate);
			insert.withTableName("trening").usingGeneratedKeyColumns("id");
			Map<String, Object> values = new HashMap<String, Object>();
			values.put("klient_id", training.getClientId());
			values.put("datum", training.getDate());
			values.put("nazov", training.getName());
			values.put("poznamka", training.getNote());
			Number key = insert.executeAndReturnKey(new MapSqlParameterSource(values));
			training.setId(key.longValue());
			return training;
		} else {
			editTraining(training);
		}
		return training;
	}

	@Override
	public List<Training> getAllbyClientId(Long clientId) {
		List<Training> all = getAll();
		List<Training> byId = new ArrayList<Training>();
		for (Training training : all) {
			if (training.getClientId().equals(clientId)) {
				byId.add(training);
			}
		}
		return byId;
	}

	@Override
	public void deleteTrainingById(Long id) {
		String deleteSQL = "DELETE FROM trening where id = " + id;
		jdbcTemplate.update(deleteSQL);
	}

	@Override
	public void editTraining(Training training) {
		String updateSQL = "UPDATE trening SET datum = ? , nazov = ? , poznamka = ? WHERE id = " + training.getId();
		jdbcTemplate.update(updateSQL, training.getDate(), training.getName(), training.getNote());
	}

}
