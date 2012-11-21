package hm.edu.esp.service.exception.mapper.esp;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import edu.hm.basic.logging.BasicLogger;
import edu.hm.esp.internal.exception.EspNotExistsException;

/**
 * ExceptionMapper fuer eine EspNotExistsException.
 * 
 * @author Stefan Wörner
 */
@Provider
public class EspNotExistsExceptionMapper implements ExceptionMapper<EspNotExistsException>
{

	/**
	 * {@inheritDoc}
	 * 
	 * @see javax.ws.rs.ext.ExceptionMapper#toResponse(java.lang.Throwable)
	 */
	@Override
	public Response toResponse( EspNotExistsException e )
	{
		BasicLogger.logError( e.getClass().getName(), e.getMessage() );
		return Response.status( Status.NOT_FOUND ).build();
	}
}
