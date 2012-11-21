package edu.hm.esp.internal.exception;

import edu.hm.basic.exception.AbstractBasicRuntimeException;

/**
 * Allgemeine Mailing Exception.
 * 
 * @author Stefan Wörner
 */
public class EspMailingException extends AbstractBasicRuntimeException
{

	private static final long serialVersionUID = 7444227810960187096L;

	/**
	 * Exception mit Nachricht.
	 * 
	 * @param msg
	 *            Nachricht
	 */
	public EspMailingException( String msg )
	{
		super( msg );
	}
}
