package sk.upjs.miesici.admin.storage;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MySQLCustomerDao implements CustomerDao {
	private JdbcTemplate jdbcTemplate;

	public MySQLCustomerDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<Customer> getAll() {
		String sql = "SELECT klient_id, meno, priezvisko, adresa, email, kredit, permanentka, login, heslo, sol, admin "
				+ "FROM klient ";
		List<Customer> result = jdbcTemplate.query(sql, new CustomerResultSetExtractor());
		return result;
	}

	@Override
	public Customer save(Customer customer) {
		if (customer == null)
			return null;
		if (customer.getId() == null) {
			SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate);
			insert.withTableName("klient").usingGeneratedKeyColumns("klient_id");
			Map<String, Object> values = new HashMap<String, Object>();
			values.put("meno", customer.getName());
			values.put("priezvisko", customer.getSurname());
			values.put("adresa", customer.getAddress());
			values.put("email", customer.getEmail());
			values.put("kredit", customer.getCredit());
			values.put("permanentka", customer.getMembershipExp());
			values.put("login", customer.getLogin());
			values.put("heslo", customer.getPassword());
			values.put("sol", customer.getSalt());
			values.put("admin", customer.isAdmin());
			Number key = insert.executeAndReturnKey(new MapSqlParameterSource(values));
			customer.setId(key.longValue());
		}
		return customer;
	}

	@Override
	public void edit(Customer customer) {
		if (customer.getPassword() != null) {
			String sql = "UPDATE klient SET meno = ? , " + "priezvisko = ? , " + "adresa = ? , " + "email = ? , "
					+ "kredit = ? , " + "permanentka = ? , " + "heslo = ? , " + "sol = ?, " + "admin = ? "
					+ "WHERE klient_id = ?;";
			jdbcTemplate.update(sql, customer.getName(), customer.getSurname(), customer.getAddress(),
					customer.getEmail(), customer.getCredit(), customer.getMembershipExp(), customer.getPassword(),
					customer.getSalt(), customer.isAdmin(), customer.getId());
		} else {
			String sql = "UPDATE klient SET meno = ? , " + "priezvisko = ? , " + "adresa = ? , " + "email = ? , "
					+ "kredit = ? , " + "permanentka = ? , " + "admin = ? " + "WHERE klient_id = ?;";
			jdbcTemplate.update(sql, customer.getName(), customer.getSurname(), customer.getAddress(),
					customer.getEmail(), customer.getCredit(), customer.getMembershipExp(), customer.isAdmin(),
					customer.getId());
		}
	}

	@Override
	public Customer getBylogin(String login) {
		List<Customer> list = getAll();
		for (Customer customer : list) {
			if (customer.getLogin().equals(login)) {
				return customer;
			}
		}
		return null;
	}
}