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

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import de.adesso.expressivetesting.system.domain.Account;
import de.adesso.expressivetesting.system.service.AccountingService;
import guidelines.create_testing_dsl.assertions.domain.CreditorAssertions;
import testsupport.SpringComponentTest;

public class AccountingServiceTest extends SpringComponentTest {

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
        
        assertThat(resultingAccount).named(" reswulting account")
            .exists()
            .hasBookings(2);
        assertThat(resultingAccount.getBookings().get(1)).named("lastBooking") // NOTE: This can even further be simplified, see AccountingServiceTestMinimal
            .exists()
            .isCreditBooking()
            .hasType(Credit)
            .hasAmount(EUR("99.99"))
            .hasReason("Strom Abschlag")
            .hasDateWithin(testStartDate, new Date())
            .hasCreditor()
            .hasCreditor( // NOTE (1): Check creditor, using comparator build with method references
                creditor()
                    .name("Vattenfall Europe")
                    .referenceId("VE1234")
                .build()
            )
            .creditor( // NOTE (2): Alternative for (1), explicitly comparing attributes using build-in function 
                       //           interface (Predicate).
                       // NOTE: Disadvantage is that no detailed failure message are possible when using this.
                CreditorAssertions.Predicates.hasName("Vattenfall Europe"), 
                CreditorAssertions.Predicates.hasReferenceId("VE1234")
            )
            .creditor( // NOTE (3): Alternative for (2), using chaining for predicates
                CreditorAssertions.Predicates.hasName("Vattenfall Europe")
                .and(CreditorAssertions.Predicates.hasReferenceId("VE1234"))
            )
            .creditor( // NOTE (4): Alternative for (2), we may also use lambda expression directly 
                c -> "Vattenfall Europe".equals(c.getName()), 
                c -> "VE1234".equals(c.getReferenceId())
            )
            .creditor( // NOTE (5): Alternative for (1), explicitly comparing attributes using helper objects (Conditon)
                       //           build with lambda expressions.
                       // NOTE: Here we have detailed failure messages.
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
    
}
