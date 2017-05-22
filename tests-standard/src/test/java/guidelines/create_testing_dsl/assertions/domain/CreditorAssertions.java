package guidelines.create_testing_dsl.assertions.domain;

import java.util.function.Predicate;

import de.adesso.expressivetesting.system.domain.Creditor;
import guidelines.create_testing_dsl.assertions.BaseAssertions;
import guidelines.create_testing_dsl.assertions.Condition;

public final class CreditorAssertions extends BaseAssertions<CreditorAssertions> {
    
    /*
     * Named entry-point
     */
    public static CreditorAssertions assertCreditor(Creditor creditor) {
        return new CreditorAssertions(creditor);
    }

    @SuppressWarnings("unused")
    private final Creditor creditor;

    private CreditorAssertions(Creditor creditor) {
        this.creditor = creditor;
        this.description = "creditor";
    }
    
    // Assertion methods go here..
    
    
    
    
    /*
     * NOTE: The predicates defined here may cause naming conflicts when being used as static imports.
     *       If so, a more unique naming could be used, e.g. 'creditorHasName', or 'creditorWithName'.
     *       
     * NOTE: Also, other prepositions may be used, e.g. 'is...', 'with...' (as aliases).
     */
    public static final class Predicates {
        
        public static Predicate<Creditor> hasName(String expectedName) {
            return c -> expectedName.equals(c.getName());
        }
        
        public static Predicate<Creditor> hasReferenceId(String expectedReferenceId) {
            return c -> expectedReferenceId.equals(c.getReferenceId());
        }
    }
    
    
    
    
    /*
     * NOTE: The conditions defined here may cause naming conflicts when being used as static imports.
     *       If so, a more unique naming could be used, e.g. 'creditorHasName', or 'creditorWithName'.
     *       
     * NOTE: Also, other prepositions may be used, e.g. 'is...', 'with...' (as aliases).
     */
    public static final class Conditions {
        
        public static Condition<Creditor> hasName(String expectedName) {
            return 
                new Condition<Creditor>(
                    c -> expectedName.equals(c.getName()), 
                    () -> ":name {equals}", 
                    c -> " expected: <" + expectedName + "> but was: <" + c.getName() + ">"
                );
        }
        
        public static Condition<Creditor> hasReferenceId(String expectedReferenceId) {
            return 
                new Condition<Creditor>(
                    c -> expectedReferenceId.equals(c.getReferenceId()), 
                    () -> ":referenceId {equals}", 
                    c -> " expected: <" + expectedReferenceId + "> but was: <" + c.getReferenceId() + ">"
                );
        }
    }
    
}
