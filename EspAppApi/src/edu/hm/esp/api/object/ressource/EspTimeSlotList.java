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
 * Klasse repraesentiert die Ressource Zeitslotliste.
 * 
 * @author Stefan Wörner
 */
@XmlRootElement( name = "TimeSlots" )
@XmlType( propOrder = { "timeSlots" } )
@XmlAccessorOrder( XmlAccessOrder.ALPHABETICAL )
@XmlAccessorType( XmlAccessType.NONE )
@JsonPropertyOrder( value = { "TimeSlots" }, alphabetic = true )
@JsonSerialize( include = Inclusion.NON_NULL )
@Produces( { "application/xml", "application/json", "text/xml" } )
@Consumes( { "application/xml", "application/json", "text/xml" } )
public class EspTimeSlotList extends AbstractEspRessourceObject
{

	private static final long serialVersionUID = 7665775349510367452L;

	private List<EspTimeSlot> m_timeSlots;

	/**
	 * Standardkonstruktor.
	 */
	public EspTimeSlotList()
	{
		this.m_timeSlots = new ArrayList<EspTimeSlot>();
	}

	/**
	 * Getter fuer die Liste der Zeitslots.
	 * 
	 * @return timeSlots
	 */
	@XmlElement( name = "TimeSlot" )
	@JsonProperty( "TimeSlots" )
	public List<EspTimeSlot> getTimeSlots()
	{
		return this.m_timeSlots;
	}

	/**
	 * Setter fuer die Liste der Zeitslots.
	 * 
	 * @param timeSlots
	 *            Liste der Zeitslots
	 */
	@JsonProperty( "TimeSlots" )
	public void setTimeSlots( List<EspTimeSlot> timeSlots )
	{
		this.m_timeSlots = timeSlots;
	}

	/**
	 * Fuegt der Liste der Zeitslots einen neuen Zeitslot hinzu (falls dieser noch nicht enthalten ist).
	 * 
	 * @param timeSlot
	 *            Hinzuzufuegender Zeitslot
	 */
	public void addTimeSlot( EspTimeSlot timeSlot )
	{
		if (!this.m_timeSlots.contains( timeSlot ))
		{
			this.m_timeSlots.add( timeSlot );
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
		hcb.append( getTimeSlots() );

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
		if (!(obj instanceof EspTimeSlotList))
		{
			return false;
		}

		EspTimeSlotList timeSlotList = (EspTimeSlotList) obj;

		EqualsBuilder eb = new EqualsBuilder();
		eb.append( getTimeSlots(), timeSlotList.getTimeSlots() );

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
		if (getTimeSlots() != null)
		{
			sb.append( "timeSlot: [{" );

			for (EspTimeSlot timeSlot : getTimeSlots())
			{
				sb.append( timeSlot.toString() ).append( "},{" );
			}

			sb.deleteCharAt( sb.length() - 1 ).deleteCharAt( sb.length() - 1 ).append( "]" );
		}
		else
		{
			sb.append( "timeSlot: [null]" );
		}

		return sb.toString();
	}
}
