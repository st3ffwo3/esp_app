package edu.hm.esp.database.bean;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import org.jboss.ejb3.annotation.LocalBinding;

import edu.hm.basic.logging.BasicLogger;
import edu.hm.esp.internal.bean.AbstractEspBean;
import edu.hm.esp.internal.bean.database.IEspExamDatabaseLocal;
import edu.hm.esp.internal.exception.EspAlreadyExistsException;
import edu.hm.esp.internal.exception.EspNotExistsException;
import edu.hm.esp.internal.exception.EspPersistenceException;
import edu.hm.esp.internal.exception.EspValidationException;
import edu.hm.esp.internal.object.entity.EspEntityExam;
import edu.hm.esp.internal.object.entity.EspEntityExamRoomAssignment;
import edu.hm.esp.internal.object.entity.EspEntityUser;

/**
 * Bean zur Verwaltung der Pruefungen in der Datenbank.
 * 
 * @author Stefan Wörner
 */
@Stateless
@LocalBinding( jndiBinding = IEspExamDatabaseLocal.JNDI_BINDING )
public class EspExamDatabaseBean extends AbstractEspBean implements IEspExamDatabaseLocal
{

	@PersistenceContext( unitName = "EspManager" )
	private EntityManager m_em;

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.esp.internal.bean.database.IEspExamDatabaseLocal#insertExam(edu.hm.esp.internal.object.entity.EspEntityExam)
	 */
	@Override
	public EspEntityExam insertExam( EspEntityExam exam )
	{
		try
		{
			if (exam == null)
			{
				throw new EspValidationException( "Ung�ltige Pr�fung: " + null );
			}

			// Eingangsparameter validieren -> Bei Fehler wird Exception geworfen
			exam.validate();

			// Pruefen ob bereits Pruefung mit dem angegebenen Pruefungscode exisitert
			EspEntityExam existingExam = m_em.find( EspEntityExam.class, exam.getCode() );

			// Pruefungscode bereits vergeben -> Exception werfen
			if (existingExam != null)
			{
				throw new EspAlreadyExistsException( "Es exisitiert bereits eine Pruefung mit dem Pruefungscode '"
						+ exam.getCode() + "'!" );
			}

			// In Datenbank persistieren
			m_em.persist( exam );
			m_em.flush();

			BasicLogger.logInfo( this, "Pr�fung [" + exam.toString() + "] erfolgreich hinzugef�gt!" );

			// Persistierte Pruefung zurueckgeben
			return exam;
		}
		catch (EntityExistsException eex)
		{
			BasicLogger.logError( this, eex.getMessage() );
			throw new EspAlreadyExistsException( "Pr�fung existiert bereits!" );
		}
		catch (PersistenceException pe)
		{
			BasicLogger.logError( this, pe.getMessage() );
			throw new EspPersistenceException( "Pr�fung konnte nicht gespeichert werden!" );
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.esp.internal.bean.database.IEspExamDatabaseLocal#readExamByPK(java.lang.String,
	 *      javax.persistence.LockModeType)
	 */
	@Override
	public EspEntityExam readExamByPK( String examCode, LockModeType lockModeTyp )
	{
		try
		{
			// Eingangsparameter validieren -> Bei Fehler wird Exception geworfen
			if (examCode == null || examCode.isEmpty())
			{
				throw new EspValidationException( "Ungueltiger Pr�fungscode: '" + examCode + "'!" );
			}

			EspEntityExam exam = null;

			if (lockModeTyp != null)
			{
				// Pruefung anhand des Pruefungscodes (PK) auslesen (LOCKED)
				exam = m_em.find( EspEntityExam.class, examCode, lockModeTyp );
			}
			else
			{
				// Pruefung anhand des Pruefungscodes (PK) auslesen
				exam = m_em.find( EspEntityExam.class, examCode );
			}

			// Pruefung nicht vorhanden -> Exception werfen
			if (exam == null)
			{
				throw new EspNotExistsException( "Pr�fung mit dem Pr�fungscode '" + examCode + "' existiert nicht!" );
			}

			BasicLogger.logInfo( this, "Pr�fung [" + exam.toString() + "] erfolgreich gelesen!" );

			// Pruefung zurueckgeben
			return exam;
		}
		catch (PersistenceException pe)
		{
			BasicLogger.logError( this, pe.getMessage() );
			throw new EspPersistenceException( "Pr�fung konnte nicht ausgelesen werden!" );
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.esp.internal.bean.database.IEspExamDatabaseLocal#readAllExams()
	 */
	@Override
	public List<EspEntityExam> readAllExams()
	{
		try
		{
			// Vorhandenen Pruefungen anhand Query auslesen
			List<EspEntityExam> examList = m_em.createQuery( "SELECT e FROM EspEntityExam e", EspEntityExam.class )
					.getResultList();

			BasicLogger.logInfo( this, "Anzahl gefundener Pr�fungen: " + examList.size() + "!" );

			for (EspEntityExam exam : examList)
			{
				exam.getExaminer();
				for (EspEntityExamRoomAssignment examRoomAssignment : exam.getRoomAssignments())
				{
					examRoomAssignment.getRoom();
					examRoomAssignment.getExam();
					for (EspEntityUser user : examRoomAssignment.getSupervisors())
					{
						user.getRoles();
						user.getExams();
						user.getRoomSupervisions();
						user.getTimeSlotSupervisions();
					}
				}
			}

			// Gefundene Pruefungen zurueckgeben
			return examList;
		}
		catch (PersistenceException pe)
		{
			BasicLogger.logError( this, pe.getMessage() );
			throw new EspPersistenceException( "Pr�fungen konnten nicht ausgelesen werden!" );
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.esp.internal.bean.database.IEspExamDatabaseLocal#updateExamWithoutReferenceUpdate(edu.hm.esp.internal.object.entity.EspEntityExam)
	 */
	@Override
	public EspEntityExam updateExamWithoutReferenceUpdate( EspEntityExam exam )
	{
		try
		{
			if (exam == null)
			{
				throw new EspValidationException( "Ung�ltige Pr�fung: " + null );
			}

			// Eingangsparameter validieren -> Bei Fehler wird Exception geworfen
			exam.validate();

			// Pruefen ob der zu aktualisierende Pruefung mit dem angegebenen Pruefungscode exisitert
			EspEntityExam existingExam = m_em.find( EspEntityExam.class, exam.getCode(), LockModeType.PESSIMISTIC_WRITE );

			// Pruefung existiert nicht -> Exception werfen und Verarbeitung abbrechen
			if (existingExam == null)
			{
				throw new EspNotExistsException( "Pr�fung mit dem Pr�fungscode '" + exam.getCode() + "' exisitert nicht!" );
			}

			// Referenzen vom existierenden Objekt holen
			exam.setRoomAssignments( existingExam.getRoomAssignments() );
			exam.setExaminer( existingExam.getExaminer() );

			// Set mit allen Aufsichtspersonen
			Set<EspEntityUser> currentSupervisors = new HashSet<EspEntityUser>();

			for (EspEntityExamRoomAssignment assignment : exam.getRoomAssignments())
			{
				// Gefundene Aufsichtspersonen dem Set hinzufuegen
				currentSupervisors.addAll( assignment.getSupervisors() );
			}

			// Pruefen ob die Anzahl der benoetigten Aufsichtspersonen groesser oder gleich der Anzahl der aktuell
			// eingetragenen Aufsichtspersonen ist
			if (exam.getNumSupervisors() == null || currentSupervisors.size() > exam.getNumSupervisors())
			{
				throw new EspValidationException( "Anzahl der ben�tigten Aufsichtspersonen: '" + exam.getNumSupervisors()
						+ "' f�r die Pr�fung darf nicht geringer sein als die aktuell eingetragenen Aufsichtspersonen: '"
						+ currentSupervisors.size() + "'!" );
			}

			// Aenderungen persistieren
			exam = m_em.merge( exam );
			m_em.flush();

			BasicLogger.logInfo( this, "Pr�fung [" + exam.toString() + "] erfolgreich aktualisiert!" );

			// Persistierte Pruefung zurueckgeben
			return exam;
		}
		catch (PersistenceException pe)
		{
			BasicLogger.logError( this, pe.getMessage() );
			throw new EspPersistenceException( "Pr�fung konnte nicht aktualisiert werden!" );
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.esp.internal.bean.database.IEspExamDatabaseLocal#deleteExam(java.lang.String)
	 */
	@Override
	public void deleteExam( String examCode )
	{
		try
		{
			// Eingangsparameter validieren -> Bei Fehler wird Exception geworfen
			if (examCode == null || examCode.isEmpty())
			{
				throw new EspValidationException( "Ungueltiger Pr�fungscode: '" + examCode + "'!" );
			}

			// Pruefung anhand des Pruefungscodes suchen
			EspEntityExam exam = m_em.find( EspEntityExam.class, examCode, LockModeType.PESSIMISTIC_WRITE );

			// Pruefung existiert nicht -> Exception werfen
			if (exam == null)
			{
				throw new EspNotExistsException( "Pr�fung mit dem Pr�fungscode '" + examCode + "' exisitert nicht!" );
			}

			// Loeschen
			m_em.remove( exam );
			m_em.flush();

			BasicLogger.logInfo( this, "Pr�fung [" + exam.toString() + "] erfolgreich gel�scht!" );
		}
		catch (PersistenceException pe)
		{
			BasicLogger.logError( this, pe.getMessage() );
			throw new EspPersistenceException( "Pr�fung konnte nicht gel�scht werden!" );
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.esp.internal.bean.database.IEspExamDatabaseLocal#deleteAllExams()
	 */
	@Override
	public void deleteAllExams()
	{
		try
		{
			// Alle Benutzer (bis auf "admin") loeschen
			int num = m_em.createQuery( "DELETE FROM EspEntityExam e" ).executeUpdate();
			m_em.flush();

			BasicLogger.logInfo( this, "Es wurden alle Pr�fungen erfolgreich gel�scht! [Anzahl=" + num + "]" );
		}
		catch (PersistenceException pe)
		{
			BasicLogger.logError( this, pe.getMessage() );
			throw new EspPersistenceException( "Pr�fungen konnten nicht gel�scht werden!" );
		}
	}
}
