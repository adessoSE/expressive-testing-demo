package de.adesso.expressivetesting.system.domain;

import java.math.BigDecimal;
import java.util.Date;

public abstract class Booking {
	
	private final Date date;

	private final BookingType type;
	
	private final BigDecimal amount;
	
	private final String reason;

	public Booking(BookingType type, BigDecimal amount, String reason) {
		this.date = new Date();
		this.type = type;
		this.amount = amount;
		this.reason = reason;
	}
	
	public Date getDate() {
		return date;
	}

	public BookingType getType() {
		return type;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public String getReason() {
		return reason;
	}
	
}
