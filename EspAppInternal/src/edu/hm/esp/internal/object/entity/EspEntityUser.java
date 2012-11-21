package edu.hm.esp.internal.object.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.validator.Email;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotEmpty;

import edu.hm.esp.internal.exception.EspValidationException;
import edu.hm.esp.internal.object.AbstractEspEntityObject;

/**
 * Entitaet zur User Tabelle.
 * 
 * @author Stefan Wörner
 */
@Entity
@Table( name = "User", catalog = "ESP" )
public class EspEntityUser extends AbstractEspEntityObject
{

	private static final long serialVersionUID = -1393229250474588200L;

	/*
	 * Primary Key
	 */
	private String m_loginName;

	/*
	 * Mandatory
	 */
	private String m_displayName;

	/*
	 * Optional
	 */
	private String m_password;

	private String m_mailAddress;

	private Integer m_numSupervisions;

	/*
	 * References / Relations
	 */
	private List<EspEntityRole> m_roles;

	private List<EspEntityExam> m_exams;

	private List<EspEntityExamRoomAssignment> m_roomSupervisions;

	private List<EspEntityTimeSlot> m_timeSlotSupervisions;

	/**
	 * Standardkonstruktor.
	 */
	public EspEntityUser()
	{
		super();
		this.m_roles = new ArrayList<EspEntityRole>();
		this.m_exams = new ArrayList<EspEntityExam>();
		this.m_roomSupervisions = new ArrayList<EspEntityExamRoomAssignment>();
		this.m_timeSlotSupervisions = new ArrayList<EspEntityTimeSlot>();
	}

	/**
	 * Konstruktur mit Pflicht-Attributen.
	 * 
	 * @param loginName
	 *            LoginName des Benutzers
	 * @param displayName
	 *            Anzeigename (Vorname Nachname) des Benutzers
	 */
	public EspEntityUser( String loginName, String displayName )
	{
		this();
		this.m_loginName = loginName.toLowerCase();
		this.m_displayName = displayName;
	}

	/**
	 * Konstruktur mit allen Attributen.
	 * 
	 * @param loginName
	 *            LoginName des Benutzers
	 * @param displayName
	 *            Anzeigename (Vorname Nachname) des Benutzers
	 * @param password
	 *            Password des Benutzers
	 * @param mailAddress
	 *            eMail Adresse des Benutzers
	 * @param numSupervisions
	 *            Anzahl zu beaufsichtigender Pruefungen
	 * @param roles
	 *            Rolle(n) welche dem Benutzer zugeordnet sind
	 * @param exams
	 *            Pruefungen fuer welche der Benutzer der Pruefungssteller ist
	 * @param roomSupervisions
	 *            Pruefungen fuer welche der Benutzer als Aufsichtsperson eingetragen ist (Raum-Aufsicht)
	 * @param timeSlotSupervisions
	 *            Zeitslots fuer welche der Benutzer als Aufsichtsperson eingetragen ist (Zeitslot-Aufsicht)
	 */
	public EspEntityUser( String loginName, String displayName, String password, String mailAddress, Integer numSupervisions,
			List<EspEntityRole> roles, List<EspEntityExam> exams, List<EspEntityExamRoomAssignment> roomSupervisions,
			List<EspEntityTimeSlot> timeSlotSupervisions )
	{
		super();
		this.m_loginName = loginName.toLowerCase();
		this.m_password = password;
		this.m_displayName = displayName;
		this.m_mailAddress = mailAddress;
		this.m_numSupervisions = numSupervisions;
		this.m_roles = roles;
		this.m_exams = exams;
		this.m_roomSupervisions = roomSupervisions;
		this.m_timeSlotSupervisions = timeSlotSupervisions;
	}

	/**
	 * Getter fuer den LoginNamen des Benutzers.
	 * 
	 * @return loginName
	 */
	@Id
	@NotNull( message = "LoginName muss gesetzt sein" )
	@NotEmpty( message = "LoginName darf nicht leer sein" )
	@Pattern( regexp = "[\\p{L}\\p{Space}\\p{Digit}-]+", message = "Ung�ltiger LoginName" )
	@Length( max = 100, message = "LoginName darf aus maximal 100 Zeichen bestehen" )
	@Column( name = "LoginName", unique = true, nullable = false, length = 100 )
	public String getLoginName()
	{
		return this.m_loginName;
	}

