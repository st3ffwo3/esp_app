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
 * Klasse repraesentiert die Ressource Pruefung.
 * 
 * @author Stefan Wörner
 */
@XmlRootElement( name = "Examination" )
@XmlType( propOrder = { "name", "code", "examiner", "examDate", "room", "numSupervisors", "currentSupervisors" } )
@XmlAccessorOrder( XmlAccessOrder.ALPHABETICAL )
@XmlAccessorType( XmlAccessType.NONE )
@JsonPropertyOrder( value = { "Name", "Code", "Examiner", "ExamineDate", "Room", "NecessarySupervisors", "Supervisors" },
					alphabetic = true )
@JsonSerialize( include = Inclusion.NON_NULL )
@Produces( { "application/xml", "application/json", "text/xml" } )
@Consumes( { "application/xml", "application/json", "text/xml" } )
public class EspExam extends AbstractEspRessourceObject
{

	private static final long serialVersionUID = 8486367759913129233L;

	private String m_name;

	private String m_code;

	private EspExaminer m_examiner;

	private EspExamDate m_examDate;

	private String m_room;

	private Integer m_numSupervisors;

	private List<EspSupervisor> m_currentSupervisors;

	/**
	 * Standardkonstruktor.
	 */
	public EspExam()
	{
		this.m_currentSupervisors = new ArrayList<EspSupervisor>();
	}

	/**
	 * Getter fuer den Namen der Pruefung.
	 * 
	 * @return name
	 */
	@XmlElement( name = "Name" )
	@JsonProperty( "Name" )
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
	@JsonProperty( "Name" )
	public void setName( String name )
	{
		this.m_name = name;
	}

	/**
	 * Getter fuer den Pruefungscode.
	 * 
	 * @return code
	 */
	@XmlElement( name = "Code" )
	@JsonProperty( "Code" )
	public String getCode()
	{
		return this.m_code;
	}

	/**
	 * Setter fuer den Pruefungscode.
	 * 
	 * @param code
	 *            Pruefungscode
	 */
	@JsonProperty( "Code" )
	public void setCode( String code )
	{
		this.m_code = code;
	}

	/**
	 * Getter fuer den Pruefer.
	 * 
	 * @return examiner
	 */
	@XmlElement( name = "Examiner" )
	@JsonProperty( "Examiner" )
	public EspExaminer getExaminer()
	{
		return this.m_examiner;
	}

	/**
	 * Setter fuer den Pruefer.
	 * 
	 * @param examiner
	 *            Pruefer
	 */
	@JsonProperty( "Examiner" )
	public void setExaminer( EspExaminer examiner )
	{
		this.m_examiner = examiner;
	}

	/**
	 * Getter fuer das Pruefungsdatum.
	 * 
	 * @return examDate
	 */
	@XmlElement( name = "ExamineDate" )
	@JsonProperty( "ExamineDate" )
	public EspExamDate getExamDate()
	{
		return this.m_examDate;
	}

	/**
	 * Setter fuer das Pruefungsdatum.
	 * 
	 * @param examDate
	 *            Pruefungsdatum
	 */
	@JsonProperty( "ExamineDate" )
	public void setExamDate( EspExamDate examDate )
	{
		this.m_examDate = examDate;
	}

	/**
	 * Getter fuer den Pruefungsraum.
	 * 
	 * @return room
	 */
	@XmlElement( name = "Room" )
	@JsonProperty( "Room" )
	public String getRoom()
	{
		return this.m_room;
	}

	/**
	 * Setter fuer den Pruefungsraum.
	 * 
	 * @param room
	 *            Pruefungsraum
	 */
	@JsonProperty( "Room" )
	public void setRoom( String room )
	{
		this.m_room = room;
	}

	/**
	 * Getter fuer die Anzahl benoetigter Aufsichtspersonen.
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
	 * Setter fuer die Anzahl benoetigter Aufsichtspersonen.
	 * 
	 * @param numSupervisors
	 *            Anzahl benoetigter Aufsichtspersonen
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
	 * Fuegt der Liste der Aufsichtspersonen fuer die Pruefung eine neue Aufsichtsperson hinzu (falls diese noch nicht
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
		hcb.append( getCode() );
		hcb.append( getName() );
		hcb.append( getExaminer() );
		hcb.append( getNumSupervisors() );
		hcb.append( getExamDate() );
		hcb.append( getRoom() );
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
		if (!(obj instanceof EspExam))
		{
			return false;
		}

		EspExam exam = (EspExam) obj;

		EqualsBuilder eb = new EqualsBuilder();
		eb.append( getCode(), exam.getCode() );
		eb.append( getName(), exam.getName() );
		eb.append( getExaminer(), exam.getExaminer() );
		eb.append( getNumSupervisors(), exam.getNumSupervisors() );
		eb.append( getExamDate(), exam.getExamDate() );
		eb.append( getRoom(), exam.getRoom() );
		eb.append( getCurrentSupervisors(), exam.getCurrentSupervisors() );

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
		sb.append( "code: " ).append( getCode() );
		sb.append( ", name: " ).append( getName() );
		sb.append( ", numSupervisors: " ).append( getNumSupervisors() );
		sb.append( ", room: " ).append( getRoom() );

		if (getExaminer() != null)
		{
			sb.append( ", examiner: [" ).append( getExaminer().toString() ).append( "]" );
		}
		else
		{
			sb.append( ", examiner: [null]" );
		}

		if (getExamDate() != null)
		{
			sb.append( ", examDate: [" ).append( getExamDate().toString() ).append( "]" );
		}
		else
		{
			sb.append( ", examDate: [null]" );
		}

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
