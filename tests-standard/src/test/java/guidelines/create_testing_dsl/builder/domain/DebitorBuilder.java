package guidelines.create_testing_dsl.builder.domain;

import java.math.BigInteger;

import de.adesso.expressivetesting.system.domain.Debitor;

public final class DebitorBuilder {
    
    public static DebitorBuilder debitor() {
        return new DebitorBuilder();
    }
    
    public static Debitor anyDebitor() {
        /*
         * NOTE: The idea is to construct an object with a minimal information set, that satisfies all constraints and
         *       invariants imposed by the object's class.
         * NOTE: The state of this object must not be used for assertions.
         */
        return
            debitor()
                .name("Test Debitor")
                .referenceId(new BigInteger("Test Debitor".getBytes()).toString())
            .build();
    }

    private String name;
    private String referenceId;
    
    public DebitorBuilder name(String name) {
        this.name = name;
        return this;
    }
    
    public DebitorBuilder referenceId(String referenceId) {
        this.referenceId = referenceId;
        return this;
    }
    
    public Debitor build() {
        Debitor debitor = new Debitor();
        if (name != null) {
            debitor.setName(name);
        }
        if (referenceId != null) {
            debitor.setReferenceId(referenceId);
        }
        
        return debitor;
    }
}
