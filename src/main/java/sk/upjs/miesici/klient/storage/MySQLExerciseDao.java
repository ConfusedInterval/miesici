package sk.upjs.miesici.klient.storage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

public class MySQLExerciseDao implements ExerciseDao {

	private JdbcTemplate jdbcTemplate;

	public MySQLExerciseDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<Exercise> getAllByTrainingId(Long trainingId) {
		String sql = "SELECT cvik.id,trening_id, cvik_id, vaha, pocet, nazov from cvik " + "JOIN typ_cviku WHERE trening_id = "
				+ trainingId + " AND cvik_id = typ_cviku.id";

		return jdbcTemplate.query(sql, new ResultSetExtractor<>() {
			@Override
			public List<Exercise> extractData(ResultSet rs) throws SQLException, DataAccessException {
				Exercise exercise = null;
				List<Exercise> result = new ArrayList<Exercise>();
				while (rs.next()) {
		            Long id = rs.getLong("id");
					long typeOfExerciseId = rs.getLong("cvik_id");
					if (exercise == null || typeOfExerciseId != exercise.getTypeOfExerciseId() || id != exercise.getId()) {
						exercise = new Exercise();
						exercise.setId(id);
						exercise.setTrainingId(rs.getLong("trening_id"));
						exercise.setTypeOfExerciseId(typeOfExerciseId);
						exercise.setWeight(rs.getDouble("vaha"));
						exercise.setReps(rs.getInt("pocet"));
						exercise.setName(rs.getString("nazov"));
					}
					result.add(exercise);
				}
				return result;
			}
		});
	}

	@Override
	public Exercise saveExercise(Exercise exercise) {
		if (exercise == null) {
			return null;
		} else {
			SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate);
			insert.withTableName("cvik").usingGeneratedKeyColumns("id");;
			Map<String, Object> values = new HashMap<String, Object>();
			values.put("trening_id", exercise.getTrainingId());
			values.put("cvik_id", exercise.getTypeOfExerciseId());
			values.put("vaha", exercise.getWeight());
			values.put("pocet", exercise.getReps());
			Number key = insert.executeAndReturnKey(new MapSqlParameterSource(values));
			exercise.setId(key.longValue());
			return exercise;
		}
	}

	@Override
	public void deleteExerciseByTrainingId(long id) {
		String deleteSQL = "DELETE FROM cvik WHERE trening_id = " + id;
		jdbcTemplate.update(deleteSQL);
	}

	@Override
	public void deleteExerciseById(Long id) {
		String deleteSQL = "DELETE FROM cvik WHERE id = " + id;
		jdbcTemplate.update(deleteSQL);
		
	}
}
