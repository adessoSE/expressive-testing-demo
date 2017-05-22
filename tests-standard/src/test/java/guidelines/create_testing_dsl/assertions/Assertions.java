package guidelines.create_testing_dsl.assertions;

import static guidelines.create_testing_dsl.assertions.domain.AccountAssertions.assertAccount;
import static guidelines.create_testing_dsl.assertions.domain.BookingAssertions.assertBooking;
import static guidelines.create_testing_dsl.assertions.domain.BookingAssertions.CreditBookingAssertions.assertCreditBooking;
import static guidelines.create_testing_dsl.assertions.domain.CreditorAssertions.assertCreditor;

import de.adesso.expressivetesting.system.domain.Account;
import de.adesso.expressivetesting.system.domain.Booking;
import de.adesso.expressivetesting.system.domain.CreditBooking;
import de.adesso.expressivetesting.system.domain.Creditor;
import guidelines.create_testing_dsl.assertions.domain.AccountAssertions;
import guidelines.create_testing_dsl.assertions.domain.BookingAssertions;
import guidelines.create_testing_dsl.assertions.domain.BookingAssertions.CreditBookingAssertions;
import guidelines.create_testing_dsl.assertions.domain.CreditorAssertions;

public final class Assertions {

    public static AccountAssertions assertThat(Account account) {
        return assertAccount(account);
    }
    
    public static <A extends BookingAssertions<A>> A assertThat(Booking booking) {
        return assertBooking(booking);
    }
    
    public static CreditBookingAssertions assertThat(CreditBooking booking) {
        return assertCreditBooking(booking);
    }
    
    public static CreditorAssertions assertThat(Creditor creditor) {
        return assertCreditor(creditor);
    }
    
    private Assertions() {}
    
}
