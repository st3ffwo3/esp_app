package edu.hm.esp.internal.converter;

import java.util.List;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;

import edu.hm.basic.logging.BasicLogger;
import edu.hm.esp.api.object.ressource.EspRole;
import edu.hm.esp.api.object.ressource.EspUser;
import edu.hm.esp.api.object.ressource.EspUserList;
import edu.hm.esp.internal.object.entity.EspEntityRole;
import edu.hm.esp.internal.object.entity.EspEntityUser;

/**
 * Konverter-Klasse fuer Benutzer-Objekte, um zwischen dem Service-Datenmodell und dem Entitaeten-Datenmodell zu
 * konvertieren.
 * 
 * @author Stefan Wörner
 */
public final class EspUserConverter
{

	private EspUserConverter()
	{

	}

	/**
	 * Konvertiert das Entity-Benutzer-Objekt in ein Benutzer-Objekt im Service-Datenmodell.
	 * 
	 * @param eUser
	 *            Entity-Benutzer-Objekt
	 * @return Benutzer-Objekt aus dem Service-Datenmodell
	 */
	public static EspUser convertEntityToServiceUser( EspEntityUser eUser )
	{
		if (eUser == null)
		{
			return null;
		}

		EspUser user = new EspUser();
		user.setLoginName( eUser.getLoginName() );
		user.setDisplayName( eUser.getDisplayName() );
		user.setMailAddress( eUser.getMailAddress() );
		user.setNumSupervisions( eUser.getNumSupervisions() );
		user.setActivated( eUser.getPassword() != null && !eUser.getPassword().isEmpty() );

		for (EspEntityRole eRole : eUser.getRoles())
		{
			EspRole role = new EspRole();
			role.setName( eRole.getName() );
			user.addRole( role );
		}

		return user;
	}

	/**
	 * Konvertiert das eine Liste von Entity-Benutzer-Objekten in eine Liste von Benutzer-Objekten im
	 * Service-Datenmodell.
	 * 
	 * @param eUserList
	 *            Liste von Entity-Benutzer-Objekten
	 * @return Liste von Benutzer-Objekten aus dem Service-Datenmodell
	 */
	public static EspUserList convertEntityToServiceUserList( List<EspEntityUser> eUserList )
	{
		if (eUserList == null || eUserList.isEmpty())
		{
			return null;
		}

		EspUserList userList = new EspUserList();

		for (EspEntityUser eUser : eUserList)
		{
			userList.addUser( convertEntityToServiceUser( eUser ) );
		}

		return userList;
	}

	/**
	 * Konvertiert ein Benutzer-Objekt aus dem Service-Datenmodell in ein Entity-Benutzer-Objekt.
	 * 
	 * @param user
	 *            Benutzer-Objekt aus dem Service-Datenmodell
	 * @return Entity-Benutzer-Objekt
	 */
	public static EspEntityUser convertServiceToEntityUser( EspUser user )
	{
		try
		{
			if (user == null)
			{
				return null;
			}

			EspEntityUser eUser = new EspEntityUser();
			eUser.setLoginName( user.getLoginName() );
			eUser.setDisplayName( user.getDisplayName() );
			eUser.setMailAddress( user.getMailAddress() );
			eUser.setNumSupervisions( user.getNumSupervisions() );

			for (EspRole role : user.getRoles())
			{
				EspEntityRole eRole = new EspEntityRole();
				eRole.setName( role.getName() );
				eUser.addRole( eRole );
			}

			return eUser;
		}
		catch (Exception e)
		{
			BasicLogger.logError( EspUserConverter.class.getName(), e.getMessage() );
			throw new WebApplicationException( Status.BAD_REQUEST );
		}
	}
}
