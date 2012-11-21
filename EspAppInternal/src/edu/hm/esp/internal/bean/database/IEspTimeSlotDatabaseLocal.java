package edu.hm.esp.internal.bean.database;

import java.util.List;

import javax.ejb.Local;
import javax.persistence.LockModeType;

import edu.hm.esp.internal.object.entity.EspEntityTimeSlot;
import edu.hm.esp.internal.object.entity.EspEntityTimeSlotPk;

/**
 * Interface beschreibt alle Methoden die von der Datenbank-Verwaltungs-Bean fuer Zeitslot-Objekte angeboten werden.
 * 
 * @author Stefan Wörner
 */
@Local
public interface IEspTimeSlotDatabaseLocal
{

	/**
	 * Konstante mit der JNDI-Binding-Information (local).
	 */
	String JNDI_BINDING = "EspApplication/EJB/EspTimeSlotDatabaseLocal";

	/**
	 * Fuegt den uebergebenen Zeitslot in die Datenbank ein. Existiert bereits ein Zeitslot mit dem angegebenem Zeitraum
	 * wird eine Exception geworfen.
	 * 
	 * @param timeSlot
	 *            Hinzuzufuegender Zeitslot
	 * @return Hinzugefuegter Zeitslot
	 */
	EspEntityTimeSlot insertTimeSlot( EspEntityTimeSlot timeSlot );

	/**
	 * Liest den Zeitslot mit dem uebergebenen Zeitraum aus der Datenbank. Existiert kein Zeitslot mit dem angegebenen
	 * Zeitraum wird eine Exception geworfen.
	 * 
	 * @param pk
	 *            Primaerschluessel: Zeitraum
	 * @param lockModeType
	 *            LockMode fuer die Entitaet. Kann NULL sein, dann wird kein Lock angefordert
	 * @return Ausgelesener Zeitslot
	 */
	EspEntityTimeSlot readTimeSlotByPK( EspEntityTimeSlotPk pk, LockModeType lockModeType );

	/**
	 * Liest alle vorhandenen Zeitslots aus der Datenbank. Existieren keine Zeitslots wird eine leere Liste
	 * zurueckgegeben.
	 * 
	 * @return Liste der vorhandenen Zeitslots
	 */
	List<EspEntityTimeSlot> readAllTimeSlots();

	/**
	 * Aktualisiert den uebergebenen Zeitslot in der Datenbank. Existiert kein Zeitslot mit dem angegebenem Zeitraum
	 * wird eine Exception geworfen.
	 * 
	 * @param timeSlot
	 *            Zu aktualisierender Zeitslot
	 * @return Aktualisierter Zeitslot
	 */
	EspEntityTimeSlot updateTimeSlotWithoutReferenceUpdate( EspEntityTimeSlot timeSlot );

	/**
	 * Loescht den Zeitslot mit dem uebergebenen Zeitraum aus der Datenbank. Existiert kein Zeitslot mit dem angegebenen
	 * Zeitraum wird eine Exception geworfen.
	 * 
	 * @param pk
	 *            Primaerschluessel: Zeitraum
	 */
	void deleteTimeSlot( EspEntityTimeSlotPk pk );

	/**
	 * Loescht alle Zeitslots (CASCADES: Zeitslot-Aufsicht).
	 */
	void deleteAllTimeSlots();
}
