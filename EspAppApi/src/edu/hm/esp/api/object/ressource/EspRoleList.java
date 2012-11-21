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
 * Klasse repraesentiert die Ressource Rollenliste.
 * 
 * @author Stefan Wörner
 */
@XmlRootElement( name = "Roles" )
@XmlType( propOrder = { "roles" } )
@XmlAccessorOrder( XmlAccessOrder.ALPHABETICAL )
@XmlAccessorType( XmlAccessType.NONE )
@JsonPropertyOrder( value = { "Roles" }, alphabetic = true )
@JsonSerialize( include = Inclusion.NON_NULL )
@Produces( { "application/xml", "application/json", "text/xml" } )
@Consumes( { "application/xml", "application/json", "text/xml" } )
public class EspRoleList extends AbstractEspRessourceObject
{

	private static final long serialVersionUID = -6034683937853190908L;

	private List<EspRole> m_roles;

	/**
	 * Standardkonstruktor.
	 */
	public EspRoleList()
	{
		this.m_roles = new ArrayList<EspRole>();
	}

	/**
	 * Getter fuer die Liste der Rollen.
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
	 * Setter fuer die Liste der Benutzer.
	 * 
	 * @param roles
	 *            Liste der Rollen
	 */
	@JsonProperty( "Roles" )
	public void setRoles( List<EspRole> roles )
	{
		this.m_roles = roles;
	}

	/**
	 * Fuegt der Liste der Rollen eine neue Rolle hinzu (falls diese noch nicht enthalten ist).
	 * 
	 * @param role
	 *            Hinzuzufuegender Benutzer
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
		if (!(obj instanceof EspRoleList))
		{
			return false;
		}

		EspRoleList roleList = (EspRoleList) obj;

		EqualsBuilder eb = new EqualsBuilder();
		eb.append( getRoles(), roleList.getRoles() );

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
		if (getRoles() != null)
		{
			sb.append( "roles: [{" );

			for (EspRole role : getRoles())
			{
				sb.append( role.toString() ).append( "},{" );
			}

			sb.deleteCharAt( sb.length() - 1 ).deleteCharAt( sb.length() - 1 ).append( "]" );
		}
		else
		{
			sb.append( "roles: [null]" );
		}

		return sb.toString();
	}
}
