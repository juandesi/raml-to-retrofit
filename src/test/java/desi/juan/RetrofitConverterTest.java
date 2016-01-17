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
        RetrofitInterface testInterface =
                new RetrofitInterface("TwitterServiceInterface",
                                        "/Users/juandesi/Workspace/raml-to-retrofit/src/test/resources/twitter/api.raml");
        TypeSpec resultInterface = testInterface.generate();
        assertThat(resultInterface, is(not(nullValue())));
    }


}
