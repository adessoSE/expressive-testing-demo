package de.adesso.expressivetesting.system.domain;

import static de.adesso.expressivetesting.system.domain.BookingType.Credit;
import static java.math.BigDecimal.ZERO;
import static java.util.Collections.unmodifiableList;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import de.adesso.expressivetesting.system.domain.error.OverdarftLimitExceeded;

public class Account {
	
	public static final BigDecimal DefaultOverdraftLimit = new BigDecimal("1000.00");

	private String iban;
	
	private AccountHolder holder;
	
	private BigDecimal overdraftLimit;
	
	private final List<Booking> bookings = new ArrayList<>();
	
	private boolean bookingsChanged = false;
	
	private transient BigDecimal balance;

	public Account(String iban, AccountHolder holder) {
		this.iban = iban;
		this.holder = holder;
		this.overdraftLimit = DefaultOverdraftLimit;
	}
	
	public Account() {
		this.overdraftLimit = DefaultOverdraftLimit;
	}

	public String getIban() {
		return iban;
	}
	
	public void setIban(String iban) {
		this.iban = iban;
	}

	public AccountHolder getHolder() {
		return holder;
	}
	
	public void setHolder(AccountHolder holder) {
		this.holder = holder;
	}

	public BigDecimal getOverdraftLimit() {
		return overdraftLimit;
	}

	public void setOverdraftLimit(BigDecimal overdraftLimit) {
		this.overdraftLimit = overdraftLimit;
	}
	
	public BigDecimal getBalance() {
		if (balance == null || bookingsChanged) {
			BigDecimal newBalance = BigDecimal.ZERO;
			for (Booking booking: bookings) {
				switch (booking.getType()) {
					case Credit:
					    newBalance = newBalance.subtract(booking.getAmount());
						break;
					case Debit:
					    newBalance = newBalance.add(booking.getAmount());
						break;
					default:
						throw new IllegalStateException("Unsupported booking type: " + booking.getType());
				}
			}
			balance = newBalance;
		}
		return balance;
	}
	
	public void addBooking(Booking booking) {
		if (booking.getType() == Credit) {
			BigDecimal balancePreview = getBalance().subtract(booking.getAmount());
			if (balancePreview.compareTo(ZERO) < 0 && balancePreview.abs().compareTo(overdraftLimit) > 0) {
				throw new OverdarftLimitExceeded();
			}
		}
		bookings.add(booking);
		bookingsChanged = true;
	}

	public List<Booking> getBookings() {
		return unmodifiableList(bookings);
	}
	
}
