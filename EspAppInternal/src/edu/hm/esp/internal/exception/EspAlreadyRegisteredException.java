package edu.hm.esp.internal.exception;

/**
 * Already Registered Exception: Benutzer ist bereits als Aufsichtsperson eingetragen.
 * 
 * @author Stefan Wörner
 */
public class EspAlreadyRegisteredException extends EspPersistenceException
{

	private static final long serialVersionUID = 5045771729723853886L;

	/**
	 * Exception mit Nachricht.
	 * 
	 * @param msg
	 *            Nachricht
	 */
	public EspAlreadyRegisteredException( String msg )
	{
		super( msg );
	}
}
