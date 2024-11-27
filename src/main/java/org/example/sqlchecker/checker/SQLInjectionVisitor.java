package org.example.sqlchecker.checker;

import org.checkerframework.common.basetype.BaseTypeChecker;
import org.checkerframework.common.basetype.BaseTypeVisitor;
import org.checkerframework.framework.type.AnnotatedTypeMirror;
import com.sun.source.tree.*;
import org.example.sqlchecker.annotation.Trusted;
import org.example.sqlchecker.annotation.Untrusted;

public class SQLInjectionVisitor extends BaseTypeVisitor<SQLInjectionAnnotatedTypeFactory> {

    public SQLInjectionVisitor(BaseTypeChecker checker) {
        super(checker);
    }

    @Override
    protected SQLInjectionAnnotatedTypeFactory createTypeFactory() {
        return new SQLInjectionAnnotatedTypeFactory(checker);
    }

    @Override
    public Void visitMethodInvocation(MethodInvocationTree tree, Void p) {
        // Check method invocations for potential SQL injection
        if (isSQLMethod(tree)) {
            ExpressionTree arg = tree.getArguments().get(0);
            AnnotatedTypeMirror argType = atypeFactory.getAnnotatedType(arg);

            if (argType.hasAnnotation(Untrusted.class)) {
                checker.reportError(tree, "sql.injection.untrusted.query");
            }
        }
        return super.visitMethodInvocation(tree, p);
    }

    private boolean isSQLMethod(MethodInvocationTree tree) {
        String methodName = tree.getMethodSelect().toString();
        return methodName.contains("executeQuery") ||
                methodName.contains("executeUpdate") ||
                methodName.contains("execute");
    }
}
