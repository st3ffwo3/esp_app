package edu.hm.esp.internal.exception;

/**
 * Unregistration Not Allowed Exception: Benutzer ist Pruefungssteller und kann sich nicht austragen.
 * 
 * @author Stefan Wörner
 */
public class EspUnregistrationNotAllowedException extends EspPersistenceException
{

	private static final long serialVersionUID = 5045771729723853886L;

	/**
	 * Exception mit Nachricht.
	 * 
	 * @param msg
	 *            Nachricht
	 */
	public EspUnregistrationNotAllowedException( String msg )
	{
		super( msg );
	}
}
