package edu.hm.esp.internal.exception;

/**
 * No Supervisors Required Exception: Das Aufsichtskoningent der Pruefung ist bereits erfuellt..
 * 
 * @author Stefan Wörner
 */
public class EspNoSupervisorsRequiredException extends EspPersistenceException
{

	private static final long serialVersionUID = 5045771729723853886L;

	/**
	 * Exception mit Nachricht.
	 * 
	 * @param msg
	 *            Nachricht
	 */
	public EspNoSupervisorsRequiredException( String msg )
	{
		super( msg );
	}
}
