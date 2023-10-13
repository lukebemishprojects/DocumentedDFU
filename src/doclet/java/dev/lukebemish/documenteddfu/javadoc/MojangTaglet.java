package dev.lukebemish.documenteddfu.javadoc;

import com.sun.source.doctree.*;
import com.sun.source.util.SimpleDocTreeVisitor;
import jdk.javadoc.doclet.Taglet;

import javax.lang.model.element.Element;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MojangTaglet implements Taglet {
    @Override
    public Set<Location> getAllowedLocations() {
        return Set.of(
            Location.MODULE,
            Location.TYPE,
            Location.PACKAGE,
            Location.FIELD,
            Location.CONSTRUCTOR,
            Location.METHOD,
            Location.OVERVIEW
        );
    }

    @Override
    public boolean isInlineTag() {
        return false;
    }

    @Override
    public String getName() {
        return "mojangDocs";
    }

    @Override
    public String toString(List<? extends DocTree> tags, Element element) {
        return "<dt>Mojang-Added Docs:</dt><dd>"+tags.stream().map(docTree ->
            "<p>" + flatten(docTree) + "</p>"
        ).collect(Collectors.joining())+"</dd>";
    }

    private static String flatten(DocTree tree) {
        return new SimpleDocTreeVisitor<String, Void>() {
            @Override
            public String visitUnknownBlockTag(UnknownBlockTagTree node, Void p) {
                return node.getContent().stream().map(tree -> tree.accept(this, null)).collect(Collectors.joining());
            }

            @Override
            public String visitUnknownInlineTag(UnknownInlineTagTree node, Void p) {
                return node.getContent().stream().map(tree -> tree.accept(this, null)).collect(Collectors.joining());
            }

            @Override
            public String visitText(TextTree node, Void p) {
                return node.getBody().replace("\n", "<br>");
            }

            @Override
            public String visitEntity(EntityTree node, Void unused) {
                return '&'+node.getName().toString()+';';
            }

            @Override
            protected String defaultAction(DocTree node, Void p) {
                return "";
            }
        }.visit(tree, null);
    }
}
