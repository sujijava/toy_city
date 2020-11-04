package ca.sheridancollege.database;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import ca.sheridancollege.beans.Toy;
import ca.sheridancollege.beans.User;

@Repository
public class DatabaseAccess {

	@Autowired
	protected NamedParameterJdbcTemplate jdbc;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	public void addToy(Toy toy) {

		MapSqlParameterSource parameters = new MapSqlParameterSource();
		
		String query = "INSERT INTO inventory (toy_name, toy_price, toy_qty) "
				+ "VALUES (:name, :price, :qty)";
		parameters.addValue("name", toy.getToy_name());
		parameters.addValue("price", toy.getToy_price());
		parameters.addValue("qty", toy.getToy_qty());

		jdbc.update(query, parameters);

	}
	
	public ArrayList<Toy> getToys() {

		ArrayList<Toy> toys = new ArrayList<>();

		String query = "SELECT * FROM inventory";

		List<Map<String, Object>> rows = jdbc.queryForList(query, new HashMap<String, Object>());

		for (Map<String, Object> row : rows) {
			Toy t = new Toy();
			t.setId((Integer) row.get("id"));
			t.setToy_name((String)row.get("toy_name"));
			t.setToy_price((Double)row.get("toy_price"));
			t.setToy_qty((Integer) row.get("toy_qty"));
			
			toys.add(t);
		}
		return toys;
	}
	
	public void deleteToy(int id) {		

		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "DELETE FROM inventory WHERE id=:id";
		parameters.addValue("id", id);
		jdbc.update(query, parameters);
	}
	
	public void editToy(Toy toy) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "UPDATE inventory SET toy_name=:name, toy_price=:price, toy_qty=:qty WHERE id=:id";
		parameters.addValue("name", toy.getToy_name());
		parameters.addValue("price", toy.getToy_price());
		parameters.addValue("qty", toy.getToy_qty());
		parameters.addValue("id", toy.getId());
		
		jdbc.update(query, parameters);

	}

	
	public Toy getToyById(long id) {
		ArrayList<Toy> toys = new ArrayList<Toy>();

		String query = "SELECT * FROM inventory WHERE id=:id";
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("id", id);
		List<Map<String, Object>> rows = jdbc.queryForList(query, parameters);
		
		
		for (Map<String, Object> row : rows) {
			Toy t = new Toy();
			
			t.setId((Integer) row.get("id"));
			t.setToy_name((String)row.get("toy_name"));
			t.setToy_price((Double)row.get("toy_price"));
			t.setToy_qty((Integer) row.get("toy_qty"));
			
			toys.add(t);
		}

		if (toys.size() > 0)
			return toys.get(0);

		return null;
	}
	
	public ArrayList<Toy> getToyByName(String name){
		ArrayList<Toy> toys = new ArrayList<Toy>();
		
		String query = "SELECT * FROM inventory WHERE toy_name =:name;";

		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("name", name);
		
		List<Map<String, Object>> rows = jdbc.queryForList(query,parameters);
		
		for(Map<String, Object> row: rows) {
			Toy t = new Toy();
			
			t.setId((Integer) row.get("id"));
			t.setToy_name((String)row.get("toy_name"));
			t.setToy_price((Double)row.get("toy_price"));
			t.setToy_qty((Integer) row.get("toy_qty"));
			
			toys.add(t);
		}
	
		return toys; 
	}
	
	public ArrayList<Toy> getToyByQty(int min, int max){
		ArrayList<Toy> toys = new ArrayList<Toy>();
		
		String query = "SELECT * FROM inventory WHERE toy_qty BETWEEN " + min + " AND " + max +";"; 
				
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("min", min);
		parameters.addValue("max", max);
		
		List<Map<String, Object>> rows = jdbc.queryForList(query,parameters);
		
		for(Map<String, Object> row: rows) {
			Toy t = new Toy();
			t.setId((Integer) row.get("id"));
			t.setToy_name((String)row.get("toy_name"));
			t.setToy_price((Double)row.get("toy_price"));
			t.setToy_qty((Integer) row.get("toy_qty"));
			toys.add(t);
		}
		return toys; 
	}
	
	public ArrayList<Toy> getToyByPrice(int min, int max){
		ArrayList<Toy> toys = new ArrayList<Toy>();
		
		String query = "SELECT * FROM inventory WHERE toy_price BETWEEN " + min + " AND " + max +";"; 
				
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("min", min);
		parameters.addValue("max", max);
		
		List<Map<String, Object>> rows = jdbc.queryForList(query,parameters);
		
		for(Map<String, Object> row: rows) {
			Toy t = new Toy();
			t.setId((Integer) row.get("id"));
			t.setToy_name((String)row.get("toy_name"));
			t.setToy_price((Double)row.get("toy_price"));
			t.setToy_qty((Integer) row.get("toy_qty"));
			toys.add(t);
		}
		return toys; 
	}
	
	
	
	
	
	
	
	
	
	
	
	
	public void addRole(long userId, long roleId) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "insert into user_role (userId, roleId) " + "values (:userId, :roleId);";
		parameters.addValue("userId", userId);
		parameters.addValue("roleId", roleId);
		jdbc.update(query, parameters);
	}
	
	public void createNewUser(String name, String password) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "insert into SEC_User " + "(userName, encryptedPassword, ENABLED)" + " values (:name, :pass, 1)";
		parameters.addValue("name", name);
		parameters.addValue("pass", passwordEncoder.encode(password));

		jdbc.update(query, parameters);
	}
	
	
	public User findUserAccount(String userName) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "SELECT * FROM sec_user where userName=:userName";
		parameters.addValue("userName", userName);
		ArrayList<User> users = (ArrayList<User>) jdbc.query(query, parameters,
				new BeanPropertyRowMapper<User>(User.class));
		if (users.size() > 0)
			return users.get(0);
		else
			return null;
	}
	
	public List<String> getRolesById(long userId) {

		ArrayList<String> roles = new ArrayList<String>();
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "SELECT user_role.userId, sec_role.roleName " + "FROM user_role, sec_role "
				+ "WHERE user_role.roleId=sec_role.roleId " + "AND userId=:userId";
		parameters.addValue("userId", userId);
		List<Map<String, Object>> rows = jdbc.queryForList(query, parameters);
		for (Map<String, Object> row : rows) {
			roles.add((String) row.get("roleName"));
		}
		return roles;	
	}
	
	
	
	
}
