package hm.edu.esp.service.bean;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;

import org.jboss.ejb3.annotation.LocalBinding;
import org.jboss.ejb3.annotation.SecurityDomain;

import edu.hm.basic.logging.BasicLogger;
import edu.hm.esp.api.communication.request.IEspUserApiLocal;
import edu.hm.esp.api.object.ressource.EspExam;
import edu.hm.esp.api.object.ressource.EspExamList;
import edu.hm.esp.api.object.ressource.EspRole;
import edu.hm.esp.api.object.ressource.EspRoleList;
import edu.hm.esp.api.object.ressource.EspSupervisionPlan;
import edu.hm.esp.api.object.ressource.EspSupervisor;
import edu.hm.esp.api.object.ressource.EspTimeSlot;
import edu.hm.esp.api.object.ressource.EspTimeSlotList;
import edu.hm.esp.api.object.ressource.EspUser;
import edu.hm.esp.api.object.ressource.EspUserList;
import edu.hm.esp.internal.auth.EspAuthMethod;
import edu.hm.esp.internal.auth.EspPasswordGenerator;
import edu.hm.esp.internal.auth.EspRoleDefinition;
import edu.hm.esp.internal.bean.AbstractEspBean;
import edu.hm.esp.internal.bean.database.IEspExamDatabaseLocal;
import edu.hm.esp.internal.bean.database.IEspTimeSlotDatabaseLocal;
import edu.hm.esp.internal.bean.database.IEspUserDatabaseLocal;
import edu.hm.esp.internal.converter.EspExamConverter;
import edu.hm.esp.internal.converter.EspTimeSlotConverter;
import edu.hm.esp.internal.converter.EspUserConverter;
import edu.hm.esp.internal.mail.EspMail;
import edu.hm.esp.internal.object.entity.EspEntityExam;
import edu.hm.esp.internal.object.entity.EspEntityRole;
import edu.hm.esp.internal.object.entity.EspEntityTimeSlot;
import edu.hm.esp.internal.object.entity.EspEntityUser;

/**
 * REST-Service fuer die Ressource Benutzer. Verfuegbare Aktionene: GET, LIST, CREATE, UPDATE, REMOVE.
 * 
 * @author Stefan Wörner
 */
@Stateless
@RolesAllowed( { EspRoleDefinition.ADMINISTRATORS } )
@SecurityDomain( "java:/jaas/EspSecurityDomain" )
@LocalBinding( jndiBinding = IEspUserApiLocal.JNDI_BINDING )
public class EspUserService extends AbstractEspBean implements IEspUserApiLocal
{

	@EJB
	private IEspUserDatabaseLocal m_databaseUserBean;

	@EJB
	private IEspExamDatabaseLocal m_databaseExamBean;

