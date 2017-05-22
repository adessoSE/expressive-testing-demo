package guidelines.group_tests_by_functionality;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import de.adesso.expressivetesting.system.service.AccountingService;

@RunWith(JUnitPlatform.class) // NOTE: This is just needed for running JUnit 5 tests in Eclipse
@DisplayName("Accounting service contracts")
public class AccountingServiceTest {
    
    /*
     * NOTE: Tests on top-level work - no need to push them to nested classes any more.
     */
    @Test
    public void TestsOnTopLevelAlsoWork() {
        // No-Op
    }
    

    @Nested
    @DisplayName("Credit account")
    @ExtendWith(SpringExtension.class)
    @ContextConfiguration("/META-INF/spring/config.xml")
    class Credit {
        
        @Autowired
        private AccountingService accountingService;
        
        
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
    
    
    @Nested
    @DisplayName("Debit account")
    class Debit {
        
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
