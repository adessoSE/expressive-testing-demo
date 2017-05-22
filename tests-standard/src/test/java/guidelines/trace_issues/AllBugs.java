package guidelines.trace_issues;

import org.junit.experimental.categories.Categories;
import org.junit.experimental.categories.Categories.IncludeCategory;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

import guidelines.trace_issues.categories.Bug;

@RunWith(Categories.class)
@IncludeCategory(Bug.class)
// NOTE: We have to explicitly specify the test classes / suites to inspect for matching categories:
@SuiteClasses({AccountingServiceTestUsingCategories.class})
public class AllBugs {

}
