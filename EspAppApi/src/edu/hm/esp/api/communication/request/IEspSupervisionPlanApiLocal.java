package edu.hm.esp.api.communication.request;

import java.util.Date;

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

import edu.hm.esp.api.object.ressource.EspExam;
import edu.hm.esp.api.object.ressource.EspExamList;
import edu.hm.esp.api.object.ressource.EspSupervisionPlan;
import edu.hm.esp.api.object.ressource.EspTimeSlot;
import edu.hm.esp.api.object.ressource.EspTimeSlotList;
import edu.hm.esp.api.resteasy.unmarschaller.EspDateFormat;

/**
 * Interface beschreibt alle Methoden des REST-Services fuer die Ressource Aufsichtsplan.
 * 
 * @author Stefan Wörner
 */
@Local
@Path( "/supervisionplan" )
@Produces( { "application/xml", "application/json", "text/xml" } )
@Consumes( { "application/xml", "application/json", "text/xml" } )
public interface IEspSupervisionPlanApiLocal
{

	/**
	 * Konstante mit der JNDI-Binding-Information (local).
	 */
	String JNDI_BINDING = "EspApplication/EJB/EspSupervisionPlanApiLocal";

	/**
	 * Liest den vollstaendigen Aufsichtsplan, inklusive Pruefungen, Raumzuordnungen, Aufsichtspersonen, Zeitslots, usw.
	 * aus.
	 * 
	 * @return Vollstaendiger Aufsichtsplan
	 */
	@GET
	@Path( "" )
	EspSupervisionPlan getSupervisionPlan();

	/**
	 * Liest alle vorhandenen Zeitslots aus. REST-Aktion: GET (LIST).
	 * 
	 * @return Liste der vorhandenen Zeitslots
	 */
	@GET
	@Path( "/timeslot" )
	EspTimeSlotList getTimeSlotList();

	/**
	 * Liest einen Zeitslot anhand der uebergebenen Daten aus. REST-Aktion: GET.
	 * 
	 * @param fromDate
	 *            Startdatum des Zeitslots (PK)
	 * @param tillDate
	 *            Enddatum des Zeitslots (PK)
	 * @return Ausgelesener Zeitslot
	 */
	@GET
	@Path( "/timeslot/{from}_till_{till}" )
	EspTimeSlot getTimeSlot( @PathParam( "from" ) @EspDateFormat Date fromDate, @PathParam( "till" ) @EspDateFormat Date tillDate );

	/**
	 * Erstellt einen neuen Zeitslot anhand der uebergebenen Daten. REST-Aktion: POST.
	 * 
	 * @param timeSlot
	 *            Zu erstellender Zeitslot
	 * @return Erstellter Zeitslot
	 */
	@POST
	@Path( "/timeslot" )
	EspTimeSlot createTimeSlot( EspTimeSlot timeSlot );

	/**
	 * Aktualisiert einen Zeitslot anhand der uebergebenen Daten. REST-Aktion: PUT.
	 * 
	 * @param fromDate
	 *            Startdatum des Zeitslots (PK)
	 * @param tillDate
	 *            Enddatum des Zeitslots (PK)
	 * @param timeSlot
	 *            Zu aktualisierender Zeitslot
	 * @return Aktualisierter Zeitslot
	 */
	@PUT
	@Path( "/timeslot/{from}_till_{till}" )
	EspTimeSlot updateTimeSlot( @PathParam( "from" ) @EspDateFormat Date fromDate,
			@PathParam( "till" ) @EspDateFormat Date tillDate, EspTimeSlot timeSlot );

	/**
	 * Loescht einen Zeitslot anhand der uebergebenen Daten. REST-Aktion: DELETE.
	 * 
	 * @param fromDate
	 *            Startdatum des Zeitslots (PK)
	 * @param tillDate
	 *            Enddatum des Zeitslots (PK)
	 */
	@DELETE
	@Path( "/timeslot/{from}_till_{till}" )
	void removeTimeSlot( @PathParam( "from" ) @EspDateFormat Date fromDate, @PathParam( "till" ) @EspDateFormat Date tillDate );

	/**
	 * Liest alle vorhandenen Pruefungen aus. REST-Aktion: GET (LIST).
	 * 
	 * @return Liste der vorhandenen Pruefungen
	 */
	@GET
	@Path( "/exam" )
	EspExamList getExamList();

	/**
	 * Liest eine Pruefung anhand der uebergebenen Daten aus. REST-Aktion: GET.
	 * 
	 * @param examCode
	 *            Auszulesende Pruefung (ID)
	 * @return Ausgelesene Pruefung
	 */
	@GET
	@Path( "/exam/{examCode}" )
	EspExam getExam( @PathParam( "examCode" ) String examCode );

