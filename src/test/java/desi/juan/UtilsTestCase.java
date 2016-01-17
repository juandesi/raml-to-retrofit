/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package desi.juan;

import static desi.juan.util.MethodGeneratorUtils.uriToCammelCase;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class UtilsTestCase
{

    @Test
    public void testUriToCamel()
    {
        String uri ="/friends/tweets/{number}";
        String result = uriToCammelCase(uri);
        assertThat(result, is("friendsTweetsNumber"));

        uri = "/friends/name{lastName}";
        result = uriToCammelCase(uri);
        assertThat(result, is("friendsNameLastName"));

        uri = "/friends/name{lastName}/tweet/{number}" ;
        result = uriToCammelCase(uri);
        assertThat(result, is("friendsNameLastNameTweetNumber"));

        uri = "/all_friends/name{lastName}/tweet/{number}" ;
        result = uriToCammelCase(uri);
        assertThat(result, is("allFriendsNameLastNameTweetNumber"));

        uri = "/all_friends/name{last_name}/a_tweet/{a_number}" ;
        result = uriToCammelCase(uri);
        assertThat(result, is("allFriendsNameLastNameATweetANumber"));

    }
}
