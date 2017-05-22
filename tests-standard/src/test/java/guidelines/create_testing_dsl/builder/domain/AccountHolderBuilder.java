package guidelines.create_testing_dsl.builder.domain;

import java.math.BigInteger;

import de.adesso.expressivetesting.system.domain.AccountHolder;

public final class AccountHolderBuilder {
    
    public static AccountHolderBuilder accountHolder() {
        return new AccountHolderBuilder();
    }
    
    public static AccountHolder anyAccountHolder() {
        /*
         * NOTE: The idea is to construct an object with a minimal information set, that satisfies all constraints and
         *       invariants imposed by the object's class.
         * NOTE: The state of this object must not be used for assertions.
         */
        return
            accountHolder()
                .firstName("John")
                .lastName("Doe")
                .customerNumber(new BigInteger("John Doe".getBytes()).toString()) 
            .build();
    }

    private String customerNumber;
    private String firstName;
    private String lastName;
    
    public AccountHolderBuilder customerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
        return this;
    }

    public AccountHolderBuilder firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public AccountHolderBuilder lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public AccountHolder build() {
        AccountHolder accountHolder = new AccountHolder();
        if (customerNumber != null) {
            accountHolder.setCustomerNumber(customerNumber);
        }
        if (firstName != null) {
            accountHolder.setFirstName(firstName);
        }
        if (lastName != null) {
            accountHolder.setLastName(lastName);
        }
        
        return accountHolder;
    }
}
