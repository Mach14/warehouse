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
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import Corleone.Warehouse.domain.Account;
import Corleone.Warehouse.persistence.AccountDAO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;

@Path("/accounts")
public class AccountResource {
	private AccountDAO aDAO = new AccountDAO();
	
	@POST
	@Produces("application/json")
	public String createAccount(@FormParam("username") String username, @FormParam("igname") String igname, @FormParam("password") String password) {
		Date date = new Date();
		Account account = new Account(username, igname, password, "pending",0,0,0,0,0,"Enforcer",3,"Non Active",date);
		aDAO.saveAccount(account);
		return accountToJson(account).build().toString();
	}
	
	@GET
	@RolesAllowed("admin")
	@Produces("application/json")
	public String getAccounts() {
		JsonArrayBuilder jab = Json.createArrayBuilder();
		for(Account a : aDAO.findAll()){ 
			JsonObjectBuilder job = accountToJson(a);
			jab.add(job);
		}
		JsonArray array = jab.build();
		return array.toString();
	}
	
	@POST
	@RolesAllowed("admin")
	@Path("{ID}")
	@Produces("application/json")
	public String updateCustomer(@PathParam("ID") int id, @FormParam("Rank") String rank, @FormParam("Tier") int tier, @FormParam("Status") String status) {
		Account found = null;
		for(Account a : aDAO.findAll()) {
			if(a.getID() == id) {
				found = a;
				
				found.setRank(rank);
				found.setTier(tier);
				found.setStatus(status);
				if(found.getStatus().equals("Faction Killed")) {
					found.setRole("guest");
				}
				else if (found.getStatus().equals("Non Active")) {
					found.setRole("guest");
				}
				else if(found.getStatus().equals("Member")) {
					found.setRole("user");
				}
				else if(found.getStatus().equals("Admin")) {
					found.setRole("admin");
				}
				System.out.print(a.getRank());
				System.out.print(a.getID());
				System.out.print(a.getStatus());
				System.out.print("Tier: " + a.getTier());

				aDAO.updateAccount(found);
				return accountToJson(found).build().toString();
			}
}
	throw new WebApplicationException("Account not found!");
	}

	
	@GET
	@RolesAllowed({"admin","user"})
	@Path("{ID}")
	@Produces("application/json")
	public String getAccount(@PathParam("ID")int ID) {
		Account a = aDAO.findAccountByPK(ID);
		if(a == null){
			throw new WebApplicationException("Account not found!");
		}
		
		JsonObjectBuilder job = accountToJson(a);
		return job.build().toString();
	}
	
	@GET
	@RolesAllowed({"admin","user"})
	@Path("username/{username}")
	@Produces("application/json")
	public String getAccountByUsername(@PathParam("username") String username) {
		Account a = aDAO.findAccountByUsername(username);
		if(a == null){
			throw new WebApplicationException("Account doesn't exist!");
		}
		JsonObjectBuilder job = accountToJson(a);
		return job.build().toString();
	}
	
	
	@GET
	@RolesAllowed({"admin","user"})
	@Path("token/{token}")
	public String getUsername(@PathParam("token") String token) {
		try {
			JwtParser parser = Jwts.parser().setSigningKey(AuthenticationResource.key);
			Claims claims = parser.parseClaimsJws(token).getBody();
			String username = claims.getSubject();
			Account account = aDAO.findAccountByUsername(username);
			
			if(account != null) {
				JsonObjectBuilder job = accountToJson(account);
				return job.build().toString();
			}
			else {
				return "Username doesn't exist.";
	
			}} 
			catch(ExpiredJwtException e) {
			
			return "expiredToken";
		}

	}
	
	@GET
	@RolesAllowed({"admin","user"})
	@Path("totalaccounts")
	@Produces("application/json")
	public String getTotalAccounts() throws IOException {
		int accs = aDAO.getTotalAccounts();
		JsonObjectBuilder job = Json.createObjectBuilder();
		job.add("TotalAccounts", accs);
		return job.build().toString();
	}
	
	
	private JsonObjectBuilder accountToJson(Account a ){
		JsonObjectBuilder job = Json.createObjectBuilder();
		job.add("ID", a.getID());
		job.add("Username", a.getUsername());
		job.add("IGName", a.getIngameName());
		job.add("Password", a.getPassword());
		job.add("Role", a.getRole());
		job.add("TotalLead", a.getTotalLead());
		job.add("TotalMetal", a.getTotalMetal());
		job.add("MatsReceived", a.getMatsReceived());
		job.add("MatsToReceive", a.getMatsToReceive());
		job.add("MatsEarned", a.getTotalMatsEarned());
		job.add("Rank", a.getRank());
		job.add("Tier", a.getTier());
		job.add("Status", a.getStatus());
		job.add("Created", a.getCreated().toString());
		return job;
	}

}
