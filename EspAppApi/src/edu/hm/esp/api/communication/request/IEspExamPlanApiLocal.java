package edu.hm.esp.api.communication.request;

import javax.ejb.Local;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import edu.hm.esp.api.object.ressource.EspExamPlan;

/**
 * Interface beschreibt alle Methoden des REST-Services fuer die Ressource Pruefungsplan.
 * 
 * @author Stefan Wörner
 */
@Local
@Path( "/examplan" )
@Produces( { "application/xml", "application/json", "text/xml" } )
@Consumes( { "application/xml", "application/json", "text/xml" } )
public interface IEspExamPlanApiLocal
{

	/**
	 * Konstante mit der JNDI-Binding-Information (local).
	 */
	String JNDI_BINDING = "EspApplication/EJB/EspExamPlanApiLocal";

	/**
	 * Importiert den uebergebenen Pruefungsplan.
	 * 
	 * @param examPlan
	 *            Zu importierender Pruefungsplan
	 */
	@PUT
	@Path( "" )
	void importExamPlan( EspExamPlan examPlan );
}
