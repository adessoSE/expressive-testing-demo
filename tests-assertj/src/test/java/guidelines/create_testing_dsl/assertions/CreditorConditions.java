package guidelines.create_testing_dsl.assertions;

import org.assertj.core.api.Condition;

import de.adesso.expressivetesting.system.domain.Creditor;

public final class CreditorConditions {

    public static Condition<Creditor> name(String expectedName) {
        return new Condition<>(c -> expectedName.equals(c.getName()), "name {equals:'%s'}", expectedName);
    }
    
    public static Condition<Creditor> referenceId(String expectedReferenceId) {
        return 
            new Condition<>(
                c -> expectedReferenceId.equals(c.getReferenceId()), 
                "referenceId {equals:'%s'}", expectedReferenceId
            );
    }
}
