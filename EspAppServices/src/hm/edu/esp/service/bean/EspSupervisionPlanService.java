package hm.edu.esp.service.bean;

import java.util.Date;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.LockModeType;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;

import org.jboss.ejb3.annotation.LocalBinding;
import org.jboss.ejb3.annotation.SecurityDomain;

import edu.hm.basic.logging.BasicLogger;
import edu.hm.esp.api.communication.request.IEspSupervisionPlanApiLocal;
import edu.hm.esp.api.object.ressource.EspExam;
import edu.hm.esp.api.object.ressource.EspExamList;
import edu.hm.esp.api.object.ressource.EspSupervisionPlan;
import edu.hm.esp.api.object.ressource.EspTimeSlot;
import edu.hm.esp.api.object.ressource.EspTimeSlotList;
import edu.hm.esp.internal.auth.EspRoleDefinition;
import edu.hm.esp.internal.bean.AbstractEspBean;
import edu.hm.esp.internal.bean.database.IEspExamDatabaseLocal;
import edu.hm.esp.internal.bean.database.IEspSupervisionPlanDatabaseLocal;
import edu.hm.esp.internal.bean.database.IEspTimeSlotDatabaseLocal;
import edu.hm.esp.internal.converter.EspExamConverter;
import edu.hm.esp.internal.converter.EspTimeSlotConverter;
import edu.hm.esp.internal.object.entity.EspEntityExam;
import edu.hm.esp.internal.object.entity.EspEntityTimeSlot;
import edu.hm.esp.internal.object.entity.EspEntityTimeSlotPk;

/**
 * REST-Service fuer die Ressource Aufsichtsplan. Verfuegbare Aktionen: GET, LIST, CREATE, UPDATE, REMOVE.
 * 
 * @author Stefan Wörner
 */
@Stateless
@RolesAllowed( { EspRoleDefinition.ADMINISTRATORS } )
@SecurityDomain( "java:/jaas/EspSecurityDomain" )
@LocalBinding( jndiBinding = IEspSupervisionPlanApiLocal.JNDI_BINDING )
public class EspSupervisionPlanService extends AbstractEspBean implements IEspSupervisionPlanApiLocal
{

	@EJB
	private IEspSupervisionPlanDatabaseLocal m_databaseSupervisionPlanBean;

	@EJB
	private IEspExamDatabaseLocal m_databaseExamBean;

