package guidelines.create_testing_dsl.assertions;

import static de.adesso.expressivetesting.system.domain.BookingType.Credit;
import static guidelines.create_testing_dsl.assertions.CreditorConditions.name;
import static guidelines.create_testing_dsl.assertions.CreditorConditions.referenceId;
import static guidelines.create_testing_dsl.builder.domain.AccountBuilder.account;
import static guidelines.create_testing_dsl.builder.domain.AccountBuilder.anyAccount;
import static guidelines.create_testing_dsl.builder.domain.AccountBuilder.BalanceBookingSpecification.resultingFrom;
import static guidelines.create_testing_dsl.builder.domain.CreditorBuilder.anyCreditor;
import static guidelines.create_testing_dsl.builder.domain.CreditorBuilder.creditor;
import static org.assertj.core.api.Assertions.allOf;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import de.adesso.expressivetesting.system.domain.Account;
import de.adesso.expressivetesting.system.domain.Booking;
import de.adesso.expressivetesting.system.domain.CreditBooking;
import de.adesso.expressivetesting.system.service.AccountingService;
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
        
        /*
         * NOTE: We are using the build-in AssertJ assertions here.
         *       This can be simplified by using generated assertion classes, see AccountingServiceTestUsingGen
         */
        
        assertThat(resultingAccount).describedAs("account")
            .isNotNull() // NOTE: We could also move the not-null check into the 'satisfies' check, but this here is easier to read 
            .satisfies(
                a -> {
                    assertThat(a.getBookings()).describedAs("account:bookings").hasSize(2);
                }
            );
        assertThat(lastBookingIn(resultingAccount)).describedAs("lastBooking")
            .isNotNull()
            .isInstanceOfSatisfying(
                CreditBooking.class, 
                b -> {
                    assertThat(b.getType()).describedAs("lastBooking:type").isEqualTo(Credit);
                    assertThat(b.getAmount()).describedAs("lastBooking:amount").isEqualTo(EUR("99.99"));
                    assertThat(b.getReason()).describedAs("lastBooking:reason").isEqualTo("Strom Abschlag");
                    assertThat(b.getDate()).describedAs("lastBooking:date").isBetween(testStartDate, new Date(), true, true);
                    assertThat(b.getCreditor()).describedAs("lastBooking.creditor")
                        // NOTE (1): Check creditor, using conditions build with lambda expressions.
                        // NOTE: This produces vague failure messages.
                        .isNotNull()
                        .has(allOf( 
                                name("Vattenfall Europe"), 
                                referenceId("VE1234")
                            )
                        );
                    assertThat(b.getCreditor()).describedAs("lastBooking.creditor")
                        // NOTE (2): Alternative for (1), using reflection to compare field values.
                        // NOTE: Here we have detailed failure messages.
                        // NOTE: Disadvantage is that this is quite fragile.
                        .isNotNull()
                        .hasFieldOrPropertyWithValue("name", "Vattenfall Europe")
                        .hasFieldOrPropertyWithValue("referenceId", "VE1234");
                    assertThat(b.getCreditor()).describedAs("lastBooking.creditor")
                        // NOTE (3): Alternative for (1), using build-in introspection base comparison mechanism.
                        // NOTE: Here we have detailed failure messages.
                        .isNotNull()
                        .isEqualToComparingFieldByField(
                            creditor()
                                .name("Vattenfall Europe")
                                .referenceId("VE1234")
                            .build()
                        );
                }
            );
        
        /*
         * NOTE: AssertJ has many additional useful features, including:
         *       - filtering on iterables
         *       - extracting properties of elements contained in iterables
         *       - soft assertions
         *       - assertions for testing thrown exceptions
         */
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
        
        assertThat(resultingAccount).describedAs("account").isNotNull();
        assertThat(resultingAccount.getBalance()).describedAs("account:balance").isEqualTo(EUR("0.01"));
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
