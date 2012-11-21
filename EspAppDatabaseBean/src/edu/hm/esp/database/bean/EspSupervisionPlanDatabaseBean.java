package edu.hm.esp.database.bean;

import java.util.HashSet;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import org.jboss.ejb3.annotation.LocalBinding;

import edu.hm.basic.logging.BasicLogger;
import edu.hm.esp.internal.bean.AbstractEspBean;
import edu.hm.esp.internal.bean.database.IEspExamDatabaseLocal;
import edu.hm.esp.internal.bean.database.IEspSupervisionPlanDatabaseLocal;
import edu.hm.esp.internal.bean.database.IEspTimeSlotDatabaseLocal;
import edu.hm.esp.internal.bean.database.IEspUserDatabaseLocal;
import edu.hm.esp.internal.exception.EspAlreadyRegisteredException;
import edu.hm.esp.internal.exception.EspNoSupervisorsRequiredException;
import edu.hm.esp.internal.exception.EspNotRegisteredException;
import edu.hm.esp.internal.exception.EspPersistenceException;
import edu.hm.esp.internal.exception.EspUnregistrationNotAllowedException;
import edu.hm.esp.internal.object.entity.EspEntityExam;
import edu.hm.esp.internal.object.entity.EspEntityExamRoomAssignment;
import edu.hm.esp.internal.object.entity.EspEntityTimeSlot;
import edu.hm.esp.internal.object.entity.EspEntityTimeSlotPk;
import edu.hm.esp.internal.object.entity.EspEntityUser;

/**
 * Bean zur Verwaltung des Aufsichtsplans in der Datenbank.
 * 
 * @author Stefan Wörner
 */
@Stateless
@LocalBinding( jndiBinding = IEspSupervisionPlanDatabaseLocal.JNDI_BINDING )
public class EspSupervisionPlanDatabaseBean extends AbstractEspBean implements IEspSupervisionPlanDatabaseLocal
{

	@PersistenceContext( unitName = "EspManager" )
	private EntityManager m_em;

	@EJB
	private IEspExamDatabaseLocal m_databaseExamBean;

	@EJB
	private IEspUserDatabaseLocal m_databaseUserBean;

