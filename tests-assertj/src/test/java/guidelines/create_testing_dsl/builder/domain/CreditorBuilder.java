package guidelines.create_testing_dsl.builder.domain;

import java.math.BigInteger;

import de.adesso.expressivetesting.system.domain.Creditor;

public final class CreditorBuilder {
    
    public static CreditorBuilder creditor() {
        return new CreditorBuilder(); 
    }
    
    public static Creditor anyCreditor() {
        return
            creditor()
                .name("Test Creditor")
                .referenceId(new BigInteger("Test Creditor".getBytes()).toString())
            .build();
    }

    private String name;
    private String referenceId;
    
    public CreditorBuilder name(String name) {
        this.name = name;
        return this;
    }
    
    public CreditorBuilder referenceId(String referenceId) {
        this.referenceId = referenceId;
        return this;
    }
    
    public Creditor build() {
        Creditor creditor = new Creditor();
        if (name != null) {
            creditor.setName(name);
        }
        if (referenceId != null) {
            creditor.setReferenceId(referenceId);
        }
        
        return creditor;
    }
}
