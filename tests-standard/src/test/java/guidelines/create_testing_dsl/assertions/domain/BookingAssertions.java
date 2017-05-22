package guidelines.create_testing_dsl.assertions.domain;

import static guidelines.create_testing_dsl.assertions.domain.BookingAssertions.CreditBookingAssertions.assertCreditBooking;
import static java.util.Comparator.comparing;
import static java.util.Comparator.naturalOrder;
import static java.util.Comparator.nullsFirst;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Date;
import java.util.function.Predicate;

import de.adesso.expressivetesting.system.domain.Booking;
import de.adesso.expressivetesting.system.domain.BookingType;
import de.adesso.expressivetesting.system.domain.CreditBooking;
import de.adesso.expressivetesting.system.domain.Creditor;
import guidelines.create_testing_dsl.assertions.BaseAssertions;
import guidelines.create_testing_dsl.assertions.Condition;

/*
 * NOTE: This is a polymorphic assertion class
 */
public class BookingAssertions<A extends BookingAssertions<A>> extends BaseAssertions<A> {
    
    /*
     * Named entry-point
     * 
     * NOTE: See Assertions for generic entry-points
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static <A extends BookingAssertions<A>> A assertBooking(Booking booking) {
        return (A) new BookingAssertions(booking);
    }
    

    private final Booking booking;
    
    private BookingAssertions(Booking booking) {
        this.booking = booking;
        this.description = "booking";
    }
    
    /*
     * NOTE: We may pull this up into a superclass.
     */
    public A exists() {
        assertNotNull(description + " {exists}", booking);
        return self();
    }
    
    public A hasType(BookingType expectedBookingType) {
        assertEquals(description + ":type {equals}", expectedBookingType, booking.getType());
        return self();
    }
    
    public A hasAmount(BigDecimal expectedAmount) {
        assertEquals(description + ":amount {equals}", expectedAmount, booking.getAmount());
        return self();
    }
    
    public A hasReason(String expectedReason) {
        assertEquals(description + ":reason {equals}", expectedReason, booking.getReason());
        return self();
    }
    
    public A hasDate() {
        assertNotNull(description + ":date {exists}", booking.getDate());
        return self();
    }
    
    public A hasDateWithin(Date startDate, Date endDate) {
        hasDate();
        
        Date date = booking.getDate();
        assertTrue(
            description + ":date {within[" + startDate.toInstant() + ";" + endDate.toInstant() + "]} - but was: " + date.toInstant(), 
            (date.after(startDate) || date.equals(startDate)) && (date.before(endDate) || date.equals(endDate))
        );
        return self();
    }
    
    /*
     * NOTE: We are checking further assertions on the polymorphic sub-type assertion class for credit bookings.
     *       There we still have access to all assertions defined in this super-type assertion class.
     */
    public CreditBookingAssertions isCreditBooking() {
        assertSame(description + " {isA:CreditBooking}", CreditBooking.class, booking.getClass());
        return assertCreditBooking((CreditBooking) booking).describedAs(description);
    }
    
    @SuppressWarnings("unchecked")
    private A self() {
        return (A) this;
    }
    
    
    
    
    public static final class CreditBookingAssertions extends BookingAssertions<CreditBookingAssertions> {
        
        /*
         * Named entry-point
         * 
         * NOTE: See Assertions for generic entry-points
         */
        public static CreditBookingAssertions assertCreditBooking(CreditBooking booking) {
            return new CreditBookingAssertions(booking);
        }
        
        private final CreditBooking booking;
        
        private CreditBookingAssertions(CreditBooking booking) {
            super(booking);
            this.booking = booking;
        }
        
        public CreditBookingAssertions hasCreditor() {
            assertNotNull(description + ":creditor {exists}", booking.getCreditor());
            return this;
        }
        
        public CreditBookingAssertions hasCreditor(Creditor expectedCreditor) {
            hasCreditor();
            
            assertTrue(
                description + ":creditor {equals}", 
                comparing(Creditor::getName, nullsFirst(naturalOrder()))
                    .thenComparing(Creditor::getReferenceId, nullsFirst(naturalOrder()))
                    .thenComparing(Creditor::getAccount, nullsFirst(naturalOrder()))
                .compare(expectedCreditor, booking.getCreditor()) == 0
            );
            return this;
        }
        
        @SafeVarargs
        public final CreditBookingAssertions creditor(
                                                Predicate<Creditor> predicate, 
                                                Predicate<Creditor>... furtherPredicates
                                             ) {
            hasCreditor();
            
            assertTrue(description + ":creditor", predicate.test(booking.getCreditor()));
            for (Predicate<Creditor> furtherPredicate: furtherPredicates) {
                assertTrue(description + ":creditor", furtherPredicate.test(booking.getCreditor()));
            }
            return this;
        }
        
        @SafeVarargs
        public final CreditBookingAssertions creditor(
                                                Condition<Creditor> condition, 
                                                Condition<Creditor>... furtherConditions
                                             ) {
            hasCreditor();
            
            assertTrue(
                description + ":creditor" + condition.description() + condition.expectation(booking.getCreditor()), 
                condition.eval(booking.getCreditor())
            );
            for (Condition<Creditor> furtherCondition: furtherConditions) {
                assertTrue(
                    description + ":creditor" + furtherCondition.description() + furtherCondition.expectation(booking.getCreditor()), 
                    furtherCondition.eval(booking.getCreditor())
                );
            }
            return this;
        }
        
    }
    
}
