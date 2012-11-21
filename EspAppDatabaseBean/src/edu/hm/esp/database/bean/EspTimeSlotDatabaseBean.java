package edu.hm.esp.database.bean;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import org.jboss.ejb3.annotation.LocalBinding;

import edu.hm.basic.logging.BasicLogger;
import edu.hm.esp.internal.bean.AbstractEspBean;
import edu.hm.esp.internal.bean.database.IEspTimeSlotDatabaseLocal;
import edu.hm.esp.internal.exception.EspAlreadyExistsException;
import edu.hm.esp.internal.exception.EspNotExistsException;
import edu.hm.esp.internal.exception.EspPersistenceException;
import edu.hm.esp.internal.exception.EspValidationException;
import edu.hm.esp.internal.object.entity.EspEntityTimeSlot;
import edu.hm.esp.internal.object.entity.EspEntityTimeSlotPk;
import edu.hm.esp.internal.object.entity.EspEntityUser;

/**
 * Bean zur Verwaltung der Zeitslots in der Datenbank.
 * 
 * @author Stefan Wörner
 */
@Stateless
@LocalBinding( jndiBinding = IEspTimeSlotDatabaseLocal.JNDI_BINDING )
public class EspTimeSlotDatabaseBean extends AbstractEspBean implements IEspTimeSlotDatabaseLocal
{

