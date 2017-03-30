package com.github.slick.middleware.components;

import com.github.slick.Utils;
import com.github.slick.middleware.ContainerClass;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;

import java.util.Set;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.VariableElement;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2017-03-29
 */

public class ConstructorGeneratorImpl implements ConstructorGenerator {
    @Override
    public MethodSpec generate(ContainerClass container, Set<ClassName> middleware) {
        final MethodSpec.Builder builder = MethodSpec.constructorBuilder();
        builder.addModifiers(Modifier.PUBLIC);
        for (VariableElement variableElement : container.getArgs()) {
            final Set<Modifier> modifiers = variableElement.getModifiers();
            builder.addParameter(TypeName.get(variableElement.asType()),
                    variableElement.getSimpleName().toString(), modifiers
                            .toArray(new Modifier[modifiers.size()]));
        }
        builder.addStatement("super($L)", container.getArgsVarNames());

        for (ClassName className : middleware) {
            final String name = Utils.deCapitalize(className.simpleName());
            builder.addParameter(className, name);
            builder.addStatement("this.$L = $L", name, name);
        }
        return builder.build();
    }
}
