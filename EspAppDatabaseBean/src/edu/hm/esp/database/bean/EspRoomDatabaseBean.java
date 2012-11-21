package edu.hm.esp.database.bean;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import org.jboss.ejb3.annotation.LocalBinding;

import edu.hm.basic.logging.BasicLogger;
import edu.hm.esp.internal.bean.AbstractEspBean;
import edu.hm.esp.internal.bean.database.IEspRoomDatabaseLocal;
import edu.hm.esp.internal.exception.EspPersistenceException;

/**
 * Bean zur Verwaltung der User in der Datenbank.
 * 
 * @author Stefan Wörner
 */
@Stateless
@LocalBinding( jndiBinding = IEspRoomDatabaseLocal.JNDI_BINDING )
public class EspRoomDatabaseBean extends AbstractEspBean implements IEspRoomDatabaseLocal
{

	@PersistenceContext( unitName = "EspManager" )
	private EntityManager m_em;

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.esp.internal.bean.database.IEspRoomDatabaseLocal#deleteAllRooms()
	 */
	@Override
	public void deleteAllRooms()
	{
		try
		{
			// Alle Benutzer (bis auf "admin") loeschen
			int num = m_em.createQuery( "DELETE FROM EspEntityRoom r" ).executeUpdate();
			m_em.flush();

			BasicLogger.logInfo( this, "Es wurden alle R�ume erfolgreich gel�scht! [Anzahl=" + num + "]" );
		}
		catch (PersistenceException pe)
		{
			BasicLogger.logError( this, pe.getMessage() );
			throw new EspPersistenceException( "R�ume konnten nicht gel�scht werden!" );
		}
	}
}
