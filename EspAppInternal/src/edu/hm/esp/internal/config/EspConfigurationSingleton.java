package edu.hm.esp.internal.config;

/**
 * Singleton Implementierung fuer den globalen Zugriff auf die Konfiguration.
 * 
 * @author Stefan Wörner
 */
public final class EspConfigurationSingleton extends EspConfiguration
{

	private static EspConfigurationSingleton m_Config = new EspConfigurationSingleton();

	/**
	 * Konstruktor.
	 */
	private EspConfigurationSingleton()
	{
		super( EspConfigurationConstants.CONFIG_FILE_NAME );
	}

	/**
	 * Singleton Factory Methode.
	 * 
	 * @return unique Instanz von {@link EspConfigurationSingleton}
	 */
	public static EspConfigurationSingleton getInstance()
	{
		synchronized (m_Config)
		{
			return m_Config;
		}
	}
}
