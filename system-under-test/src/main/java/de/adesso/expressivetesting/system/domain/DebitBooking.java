package de.adesso.expressivetesting.system.domain;

import java.math.BigDecimal;

public class DebitBooking extends Booking {
	
	private final Debitor debitor;

	public DebitBooking(BigDecimal amount, Debitor debitor, String reason) {
		super(BookingType.Debit, amount, reason);
		this.debitor = debitor;
	}

    public Debitor getDebitor() {
        return debitor;
    }
	
}