	/**
	 * Aktualisiert eine Pruefung anhand der uebergebenen Daten. REST-Aktion: PUT.
	 * 
	 * @param examCode
	 *            Zu aktualisierende Pruefung (ID)
	 * @param exam
	 *            Zu aktualisierende Pruefung
	 * @return Aktualisierte Pruefung
	 */
	@PUT
	@Path( "/exam/{examCode}" )
	EspExam updateExam( @PathParam( "examCode" ) String examCode, EspExam exam );

	/**
	 * Traegt den angemeldeten Benutzer als Aufsicht fuer die uebergebene Pruefung ein. Dies ist allerdings nur
	 * moeglich, wenn das Kontingent der Pruefung noch nicht erfuellt ist und die Aufsichtsperson noch nicht als
	 * Aufsicht eingetragen ist. Ist eine Eintragung nicht moeglich, wird ein Fehler zurueckgegeben. REST-Aktion: PUT.
	 * 
	 * @param examCode
	 *            Pruefungscode der Pruefung fuer welche sich die Aufsichtsperson eintragen moechte
	 * @param request
	 *            HTTP Context. (wird vom Framework injiziert)
	 * @return Aktualisierte Pruefung inkl. Aufsichtspersonen
	 */
	@PUT
	@Path( "/exam/{examCode}/register" )
	EspExam registerSupervisorForExam( @PathParam( "examCode" ) String examCode, @Context HttpServletRequest request );

	/**
	 * Traegt die uebergebene Aufsichtsperson als Aufsicht fuer die uebergebene Pruefung ein. Dies ist allerdings nur
	 * moeglich, wenn das Kontingent der Pruefung noch nicht erfuellt ist und die Aufsichtsperson noch nicht als
	 * Aufsicht eingetragen ist. Ist eine Eintragung nicht moeglich, wird ein Fehler zurueckgegeben. REST-Aktion: PUT.
	 * 
	 * @param examCode
	 *            Pruefungscode der Pruefung fuer welche sich die Aufsichtsperson eintragen moechte
	 * @param loginName
	 *            LoginName der einzutragenden Aufsichtsperson
	 * @return Aktualisierte Pruefung inkl. Aufsichtspersonen
	 */
	@PUT
	@Path( "/exam/{examCode}/register/{loginName}" )
	EspExam registerSupervisorForExam( @PathParam( "examCode" ) String examCode, @PathParam( "loginName" ) String loginName );

	/**
	 * Traegt den angemeldeten Benutzer als Aufsicht fuer die uebergebene Pruefung aus. Dies ist allerdings nur
	 * moeglich, wenn die Aufsichtsperson als Aufsicht eingetragen ist und die Aufsichtsperson nicht der Pruefer ist.
	 * Ist eine Austragung nicht moeglich, wird ein Fehler zurueckgegeben. REST-Aktion: PUT.
	 * 
	 * @param examCode
	 *            Pruefungscode der Pruefung fuer welche sich die Aufsichtsperson austragen moechte
	 * @param request
	 *            HTTP Context. (wird vom Framework injiziert)
	 * @return Aktualisierte Pruefung inkl. Aufsichtspersonen
	 */
	@PUT
	@Path( "/exam/{examCode}/unregister" )
	EspExam unregisterSupervisorFromExam( @PathParam( "examCode" ) String examCode, @Context HttpServletRequest request );

	/**
	 * Traegt die uebergebene Aufsichtsperson als Aufsicht fuer die uebergebene Pruefung aus. Dies ist allerdings nur
	 * moeglich, wenn die Aufsichtsperson als Aufsicht eingetragen ist und die Aufsichtsperson nicht der Pruefer ist.
	 * Ist eine Austragung nicht moeglich, wird ein Fehler zurueckgegeben. REST-Aktion: PUT.
	 * 
	 * @param examCode
	 *            Pruefungscode der Pruefung fuer welche sich die Aufsichtsperson austragen moechte
	 * @param loginName
	 *            LoginName der auszutragenden Aufsichtsperson
	 * @return Aktualisierte Pruefung inkl. Aufsichtspersonen
	 */
	@PUT
	@Path( "/exam/{examCode}/unregister/{loginName}" )
	EspExam unregisterSupervisorFromExam( @PathParam( "examCode" ) String examCode, @PathParam( "loginName" ) String loginName );

