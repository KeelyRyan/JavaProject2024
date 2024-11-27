package com.ticket;

public abstract class User {

	protected String username;
	protected String emailAddress;
	protected String password;
	
public User(String emailAddress, String username, String password) {
	this.emailAddress=emailAddress;
	this.username=username;
	this.password=password;
	}
	

public void setUserName(String username) {
	this.username= username;
}

public String getUserName() {
	return username;
}

public void setEmailAddress(String emailAddress) {
	this.emailAddress= emailAddress;
}

public String getEmailAddress() {
	return emailAddress;
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


