package edu.hm.esp.database.bean;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import org.jboss.ejb3.annotation.LocalBinding;

import edu.hm.basic.logging.BasicLogger;
import edu.hm.esp.internal.bean.AbstractEspBean;
import edu.hm.esp.internal.bean.database.IEspUserDatabaseLocal;
import edu.hm.esp.internal.exception.EspAlreadyExistsException;
import edu.hm.esp.internal.exception.EspNotExistsException;
import edu.hm.esp.internal.exception.EspPersistenceException;
import edu.hm.esp.internal.exception.EspValidationException;
import edu.hm.esp.internal.object.entity.EspEntityUser;

/**
 * Bean zur Verwaltung der User in der Datenbank.
 * 
 * @author Stefan Wörner
 */
@Stateless
@LocalBinding( jndiBinding = IEspUserDatabaseLocal.JNDI_BINDING )
public class EspUserDatabaseBean extends AbstractEspBean implements IEspUserDatabaseLocal
{

	@PersistenceContext( unitName = "EspManager" )
	private EntityManager m_em;

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.esp.internal.bean.database.IEspUserDatabaseLocal#insertUser(edu.hm.esp.internal.object.entity.EspEntityUser)
	 */
	@Override
	public EspEntityUser insertUser( EspEntityUser user )
	{
		try
		{
			if (user == null)
			{
				throw new EspValidationException( "Ung�ltiger Benutzer: " + null );
			}

			// Eingangsparameter validieren -> Bei Fehler wird Exception geworfen
			user.validate();

			// Pruefen ob bereits Nutzer mit dem angegebenen LoginName exisitert
			EspEntityUser existingUser = m_em.find( EspEntityUser.class, user.getLoginName() );

			// LoginName bereits vergeben -> Exception werfen
			if (existingUser != null)
			{
				throw new EspAlreadyExistsException( "Es exisitiert bereits ein Nutzer mit dem Benutzernamen '"
						+ user.getLoginName() + "'!" );
			}

			// In Datenbank persistieren
			m_em.persist( user );
			m_em.flush();

			BasicLogger.logInfo( this, "Benutzer [" + user.toString() + "] erfolgreich hinzugef�gt!" );

			// Persistierten Nutzer zurueckgeben
			return user;
		}
		catch (EntityExistsException eex)
		{
			BasicLogger.logError( this, eex.getMessage() );
			throw new EspAlreadyExistsException( "Benutzer existiert bereits!" );
		}
		catch (PersistenceException pe)
		{
			BasicLogger.logError( this, pe.getMessage() );
			throw new EspPersistenceException( "Benutzer konnte nicht gespeichert werden!" );
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.esp.internal.bean.database.IEspUserDatabaseLocal#readUserByPK(java.lang.String)
	 */
	@Override
	public EspEntityUser readUserByPK( String loginName )
	{
		try
		{
			// Eingangsparameter validieren -> Bei Fehler wird Exception geworfen
			if (loginName == null || loginName.isEmpty())
			{
				throw new EspValidationException( "Ungueltiger Benutzername: '" + loginName + "'!" );
			}

			// Nutzer anhand des LoginNamens auslesen
			EspEntityUser user = m_em.find( EspEntityUser.class, loginName );

			// Nutzer nicht vorhanden -> Exception werfen
			if (user == null)
			{
				throw new EspNotExistsException( "Benutzer mit dem Benutzernamen '" + loginName + "' existiert nicht!" );
			}

			BasicLogger.logInfo( this, "Benutzer [" + user.toString() + "] erfolgreich gelesen!" );

			// Benutzer zurueckgeben
			return user;
		}
		catch (PersistenceException pe)
		{
			BasicLogger.logError( this, pe.getMessage() );
			throw new EspPersistenceException( "Benutzer konnte nicht ausgelesen werden!" );
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.esp.internal.bean.database.IEspUserDatabaseLocal#readAllUser()
	 */
	@Override
	public List<EspEntityUser> readAllUser()
	{
		try
		{
			// Vorhandenen Benutzer anhand Query auslesen
			List<EspEntityUser> userList = m_em.createQuery( "SELECT u FROM EspEntityUser u WHERE u.loginName != 'admin'",
					EspEntityUser.class ).getResultList();

			BasicLogger.logInfo( this, "Anzahl gefundener Benutzer: " + userList.size() + "!" );

			// Gefundene Benutzer zurueckgeben
			return userList;
		}
		catch (PersistenceException pe)
		{
			BasicLogger.logError( this, pe.getMessage() );
			throw new EspPersistenceException( "Benutzer konnten nicht ausgelesen werden!" );
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.esp.internal.bean.database.IEspUserDatabaseLocal#updateUserWithoutReferenceUptdate(edu.hm.esp.internal.object.entity.EspEntityUser)
	 */
	@Override
	public EspEntityUser updateUserWithoutReferenceUptdate( EspEntityUser user )
	{
		try
		{
			if (user == null)
			{
				throw new EspValidationException( "Ung�ltiger Benutzer: " + null );
			}

			// Eingangsparameter validieren -> Bei Fehler wird Exception geworfen
			user.validate();

			// Pruefen ob der zu aktualisierende Nutzer unter dem loginName exisitert
			EspEntityUser existingUser = m_em.find( EspEntityUser.class, user.getLoginName() );

			// Nutzer existiert nicht -> Exception werfen und Verarbeitung abbrechen
			if (existingUser == null)
			{
				throw new EspNotExistsException( "Benutzer mit dem Benutzernamen '" + user.getLoginName() + "' exisitert nicht!" );
			}

			// Aenderungen zusammenfuehren
			if (user.getExams() == null || user.getExams().isEmpty())
			{
				user.setExams( existingUser.getExams() );
			}
			if (user.getRoles() == null || user.getRoles().isEmpty())
			{
				user.setRoles( existingUser.getRoles() );
			}
			if (user.getRoomSupervisions() == null || user.getRoomSupervisions().isEmpty())
			{
				user.setRoomSupervisions( existingUser.getRoomSupervisions() );
			}
			if (user.getTimeSlotSupervisions() == null || user.getTimeSlotSupervisions().isEmpty())
			{
				user.setTimeSlotSupervisions( existingUser.getTimeSlotSupervisions() );
			}
			user = m_em.merge( user );

			// Aenderungen persistieren
			m_em.flush();

			BasicLogger.logInfo( this, "Benutzer [" + user.toString() + "] erfolgreich aktualisiert!" );

			// Persistierten Nutzer zurueckgeben
			return user;
		}
		catch (PersistenceException pe)
		{
			BasicLogger.logError( this, pe.getMessage() );
			throw new EspPersistenceException( "Benutzer konnte nicht aktualisiert werden!" );
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.esp.internal.bean.database.IEspUserDatabaseLocal#deleteUser(java.lang.String)
	 */
	@Override
	public void deleteUser( String loginName )
	{
		try
		{
			// Eingangsparameter validieren -> Bei Fehler wird Exception geworfen
			if (loginName == null || loginName.isEmpty())
			{
				throw new EspValidationException( "Ungueltiger Benutzername: '" + loginName + "'!" );
			}

			// Nutzer anhand des LoginNamen suchen
			EspEntityUser user = m_em.find( EspEntityUser.class, loginName );

			// Benutzer existiert nicht -> Exception werfen
			if (user == null)
			{
				throw new EspNotExistsException( "Benutzer mit dem Benutzernamen '" + loginName + "' exisitert nicht!" );
			}

			// Loeschen
			m_em.remove( user );
			m_em.flush();

			BasicLogger.logInfo( this, "Benutzer [" + user.toString() + "] erfolgreich gel�scht!" );
		}
		catch (PersistenceException pe)
		{
			BasicLogger.logError( this, pe.getMessage() );
			throw new EspPersistenceException( "Benutzer konnte nicht gel�scht werden!" );
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.esp.internal.bean.database.IEspUserDatabaseLocal#deleteAllUser()
	 */
	@Override
	public void deleteAllUser()
	{
		try
		{
			// Alle Benutzer (bis auf "admin") loeschen
			int num = m_em.createQuery( "DELETE FROM EspEntityUser u WHERE u.loginName != 'admin'" ).executeUpdate();
			m_em.flush();

			BasicLogger.logInfo( this, "Es wurden alle Benutzer erfolgreich gel�scht! [Anzahl=" + num + "]" );
		}
		catch (PersistenceException pe)
		{
			BasicLogger.logError( this, pe.getMessage() );
			throw new EspPersistenceException( "Benutzer konnten nicht gel�scht werden!" );
		}
	}
}
