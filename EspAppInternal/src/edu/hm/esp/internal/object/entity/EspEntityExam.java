package edu.hm.esp.internal.object.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import edu.hm.esp.internal.exception.EspValidationException;
import edu.hm.esp.internal.object.AbstractEspEntityObject;

/**
 * Entitaet zur Exam Tabelle.
 * 
 * @author Stefan Wörner
 */
@Entity
@Table( name = "Exam", catalog = "ESP" )
public class EspEntityExam extends AbstractEspEntityObject
{

	private static final long serialVersionUID = -6374355125745535080L;

	/*
	 * Primary Key
	 */
	private String m_code;

	/*
	 * Mandatory
	 */
	private String m_name;

	private EspEntityUser m_examiner;

	/*
	 * Optional
	 */
	private Integer m_numSupervisors;

	/*
	 * References / Relations
	 */
	private List<EspEntityExamRoomAssignment> m_roomAssignments;

	/**
	 * Standardkonstruktor.
	 */
	public EspEntityExam()
	{
		super();
		this.m_roomAssignments = new ArrayList<EspEntityExamRoomAssignment>();
	}

	/**
	 * Konstruktur mit Pflicht-Attributen.
	 * 
	 * @param code
	 *            Pruefungscode (ID)
	 * @param name
	 *            Name der Pruefung
	 * @param examiner
	 *            Pruefungssteller/-in
	 */
	public EspEntityExam( String code, String name, EspEntityUser examiner )
	{
		this();
		this.m_code = code;
		this.m_name = name;
		this.m_examiner = examiner;
	}

	/**
	 * Konstruktur mit allen Attributen.
	 * 
	 * @param code
	 *            Pruefungscode (ID)
	 * @param name
	 *            Name der Pruefung
	 * @param examiner
	 *            Pruefungssteller/-in
	 * @param numSupervisors
	 *            Anzahl benoetigter Aufsichtspersonen
	 * @param roomAssignments
	 *            Liste der zugeordneten Raeume in denen die Pruefung durchgefuehrt wird
	 */
	public EspEntityExam( String code, String name, EspEntityUser examiner, int numSupervisors,
			List<EspEntityExamRoomAssignment> roomAssignments )
	{
		this( code, name, examiner );
		this.m_numSupervisors = numSupervisors;
		this.m_examiner = examiner;
		this.m_roomAssignments = roomAssignments;
	}

	/**
	 * Getter fuer den Pruefungscode (ID).
	 * 
	 * @return id
	 */
	@Id
	@NotNull( message = "Pr�fungscode muss gesetzt sein" )
	@NotEmpty( message = "Pr�fungscode darf nicht leer sein" )
	@Pattern( regexp = "[\\d]{3}", message = "Ung�ltiger Pr�fungscode" )
	@Length( max = 5, message = "Pr�fungscode darf aus maximal 5 Zeichen bestehen" )
	@Column( name = "Code", nullable = false, length = 5 )
	public String getCode()
	{
		return this.m_code;
	}

	/**
	 * Setter fuer den Pruefungscode (ID).
	 * 
	 * @param code
	 *            Pruefungscode (ID)
	 */
	public void setCode( String code )
	{
		this.m_code = code;
	}

	/**
	 * Getter fuer den Namen der Pruefung.
	 * 
	 * @return name
	 */
	@NotEmpty( message = "Pr�fungsname darf nicht leer sein" )
	@Pattern( regexp = "[\\p{L}\\p{Space}\\p{Digit}-]+", message = "Ung�ltiger Pr�fungsname" )
	@Length( max = 200, message = "Pr�fungsname darf aus maximal 200 Zeichen bestehen" )
	@Column( name = "Name", nullable = false, length = 200 )
	public String getName()
	{
		return this.m_name;
	}

	/**
	 * Setter fuer den Namen der Pruefung.
	 * 
	 * @param name
	 *            Name der Pruefung
	 */
	public void setName( String name )
	{
		this.m_name = name;
	}

