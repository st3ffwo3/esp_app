package edu.hm.esp.api.object.ressource;

import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import edu.hm.esp.api.jaxb.adapter.EspDateAdapter;
import edu.hm.esp.api.object.AbstractEspRessourceObject;
import edu.hm.esp.api.object.util.EspDateFormatter;

/**
 * Klasse repraesentiert die Ressource Zeitslotdatum.
 * 
 * @author Stefan Wörner
 */
@XmlRootElement( name = "SlotDate" )
@XmlType( propOrder = { "startDate", "endDate" } )
@XmlAccessorOrder( XmlAccessOrder.ALPHABETICAL )
@XmlAccessorType( XmlAccessType.NONE )
@JsonPropertyOrder( value = { "FromDate", "TillDate" }, alphabetic = true )
@JsonSerialize( include = Inclusion.NON_NULL )
@Produces( { "application/xml", "application/json", "text/xml" } )
@Consumes( { "application/xml", "application/json", "text/xml" } )
public class EspTimeSlotDate extends AbstractEspRessourceObject
{

	private static final long serialVersionUID = 8211039835761143535L;

	private Date m_startDate;

	private Date m_endDate;

	/**
	 * Getter fuer den Startzeitpunkt.
	 * 
	 * @return startDate
	 */
	@XmlAttribute( name = "FromDate" )
	@JsonProperty( "FromDate" )
	@XmlJavaTypeAdapter( EspDateAdapter.class )
	public Date getStartDate()
	{
		return this.m_startDate;
	}

	/**
	 * Setter fuer den Startzeitpunkt.
	 * 
	 * @param startDate
	 *            Startzeitpunkt
	 */
	@JsonProperty( "FromDate" )
	public void setStartDate( Date startDate )
	{
		this.m_startDate = startDate;
	}

	/**
	 * Getter fuer den Endzeitpunkt.
	 * 
	 * @return endDate
	 */
	@XmlAttribute( name = "TillDate" )
	@JsonProperty( "TillDate" )
	@XmlJavaTypeAdapter( EspDateAdapter.class )
	public Date getEndDate()
	{
		return this.m_endDate;
	}

	/**
	 * Setter fuer den Endzeitpunkt.
	 * 
	 * @param endDate
	 *            Endzeitpunkt
	 */
	@JsonProperty( "TillDate" )
	public void setEndDate( Date endDate )
	{
		this.m_endDate = endDate;
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
		hcb.append( getStartDate() );
		hcb.append( getEndDate() );

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
		if (!(obj instanceof EspTimeSlotDate))
		{
			return false;
		}

		EspTimeSlotDate slotDate = (EspTimeSlotDate) obj;

		EqualsBuilder eb = new EqualsBuilder();
		eb.append( getStartDate(), slotDate.getStartDate() );
		eb.append( getEndDate(), slotDate.getEndDate() );

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
		sb.append( "startDate: " ).append( EspDateFormatter.toString( getStartDate() ) );
		sb.append( ", endDate: " ).append( EspDateFormatter.toString( getEndDate() ) );

		return sb.toString();
	}
}
