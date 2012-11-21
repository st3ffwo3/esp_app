package edu.hm.esp.internal.auth;

/**
 * Klasse enhtaelt alle zulaessigen und im System verfuegbaren Rollennamen.
 * 
 * @author Stefan Wörner
 */
public final class EspRoleDefinition
{

	private EspRoleDefinition()
	{

	}

	/**
	 * Konstante fuer den Administrator-Rollennamen.
	 */
	public static final String ADMINISTRATORS = "Administrators";

	/**
	 * Konstante fuer den Benutzer-Rollennamen.
	 */
	public static final String USER = "User";
}
