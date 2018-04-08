package Corleone.Warehouse.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import Corleone.Warehouse.domain.Account;

public class AccountDAO extends BaseDAO {
	private PreparedStatement preparedStatement = null;

	private List<Account> selectAccounts(String query){
		List<Account> accounts = new ArrayList<Account>();
				
		try(Connection con = super.getConnection()){
			Statement stmt = con.createStatement();
			ResultSet dbResultSet = stmt.executeQuery(query);
			accounts.clear();
			while(dbResultSet.next()){ 
				 int ID = dbResultSet.getInt("ID");
				 String username = dbResultSet.getString("Username");
				 String igname = dbResultSet.getString("IngameName");
				 String password = dbResultSet.getString("Password");
				 String role = dbResultSet.getString("Role");
				 int lead = dbResultSet.getInt("TotalLead");
				 int metal = dbResultSet.getInt("TotalMetal");
				 int matsreceived = dbResultSet.getInt("MaterialsReceived");
				 int matstoreceive = dbResultSet.getInt("MaterialsToReceive");
				 String rank = dbResultSet.getString("Rank");
				 int tier = dbResultSet.getInt("Tier");
				 int totalmats = dbResultSet.getInt("TotalMaterials");
				 String status = dbResultSet.getString("Status");
				 Date created = dbResultSet.getDate("Created");
				
				 Account account = new Account(ID, username,igname, password, role, lead, metal, matsreceived, matstoreceive, totalmats, rank, tier, status, created);
				 accounts.add(account);
			}
		}
		catch(SQLException sqle){ sqle.printStackTrace();
	}
		return accounts; 
	}
	public List<Account> findAll(){ return selectAccounts("SELECT * FROM public.\"Account\" ORDER BY \"ID\"");}

	public Account findAccountByPK(int ID){ 	
		return selectAccounts("SELECT * FROM public.\"Account\" WHERE \"ID\" = " + ID).get(0);
	}
	
	public Account findAccountByUsername(String username){ 	
		return selectAccounts("SELECT * FROM public.\"Account\" WHERE \"Username\" = '" + username+"'").get(0);
	}
	
	public void saveAccount(Account account){
		int accountID = 0;

		String query = "INSERT INTO public.\"Account\"(\r\n" + 
				"	\"ID\", \"Username\", \"IngameName\", \"Password\", \"Role\", \"TotalLead\", \"TotalMetal\", \"MaterialsReceived\", \r\n" + 
				"	\"MaterialsToReceive\", \"Rank\", \"Tier\", \"TotalMaterials\", \"Status\", \"Created\")\r\n" + 
				"	VALUES (nextval('account_seq'::regclass), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);\r\n" + 
				"";
		try (Connection con = super.getConnection()) {
			preparedStatement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS); 
			preparedStatement.setString(1, account.getUsername()); 
			preparedStatement.setString(2, account.getIngameName()); 
			preparedStatement.setString(3, account.getPassword()); 
			preparedStatement.setString(4, account.getRole()); 
			preparedStatement.setInt(5, account.getTotalLead()); 
			preparedStatement.setInt(6, account.getTotalMetal()); 
			preparedStatement.setInt(7, account.getMatsReceived()); 
			preparedStatement.setInt(8, account.getMatsToReceive()); 
			preparedStatement.setString(9, account.getRank()); 
			preparedStatement.setInt(10, account.getTier()); 
			preparedStatement.setInt(11, account.getTotalMatsEarned()); 
			preparedStatement.setString(12, account.getStatus()); 
			preparedStatement.setDate(13, new java.sql.Date(account.getCreated().getTime())); 
			preparedStatement.executeUpdate();	
			ResultSet rs = preparedStatement.getGeneratedKeys();
            if(rs.next())
            {
            	accountID = rs.getInt(1);
            }
			preparedStatement.close();
			account.setID(accountID);
			System.out.println("Account: " + account.getID()  + " saved.");
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}	
}
	
	
	
	
	public int getTotalAccounts() {
		String query ="SELECT count(*) FROM public.\"Account\";";
		int totalAccs = 0;
		try (Connection con = super.getConnection()) {
			preparedStatement = con.prepareStatement(query);
			Statement stmt = con.createStatement();
			ResultSet dbResultSet = stmt.executeQuery(query);
			while(dbResultSet.next()){ 
				 int accs = dbResultSet.getInt("count");
				 totalAccs = accs;
			}
			preparedStatement.close();
		}
		 catch (SQLException sqle) {
				sqle.printStackTrace();
			}	
		return totalAccs;
		
		
	}
	
	
	public boolean updateAccount(Account account){ 
		boolean result = false;
		boolean exists = findAccountByPK(account.getID()) != null;
		
		if(exists){ 
			String query = "UPDATE public.\"Account\" "
			+ " SET \"Tier\" = " + account.getTier()
			+ ", \"Rank\" = '" +account.getRank()+"'"
			+", \"Status\" = '" + account.getStatus() + "'"
			+", \"Role\" = '" + account.getRole() + "'"

			+ " WHERE \"ID\" = " 	+ account.getID();
			try(Connection con = super.getConnection()){
				Statement stmt = con.createStatement();
				if(stmt.executeUpdate(query) == 1){
					result = true;
				}
			}
			catch(SQLException sqle){
				sqle.printStackTrace();
			}
		}
			return result;
	}
	
	
	
}
