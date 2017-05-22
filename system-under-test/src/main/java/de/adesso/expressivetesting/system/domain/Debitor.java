package de.adesso.expressivetesting.system.domain;

public class Debitor {

private String name;
	
	private String referenceId;
	
	private AccountReference account;

	public Debitor(String name, String referenceId, AccountReference account) {
		this.name = name;
		this.referenceId = referenceId;
		this.account = account;
	}

	public Debitor() {}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public AccountReference getAccount() {
		return account;
	}

	public void setAccount(AccountReference account) {
		this.account = account;
	}

	public String getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}
	 
}
