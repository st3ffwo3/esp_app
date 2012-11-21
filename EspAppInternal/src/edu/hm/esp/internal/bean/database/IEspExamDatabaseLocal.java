package edu.hm.esp.internal.bean.database;

import java.util.List;

import javax.ejb.Local;
import javax.persistence.LockModeType;

import edu.hm.esp.internal.object.entity.EspEntityExam;

/**
 * Interface beschreibt alle Methoden die von der Datenbank-Verwaltungs-Bean fuer Pruefungs-Objekte angeboten werden.
 * 
 * @author Stefan Wörner
 */
@Local
public interface IEspExamDatabaseLocal
{

	/**
	 * Konstante mit der JNDI-Binding-Information (local).
	 */
	String JNDI_BINDING = "EspApplication/EJB/EspExamDatabaseLocal";

	/**
	 * Fuegt den uebergebenen Pruefung in die Datenbank ein. Existiert bereits eine Pruefung mit dem angegebenem
	 * Pruefungscode wird eine Exception geworfen.
	 * 
	 * @param exam
	 *            Hinzuzufuegende Pruefung
	 * @return Hinzugefuegte Pruefung
	 */
	EspEntityExam insertExam( EspEntityExam exam );

	/**
	 * Liest die Pruefung mit dem uebergebenen Pruefungscode aus der Datenbank. Existiert keine Pruefung mit dem
	 * angegebenen Pruefungscode wird eine Exception geworfen.
	 * 
	 * @param examCode
	 *            Primaerschluessel: Pruefungscode
	 * @param lockModeType
	 *            LockMode fuer die Entitaet. Kann NULL sein, dann wird kein Lock angefordert
	 * @return Ausgelesene Pruefung
	 */
	EspEntityExam readExamByPK( String examCode, LockModeType lockModeType );

	/**
	 * Liest alle vorhandenen Pruefungen aus der Datenbank. Existieren keine Pruefungen wird eine leere Liste
	 * zurueckgegeben.
	 * 
	 * @return Liste der vorhandenen Pruefungen
	 */
	List<EspEntityExam> readAllExams();

	/**
	 * Aktualisiert die uebergebene Pruefen in der Datenbank. Existiert keine Pruefung mit dem angegebenem Pruefungscode
	 * wird eine Exception geworfen.
	 * 
	 * @param exam
	 *            Zu aktualisierende Pruefung
	 * @return Aktualisierte Pruefung
	 */
	EspEntityExam updateExamWithoutReferenceUpdate( EspEntityExam exam );

	/**
	 * Loescht die Pruefung mit dem uebergebenen Pruefungscode aus der Datenbank. Existiert keine Pruefung mit dem
	 * angegebenen Pruefungscode wird eine Exception geworfen.
	 * 
	 * @param examCode
	 *            Primaerschluessel: Pruefungscode
	 */
	void deleteExam( String examCode );

	/**
	 * Loescht alle Pruefungen (CASCADES: Pruefung-Raum-Zuordnung).
	 */
	void deleteAllExams();
}
