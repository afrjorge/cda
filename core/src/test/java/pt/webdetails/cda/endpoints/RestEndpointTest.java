/*!
 * Copyright 2018 Webdetails, a Hitachi Vantara company. All rights reserved.
 *
 * This software was developed by Webdetails and is provided under the terms
 * of the Mozilla Public License, Version 2.0, or any later version. You may not use
 * this file except in compliance with the license. If you need a copy of the license,
 * please go to  http://mozilla.org/MPL/2.0/. The Initial Developer is Webdetails.
 *
 * Software distributed under the Mozilla Public License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. Please refer to
 * the license for the specific language governing your rights and limitations.
 */
package pt.webdetails.cda.endpoints;

import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;

import pt.webdetails.cda.endpoints.RestEndpoint.RequestParameter;

import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static pt.webdetails.cda.endpoints.RestEndpoint.PREFIX_PARAMETER;
import static pt.webdetails.cda.endpoints.RestEndpoint.PREFIX_SETTING;

public class RestEndpointTest {

  private RestEndpoint restEndpoint;
  private HttpServletRequest servletRequestMock;

  @Before
  public void setup() {
    this.restEndpoint = spy( new RestEndpoint() );
    this.servletRequestMock = mock( HttpServletRequest.class );
  }

  @Test
  public void testGetParameters_singleValueExtraParameter() {
    final String expectedName = "foo";
    final String expectedValue = "bar";

    final String paramName = PREFIX_PARAMETER + expectedName;
    doReturn( getParameterNames( paramName ) ).when( servletRequestMock ).getParameterNames();
    doReturn( getParameterValues( expectedValue ) ).when( servletRequestMock ).getParameterValues( paramName );

    // ---

    Map<String, Object> extraParameters = getExtraParameters();

    assertEquals( 1, extraParameters.size() );
    assertEquals( expectedValue, extraParameters.get( expectedName ) );
  }

  @Test
  public void testGetParameters_MultipleValueExtraParameter() {
    final String expectedName = "foo";
    final String[] expectedValue = getParameterValues( "bar1", "bar2" );

    final String paramName = PREFIX_PARAMETER + expectedName;
    doReturn( getParameterNames( paramName ) ).when( servletRequestMock ).getParameterNames();
    doReturn( expectedValue ).when( servletRequestMock ).getParameterValues( paramName );

    // ---

    Map<String, Object> extraParameters = getExtraParameters();

    assertEquals( 1, extraParameters.size() );
    assertEquals( expectedValue, extraParameters.get( expectedName ) );
  }

  private Map<String, Object> getExtraParameters() {
    return this.restEndpoint.getParameters( servletRequestMock, RequestParameter::isExtraParameter );
  }

  @Test
  public void testGetParameters_singleValueExtraSetting() {
    final String expectedName = "foo";
    final String expectedValue = "bar";

    final String paramName = PREFIX_SETTING + expectedName;
    doReturn( getParameterNames( paramName ) ).when( servletRequestMock ).getParameterNames();
    doReturn( getParameterValues( expectedValue ) ).when( servletRequestMock ).getParameterValues( paramName );

    // ---

    Map<String, Object> extraSettings = getExtraSettings();

    assertEquals( 1, extraSettings.size() );
    assertEquals( expectedValue, extraSettings.get( expectedName ) );
  }

  @Test
  public void testGetParameters_MultipleValueExtraSetting() {
    final String expectedName = "foo";
    final String[] expectedValue = getParameterValues( "bar1", "bar2" );

    final String paramName = PREFIX_SETTING + expectedName;
    doReturn( getParameterNames( paramName ) ).when( servletRequestMock ).getParameterNames();
    doReturn( expectedValue ).when( servletRequestMock ).getParameterValues( paramName );

    // ---

    Map<String, Object> extraSettings = getExtraSettings();

    assertEquals( 1, extraSettings.size() );
    assertEquals( expectedValue, extraSettings.get( expectedName ) );
  }

  private Map<String, Object> getExtraSettings() {
    return this.restEndpoint.getParameters( servletRequestMock, RequestParameter::isSettingParameter );
  }

  private Enumeration<String> getParameterNames( String ...names ) {
    return Collections.enumeration( Arrays.asList( names ) );
  }

  private String[] getParameterValues( String ...values ) {
    return values;
  }
}