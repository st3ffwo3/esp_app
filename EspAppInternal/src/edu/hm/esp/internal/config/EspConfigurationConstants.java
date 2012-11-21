package edu.hm.esp.internal.config;

/**
 * Diese Klasse enthaelt die Konstanten zum Auslesen aus der Konfiguration.
 * 
 * @author Stefan Wörner
 */
public final class EspConfigurationConstants
{

	private EspConfigurationConstants()
	{

	}

	/**
	 * ConfigFile Name.
	 */
	public static final String CONFIG_FILE_NAME = "esp_config.xml";

	/**
	 * PropertiesFile Name.
	 */
	public static final String PROPERTIES_FILE_NAME = "esp_user.properties";

	/**
	 * Flag: Mailversand aktiviert.
	 */
	public static final String MAIL_ENABLED = "esp.mail.enabled";

	/**
	 * SMTP Mail-Host.
	 */
	public static final String MAIL_SMTP_HOST = "esp.mail.smtp.host";

	/**
	 * SMTP Mail-Port.
	 */
	public static final String MAIL_SMTP_PORT = "esp.mail.smtp.port";

	/**
	 * Flag: Authentifizierung notwendig.
	 */
	public static final String MAIL_AUTHENTICATION_REQUIRED = "esp.mail.auth.required";

	/**
	 * Benutzername fuer die Authentifizierung.
	 */
	public static final String MAIL_AUTHENTICATION_USERNAME = "esp.mail.auth.username";

	/**
	 * Passwort fuer die Authentifizierung.
	 */
	public static final String MAIL_AUTHENTICATION_PASSWORD = "esp.mail.auth.password";

	/**
	 * E-Mail-Adresse des Absenders.
	 */
	public static final String MAIL_SENDER_ADDRESS = "esp.mail.sender.address";

	/**
	 * Name des Absenders.
	 */
	public static final String MAIL_SENDER_NAME = "esp.mail.sender.name";

	/**
	 * Flag: Debug einschalten.
	 */
	public static final String MAIL_DEBUG_ENABLED = "esp.mail.debug.enabled";

	/**
	 * Server-Host.
	 */
	public static final String SYSTEM_SERVER_PROTOCOL = "esp.system.server.protocol";

	/**
	 * Server-Host.
	 */
	public static final String SYSTEM_SERVER_HOST = "esp.system.server.host";

	/**
	 * Server-Port.
	 */
	public static final String SYSTEM_SERVER_PORT = "esp.system.server.port";

	/**
	 * Flag: Debug einschalten.
	 */
	public static final String SYSTEM_ADMIN_MAIL_ADDRESS = "esp.system.admin.mailaddress";
}
