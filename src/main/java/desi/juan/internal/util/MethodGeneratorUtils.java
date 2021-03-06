/*
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package desi.juan.internal.util;

import javax.lang.model.SourceVersion;

import org.apache.commons.lang.StringUtils;
import org.raml.model.Action;

import com.google.common.base.CaseFormat;

import desi.juan.internal.exception.IllegalNameException;

public class MethodGeneratorUtils {
  
  public static String uriToCammelCase(String uri) {
    uri = uri.replace("{", "/").replace("}", "/");
    StringBuilder result = new StringBuilder();
    for(String word : StringUtils.split(uri, "/")) {
      if(word.contains("_")) {
        word = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, word);
      }
      result.append(StringUtils.capitalize(word));
    }
    return StringUtils.uncapitalize(result.toString());
  }
  
  public static String buildMethodName(Action action) {
    if(action.getDisplayName() != null)
      return action.getDisplayName();
    else {
      String methodName = action.getResource().getUri() +
              StringUtils.capitalize(StringUtils.lowerCase(action.getType().toString()));
      return uriToCammelCase(methodName);
    }
  }
  
  public static String buildParamName(String name) throws IllegalNameException {
    if(!SourceVersion.isName(name)) {
      name = name + "Value";
      if(!SourceVersion.isName(name)) {
        throw new IllegalNameException(
                name + " is not a valid java parameter name, update your RAML file so it can be parsed.");
      }
    }
    return name;
  }
  
  public static String toStringValue(String value) {
    return "\"" + value + "\"";
  }
  
}
