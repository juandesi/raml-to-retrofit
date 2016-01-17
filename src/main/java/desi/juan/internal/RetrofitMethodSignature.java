/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package desi.juan.internal;

import static desi.juan.internal.util.MethodGeneratorUtils.buildMethodName;
import static desi.juan.internal.util.MethodGeneratorUtils.buildParamName;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.lang.model.element.Modifier;

import desi.juan.internal.action.ActionRegisterProvider;
import org.raml.model.Action;
import org.raml.model.parameter.Header;
import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.Path;
import retrofit.http.Query;

class RetrofitMethodSignature implements RetrofitCodeGenerable<MethodSpec>
{
    private static final String VALUE = "value";
    private final Action action;

    public RetrofitMethodSignature(Action action)
    {
        this.action = action;
    }

    @Override
    public MethodSpec generate()
    {
        Set<ParameterSpec> parameters = new LinkedHashSet<>();
        action.getResource().getResolvedUriParameters().keySet()
                .forEach(uriParam -> parameters.add(buildPathParam(uriParam)));
        action.getQueryParameters().keySet()
                .forEach(queryParam -> parameters.add(buildQueryParam(ParameterizedTypeName.get(String.class), queryParam)));

        if (action.hasBody())
        {
            parameters.add(buildBodyParam());
        }

        MethodSpec.Builder method = MethodSpec.methodBuilder(buildMethodName(action))
                .addParameters(parameters)
                .addAnnotation(buildBaseMethodAnnotation(action))
                .addModifiers(Modifier.ABSTRACT, Modifier.PUBLIC)
                .returns(ParameterizedTypeName.get(Call.class, String.class));

        return method.build();
    }

    private ParameterSpec buildQueryParam(TypeName type, String name)
    {
        name = name.replace(":", "");
        return ParameterSpec.builder(type, buildParamName(name))
                .addAnnotation(AnnotationSpec.builder(Query.class).addMember(VALUE, name).build())
                .build();
    }

    private ParameterSpec buildPathParam(String name)
    {
        return ParameterSpec.builder(ParameterizedTypeName.get(String.class), buildParamName(name))
                .addAnnotation(AnnotationSpec.builder(Path.class).addMember(VALUE, name).build())
                .build();
    }

    private ParameterSpec buildBodyParam()
    {
        return ParameterSpec.builder(ParameterizedTypeName.get(Object.class), "body")
                .addAnnotation(Body.class)
                .build();
    }

    private String buildHeadersArray(Map<String, Header> headers)
    {
        return "";
        //headers.keySet()
        //        .forEach(header -> header);
    }

    private AnnotationSpec buildBaseMethodAnnotation(Action action)
    {
        return AnnotationSpec.builder(ActionRegisterProvider.get(action)).addMember(VALUE, action.getResource().getUri()).build();
    }



}
