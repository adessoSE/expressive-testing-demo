package de.adesso.expressivetesting.system.domain;

public class AccountHolder {
	
	private String customerNumber;

	private String firstName;
	
	private String lastName;

	public AccountHolder(String customerNumber, String firstName, String lastName) {
		this.customerNumber = customerNumber;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public AccountHolder() {}

	public String getCustomerNumber() {
		return customerNumber;
	}

	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
}
