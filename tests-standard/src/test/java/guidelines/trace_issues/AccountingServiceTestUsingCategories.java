package guidelines.trace_issues;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import guidelines.trace_issues.categories.Bug;
import guidelines.trace_issues.categories.Feature;
import guidelines.trace_issues.categories.Story;

public class AccountingServiceTestUsingCategories {
    
    @Test
    @Category(
        {
            Feature.Accounting.class, 
            Story.ADD_201.class
        }
    )
    public void CreditNotExceedingBalance_MustAddCreditBookingToAccount() {
        // Test code omitted
    }
    
    @Test
    @Category(
        {
            Feature.Accounting.class, 
            Story.ADD_201.class,
            Bug.ADD_101.class
        }
    )
    public void CreditNotExceedingBalance_MustComputeAccountBalanceCorrectly() {
        // Test code omitted
    }
    
}
