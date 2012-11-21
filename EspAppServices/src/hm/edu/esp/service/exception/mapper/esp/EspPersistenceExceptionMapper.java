package hm.edu.esp.service.exception.mapper.esp;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import edu.hm.basic.logging.BasicLogger;
import edu.hm.esp.internal.exception.EspPersistenceException;

/**
 * ExceptionMapper fuer eine EspPersistenceException.
 * 
 * @author Stefan Wörner
 */
@Provider
public class EspPersistenceExceptionMapper implements ExceptionMapper<EspPersistenceException>
{

	/**
	 * {@inheritDoc}
	 * 
	 * @see javax.ws.rs.ext.ExceptionMapper#toResponse(java.lang.Throwable)
	 */
	@Override
	public Response toResponse( EspPersistenceException e )
	{
		BasicLogger.logError( e.getClass().getName(), e.getMessage() );
		return Response.serverError().build();
	}
}
