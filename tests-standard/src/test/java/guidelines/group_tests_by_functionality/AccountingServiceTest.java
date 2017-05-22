package guidelines.group_tests_by_functionality;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.beans.factory.annotation.Autowired;

import de.adesso.expressivetesting.system.service.AccountingService;
import testsupport.SpringComponentTest;

@RunWith(Suite.class)
@Suite.SuiteClasses(
    {
        AccountingServiceTest.Credit.class,
        AccountingServiceTest.Debit.class
    }
)
public class AccountingServiceTest {
    /*
     * NOTE: Common definitions can be pulled-up into superclass, see AccountingServiceTestRefactored
     */
    
    /*
     * NOTE: Tests on top-level are not possible - they must all be defined in the nested classes.
     */
    
    public static class Credit extends SpringComponentTest {
        @Autowired
        private AccountingService accountingService;
        
        private Date testStartDate;
        
        @Before
        public void setUp() {
            testStartDate = new Date();
        }
        
        @Test
        public void NotExceedingBalance_MustAddCreditBookingToAccount() {
            // Test code omitted
        }
        
        @Test
        public void NotExceedingBalance_MustComputeAccountBalanceCorrectly() {
            // Test code omitted
        }
        
        @Test
        public void ExceedingBalance_MustAddCreditBookingToAccount() {
            // Test code omitted
        }
        
        @Test
        public void ExceedingBalance_MustComputeAccountBalanceCorrectly() {
            // Test code omitted
        }
        
        @Test
        public void ExceedingOverdraftLimit_MustFail() {
            // Test code omitted
        }
    }
    
    
    
    
    public static class Debit extends SpringComponentTest {
        @Autowired
        private AccountingService accountingService;
        
        private Date testStartDate;
        
        @Before
        public void setUp() {
            testStartDate = new Date();
        }
        
        @Test
        public void MustAddDebitBookingToAccount() {
            // Test code omitted
        }
        
        @Test
        public void MustIncreaseAccountBalance() {
            // Test code omitted
        }
        
    }
    
}
