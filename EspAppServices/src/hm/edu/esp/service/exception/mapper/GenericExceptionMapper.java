package hm.edu.esp.service.exception.mapper;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.Providers;

import edu.hm.basic.logging.BasicLogger;

/**
 * ExceptionMapper fuer eine RuntimeException.
 * 
 * @author Stefan Wörner
 */
@Provider
public class GenericExceptionMapper implements ExceptionMapper<RuntimeException>
{

	@Context
	private Providers m_providers;

	/**
	 * {@inheritDoc}
	 * 
	 * @see javax.ws.rs.ext.ExceptionMapper#toResponse(java.lang.Throwable)
	 */
	@Override
	@SuppressWarnings( { "rawtypes", "unchecked" } )
	public Response toResponse( RuntimeException e )
	{
		Throwable cause = e;
		ExceptionMapper mapper = null;

		try
		{
			while (cause != null)
			{
				if (cause instanceof WebApplicationException)
				{
					return ((WebApplicationException) cause).getResponse();
				}

				mapper = m_providers.getExceptionMapper( cause.getClass() );

				if (mapper != null)
				{
					return mapper.toResponse( e.getCause() );
				}
				else
				{
					cause = cause.getCause();
				}
			}
		}
		catch (Exception ex)
		{
			BasicLogger.logError( ex.getClass().getName(), ex.getMessage() );
		}

		BasicLogger.logError( e.getClass().getName(), e.getMessage() );
		return Response.serverError().build();
	}
}
