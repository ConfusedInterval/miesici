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

import sk.upjs.miesici.admin.storage.Customer;


public class MySQLTypeOfExerciseDao implements TypeOfExerciseDao {

	private JdbcTemplate jdbcTemplate;
	private Customer customer; 

	public MySQLTypeOfExerciseDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<TypeOfExercise> getAll() {
		String sql = "SELECT * from typ_cviku";
		return jdbcTemplate.query(sql, new ResultSetExtractor<List<TypeOfExercise>>() {

			@Override
			public List<TypeOfExercise> extractData(ResultSet rs) throws SQLException, DataAccessException {
				TypeOfExercise typeOfExercise = null;
				List<TypeOfExercise> result = new ArrayList<TypeOfExercise>();
				while (rs.next()) {
					long id = rs.getLong("id");
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
		});
	}

	@Override
	public List<TypeOfExercise> getAllByClientId(long clientId) {
		String sql = "SELECT * from typ_cviku where klient_id = " + clientId;
		return jdbcTemplate.query(sql, new ResultSetExtractor<List<TypeOfExercise>>() {

			@Override
			public List<TypeOfExercise> extractData(ResultSet rs) throws SQLException, DataAccessException {
				TypeOfExercise typeOfExercise = null;
				List<TypeOfExercise> result = new ArrayList<TypeOfExercise>();
				while (rs.next()) {
					long id = rs.getLong("id");
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
		});
	}

	@Override
	public TypeOfExercise save(TypeOfExercise typeOfExercise) {
		List<TypeOfExercise> types = getAllByClientId(customer.getId());
		typeOfExercise.setClientId(customer.getId());
		int count = 0;
		for (TypeOfExercise type : types) {
			if (type.getName().equals(typeOfExercise.getName())) {
				count++;
			}
		}
		if (count == 0) {
			SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate);
			insert.withTableName("typ_cviku").usingGeneratedKeyColumns("id");
			Map<String, Object> values = new HashMap<String, Object>();
			values.put("nazov", typeOfExercise.getName());
			values.put("klient_id", typeOfExercise.getClientId());
			Number key = insert.executeAndReturnKey(new MapSqlParameterSource(values));
			typeOfExercise.setId(key.longValue());
			return typeOfExercise;
		}
		return null;
	}

	@Override
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
}
