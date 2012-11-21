package hm.edu.esp.service.exception.mapper.esp;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import edu.hm.basic.logging.BasicLogger;
import edu.hm.esp.internal.exception.EspAlreadyRegisteredException;

/**
 * ExceptionMapper fuer eine EspAlreadyRegisteredException.
 * 
 * @author Stefan Wörner
 */
@Provider
public class EspAlreadyRegisteredExceptionMapper implements ExceptionMapper<EspAlreadyRegisteredException>
{

	/**
	 * {@inheritDoc}
	 * 
	 * @see javax.ws.rs.ext.ExceptionMapper#toResponse(java.lang.Throwable)
	 */
	@Override
	public Response toResponse( EspAlreadyRegisteredException e )
	{
		BasicLogger.logError( e.getClass().getName(), e.getMessage() );
		return Response.status( Status.BAD_REQUEST ).build();
	}
}
