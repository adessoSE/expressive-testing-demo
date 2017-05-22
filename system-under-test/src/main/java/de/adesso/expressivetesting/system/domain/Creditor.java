package de.adesso.expressivetesting.system.domain;

public class Creditor {

	private String name;
	
	private String referenceId;
	
	private AccountReference account;

	public Creditor(String name, String referenceId, AccountReference account) {
		this.name = name;
		this.referenceId = referenceId;
		this.account = account;
	}
	
	public Creditor() {}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}

	public AccountReference getAccount() {
		return account;
	}

	public void setAccount(AccountReference account) {
		this.account = account;
	}

}
