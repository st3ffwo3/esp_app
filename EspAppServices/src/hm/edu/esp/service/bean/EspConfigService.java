package hm.edu.esp.service.bean;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.jboss.ejb3.annotation.LocalBinding;
import org.jboss.ejb3.annotation.SecurityDomain;

import edu.hm.esp.api.communication.request.IEspConfigApiLocal;
import edu.hm.esp.api.object.ressource.EspVersion;
import edu.hm.esp.internal.auth.EspRoleDefinition;
import edu.hm.esp.internal.bean.AbstractEspBean;
import edu.hm.esp.internal.bean.database.IEspExamDatabaseLocal;
import edu.hm.esp.internal.bean.database.IEspRoomDatabaseLocal;
import edu.hm.esp.internal.bean.database.IEspTimeSlotDatabaseLocal;
import edu.hm.esp.internal.bean.database.IEspUserDatabaseLocal;

/**
 * REST-Service fuer die Ressource Konfiguration. Verfuegbare Aktionen: GET.
 * 
 * @author Stefan Wörner
 */
@Stateless
@RolesAllowed( { EspRoleDefinition.ADMINISTRATORS } )
@SecurityDomain( "java:/jaas/EspSecurityDomain" )
@LocalBinding( jndiBinding = IEspConfigApiLocal.JNDI_BINDING )
public class EspConfigService extends AbstractEspBean implements IEspConfigApiLocal
{

	@EJB
	private IEspUserDatabaseLocal m_databaseUserBean;

	@EJB
	private IEspExamDatabaseLocal m_databaseExamBean;

	@EJB
	private IEspTimeSlotDatabaseLocal m_databaseTimeSlotBean;

	@EJB
	private IEspRoomDatabaseLocal m_databaseRoomBean;

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.esp.api.communication.request.IEspConfigApiLocal#getVersion()
	 */
	@Override
	@RolesAllowed( { EspRoleDefinition.ADMINISTRATORS, EspRoleDefinition.USER } )
	public EspVersion getVersion()
	{
		return new EspVersion();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.esp.api.communication.request.IEspConfigApiLocal#resetDatabaseCompletely()
	 */
	@Override
	public void resetDatabaseCompletely()
	{
		// Alle Pruefungen loeschen
		m_databaseExamBean.deleteAllExams();

		// Alle Benutzer loeschen
		m_databaseUserBean.deleteAllUser();

		// Alle TimeSlots loeschen
		m_databaseTimeSlotBean.deleteAllTimeSlots();

		// Alle Raeume loeschen
		m_databaseRoomBean.deleteAllRooms();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.esp.api.communication.request.IEspConfigApiLocal#resetDatabaseExamplanOnly()
	 */
	@Override
	public void resetDatabaseExamplanOnly()
	{
		// Alle Pruefungen loeschen
		m_databaseExamBean.deleteAllExams();

		// Alle TimeSlots loeschen
		m_databaseTimeSlotBean.deleteAllTimeSlots();

		// Alle Raeume loeschen
		m_databaseRoomBean.deleteAllRooms();
	}
}
