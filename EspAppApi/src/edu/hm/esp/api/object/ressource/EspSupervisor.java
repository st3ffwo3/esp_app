package edu.hm.esp.api.object.ressource;

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
 * Klasse repraesentiert die Ressource Aufsichtsperson.
 * 
 * @author Stefan Wörner
 */
@XmlRootElement( name = "Supervisor" )
@XmlType( propOrder = { "loginName", "displayName", "numSupervisions" } )
@XmlAccessorOrder( XmlAccessOrder.ALPHABETICAL )
@XmlAccessorType( XmlAccessType.NONE )
@JsonPropertyOrder( value = { "ID", "DisplayName", "AchievableSupervisions" }, alphabetic = true )
@JsonSerialize( include = Inclusion.NON_NULL )
@Produces( { "application/xml", "application/json", "text/xml" } )
@Consumes( { "application/xml", "application/json", "text/xml" } )
public class EspSupervisor extends AbstractEspRessourceObject
{

	private static final long serialVersionUID = 7790294854299645742L;

	private String m_loginName;

	private String m_displayName;

	private Integer m_numSupervisions;

	/**
	 * Getter fuer den Login Namen der Aufsichtsperson.
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
	 * Setter fuer den Login Namen der Aufsichtsperson.
	 * 
	 * @param loginName
	 *            Login Name der Aufsichtsperson
	 */
	@JsonProperty( "ID" )
	public void setLoginName( String loginName )
	{
		this.m_loginName = loginName;
	}

	/**
	 * Getter fuer den Anzeigenamen der Aufsichtsperson.
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
	 * Setter fuer den Anzeigenamen der Aufsichtsperson.
	 * 
	 * @param displayName
	 *            Anzeigename des der Aufsichtsperson
	 */
	@JsonProperty( "DisplayName" )
	public void setDisplayName( String displayName )
	{
		this.m_displayName = displayName;
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
		hcb.append( getNumSupervisions() );

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
		if (!(obj instanceof EspSupervisor))
		{
			return false;
		}

		EspSupervisor supervisor = (EspSupervisor) obj;

		EqualsBuilder eb = new EqualsBuilder();
		eb.append( getLoginName(), supervisor.getLoginName() );
		eb.append( getDisplayName(), supervisor.getDisplayName() );
		eb.append( getNumSupervisions(), supervisor.getNumSupervisions() );

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

		return sb.toString();
	}
}