	/**
	 * Traegt den angemeldeten Benutzer als Aufsicht fuer die uebergebenen Zeitslot ein. Dies ist allerdings nur
	 * moeglich, wenn das Kontingent des Zeitslots noch nicht erfuellt ist und die Aufsichtsperson noch nicht als
	 * Aufsicht eingetragen ist. Ist eine Eintragung nicht moeglich, wird ein Fehler zurueckgegeben. REST-Aktion: PUT.
	 * 
	 * @param fromDate
	 *            Startdatum des Zeitslots fuer welchen sich die Aufsichtsperson eintragen moechte
	 * @param tillDate
	 *            Enddatum des Zeitslots fuer welchen sich die Aufsichtsperson eintragen moechte
	 * @param request
	 *            HTTP Context. (wird vom Framework injiziert)
	 * @return Aktualisierter Zeitslot inkl. Aufsichtspersonen
	 */
	@PUT
	@Path( "/timeslot/{from}_till_{till}/register" )
	EspTimeSlot registerSupervisorForTimeSlot( @PathParam( "from" ) @EspDateFormat Date fromDate,
			@PathParam( "till" ) @EspDateFormat Date tillDate, @Context HttpServletRequest request );

	/**
	 * Traegt die uebergebene Aufsichtsperson als Aufsicht fuer die uebergebenen Zeitslot ein. Dies ist allerdings nur
	 * moeglich, wenn das Kontingent des Zeitslots noch nicht erfuellt ist und die Aufsichtsperson noch nicht als
	 * Aufsicht eingetragen ist. Ist eine Eintragung nicht moeglich, wird ein Fehler zurueckgegeben. REST-Aktion: PUT.
	 * 
	 * @param fromDate
	 *            Startdatum des Zeitslots fuer welchen sich die Aufsichtsperson eintragen moechte
	 * @param tillDate
	 *            Enddatum des Zeitslots fuer welchen sich die Aufsichtsperson eintragen moechte
	 * @param loginName
	 *            LoginName der einzutragenden Aufsichtsperson
	 * @return Aktualisierter Zeitslot inkl. Aufsichtspersonen
	 */
	@PUT
	@Path( "/timeslot/{from}_till_{till}/register/{loginName}" )
	EspTimeSlot registerSupervisorForTimeSlot( @PathParam( "from" ) @EspDateFormat Date fromDate,
			@PathParam( "till" ) @EspDateFormat Date tillDate, @PathParam( "loginName" ) String loginName );

	/**
	 * Traegt den angemeldeten Benutzer als Aufsicht fuer die uebergebenen Zeitslot aus. Dies ist allerdings nur
	 * moeglich, wenn die Aufsichtsperson als Aufsicht eingetragen ist. Ist eine Austragung nicht moeglich, wird ein
	 * Fehler zurueckgegeben. REST-Aktion: PUT.
	 * 
	 * @param fromDate
	 *            Startdatum des Zeitslots fuer welchen sich die Aufsichtsperson austragen moechte
	 * @param tillDate
	 *            Enddatum des Zeitslots fuer welchen sich die Aufsichtsperson austragen moechte
	 * @param request
	 *            HTTP Context. (wird vom Framework injiziert)
	 * @return Aktualisierter Zeitslot inkl. Aufsichtspersonen
	 */
	@PUT
	@Path( "/timeslot/{from}_till_{till}/unregister" )
	EspTimeSlot unregisterSupervisorFromTimeSlot( @PathParam( "from" ) @EspDateFormat Date fromDate,
			@PathParam( "till" ) @EspDateFormat Date tillDate, @Context HttpServletRequest request );

	/**
	 * Traegt die uebergebene Aufsichtsperson als Aufsicht fuer die uebergebenen Zeitslot aus. Dies ist allerdings nur
	 * moeglich, wenn die Aufsichtsperson als Aufsicht eingetragen ist. Ist eine Austragung nicht moeglich, wird ein
	 * Fehler zurueckgegeben. REST-Aktion: PUT.
	 * 
	 * @param fromDate
	 *            Startdatum des Zeitslots fuer welchen sich die Aufsichtsperson austragen moechte
	 * @param tillDate
	 *            Enddatum des Zeitslots fuer welchen sich die Aufsichtsperson austragen moechte
	 * @param loginName
	 *            LoginName der auszutragenden Aufsichtsperson
	 * @return Aktualisierter Zeitslot inkl. Aufsichtspersonen
	 */
	@PUT
	@Path( "/timeslot/{from}_till_{till}/unregister/{loginName}" )
	EspTimeSlot unregisterSupervisorFromTimeSlot( @PathParam( "from" ) @EspDateFormat Date fromDate,
			@PathParam( "till" ) @EspDateFormat Date tillDate, @PathParam( "loginName" ) String loginName );
}
