package Corleone.Warehouse.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO extends BaseDAO {
	 public String findRoleForUsernameAndPassword(String username, String password) {
	 String role = null;
	 String query = "SELECT \"Role\" FROM \"Account\" WHERE \"Username\" = ? AND \"Password\" = ?";

	 try (Connection con = super.getConnection()) {

	 PreparedStatement pstmt = con.prepareStatement(query);
	 pstmt.setString(1, username);
	 pstmt.setString(2, password);

	 ResultSet rs = pstmt.executeQuery();
	 if (rs.next())
	 role = rs.getString("Role");

	 } catch (SQLException sqle) {
	 sqle.printStackTrace();
	 }

	 return role;
	 }
	}
