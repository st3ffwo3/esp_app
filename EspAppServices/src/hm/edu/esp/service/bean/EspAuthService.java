package hm.edu.esp.service.bean;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;

import org.jboss.ejb3.annotation.LocalBinding;
import org.jboss.ejb3.annotation.SecurityDomain;

import edu.hm.esp.api.communication.request.IEspAuthApiLocal;
import edu.hm.esp.internal.auth.EspRoleDefinition;
import edu.hm.esp.internal.bean.AbstractEspBean;

/**
 * REST-Service fuer die Authentifizierung. Verfuegbare Aktionen: GET.
 * 
 * @author Stefan Wörner
 */
@Stateless
@RolesAllowed( { EspRoleDefinition.ADMINISTRATORS, EspRoleDefinition.USER } )
@SecurityDomain( "java:/jaas/EspSecurityDomain" )
@LocalBinding( jndiBinding = IEspAuthApiLocal.JNDI_BINDING )
public class EspAuthService extends AbstractEspBean implements IEspAuthApiLocal
{

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.esp.api.communication.request.IEspAuthApiLocal#logout(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public void logout( HttpServletRequest request )
	{
		// Session invalidieren und damit abmelden
		request.getSession().invalidate();
	}
}
