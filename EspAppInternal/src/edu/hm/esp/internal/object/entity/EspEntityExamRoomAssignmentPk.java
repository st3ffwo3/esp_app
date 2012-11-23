package edu.hm.esp.internal.object.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import edu.hm.esp.internal.exception.EspValidationException;
import edu.hm.esp.internal.object.AbstractEspEntityObject;

/**
 * Klasse implementiert den zusammengesetzten Primaerschluessel der {@link EspEntityExamRoomAssignment} Entitaet.
 * 
 * @author Stefan Wörner
 */
@Embeddable
public class EspEntityExamRoomAssignmentPk extends AbstractEspEntityObject
{

	private static final long serialVersionUID = -934552031013202928L;

	/*
	 * Primary Key
	 */
	private String m_examCode;

	private String m_roomName;

	/**
	 * Standardkonstruktor.
	 */
	public EspEntityExamRoomAssignmentPk()
	{
		super();
	}

	/**
	 * Konstruktur mit Pflicht-Attributen.
	 * 
	 * @param examCode
	 *            Pruefungscode (ID)
	 * @param roomName
	 *            Raum in welchem die Pruefung stattfindet
	 */
	public EspEntityExamRoomAssignmentPk( String examCode, String roomName )
	{
		this();
		this.m_examCode = examCode;
		this.m_roomName = roomName;
	}

	/**
	 * Getter fuer den Pruefungscode (ID).
	 * 
	 * @return examCode
	 */
	@NotNull( message = "Pr�fungscode muss gesetzt sein" )
	@NotEmpty( message = "Pr�fungscode darf nicht leer sein" )
	@Pattern( regexp = "[\\d]{3}", message = "Ung�ltiger Pr�fungscode" )
	@Length( max = 5, message = "Pr�fungscode darf aus maximal 5 Ziffern bestehen" )
	@Column( name = "Exam_Code", nullable = false, length = 5 )
	public String getExamCode()
	{
		return this.m_examCode;
	}

	/**
	 * Setter fuer den Pruefungscode (ID).
	 * 
	 * @param examCode
	 *            Pruefungscode (ID)
	 */
	public void setExamCode( String examCode )
	{
		this.m_examCode = examCode;
	}

	/**
	 * Getter fuer den Raum in welchem die Pruefung stattfindet.
	 * 
	 * @return roomId
	 */
	@NotNull( message = "Raumname muss gesetzt sein" )
	@NotEmpty( message = "Raumname darf nicht leer sein" )
	@Pattern( regexp = "R[\\d]{1}.[\\d]{3}", message = "Ung�ltiger Raumname" )
	@Length( max = 10, message = "Raumname darf aus maximal 10 Zeichen bestehen" )
	@Column( name = "Room_Name", nullable = false, length = 10 )
	public String getRoomName()
	{
		return this.m_roomName;
	}

	/**
	 * Setter fuer den Raum in welchem die Pruefung stattfindet.
	 * 
	 * @param roomName
	 *            Raum in welchem die Pruefung stattfindet
	 */
	public void setRoomName( String roomName )
	{
		this.m_roomName = roomName;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.esp.internal.object.AbstractEspEntityObject#validate()
	 */
	@Override
	public void validate()
	{
		if (getExamCode() == null || getExamCode().isEmpty())
		{
			throw new EspValidationException( "Ung�ltige Pr�fungscode: '" + getExamCode() + "'!" );
		}

		if (getRoomName() == null || getRoomName().isEmpty())
		{
			throw new EspValidationException( "Ung�ltiger Raumname: '" + getRoomName() + "'!" );
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
		hcb.append( getExamCode() );
		hcb.append( getRoomName() );

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
		if (!(obj instanceof EspEntityExamRoomAssignmentPk))
		{
			return false;
		}

		EspEntityExamRoomAssignmentPk pk = (EspEntityExamRoomAssignmentPk) obj;

		EqualsBuilder eb = new EqualsBuilder();
		eb.append( getExamCode(), pk.getExamCode() );
		eb.append( getRoomName(), pk.getRoomName() );

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
		sb.append( "examCode: " ).append( getExamCode() );
		sb.append( ", roomName: " ).append( getRoomName() );

		return sb.toString();
	}
}
