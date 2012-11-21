package edu.hm.esp.database.bean;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import org.jboss.ejb3.annotation.LocalBinding;

import edu.hm.basic.logging.BasicLogger;
import edu.hm.esp.internal.bean.AbstractEspBean;
import edu.hm.esp.internal.bean.database.IEspExamPlanDatabaseLocal;
import edu.hm.esp.internal.exception.EspPersistenceException;
import edu.hm.esp.internal.exception.EspValidationException;
import edu.hm.esp.internal.object.entity.EspEntityExam;
import edu.hm.esp.internal.object.entity.EspEntityExamRoomAssignment;
import edu.hm.esp.internal.object.entity.EspEntityExamRoomAssignmentPk;
import edu.hm.esp.internal.object.entity.EspEntityRoom;
import edu.hm.esp.internal.object.entity.EspEntityUser;

/**
 * Bean zur Verwaltung des Pruefungsplans in der Datenbank.
 * 
 * @author Stefan Wörner
 */
@Stateless
@LocalBinding( jndiBinding = IEspExamPlanDatabaseLocal.JNDI_BINDING )
public class EspExamPlanDatabaseBean extends AbstractEspBean implements IEspExamPlanDatabaseLocal
{

	@PersistenceContext( unitName = "EspManager" )
	private EntityManager m_em;

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.esp.internal.bean.database.IEspExamDatabaseLocal#insertOrUpdateExamPlanWithoutReferenceUpdate(java.util.List)
	 */
	@Override
	public List<EspEntityExam> insertOrUpdateExamPlanWithoutReferenceUpdate( List<EspEntityExam> examPlan )
	{
		List<EspEntityExam> examPlanInserted = new ArrayList<EspEntityExam>();

		for (EspEntityExam exam : examPlan)
		{
			examPlanInserted.add( insertOrUpdateExamWithoutReferenceUpdate( exam ) );
		}

		return examPlanInserted;
	}

	/**
	 * Fuegt die uebergebene Pruefung in die Datenbank ein oder aktualisiert entsprechend.
	 * 
	 * @param exam
	 *            Einzufuegende Pruefung
	 * @return Hinzugefuegte Pruefung
	 */
	private EspEntityExam insertOrUpdateExamWithoutReferenceUpdate( EspEntityExam exam )
	{
		try
		{
			if (exam == null)
			{
				throw new EspValidationException( "Ung�ltige Pr�fung: " + null );
			}

			// Eingangsparameter validieren -> Bei Fehler wird Exception geworfen
			exam.validate();

			// Nach Benutzer anhand des loginNamen suchen
			EspEntityUser existingUser = m_em.find( EspEntityUser.class, exam.getExaminer().getLoginName() );

			// Wenn Benutzer noch nicht existiert, dann Benutzer fuer Pruefungssteller anlegen
			if (existingUser == null)
			{
				// Benutzer persistieren
				m_em.persist( exam.getExaminer() );
			}

			// Nach Pruefung anhand des Pruefungscodes suchen
			EspEntityExam existingExam = m_em.find( EspEntityExam.class, exam.getCode(), LockModeType.PESSIMISTIC_WRITE );
			EspEntityUser existingExaminer = null;

			// Wenn Pruefung noch nicht exisitert, dann Pruefung neu anlegen
			if (existingExam == null)
			{
				// Pruefung persisiteren
				m_em.persist( exam );
			}
			else if (existingExam != null && !existingExam.getExaminer().equals( exam.getExaminer() ))
			{
				existingExaminer = existingExam.getExaminer();
			}

			for (EspEntityExamRoomAssignment examRoomAssignment : exam.getRoomAssignments())
			{
				// Pruefer als Aufsichtsperson eintragen
				examRoomAssignment.addSupervisor( exam.getExaminer() );

				// Validieren
				examRoomAssignment.validate();

				// Nach Raum anhand der RaumID suchen
				EspEntityRoom existingRoom = m_em.find( EspEntityRoom.class, examRoomAssignment.getRoom().getName() );

				// Wenn Raum noch nicht exisitert, dann Raum neu anlegen
				if (existingRoom == null)
				{
					// Raum persistieren
					m_em.persist( examRoomAssignment.getRoom() );
				}

				// Nach Pruefungs-Raum-Zuordnung suchen
				EspEntityExamRoomAssignment existingExamRoomAssignment = m_em.find( EspEntityExamRoomAssignment.class,
						new EspEntityExamRoomAssignmentPk( exam.getCode(), examRoomAssignment.getRoom().getName() ) );

				// Wenn Pruefungs-Raum-Zuordnung nicht exisitert, dann Pruefungs-Raum-Zuordnung neu anlegen
				if (existingExamRoomAssignment == null)
				{
					// Pruefungs-Raum-Zuordnung persistieren
					m_em.persist( examRoomAssignment );
				}
				else
				{
					if (existingExaminer != null)
					{
						for (EspEntityExamRoomAssignment innerAssignment : existingExamRoomAssignment.getExam()
								.getRoomAssignments())
						{
							// Bisherigen Pruefer aus der Liste der Aufsichtspersonen entfernen
							innerAssignment.getSupervisors().remove( existingExaminer );
							innerAssignment.addSupervisor( exam.getExaminer() );
						}
					}
					// Neuen Pruefer in die Liste der Aufsichtspersonen aufnehmen
					existingExamRoomAssignment.addSupervisor( exam.getExaminer() );
					// Aenderungen zusammenfuehren
					existingExamRoomAssignment = m_em.merge( existingExamRoomAssignment );
				}
			}

			// Wenn Anzahl Aufsichtspersonen nicht gesetzt ist die exisiterende Anzahl nehmen (kann auch null sein)
			if ((exam.getNumSupervisors() == null || exam.getNumSupervisors() == 0) && existingExam != null)
			{
				exam.setNumSupervisors( existingExam.getNumSupervisors() );
			}

			// Pruefung abschlie�end zusammenfuehren
			exam = m_em.merge( exam );
			// In Datenbank persistieren
			m_em.flush();

			BasicLogger.logInfo( this, "Pr�fung [" + exam.toString() + "] erfolgreich hinzugef�gt bzw. aktualisiert!" );

			// Pruefung zurueckgeben
			return exam;
		}
		catch (PersistenceException pe)
		{
			BasicLogger.logError( this, pe.getMessage() );
			throw new EspPersistenceException( "Pr�fung konnte nicht gespeichert werden!" );
		}
	}
}
