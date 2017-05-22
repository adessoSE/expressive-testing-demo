package guidelines.create_testing_dsl.builder.domain;

import static guidelines.create_testing_dsl.builder.domain.AccountHolderBuilder.anyAccountHolder;
import static guidelines.create_testing_dsl.builder.domain.CreditBookingBuilder.anyCreditBooking;
import static guidelines.create_testing_dsl.builder.domain.CreditBookingBuilder.creditBooking;
import static guidelines.create_testing_dsl.builder.domain.DebitBookingBuilder.anyDebitBooking;
import static guidelines.create_testing_dsl.builder.domain.DebitBookingBuilder.debitBooking;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import de.adesso.expressivetesting.system.domain.Account;
import de.adesso.expressivetesting.system.domain.AccountHolder;
import de.adesso.expressivetesting.system.domain.Booking;

public final class AccountBuilder {
    
    public static AccountBuilder account() {
        return new AccountBuilder();
    }
    
    public static AccountBuilder account(Account template) {
        return new AccountBuilder(template);
    }
    
    public static Account anyAccount() {
        /*
         * NOTE: The idea is to construct an object with a minimal information set, that satisfies all constraints and
         *       invariants imposed by the object's class.
         * NOTE: The state of this object must not be used for assertions.
         */
        return 
            account()
                .iban("DE89 3704 0044 0532 0130 00")
                .holder(anyAccountHolder())
            .build();
    }
    
    private String iban;
    private AccountHolder holder;
    private BigDecimal overdraftLimit;
    private List<Booking> bookings = new ArrayList<>();
    private BigDecimal balance;
    private BalanceBookingSpecification balanceBookingSpecification;
    
    private AccountBuilder() {}
    
    private AccountBuilder(Account template) {
        this.iban = template.getIban();
        this.holder = template.getHolder();
        this.overdraftLimit = template.getOverdraftLimit();
        this.bookings.addAll(template.getBookings());
    }
    
    public AccountBuilder iban(String iban) {
        this.iban = iban;
        return this;
    }
    
    public AccountBuilder holder(AccountHolder holder) {
        this.holder = holder;
        return this;
    }
    
    public AccountBuilder overdraftLimit(BigDecimal overdraftLimit) {
        this.overdraftLimit = overdraftLimit;
        return this;
    }
    
    public AccountBuilder bookings(Booking... bookings) {
        if (balance != null) {
            throw new IllegalStateException("Bookings cannot be specified: a balance has already been set");
        }
        
        this.bookings.clear();
        for (Booking booking: bookings) {
            this.bookings.add(booking);
        }
        return this;
    }
    
    public AccountBuilder balance(BigDecimal balance, BalanceBookingSpecification balanceBookingSpecification) {
        if (! bookings.isEmpty()) {
            throw new IllegalStateException("Balance cannot be specified: bookings have already been added");
        }
        
        this.balance = balance;
        this.balanceBookingSpecification = balanceBookingSpecification;
        return this;
    }

    public Account build() {
        Account account = new Account();
        if (iban != null) {
            account.setIban(iban);
        }
        if (holder != null) {
            account.setHolder(holder);
        }
        if (overdraftLimit != null) {
            account.setOverdraftLimit(overdraftLimit);
        }
        if (! bookings.isEmpty()) {
            for (Booking booking: bookings) {
                account.addBooking(booking);
            }
        } else if (balance != null) {
            BigDecimal oneCent = new BigDecimal("0.01");
            BigDecimal numberOfBookings = new BigDecimal(balanceBookingSpecification.numberOfBookings);
            if (oneCent.multiply(numberOfBookings).compareTo(balance.abs()) > 0) {
                throw 
                    new IllegalStateException(
                        "Balance of " + balance + " cannot be established with " + numberOfBookings + " bookings"
                    );
            }
            
            BigDecimal finalBookingAmount = balance.abs();
            for (int i = 0; i < numberOfBookings.intValue() - 1; i++) {
                if (balance.signum() < 0) {
                    account.addBooking(creditBooking(anyCreditBooking()).amount(oneCent).build());
                } else {
                    account.addBooking(debitBooking(anyDebitBooking()).amount(oneCent).build());
                }
                finalBookingAmount = finalBookingAmount.subtract(oneCent);
            }
            if (balance.signum() < 0) {
                account.addBooking(creditBooking(anyCreditBooking()).amount(finalBookingAmount).build());
            } else {
                account.addBooking(debitBooking(anyDebitBooking()).amount(finalBookingAmount).build());
            }
        }
        
        return account;
    }
    
    
    
    
    /*
     * NOTE: This is just syntactic sugar for specifying how an account balance should be created.
     */
    public static final class BalanceBookingSpecification {
        
        public static BalanceBookingSpecification resultingFrom(int numberOfBookings) {
            return new BalanceBookingSpecification(numberOfBookings);
        }
        
        private final int numberOfBookings;

        private BalanceBookingSpecification(int numberOfBookings) {
            if (numberOfBookings <= 0) {
                throw 
                    new IllegalArgumentException(
                        "Number of bookings must be greater than 0, but was " + numberOfBookings
                    );
            }
            this.numberOfBookings = numberOfBookings;
        }
        
        public BalanceBookingSpecification booking() {
            return this;
        }
        
        public BalanceBookingSpecification bookings() {
            return this;
        }
        
    }
    
}
