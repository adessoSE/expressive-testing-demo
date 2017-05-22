package guidelines.create_testing_dsl.assertions.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.util.List;

import de.adesso.expressivetesting.system.domain.Account;
import de.adesso.expressivetesting.system.domain.Booking;
import guidelines.create_testing_dsl.assertions.BaseAssertions;

public final class AccountAssertions extends BaseAssertions<AccountAssertions> {
    
    /*
     * Named entry-point
     */
    public static AccountAssertions assertAccount(Account account) {
        return new AccountAssertions(account);
    }
    
    /*
     * Generic entry-point
     * 
     * NOTE: This may cause naming conflicts. We should therefore create a central entry-point class (see Assertions).
     */
    public static AccountAssertions assertThat(Account account) {
        return new AccountAssertions(account);
    }

    
    private final Account account;
    
    private AccountAssertions(Account account) {
        this.account = account;
        this.description = "account";
    }
    
    /*
     * NOTE: We may pull this up into a superclass.
     */
    public AccountAssertions exists() {
        assertNotNull(description + " {exists}", account);
        return this;
    }
    
    public AccountAssertions hasBookings(int expectedNumberOfBookings) {
        List<Booking> bookings = account.getBookings();
        
        assertNotNull(description + ":bookings {exists}", bookings);
        assertEquals(description + ":bookings {size}", expectedNumberOfBookings, bookings.size());
        return this;
    }
    
    public AccountAssertions hasBookings() {
        List<Booking> bookings = account.getBookings();
        
        assertNotNull(description + ":bookings {exists}", bookings);
        assertFalse(description + ":bookings {notEmpty}", bookings.isEmpty());
        return this;
    }
    
    public AccountAssertions hasNoBookings() {
        return hasBookings(0);
    }
    
    public AccountAssertions hasBalance(BigDecimal expectedBalance) {
        assertEquals(description + ":balance {equals}", expectedBalance, account.getBalance());
        return this;
    }
    
    public AccountAssertions hasBalance() {
        assertNotNull(description + ":balance {exists}", account.getBalance());
        return this;
    }
    
}