	@EJB
	private IEspTimeSlotDatabaseLocal m_databaseTimeSlotBean;

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.esp.internal.bean.database.IEspSupervisionPlanDatabaseLocal#addSupervisorForExam(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public EspEntityExam addSupervisorForExam( String examCode, String loginName )
	{
		// Benutzer anfordern
		EspEntityUser user = m_databaseUserBean.readUserByPK( loginName );

		// Pruefung gelocked anfordern, um konkurierende Zugriffen zu behandeln
		EspEntityExam exam = m_databaseExamBean.readExamByPK( examCode, LockModeType.PESSIMISTIC_WRITE );

		try
		{
			// (Anzahl Aufsichtspersonen aktuell | index)
			SortedMap<Integer, EspEntityExamRoomAssignment> supervisorMap = new TreeMap<Integer, EspEntityExamRoomAssignment>();
			// Set mit allen Aufsichtspersonen
			Set<EspEntityUser> currentSupervisors = new HashSet<EspEntityUser>();

			// Ueber die Pruefungs-Raum-Zuordnungen iterieren und pruefen ob der Nutzer bereits als Aufsicht eingetragen
			// ist
			for (EspEntityExamRoomAssignment assignment : exam.getRoomAssignments())
			{
				// Wenn bereits eingetragen, dann Exception werfen und Verarbeitung abbrechen
				if (assignment.getSupervisors().contains( user ))
				{
					throw new EspAlreadyRegisteredException( "Benutzer '" + user.getLoginName()
							+ "' ist bereits als Aufsichtsperson f�r die Pr�fung mit dem Pr�fungscode '" + exam.getCode()
							+ "' eingetragen!" );
				}

				// Gefundene Aufsichtspersonen dem Set hinzufuegen
				currentSupervisors.addAll( assignment.getSupervisors() );

				// Anzahl Aufsichten pro Zuordnung fuer spaeter zwischenspeichern (sortiert)
				supervisorMap.put( assignment.getSupervisors().size(), assignment );
			}

			// Benutzer ist noch nicht eingetragen; Nun noch pruefen ob noch Koningente frei sind
			if (exam.getNumSupervisors() == null || currentSupervisors.size() >= exam.getNumSupervisors())
			{
				throw new EspNoSupervisorsRequiredException( "F�r die Pr�fung mit dem Pr�fungscode '" + exam.getCode()
						+ "' werden keine Aufsichtspersonen mehr ben�tigt!" );
			}

			// Benutzer kann als Aufsichtsperson eingetragen werden; Dazu muss der ideale (naechste freie) Raum gefunden
			// werden, das heisst der mit den aktuell wenigsten Aufsichtspersonen
			supervisorMap.get( supervisorMap.firstKey() ).addSupervisor( user );

			// Pruefung abschlie�end zusammenfuehren
			exam = m_em.merge( exam );
			// In Datenbank persistieren
			m_em.flush();

			// Pruefung zurueckgeben
			return exam;
		}
		catch (PersistenceException pe)
		{
			BasicLogger.logError( this, pe.getMessage() );
			throw new EspPersistenceException( "(Pr�fung) Eintragung konnte nicht abgeschlossen werden!" );
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.esp.internal.bean.database.IEspSupervisionPlanDatabaseLocal#removeSupervisorFromExam(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public EspEntityExam removeSupervisorFromExam( String examCode, String loginName )
	{
		// Benutzer anfordern
		EspEntityUser user = m_databaseUserBean.readUserByPK( loginName );

		// Pruefung gelocked anfordern, um konkurierende Zugriffen zu behandeln
		EspEntityExam exam = m_databaseExamBean.readExamByPK( examCode, LockModeType.PESSIMISTIC_WRITE );

		try
		{
			// Wenn Benutzer Pruefer der Pruefung ist, dann Exception werfen (Pruefer koennen sich nicht austragen)
			if (exam.getExaminer().equals( user ))
			{
				throw new EspUnregistrationNotAllowedException( "Benutzer '" + user.getLoginName()
						+ "' ist Pr�fungssteller der Pr�fung mit dem Pr�fungscode '" + exam.getCode()
						+ "' und kann daher nicht ausgetragen werden!" );
			}

			boolean registered = false;

			// Ueber die Pruefungs-Raum-Zuordnungen iterieren und pruefen ob der Nutzer als Aufsicht eingetragen ist
			for (EspEntityExamRoomAssignment assignment : exam.getRoomAssignments())
			{
				// Wenn eingetragen, dann austragen und for-Schleife verlassen
				if (assignment.getSupervisors().contains( user ))
				{
					assignment.getSupervisors().remove( user );
					registered = true;
					break;
				}
			}

			if (!registered)
			{
				throw new EspNotRegisteredException( "Benutzer '" + user.getLoginName()
						+ "' ist f�r die Pr�fung mit dem Pr�fungscode '" + exam.getCode()
						+ "' nicht als Aufsichtsperson eingetragen!" );
			}

			// Pruefung abschlie�end zusammenfuehren
			exam = m_em.merge( exam );
			// In Datenbank persistieren
			m_em.flush();

			// Pruefung zurueckgeben
			return exam;
		}
		catch (PersistenceException pe)
		{
			BasicLogger.logError( this, pe.getMessage() );
			throw new EspPersistenceException( "(Pr�fung) Austragung konnte nicht abgeschlossen werden!" );
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.esp.internal.bean.database.IEspSupervisionPlanDatabaseLocal
	 *      #addSupervisorForTimeSlot(edu.hm.esp.internal.object.entity.EspEntityTimeSlotPk, java.lang.String)
	 */
	@Override
	public EspEntityTimeSlot addSupervisorForTimeSlot( EspEntityTimeSlotPk timeSlotPK, String loginName )
	{
		// Benutzer anfordern
		EspEntityUser user = m_databaseUserBean.readUserByPK( loginName );

		// Zeitslot gelocked anfordern, um konkurierende Zugriffen zu behandeln
		EspEntityTimeSlot timeSlot = m_databaseTimeSlotBean.readTimeSlotByPK( timeSlotPK, LockModeType.PESSIMISTIC_WRITE );

		try
		{
			// UPruefen ob der Nutzer bereits als Aufsicht fuer den Zeitslot eingetragen ist
			if (timeSlot.getSupervisors().contains( user ))
			{
				throw new EspAlreadyRegisteredException( "Benutzer '" + user.getLoginName()
						+ "' ist bereits als Aufsichtsperson f�r den Zeitslot [" + timeSlot.getPk().toString() + "] eingetragen!" );
			}

			// Benutzer ist noch nicht eingetragen; Nun noch pruefen ob noch Koningente frei sind
			if (timeSlot.getNumSupervisors() == null || timeSlot.getSupervisors().size() >= timeSlot.getNumSupervisors())
			{
				throw new EspNoSupervisorsRequiredException( "F�r den Zeitslot [" + timeSlot.getPk().toString()
						+ "] werden keine Aufsichtspersonen mehr ben�tigt!" );
			}

			// Benutzer kann als Aufsichtsperson eingetragen werden
			timeSlot.addSupervisor( user );

			// Zeitslot abschlie�end zusammenfuehren
			timeSlot = m_em.merge( timeSlot );
			// In Datenbank persistieren
			m_em.flush();

			// Zeitslot zurueckgeben
			return timeSlot;
		}
		catch (PersistenceException pe)
		{
			BasicLogger.logError( this, pe.getMessage() );
			throw new EspPersistenceException( "(Zeitslot) Eintragung konnte nicht abgeschlossen werden!" );
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.esp.internal.bean.database.IEspSupervisionPlanDatabaseLocal
	 *      #removeSupervisorFromTimeSlot(edu.hm.esp.internal.object.entity.EspEntityTimeSlotPk, java.lang.String)
	 */
	@Override
	public EspEntityTimeSlot removeSupervisorFromTimeSlot( EspEntityTimeSlotPk timeSlotPK, String loginName )
	{
		// Benutzer anfordern
		EspEntityUser user = m_databaseUserBean.readUserByPK( loginName );

		// Zeitslot gelocked anfordern, um konkurierende Zugriffen zu behandeln
		EspEntityTimeSlot timeSlot = m_databaseTimeSlotBean.readTimeSlotByPK( timeSlotPK, LockModeType.PESSIMISTIC_WRITE );

		try
		{
			// Pruefen ob der Nutzer als Aufsicht fuer Zeitslot eingetragen ist
			if (!timeSlot.getSupervisors().contains( user ))
			{
				throw new EspNotRegisteredException( "Benutzer '" + user.getLoginName() + "' ist f�r den Zeitslot ["
						+ timeSlot.getPk().toString() + "] nicht als Aufsichtsperson eingetragen!" );
			}

			// Benutzer eingetragen -> Austragen
			timeSlot.getSupervisors().remove( user );

			// Zeitslot abschlie�end zusammenfuehren
			timeSlot = m_em.merge( timeSlot );
			// In Datenbank persistieren
			m_em.flush();

			// Zeitslot zurueckgeben
			return timeSlot;
		}
		catch (PersistenceException pe)
		{
			BasicLogger.logError( this, pe.getMessage() );
			throw new EspPersistenceException( "(Zeitslot) Austragung konnte nicht abgeschlossen werden!" );
		}
	}
}
