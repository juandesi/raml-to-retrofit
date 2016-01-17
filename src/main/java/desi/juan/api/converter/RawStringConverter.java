/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package desi.juan.api.converter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;

import retrofit.converter.ConversionException;
import retrofit.converter.Converter;
import retrofit.mime.TypedInput;
import retrofit.mime.TypedOutput;

public class RawStringConverter implements Converter
{

        @Override
        public String fromBody(TypedInput body, Type type) throws ConversionException
        {
            try
            {
                Reader inputStreamReader = new InputStreamReader(body.in());
                BufferedReader reader = new BufferedReader(inputStreamReader);
                return reader.readLine();
            }
            catch (IOException e)
            {
                throw new ConversionException(e);
            }
        }

        @Override
        public TypedOutput toBody(Object object) {
            return null;
        }
}
