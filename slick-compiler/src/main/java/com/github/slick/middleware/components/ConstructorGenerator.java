package com.github.slick.middleware.components;

import com.github.slick.middleware.ContainerClass;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;

import java.util.Set;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2017-03-29
 */

interface ConstructorGenerator {
    MethodSpec generate(ContainerClass container, Set<ClassName> middleware);
}
