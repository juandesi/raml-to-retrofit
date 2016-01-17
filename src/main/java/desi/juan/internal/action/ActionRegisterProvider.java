/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package desi.juan.internal.action;

import java.util.HashMap;
import java.util.Map;

import org.raml.model.Action;
import org.raml.model.ActionType;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.HEAD;
import retrofit.http.PATCH;
import retrofit.http.POST;
import retrofit.http.PUT;

public class ActionRegisterProvider
{
    private static final Map<ActionType, Class> registers;

    static {
        registers = new HashMap<>();
        registers.put(ActionType.GET, GET.class);
        registers.put(ActionType.POST, POST.class);
        registers.put(ActionType.DELETE, DELETE.class);
        registers.put(ActionType.HEAD, HEAD.class);
        registers.put(ActionType.PATCH, PATCH.class);
        registers.put(ActionType.PUT, PUT.class);
    }

    public static Class get(Action action)
    {
        return registers.get(action.getType());
    }
}
