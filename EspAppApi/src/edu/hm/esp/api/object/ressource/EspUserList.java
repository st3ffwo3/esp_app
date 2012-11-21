package edu.hm.esp.api.object.ressource;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlAccessorType;
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
 * Klasse repraesentiert die Ressource Benutzerliste.
 * 
 * @author Stefan Wörner
 */
@XmlRootElement( name = "Users" )
@XmlType( propOrder = { "users" } )
@XmlAccessorOrder( XmlAccessOrder.ALPHABETICAL )
@XmlAccessorType( XmlAccessType.NONE )
@JsonPropertyOrder( value = { "Users" }, alphabetic = true )
@JsonSerialize( include = Inclusion.NON_NULL )
@Produces( { "application/xml", "application/json", "text/xml" } )
@Consumes( { "application/xml", "application/json", "text/xml" } )
public class EspUserList extends AbstractEspRessourceObject
{

	private static final long serialVersionUID = -6034683937853190908L;

	private List<EspUser> m_users;

	/**
	 * Standardkonstruktor.
	 */
	public EspUserList()
	{
		this.m_users = new ArrayList<EspUser>();
	}

	/**
	 * Getter fuer die Liste der Benutzer.
	 * 
	 * @return users
	 */
	@XmlElement( name = "User" )
	@JsonProperty( "Users" )
	public List<EspUser> getUsers()
	{
		return this.m_users;
	}

	/**
	 * Setter fuer die Liste der Benutzer.
	 * 
	 * @param users
	 *            Liste der Benutzer
	 */
	@JsonProperty( "Users" )
	public void setUsers( List<EspUser> users )
	{
		this.m_users = users;
	}

	/**
	 * Fuegt der Liste der Benutzer einen neuen Benutzer hinzu (falls dieser noch nicht enthalten ist).
	 * 
	 * @param user
	 *            Hinzuzufuegender Benutzer
	 */
	public void addUser( EspUser user )
	{
		if (!this.m_users.contains( user ))
		{
			this.m_users.add( user );
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
		hcb.append( getUsers() );

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
		if (!(obj instanceof EspUserList))
		{
			return false;
		}

		EspUserList userList = (EspUserList) obj;

		EqualsBuilder eb = new EqualsBuilder();
		eb.append( getUsers(), userList.getUsers() );

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
		if (getUsers() != null)
		{
			sb.append( "users: [{" );

			for (EspUser user : getUsers())
			{
				sb.append( user.toString() ).append( "},{" );
			}

			sb.deleteCharAt( sb.length() - 1 ).deleteCharAt( sb.length() - 1 ).append( "]" );
		}
		else
		{
			sb.append( "users: [null]" );
		}

		return sb.toString();
	}
}
