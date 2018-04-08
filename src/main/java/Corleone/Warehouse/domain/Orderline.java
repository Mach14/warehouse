package Corleone.Warehouse.domain;

public class Orderline {
private int ID;
private int lead;
private int metal;
private Order order;
private Account account;

public Orderline(int ID, int lead, int metal, Order order, Account account) {
	this.ID = ID;
	this.lead = lead;
	this.metal = metal;
	this.order = order;
	this.account = account;
}

public Orderline(int lead, int metal, Order order, Account account) {
	this.lead = lead;
	this.metal = metal;
	this.order = order;
	this.account = account;
}

public int getID() {
	return ID;
}

public int getLead() {
	return lead;
}

public int getMetal() {
	return metal;
}

public Order getOrder() {
	return order;
}


public Account getAccount() {
	return account;
}

public void setID(int ID) {
	this.ID = ID;
}

public void setLead(int lead) {
	this.lead = lead;
}

public void setMetal(int metal) {
	this.metal = metal;
}

public void setOrder(Order order) {
	this.order = order;
}

public void setAccount(Account account) {
	this.account = account;
}

}
