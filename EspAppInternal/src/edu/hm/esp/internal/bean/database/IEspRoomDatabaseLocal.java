package edu.hm.esp.internal.bean.database;

import javax.ejb.Local;

/**
 * Interface beschreibt alle Methoden die von der Datenbank-Verwaltungs-Bean fuer Raum-Objekte angeboten werden.
 * 
 * @author Stefan Wörner
 */
@Local
public interface IEspRoomDatabaseLocal
{

	/**
	 * Konstante mit der JNDI-Binding-Information (local).
	 */
	String JNDI_BINDING = "EspApplication/EJB/EspRoomDatabaseLocal";

	/**
	 * Loescht alle Raeume (CASCADES: Pruefung-Raum-Zuordnung).
	 */
	void deleteAllRooms();
}
