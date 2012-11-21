package edu.hm.esp.api.communication.request;

import javax.ejb.Local;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import edu.hm.esp.api.object.ressource.EspVersion;

/**
 * Interface beschreibt alle Methoden des REST-Services fuer die Ressource Konfiguration.
 * 
 * @author Stefan Wörner
 */
@Local
@Path( "/config" )
@Produces( { "application/xml", "application/json", "text/xml" } )
@Consumes( { "application/xml", "application/json", "text/xml" } )
public interface IEspConfigApiLocal
{

	/**
	 * Konstante mit der JNDI-Binding-Information (local).
	 */
	String JNDI_BINDING = "EspApplication/EJB/EspConfigApiLocal";

	/**
	 * Liefert die aktuelle Versionsnummer der ESP-Server-Anteile zurueck.
	 * 
	 * @return String, Version des internen Replikationsdienstes
	 */
	@GET
	@Path( "version" )
	EspVersion getVersion();

	/**
	 * Setzt die Datenbank vollstaendig zurueck (Ausgangszustand).
	 */
	@DELETE
	@Path( "reset_all" )
	void resetDatabaseCompletely();

	/**
	 * Setzt die Datenbank zurueck, Benutzer bleiben erhalten.
	 */
	@DELETE
	@Path( "reset_examplan" )
	void resetDatabaseExamplanOnly();
}
