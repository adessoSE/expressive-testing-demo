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
        AccountingServiceTestDeeplyNested.Credit.class,
        AccountingServiceTestDeeplyNested.Debit.class
    }
)
public class AccountingServiceTestDeeplyNested {

    @RunWith(Suite.class)
    @Suite.SuiteClasses(
        {
            AccountingServiceTestDeeplyNested.Credit.NotExceedingBalance.class,
            AccountingServiceTestDeeplyNested.Credit.ExceedingBalance.class,
            AccountingServiceTestDeeplyNested.Credit.General.class
        }
    )
    public static class Credit extends TestingBase {
        
        public static class NotExceedingBalance extends TestingBase {
            
            @Before
            public void Given() {
                // NOTE: Establish common fixture here
            }
            
            @Test
            public void NotExceedingBalance_MustAddCreditBookingToAccount() {
                // Test code omitted
            }
            
            @Test
            public void NotExceedingBalance_MustComputeAccountBalanceCorrectly() {
                // Test code omitted
            }
        }
        
        public static class ExceedingBalance extends TestingBase {
            
            @Before
            public void Given() {
                // NOTE: Establish common fixture here
            }
            
            @Test
            public void ExceedingBalance_MustAddCreditBookingToAccount() {
                // Test code omitted
            }
            
            @Test
            public void ExceedingBalance_MustComputeAccountBalanceCorrectly() {
                // Test code omitted
            }
        }
        
        public static class General extends TestingBase {
            @Test
            public void ExceedingOverdraftLimit_MustFail() {
                // Test code omitted
            }
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
