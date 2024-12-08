package com.ticket;

public abstract class User {

	protected String emailAddress;
	protected String password;
	protected String username;
	

	public User(String emailAddress, String password) {
		this.emailAddress=emailAddress;
		this.password=password;
		}
	
	
	public User(String username, String emailAddress, String password) {
		this.username = username;
		this.emailAddress=emailAddress;
		this.password=password;
	}
	

public void setEmailAddress(String emailAddress) {
	this.emailAddress= emailAddress;
}

public String getEmailAddress() {
	return emailAddress;
}
public void setUserName(String username) {
	this.username= username;
}

public String getUserName() {
	return username;
}
public void setPassword(String password) {
	this.password= password;
}

public String getPassword() {
	return password;
}

public abstract String displayUserRole();

public abstract void postLoginMenu();

protected abstract Object getUserId();


public void addEvent() {
	// TODO Auto-generated method stub
	
}
}


