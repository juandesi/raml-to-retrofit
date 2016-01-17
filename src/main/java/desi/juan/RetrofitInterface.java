/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package desi.juan;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.util.Map;

import org.raml.model.Action;
import org.raml.model.ActionType;
import org.raml.model.Raml;
import org.raml.model.Resource;
import org.raml.parser.visitor.RamlDocumentBuilder;

public class RetrofitInterface implements RetrofitCodeGenerable<JavaFile>
{

    private final TypeSpec.Builder interfaceBuilder;
    private final String ramlResource;

    public RetrofitInterface(String interfaceName, String ramlResource) {
        this.ramlResource = ramlResource;
        interfaceBuilder = TypeSpec.interfaceBuilder(interfaceName);
    }

    public JavaFile generate()
    {
        Raml raml = new RamlDocumentBuilder().build(ramlResource);
        registerResources(raml.getResources());
        return JavaFile.builder("com.example.helloworld", interfaceBuilder.build()).build();
    }

    private void registerResources(Map<String, Resource> resources)
    {
        resources.keySet().stream().forEach(resourceName -> {
            Resource resource = resources.get(resourceName);
            Map<ActionType, Action> actions = resource.getActions();
            actions.keySet().stream().forEach(actionType -> registerAction(actions.get(actionType)));
            registerResources(resource.getResources());
        });
    }

    private void registerAction(Action action)
    {
        MethodSpec method = new RetrofitMethodSignature(action).generate();
        interfaceBuilder.addMethod(method);
    }

}
