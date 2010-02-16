package pt.webdetails.cda.connections.sql;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Element;
import org.pentaho.reporting.engine.classic.core.modules.misc.datafactory.sql.ConnectionProvider;
import org.pentaho.reporting.engine.classic.core.modules.misc.datafactory.sql.DriverConnectionProvider;
import pt.webdetails.cda.connections.InvalidConnectionException;
import pt.webdetails.cda.utils.Util;

/**
 * Created by IntelliJ IDEA.
 * User: pedro
 * Date: Feb 2, 2010
 * Time: 5:09:18 PM
 */
public class JdbcConnection extends AbstractSqlConnection
{

  private static final Log logger = LogFactory.getLog(JdbcConnection.class);
  public static final String TYPE = "sqlJdbc";

  private JdbcConnectionInfo connectionInfo;

  public JdbcConnection(final Element connection) throws InvalidConnectionException
  {

    super(connection);

  }


  @Override
  protected void initializeConnection(final Element connection) throws InvalidConnectionException
  {

    connectionInfo = new JdbcConnectionInfo(connection);

  }

  @Override
  public String getType()
  {
    return TYPE;
  }


  @Override
  public ConnectionProvider getInitializedConnectionProvider() throws InvalidConnectionException
  {


    logger.debug("Creating new jdbc connection");

    final DriverConnectionProvider connectionProvider = new DriverConnectionProvider();
    connectionProvider.setDriver(connectionInfo.getDriver());
    connectionProvider.setUrl(connectionInfo.getUrl());
    logger.debug("Opening connection");
    try
    {
      final Connection connection = connectionProvider.createConnection(connectionInfo.getUser(), connectionInfo.getPass());
      connection.close();
    }
    catch (SQLException e)
    {

      throw new InvalidConnectionException("JdbcConnection: Found SQLException: " + Util.getExceptionDescription(e), e);
    }

    logger.debug("Connection opened");

    return connectionProvider;
  }

  public boolean equals(final Object o)
  {
    if (this == o)
    {
      return true;
    }
    if (o == null || getClass() != o.getClass())
    {
      return false;
    }

    final JdbcConnection that = (JdbcConnection) o;

    if (connectionInfo != null ? !connectionInfo.equals(that.connectionInfo) : that.connectionInfo != null)
    {
      return false;
    }

    return true;
  }

  public int hashCode()
  {
    return connectionInfo != null ? connectionInfo.hashCode() : 0;
  }
}