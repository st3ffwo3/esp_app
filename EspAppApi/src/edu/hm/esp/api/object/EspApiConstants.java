/**
 * 
 */
package edu.hm.esp.api.object;

/**
 * Klasse enthaelt alle Konstanten der ESP API die auch fuer Clients notwendig bzw. sinnvoll sind.
 * 
 * @author Stefan Wörner
 */
public final class EspApiConstants
{

	/**
	 * Privater Konstructor.
	 */
	private EspApiConstants()
	{

	}

	/**
	 * Konstante mit der Versionsnummer der ESP-Server-Anteile.
	 */
	public static final String VERSION = "1.001.0";

	/**
	 * Realm Name der Applikation.
	 */
	public static final String APPLICATION_REALM = "Exam Supervision Planning Services";

	/**
	 * Suffix der Applikations-URI.
	 */
	public static final String APPLICATION_URI_SUFFIX = "esp/portal";

	/**
	 * Datumsformatierungs-String.
	 */
	public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
}
