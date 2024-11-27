package org.example.sqlchecker.checker;

import org.checkerframework.common.basetype.BaseAnnotatedTypeFactory;
import org.checkerframework.common.basetype.BaseTypeChecker;
import org.checkerframework.framework.type.treeannotator.ListTreeAnnotator;
import org.checkerframework.framework.type.treeannotator.TreeAnnotator;
import org.example.sqlchecker.annotation.Trusted;
import org.example.sqlchecker.annotation.Untrusted;
import org.example.sqlchecker.checker.SQLInjectionTreeAnnotator;
import org.checkerframework.framework.qual.TypeUseLocation;
import org.checkerframework.framework.util.defaults.QualifierDefaults;
import org.checkerframework.framework.type.AnnotatedTypeMirror;
//import org.checkerframework.framework.qual.AnnotationMirror;
import org.checkerframework.javacutil.AnnotationUtils;
import org.checkerframework.javacutil.AnnotationBuilder; // Use this instead of AnnotationBuilder
import javax.lang.model.element.AnnotationMirror;
import java.util.LinkedHashSet;
import java.util.Set;

public class SQLInjectionAnnotatedTypeFactory extends BaseAnnotatedTypeFactory {

    public SQLInjectionAnnotatedTypeFactory(BaseTypeChecker checker) {
        super(checker, true);  // Add 'true' parameter here to enable initialization
//        this.postInit();
    }

    @Override
    protected Set<Class<? extends java.lang.annotation.Annotation>> createSupportedTypeQualifiers() {
        Set<Class<? extends java.lang.annotation.Annotation>> qualifiers =
                new LinkedHashSet<>();
        qualifiers.add(Trusted.class);
        qualifiers.add(Untrusted.class);
        return qualifiers;
    }

    @Override
    protected TreeAnnotator createTreeAnnotator() {
        return new ListTreeAnnotator(
                super.createTreeAnnotator(),
                new SQLInjectionTreeAnnotator(this)
        );
    }
//    @Override
//    protected void addCheckedCodeDefaults(QualifierDefaults defaults) {
//        AnnotationMirror trusted = getAnnotationUtils().fromClass(elements, Trusted.class);
//        defaults.addCheckedCodeDefault(trusted, TypeUseLocation.OTHERWISE);
//    }
    @Override
    protected void addCheckedCodeDefaults(QualifierDefaults defaults) {
        defaults.addCheckedCodeDefault(AnnotationBuilder.fromClass(elements, Trusted.class),
                TypeUseLocation.OTHERWISE);
    }
}