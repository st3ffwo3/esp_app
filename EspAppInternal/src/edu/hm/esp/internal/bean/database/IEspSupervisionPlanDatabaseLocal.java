package edu.hm.esp.internal.bean.database;

import javax.ejb.Local;

import edu.hm.esp.internal.object.entity.EspEntityExam;
import edu.hm.esp.internal.object.entity.EspEntityTimeSlot;
import edu.hm.esp.internal.object.entity.EspEntityTimeSlotPk;

/**
 * Interface beschreibt alle Methoden die von der Datenbank-Verwaltungs-Bean fuer Aufsichtsplan-Objekte angeboten
 * werden.
 * 
 * @author Stefan Wörner
 */
@Local
public interface IEspSupervisionPlanDatabaseLocal
{

	/**
	 * Konstante mit der JNDI-Binding-Information (local).
	 */
	String JNDI_BINDING = "EspApplication/EJB/EspSupervisionPlanDatabaseLocal";

	/**
	 * Fuegt den uebergebenen Benutzer der uebergebenen Pruefung als Aufsicht hinzu.
	 * 
	 * @param examCode
	 *            Pruefungscode der Pruefung (PK)
	 * @param loginName
	 *            LoginName des Benutzers (PK)
	 * @return Aktualisierte Pruefung
	 */
	EspEntityExam addSupervisorForExam( String examCode, String loginName );

	/**
	 * Loescht den uebergebenen Benutzer als Aufsicht der uebergebenen Pruefung.
	 * 
	 * @param examCode
	 *            Pruefungscode der Pruefung (PK)
	 * @param loginName
	 *            LoginName des Benutzers (PK)
	 * @return Aktualisierte Pruefung
	 */
	EspEntityExam removeSupervisorFromExam( String examCode, String loginName );

	/**
	 * Fuegt den uebergebenen Benutzer dem Zeitslot als Aufsicht hinzu.
	 * 
	 * @param timeSlotPK
	 *            Zeitslot (PK)
	 * @param loginName
	 *            LoginName des Benutzers (PK)
	 * @return Aktualisierter Zeitslot
	 */
	EspEntityTimeSlot addSupervisorForTimeSlot( EspEntityTimeSlotPk timeSlotPK, String loginName );

	/**
	 * Loescht den uebergebenen Benutzer als Aufsicht des uebergebenen Zeitslots.
	 * 
	 * @param timeSlotPK
	 *            Zeitslot (PK)
	 * @param loginName
	 *            LoginName des Benutzers (PK)
	 * @return Aktualisierter Zeitslot
	 */
	EspEntityTimeSlot removeSupervisorFromTimeSlot( EspEntityTimeSlotPk timeSlotPK, String loginName );
}
