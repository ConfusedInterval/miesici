package sk.upjs.miesici.klient.storage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

public class MySQLTrainingDao implements TrainingDao {

	private JdbcTemplate jdbcTemplate;
	
	public MySQLTrainingDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<Training> getAll() {
		String sql = "SELECT id, klient_id, nazov, datum, dayofweek(datum) as den, poznamka " +
				   "FROM trening " +
				           "JOIN klient " +
				   "USING (klient_id)";
		List<Training> result = jdbcTemplate.query(sql, new TrainingResultSetExtractor());
		return result;
	}

	@Override
	public List<Training> getAllbyClientId(long clientId) {
		List<Training> all = getAll();
		List<Training> byId = new ArrayList<Training>();
		for(Training training : all) {
			if(training.getClientId() == clientId) {
				byId.add(training);
			}
		}
		return byId;
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

}
