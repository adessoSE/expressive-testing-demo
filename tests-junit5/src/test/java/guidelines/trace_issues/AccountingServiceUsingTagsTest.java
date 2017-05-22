package guidelines.trace_issues;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class) // NOTE: This is just needed for running JUnit 5 tests in Eclipse
@DisplayName("Test using tags")
public class AccountingServiceUsingTagsTest {

    @Test
    @Tag("feature:accounting")
    @Tag("story:ADD-201")
    public void CreditNotExceedingBalance_MustAddCreditBookingToAccount() {
        // Test code omitted
    }
    
    @Test
    @Tag("feature:accounting")
    @Tag("story:ADD-201")
    @Tag("bug:ADD-666")
    public void CreditNotExceedingBalance_MustComputeAccountBalanceCorrectly() {
        // Test code omitted
    }
}
