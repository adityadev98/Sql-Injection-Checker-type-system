//package org.example.sqlchecker;
//
//import org.checkerframework.framework.test.CheckerFrameworkTest;
//import org.junit.runners.Parameterized.Parameters;
//import org.junit.Test;
//import java.io.File;
//import java.util.List;
//
//public class SQLInjectionCheckerTest extends CheckerFrameworkTest {
//
//    public SQLInjectionCheckerTest() {
//        super(org.example.sqlchecker.checker.SQLInjectionChecker.class,
//                "sql-injection-checker",
//                "-Anomsgtext");
////        super();
//    }
//
//    @Parameters
//    public static String[] getTestDirs() {
//        return new String[]{"sql-injection-checker"};
//    }
//
//    @Test
//    public void testTruePositives() {
//        test("cases/TruePositiveCases");
//    }
//
//    @Test
//    public void testTrueNegatives() {
//        test("cases/TrueNegativeCases");
//    }
//}
package org.example.sqlchecker;

import org.checkerframework.framework.test.CheckerFrameworkPerDirectoryTest;
import org.junit.runners.Parameterized.Parameters;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.io.File;
import java.util.List;

@RunWith(Parameterized.class)
public class SQLInjectionCheckerTest extends CheckerFrameworkPerDirectoryTest {

    public SQLInjectionCheckerTest(List<File> testFiles) {
        super(
                testFiles,
                org.example.sqlchecker.checker.SQLInjectionChecker.class,
                "cases",
                "-Anomsgtext",
                "-Astubs=stubs/",
                "-nowarn"
        );
    }

    @Parameters
    public static String[] getTestDirs() {
        return new String[]{"cases"};
    }

    // The test methods are now handled by CheckerFrameworkPerDirectoryTest
    // You don't need to define them explicitly
}