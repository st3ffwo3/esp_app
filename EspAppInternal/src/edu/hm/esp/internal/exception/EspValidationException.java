package edu.hm.esp.internal.exception;

import edu.hm.basic.exception.AbstractBasicRuntimeException;

/**
 * Allgemeine Validierungs Exception.
 * 
 * @author Stefan Wörner
 */
public class EspValidationException extends AbstractBasicRuntimeException
{

	private static final long serialVersionUID = 7444227810960187096L;

	/**
	 * Exception mit Nachricht.
	 * 
	 * @param msg
	 *            Nachricht
	 */
	public EspValidationException( String msg )
	{
		super( msg );
	}
}
