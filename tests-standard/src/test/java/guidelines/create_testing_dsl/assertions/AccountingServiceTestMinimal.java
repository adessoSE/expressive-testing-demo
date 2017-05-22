package guidelines.create_testing_dsl.assertions;

import static de.adesso.expressivetesting.system.domain.BookingType.Credit;
import static guidelines.create_testing_dsl.assertions.Assertions.assertThat;
import static guidelines.create_testing_dsl.assertions.domain.CreditorAssertions.Conditions.hasName;
import static guidelines.create_testing_dsl.assertions.domain.CreditorAssertions.Conditions.hasReferenceId;
import static guidelines.create_testing_dsl.builder.domain.AccountBuilder.account;
import static guidelines.create_testing_dsl.builder.domain.AccountBuilder.anyAccount;
import static guidelines.create_testing_dsl.builder.domain.AccountBuilder.BalanceBookingSpecification.resultingFrom;
import static guidelines.create_testing_dsl.builder.domain.CreditorBuilder.anyCreditor;
import static guidelines.create_testing_dsl.builder.domain.CreditorBuilder.creditor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import de.adesso.expressivetesting.system.domain.Account;
import de.adesso.expressivetesting.system.domain.Booking;
import de.adesso.expressivetesting.system.service.AccountingService;
import testsupport.SpringComponentTest;

public class AccountingServiceTestMinimal extends SpringComponentTest {

    @Autowired
    private AccountingService accountingService;
    
    private Date testStartDate;
    
    @Before
    public void setUp() {
        testStartDate = new Date();
    }
    
    @Test
    public void CreditNotExceedingBalance_MustAddCreditBookingToAccount() {
        Account account =
            account(anyAccount())
                .balance(EUR("100.00"), resultingFrom(1).booking())
            .build();
        
        Account resultingAccount = 
            accountingService
                .credit(
                    account, 
                    EUR("99.99"), 
                    creditor()
                        .name("Vattenfall Europe")
                        .referenceId("VE1234")
                        .build(), 
                    "Strom Abschlag"
                );
        
        assertThat(resultingAccount).named("account")
            .exists()
            .hasBookings(2); // NOTE: This could also be written as 'has(2).bookings()' quite easily.
        assertThat(lastBookingIn(resultingAccount)).named("lastBooking")
            .exists()
            .isCreditBooking()
            .hasType(Credit)
            .hasAmount(EUR("99.99"))
            .hasReason("Strom Abschlag")
            .hasDateWithin(testStartDate, new Date())
            .creditor(
                hasName("Vattenfall Europe"),
                hasReferenceId("VE1234")
            );
    }
    
    
    @Test
    public void CreditNotExceedingBalance_MustComputeAccountBalanceCorrectly() {
        Account account =
            account(anyAccount())
                .balance(EUR("100.00"), resultingFrom(3).bookings())
            .build();
        
        Account resultingAccount = 
            accountingService
                .credit(
                    account, 
                    EUR("99.99"), 
                    anyCreditor(), 
                    "Strom Abschlag"
                );
        
        assertThat(resultingAccount).named("account")
            .exists()
            .hasBalance(EUR("0.01"));
    }
    
    /*
     * NOTE: This is just syntactic sugar for specifying money amounts (assuming EUR).
     */
    private static BigDecimal EUR(String amount) {
        return new BigDecimal(amount);
    }
    
    /*
     * NOTE: This is just syntactic sugar for retrieving the last booking from a account.
     */
    private static Booking lastBookingIn(Account account) {
        List<Booking> bookings = account.getBookings();
        if (bookings.isEmpty()) {
            return null;
        }
        
        return bookings.get(bookings.size() - 1);
    }
}
