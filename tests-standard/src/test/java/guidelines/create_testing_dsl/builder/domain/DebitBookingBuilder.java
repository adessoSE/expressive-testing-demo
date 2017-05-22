package guidelines.create_testing_dsl.builder.domain;

import static guidelines.create_testing_dsl.builder.domain.DebitorBuilder.anyDebitor;

import java.math.BigDecimal;

import de.adesso.expressivetesting.system.domain.DebitBooking;
import de.adesso.expressivetesting.system.domain.Debitor;

public final class DebitBookingBuilder {
    
    public static DebitBookingBuilder debitBooking() {
        return new DebitBookingBuilder();
    }
    
    public static DebitBookingBuilder debitBooking(DebitBooking template) {
        return new DebitBookingBuilder(template);
    }
    
    public static DebitBooking anyDebitBooking() {
        /*
         * NOTE: The idea is to construct an object with a minimal information set, that satisfies all constraints and
         *       invariants imposed by the object's class.
         * NOTE: The state of this object must not be used for assertions.
         */
        return
            debitBooking()
                .amount(new BigDecimal("0.01"))
                .reason("Test debit booking")
                .debitor(anyDebitor())
            .build();
    }

    private BigDecimal amount;
    private String reason;
    private Debitor debitor;
    
    private DebitBookingBuilder() {}
    
    private DebitBookingBuilder(DebitBooking template) {
        this.amount = template.getAmount();
        this.reason = template.getReason();
        this.debitor = template.getDebitor();
    }
    
    public DebitBookingBuilder amount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }
    
    public DebitBookingBuilder reason(String reason) {
        this.reason = reason;
        return this;
    }
    
    public DebitBookingBuilder debitor(Debitor debitor) {
        this.debitor = debitor;
        return this;
    }
    
    public DebitBooking build() {
        return new DebitBooking(amount, debitor, reason);
    }
}
