package edu.hm.esp.internal.converter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;

import edu.hm.basic.logging.BasicLogger;
import edu.hm.esp.api.object.ressource.EspExam;
import edu.hm.esp.api.object.ressource.EspExamDate;
import edu.hm.esp.api.object.ressource.EspExamList;
import edu.hm.esp.api.object.ressource.EspExaminer;
import edu.hm.esp.api.object.ressource.EspSupervisor;
import edu.hm.esp.internal.object.entity.EspEntityExam;
import edu.hm.esp.internal.object.entity.EspEntityExamRoomAssignment;
import edu.hm.esp.internal.object.entity.EspEntityUser;

/**
 * Konverter-Klasse fuer Pruefungs-Objekte, um zwischen dem Service-Datenmodell und dem Entitaeten-Datenmodell zu
 * konvertieren.
 * 
 * @author Stefan Wörner
 */
public final class EspExamConverter
{

	private EspExamConverter()
	{

	}

	/**
	 * Konvertiert das Entity-Pruefungs-Objekt in ein Pruefungs-Objekt im Service-Datenmodell.
	 * 
	 * @param eExam
	 *            Entity-Pruefungs-Objekt
	 * @return Pruefungs-Objekt aus dem Service-Datenmodell
	 */
	public static EspExam convertEntityToServiceExam( EspEntityExam eExam )
	{
		if (eExam == null)
		{
			return null;
		}

		EspExaminer examiner = new EspExaminer();
		examiner.setLoginName( eExam.getExaminer().getLoginName() );
		examiner.setDisplayName( eExam.getExaminer().getDisplayName() );

		EspExam exam = new EspExam();
		exam.setCode( eExam.getCode() );
		exam.setName( eExam.getName() );
		exam.setNumSupervisors( eExam.getNumSupervisors() );
		exam.setExaminer( examiner );

		// Set mit allen Aufsichtspersonen
		Set<EspEntityUser> eSupervisors = new HashSet<EspEntityUser>();

		for (EspEntityExamRoomAssignment assignment : eExam.getRoomAssignments())
		{
			// Gefundene Aufsichtspersonen dem Set hinzufuegen
			eSupervisors.addAll( assignment.getSupervisors() );
		}

		for (EspEntityUser eSupervisor : eSupervisors)
		{
			EspSupervisor supervisor = new EspSupervisor();
			supervisor.setLoginName( eSupervisor.getLoginName() );
			supervisor.setDisplayName( eSupervisor.getDisplayName() );
			supervisor.setNumSupervisions( eSupervisor.getNumSupervisions() );

			exam.addSupervisor( supervisor );
		}

		return exam;
	}

	/**
	 * Konvertiert das eine Liste von Entity-Zeitslot-Objekten in eine Liste von Zeitslot-Objekten im
	 * Service-Datenmodell.
	 * 
	 * @param eExamList
	 *            Liste von Entity-Zeitslot-Objekten
	 * @param simple
	 *            Flag: true - Es werden keine Raumzuordnungen aufgeloest; false - Es werden alle Referenzen aufgeloest
	 * @return Liste von Zeitslot-Objekten aus dem Service-Datenmodell
	 */
	public static EspExamList convertEntityToServiceExamList( List<EspEntityExam> eExamList, boolean simple )
	{
		if (eExamList == null || eExamList.isEmpty())
		{
			return null;
		}

		if (simple)
		{
			EspExamList examList = new EspExamList();

			for (EspEntityExam eExam : eExamList)
			{
				examList.addExam( convertEntityToServiceExam( eExam ) );
			}

			return examList;
		}
		else
		{
			return convertEntityToServiceExamComplete( eExamList );
		}
	}

	/**
	 * Konvertiert eine Liste von Entity-Pruefungs-Objekten in eine Liste von Pruefungs-Objekten im Service-Datenmodell.
	 * 
	 * @param eExamList
	 *            Liste von Entity-Pruefungs-Objekten
	 * @return Liste von Pruefungs-Objekten aus dem Service-Datenmodell
	 */
	private static EspExamList convertEntityToServiceExamComplete( List<EspEntityExam> eExamList )
	{
		if (eExamList == null || eExamList.isEmpty())
		{
			return null;
		}

		EspExamList examList = new EspExamList();

		for (EspEntityExam eExam : eExamList)
		{
			EspExaminer examiner = new EspExaminer();
			examiner.setLoginName( eExam.getExaminer().getLoginName() );
			examiner.setDisplayName( eExam.getExaminer().getDisplayName() );

			for (EspEntityExamRoomAssignment eExamRoomAssignment : eExam.getRoomAssignments())
			{
				EspExam exam = new EspExam();
				exam.setCode( eExam.getCode() );
				exam.setName( eExam.getName() );
				exam.setNumSupervisors( eExam.getNumSupervisors() );
				exam.setExaminer( examiner );

				EspExamDate examDate = new EspExamDate();
				examDate.setStartDate( eExamRoomAssignment.getStartDate() );
				examDate.setEndDate( eExamRoomAssignment.getEndDate() );

				exam.setRoom( eExamRoomAssignment.getRoom().getName() );
				exam.setExamDate( examDate );

				for (EspEntityUser user : eExamRoomAssignment.getSupervisors())
				{
					EspSupervisor supervisor = new EspSupervisor();
					supervisor.setLoginName( user.getLoginName() );
					supervisor.setDisplayName( user.getDisplayName() );
					supervisor.setNumSupervisions( user.getNumSupervisions() );

					exam.addSupervisor( supervisor );
				}

				examList.addExam( exam );
			}
		}

		return examList;
	}

	/**
	 * Konvertiert ein Pruefung-Objekt aus dem Service-Datenmodell in ein Entity-Pruefung-Objekt.
	 * 
	 * @param exam
	 *            Pruefung-Objekt aus dem Service-Datenmodell
	 * @return Entity-Pruefung-Objekt
	 */
	public static EspEntityExam convertServiceToEntityExam( EspExam exam )
	{
		try
		{
			if (exam == null)
			{
				return null;
			}

			EspEntityExam eExam = new EspEntityExam();
			eExam.setCode( exam.getCode() );
			eExam.setName( exam.getName() );
			eExam.setNumSupervisors( exam.getNumSupervisors() );

			if (exam.getExaminer() != null)
			{
				EspEntityUser examiner = new EspEntityUser();
				examiner.setLoginName( exam.getExaminer().getLoginName() );
				examiner.setDisplayName( exam.getExaminer().getDisplayName() );

				eExam.setExaminer( examiner );
			}

			return eExam;
		}
		catch (Exception e)
		{
			BasicLogger.logError( EspExamConverter.class.getName(), e.getMessage() );
			throw new WebApplicationException( Status.BAD_REQUEST );
		}
	}
}
