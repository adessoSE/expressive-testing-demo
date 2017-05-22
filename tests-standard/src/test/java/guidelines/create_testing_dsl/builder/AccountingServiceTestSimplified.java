package guidelines.create_testing_dsl.builder;

import static guidelines.create_testing_dsl.builder.domain.AccountBuilder.account;
import static guidelines.create_testing_dsl.builder.domain.AccountBuilder.anyAccount;
import static guidelines.create_testing_dsl.builder.domain.CreditorBuilder.anyCreditor;
import static guidelines.create_testing_dsl.builder.domain.CreditorBuilder.creditor;
import static guidelines.create_testing_dsl.builder.domain.DebitBookingBuilder.anyDebitBooking;
import static guidelines.create_testing_dsl.builder.domain.DebitBookingBuilder.debitBooking;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import de.adesso.expressivetesting.system.domain.Account;
import de.adesso.expressivetesting.system.domain.Booking;
import de.adesso.expressivetesting.system.domain.BookingType;
import de.adesso.expressivetesting.system.domain.CreditBooking;
import de.adesso.expressivetesting.system.domain.Creditor;
import de.adesso.expressivetesting.system.service.AccountingService;
import testsupport.SpringComponentTest;

public class AccountingServiceTestSimplified extends SpringComponentTest {

    @Autowired
    private AccountingService accountingService;
    
    private Date testStartDate;
    
    @Before
    public void setUp() {
        testStartDate = new Date();
    }
    
    @Test
    public void CreditNotExceedingBalance_MustAddCreditBookingToAccount() {
        /*
         * NOTE: This can even further be simplified, see AccountingServiceTestMinimal
         */
        Account account =
            account(anyAccount())
                .bookings(
                    debitBooking(anyDebitBooking())
                        .amount(new BigDecimal("100.00"))
                    .build()
                )
            .build();
        
        Account resultingAccount = 
            accountingService
                .credit(
                    account, 
                    new BigDecimal("99.99"), 
                    creditor()
                        .name("Vattenfall Europe")
                        .referenceId("VE1234")
                        .build(), 
                    "Strom Abschlag"
                );
        
        assertNotNull("account {exists}", resultingAccount);
        assertEquals("account:bookings {size}", 2, resultingAccount.getBookings().size());
        
        Booking lastBooking = resultingAccount.getBookings().get(1);
        assertSame("lastBooking {isA:CreditBooking}", CreditBooking.class, lastBooking.getClass());
        assertEquals("lastBooking:type {equals}", BookingType.Credit, lastBooking.getType());
        assertEquals("lastBooking:amount {equals}", new BigDecimal("99.99"), lastBooking.getAmount());
        assertEquals("lastBooking:reason {equals}", "Strom Abschlag", lastBooking.getReason());
        assertNotNull("lastBooking:date {exists}", lastBooking.getDate());
        Date currentDate = new Date();
        assertTrue(
            "lastBooking:date {within[" + testStartDate + ";" + currentDate + "]} but was: " + lastBooking.getDate(),
            (lastBooking.getDate().after(testStartDate) || lastBooking.getDate().equals(testStartDate)) 
            && (lastBooking.getDate().before(currentDate) || lastBooking.getDate().equals(currentDate))
        );
        
        Creditor lastBookingCreditor = ((CreditBooking) lastBooking).getCreditor();
        assertNotNull("lastBooking:creditor {exists}", lastBookingCreditor);
        assertEquals("lastBooking:creditor:name {equals}", "Vattenfall Europe", lastBookingCreditor.getName());
        assertEquals("lastBooking:creditor:referenceId {equals}", "VE1234", lastBookingCreditor.getReferenceId());
    }
    
    @Test
    public void CreditNotExceedingBalance_MustComputeAccountBalanceCorrectly() {
        /*
         * NOTE: This can even further be simplified, see AccountingServiceTestMinimal
         */
        Account account =
            account(anyAccount())
                .bookings(
                    debitBooking(anyDebitBooking())
                        .amount(new BigDecimal("100.00"))
                    .build()
                )
            .build();
        
        Account resultingAccount = 
            accountingService
                .credit(
                    account, 
                    new BigDecimal("99.99"), 
                    anyCreditor(), 
                    "Strom Abschlag"
                );
        
        assertNotNull("account {exists}", resultingAccount);
        assertEquals("account:balance {equals}", new BigDecimal("0.01"), resultingAccount.getBalance());
    }
    
}
