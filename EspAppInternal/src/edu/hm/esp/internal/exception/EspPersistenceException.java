package edu.hm.esp.internal.exception;

import edu.hm.basic.exception.AbstractBasicRuntimeException;

/**
 * Allgemeine Persistence Exception.
 * 
 * @author Stefan Wörner
 */
public class EspPersistenceException extends AbstractBasicRuntimeException
{

	private static final long serialVersionUID = 5484956956672543484L;

	/**
	 * Exception mit Nachricht.
	 * 
	 * @param msg
	 *            Nachricht
	 */
	public EspPersistenceException( String msg )
	{
		super( msg );
	}
}
