package guidelines.split_tests_by_expectations;

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
import de.adesso.expressivetesting.system.domain.AccountHolder;
import de.adesso.expressivetesting.system.domain.Booking;
import de.adesso.expressivetesting.system.domain.BookingType;
import de.adesso.expressivetesting.system.domain.CreditBooking;
import de.adesso.expressivetesting.system.domain.Creditor;
import de.adesso.expressivetesting.system.domain.DebitBooking;
import de.adesso.expressivetesting.system.domain.Debitor;
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
        AccountHolder accountHolder = new AccountHolder();
        accountHolder.setFirstName("John");
        accountHolder.setLastName("Doe");
        accountHolder.setCustomerNumber("test-001");
        Account account = new Account();
        account.setHolder(accountHolder);
        account.setIban("DE89 3704 0044 0532 0130 00");
        Debitor debitor = new Debitor();
        debitor.setName("adesso AG");
        debitor.setReferenceId("AD4711");
        account.addBooking(new DebitBooking(new BigDecimal("100.00"), debitor, "Reisekosten 201717"));
        
        Creditor creditor = new Creditor();
        creditor.setName("Vattenfall Europe");
        creditor.setReferenceId("VE1234");
        
        Account resultingAccount = accountingService.credit(account, new BigDecimal("99.99"), creditor, "Strom Abschlag");
        
        assertNotNull(resultingAccount);
        assertEquals(2, resultingAccount.getBookings().size());
        
        Booking lastBooking = resultingAccount.getBookings().get(1);
        assertSame(CreditBooking.class, lastBooking.getClass());
        assertEquals(BookingType.Credit, lastBooking.getType());
        assertEquals(new BigDecimal("99.99"), lastBooking.getAmount());
        assertEquals("Strom Abschlag", lastBooking.getReason());
        assertNotNull(lastBooking.getDate());
        Date currentDate = new Date();
        assertTrue(
            (lastBooking.getDate().after(testStartDate) || lastBooking.getDate().equals(testStartDate)) 
            && (lastBooking.getDate().before(currentDate) || lastBooking.getDate().equals(currentDate))
        );
        
        Creditor lastBookingCreditor = ((CreditBooking) lastBooking).getCreditor();
        assertNotNull(lastBookingCreditor);
        assertEquals("Vattenfall Europe", lastBookingCreditor.getName());
        assertEquals("VE1234", lastBookingCreditor.getReferenceId());
    }
    
    @Test
    public void CreditNotExceedingBalance_MustComputeAccountBalanceCorrectly() {
        AccountHolder accountHolder = new AccountHolder();
        accountHolder.setFirstName("John");
        accountHolder.setLastName("Doe");
        accountHolder.setCustomerNumber("test-001");
        Account account = new Account();
        account.setHolder(accountHolder);
        account.setIban("DE89 3704 0044 0532 0130 00");
        Debitor debitor = new Debitor();
        debitor.setName("adesso AG");
        debitor.setReferenceId("AD4711");
        account.addBooking(new DebitBooking(new BigDecimal("100.00"), debitor, "Reisekosten 201717"));
        
        Creditor creditor = new Creditor();
        creditor.setName("Vattenfall Europe");
        creditor.setReferenceId("VE1234");
        
        Account resultingAccount = accountingService.credit(account, new BigDecimal("99.99"), creditor, "Strom Abschlag");
        
        assertNotNull(resultingAccount);
        assertEquals(new BigDecimal("0.01"), resultingAccount.getBalance());
    }
}
