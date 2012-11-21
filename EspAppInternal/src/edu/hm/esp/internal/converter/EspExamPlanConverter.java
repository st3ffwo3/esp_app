package edu.hm.esp.internal.converter;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;

import edu.hm.basic.logging.BasicLogger;
import edu.hm.esp.api.object.ressource.EspExam;
import edu.hm.esp.api.object.ressource.EspExamPlan;
import edu.hm.esp.internal.object.entity.EspEntityExam;
import edu.hm.esp.internal.object.entity.EspEntityExamRoomAssignment;
import edu.hm.esp.internal.object.entity.EspEntityExamRoomAssignmentPk;
import edu.hm.esp.internal.object.entity.EspEntityRoom;
import edu.hm.esp.internal.object.entity.EspEntityUser;

/**
 * Konverter-Klasse fuer Pruefungs-Objekte, um zwischen dem Service-Datenmodell und dem Entitaeten-Datenmodell zu
 * konvertieren.
 * 
 * @author Stefan Wörner
 */
public final class EspExamPlanConverter
{

	private EspExamPlanConverter()
	{

	}

	/**
	 * Konvertiert den Pruefungsplan aus dem Service-Datenmodell in eine Liste von Entity-Pruefungs-Objekten.
	 * 
	 * @param examPlan
	 *            Pruefungsplan aus dem Service-Datenmodell
	 * @return Liste mit Entity-Pruefungs-Objekten.
	 */
	public static List<EspEntityExam> convertServiceToEntityExamPlan( EspExamPlan examPlan )
	{
		List<EspEntityExam> eExamPlan = new ArrayList<EspEntityExam>();

		for (EspExam exam : examPlan.getExams())
		{
			eExamPlan.add( EspExamPlanConverter.convertServiceToEntityExam( exam ) );
		}

		return eExamPlan;
	}

	/**
	 * Konvertierte eine Pruefung aus dem Service-Datenmodell in ein Entity-Pruefungs-Objekt.
	 * 
	 * @param exam
	 *            Pruefungn aus dem Service-Datenmodell
	 * @return Entity-Pruefungs-Objekt
	 */
	public static EspEntityExam convertServiceToEntityExam( EspExam exam )
	{
		try
		{
			if (exam == null)
			{
				return null;
			}

			// Benutzer erzeugen
			EspEntityUser eUser = new EspEntityUser();
			eUser.setLoginName( exam.getExaminer().getLoginName() );
			eUser.setDisplayName( exam.getExaminer().getDisplayName() );

			// Raum erzeugen
			EspEntityRoom eRoom = new EspEntityRoom();
			eRoom.setName( exam.getRoom() );

			// Pruefung erzeugen
			EspEntityExam eExam = new EspEntityExam();
			eExam.setCode( exam.getCode() );
			eExam.setName( exam.getName() );
			eExam.setExaminer( eUser );

			// Zusammengesetzten Primaerschluessel fuer die Pruefungs-Raum-Zuordnung erzeugen
			EspEntityExamRoomAssignmentPk eExamRoomAssignmentPk = new EspEntityExamRoomAssignmentPk();
			eExamRoomAssignmentPk.setExamCode( exam.getCode() );
			eExamRoomAssignmentPk.setRoomName( exam.getRoom() );

			// Pruefungs-Raum-Zuordnung erzeugen
			EspEntityExamRoomAssignment eExamRoomAssignment = new EspEntityExamRoomAssignment();
			eExamRoomAssignment.setPk( eExamRoomAssignmentPk );
			eExamRoomAssignment.setRoom( eRoom );
			eExamRoomAssignment.setExam( eExam );
			eExamRoomAssignment.setStartDate( exam.getExamDate().getStartDate() );
			eExamRoomAssignment.setEndDate( exam.getExamDate().getEndDate() );

			// Der Pruefung die Pruefungs-Raum-Zuordnung hinzufuegen
			eExam.addRoomAssignment( eExamRoomAssignment );

			// Erzeugte Pruefung zurueckgeben
			return eExam;
		}
		catch (Exception e)
		{
			BasicLogger.logError( EspExamPlanConverter.class.getName(), e.getMessage() );
			throw new WebApplicationException( Status.BAD_REQUEST );
		}
	}
}
