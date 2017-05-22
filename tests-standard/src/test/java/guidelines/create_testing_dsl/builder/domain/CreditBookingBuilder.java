package guidelines.create_testing_dsl.builder.domain;

import static guidelines.create_testing_dsl.builder.domain.CreditorBuilder.anyCreditor;

import java.math.BigDecimal;

import de.adesso.expressivetesting.system.domain.CreditBooking;
import de.adesso.expressivetesting.system.domain.Creditor;

public final class CreditBookingBuilder {
    
    public static CreditBookingBuilder creditBooking() {
        return new CreditBookingBuilder();
    }
    
    public static CreditBookingBuilder creditBooking(CreditBooking template) {
        return new CreditBookingBuilder(template);
    }
    
    public static CreditBooking anyCreditBooking() {
        /*
         * NOTE: The idea is to construct an object with a minimal information set, that satisfies all constraints and
         *       invariants imposed by the object's class.
         * NOTE: The state of this object must not be used for assertions.
         */
        return 
            creditBooking()
                .amount(new BigDecimal("0.01"))
                .reason("Test credit booking")
                .creditor(anyCreditor())
            .build();
    }

    private BigDecimal amount;
    private String reason;
    private Creditor creditor;
    
    private CreditBookingBuilder() {}
    
    private CreditBookingBuilder(CreditBooking template) {
        this.amount = template.getAmount();
        this.reason = template.getReason();
        this.creditor = template.getCreditor();
    }
    
    public CreditBookingBuilder amount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }
    
    public CreditBookingBuilder reason(String reason) {
        this.reason = reason;
        return this;
    }
    
    public CreditBookingBuilder creditor(Creditor creditor) {
        this.creditor = creditor;
        return this;
    }
    
    public CreditBooking build() {
        return new CreditBooking(amount, creditor, reason);
    }
}
