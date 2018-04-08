package Corleone.Warehouse.webservices;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
//import com.sun.jersey.core.header.FormDataContentDisposition;
//import com.sun.jersey.multipart.FormDataParam;

import Corleone.Warehouse.domain.Account;
import Corleone.Warehouse.domain.Order;
import Corleone.Warehouse.persistence.AccountDAO;
import Corleone.Warehouse.persistence.OrderDAO;

@Path("/file")
public class UploadFileService {
	private OrderDAO oDAO = new OrderDAO();
	private AccountDAO aDAO = new AccountDAO();
/*	
	@POST
	@Path("/upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadFile(
			@FormDataParam("AccountID") int accID, 
			@FormDataParam("TotalLead") int totallead, @FormDataParam("TotalMetal") int totalmetal, @FormDataParam("Comment") String comment, @FormDataParam("DateOfRun") Date dateofrun,			
		@FormDataParam("file") InputStream uploadedInputStream,	@FormDataParam("file") FormDataContentDisposition fileDetail,	
		@FormDataParam("file2") InputStream uploadedInputStream2,@FormDataParam("file2") FormDataContentDisposition fileDetail2) throws IOException {

		
		Date created = new Date();
		Account account = aDAO.findAccountByPK(accID);
		Order order = new Order(account, "Pending", created, totallead, totalmetal, comment, dateofrun);
		String uploadedFileLocation = "A:\\Users\\Q\\eclipse-workspace\\Warehouse\\src\\main\\webapp\\images\\uploaded\\"+account.getUsername()+"\\Order-"+order.getID()+"\\"+fileDetail.getFileName();
		String uploadedFileLocation2 = "A:\\Users\\Q\\eclipse-workspace\\Warehouse\\src\\main\\webapp\\images\\uploaded\\"+account.getUsername()+"\\Order-"+order.getID()+"\\"+fileDetail2.getFileName();

		// save it
		writeToFile(uploadedInputStream, uploadedFileLocation);
		writeToFile(uploadedInputStream2, uploadedFileLocation2);

		String output = " File uploaded to : " + uploadedFileLocation + " and " + uploadedFileLocation2;
		oDAO.saveOrder(order);

		return Response.status(200).entity(output).build();

	}
*/
	@POST
	@Path("/order")
	public Response uploadFile(
	@FormDataParam("AccountID") int accID, @FormDataParam("TotalLead") int totallead, @FormDataParam("TotalMetal") int totalmetal, 
	@FormDataParam("file") InputStream uploadedInputStream,	@FormDataParam("file") FormDataContentDisposition fileDetail,	
	@FormDataParam("file2") InputStream uploadedInputStream2,@FormDataParam("file2") FormDataContentDisposition fileDetail2,
	@FormDataParam("Comment") String comment, @FormDataParam("DateOfRun") String dateOfRun) throws IOException{
		Date created = new Date();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
		LocalDate date1 = LocalDate.parse(dateOfRun, formatter);
		Date date = Date.from(date1.atStartOfDay(ZoneId.systemDefault()).toInstant());
		Account account = aDAO.findAccountByPK(accID);
		Order order = new Order(account, "Pending", created, totallead, totalmetal, comment, date);
		oDAO.saveOrder(order);

		String uploadedFileLocation = "A:\\Users\\Q\\eclipse-workspace\\Warehouse\\src\\main\\webapp\\images\\uploaded\\"+account.getUsername()+"\\Order-"+order.getID()+"\\1-"+fileDetail.getFileName();
		String uploadedFileLocation2 = "A:\\Users\\Q\\eclipse-workspace\\Warehouse\\src\\main\\webapp\\images\\uploaded\\"+account.getUsername()+"\\Order-"+order.getID()+"\\2-"+fileDetail2.getFileName();
		
		// save it
		writeToFile(uploadedInputStream, uploadedFileLocation);
		writeToFile(uploadedInputStream2, uploadedFileLocation2);
		
		System.out.println(date.toString()); 		
		return Response.status(200).entity(dateOfRun).build();
	}
	// save uploaded file to new location
	private void writeToFile(InputStream uploadedInputStream,
		String uploadedFileLocation) {

		try {
			
			File file = new File(uploadedFileLocation);
			file.getParentFile().mkdirs();
			OutputStream out = new FileOutputStream(file);
			int read = 0;
			byte[] bytes = new byte[1024];

			//out = new FileOutputStream(new File(uploadedFileLocation));
			while ((read = uploadedInputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			out.flush();
			out.close();
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

}