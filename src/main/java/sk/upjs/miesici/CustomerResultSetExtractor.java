package sk.upjs.miesici;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerResultSetExtractor implements ResultSetExtractor<List<Customer>> {

    @Override
    public List<Customer> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<Customer> result = new ArrayList<Customer>();
        Customer customer = null;
        while(rs.next()) {
            long id = rs.getLong("klient_id");
            if (customer == null || customer.getId() != id) {
                customer = new Customer();
                customer.setId(id);
                customer.setName(rs.getString("meno"));
                customer.setSurname(rs.getString("priezvisko"));
                customer.setAddress(rs.getString("adresa"));
                customer.setEmail(rs.getString("email"));
                customer.setCredit(rs.getDouble("kredit"));
                customer.setMembershipExp(rs.getDate("permanentka"));
                customer.setAdmin(rs.getBoolean("admin"));
                result.add(customer);
            }
        }
        return result;
    }
}