	@EJB
	private IEspTimeSlotDatabaseLocal m_databaseTimeSlotBean;

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.esp.api.communication.request.IEspSupervisionPlanApiLocal#getSupervisionPlan()
	 */
	@Override
	@RolesAllowed( { EspRoleDefinition.ADMINISTRATORS, EspRoleDefinition.USER } )
	public EspSupervisionPlan getSupervisionPlan()
	{
		// Liste der Pruefungen aus der Datenbank auslesen
		List<EspEntityExam> eExamList = m_databaseExamBean.readAllExams();
		// Liste der Zeitslots aus der Datenbank auslesen
		List<EspEntityTimeSlot> eTimeSlotList = m_databaseTimeSlotBean.readAllTimeSlots();

		// Gefundene Entitaets Pruefungen in Service Pruefungen konvertieren
		EspExamList examList = EspExamConverter.convertEntityToServiceExamList( eExamList, false );
		// Gefundene Entitaets Zeitslot in Service Zeitslot konvertieren
		EspTimeSlotList timeSlotList = EspTimeSlotConverter.convertEntityToServiceTimeSlotList( eTimeSlotList );

		// Aufsichtsplan erzeugen
		EspSupervisionPlan supervisionPlan = new EspSupervisionPlan();
		if (examList != null)
		{
			supervisionPlan.setExams( examList.getExams() );
		}
		if (timeSlotList != null)
		{
			supervisionPlan.setTimeSlots( timeSlotList.getTimeSlots() );
		}

		return supervisionPlan;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.esp.api.communication.request.IEspSupervisionPlanApiLocal#createTimeSlot(edu.hm.esp.api.object.ressource.EspTimeSlot)
	 */
	@Override
	public EspTimeSlot createTimeSlot( EspTimeSlot timeSlot )
	{
		// Service Zeitslot in Entitaets Zeitslot konvertieren
		EspEntityTimeSlot eTimeSlot = EspTimeSlotConverter.convertServiceToEntityTimeSlot( timeSlot );

		// Zeitslot in der Datenbank speichern
		eTimeSlot = m_databaseTimeSlotBean.insertTimeSlot( eTimeSlot );

		// Gespeicherten Entitaets Zeitslot in Service Zeitslot konvertieren
		timeSlot = EspTimeSlotConverter.convertEntityToServiceTimeSlot( eTimeSlot );

		// Zeitslot zurueckgeben
		return timeSlot;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.esp.api.communication.request.IEspSupervisionPlanApiLocal#getTimeSlotList()
	 */
	@Override
	@RolesAllowed( { EspRoleDefinition.ADMINISTRATORS, EspRoleDefinition.USER } )
	public EspTimeSlotList getTimeSlotList()
	{
		// Zeitslots aus der Datenbank auslesen
		List<EspEntityTimeSlot> eTimeSlotList = m_databaseTimeSlotBean.readAllTimeSlots();

		// Erhaltene Zeitslots in Service Zeitslots konvertieren
		EspTimeSlotList timeSlotList = EspTimeSlotConverter.convertEntityToServiceTimeSlotList( eTimeSlotList );

		// Zeitslots zurueckgeben
		return timeSlotList;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.esp.api.communication.request.IEspSupervisionPlanApiLocal#getTimeSlot(java.util.Date, java.util.Date)
	 */
	@Override
	@RolesAllowed( { EspRoleDefinition.ADMINISTRATORS, EspRoleDefinition.USER } )
	public EspTimeSlot getTimeSlot( Date fromDate, Date tillDate )
	{
		// Zeitslots aus der Datenbank auslesen
		EspEntityTimeSlot eTimeSlot = m_databaseTimeSlotBean.readTimeSlotByPK( new EspEntityTimeSlotPk( fromDate, tillDate ),
				LockModeType.PESSIMISTIC_READ );

		// Erhaltenen Zeitslot in Service Zeitslot konvertieren
		EspTimeSlot timeSlot = EspTimeSlotConverter.convertEntityToServiceTimeSlot( eTimeSlot );

		// Zeitslot zurueckgeben
		return timeSlot;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.esp.api.communication.request.IEspSupervisionPlanApiLocal#updateTimeSlot(java.util.Date,
	 *      java.util.Date, edu.hm.esp.api.object.ressource.EspTimeSlot)
	 */
	@Override
	public EspTimeSlot updateTimeSlot( Date fromDate, Date tillDate, EspTimeSlot timeSlot )
	{
		if (fromDate == null)
		{
			BasicLogger.logError( this, "Ung�ltiger �bergabeparameter fromDate: " + fromDate );
			throw new WebApplicationException( Status.BAD_REQUEST );
		}
		if (tillDate == null)
		{
			BasicLogger.logError( this, "Ung�ltiger �bergabeparameter tillDate: " + tillDate );
			throw new WebApplicationException( Status.BAD_REQUEST );
		}
		if (timeSlot == null)
		{
			BasicLogger.logError( this, "Ung�ltiger �bergabeparameter timeSlot-Objekt: " + timeSlot );
			throw new WebApplicationException( Status.BAD_REQUEST );
		}
		if (!timeSlot.getTimeSlotDate().getStartDate().equals( fromDate ))
		{
			BasicLogger.logError( this, "�bergabeparameter fromDate und fromDate des timeSlot-Objekts sind nicht "
					+ "identisch: fromDate=" + fromDate + ",timeSlot.fromDate=" + timeSlot.getTimeSlotDate().getStartDate() );
			throw new WebApplicationException( Status.BAD_REQUEST );
		}
		if (!timeSlot.getTimeSlotDate().getEndDate().equals( tillDate ))
		{
			BasicLogger.logError( this, "�bergabeparameter tillDate und tillDate des timeSlot-Objekts sind nicht "
					+ "identisch: tillDate=" + tillDate + ",timeSlot.tillDate=" + timeSlot.getTimeSlotDate().getEndDate() );
			throw new WebApplicationException( Status.BAD_REQUEST );
		}

		// Service Zeitslot in Entitaets Zeitslot konvertieren
		EspEntityTimeSlot eTimeSlot = EspTimeSlotConverter.convertServiceToEntityTimeSlot( timeSlot );

		// Zeitslot in der Datenbank aktualisieren
		eTimeSlot = m_databaseTimeSlotBean.updateTimeSlotWithoutReferenceUpdate( eTimeSlot );

		// Gespeicherten Entitaets Zeitslot in Service Zeitslot konvertieren
		timeSlot = EspTimeSlotConverter.convertEntityToServiceTimeSlot( eTimeSlot );

		// Zeitslot zurueckgeben
		return timeSlot;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.esp.api.communication.request.IEspSupervisionPlanApiLocal#removeTimeSlot(java.util.Date,
	 *      java.util.Date)
	 */
	@Override
	public void removeTimeSlot( Date fromDate, Date tillDate )
	{
		m_databaseTimeSlotBean.deleteTimeSlot( new EspEntityTimeSlotPk( fromDate, tillDate ) );
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.esp.api.communication.request.IEspSupervisionPlanApiLocal#getExamList()
	 */
	@Override
	@RolesAllowed( { EspRoleDefinition.ADMINISTRATORS, EspRoleDefinition.USER } )
	public EspExamList getExamList()
	{
		// Pruefungen aus der Datenbank auslesen
		List<EspEntityExam> eExamList = m_databaseExamBean.readAllExams();

		// Erhaltene Pruefungen in Service Pruefungen konvertieren
		EspExamList examList = EspExamConverter.convertEntityToServiceExamList( eExamList, true );

		// Pruefungen zurueckgeben
		return examList;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.esp.api.communication.request.IEspSupervisionPlanApiLocal#getExam(java.lang.String)
	 */
	@Override
	@RolesAllowed( { EspRoleDefinition.ADMINISTRATORS, EspRoleDefinition.USER } )
	public EspExam getExam( String examCode )
	{
		// Pruefung aus der Datenbank auslesen
		EspEntityExam eExam = m_databaseExamBean.readExamByPK( examCode, LockModeType.PESSIMISTIC_READ );

		// Erhaltene Pruefung in Service Pruefung konvertieren
		EspExam exam = EspExamConverter.convertEntityToServiceExam( eExam );

		// Pruefung zurueckgeben
		return exam;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.esp.api.communication.request.IEspSupervisionPlanApiLocal#updateExam(java.lang.String,
	 *      edu.hm.esp.api.object.ressource.EspExam)
	 */
	@Override
	public EspExam updateExam( String examCode, EspExam exam )
	{
		if (examCode == null || examCode.isEmpty())
		{
			BasicLogger.logError( this, "Ung�ltiger �bergabeparameter examCode: " + examCode );
			throw new WebApplicationException( Status.BAD_REQUEST );
		}
		if (exam == null)
		{
			BasicLogger.logError( this, "Ung�ltiger �bergabeparameter exam-Objekt: " + exam );
			throw new WebApplicationException( Status.BAD_REQUEST );
		}
		if (!exam.getCode().equals( examCode ))
		{
			BasicLogger.logError( this, "�bergabeparameter examCode und examCode des exam-Objekts sind nicht "
					+ "identisch: examCode=" + examCode + ",exam.examCode=" + exam.getCode() );
			throw new WebApplicationException( Status.BAD_REQUEST );
		}

		// Service Pruefung in Entitaets Pruefung konvertieren
		EspEntityExam eExam = EspExamConverter.convertServiceToEntityExam( exam );

		// Pruefung in der Datenbank aktualisieren
		eExam = m_databaseExamBean.updateExamWithoutReferenceUpdate( eExam );

		// Gespeicherte Entitaets Pruefung in Service Pruefung konvertieren
		exam = EspExamConverter.convertEntityToServiceExam( eExam );

		// Pruefung zurueckgeben
		return exam;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.esp.api.communication.request.IEspSupervisionPlanApiLocal#registerSupervisorForExam(java.lang.String,
	 *      javax.servlet.http.HttpServletRequest)
	 */
	@Override
	@RolesAllowed( { EspRoleDefinition.ADMINISTRATORS, EspRoleDefinition.USER } )
	public EspExam registerSupervisorForExam( String examCode, HttpServletRequest request )
	{
		if (examCode == null || examCode.isEmpty())
		{
			BasicLogger.logError( this, "Ung�ltiger �bergabeparameter examCode: " + examCode );
			throw new WebApplicationException( Status.BAD_REQUEST );
		}

		// Supervisor eintragen
		EspEntityExam eExam = m_databaseSupervisionPlanBean.addSupervisorForExam( examCode, request.getRemoteUser() );

		// Gespeicherte Entitaets Pruefung in Service Pruefung konvertieren
		EspExam exam = EspExamConverter.convertEntityToServiceExam( eExam );

		// Pruefung zurueckgeben
		return exam;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.esp.api.communication.request.IEspSupervisionPlanApiLocal#registerSupervisorForExam(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public EspExam registerSupervisorForExam( String examCode, String loginName )
	{
		if (examCode == null || examCode.isEmpty())
		{
			BasicLogger.logError( this, "Ung�ltiger �bergabeparameter examCode: " + examCode );
			throw new WebApplicationException( Status.BAD_REQUEST );
		}
		if (loginName == null || loginName.isEmpty())
		{
			BasicLogger.logError( this, "Ung�ltiger �bergabeparameter loginName: " + loginName );
			throw new WebApplicationException( Status.BAD_REQUEST );
		}

		// Supervisor eintragen
		EspEntityExam eExam = m_databaseSupervisionPlanBean.addSupervisorForExam( examCode, loginName );

		// Gespeicherte Entitaets Pruefung in Service Pruefung konvertieren
		EspExam exam = EspExamConverter.convertEntityToServiceExam( eExam );

		// Pruefung zurueckgeben
		return exam;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.esp.api.communication.request.IEspSupervisionPlanApiLocal#unregisterSupervisorFromExam(java.lang.String,
	 *      javax.servlet.http.HttpServletRequest)
	 */
	@Override
	@RolesAllowed( { EspRoleDefinition.ADMINISTRATORS, EspRoleDefinition.USER } )
	public EspExam unregisterSupervisorFromExam( String examCode, HttpServletRequest request )
	{
		if (examCode == null || examCode.isEmpty())
		{
			BasicLogger.logError( this, "Ung�ltiger �bergabeparameter examCode: " + examCode );
			throw new WebApplicationException( Status.BAD_REQUEST );
		}

		// Supervisor austragen
		EspEntityExam eExam = m_databaseSupervisionPlanBean.removeSupervisorFromExam( examCode, request.getRemoteUser() );

		// Gespeicherte Entitaets Pruefung in Service Pruefung konvertieren
		EspExam exam = EspExamConverter.convertEntityToServiceExam( eExam );

		// Pruefung zurueckgeben
		return exam;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.esp.api.communication.request.IEspSupervisionPlanApiLocal#unregisterSupervisorFromExam(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public EspExam unregisterSupervisorFromExam( String examCode, String loginName )
	{
		if (examCode == null || examCode.isEmpty())
		{
			BasicLogger.logError( this, "Ung�ltiger �bergabeparameter examCode: " + examCode );
			throw new WebApplicationException( Status.BAD_REQUEST );
		}
		if (loginName == null || loginName.isEmpty())
		{
			BasicLogger.logError( this, "Ung�ltiger �bergabeparameter loginName: " + loginName );
			throw new WebApplicationException( Status.BAD_REQUEST );
		}

		// Supervisor austragen
		EspEntityExam eExam = m_databaseSupervisionPlanBean.removeSupervisorFromExam( examCode, loginName );

		// Gespeicherte Entitaets Pruefung in Service Pruefung konvertieren
		EspExam exam = EspExamConverter.convertEntityToServiceExam( eExam );

		// Pruefung zurueckgeben
		return exam;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.esp.api.communication.request.IEspSupervisionPlanApiLocal#registerSupervisorForTimeSlot(java.util.Date,
	 *      java.util.Date, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	@RolesAllowed( { EspRoleDefinition.ADMINISTRATORS, EspRoleDefinition.USER } )
	public EspTimeSlot registerSupervisorForTimeSlot( Date fromDate, Date tillDate, HttpServletRequest request )
	{
		// Supervisor eintragen
		EspEntityTimeSlot eTimeSlot = m_databaseSupervisionPlanBean.addSupervisorForTimeSlot( new EspEntityTimeSlotPk( fromDate,
				tillDate ), request.getRemoteUser() );

		// Gespeicherten Entitaets Zeitslot in Service Zeitslot konvertieren
		EspTimeSlot timeSlot = EspTimeSlotConverter.convertEntityToServiceTimeSlot( eTimeSlot );

		// Zeitslot zurueckgeben
		return timeSlot;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.esp.api.communication.request.IEspSupervisionPlanApiLocal#registerSupervisorForTimeSlot(java.util.Date,
	 *      java.util.Date, java.lang.String)
	 */
	@Override
	public EspTimeSlot registerSupervisorForTimeSlot( Date fromDate, Date tillDate, String loginName )
	{
		if (loginName == null || loginName.isEmpty())
		{
			BasicLogger.logError( this, "Ung�ltiger �bergabeparameter loginName: " + loginName );
			throw new WebApplicationException( Status.BAD_REQUEST );
		}

		// Supervisor eintragen
		EspEntityTimeSlot eTimeSlot = m_databaseSupervisionPlanBean.addSupervisorForTimeSlot( new EspEntityTimeSlotPk( fromDate,
				tillDate ), loginName );

		// Gespeicherten Entitaets Zeitslot in Service Zeitslot konvertieren
		EspTimeSlot timeSlot = EspTimeSlotConverter.convertEntityToServiceTimeSlot( eTimeSlot );

		// Zeitslot zurueckgeben
		return timeSlot;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.esp.api.communication.request.IEspSupervisionPlanApiLocal#unregisterSupervisorFromTimeSlot(java.util.Date,
	 *      java.util.Date, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	@RolesAllowed( { EspRoleDefinition.ADMINISTRATORS, EspRoleDefinition.USER } )
	public EspTimeSlot unregisterSupervisorFromTimeSlot( Date fromDate, Date tillDate, HttpServletRequest request )
	{
		// Supervisor austragen
		EspEntityTimeSlot eTimeSlot = m_databaseSupervisionPlanBean.removeSupervisorFromTimeSlot( new EspEntityTimeSlotPk(
				fromDate, tillDate ), request.getRemoteUser() );

		// Gespeicherten Entitaets Zeitslot in Service Zeitslot konvertieren
		EspTimeSlot timeSlot = EspTimeSlotConverter.convertEntityToServiceTimeSlot( eTimeSlot );

		// Zeitslot zurueckgeben
		return timeSlot;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.esp.api.communication.request.IEspSupervisionPlanApiLocal#unregisterSupervisorFromTimeSlot(java.util.Date,
	 *      java.util.Date, java.lang.String)
	 */
	@Override
	public EspTimeSlot unregisterSupervisorFromTimeSlot( Date fromDate, Date tillDate, String loginName )
	{
		if (loginName == null || loginName.isEmpty())
		{
			BasicLogger.logError( this, "Ung�ltiger �bergabeparameter loginName: " + loginName );
			throw new WebApplicationException( Status.BAD_REQUEST );
		}
		// Supervisor austragen
		EspEntityTimeSlot eTimeSlot = m_databaseSupervisionPlanBean.removeSupervisorFromTimeSlot( new EspEntityTimeSlotPk(
				fromDate, tillDate ), loginName );

		// Gespeicherten Entitaets Zeitslot in Service Zeitslot konvertieren
		EspTimeSlot timeSlot = EspTimeSlotConverter.convertEntityToServiceTimeSlot( eTimeSlot );

		// Zeitslot zurueckgeben
		return timeSlot;
	}
}
