//package org.example.sqlchecker.checker;
//
//import org.checkerframework.common.basetype.BaseTypeChecker;
//import org.checkerframework.framework.source.SupportedOptions;
//import org.checkerframework.framework.source.SupportedAnnotationTypes;
//
//@SupportedAnnotationTypes({
//        "org.example.sqlchecker.annotation.Trusted",
//        "org.example.sqlchecker.annotation.Untrusted"
//})
//@SupportedOptions({"debugSpew"})
//public class SQLInjectionChecker extends BaseTypeChecker {
//    @Override
//    protected SQLInjectionVisitor createSourceVisitor() {
//        return new SQLInjectionVisitor(this);
//    }
//}
package org.example.sqlchecker.checker;

import com.google.auto.service.AutoService;
import org.checkerframework.common.basetype.BaseTypeChecker;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedOptions;
@AutoService(javax.annotation.processing.Processor.class)
//@SupportedAnnotationTypes({
//        "org.example.sqlchecker.annotation.Trusted",
//        "org.example.sqlchecker.annotation.Untrusted"
//})
@SupportedOptions({"debugSpew"})
public class SQLInjectionChecker extends BaseTypeChecker {
    @Override
    protected SQLInjectionVisitor createSourceVisitor() {
        return new SQLInjectionVisitor(this);
    }
}