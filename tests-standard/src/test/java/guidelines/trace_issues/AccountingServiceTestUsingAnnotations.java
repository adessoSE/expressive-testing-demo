package guidelines.trace_issues;

import org.junit.Test;

import guidelines.trace_issues.annotations.Bug;
import guidelines.trace_issues.annotations.Feature;
import guidelines.trace_issues.annotations.Story;

public class AccountingServiceTestUsingAnnotations {

    @Test
    @Feature("Accounting")
    @Story("ADD-201")
    public void CreditNotExceedingBalance_MustAddCreditBookingToAccount() {
        // Test code omitted
    }
    
    @Test
    @Feature("Accounting")
    @Story("ADD-201")
    @Bug("ADD-666")
    @Bug("ADD-123")
    public void CreditNotExceedingBalance_MustComputeAccountBalanceCorrectly() {
        // Test code omitted
    }
}