	/**
	 * Setter fuer den LoginNamen des Benutzers.
	 * 
	 * @param loginName
	 *            LoginName des Benutzers
	 */
	public void setLoginName( String loginName )
	{
		this.m_loginName = loginName.toLowerCase();
	}

	/**
	 * Getter fuer den Anzeigenamen (Vorname Nachname) des Benutzers.
	 * 
	 * @return displayName
	 */
	@NotNull( message = "Anzeigename muss gesetzt sein" )
	@NotEmpty( message = "Anzeigename darf nicht leer sein" )
	@Pattern( regexp = "[\\p{L}\\p{Space}\\p{Digit}-]+", message = "Ung�ltiger Anzeigename" )
	@Length( max = 200, message = "Anzeigename darf aus maximal 200 Zeichen bestehen" )
	@Column( name = "DisplayName", nullable = false, length = 200 )
	public String getDisplayName()
	{
		return this.m_displayName;
	}

	/**
	 * Setter fuer den Anzeigenamen (Vorname Nachname) des Benutzers.
	 * 
	 * @param displayName
	 *            Anzeigename (Vorname Nachname) des Benutzers
	 */
	public void setDisplayName( String displayName )
	{
		this.m_displayName = displayName;
	}

	/**
	 * Getter fuer das Password des Benutzers.
	 * 
	 * @return password
	 */
	@Column( name = "Password", length = 100 )
	public String getPassword()
	{
		return this.m_password;
	}

	/**
	 * Setter fuer das Password des Benutzers.
	 * 
	 * @param password
	 *            Password des Benutzers
	 */
	public void setPassword( String password )
	{
		this.m_password = password;
	}

	/**
	 * Getter fuer die eMail Adresse des Benutzers.
	 * 
	 * @return mailAddress
	 */
	@Email( message = "Ung�ltige E-Mail Adresse" )
	// @NotEmpty( message = "E-Mail Adresse darf nicht leer sein" )
	@Length( max = 200, message = "E-Mail Adresse darf aus maximal 200 Zeichen bestehen" )
	@Column( name = "MailAddress", length = 200 )
	public String getMailAddress()
	{
		return this.m_mailAddress;
	}

	/**
	 * Setter fuer die eMail Adresse des Benutzers.
	 * 
	 * @param mailAddress
	 *            eMail Adresse des Benutzers
	 */
	public void setMailAddress( String mailAddress )
	{
		this.m_mailAddress = mailAddress;
	}

	/**
	 * Getter fuer die Anzahl zu beaufsichtigender Pruefungen.
	 * 
	 * @return numSupervisions
	 */
	@Max( value = 100, message = "Anzahl Aufsichten darf maximal 100 sein" )
	@Column( name = "NumSupervisions" )
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
	public void setNumSupervisions( Integer numSupervisions )
	{
		this.m_numSupervisions = numSupervisions;
	}

	/**
	 * Getter fuer die Rolle(n) welche dem Benutzer zugeordnet sind.
	 * 
	 * @return roles
	 */
	@Valid
	@ManyToMany( fetch = FetchType.EAGER )
	@JoinTable( name = "UserRoleAssignment",
				catalog = "ESP",
				joinColumns = { @JoinColumn( name = "User_LoginName", nullable = false, updatable = false ) },
				inverseJoinColumns = { @JoinColumn( name = "Role_Name", nullable = false, updatable = false ) } )
	public List<EspEntityRole> getRoles()
	{
		return this.m_roles;
	}

	/**
	 * Setter fuer die Rolle(n) welche dem Benutzer zugeordnet sind.
	 * 
	 * @param roles
	 *            Rolle(n) welche dem Benutzer zugeordnet sind
	 */
	public void setRoles( List<EspEntityRole> roles )
	{
		this.m_roles = roles;
	}

