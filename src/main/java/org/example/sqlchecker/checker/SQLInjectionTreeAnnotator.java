package org.example.sqlchecker.checker;

import com.sun.source.tree.LiteralTree;
import com.sun.source.tree.Tree;
import com.sun.source.tree.MethodInvocationTree;
import org.checkerframework.framework.type.AnnotatedTypeFactory;
import org.checkerframework.framework.type.AnnotatedTypeMirror;
import org.checkerframework.framework.type.treeannotator.TreeAnnotator;
import org.example.sqlchecker.annotation.Trusted;

public class SQLInjectionTreeAnnotator extends TreeAnnotator {

//    public SQLInjectionTreeAnnotator(AnnotatedTypeFactory atypeFactory) {
//        super(atypeFactory);
//    }
    private final SQLInjectionAnnotatedTypeFactory typeFactory;

    public SQLInjectionTreeAnnotator(SQLInjectionAnnotatedTypeFactory typeFactory) {
        super(typeFactory);
        this.typeFactory = typeFactory;
    }

    @Override
    public Void visitLiteral(LiteralTree tree, AnnotatedTypeMirror type) {
        // String literals are trusted by default
        if (tree.getKind() == Tree.Kind.STRING_LITERAL) {
            type.addAnnotation(Trusted.class);
        }
        return super.visitLiteral(tree, type);
    }
//    @Override
//    public Void visitLiteral(LiteralTree tree, AnnotatedTypeMirror type) {
//        if (tree.getKind() == Tree.Kind.STRING_LITERAL) {
//            type.addAnnotation(typeFactory.getTrustedAnnotation());
//        }
//        return super.visitLiteral(tree, type);
//}

    @Override
    public Void visitMethodInvocation(MethodInvocationTree node, AnnotatedTypeMirror type) {
        return super.visitMethodInvocation(node, type);
    }

}