package hm.edu.esp.service.bean;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.jboss.ejb3.annotation.LocalBinding;
import org.jboss.ejb3.annotation.SecurityDomain;

import edu.hm.esp.api.communication.request.IEspExamPlanApiLocal;
import edu.hm.esp.api.object.ressource.EspExamPlan;
import edu.hm.esp.internal.auth.EspRoleDefinition;
import edu.hm.esp.internal.bean.AbstractEspBean;
import edu.hm.esp.internal.bean.database.IEspExamPlanDatabaseLocal;
import edu.hm.esp.internal.converter.EspExamPlanConverter;
import edu.hm.esp.internal.object.entity.EspEntityExam;

/**
 * REST-Service fuer die Ressource Pruefungsplan. Verfuegbare Aktionen: CREATE, UPDATE.
 * 
 * @author Stefan Wörner
 */
@Stateless
@RolesAllowed( { EspRoleDefinition.ADMINISTRATORS } )
@SecurityDomain( "java:/jaas/EspSecurityDomain" )
@LocalBinding( jndiBinding = IEspExamPlanApiLocal.JNDI_BINDING )
public class EspExamPlanService extends AbstractEspBean implements IEspExamPlanApiLocal
{

	@EJB
	private IEspExamPlanDatabaseLocal m_databaseExamPlanBean;

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.esp.api.communication.request.IEspExamPlanApiLocal#importExamPlan(edu.hm.esp.api.object.ressource.EspExamPlan)
	 */
	@Override
	public void importExamPlan( EspExamPlan examPlan )
	{
		// Service Pruefungsplan in Entitaets Pruefungsplan konvertieren
		List<EspEntityExam> eExamPlan = EspExamPlanConverter.convertServiceToEntityExamPlan( examPlan );

		// Pruefungsplan in der Datenbank speichern
		m_databaseExamPlanBean.insertOrUpdateExamPlanWithoutReferenceUpdate( eExamPlan );
	}
}
