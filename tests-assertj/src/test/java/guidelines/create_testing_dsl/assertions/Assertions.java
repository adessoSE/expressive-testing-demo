package guidelines.create_testing_dsl.assertions;

import de.adesso.expressivetesting.system.domain.Account;
import de.adesso.expressivetesting.system.domain.AccountAssert;
import de.adesso.expressivetesting.system.domain.Booking;
import de.adesso.expressivetesting.system.domain.BookingAssert;
import de.adesso.expressivetesting.system.domain.CreditBooking;
import de.adesso.expressivetesting.system.domain.CreditBookingAssert;
import de.adesso.expressivetesting.system.domain.Creditor;
import de.adesso.expressivetesting.system.domain.CreditorAssert;

/*
 * FIXME: This class should have been generated automatically!
 */
public final class Assertions extends org.assertj.core.api.Assertions {

    public static AccountAssert assertThat(Account account) {
        return AccountAssert.assertThat(account);
    }
    
    public static BookingAssert assertThat(Booking booking) {
        return BookingAssert.assertThat(booking);
    }
    
    public static CreditBookingAssert assertThat(CreditBooking booking) {
        return CreditBookingAssert.assertThat(booking);
    }
    
    public static CreditorAssert assertThat(Creditor creditor) {
        return CreditorAssert.assertThat(creditor);
    }
    
}
