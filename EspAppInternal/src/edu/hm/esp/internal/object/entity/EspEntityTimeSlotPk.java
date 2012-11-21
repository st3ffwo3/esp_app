package edu.hm.esp.internal.object.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import edu.hm.esp.api.object.util.EspDateFormatter;
import edu.hm.esp.internal.exception.EspValidationException;
import edu.hm.esp.internal.object.AbstractEspEntityObject;

/**
 * Klasse implementiert den zusammengesetzten Primaerschluessel der {@link EspEntityTimeSlot} Entitaet.
 * 
 * @author Stefan Wörner
 */
@Embeddable
public class EspEntityTimeSlotPk extends AbstractEspEntityObject
{

	private static final long serialVersionUID = -5453107369987907804L;

	/*
	 * Primary Key
	 */
	private Date m_startDate;

	private Date m_endDate;

	/**
	 * Standardkonstruktor.
	 */
	public EspEntityTimeSlotPk()
	{
		super();
	}

	/**
	 * Konstruktur mit Pflicht-Attributen.
	 * 
	 * @param startDate
	 *            Startzeitpunkt des TimeSlots
	 * @param endDate
	 *            Endzeitpunkt des TimeSlots
	 */
	public EspEntityTimeSlotPk( Date startDate, Date endDate )
	{
		this();
		this.m_startDate = startDate;
		this.m_endDate = endDate;
	}

	/**
	 * Getter fuer den Startzeitpunkt des TimeSlots.
	 * 
	 * @return startDate
	 */
	@Column( name = "StartDate", nullable = false, length = 19 )
	public Date getStartDate()
	{
		return this.m_startDate;
	}

	/**
	 * Setter fuer den Startzeitpunkt des TimeSlots.
	 * 
	 * @param startDate
	 *            Startzeitpunkt des TimeSlots
	 */
	public void setStartDate( Date startDate )
	{
		this.m_startDate = startDate;
	}

	/**
	 * Getter fuer den Endzeitpunkt des TimeSlots.
	 * 
	 * @return endDate
	 */
	@Column( name = "EndDate", nullable = false, length = 19 )
	public Date getEndDate()
	{
		return this.m_endDate;
	}

	/**
	 * Setter fuer den Endzeitpunkt des TimeSlots.
	 * 
	 * @param endDate
	 *            Endzeitpunkt des TimeSlots
	 */
	public void setEndDate( Date endDate )
	{
		this.m_endDate = endDate;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.esp.internal.object.AbstractEspEntityObject#validate()
	 */
	@Override
	public void validate()
	{
		if (getStartDate() == null)
		{
			throw new EspValidationException( "Ung�ltiges Startdatum: '" + getStartDate() + "'!" );
		}

		if (getEndDate() == null)
		{
			throw new EspValidationException( "Ung�ltiges Enddatum: '" + getEndDate() + "'!" );
		}

		if (!(getStartDate().getTime() < getEndDate().getTime()))
		{
			throw new EspValidationException( "Das Startdatum muss nach dem Enddatum liegen!" );
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.esp.object.AbstractEspApiObject#hashCode()
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
	 * @see edu.hm.esp.object.AbstractEspApiObject#equals(java.lang.Object)
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
		if (!(obj instanceof EspEntityTimeSlotPk))
		{
			return false;
		}

		EspEntityTimeSlotPk pk = (EspEntityTimeSlotPk) obj;

		EqualsBuilder eb = new EqualsBuilder();
		eb.append( getStartDate(), pk.getStartDate() );
		eb.append( getEndDate(), pk.getEndDate() );

		return eb.isEquals();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.esp.object.AbstractEspApiObject#toString()
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
