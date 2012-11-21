package hm.edu.esp.service.exception.mapper.ejb;

import javax.ejb.EJBAccessException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import edu.hm.basic.logging.BasicLogger;

/**
 * ExceptionMapper fuer eine EJBAccessException.
 * 
 * @author Stefan Wörner
 */
@Provider
public class EJBAccessExceptionMapper implements ExceptionMapper<EJBAccessException>
{

	/**
	 * {@inheritDoc}
	 * 
	 * @see javax.ws.rs.ext.ExceptionMapper#toResponse(java.lang.Throwable)
	 */
	@Override
	public Response toResponse( EJBAccessException e )
	{
		BasicLogger.logError( e.getClass().getName(), e.getMessage() );
		return Response.status( Status.FORBIDDEN ).build();
	}
}
