package hm.edu.esp.service.exception.mapper.esp;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import edu.hm.basic.logging.BasicLogger;
import edu.hm.esp.internal.exception.EspNotRegisteredException;

/**
 * ExceptionMapper fuer eine EspNotRegisteredException.
 * 
 * @author Stefan Wörner
 */
@Provider
public class EspNotRegisteredExceptionMapper implements ExceptionMapper<EspNotRegisteredException>
{

	/**
	 * {@inheritDoc}
	 * 
	 * @see javax.ws.rs.ext.ExceptionMapper#toResponse(java.lang.Throwable)
	 */
	@Override
	public Response toResponse( EspNotRegisteredException e )
	{
		BasicLogger.logError( e.getClass().getName(), e.getMessage() );
		return Response.status( Status.BAD_REQUEST ).build();
	}
}
