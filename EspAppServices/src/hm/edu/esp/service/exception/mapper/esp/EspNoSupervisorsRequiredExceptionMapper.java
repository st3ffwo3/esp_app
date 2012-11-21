package hm.edu.esp.service.exception.mapper.esp;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import edu.hm.basic.logging.BasicLogger;
import edu.hm.esp.internal.exception.EspNoSupervisorsRequiredException;

/**
 * ExceptionMapper fuer eine EspNoSupervisorsRequiredException.
 * 
 * @author Stefan Wörner
 */
@Provider
public class EspNoSupervisorsRequiredExceptionMapper implements ExceptionMapper<EspNoSupervisorsRequiredException>
{

	/**
	 * {@inheritDoc}
	 * 
	 * @see javax.ws.rs.ext.ExceptionMapper#toResponse(java.lang.Throwable)
	 */
	@Override
	public Response toResponse( EspNoSupervisorsRequiredException e )
	{
		BasicLogger.logError( e.getClass().getName(), e.getMessage() );
		return Response.status( Status.BAD_REQUEST ).build();
	}
}
