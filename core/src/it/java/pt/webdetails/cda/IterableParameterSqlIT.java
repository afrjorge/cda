/*!
* Copyright 2002 - 2013 Webdetails, a Pentaho company.  All rights reserved.
* 
* This software was developed by Webdetails and is provided under the terms
* of the Mozilla Public License, Version 2.0, or any later version. You may not use
* this file except in compliance with the license. If you need a copy of the license,
* please go to  http://mozilla.org/MPL/2.0/. The Initial Developer is Webdetails.
*
* Software distributed under the Mozilla Public License is distributed on an "AS IS"
* basis, WITHOUT WARRANTY OF ANY KIND, either express or  implied. Please refer to
* the license for the specific language governing your rights and limitations.
*/

package pt.webdetails.cda;

import pt.webdetails.cda.query.QueryOptions;
import pt.webdetails.cda.settings.CdaSettings;
import pt.webdetails.cda.utils.test.CdaTestCase;

public class IterableParameterSqlIT extends CdaTestCase {

  public void testIterateStatus() throws Exception {

    final CdaSettings cdaSettings = parseSettingsFile( "sample-iterable-sql.cda" );

    final CdaEngine engine = CdaEngine.getInstance();

    QueryOptions queryOptions = new QueryOptions();
    queryOptions.setDataAccessId( "1" );
    queryOptions.addParameter( "status", "$FOREACH(2,0)" );
    queryOptions.addParameter( "year", "$FOREACH(3,0,minYear=2003)" );
    queryOptions.getParameter( "year" ).setDefaultValue( "2004" );
    queryOptions.setOutputType( "csv" );
    queryOptions.addParameter( "status", "In Process" );

    engine.doQuery( cdaSettings, queryOptions );

    queryOptions = new QueryOptions();
    queryOptions.setDataAccessId( "4" );
    queryOptions.addParameter( "status", "$FOREACH(2,0)" );
    queryOptions.addParameter( "year", "$FOREACH(3,0,minYear=2525)" ); //no results
    queryOptions.getParameter( "year" ).setDefaultValue( "2004" ); //this time should fallback to default
    queryOptions.setOutputType( "csv" );

    engine.doQuery( cdaSettings, queryOptions );

  }
}
