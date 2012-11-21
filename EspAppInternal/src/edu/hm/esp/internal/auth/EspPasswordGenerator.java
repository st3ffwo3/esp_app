package edu.hm.esp.internal.auth;

import org.apache.commons.lang3.RandomStringUtils;
import org.jboss.security.auth.spi.Util;

import edu.hm.esp.api.object.EspApiConstants;

/**
 * Klasse implementiert Methoden zum Generieren eines Passworts.
 * 
 * @author Stefan Wörner
 */
public class EspPasswordGenerator
{

	private EspAuthMethod m_authMethod;

	private String m_clearTextPassword;

	private String m_hashedCredential;

	private String m_credential;

	private String m_userName;

	/**
	 * Parametrisierter Konstruktor.
	 * 
	 * @param userName
	 *            Benutzername fuer welchen ein Passwort generiert werden soll
	 * @param authMethod
	 *            Authenifizierungs-Methode
	 */
	public EspPasswordGenerator( String userName, EspAuthMethod authMethod )
	{
		this.m_authMethod = authMethod;
		this.m_userName = userName;
	}

	/**
	 * Generiert ein Benutzer-Passwort.
	 */
	public void generatePassword()
	{
		// Passwort generieren
		generatePasswordInternal();

		// Credential erzeugen
		generateCredentialInternal();

		// Credential Hash erzeugen
		generateCredentialHashInternal();
	}

	private void generatePasswordInternal()
	{
		// Passwort generieren
		m_clearTextPassword = RandomStringUtils.random( 10, true, true );
	}

	private void generateCredentialInternal()
	{
		switch (m_authMethod)
		{
		case FORM_BASED:
			// Credential holen
			m_credential = m_clearTextPassword;
			break;

		case DIGEST:
			// A1 Credential erzeugen
			m_credential = m_userName + ":" + EspApiConstants.APPLICATION_REALM + ":" + m_clearTextPassword;
			break;

		default:
			// Credential holen
			m_credential = m_clearTextPassword;
			break;
		}
	}

	private void generateCredentialHashInternal()
	{
		switch (m_authMethod)
		{
		case FORM_BASED:
			// Credential Hash erzeugen
			m_hashedCredential = Util.createPasswordHash( "SHA-384", Util.BASE64_ENCODING, null, m_userName, m_credential, null );
			break;

		case DIGEST:
			// Credential Hash erzeugen
			m_hashedCredential = Util.createPasswordHash( "MD5", Util.RFC2617_ENCODING, null, m_userName, m_credential );
			break;

		default:
			// Credential Hash erzeugen
			m_hashedCredential = Util.createPasswordHash( "SHA-384", Util.BASE64_ENCODING, null, m_userName, m_credential, null );
			break;
		}
	}

	/**
	 * Getter fuer das Klartext-Passwort.
	 * 
	 * @return clearTextPassword
	 */
	public String getClearTextPassword()
	{
		return this.m_clearTextPassword;
	}

	/**
	 * Getter fuer den Passwort-Hash.
	 * 
	 * @return hashedCredential
	 */
	public String getHashedCredential()
	{
		return this.m_hashedCredential;
	}
}