	/**
	 * Getter fuer den/die Pruefungssteller/-in.
	 * 
	 * @return examiner
	 */
	@Valid
	@ManyToOne( fetch = FetchType.EAGER )
	@JoinColumn( name = "Examiner_LoginName", nullable = false )
	public EspEntityUser getExaminer()
	{
		return this.m_examiner;
	}

	/**
	 * Setter fuer den/die Pruefungssteller/-in.
	 * 
	 * @param examiner
	 *            Pruefungssteller/-in
	 */
	public void setExaminer( EspEntityUser examiner )
	{
		this.m_examiner = examiner;
	}

	/**
	 * Getter fuer die Anzahl benoetigter Aufsichtspersonen.
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
	 * Setter fuer die Anzahl benoetigter Aufsichtspersonen.
	 * 
	 * @param numSupervisors
	 *            Anzahl benoetigter Aufsichtspersonen
	 */
	public void setNumSupervisors( Integer numSupervisors )
	{
		this.m_numSupervisors = numSupervisors;
	}

	/**
	 * Getter fuer die Liste der zugeordneten Raeume in denen die Pruefung durchgefuehrt wird.
	 * 
	 * @return roomAssignments
	 */
	@Valid
	@OneToMany( fetch = FetchType.LAZY, mappedBy = "exam" )
	public List<EspEntityExamRoomAssignment> getRoomAssignments()
	{
		return this.m_roomAssignments;
	}

	/**
	 * Setter fuer die Liste der zugeordneten Raeume in denen die Pruefung durchgefuehrt wird.
	 * 
	 * @param roomAssignments
	 *            Liste der zugeordneten Raeume in denen die Pruefung durchgefuehrt wird
	 */
	public void setRoomAssignments( List<EspEntityExamRoomAssignment> roomAssignments )
	{
		this.m_roomAssignments = roomAssignments;
	}

	/**
	 * Fuegt der Liste der zugeordneten Raeume in denen die Pruefung durchgefuehrt wird eine neue Zuordnung hinzu.
	 * 
	 * @param roomAssignment
	 *            Neu zuzuordnender Raum in dem die Pruefung durchgefuehrt wird
	 */
	public void addRoomAssignment( EspEntityExamRoomAssignment roomAssignment )
	{
		if (this.m_roomAssignments == null)
		{
			this.m_roomAssignments = new ArrayList<EspEntityExamRoomAssignment>();
		}

		if (!this.m_roomAssignments.contains( roomAssignment ))
		{
			this.m_roomAssignments.add( roomAssignment );
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
		if (getCode() == null || getCode().isEmpty())
		{
			throw new EspValidationException( "Ung�ltiger Pr�fungscode: '" + getCode() + "'!" );
		}

		if (getName() == null || getName().isEmpty())
		{
			throw new EspValidationException( "Ung�ltiger Pr�fungsname: '" + getName() + "'!" );
		}

		if (getExaminer() != null)
		{
			getExaminer().validate();
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
		hcb.append( getCode() );
		// hcb.append( getName() );
		// hcb.append( getExaminer() );
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
		if (!(obj instanceof EspEntityExam))
		{
			return false;
		}

		EspEntityExam exam = (EspEntityExam) obj;

		EqualsBuilder eb = new EqualsBuilder();
		eb.append( getCode(), exam.getCode() );
		// eb.append( getName(), exam.getName() );
		// eb.append( getExaminer(), exam.getExaminer() );
		// eb.append( getNumSupervisors(), exam.getNumSupervisors() );

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
		sb.append( "code: " ).append( getCode() );
		sb.append( ", name: " ).append( getName() );
		sb.append( ", examiner: [" ).append( getExaminer().toString() ).append( "]" );
		sb.append( ", numSupervisors: " ).append( getNumSupervisors() );

		return sb.toString();
	}
}
