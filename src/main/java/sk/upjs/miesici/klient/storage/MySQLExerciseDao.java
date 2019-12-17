package sk.upjs.miesici.klient.storage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import sk.upjs.miesici.admin.storage.Customer;

public class MySQLExerciseDao implements ExerciseDao {

    private JdbcTemplate jdbcTemplate;

    public MySQLExerciseDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Exercise> getAllByTrainingId(long trainingId) {
        String sql = "Select * from cvik where trening_id = " + trainingId;
        return jdbcTemplate.query(sql, new ResultSetExtractor<>() {
			@Override
			public List<Exercise> extractData(ResultSet rs) throws SQLException, DataAccessException {
				Exercise exercise = null;
				List<Exercise> result = new ArrayList<Exercise>();
				while (rs.next()) {
					long typeOfExerciseId = rs.getLong("cvik_id");
					if (exercise == null || typeOfExerciseId != exercise.getTypeOfExerciseId()) {
						exercise = new Exercise();
						exercise.setTrainingId(rs.getLong("trening_id"));
						exercise.setTypeOfExerciseId(typeOfExerciseId);
						exercise.setWeight(rs.getDouble("vaha"));
						exercise.setReps(rs.getInt("pocet"));
					}
					result.add(exercise);
				}
				return result;
			}

		});
    }

	// docasne riesenie - treba upravit cez klasicky jdbcTemplate.insert
    @Override
    public void saveExercise(Exercise exercise) {
        String sql = "INSERT into cvik (trening_id,cvik_id,vaha,pocet)" +
                " values((select id from trening where id = ?),"
                + " (select id from typ_cviku where id = ?), ?, ?)";

		jdbcTemplate.execute(sql, new PreparedStatementCallback<Object>() {
			@Override
			public Object doInPreparedStatement(PreparedStatement pstmt) throws SQLException, DataAccessException {
				pstmt.setLong(1, exercise.getTrainingId());
				pstmt.setLong(2, exercise.getTypeOfExerciseId());
				pstmt.setDouble(3, exercise.getWeight());
				pstmt.setInt(4, exercise.getReps());
				return pstmt.executeUpdate();
			}
		});
    }
}
