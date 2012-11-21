package edu.hm.esp.internal.object.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Max;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import edu.hm.esp.internal.exception.EspValidationException;
import edu.hm.esp.internal.object.AbstractEspEntityObject;

/**
 * Entitaet zur TimeSlot Tabelle.
 * 
 * @author Stefan Wörner
 */
@Entity
@Table( name = "TimeSlot", catalog = "ESP" )
public class EspEntityTimeSlot extends AbstractEspEntityObject
{

	private static final long serialVersionUID = 3124387305133581547L;

	/*
	 * Primary Key
	 */
	private EspEntityTimeSlotPk m_pk;

	/*
	 * Optional
	 */
	private Integer m_numSupervisors;

	/*
	 * References / Relations
	 */
	private List<EspEntityUser> m_supervisors;

	/**
	 * Standardkonstruktor.
	 */
	public EspEntityTimeSlot()
	{
		super();
		this.m_supervisors = new ArrayList<EspEntityUser>();
	}

	/**
	 * Konstruktur mit Pflicht-Attributen.
	 * 
	 * @param pk
	 *            Zusammengesetzter Primaerschluessel des TimeSlots
	 */
	public EspEntityTimeSlot( EspEntityTimeSlotPk pk )
	{
		this();
		this.m_pk = pk;
	}

	/**
	 * Konstruktur mit allen Attributen.
	 * 
	 * @param pk
	 *            Zusammengesetzter Primaerschluessel des TimeSlots
	 * @param numSupervisors
	 *            Gesamtzahl benoetigter Aufsichtspersonen fuer den TimeSlot
	 * @param supervisors
	 *            Liste der Aufsichtspersonen welche dem TimeSlot zugeordnet sind
	 */
	public EspEntityTimeSlot( EspEntityTimeSlotPk pk, Integer numSupervisors, List<EspEntityUser> supervisors )
	{
		this( pk );
		this.m_numSupervisors = numSupervisors;
		this.m_supervisors = supervisors;
	}

	/**
	 * Getter fuer den zusammengesetzten Primaerschluessel des TimeSlots.
	 * 
	 * @return pk
	 */
	@Valid
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride( name = "m_startDate", column = @Column( name = "StartDate", nullable = false, length = 19 ) ),
			@AttributeOverride( name = "m_endDate", column = @Column( name = "EndDate", nullable = false, length = 19 ) ) } )
	public EspEntityTimeSlotPk getPk()
	{
		return this.m_pk;
	}

	/**
	 * Setter fuer den zusammengesetzten Primaerschluessel des TimeSlots.
	 * 
	 * @param pk
	 *            Zusammengesetzter Primaerschluessel des TimeSlots
	 */
	public void setPk( EspEntityTimeSlotPk pk )
	{
		this.m_pk = pk;
	}

	/**
	 * Getter fuer die Gesamtzahl benoetigter Aufsichtspersonen fuer den TimeSlot.
	 * 
	 * @return numSupervisors
	 */
	@Max( value = 100, message = "Anzahl ben�tigter Aufsichtspersonenen darf maximal 100 sein" )
	@Column( name = "NumSupervisors" )
	public Integer getNumSupervisors()
	{
		return this.m_numSupervisors;
	}

	/**
	 * Setter fuer die Gesamtzahl benoetigter Aufsichtspersonen fuer den TimeSlot.
	 * 
	 * @param numSupervisors
	 *            Gesamtzahl benoetigter Aufsichtspersonen fuer den TimeSlot
	 */
	public void setNumSupervisors( Integer numSupervisors )
	{
		this.m_numSupervisors = numSupervisors;
	}

	/**
	 * Getter fuer die Liste der Aufsichtspersonen welche dem TimeSlot zugeordnet sind.
	 * 
	 * @return supervisors
	 */
	@Valid
	@ManyToMany( fetch = FetchType.LAZY )
	@JoinTable( name = "UserTimeSlotSupervision",
				catalog = "ESP",
				joinColumns = {
						@JoinColumn(	name = "TimeSlot_StartDate",
										referencedColumnName = "StartDate",
										nullable = false,
										updatable = false ),
						@JoinColumn(	name = "TimeSlot_EndDate",
										referencedColumnName = "EndDate",
										nullable = false,
										updatable = false ) },
				inverseJoinColumns = { @JoinColumn( name = "Supervisor_LoginName", nullable = false, updatable = false ) } )
	public List<EspEntityUser> getSupervisors()
	{
		return this.m_supervisors;
	}

	/**
	 * Setter fuer die Liste der Aufsichtspersonen welche dem TimeSlot zugeordnet sind.
	 * 
	 * @param supervisors
	 *            Liste der Aufsichtspersonen welche dem TimeSlot zugeordnet sind
	 */
	public void setSupervisors( List<EspEntityUser> supervisors )
	{
		this.m_supervisors = supervisors;
	}

	/**
	 * Fuegt der Liste der Aufsichtspersonen hinzu, falls diese noch nicht vorhanden ist.
	 * 
	 * @param supervisor
	 *            Hinzuzufuegende Aufsichtsperson
	 */
	public void addSupervisor( EspEntityUser supervisor )
	{
		if (this.m_supervisors == null)
		{
			this.m_supervisors = new ArrayList<EspEntityUser>();
		}

		if (!this.m_supervisors.contains( supervisor ))
		{
			this.m_supervisors.add( supervisor );
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.esp.internal.object.AbstractEspEntityObject#validate()
	 */
	@Override
	public void validate()
	{
		if (getPk() == null)
		{
			throw new EspValidationException( "Ung�ltiger Prim�rschl�ssel: '" + getPk() + "'!" );
		}
		else
		{
			getPk().validate();
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
		hcb.append( getPk() );
		// hcb.append( getNumSupervisors() );

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
		if (!(obj instanceof EspEntityTimeSlot))
		{
			return false;
		}

		EspEntityTimeSlot ts = (EspEntityTimeSlot) obj;

		EqualsBuilder eb = new EqualsBuilder();
		eb.append( getPk(), ts.getPk() );
		// eb.append( getNumSupervisors(), ts.getNumSupervisors() );

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
		sb.append( "pk: [" ).append( getPk() ).append( "]" );
		sb.append( ", numSupervisors: [" ).append( getNumSupervisors() ).append( "]" );

		return sb.toString();
	}
}
