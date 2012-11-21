package edu.hm.esp.api.object.ressource;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
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
 * Klasse repraesentiert die Ressource Pruefer.
 * 
 * @author Stefan Wörner
 */
@XmlRootElement( name = "Examiner" )
@XmlType( propOrder = { "loginName", "displayName" } )
@XmlAccessorOrder( XmlAccessOrder.ALPHABETICAL )
@XmlAccessorType( XmlAccessType.NONE )
@JsonPropertyOrder( value = { "ID", "DisplayName" }, alphabetic = true )
@JsonSerialize( include = Inclusion.NON_NULL )
@Produces( { "application/xml", "application/json", "text/xml" } )
@Consumes( { "application/xml", "application/json", "text/xml" } )
public class EspExaminer extends AbstractEspRessourceObject
{

	private static final long serialVersionUID = -1723150753974479825L;

	private String m_loginName;

	private String m_displayName;

	/**
	 * Getter fuer den Login Namen des Pruefers.
	 * 
	 * @return loginName
	 */
	@XmlAttribute( name = "ID" )
	@JsonProperty( "ID" )
	public String getLoginName()
	{
		return this.m_loginName;
	}

	/**
	 * Setter fuer den Login Namen des Pruefers.
	 * 
	 * @param loginName
	 *            Login Name des Pruefers
	 */
	@JsonProperty( "ID" )
	public void setLoginName( String loginName )
	{
		this.m_loginName = loginName;
	}

	/**
	 * Getter fuer den Anzeigenamen des Pruefers.
	 * 
	 * @return displayName
	 */
	@XmlAttribute( name = "DisplayName" )
	@JsonProperty( "DisplayName" )
	public String getDisplayName()
	{
		return this.m_displayName;
	}

	/**
	 * Setter fuer den Anzeigenamen des Pruefers.
	 * 
	 * @param displayName
	 *            Anzeigename des Pruefers
	 */
	@JsonProperty( "DisplayName" )
	public void setDisplayName( String displayName )
	{
		this.m_displayName = displayName;
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
		if (!(obj instanceof EspExaminer))
		{
			return false;
		}

		EspExaminer examiner = (EspExaminer) obj;

		EqualsBuilder eb = new EqualsBuilder();
		eb.append( getLoginName(), examiner.getLoginName() );
		eb.append( getDisplayName(), examiner.getDisplayName() );

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

		return sb.toString();
	}
}
