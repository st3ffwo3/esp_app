package edu.hm.esp.api.object.ressource;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import edu.hm.esp.api.object.AbstractEspRessourceObject;

/**
 * Klasse repraesentiert die Ressource Benutzer.
 * 
 * @author Stefan Wörner
 */
@XmlRootElement( name = "User" )
@XmlType( propOrder = { "loginName", "displayName", "mailAddress", "roles", "numSupervisions", "activated" } )
@XmlAccessorOrder( XmlAccessOrder.ALPHABETICAL )
@XmlAccessorType( XmlAccessType.NONE )
@JsonPropertyOrder( value = { "LoginName", "DisplayName", "MailAddress", "Roles", "AchievableSupervisions", "Activated" },
					alphabetic = true )
@JsonSerialize( include = Inclusion.NON_NULL )
@Produces( { "application/xml", "application/json", "text/xml" } )
@Consumes( { "application/xml", "application/json", "text/xml" } )
public class EspUser extends AbstractEspRessourceObject
{

	private static final long serialVersionUID = -5791608332421374534L;

	private String m_loginName;

	private String m_displayName;

	private Boolean m_activated;

	private String m_mailAddress;

	private Integer m_numSupervisions;

	private List<EspRole> m_roles;

	/**
	 * Standerdkonstruktor.
	 */
	public EspUser()
	{
		super();
		m_roles = new ArrayList<EspRole>();
	}

	/**
	 * Getter fuer den Login Namen des Benutzers.
	 * 
	 * @return loginName
	 */
	@XmlAttribute( name = "LoginName" )
	@JsonProperty( "LoginName" )
	public String getLoginName()
	{
		return this.m_loginName;
	}

	/**
	 * Setter fuer den Login Namen des Benutzers.
	 * 
	 * @param loginName
	 *            Login Name des Benutzers
	 */
	@JsonProperty( "LoginName" )
	public void setLoginName( String loginName )
	{
		this.m_loginName = loginName;
	}

	/**
	 * Getter fuer den Anzeigenamen des Benutzers.
	 * 
	 * @return displayName
	 */
	@XmlElement( name = "DisplayName" )
	@JsonProperty( "DisplayName" )
	public String getDisplayName()
	{
		return this.m_displayName;
	}

	/**
	 * Setter fuer den Anzeigenamen des Benutzers.
	 * 
	 * @param displayName
	 *            Anzeigename des Benutzers
	 */
	@JsonProperty( "DisplayName" )
	public void setDisplayName( String displayName )
	{
		this.m_displayName = displayName;
	}

	/**
	 * Getter fuer das Aktiviert-Flag. Es zeigt an ob fuer den Benutzer bereits ein Passwort generiert wurde und dieser
	 * sich am System anmelden kann.
	 * 
	 * @return activated
	 */
	@XmlElement( name = "Activated" )
	@JsonProperty( "Activated" )
	public Boolean isActivated()
	{
		return this.m_activated;
	}

	/**
	 * Setter fuer das Aktiviert-Flag. Es zeigt an ob fuer den Benutzer bereits ein Passwort generiert wurde und dieser
	 * sich am System anmelden kann.
	 * 
	 * @param activated
	 *            Aktiviert-Flag
	 */
	@JsonProperty( "Activated" )
	public void setActivated( Boolean activated )
	{
		this.m_activated = activated;
	}

	/**
	 * Getter fuer die eMail-Adresse des Benutzers.
	 * 
	 * @return mailAddress
	 */
	@XmlElement( name = "MailAddress" )
	@JsonProperty( "MailAddress" )
	public String getMailAddress()
	{
		return this.m_mailAddress;
	}

	/**
	 * Setter fuer die eMail-Adresse des Benutzers.
	 * 
	 * @param mailAddress
	 *            eMail-Adresse des Benutzers
	 */
	@JsonProperty( "MailAddress" )
	public void setMailAddress( String mailAddress )
	{
		this.m_mailAddress = mailAddress;
	}

	/**
	 * Getter fuer die Anzahl zu beaufsichtigender Pruefungen.
	 * 
	 * @return numSupervisions
	 */
	@XmlElement( name = "AchievableSupervisions" )
	@JsonProperty( "AchievableSupervisions" )
	public Integer getNumSupervisions()
	{
		return this.m_numSupervisions;
	}

	/**
	 * Setter fuer die Anzahl zu beaufsichtigender Pruefungen.
	 * 
	 * @param numSupervisions
	 *            Anzahl zu beaufsichtigender Pruefungen
	 */
	@JsonProperty( "AchievableSupervisions" )
	public void setNumSupervisions( Integer numSupervisions )
	{
		this.m_numSupervisions = numSupervisions;
	}

	/**
	 * Getter fuer die Rollen des Benutzers.
	 * 
	 * @return roles
	 */
	@XmlElement( name = "Role" )
	@JsonProperty( "Roles" )
	public List<EspRole> getRoles()
	{
		return this.m_roles;
	}

	/**
	 * Setter fuer die Rollen des Benutzers.
	 * 
	 * @param roles
	 *            Rollen des Benutzers
	 */
	@JsonProperty( "Roles" )
	public void setRoles( List<EspRole> roles )
	{
		this.m_roles = roles;
	}

	/**
	 * Fuegt den Rollen des Benutzers die uebergebene Rolle hinzu, falls diese nicht bereits in der Liste der Rollen
	 * vorhanden ist.
	 * 
	 * @param role
	 *            Rolle
	 */
	public void addRole( EspRole role )
	{
		if (!this.m_roles.contains( role ))
		{
			this.m_roles.add( role );
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.basic.object.AbstractBasicObject#hashCode()
	 */
	@Override
	public int hashCode()
	{
		HashCodeBuilder hcb = new HashCodeBuilder( 17, 37 );
		hcb.append( getLoginName() );
		hcb.append( getDisplayName() );
		hcb.append( isActivated() );
		hcb.append( getMailAddress() );
		hcb.append( getNumSupervisions() );
		hcb.append( getRoles() );

		return hcb.toHashCode();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.basic.object.AbstractBasicObject#equals(java.lang.Object)
	 */
	@Override
	public boolean equals( Object obj )
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (!(obj instanceof EspUser))
		{
			return false;
		}

		EspUser user = (EspUser) obj;

		EqualsBuilder eb = new EqualsBuilder();
		eb.append( getLoginName(), user.getLoginName() );
		eb.append( getDisplayName(), user.getDisplayName() );
		eb.append( getNumSupervisions(), user.getNumSupervisions() );
		eb.append( isActivated(), user.isActivated() );
		eb.append( getMailAddress(), user.getMailAddress() );
		eb.append( getRoles(), user.getRoles() );

		return eb.isEquals();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.basic.object.AbstractBasicObject#toString()
	 */
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append( "loginName: " ).append( getLoginName() );
		sb.append( ", displayName: " ).append( getDisplayName() );
		sb.append( ", numSupervisions: " ).append( getNumSupervisions() );
		sb.append( ", activated: " ).append( isActivated() );
		sb.append( ", mailAddress: " ).append( getMailAddress() );

		if (getRoles() != null)
		{
			sb.append( ", roles: [{" );

			for (EspRole role : getRoles())
			{
				sb.append( role.toString() ).append( "},{" );
			}

			sb.deleteCharAt( sb.length() - 1 ).deleteCharAt( sb.length() - 1 ).append( "]" );

		}
		else
		{
			sb.append( ", roles: [null]" );
		}

		return sb.toString();
	}
}
