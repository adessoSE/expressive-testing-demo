package de.adesso.expressivetesting.system.domain;

import java.math.BigDecimal;

public class CreditBooking extends Booking {
	
	private final Creditor creditor;

	public CreditBooking(BigDecimal amount, Creditor creditor, String reason) {
		super(BookingType.Credit, amount, reason);
		this.creditor = creditor;
	}

	public Creditor getCreditor() {
		return creditor;
	}
	
}