	/**
	 * Fuegt dem Benutzer eine Rolle hinzu, falls diese noch nicht vorhanden ist.
	 * 
	 * @param role
	 *            Hinzuzufuegende Rolle
	 */
	public void addRole( EspEntityRole role )
	{
		if (this.m_roles == null)
		{
			this.m_roles = new ArrayList<EspEntityRole>();
		}

		if (!this.m_roles.contains( role ))
		{
			this.m_roles.add( role );
		}
	}

	/**
	 * Getter fuer die Pruefungen fuer welche der Benutzer der Pruefungssteller ist.
	 * 
	 * @return exams
	 */
	@Valid
	@OneToMany( fetch = FetchType.LAZY, mappedBy = "examiner" )
	public List<EspEntityExam> getExams()
	{
		return this.m_exams;
	}

	/**
	 * Setter fuer die Pruefungen fuer welche der Benutzer der Pruefungssteller ist.
	 * 
	 * @param exams
	 *            Pruefungen fuer welche der Benutzer der Pruefungssteller ist
	 */
	public void setExams( List<EspEntityExam> exams )
	{
		this.m_exams = exams;
	}

	/**
	 * Fuegt dem Benutzer eine Pruefung hinzu, falls diese noch nicht vorhanden ist.
	 * 
	 * @param exam
	 *            Hinzuzufuegende Pruefung
	 */
	public void addExam( EspEntityExam exam )
	{
		if (this.m_exams == null)
		{
			this.m_exams = new ArrayList<EspEntityExam>();
		}

		if (!this.m_exams.contains( exam ))
		{
			this.m_exams.add( exam );
		}
	}

	/**
	 * Getter fuer die Pruefungen fuer welche der Benutzer als Aufsichtsperson eingetragen ist (Raum-Aufsicht).
	 * 
	 * @return roomSupervisions
	 */
	@Valid
	@ManyToMany( fetch = FetchType.LAZY )
	@JoinTable( name = "UserRoomSupervision",
				catalog = "ESP",
				joinColumns = { @JoinColumn( name = "Supervisor_LoginName", nullable = false, updatable = false ) },
				inverseJoinColumns = { @JoinColumn( name = "Exam_Code", nullable = false, updatable = false ),
						@JoinColumn( name = "Room_Name", nullable = false, updatable = false ) } )
	public List<EspEntityExamRoomAssignment> getRoomSupervisions()
	{
		return this.m_roomSupervisions;
	}

	/**
	 * Setter fuer die Pruefungen fuer welche der Benutzer als Aufsichtsperson eingetragen ist (Raum-Aufsicht).
	 * 
	 * @param roomSupervisions
	 *            Pruefungen fuer welche der Benutzer als Aufsichtsperson eingetragen ist (Raum-Aufsicht)
	 */
	public void setRoomSupervisions( List<EspEntityExamRoomAssignment> roomSupervisions )
	{
		this.m_roomSupervisions = roomSupervisions;
	}

	/**
	 * Fuegt dem Benutzer eine Pruefung fuer welche der Benutzer als Aufsichtsperson eingetragen ist (Raum-Aufsicht)
	 * hinzu, falls diese noch nicht vorhanden ist.
	 * 
	 * @param roomSupervision
	 *            Hinzuzufuegende Pruefung fuer welche der Benutzer als Aufsichtsperson eingetragen ist (Raum-Aufsicht)
	 */
	public void addRoomSupersision( EspEntityExamRoomAssignment roomSupervision )
	{
		if (this.m_roomSupervisions == null)
		{
			this.m_roomSupervisions = new ArrayList<EspEntityExamRoomAssignment>();
		}

		if (!this.m_roomSupervisions.contains( roomSupervision ))
		{
			this.m_roomSupervisions.add( roomSupervision );
		}
	}

