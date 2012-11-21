package edu.hm.esp.internal.exception;

/**
 * Not Registered Exception: Benutzer ist nicht als Aufsichtsperson eingetragen.
 * 
 * @author Stefan Wörner
 */
public class EspNotRegisteredException extends EspPersistenceException
{

	private static final long serialVersionUID = 5045771729723853886L;

	/**
	 * Exception mit Nachricht.
	 * 
	 * @param msg
	 *            Nachricht
	 */
	public EspNotRegisteredException( String msg )
	{
		super( msg );
	}
}
