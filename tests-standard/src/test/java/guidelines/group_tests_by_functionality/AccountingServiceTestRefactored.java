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
        AccountingServiceTestRefactored.Credit.class,
        AccountingServiceTestRefactored.Debit.class
    }
)
public class AccountingServiceTestRefactored {

    /*
     * NOTE: If reasonable, tests may further be nested, e.g. in order to share common fixtures.
     *       See AccountingServiceTestDeeplyNested
     */
    
    public static class Credit extends TestingBase {
        
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
    
    
    
    
    public static class Debit extends TestingBase {
        
        @Test
        public void MustAddDebitBookingToAccount() {
            // Test code omitted
        }
        
        @Test
        public void MustIncreaseAccountBalance() {
            // Test code omitted
        }
        
    }
    
    
    
    
    static abstract class TestingBase extends SpringComponentTest {
        @Autowired
        AccountingService accountingService;
        
        Date testStartDate;
        
        @Before
        public void setUp() {
            testStartDate = new Date();
        }
    }
}
