package edu.hm.esp.internal.exception;

/**
 * Already Exists Exception: Objekt existiert bereits.
 * 
 * @author Stefan Wörner
 */
public class EspAlreadyExistsException extends EspPersistenceException
{

	private static final long serialVersionUID = 5045771729723853886L;

	/**
	 * Exception mit Nachricht.
	 * 
	 * @param msg
	 *            Nachricht
	 */
	public EspAlreadyExistsException( String msg )
	{
		super( msg );
	}
}
