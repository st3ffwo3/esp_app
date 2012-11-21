package edu.hm.esp.internal.object.entity;

import java.util.ArrayList;
import java.util.Date;
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
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import edu.hm.esp.api.object.util.EspDateFormatter;
import edu.hm.esp.internal.exception.EspValidationException;
import edu.hm.esp.internal.object.AbstractEspEntityObject;

/**
 * Entitaet zur ExamRoomAssignment Tabelle.
 * 
 * @author Stefan Wörner
 */
@Entity
@Table( name = "ExamRoomAssignment", catalog = "ESP" )
public class EspEntityExamRoomAssignment extends AbstractEspEntityObject
{

	private static final long serialVersionUID = -473400349798078842L;

	/*
	 * Primary Key
	 */
	private EspEntityExamRoomAssignmentPk m_pk;

	private EspEntityExam m_exam;

	private EspEntityRoom m_room;

	/*
	 * Optional
	 */
	private Date m_startDate;

	private Date m_endDate;

	/*
	 * References / Relations
	 */
	private List<EspEntityUser> m_supervisors;

	/**
	 * Standardkonstruktor.
	 */
	public EspEntityExamRoomAssignment()
	{
		super();
		this.m_supervisors = new ArrayList<EspEntityUser>();
	}

	/**
	 * Konstruktur mit Pflicht-Attributen.
	 * 
	 * @param pk
	 *            Zusammengesetzter Primaerschluessel der Pruefungs-Raum-Zuordnung
	 * @param exam
	 *            Pruefung
	 * @param room
	 *            Raum
	 */
	public EspEntityExamRoomAssignment( EspEntityExamRoomAssignmentPk pk, EspEntityExam exam, EspEntityRoom room )
	{
		this();
		this.m_pk = pk;
		this.m_exam = exam;
		this.m_room = room;
	}

	/**
	 * Konstruktur mit allen Attributen.
	 * 
	 * @param pk
	 *            Zusammengesetzter Primaerschluessel der Pruefungs-Raum-Zuordnung
	 * @param exam
	 *            Pruefung
	 * @param room
	 *            Raum
	 * @param startDate
	 *            Startzeitpunkt der Pruefung
	 * @param endDate
	 *            Endzeitpunkt der Pruefung
	 * @param supervisors
	 *            Liste der Aufsichtspersonen welche der Pruefungs-Raum-Zuordnung zugeordnet sind
	 */
	public EspEntityExamRoomAssignment( EspEntityExamRoomAssignmentPk pk, EspEntityExam exam, EspEntityRoom room, Date startDate,
			Date endDate, List<EspEntityUser> supervisors )
	{
		this( pk, exam, room );
		this.m_startDate = startDate;
		this.m_endDate = endDate;
		this.m_supervisors = supervisors;
	}

	/**
	 * Getter fuer den zusammengesetzten Primaerschluessel der Pruefungs-Raum-Zuordnung.
	 * 
	 * @return pk
	 */
	@Valid
	@EmbeddedId
	@AttributeOverrides( { @AttributeOverride( name = "m_examCode", column = @Column( name = "Exam_Code", nullable = false ) ),
			@AttributeOverride( name = "m_roomName", column = @Column( name = "Room_Name", nullable = false ) ) } )
	public EspEntityExamRoomAssignmentPk getPk()
	{
		return this.m_pk;
	}

	/**
	 * Setter fuer den zusammengesetzten Primaerschluessel der Pruefungs-Raum-Zuordnung.
	 * 
	 * @param pk
	 *            Zusammengesetzter Primaerschluessel der Pruefungs-Raum-Zuordnung
	 */
	public void setPk( EspEntityExamRoomAssignmentPk pk )
	{
		this.m_pk = pk;
	}

	/**
	 * Getter fuer die Pruefung.
	 * 
	 * @return exam
	 */
	@Valid
	@ManyToOne( fetch = FetchType.LAZY )
	@JoinColumn( name = "Exam_Code", nullable = false, insertable = false, updatable = false )
	public EspEntityExam getExam()
	{
		return this.m_exam;
	}

	/**
	 * Setter fuer die Pruefung.
	 * 
	 * @param exam
	 *            Pruefung
	 */
	public void setExam( EspEntityExam exam )
	{
		this.m_exam = exam;
		this.m_pk.setExamCode( exam.getCode() );
	}

