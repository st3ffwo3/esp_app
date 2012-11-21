package edu.hm.esp.internal.converter;

import java.util.List;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;

import edu.hm.basic.logging.BasicLogger;
import edu.hm.esp.api.object.ressource.EspSupervisor;
import edu.hm.esp.api.object.ressource.EspTimeSlot;
import edu.hm.esp.api.object.ressource.EspTimeSlotDate;
import edu.hm.esp.api.object.ressource.EspTimeSlotList;
import edu.hm.esp.internal.object.entity.EspEntityTimeSlot;
import edu.hm.esp.internal.object.entity.EspEntityTimeSlotPk;
import edu.hm.esp.internal.object.entity.EspEntityUser;

/**
 * Konverter-Klasse fuer Zeitslot-Objekte, um zwischen dem Service-Datenmodell und dem Entitaeten-Datenmodell zu
 * konvertieren.
 * 
 * @author Stefan Wörner
 */
public final class EspTimeSlotConverter
{

	private EspTimeSlotConverter()
	{

	}

	/**
	 * Konvertiert das Entity-Zeitslot-Objekt in ein Zeitslot-Objekt im Service-Datenmodell.
	 * 
	 * @param eTimeSlot
	 *            Entity-Zeitslot-Objekt
	 * @return Zeitslot-Objekt aus dem Service-Datenmodell
	 */
	public static EspTimeSlot convertEntityToServiceTimeSlot( EspEntityTimeSlot eTimeSlot )
	{
		if (eTimeSlot == null)
		{
			return null;
		}

		EspTimeSlotDate timeSlotDate = new EspTimeSlotDate();
		timeSlotDate.setStartDate( eTimeSlot.getPk().getStartDate() );
		timeSlotDate.setEndDate( eTimeSlot.getPk().getEndDate() );

		EspTimeSlot timeSlot = new EspTimeSlot();
		timeSlot.setTimeSlotDate( timeSlotDate );
		timeSlot.setNumSupervisors( eTimeSlot.getNumSupervisors() );

		for (EspEntityUser user : eTimeSlot.getSupervisors())
		{
			EspSupervisor supervisor = new EspSupervisor();
			supervisor.setLoginName( user.getLoginName() );
			supervisor.setDisplayName( user.getDisplayName() );
			supervisor.setNumSupervisions( user.getNumSupervisions() );

			timeSlot.addSupervisor( supervisor );
		}

		return timeSlot;
	}

	/**
	 * Konvertiert das eine Liste von Entity-Zeitslot-Objekten in eine Liste von Zeitslot-Objekten im
	 * Service-Datenmodell.
	 * 
	 * @param eTimeSlotList
	 *            Liste von Entity-Zeitslot-Objekten
	 * @return Liste von Zeitslot-Objekten aus dem Service-Datenmodell
	 */
	public static EspTimeSlotList convertEntityToServiceTimeSlotList( List<EspEntityTimeSlot> eTimeSlotList )
	{
		if (eTimeSlotList == null || eTimeSlotList.isEmpty())
		{
			return null;
		}

		EspTimeSlotList timeSlotList = new EspTimeSlotList();

		for (EspEntityTimeSlot eTimeSlot : eTimeSlotList)
		{
			timeSlotList.addTimeSlot( convertEntityToServiceTimeSlot( eTimeSlot ) );
		}

		return timeSlotList;
	}

	/**
	 * Konvertiert ein Zeitslot-Objekt aus dem Service-Datenmodell in ein Entity-Zeitslot-Objekt.
	 * 
	 * @param timeSlot
	 *            Zeitslot-Objekt aus dem Service-Datenmodell
	 * @return Entity-Zeitslot-Objekt
	 */
	public static EspEntityTimeSlot convertServiceToEntityTimeSlot( EspTimeSlot timeSlot )
	{
		try
		{
			if (timeSlot == null)
			{
				return null;
			}

			EspEntityTimeSlotPk ePk = new EspEntityTimeSlotPk();
			ePk.setStartDate( timeSlot.getTimeSlotDate().getStartDate() );
			ePk.setEndDate( timeSlot.getTimeSlotDate().getEndDate() );

			EspEntityTimeSlot eTimeSlot = new EspEntityTimeSlot();
			eTimeSlot.setPk( ePk );
			eTimeSlot.setNumSupervisors( timeSlot.getNumSupervisors() );

			for (EspSupervisor supervisor : timeSlot.getCurrentSupervisors())
			{
				EspEntityUser user = new EspEntityUser();
				user.setLoginName( supervisor.getLoginName() );
				user.setDisplayName( supervisor.getDisplayName() );
				user.setNumSupervisions( supervisor.getNumSupervisions() );
			}

			return eTimeSlot;
		}
		catch (Exception e)
		{
			BasicLogger.logError( EspTimeSlotConverter.class.getName(), e.getMessage() );
			throw new WebApplicationException( Status.BAD_REQUEST );
		}
	}
}
