package hm.edu.esp.service.exception.mapper.esp;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import edu.hm.basic.logging.BasicLogger;
import edu.hm.esp.internal.exception.EspMailingException;

/**
 * ExceptionMapper fuer eine EspMailingException.
 * 
 * @author Stefan Wörner
 */
@Provider
public class EspMailingExceptionMapper implements ExceptionMapper<EspMailingException>
{

	/**
	 * {@inheritDoc}
	 * 
	 * @see javax.ws.rs.ext.ExceptionMapper#toResponse(java.lang.Throwable)
	 */
	@Override
	public Response toResponse( EspMailingException e )
	{
		BasicLogger.logError( e.getClass().getName(), e.getMessage() );
		return Response.serverError().build();
	}
}
