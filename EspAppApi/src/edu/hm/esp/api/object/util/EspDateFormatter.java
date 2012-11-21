package edu.hm.esp.api.object.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import edu.hm.esp.api.object.EspApiConstants;

/**
 * Klasse implementiert den Standard Datumsformatierer.
 * 
 * @author Stefan Wörner
 */
public final class EspDateFormatter
{

	private static final DateFormat FORMATTER = new SimpleDateFormat( EspApiConstants.DEFAULT_DATE_FORMAT );

	/**
	 * Privater Konstruktor.
	 */
	private EspDateFormatter()
	{

	}

	/**
	 * Gibt den formatierten String zum Datumswert zurueck.
	 * 
	 * @param date
	 *            Datumswert
	 * @return Formatierter Datumsstring (Format: yyyy-MM-dd'T'HH:mm:ss)
	 */
	public static String toString( Date date )
	{
		try
		{
			return FORMATTER.format( date );
		}
		catch (Exception e)
		{
			return null;
		}
	}

	/**
	 * Gibt den Datumswert zum Datumsstring zurueck oder null falls der Datumsstring fehlerhaft ist. (Format:
	 * yyyy-MM-dd'T'HH:mm:ss)
	 * 
	 * @param dateString
	 *            Formatierter Datumsstring (Format: yyyy-MM-dd'T'HH:mm:ss)
	 * @return Datumswert
	 */
	public static Date toDate( String dateString )
	{
		try
		{
			return FORMATTER.parse( dateString );
		}
		catch (Exception e)
		{
			return null;
		}
	}
}
