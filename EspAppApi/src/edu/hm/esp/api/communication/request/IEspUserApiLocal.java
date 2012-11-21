package edu.hm.esp.api.communication.request;

import javax.ejb.Local;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import edu.hm.esp.api.object.ressource.EspRoleList;
import edu.hm.esp.api.object.ressource.EspSupervisionPlan;
import edu.hm.esp.api.object.ressource.EspUser;
import edu.hm.esp.api.object.ressource.EspUserList;

/**
 * Interface beschreibt alle Methoden des REST-Services fuer die Ressource Benutzer.
 * 
 * @author Stefan Wörner
 */
@Local
@Path( "/user" )
@Produces( { "application/xml", "application/json", "text/xml" } )
@Consumes( { "application/xml", "application/json", "text/xml" } )
public interface IEspUserApiLocal
{

	/**
	 * Konstante mit der JNDI-Binding-Information (local).
	 */
	String JNDI_BINDING = "EspApplication/EJB/EspUserApiLocal";

	/**
	 * Liest alle vorhandenen Benutzer aus. REST-Aktion: GET (LIST).
	 * 
	 * @return Liste der vorhandenen Benutzer
	 */
	@GET
	@Path( "" )
	EspUserList getUserList();

	/**
	 * Liest den angemeldeten Benutzer aus. REST-Aktion: GET.
	 * 
	 * @param request
	 *            HTTP Context. (wird vom Framework injiziert)
	 * @return Ausgelesener Benutzer
	 */
	@GET
	@Path( "myprofile" )
	EspUser getUser( @Context HttpServletRequest request );

	/**
	 * Liest einen Benutzer anhand der uebergebenen Daten aus. REST-Aktion: GET.
	 * 
	 * @param loginName
	 *            Auszulesender Benutzer (ID)
	 * @return Ausgelesener Benutzer
	 */
	@GET
	@Path( "{loginName}" )
	EspUser getUser( @PathParam( "loginName" ) String loginName );

	/**
	 * Erstellt einen neuen Benutzer anhand der uebergebenen Daten. REST-Aktion: POST.
	 * 
	 * @param user
	 *            Zu speichernder Benutzer
	 * @return Erstellter Benutzer
	 */
	@POST
	@Path( "" )
	EspUser createUser( EspUser user );

	/**
	 * Aktualisiert einen Benutzer anhand der uebergebenen Daten. REST-Aktion: PUT.
	 * 
	 * @param loginName
	 *            Zu aktualisierender Benutzer (ID)
	 * @param user
	 *            Zu aktualisierender Benutzer
	 * @return Aktualisierter Benutzer
	 */
	@PUT
	@Path( "{loginName}" )
	EspUser updateUser( @PathParam( "loginName" ) String loginName, EspUser user );

	/**
	 * Loescht einen Benutzer anhand der uebergebenen Daten. REST-Aktion: DELETE.
	 * 
	 * @param loginName
	 *            Zu loeschender Benutzer (ID)
	 */
	@DELETE
	@Path( "{loginName}" )
	void removeUser( @PathParam( "loginName" ) String loginName );

	/**
	 * Aktiviert einen Benutzer. Das hei�t, es wird ein Passwort generiert und eine Mail an den betreffenden Benutzer
	 * gesendet. REST-Aktion: PUT.
	 * 
	 * @param loginName
	 *            Zu aktivierender Benutzer (ID)
	 * @return Aktivierter Benutzer
	 */
	@PUT
	@Path( "{loginName}/activate" )
	EspUser activateUser( @PathParam( "loginName" ) String loginName );

	/**
	 * Liest die Rollen des angemeldeten Benutzers aus. REST-Aktion: GET.
	 * 
	 * @param request
	 *            HTTP Context. (wird vom Framework injiziert)
	 * @return Ausgelesener Benutzer
	 */
	@GET
	@Path( "myroles" )
	EspRoleList getUserRoles( @Context HttpServletRequest request );

	/**
	 * Liest die Rollen des uebergebenen Benutzers aus. REST-Aktion: GET.
	 * 
	 * @param loginName
	 *            Benutzer (ID)
	 * @return Ausgelesener Benutzer
	 */
	@GET
	@Path( "{loginName}/roles" )
	EspRoleList getUserRoles( @PathParam( "loginName" ) String loginName );

	/**
	 * Liest den Aufsichtsplan des angemeldeten Benutzers aus. REST-Aktion: GET.
	 * 
	 * @param request
	 *            HTTP Context. (wird vom Framework injiziert)
	 * @return Ausgelesener Aufsichtsplan
	 */
	@GET
	@Path( "mysupervisionplan" )
	EspSupervisionPlan getUserSupervisionPlan( @Context HttpServletRequest request );

	/**
	 * Liest den Aufsichtsplan des uebergebenen Benutzers aus. REST-Aktion: GET.
	 * 
	 * @param loginName
	 *            LoginName des Benutzers
	 * @return Ausgelesener Aufsichtsplan
	 */
	@GET
	@Path( "/{loginName}/supervisionplan" )
	EspSupervisionPlan getUserSupervisionPlan( @PathParam( "loginName" ) String loginName );
}
