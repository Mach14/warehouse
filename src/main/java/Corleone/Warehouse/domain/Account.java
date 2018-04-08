package Corleone.Warehouse.domain;

import java.util.Date;

public class Account {
	private int ID;
	private String username;
	private String ingameName;
	private String password;
	private String role;
	private int totalLead;
	private int totalMetal;
	private int matsReceived;
	private int matsToReceive;
	private int matsEarned;
	private String rank;
	private int tier;
	private String status;
	private Date created;
	
	
	public Account(int ID, String username, String ingameName, String password, String role, int totalLead, 
			int totalMetal, int matsReceived, int matsToReceive, int matsEarned, String rank, int tier,
			String status, Date created) {
		this.ID = ID;
		this.username = username;
		this.ingameName = ingameName;
		this.password = password;
		this.role = role;
		this.totalLead = totalLead;
		this.totalMetal = totalMetal;
		this.matsReceived = matsReceived;
		this.matsToReceive = matsToReceive;
		this.matsEarned = matsEarned;
		this.rank = rank;
		this.tier = tier;
		this.status = status;
		this.created = created;
	}
	
	public Account(String username, String ingameName, String password, String role, int totalLead, 
			int totalMetal, int matsReceived, int matsToReceive, int matsEarned, String rank, int tier,
			String status, Date created) {
		this.username = username;
		this.ingameName = ingameName;
		this.password = password;
		this.role = role;
		this.totalLead = totalLead;
		this.totalMetal = totalMetal;
		this.matsReceived = matsReceived;
		this.matsToReceive = matsToReceive;
		this.matsEarned = matsEarned;
		this.rank = rank;
		this.tier = tier;
		this.status = status;
		this.created = created;
	}
	
	public int getID() {
		return ID;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getIngameName() {
		return ingameName;
	}
	
	public String getPassword() {
		return password;
	}
	
	public String getRole() {
		return role;
	}
	
	public int getTotalLead() {
		return totalLead;
	}
	
	public int getTotalMetal() {
		return totalMetal;
	}
	
	public int getMatsReceived() {
		return matsReceived;
	}
	
	public int getMatsToReceive() {
		return matsToReceive;
	}
	
	public String getRank() {
		return rank;
	}
	
	public int getTier() {
		return tier;
	}
	
	public int getTotalMatsEarned() {
		return matsEarned;
	}
	
	public String getStatus() {
		return status;
	}
	
	public Date getCreated() {
		return created;
	}
	
	public void setID(int ID) {
		this.ID = ID;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public void setIngameName(String igName) {
		this.ingameName = igName;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setRole(String role) {
		this.role = role;
	}
	
	
	
	public void setTotalLead(int lead) {
		this.totalLead = lead;
	}

	public void setTotalMetal(int metal) {
		this.totalMetal = metal;
	}
	
	public void setMatsReceived(int mats) {
		this.matsReceived = mats;
	}
	public void setMatsToReceive(int mats) {
		this.matsToReceive = mats;
	}
	
	public void setRank(String rank) {
		this.rank = rank;
	}
	
	public void setTier(int tier) {
		this.tier = tier;
	}
	
	public void setTotalMats(int mats) {
		this.matsEarned = mats;
	}
	
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public void setCreated(Date date) {
		this.created = date;
	}
	
}