	@PersistenceContext( unitName = "EspManager" )
	private EntityManager m_em;

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.esp.internal.bean.database.IEspTimeSlotDatabaseLocal#insertTimeSlot(edu.hm.esp.internal.object.entity.EspEntityTimeSlot)
	 */
	@Override
	public EspEntityTimeSlot insertTimeSlot( EspEntityTimeSlot timeSlot )
	{
		try
		{
			if (timeSlot == null)
			{
				throw new EspValidationException( "Ung�ltiger Zeitslot: " + null );
			}

			// Eingangsparameter validieren -> Bei Fehler wird Exception geworfen
			timeSlot.validate();

			// Pruefen ob bereits Zeitslot mit dem angegebenen Zeitraum exisitert
			EspEntityTimeSlot existingTimeSlot = m_em.find( EspEntityTimeSlot.class, timeSlot.getPk() );

			// Zeitraum bereits vergeben -> Exception werfen
			if (existingTimeSlot != null)
			{
				throw new EspAlreadyExistsException( "Es exisitiert bereits ein Zeitslot mit dem Zeitraum ["
						+ timeSlot.getPk().toString() + "]!" );
			}

			// In Datenbank persistieren
			m_em.persist( timeSlot );
			m_em.flush();

			BasicLogger.logInfo( this, "Zeitslot [" + timeSlot.toString() + "] erfolgreich hinzugef�gt!" );

			// Persistierten Zeitslot zurueckgeben
			return timeSlot;
		}
		catch (EntityExistsException eex)
		{
			BasicLogger.logError( this, eex.getMessage() );
			throw new EspAlreadyExistsException( "Zeitslot existiert bereits!" );
		}
		catch (PersistenceException pe)
		{
			BasicLogger.logError( this, pe.getMessage() );
			throw new EspPersistenceException( "Zeitslot konnte nicht gespeichert werden!" );
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.esp.internal.bean.database.IEspTimeSlotDatabaseLocal#readTimeSlotByPK(edu.hm.esp.internal.object.entity.EspEntityTimeSlotPk,
	 *      javax.persistence.LockModeType)
	 */
	@Override
	public EspEntityTimeSlot readTimeSlotByPK( EspEntityTimeSlotPk pk, LockModeType lockModeType )
	{
		try
		{
			// Eingangsparameter validieren -> Bei Fehler wird Exception geworfen
			if (pk == null)
			{
				throw new EspValidationException( "Ungueltiger Zeitraum: '" + pk + "'!" );
			}

			// Primaerschluessel validieren
			pk.validate();

			EspEntityTimeSlot timeSlot = null;

			if (lockModeType != null)
			{
				// Zeitslot anhand des Zeitraums (PK) auslesen (LOCKED)
				timeSlot = m_em.find( EspEntityTimeSlot.class, pk, lockModeType );
			}
			else
			{
				// Zeitslot anhand des Zeitraums (PK) auslesen
				timeSlot = m_em.find( EspEntityTimeSlot.class, pk );
			}

			// Zeitslot nicht vorhanden -> Exception werfen
			if (timeSlot == null)
			{
				throw new EspNotExistsException( "Zeitslot mit dem Zeitraum [" + pk.toString() + "] existiert nicht!" );
			}

			BasicLogger.logInfo( this, "Zeitslot [" + timeSlot.toString() + "] erfolgreich gelesen!" );

			// Zeitslot zurueckgeben
			return timeSlot;
		}
		catch (PersistenceException pe)
		{
			BasicLogger.logError( this, pe.getMessage() );
			throw new EspPersistenceException( "Zeitslot konnte nicht ausgelesen werden!" );
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.esp.internal.bean.database.IEspTimeSlotDatabaseLocal#readAllTimeSlots()
	 */
	@Override
	public List<EspEntityTimeSlot> readAllTimeSlots()
	{
		try
		{
			// Vorhandenen Zeitslots anhand Query auslesen
			List<EspEntityTimeSlot> timeSlotList = m_em.createQuery( "SELECT ts FROM EspEntityTimeSlot ts",
					EspEntityTimeSlot.class ).getResultList();

			BasicLogger.logInfo( this, "Anzahl gefundener Zeitslots: " + timeSlotList.size() + "!" );

			for (EspEntityTimeSlot timeSlot : timeSlotList)
			{
				timeSlot.getPk();

				for (EspEntityUser user : timeSlot.getSupervisors())
				{
					user.getRoles();
					user.getExams();
					user.getRoomSupervisions();
					user.getTimeSlotSupervisions();
				}
			}

			// Gefundene Zeitslots zurueckgeben
			return timeSlotList;
		}
		catch (PersistenceException pe)
		{
			BasicLogger.logError( this, pe.getMessage() );
			throw new EspPersistenceException( "Zeitslots konnten nicht ausgelesen werden!" );
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.esp.internal.bean.database.IEspTimeSlotDatabaseLocal
	 *      #updateTimeSlotWithoutReferenceUpdate(edu.hm.esp.internal.object.entity.EspEntityTimeSlot)
	 */
	@Override
	public EspEntityTimeSlot updateTimeSlotWithoutReferenceUpdate( EspEntityTimeSlot timeSlot )
	{
		try
		{
			if (timeSlot == null)
			{
				throw new EspValidationException( "Ung�ltiger Zeitslot: " + null );
			}

			// Eingangsparameter validieren -> Bei Fehler wird Exception geworfen
			timeSlot.validate();

			// Pruefen ob der zu aktualisierende Zeitslot mit dem angegebenen Zeitraum exisitert
			EspEntityTimeSlot existingTimeSlot = m_em.find( EspEntityTimeSlot.class, timeSlot.getPk(),
					LockModeType.PESSIMISTIC_WRITE );

			// Zeitslot existiert nicht -> Exception werfen und Verarbeitung abbrechen
			if (existingTimeSlot == null)
			{
				throw new EspNotExistsException( "Zeitslot mit dem Zeitraum [" + timeSlot.getPk().toString()
						+ "] exisitert nicht!" );
			}

			// Referenzen vom existierenden Objekt holen
			timeSlot.setSupervisors( existingTimeSlot.getSupervisors() );

			// Pruefen ob die Anzahl der benoetigten Aufsichtspersonen groesser oder gleich der Anzahl der aktuell
			// eingetragenen Aufsichtspersonen ist
			if (timeSlot.getNumSupervisors() == null || timeSlot.getSupervisors().size() > timeSlot.getNumSupervisors())
			{
				throw new EspValidationException( "Anzahl der ben�tigten Aufsichtspersonen: '" + timeSlot.getNumSupervisors()
						+ "' f�r den Zeitslot darf nicht geringer sein als die aktuell eingetragenen Aufsichtspersonen: '"
						+ timeSlot.getSupervisors().size() + "'!" );
			}

			// Aenderungen persistieren
			timeSlot = m_em.merge( timeSlot );
			m_em.flush();

			BasicLogger.logInfo( this, "Zeitslot [" + timeSlot.toString() + "] erfolgreich aktualisiert!" );

			// Persistierten Zeitslot zurueckgeben
			return timeSlot;
		}
		catch (PersistenceException pe)
		{
			BasicLogger.logError( this, pe.getMessage() );
			throw new EspPersistenceException( "Zeitslot konnte nicht aktualisiert werden!" );
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.esp.internal.bean.database.IEspTimeSlotDatabaseLocal#deleteTimeSlot(edu.hm.esp.internal.object.entity.EspEntityTimeSlotPk)
	 */
	@Override
	public void deleteTimeSlot( EspEntityTimeSlotPk pk )
	{
		try
		{
			// Eingangsparameter validieren -> Bei Fehler wird Exception geworfen
			if (pk == null)
			{
				throw new EspValidationException( "Ungueltiger Zeitraum: '" + pk + "'!" );
			}

			// Zeitslot anhand des Zeitraums suchen
			EspEntityTimeSlot timeSlot = m_em.find( EspEntityTimeSlot.class, pk, LockModeType.PESSIMISTIC_WRITE );

			// Zeitslot existiert nicht -> Exception werfen
			if (timeSlot == null)
			{
				throw new EspNotExistsException( "Zeitslot mit dem Zeitraum [" + pk.toString() + "] exisitert nicht!" );
			}

			// Loeschen
			m_em.remove( timeSlot );
			m_em.flush();

			BasicLogger.logInfo( this, "Zeitslot [" + timeSlot.toString() + "] erfolgreich gel�scht!" );
		}
		catch (PersistenceException pe)
		{
			BasicLogger.logError( this, pe.getMessage() );
			throw new EspPersistenceException( "Zeitslot konnte nicht gel�scht werden!" );
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.esp.internal.bean.database.IEspTimeSlotDatabaseLocal#deleteAllTimeSlots()
	 */
	@Override
	public void deleteAllTimeSlots()
	{
		try
		{
			// Alle Benutzer (bis auf "admin") loeschen
			int num = m_em.createQuery( "DELETE FROM EspEntityTimeSlot ts" ).executeUpdate();
			m_em.flush();

			BasicLogger.logInfo( this, "Es wurden alle Zeitslots erfolgreich gel�scht! [Anzahl=" + num + "]" );
		}
		catch (PersistenceException pe)
		{
			BasicLogger.logError( this, pe.getMessage() );
			throw new EspPersistenceException( "Zeitslots konnten nicht gel�scht werden!" );
		}
	}
}
