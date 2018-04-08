package Corleone.Warehouse.webservices;

import java.io.IOException;
import java.util.Date;

import javax.annotation.security.RolesAllowed;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import Corleone.Warehouse.domain.Account;
import Corleone.Warehouse.domain.Order;
import Corleone.Warehouse.domain.Orderline;
import Corleone.Warehouse.persistence.AccountDAO;
import Corleone.Warehouse.persistence.OrderDAO;
import Corleone.Warehouse.persistence.OrderlineDAO;

@Path("/orderlines")
public class OrderlineResource {
	private AccountDAO aDAO = new AccountDAO();
	private OrderDAO orderDAO = new OrderDAO();
	private OrderlineDAO oDAO = new OrderlineDAO();

	@POST
	@RolesAllowed({"admin","user"})
	@Produces("application/json")
	public String createOrder(@FormParam("Lead") int lead, @FormParam("Metal") int metal, @FormParam("OrderID") int orderID,  @FormParam("AccountID") int accountID) throws IOException {
		
		Account account = aDAO.findAccountByPK(accountID);
		Order order = orderDAO.findOrderbyPK(orderID);
		Orderline orderline = new Orderline(lead, metal, order, account);
		oDAO.saveOrderline(orderline);
		return orderlineToJson(orderline).build().toString();
	}
	
	@GET
	@RolesAllowed("admin")
	@Produces("application/json")
	public String getOrderlines() throws IOException {
		JsonArrayBuilder jab = Json.createArrayBuilder();
		for(Orderline o : oDAO.findAll()){ 
			JsonObjectBuilder job = orderlineToJson(o);
			jab.add(job);
		}
		JsonArray array = jab.build();
		return array.toString();
	}
	
	@GET
	@RolesAllowed("admin")
	@Path("pending")
	@Produces("application/json")
	public String getPendingOrderlines() throws IOException {
		JsonArrayBuilder jab = Json.createArrayBuilder();
		for(Orderline o : oDAO.findAllPending()){ 
			JsonObjectBuilder job = orderlineToJson(o);
			jab.add(job);
		}
		JsonArray array = jab.build();
		return array.toString();
	}
	
	
	@GET
	@RolesAllowed({"admin","user"})
	@Path("{ID}")
	@Produces("application/json")
	public String getOrderline(@PathParam("ID")int ID) throws IOException {
		Orderline o = oDAO.findOrderlinebyPK(ID);
		if(o == null){
			throw new WebApplicationException("Orderline not found!");
		}
		JsonObjectBuilder job = orderlineToJson(o);
		return job.build().toString();
	}
	
	
	
	
	private JsonObjectBuilder orderlineToJson(Orderline o ){
		JsonObjectBuilder job = Json.createObjectBuilder();
		job.add("ID", o.getID());
		job.add("Lead", o.getLead());
		job.add("Metal", o.getMetal());
		job.add("Order_ID", o.getOrder().getID());
		job.add("Account_ID", o.getAccount().getID());


		return job;
	}
}
