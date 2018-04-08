package Corleone.Warehouse.domain;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.imageio.ImageIO;

public class Order {
private int ID;
private Account account;
private BufferedImage screenshotOne;
private BufferedImage screenshotTwo;
private String status;
private Date created;
private int totalLead;
private int totalMetal;
private String comment;
private Date dateOfRun;

public Order(int ID, Account account, String status, Date created,
		int totalLead, int totalMetal, String comment, Date dateOfRun) throws IOException {
	this.ID = ID;
	this.account = account;
//	this.screenshotOne = ImageIO.read(new File("images/uploaded/"+ account.getUsername()+"/"+created.toString() + ": " + created.getTime()+"-1.png"));
//	this.screenshotTwo = ImageIO.read(new File("images/uploaded/"+ account.getUsername()+"/"+created.toString() + ": " + created.getTime()+"-2.png"));
	this.status = status;
	this.created = created;
	this.totalLead = totalLead;
	this.totalMetal = totalMetal;
	this.comment = comment;
	this.dateOfRun = dateOfRun;

}

public Order(Account account, String status, Date created,
		int totalLead, int totalMetal, String comment, Date dateOfRun) throws IOException {
	this.account = account;
//	this.screenshotOne = ImageIO.read(new File("images/uploaded/"+ account.getUsername()+"/"+created.toString() + ": " + created.getTime()+"-1.png"));
//	this.screenshotTwo = ImageIO.read(new File("images/uploaded/"+ account.getUsername()+"/"+created.toString() + ": " + created.getTime()+"-2.png"));
	this.status = status;
	this.created = created;
	this.totalLead = totalLead;
	this.totalMetal = totalMetal;
	this.comment = comment;
	this.dateOfRun = dateOfRun;

}

public Date getDateOfRun() {
	return dateOfRun;
}

public void setDateOfRun(Date dateOfRun) {
	this.dateOfRun = dateOfRun;
}

public int getID() {
	return ID;
}

public Account getAccount() {
	return account;
}

public BufferedImage getScreenshotOne() {
	return screenshotOne;
}

public BufferedImage getScreenshotTwo() {
	return screenshotTwo;
}

public String getStatus() {
	return status;
}

public Date getCreated() {
	return created;
}

public int getTotalLead() {
	return totalLead;
}

public int getTotalMetal() {
	return totalMetal;
}

public String getComment() {
	return comment;
}

public void setID(int ID) {
	this.ID = ID;
}

public void setAccount(Account account) {
	this.account = account;
}



public void setStatus(String status) {
	this.status = status;
}

public void setCreated(Date date) {
	this.created= date;
}

public void setTotalLead(int lead) {
	this.totalLead = lead;
}

public void setTotalMetal(int metal) {
	this.totalMetal = metal;
}
public void setComment(String comment) {
	this.comment = comment;
}

}
