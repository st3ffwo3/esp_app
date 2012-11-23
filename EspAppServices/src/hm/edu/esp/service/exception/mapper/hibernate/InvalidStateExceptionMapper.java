package hm.edu.esp.service.exception.mapper.hibernate;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import edu.hm.basic.logging.BasicLogger;

/**
 * ExceptionMapper fuer eine InvalidStateException.
 * 
 * @author Stefan Wörner
 */
@Provider
public class InvalidStateExceptionMapper implements ExceptionMapper<ConstraintViolationException>
{

	/**
	 * {@inheritDoc}
	 * 
	 * @see javax.ws.rs.ext.ExceptionMapper#toResponse(java.lang.Throwable)
	 */
	@Override
	public Response toResponse( ConstraintViolationException e )
	{
		BasicLogger.logError( e.getClass().getName(), e.getMessage() );
		return Response.status( Status.BAD_REQUEST ).build();
	}
}
