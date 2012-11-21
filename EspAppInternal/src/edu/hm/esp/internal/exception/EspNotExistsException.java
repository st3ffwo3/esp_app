package edu.hm.esp.internal.exception;

/**
 * Not Exsists Exception: Gesuchtes Objekt existiert nicht.
 * 
 * @author Stefan Wörner
 */
public class EspNotExistsException extends EspPersistenceException
{

	private static final long serialVersionUID = -7691263588985824223L;

	/**
	 * Exception mit Nachricht.
	 * 
	 * @param msg
	 *            Nachricht
	 */
	public EspNotExistsException( String msg )
	{
		super( msg );
	}
}
