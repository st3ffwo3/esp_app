package edu.hm.esp.internal.mail;

import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import edu.hm.basic.logging.BasicLogger;
import edu.hm.esp.api.object.EspApiConstants;
import edu.hm.esp.internal.config.EspConfiguration;
import edu.hm.esp.internal.config.EspConfigurationConstants;
import edu.hm.esp.internal.config.EspConfigurationSingleton;
import edu.hm.esp.internal.exception.EspMailingException;

/**
 * Klasse implementiert Methoden fuer dem Mail versandt.
 * 
 * @author Stefan Wörner
 */
public final class EspMail
{

	private static EspConfiguration CONFIGURATION;

	private static boolean MAIL_ENABLED;

	private static boolean DEBUG_ENABLED;

	private static String HOST;

	private static String PORT;

	private static boolean AUTH_REQUIRED;

	private static String AUTH_USERNAME;

	private static String AUTH_CREDENTIAL;

	private static String SENDER_ADDRESS;

	private static String SENDER_NAME;

	private EspMail()
	{

	}

	private static void initialize()
	{
		CONFIGURATION = EspConfigurationSingleton.getInstance();

		MAIL_ENABLED = CONFIGURATION.getBoolean( EspConfigurationConstants.MAIL_ENABLED );
		HOST = CONFIGURATION.getString( EspConfigurationConstants.MAIL_SMTP_HOST );
		PORT = CONFIGURATION.getString( EspConfigurationConstants.MAIL_SMTP_PORT );
		DEBUG_ENABLED = CONFIGURATION.getBoolean( EspConfigurationConstants.MAIL_DEBUG_ENABLED );
		AUTH_REQUIRED = CONFIGURATION.getBoolean( EspConfigurationConstants.MAIL_AUTHENTICATION_REQUIRED );
		AUTH_USERNAME = CONFIGURATION.getString( EspConfigurationConstants.MAIL_AUTHENTICATION_USERNAME );
		AUTH_CREDENTIAL = CONFIGURATION.getString( EspConfigurationConstants.MAIL_AUTHENTICATION_PASSWORD );
		SENDER_ADDRESS = CONFIGURATION.getString( EspConfigurationConstants.MAIL_SENDER_ADDRESS );
		SENDER_NAME = CONFIGURATION.getString( EspConfigurationConstants.MAIL_SENDER_NAME );
	}

	/**
	 * Versendet eine Mail mit den Zugangsdaten an den uebergebenen Benutzer.
	 * 
	 * @param receipientAddress
	 *            E-Mail-Adresse des Empfaengers
	 * @param receipientName
	 *            Name des Empfaengers
	 * @param password
	 *            Generiertes Passwort
	 * @return Flag: Erfolgreich versandt.
	 */
	public static boolean sendMail( String receipientAddress, String receipientName, String password )
	{
		boolean success = false;
		
		// Config initialisieren
		initialize();

		if (MAIL_ENABLED)
		{
			try
			{
				EspMailAuthenticator auth = null;
				Session session = null;

				// Properties definieren
				Properties properties = new Properties();
				properties.put( "mail.smtp.host", HOST );
				properties.put( "mail.smtp.port", PORT );
				properties.put( "mail.debug", DEBUG_ENABLED );

				if (AUTH_REQUIRED)
				{
					properties.put( "mail.smtp.auth", AUTH_REQUIRED );
					auth = new EspMail.EspMailAuthenticator( AUTH_USERNAME, AUTH_CREDENTIAL );

					// Session erzeugen
					session = Session.getDefaultInstance( properties, auth );
				}
				else
				{
					// Session erzeugen
					session = Session.getDefaultInstance( properties );
				}

				// Message erzeugen
				Message msg = new MimeMessage( session );

				// Sendedatum festlegen
				msg.setSentDate( new Date() );

				// Senderadresse festlegen
				msg.setFrom( new InternetAddress( SENDER_ADDRESS, SENDER_NAME, "UTF-8" ) );

				// Empfaenger festlegen
				msg.setRecipient( MimeMessage.RecipientType.TO, new InternetAddress( receipientAddress, receipientName, "UTF-8" ) );

				// Betreff festlegen
				msg.setSubject( "ESP: Aktivierung Ihres Benutzeraccounts!" );

				// Inhalt festlegen
				msg.setText( getMailContent( receipientName, password ) );

				// Versenden
				Transport.send( msg );
				success = true;
			}
			catch (Exception e)
			{
				BasicLogger.logError( EspMail.class, e.getMessage() );
				success = false;
			}
		}
		else
		{
			BasicLogger.logInfo( EspMail.class, password );
			success = true;
		}

		if (success)
		{
			return true;
		}
		else
		{
			throw new EspMailingException( "Mail konnte nicht gesendet werden!" );
		}
	}

	private static String getMailContent( String displayName, String password )
	{
		StringBuilder sb = new StringBuilder();
		sb.append( "Sehr geehrte(r) " ).append( displayName ).append( ",\n\n" );

		sb.append( "soeben wurde Ihr Benutzeraccount f�r das ESP Portal vom Administrator aktiviert!\n\n" );

		sb.append( "Ab sofort k�nnen Sie sich am ESP-Portal anmelden und sich f�r Pr�fungsaufsichten eintragen. " );
		sb.append( "Hierzu melden Sie sich mit Ihrem Hochschulaccount und dem f�r Sie generierten Passwort an. " );
		sb.append( "Sollten Sie Ihr Passwort vergessen, diese Mail l�schen oder verlieren, wenden Sie sich " );
		sb.append( "bitte an den System-Administrator (" );
		sb.append( CONFIGURATION.getString( EspConfigurationConstants.SYSTEM_ADMIN_MAIL_ADDRESS ) );
		sb.append( ").\n\n" );

		sb.append( "ESP-Portal: " ).append( CONFIGURATION.getString( EspConfigurationConstants.SYSTEM_SERVER_PROTOCOL ) )
				.append( "://" ).append( CONFIGURATION.getString( EspConfigurationConstants.SYSTEM_SERVER_HOST ) ).append( ":" )
				.append( CONFIGURATION.getString( EspConfigurationConstants.SYSTEM_SERVER_PORT ) ).append( "/" )
				.append( EspApiConstants.APPLICATION_URI_SUFFIX ).append( "\n" );
		sb.append( "Ihr Passwort: " ).append( password ).append( "\n\n" );

		sb.append( "Mit freundlichen Gr��en,\n" );
		sb.append( "Ihr ESP-Portal-Team" );

		return sb.toString();
	}

	/**
	 * Mail-Authentifizierungs-Klasse.
	 * 
	 * @author Stefan Wörner
	 */
	private static class EspMailAuthenticator extends Authenticator
	{

		private final String m_user;

		private final String m_password;

		/**
		 * Der Konstruktor erzeugt ein MailAuthenticator Objekt aus den beiden Parametern user und passwort.
		 * 
		 * @param user
		 *            String, der Username fuer den Mailaccount.
		 * @param password
		 *            String, das Passwort fuer den Mailaccount.
		 */
		public EspMailAuthenticator( String user, String password )
		{
			this.m_user = user;
			this.m_password = password;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see javax.mail.Authenticator#getPasswordAuthentication()
		 */
		@Override
		protected PasswordAuthentication getPasswordAuthentication()
		{
			return new PasswordAuthentication( this.m_user, this.m_password );
		}
	}
}
