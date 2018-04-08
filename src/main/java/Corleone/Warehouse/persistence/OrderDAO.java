package Corleone.Warehouse.persistence;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Corleone.Warehouse.domain.Account;
import Corleone.Warehouse.domain.Order;

public class OrderDAO extends BaseDAO{
	private AccountDAO aDAO = new AccountDAO();
	private PreparedStatement preparedStatement = null;

		private List<Order> selectOrders(String query) throws IOException{
			List<Order> orders = new ArrayList<Order>();
					
			try(Connection con = super.getConnection()){
				Statement stmt = con.createStatement();
				ResultSet dbResultSet = stmt.executeQuery(query);
				orders.clear();
				while(dbResultSet.next()){ 
					 int ID = dbResultSet.getInt("ID");
					 int accountID = dbResultSet.getInt("Account_ID");
					
					 String status = dbResultSet.getString("Status");
					 Date created = dbResultSet.getDate("Created");
					 int metal = dbResultSet.getInt("TotalMetal");
					 int lead = dbResultSet.getInt("TotalLead");
					 String comment = dbResultSet.getString("Comment");
					 Date dateofrun = dbResultSet.getDate("DateOfRun");
					 Account account = aDAO.findAccountByPK(accountID);
					 Order order = new Order(ID, account, status, created, lead, metal, comment,dateofrun);
					 orders.add(order);
				}
			}
			catch(SQLException sqle){ sqle.printStackTrace();
		}
			return orders; 
		}
		public List<Order> findAll() throws IOException{ return selectOrders("SELECT * FROM public.\"Order\" ORDER BY \"ID\" DESC");}
		public List<Order> findAllPending() throws IOException{ return selectOrders("SELECT * FROM public.\"Order\" WHERE \"Status\" ='Pending' ORDER BY \"ID\" DESC");}

		public Order findOrderbyPK(int ID) throws IOException{ 	
			return selectOrders("SELECT * FROM public.\"Order\" WHERE \"ID\" = " + ID).get(0);
		}
		
	
		
		public void saveOrder(Order order){
			int orderlineID = 0;

			String query = "INSERT INTO public.\"Order\"(\r\n" + 
					"	\"ID\", \"Account_ID\", \"Status\", \"Created\", \"TotalLead\", \"TotalMetal\", \"Comment\", \"DateOfRun\")\r\n" + 
					"	VALUES (nextval('order_seq'::regclass), ?, ?, ?, ?, ?, ?, ?);";
			try (Connection con = super.getConnection()) {
				preparedStatement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS); 

				preparedStatement.setInt(1, order.getAccount().getID()); 
			//	preparedStatement.setBytes(2, order.getScreenshotOne()); 
			//	preparedStatement.setBytes(3, order.getScreenshotOne()); 

				preparedStatement.setString(2, order.getStatus()); 
				preparedStatement.setDate(3, new java.sql.Date(order.getCreated().getTime())); 
				preparedStatement.setInt(4, order.getTotalLead()); 
				preparedStatement.setInt(5, order.getTotalMetal()); 
				preparedStatement.setString(6, order.getComment()); 
				preparedStatement.setDate(7, new java.sql.Date(order.getDateOfRun().getTime())); 

				preparedStatement.executeUpdate();	
				ResultSet rs = preparedStatement.getGeneratedKeys();
	            if(rs.next())
	            {
	            	orderlineID = rs.getInt(1);
	            }
				preparedStatement.close();
				order.setID(orderlineID);
				System.out.println("Order: " + order.getID()  + " saved.");
			} catch (SQLException sqle) {
				sqle.printStackTrace();
			}	
	}
		
		public int getTotalMaterials() {
			String query ="SELECT sum(\"TotalMaterials\") FROM public.\"Account\";";
			int totalMats = 0;
			try (Connection con = super.getConnection()) {
				preparedStatement = con.prepareStatement(query);
				Statement stmt = con.createStatement();
				ResultSet dbResultSet = stmt.executeQuery(query);
				while(dbResultSet.next()){ 
					 int mats = dbResultSet.getInt("sum");
					totalMats = mats;
				}
				preparedStatement.close();
			}
			 catch (SQLException sqle) {
					sqle.printStackTrace();
				}	
			return totalMats;
		}
		
		public int getTotalLead() {
			String query ="SELECT sum(\"TotalLead\") FROM public.\"Order\";";
			int totalLead = 0;
			try (Connection con = super.getConnection()) {
				preparedStatement = con.prepareStatement(query);
				Statement stmt = con.createStatement();
				ResultSet dbResultSet = stmt.executeQuery(query);
				while(dbResultSet.next()){ 
					 int lead = dbResultSet.getInt("sum");
					totalLead = lead;
				}
				preparedStatement.close();
			}
			 catch (SQLException sqle) {
					sqle.printStackTrace();
				}	
			return totalLead;
		}
		
		public int getTotalMetal() {
			String query ="SELECT sum(\"TotalMetal\") FROM public.\"Order\";";
			int totalMetal = 0;
			try (Connection con = super.getConnection()) {
				preparedStatement = con.prepareStatement(query);
				Statement stmt = con.createStatement();
				ResultSet dbResultSet = stmt.executeQuery(query);
				while(dbResultSet.next()){ 
					 int metal = dbResultSet.getInt("sum");
					totalMetal = metal;
				}
				preparedStatement.close();
			}
			 catch (SQLException sqle) {
					sqle.printStackTrace();
				}	
			return totalMetal;
		}
		
	}

	