	@EJB
	private IEspTimeSlotDatabaseLocal m_databaseTimeSlotBean;

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.esp.api.communication.request.IEspUserApiLocal#getUserList()
	 */
	@Override
	public EspUserList getUserList()
	{
		// Benutzer aus der Datenbank auslesen
		List<EspEntityUser> eUserList = m_databaseUserBean.readAllUser();

		// Erhaltene Benutzer in Service Benutzer konvertieren
		EspUserList userList = EspUserConverter.convertEntityToServiceUserList( eUserList );

		// Benutzer zurueckgeben
		return userList;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.esp.api.communication.request.IEspUserApiLocal#getUser(java.lang.String)
	 */
	@Override
	public EspUser getUser( String loginName )
	{
		if (loginName == null || loginName.isEmpty())
		{
			BasicLogger.logError( this, "Ung�ltiger �bergabeparameter loginName: " + loginName );
			throw new WebApplicationException( Status.BAD_REQUEST );
		}

		return getUserInternal( loginName );
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.esp.api.communication.request.IEspUserApiLocal#getUser(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	@RolesAllowed( { EspRoleDefinition.ADMINISTRATORS, EspRoleDefinition.USER } )
	public EspUser getUser( HttpServletRequest request )
	{
		return getUserInternal( request.getRemoteUser() );
	}

	private EspUser getUserInternal( String loginName )
	{
		// Benutzer aus der Datenbank auslesen
		EspEntityUser eUser = m_databaseUserBean.readUserByPK( loginName );

		// Erhaltenen Benutzer in Service Benutzer konvertieren
		EspUser user = EspUserConverter.convertEntityToServiceUser( eUser );

		// Benutzer zurueckgeben
		return user;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.esp.api.communication.request.IEspUserApiLocal#createUser(edu.hm.esp.api.object.ressource.EspUser)
	 */
	@Override
	public EspUser createUser( EspUser user )
	{
		// Uebergebene Rollenliste des Benutzers (falls vorhanden) loeschen und die User-Rolle einfuegen, da nur
		// Benutzer mit der Rolle User angelegt werden sollen.
		limitRoleCreation( user );

		// Service Benutzer in Entitaets Benutzer konvertieren
		EspEntityUser eUser = EspUserConverter.convertServiceToEntityUser( user );

		// Benutzer in der Datenbank speichern
		eUser = m_databaseUserBean.insertUser( eUser );

		// Gespeicherten Entitaets Benutzer in Service Benutzer konvertieren
		user = EspUserConverter.convertEntityToServiceUser( eUser );

		// Benutzer zurueckgeben
		return user;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.esp.api.communication.request.IEspUserApiLocal#updateUser(java.lang.String,
	 *      edu.hm.esp.api.object.ressource.EspUser)
	 */
	@Override
	public EspUser updateUser( String loginName, EspUser user )
	{
		if (loginName == null || loginName.isEmpty())
		{
			BasicLogger.logError( this, "Ung�ltiger �bergabeparameter loginName: " + loginName );
			throw new WebApplicationException( Status.BAD_REQUEST );
		}
		if (user == null)
		{
			BasicLogger.logError( this, "Ung�ltiger �bergabeparameter user: " + user );
			throw new WebApplicationException( Status.BAD_REQUEST );
		}
		if (!loginName.equals( user.getLoginName() ))
		{
			BasicLogger.logError( this, "�bergabeparameter loginName und loginName des user-Objekts "
					+ "sind nicht identisch: loginName=" + loginName + ",user.loginName=" + user.getLoginName() );
			throw new WebApplicationException( Status.BAD_REQUEST );
		}

		// Uebergebene Rollenliste des Benutzers (falls vorhanden) loeschen und die User-Rolle einfuegen, da nur
		// Benutzer mit der Rolle User angelegt werden sollen.
		limitRoleCreation( user );

		// Service Benutzer in Entitaets Benutzer konvertieren
		EspEntityUser eUser = EspUserConverter.convertServiceToEntityUser( user );

		// Benutzer in der Datenbank aktualisieren
		eUser = m_databaseUserBean.updateUserWithoutReferenceUptdate( eUser );

		// Gespeicherten Entitaets Benutzer in Service Benutzer konvertieren
		user = EspUserConverter.convertEntityToServiceUser( eUser );

		// Benutzer zurueckgeben
		return user;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.esp.api.communication.request.IEspUserApiLocal#removeUser(java.lang.String)
	 */
	@Override
	public void removeUser( String loginName )
	{
		m_databaseUserBean.deleteUser( loginName );
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.esp.api.communication.request.IEspUserApiLocal#activateUser(java.lang.String)
	 */
	@Override
	public EspUser activateUser( String loginName )
	{
		if (loginName == null || loginName.isEmpty())
		{
			BasicLogger.logError( this, "Ung�ltiger �bergabeparameter loginName: " + loginName );
			throw new WebApplicationException( Status.BAD_REQUEST );
		}

		// Benutzer auslesen
		EspEntityUser eUser = m_databaseUserBean.readUserByPK( loginName );

		// Pruefen ob alle fuer den Mailversand benoetigten Attribute gesetzt sind
		eUser.validateMailability();

		// Passwort generieren
		EspPasswordGenerator gen = new EspPasswordGenerator( eUser.getLoginName(), EspAuthMethod.FORM_BASED );
		gen.generatePassword();

		// Wenn Mail erfolgreich versendet wurde, dann Password persistieren
		if (EspMail.sendMail( eUser.getMailAddress(), eUser.getDisplayName(), gen.getClearTextPassword() ))
		{
			// PasswortHash setzen
			eUser.setPassword( gen.getHashedCredential() );

			// Benutzer in der Datenbank aktualisieren
			eUser = m_databaseUserBean.updateUserWithoutReferenceUptdate( eUser );
		}

		// Gespeicherten Entitaets Benutzer in Service Benutzer konvertieren
		EspUser user = EspUserConverter.convertEntityToServiceUser( eUser );

		// Benutzer zurueckgeben
		return user;
	}

	private void limitRoleCreation( EspUser user )
	{
		// Uebergebene Rollenliste des Benutzers (falls vorhanden) loeschen und die User-Rolle einfuegen, da nur
		// Benutzer mit der Rolle User angelegt werden sollen.
		user.getRoles().clear();
		EspRole role = new EspRole();
		role.setName( EspRoleDefinition.USER );
		user.addRole( role );
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.esp.api.communication.request.IEspUserApiLocal#getUserRoles(java.lang.String)
	 */
	@Override
	public EspRoleList getUserRoles( String loginName )
	{
		if (loginName == null || loginName.isEmpty())
		{
			BasicLogger.logError( this, "Ung�ltiger �bergabeparameter loginName: " + loginName );
			throw new WebApplicationException( Status.BAD_REQUEST );
		}

		return getUserRolesInternal( loginName );
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.esp.api.communication.request.IEspUserApiLocal#getUserRoles(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	@RolesAllowed( { EspRoleDefinition.ADMINISTRATORS, EspRoleDefinition.USER } )
	public EspRoleList getUserRoles( HttpServletRequest request )
	{
		return getUserRolesInternal( request.getRemoteUser() );
	}

	private EspRoleList getUserRolesInternal( String loginName )
	{
		// Benutzer aus der Datenbank auslesen
		EspEntityUser eUser = m_databaseUserBean.readUserByPK( loginName );

		// Rollenliste erstellen
		EspRoleList roleList = new EspRoleList();

		for (EspEntityRole eRole : eUser.getRoles())
		{
			EspRole role = new EspRole();
			role.setName( eRole.getName() );

			roleList.addRole( role );
		}

		// Rollen zurueckgeben
		return roleList;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.esp.api.communication.request.IEspUserApiLocal#getUserSupervisionPlan(java.lang.String)
	 */
	@Override
	public EspSupervisionPlan getUserSupervisionPlan( String loginName )
	{
		if (loginName == null || loginName.isEmpty())
		{
			BasicLogger.logError( this, "Ung�ltiger �bergabeparameter loginName: " + loginName );
			throw new WebApplicationException( Status.BAD_REQUEST );
		}

		return getUserSupervisionPlanInternal( loginName );
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.esp.api.communication.request.IEspUserApiLocal#getUserSupervisionPlan(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	@RolesAllowed( { EspRoleDefinition.ADMINISTRATORS, EspRoleDefinition.USER } )
	public EspSupervisionPlan getUserSupervisionPlan( HttpServletRequest request )
	{
		return getUserSupervisionPlanInternal( request.getRemoteUser() );
	}

	private EspSupervisionPlan getUserSupervisionPlanInternal( String loginName )
	{
		// Benutzer aus der Datenbank auslesen
		EspEntityUser eUser = m_databaseUserBean.readUserByPK( loginName );

		// Supervisor erzeugen
		EspSupervisor supervisor = new EspSupervisor();
		supervisor.setLoginName( eUser.getLoginName() );
		supervisor.setDisplayName( eUser.getDisplayName() );
		supervisor.setNumSupervisions( eUser.getNumSupervisions() );

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
			// Nicht relevante Pruefungen herausfiltern
			for (EspExam exam : examList.getExams())
			{
				// Wenn Supervisor enthalten, dann speichern
				if (exam.getCurrentSupervisors().contains( supervisor ))
				{
					supervisionPlan.addExam( exam );
				}
			}
		}
		if (timeSlotList != null)
		{
			// Nicht relevante Zeitslots herausfiltern
			for (EspTimeSlot timeSlot : timeSlotList.getTimeSlots())
			{
				// Wenn Supervisor enthalten, dann speichern
				if (timeSlot.getCurrentSupervisors().contains( supervisor ))
				{
					supervisionPlan.addTimeSlot( timeSlot );
				}
			}
		}

		return supervisionPlan;
	}
}
