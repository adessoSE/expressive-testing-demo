package guidelines.create_testing_dsl.assertions;

public abstract class BaseAssertions<A extends BaseAssertions<?>> {

    protected String description;
    
    @SuppressWarnings("unchecked")
    public final A describedAs(String description) {
        this.description = description;
        return (A) this;
    }
    
    @SuppressWarnings("unchecked")
    public final A denotedAs(String description) {
        this.description = description;
        return (A) this;
    }
    
    @SuppressWarnings("unchecked")
    public final A named(String description) {
        this.description = description;
        return (A) this;
    }
    
}
