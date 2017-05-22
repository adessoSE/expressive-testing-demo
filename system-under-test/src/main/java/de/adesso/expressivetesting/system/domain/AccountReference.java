package de.adesso.expressivetesting.system.domain;

public class AccountReference implements Comparable<AccountReference> {

	private final String iban;

	public AccountReference(String iban) {
	    if (iban == null) {
	        throw new IllegalArgumentException("IBAN must not be null");
	    }
		this.iban = iban;
	}
	
    @Override
    public int hashCode() {
        return iban.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        
        AccountReference other = (AccountReference) obj;
        return this.iban.equals(other.iban);
    }

    @Override
    public String toString() {
        return iban;
    }

    @Override
    public int compareTo(AccountReference other) {
        if (this.equals(other)) {
            return 0;
        }
        return this.iban.compareTo(other.iban);
    }
	
}
