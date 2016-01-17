/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package desi.juan;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import desi.juan.api.Scaffolder;
import desi.juan.internal.RetrofitInterface;
import org.junit.Test;

public class RetrofitConverterTest
{
    @Test
    public void testTwitterInterfaceGeneration() throws IOException
    {
        RetrofitInterface testInterface = new RetrofitInterface("TwitterServiceInterface", "twitter/api.raml");
        TypeSpec resultInterface = testInterface.generate();
        assertThat(resultInterface, is(not(nullValue())));
    }

    @Test
    public void testTwitterInterfaceGeneration2() throws IOException
    {
        final String directory = "/Users/juandesi/Workspace/raml-to-retrofit/target";
        final String thePackage = "org.desi.juan";
        final String interfaceName = "InterfaceName";
        Scaffolder converter = new Scaffolder(interfaceName, thePackage, "twitter/api.raml");
        converter.write(Paths.get(directory));

        Path path = Paths.get(directory, thePackage.replace(".", "/"), interfaceName + ".java");
        assertThat(path.toFile().exists(), is(true));
        assertThat(path.toFile().isDirectory(), is(false));
    }




}
