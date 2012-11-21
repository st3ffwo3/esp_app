package edu.hm.esp.api.communication.request;

import javax.ejb.Local;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

/**
 * Interface beschreibt Methoden fuer die Authentifizierung (Login/Logout) eines Benutzers.
 * 
 * @author Stefan Wörner
 */
@Local
@Path( "/" )
@Produces( { "application/xml", "application/json", "text/xml" } )
@Consumes( { "application/xml", "application/json", "text/xml" } )
public interface IEspAuthApiLocal
{

	/**
	 * Konstante mit der JNDI-Binding-Information (local).
	 */
	String JNDI_BINDING = "EspApplication/EJB/EspAuthenticationApiLocal";

	/**
	 * Meldet den Benutzer vom Sytem ab und invalidiert die zugehoerige Session.
	 * 
	 * @param request
	 *            HTTP Context. (wird vom Framework injiziert)
	 */
	@GET
	@Path( "logout" )
	void logout( @Context HttpServletRequest request );
}
