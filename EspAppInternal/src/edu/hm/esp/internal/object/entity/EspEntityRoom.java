package edu.hm.esp.internal.object.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotEmpty;

import edu.hm.esp.internal.exception.EspValidationException;
import edu.hm.esp.internal.object.AbstractEspEntityObject;

/**
 * Entitaet zur Room Tabelle.
 * 
 * @author Stefan Wörner
 */
@Entity
@Table( name = "Room", catalog = "ESP" )
public class EspEntityRoom extends AbstractEspEntityObject
{

	private static final long serialVersionUID = 239374741083255619L;

	/*
	 * Primary Key
	 */
	private String m_name;

	/*
	 * References / Relations
	 */
	private List<EspEntityExamRoomAssignment> m_examAssignments;

	/**
	 * Standardkonstruktor.
	 */
	public EspEntityRoom()
	{
		super();
		this.m_examAssignments = new ArrayList<EspEntityExamRoomAssignment>();
	}

	/**
	 * Konstruktur mit Pflicht-Attributen.
	 * 
	 * @param name
	 *            Name / Nummer des Raumes
	 */
	public EspEntityRoom( String name )
	{
		this();
		this.m_name = name;
	}

	/**
	 * Konstruktur mit allen Attributen.
	 * 
	 * @param name
	 *            Name / Nummer des Raumes
	 * @param examRoomAssignments
	 *            Liste der Pruefungen die in diesem Raum durchgefuehrt werden
	 */
	public EspEntityRoom( String name, List<EspEntityExamRoomAssignment> examRoomAssignments )
	{
		this( name );
		this.m_examAssignments = examRoomAssignments;
	}

	/**
	 * Getter fuer den Namen / Nummer des Raumes.
	 * 
	 * @return name
	 */
	@Id
	@NotNull( message = "Raumname muss gesetzt sein" )
	@NotEmpty( message = "Raumname darf nicht leer sein" )
	@Pattern( regexp = "R[\\d]{1}.[\\d]{3}", message = "Ung�ltiger Raumname" )
	@Length( max = 10, message = "Raumname darf aus maximal 10 Zeichen bestehen" )
	@Column( name = "Name", nullable = false, length = 10 )
	public String getName()
	{
		return this.m_name;
	}

	/**
	 * Setter fuer den Namen / Nummer des Raumes.
	 * 
	 * @param name
	 *            Name / Nummer des Raumes
	 */
	public void setName( String name )
	{
		this.m_name = name;
	}

	/**
	 * Getter fuer die Liste der Pruefungen die in diesem Raum durchgefuehrt werden.
	 * 
	 * @return examRoomAssignments
	 */
	@Valid
	@OneToMany( fetch = FetchType.LAZY, mappedBy = "room" )
	public List<EspEntityExamRoomAssignment> getExamAssignments()
	{
		return this.m_examAssignments;
	}

	/**
	 * Setter fuer die Liste der Pruefungen die in diesem Raum durchgefuehrt werden.
	 * 
	 * @param examAssignments
	 *            Liste der Pruefungen die in diesem Raum durchgefuehrt werden
	 */
	public void setExamAssignments( List<EspEntityExamRoomAssignment> examAssignments )
	{
		this.m_examAssignments = examAssignments;
	}

	/**
	 * Fuegt der Liste der zugeordneten Pruefungen welche im Raum durchgefuehrt werden eine neue Zuordnung hinzu.
	 * 
	 * @param examAssignment
	 *            Neu zuzuordnende Pruefung welche im Raum durchgefuehrt wird
	 */
	public void addExamAssignment( EspEntityExamRoomAssignment examAssignment )
	{
		if (this.m_examAssignments == null)
		{
			this.m_examAssignments = new ArrayList<EspEntityExamRoomAssignment>();
		}

		if (!this.m_examAssignments.contains( examAssignment ))
		{
			this.m_examAssignments.add( examAssignment );
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
		if (getName() == null || getName().isEmpty())
		{
			throw new EspValidationException( "Ung�ltiger Raumname: '" + getName() + "'!" );
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
		hcb.append( getName() );

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
		if (!(obj instanceof EspEntityRoom))
		{
			return false;
		}

		EspEntityRoom room = (EspEntityRoom) obj;

		EqualsBuilder eb = new EqualsBuilder();
		eb.append( getName(), room.getName() );

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
		sb.append( "name: " ).append( getName() );

		return sb.toString();
	}
}
