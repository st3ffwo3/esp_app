package hm.edu.esp.service.exception.mapper.hibernate;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.hibernate.validator.InvalidStateException;

import edu.hm.basic.logging.BasicLogger;

/**
 * ExceptionMapper fuer eine InvalidStateException.
 * 
 * @author Stefan Wörner
 */
@Provider
public class InvalidStateExceptionMapper implements ExceptionMapper<InvalidStateException>
{

	/**
	 * {@inheritDoc}
	 * 
	 * @see javax.ws.rs.ext.ExceptionMapper#toResponse(java.lang.Throwable)
	 */
	@Override
	public Response toResponse( InvalidStateException e )
	{
		BasicLogger.logError( e.getClass().getName(), e.getMessage() );
		return Response.status( Status.BAD_REQUEST ).build();
	}
}
