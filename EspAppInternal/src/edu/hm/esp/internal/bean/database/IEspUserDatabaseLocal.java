package edu.hm.esp.internal.bean.database;

import java.util.List;

import javax.ejb.Local;

import edu.hm.esp.internal.object.entity.EspEntityUser;

/**
 * Interface beschreibt alle Methoden die von der Datenbank-Verwaltungs-Bean fuer Benutzer-Objekte angeboten werden.
 * 
 * @author Stefan Wörner
 */
@Local
public interface IEspUserDatabaseLocal
{

	/**
	 * Konstante mit der JNDI-Binding-Information (local).
	 */
	String JNDI_BINDING = "EspApplication/EJB/EspUserDatabaseLocal";

	/**
	 * Fuegt den uebergebenen Benutzer in die Datenbank ein. Existiert bereits ein Benutzer mit dem angegebenem
	 * LoginNamen wird eine Exception geworfen.
	 * 
	 * @param user
	 *            Hinzuzufuegender Benutzer
	 * @return Hinzugefuegter Benutzer
	 */
	EspEntityUser insertUser( EspEntityUser user );

	/**
	 * Liest den Benutzer mit dem uebergebenen LoginNamen aus der Datenbank. Existiert kein Benutzer mit dem angegebenen
	 * LoginNamen wird eine Exception geworfen.
	 * 
	 * @param loginName
	 *            LoginName des auszulesenden Benutzers
	 * @return Ausgelesener Benutzer
	 */
	EspEntityUser readUserByPK( String loginName );

	/**
	 * Liest alle vorhandenen Benutzer aus der Datenbank. Existieren keine Benutzer wird eine leere Liste
	 * zurueckgegeben.
	 * 
	 * @return Liste der vorhandenen Benutzer
	 */
	List<EspEntityUser> readAllUser();

	/**
	 * Aktualisiert den uebergebenen Benutzer in der Datenbank. Existiert kein Benutzer mit dem angegebenem LoginNamen
	 * wird eine Exception geworfen.
	 * 
	 * @param user
	 *            Zu aktualisierender Benutzer
	 * @return Aktualisierter Benutzer
	 */
	EspEntityUser updateUserWithoutReferenceUptdate( EspEntityUser user );

	/**
	 * Loescht den Benutzer mit dem uebergebenen LoginNamen aus der Datenbank. Existiert kein Benutzer mit dem
	 * angegebenen LoginNamen wird eine Exception geworfen.
	 * 
	 * @param loginName
	 *            LoginName des zu loeschenden Benutzers
	 */
	void deleteUser( String loginName );

	/**
	 * Loescht alle Benutzer (CASCADES: Pruefungs-Aufsicht, Zeitslot-Aufsicht).
	 */
	void deleteAllUser();
}
