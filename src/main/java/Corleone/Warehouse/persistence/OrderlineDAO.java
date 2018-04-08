package Corleone.Warehouse.persistence;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Corleone.Warehouse.domain.Account;
import Corleone.Warehouse.domain.Order;
import Corleone.Warehouse.domain.Orderline;

public class OrderlineDAO extends BaseDAO{
	private OrderDAO oDAO = new OrderDAO();
	private AccountDAO aDAO = new AccountDAO();
	private PreparedStatement preparedStatement = null;

	
	public void get6LatestRuns() {
		
	}
		private List<Orderline> selectOrderlines(String query) throws IOException{
			List<Orderline> orderlines = new ArrayList<Orderline>();
					
			try(Connection con = super.getConnection()){
				Statement stmt = con.createStatement();
				ResultSet dbResultSet = stmt.executeQuery(query);
				orderlines.clear();
				while(dbResultSet.next()){ 
					 int ID = dbResultSet.getInt("ID");
					 int metal = dbResultSet.getInt("Metal");
					 int lead = dbResultSet.getInt("Lead");
					 int orderID = dbResultSet.getInt("Order_ID");
					 int accountID = dbResultSet.getInt("Account_ID");

					 Order order = oDAO.findOrderbyPK(orderID);
					 Account account = aDAO.findAccountByPK(accountID);
					 
					 Orderline orderline = new Orderline(ID, metal, lead, order, account);
					 orderlines.add(orderline);
				}
			}
			catch(SQLException sqle){ sqle.printStackTrace();
		}
			return orderlines; 
		}
		public List<Orderline> findAll() throws IOException{ return selectOrderlines("SELECT * FROM public.\"Orderline\" ORDER BY \"ID\" DESC");}
		public List<Orderline> findAllPending() throws IOException{ return selectOrderlines("SELECT public.\"Orderline\".\"ID\", public.\"Orderline\".\"Lead\", public.\"Orderline\".\"Metal\", public.\"Orderline\".\"Order_ID\", public.\"Orderline\".\"Account_ID\"\r\n" + 
				"	FROM public.\"Orderline\" , public.\"Order\" \r\n" + 
				"	WHERE public.\"Order\".\"ID\" = public.\"Orderline\".\"Order_ID\"\r\n" + 
				"	AND public.\"Order\".\"Status\" = 'Pending' ORDER BY public.\"Orderline\".\"ID\" DESC;");}

		public Orderline findOrderlinebyPK(int ID) throws IOException{ 	
			return selectOrderlines("SELECT * FROM public.\"Orderline\" WHERE \"ID\" = " + ID).get(0);
		}
		
	
		
		public void saveOrderline(Orderline orderline){
			int orderlineID = 0;

			String query = "INSERT INTO public.\"Orderline\"(\r\n" + 
					"	\"ID\", \"Lead\", \"Metal\", \"Order_ID\", \"Account_ID\")\r\n" + 
					"	VALUES (nextval('orderline_seq'::regclass), ?, ?, ?, ?);";
			try (Connection con = super.getConnection()) {
				preparedStatement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS); 

				preparedStatement.setInt(1, orderline.getLead()); 
				preparedStatement.setInt(2, orderline.getMetal()); 
				preparedStatement.setInt(3, orderline.getOrder().getID()); 
				preparedStatement.setInt(4, orderline.getAccount().getID()); 
			
				preparedStatement.executeUpdate();	
				ResultSet rs = preparedStatement.getGeneratedKeys();
	            if(rs.next())
	            {
	            	orderlineID = rs.getInt(1);
	            }
				preparedStatement.close();
				orderline.setID(orderlineID);
				System.out.println("Orderline: " + orderline.getID()  + " saved.");
			} catch (SQLException sqle) {
				sqle.printStackTrace();
			}	
	}
		
		
		
	}

	

