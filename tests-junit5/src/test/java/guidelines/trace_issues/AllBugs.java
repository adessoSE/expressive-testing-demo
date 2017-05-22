package guidelines.trace_issues;

import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class) // NOTE: This is just needed for running JUnit 5 tests in Eclipse
/*
 * NOTE: Say "good-by" to @Suite and @SuiteClasses
 * NOTE: Per default it will only include test classes whose names match the pattern ^.*Tests?$
 * NOTE: There are more filtering options available, e.g.
 *       - @IncludeClassNamePatterns, @ExcludeClassNamePatterns
 *       - @ExcludePackages
 *       - @SelectClasses
 */
@SelectPackages("guidelines")
@IncludeTags("bug:ADD-666") // NOTE: Currently it is not possible to specify patterns for tags.
public class AllBugs {

}
