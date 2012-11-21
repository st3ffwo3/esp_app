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
 * Klasse repraesentiert die Ressource Zeitslot.
 * 
 * @author Stefan Wörner
 */
@XmlRootElement( name = "TimeSlot" )
@XmlType( propOrder = { "timeSlotDate", "numSupervisors", "currentSupervisors" } )
@XmlAccessorOrder( XmlAccessOrder.ALPHABETICAL )
@XmlAccessorType( XmlAccessType.NONE )
@JsonPropertyOrder( value = { "SlotDate", "NecessarySupervisors", "Supervisors" }, alphabetic = true )
@JsonSerialize( include = Inclusion.NON_NULL )
@Produces( { "application/xml", "application/json", "text/xml" } )
@Consumes( { "application/xml", "application/json", "text/xml" } )
public class EspTimeSlot extends AbstractEspRessourceObject
{

	private static final long serialVersionUID = 9100151926381454577L;

	private EspTimeSlotDate m_timeSlotDate;

	private Integer m_numSupervisors;

	private List<EspSupervisor> m_currentSupervisors;

	/**
	 * Standardkonstruktor.
	 */
	public EspTimeSlot()
	{
		this.m_currentSupervisors = new ArrayList<EspSupervisor>();
	}

	/**
	 * Getter fuer das Zeitslotdatum.
	 * 
	 * @return examDate
	 */
	@XmlElement( name = "SlotDate" )
	@JsonProperty( "SlotDate" )
	public EspTimeSlotDate getTimeSlotDate()
	{
		return this.m_timeSlotDate;
	}

	/**
	 * Setter fuer das Zeitslotdatum.
	 * 
	 * @param timeSlotDate
	 *            Pruefungsdatum
	 */
	@JsonProperty( "SlotDate" )
	public void setTimeSlotDate( EspTimeSlotDate timeSlotDate )
	{
		this.m_timeSlotDate = timeSlotDate;
	}

	/**
	 * Getter fuer die Anzahl der benoetigten Aufsichtspersonen.
	 * 
	 * @return numSupervisors
	 */
	@XmlElement( name = "NecessarySupervisors" )
	@JsonProperty( "NecessarySupervisors" )
	public Integer getNumSupervisors()
	{
		return this.m_numSupervisors;
	}

	/**
	 * Setter fuer die Anzahl der benoetigten Aufsichtspersonen.
	 * 
	 * @param numSupervisors
	 *            Anzahl der benoetigten Aufsichtspersonen
	 */
	@JsonProperty( "NecessarySupervisors" )
	public void setNumSupervisors( Integer numSupervisors )
	{
		this.m_numSupervisors = numSupervisors;
	}

	/**
	 * Getter fuer die Liste der aktuell eingetragenen Aufsichtspersonen.
	 * 
	 * @return currentSupervisors
	 */
	@XmlElement( name = "Supervisor" )
	@JsonProperty( "Supervisors" )
	public List<EspSupervisor> getCurrentSupervisors()
	{
		return this.m_currentSupervisors;
	}

	/**
	 * Setter fuer die Liste der aktuell eingetragenen Aufsichtspersonen.
	 * 
	 * @param currentSupervisors
	 *            Liste der aktuell eingetragenen Aufsichtspersonen
	 */
	@JsonProperty( "Supervisors" )
	public void setCurrentSupervisors( List<EspSupervisor> currentSupervisors )
	{
		this.m_currentSupervisors = currentSupervisors;
	}

	/**
	 * Fuegt der Liste der Aufsichtspersonen fuer den Zeitslot eine neue Aufsichtsperson hinzu (falls diese noch nicht
	 * enthalten ist).
	 * 
	 * @param supervisor
	 *            Hinzuzufuegende Aufsichtsperson
	 */
	public void addSupervisor( EspSupervisor supervisor )
	{
		if (!this.m_currentSupervisors.contains( supervisor ))
		{
			this.m_currentSupervisors.add( supervisor );
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
		hcb.append( getTimeSlotDate() );
		hcb.append( getNumSupervisors() );
		hcb.append( getCurrentSupervisors() );

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
		if (!(obj instanceof EspTimeSlot))
		{
			return false;
		}

		EspTimeSlot timeSlot = (EspTimeSlot) obj;

		EqualsBuilder eb = new EqualsBuilder();
		eb.append( getTimeSlotDate(), timeSlot.getTimeSlotDate() );
		eb.append( getNumSupervisors(), timeSlot.getNumSupervisors() );
		eb.append( getCurrentSupervisors(), timeSlot.getCurrentSupervisors() );

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
		if (getTimeSlotDate() != null)
		{
			sb.append( "slotDate: " ).append( getTimeSlotDate().toString() );
		}
		else
		{
			sb.append( "slotDate: null" );
		}
		sb.append( ", numSupervisors: " ).append( getNumSupervisors() );

		if (getCurrentSupervisors() != null)
		{
			sb.append( ", currentSupervisors: [{" );

			for (EspSupervisor supervisor : getCurrentSupervisors())
			{
				sb.append( supervisor.toString() ).append( "},{" );
			}

			sb.deleteCharAt( sb.length() - 1 ).deleteCharAt( sb.length() - 1 ).append( "]" );
		}
		else
		{
			sb.append( ", currentSupervisors: [null]" );
		}

		return sb.toString();
	}
}
