package de.adesso.expressivetesting.system.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import de.adesso.expressivetesting.system.domain.Account;
import de.adesso.expressivetesting.system.domain.CreditBooking;
import de.adesso.expressivetesting.system.domain.Creditor;

@Component
public class AccountingService {

	public Account credit(Account account, BigDecimal amount, Creditor creditor, String reason) {
		account.addBooking(new CreditBooking(amount, creditor, reason));
		return account;
	}
}