	/**
	 * Getter fuer die Zeitslots fuer welche der Benutzer als Aufsichtsperson eingetragen ist (Zeitslot-Aufsicht).
	 * 
	 * @return timeSlotSupervisions
	 */
	@Valid
	@ManyToMany( fetch = FetchType.LAZY )
	@JoinTable( name = "UserTimeSlotSupervision",
				catalog = "ESP",
				joinColumns = { @JoinColumn( name = "Supervisor_LoginName", nullable = false, updatable = false ) },
				inverseJoinColumns = {
						@JoinColumn(	name = "TimeSlot_StartDate",
										referencedColumnName = "StartDate",
										nullable = false,
										updatable = false ),
						@JoinColumn(	name = "TimeSlot_EndDate",
										referencedColumnName = "EndDate",
										nullable = false,
										updatable = false ) } )
	public List<EspEntityTimeSlot> getTimeSlotSupervisions()
	{
		return this.m_timeSlotSupervisions;
	}

	/**
	 * Setter fuer die Zeitslots fuer welche der Benutzer als Aufsichtsperson eingetragen ist (Zeitslot-Aufsicht).
	 * 
	 * @param timeSlotSupervisions
	 *            Zeitslots fuer welche der Benutzer als Aufsichtsperson eingetragen ist (Zeitslot-Aufsicht)
	 */
	public void setTimeSlotSupervisions( List<EspEntityTimeSlot> timeSlotSupervisions )
	{
		this.m_timeSlotSupervisions = timeSlotSupervisions;
	}

	/**
	 * Fuegt dem Benutzer einen Zeitslot fuer welche der Benutzer als Aufsichtsperson eingetragen ist
	 * (Zeitslot-Aufsicht) hinzu, falls diese noch nicht vorhanden ist.
	 * 
	 * @param timeSlotSupervision
	 *            Hinzuzufuegende Zeitslot fuer welche der Benutzer als Aufsichtsperson eingetragen ist
	 *            (Zeitslot-Aufsicht)
	 */
	public void addTimeSlotSupervision( EspEntityTimeSlot timeSlotSupervision )
	{
		if (this.m_timeSlotSupervisions == null)
		{
			this.m_timeSlotSupervisions = new ArrayList<EspEntityTimeSlot>();
		}

		if (!this.m_timeSlotSupervisions.contains( timeSlotSupervision ))
		{
			this.m_timeSlotSupervisions.add( timeSlotSupervision );
		}
	}

	/**
	 * Prueft ob alle Attribute die zum Mailversand benoetigt werden gesetzt sind.
	 */
	public void validateMailability()
	{
		validate();

		if (getMailAddress() == null || getMailAddress().isEmpty())
		{
			throw new EspValidationException( "Ung�ltige E-Mail-Adresse: '" + getMailAddress() + "'!" );
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
		if (getLoginName() == null || getLoginName().isEmpty())
		{
			throw new EspValidationException( "Ung�ltiger Loginname: '" + getLoginName() + "'!" );
		}

		if (getDisplayName() == null || getDisplayName().isEmpty())
		{
			throw new EspValidationException( "Ung�ltiger Anzeigename: '" + getDisplayName() + "'!" );
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
		hcb.append( getLoginName() );
		// hcb.append( getDisplayName() );
		// hcb.append( getPassword() );
		// hcb.append( getMailAddress() );
		// hcb.append( getNumSupervisions() );

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
		if (!(obj instanceof EspEntityUser))
		{
			return false;
		}

		EspEntityUser user = (EspEntityUser) obj;

		EqualsBuilder eb = new EqualsBuilder();
		eb.append( getLoginName(), user.getLoginName() );
		// eb.append( getDisplayName(), user.getDisplayName() );
		// eb.append( getPassword(), user.getPassword() );
		// eb.append( getMailAddress(), user.getMailAddress() );
		// eb.append( getNumSupervisions(), user.getNumSupervisions() );

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
		sb.append( "loginName: " ).append( getLoginName() );
		sb.append( ", displayName: " ).append( getDisplayName() );
		sb.append( ", mailAddress: " ).append( getMailAddress() );
		sb.append( ", numSupervisions: " ).append( getNumSupervisions() );

		return sb.toString();
	}
}
