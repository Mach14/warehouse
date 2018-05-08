package Corleone.Warehouse.webservices;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import javax.annotation.security.RolesAllowed;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import Corleone.Warehouse.domain.Account;
import Corleone.Warehouse.domain.Order;
import Corleone.Warehouse.persistence.AccountDAO;
import Corleone.Warehouse.persistence.OrderDAO;

@Path("/orders")
public class OrderResource {
	private OrderDAO oDAO = new OrderDAO();
	private AccountDAO aDAO = new AccountDAO();

	@POST
//	@RolesAllowed({"admin","user"})
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces("application/json")
	public String createOrder(@FormParam("AccountID") int accID, @FormDataParam("ScreenshotOne") InputStream ss1, @FormDataParam("ScreenshotOne") FormDataContentDisposition fileDetails,
			@FormDataParam("ScreenshotTwo") InputStream ss2, @FormDataParam("ScreenshotTwo")  FormDataContentDisposition fileDetails2,
			@FormParam("TotalLead") int totallead, @FormParam("TotalMetal") int totalmetal, @FormParam("Comment") String comment, @FormParam("DateOfRun") Date dateofrun ) throws IOException {
		
		Date created = new Date();
		Account account = aDAO.findAccountByPK(accID);
		Order order = new Order(account, "Pending", created, totallead, totalmetal, comment, dateofrun);
	    
		String uploadedFileLocation = "A:\\Users\\Q\\eclipse-workspace\\Warehouse\\src\\main\\webapp\\images\\uploaded\\"+account.getUsername()+"\\Order-"+order.getID()+"\\1";
		String uploadedFileLocation2 = "A:\\Users\\Q\\eclipse-workspace\\Warehouse\\src\\main\\webapp\\images\\uploaded\\"+account.getUsername()+"\\Order-"+order.getID()+"\\2";

		// save it
		writeToFile(ss1, uploadedFileLocation);
		writeToFile(ss2, uploadedFileLocation2);

		oDAO.saveOrder(order);
		return orderToJson(order).build().toString();
	}
	
	// save uploaded file to new location
		private void writeToFile(InputStream uploadedInputStream,
			String uploadedFileLocation) {
			try {
				OutputStream out = new FileOutputStream(new File(
						uploadedFileLocation));
				int read = 0;
				byte[] bytes = new byte[1024];

				out = new FileOutputStream(new File(uploadedFileLocation));
				while ((read = uploadedInputStream.read(bytes)) != -1) {
					out.write(bytes, 0, read);
				}
				out.flush();
				out.close();
			} catch (IOException e) {

				e.printStackTrace();
			}
		}
	
	@GET
	@RolesAllowed("admin")
	@Produces("application/json")
	public String getOrders() throws IOException {
		JsonArrayBuilder jab = Json.createArrayBuilder();
		for(Order o : oDAO.findAll()){ 
			JsonObjectBuilder job = orderToJson(o);
			jab.add(job);
		}
		JsonArray array = jab.build();
		return array.toString();
	}
	
	@GET
	@RolesAllowed({"admin","user"})
	@Path("totallead")
	@Produces("application/json")
	public String getTotalLead() throws IOException {
		int lead = oDAO.getTotalLead();
		JsonObjectBuilder job = Json.createObjectBuilder();
		job.add("TotalLead", lead);
		return job.build().toString();
	}
	
	@GET
	@RolesAllowed({"admin","user"})
	@Path("totalmetal")
	@Produces("application/json")
	public String getTotalMetal() throws IOException {
		int metal = oDAO.getTotalMetal();
		JsonObjectBuilder job = Json.createObjectBuilder();
		job.add("TotalMetal", metal);
		return job.build().toString();
	}
	
	@GET
	@RolesAllowed({"admin","user"})
	@Path("totalmaterials")
	@Produces("application/json")
	public String getTotalMaterials() throws IOException {
		int lead = oDAO.getTotalLead();
		int metal = oDAO.getTotalMetal();
		int mats = Math.max(lead, metal);
		JsonObjectBuilder job = Json.createObjectBuilder();
		job.add("TotalMaterials", mats);
		return job.build().toString();
	}
	
	
	@GET
	@RolesAllowed("admin")
	@Path("pending")
	@Produces("application/json")
	public String getPendingOrders() throws IOException {
		JsonArrayBuilder jab = Json.createArrayBuilder();
		for(Order o : oDAO.findAllPending()){ 
			JsonObjectBuilder job = orderToJson(o);
			jab.add(job);
		}
		JsonArray array = jab.build();
		return array.toString();
	}
	
	@GET
//	@RolesAllowed({"admin","user"})
	@Path("{ID}")
	@Produces("application/json")
	public String getOrder(@PathParam("ID")int ID) throws IOException {
		Order o = oDAO.findOrderbyPK(ID);
		if(o == null){
			throw new WebApplicationException("Order not found!");
		}
		JsonObjectBuilder job = orderToJson(o);
		return job.build().toString();
	}
	
	private JsonObjectBuilder orderToJson(Order o ){
		JsonObjectBuilder job = Json.createObjectBuilder();
		job.add("ID", o.getID());
		job.add("Account_ID", o.getAccount().getID());
		job.add("Status", o.getStatus());
		job.add("TotalLead", o.getTotalLead());
		job.add("TotalMetal", o.getTotalMetal());
		job.add("Created", o.getCreated().toString());
//		job.add("ScreenshotOne", o.getScreenshotOne().toString());
//		job.add("ScreenshotTwo", o.getScreenshotTwo().toString());
		job.add("DateOfRun", o.getDateOfRun().toString());
		job.add("Comment", o.getComment());
		return job;
	}
}