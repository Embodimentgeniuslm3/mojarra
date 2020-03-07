/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0, which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the
 * Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
 * version 2 with the GNU Classpath Exception, which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 */

package com.sun.faces.application.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

import com.sun.faces.util.Util;

import jakarta.persistence.PersistenceContext;

/**
 * <code>Scanner</code> implementation responsible for {@link PersistenceContext} annotations.
 */
class PersistenceContextScanner implements Scanner {

    /**
     * Get the annotation we handle.
     *
     * @return the annotation we handle.
     */
    @Override
    public Class<? extends Annotation> getAnnotation() {
        return PersistenceContext.class;
    }

    /**
     * Scan the specified class for the given annotation.
     *
     * @param clazz the class.
     * @return the runtime annotation handler.
     * @todo Make sure we get all the fields, handle method and class based injection, handle PersistenceContexts.
     */
    @Override
    public RuntimeAnnotationHandler scan(Class<?> clazz) {
        Util.notNull("clazz", clazz);
        PersistenceContextHandler handler = null;

        ArrayList<PersistenceContext> fieldAnnotations = new ArrayList<>();
        ArrayList<Field> fields = new ArrayList<>();

        for (Field field : clazz.getDeclaredFields()) {
            PersistenceContext fieldAnnotation = field.getAnnotation(PersistenceContext.class);
            if (fieldAnnotation != null) {
                fieldAnnotations.add(fieldAnnotation);
                fields.add(field);
            }
        }

        ArrayList<PersistenceContext> methodAnnotations = new ArrayList<>();
        ArrayList<Method> methods = new ArrayList<>();
        for (Method method : clazz.getDeclaredMethods()) {
            PersistenceContext methodAnnotation = method.getAnnotation(PersistenceContext.class);
            if (methodAnnotation != null) {
                methodAnnotations.add(methodAnnotation);
                methods.add(method);
            }
        }

        if (!fieldAnnotations.isEmpty() || !methodAnnotations.isEmpty()) {
            handler = new PersistenceContextHandler(methods.toArray(new Method[0]), methodAnnotations.toArray(new PersistenceContext[0]),
                    fields.toArray(new Field[0]), fieldAnnotations.toArray(new PersistenceContext[0]));
        }
        return handler;
    }
}