	/**
	 * Getter fuer den Raum.
	 * 
	 * @return room
	 */
	@Valid
	@ManyToOne( fetch = FetchType.LAZY )
	@JoinColumn( name = "Room_Name", nullable = false, insertable = false, updatable = false )
	public EspEntityRoom getRoom()
	{
		return this.m_room;
	}

	/**
	 * Setter fuer den Raum.
	 * 
	 * @param room
	 *            Raum
	 */
	public void setRoom( EspEntityRoom room )
	{
		this.m_room = room;
		this.m_pk.setRoomName( room.getName() );
	}

	/**
	 * Getter fuer den Startzeitpunkt der Pruefung.
	 * 
	 * @return startDate
	 */
	@Temporal( TemporalType.TIMESTAMP )
	@Column( name = "StartDate", nullable = false, length = 19 )
	public Date getStartDate()
	{
		return this.m_startDate;
	}

	/**
	 * Setter fuer den Startzeitpunkt der Pruefung.
	 * 
	 * @param startDate
	 *            Startzeitpunkt der Pruefung
	 */
	public void setStartDate( Date startDate )
	{
		this.m_startDate = startDate;
	}

	/**
	 * Getter fuer den Endzeitpunkt der Pruefung.
	 * 
	 * @return endDate
	 */
	@Temporal( TemporalType.TIMESTAMP )
	@Column( name = "EndDate", nullable = false, length = 19 )
	public Date getEndDate()
	{
		return this.m_endDate;
	}

	/**
	 * Setter fuer den Endzeitpunkt der Pruefung.
	 * 
	 * @param endDate
	 *            Endzeitpunkt der Pruefung
	 */
	public void setEndDate( Date endDate )
	{
		this.m_endDate = endDate;
	}

	/**
	 * Getter fuer die Liste der Aufsichtspersonen welche der Pruefungs-Raum-Zuordnung zugeordnet sind.
	 * 
	 * @return supervisors
	 */
	@Valid
	@ManyToMany( fetch = FetchType.LAZY )
	@JoinTable( name = "UserRoomSupervision",
				catalog = "ESP",
				joinColumns = { @JoinColumn( name = "Exam_Code", nullable = false, updatable = false ),
						@JoinColumn( name = "Room_Name", nullable = false, updatable = false ) },
				inverseJoinColumns = { @JoinColumn( name = "Supervisor_LoginName", nullable = false, updatable = false ) } )
	public List<EspEntityUser> getSupervisors()
	{
		return this.m_supervisors;
	}

	/**
	 * Setter fuer die Liste der Aufsichtspersonen welche der Pruefungs-Raum-Zuordnung zugeordnet sind.
	 * 
	 * @param supervisors
	 *            Liste der Aufsichtspersonen welche der Pruefungs-Raum-Zuordnung zugeordnet sind
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

		if (getExam() == null)
		{
			throw new EspValidationException( "Ung�ltige Pr�fung: '" + getExam() + "'!" );
		}
		else
		{
			getExam().validate();
		}

		if (getRoom() == null)
		{
			throw new EspValidationException( "Ung�ltiger Raum: '" + getRoom() + "'!" );
		}
		else
		{
			getRoom().validate();
		}

		if (getStartDate() != null && getEndDate() != null && !(getStartDate().getTime() < getEndDate().getTime()))
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
		hcb.append( getPk() );
		hcb.append( getExam() );
		hcb.append( getRoom() );
		// hcb.append( getStartDate() );
		// hcb.append( getEndDate() );

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
		if (!(obj instanceof EspEntityExamRoomAssignment))
		{
			return false;
		}

		EspEntityExamRoomAssignment assignment = (EspEntityExamRoomAssignment) obj;

		EqualsBuilder eb = new EqualsBuilder();
		eb.append( getPk(), assignment.getPk() );
		eb.append( getExam(), assignment.getExam() );
		eb.append( getRoom(), assignment.getRoom() );
		// eb.append( getStartDate(), assignment.getStartDate() );
		// eb.append( getEndDate(), assignment.getEndDate() );

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
		sb.append( ", exam: [" ).append( getExam() ).append( "]" );
		sb.append( ", room: [" ).append( getRoom() ).append( "]" );
		sb.append( ", startDate: " ).append( EspDateFormatter.toString( getStartDate() ) );
		sb.append( ", endDate: " ).append( EspDateFormatter.toString( getEndDate() ) );

		return sb.toString();
	}
}
