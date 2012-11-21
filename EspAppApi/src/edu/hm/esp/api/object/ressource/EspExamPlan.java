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
 * Klasse repraesentiert die Ressource Pruefungsplan und enthaelt damit alle Pruefungen.
 * 
 * @author Stefan Wörner
 */
@XmlRootElement( name = "Examplan" )
@XmlType( propOrder = { "exams" } )
@XmlAccessorOrder( XmlAccessOrder.ALPHABETICAL )
@XmlAccessorType( XmlAccessType.NONE )
@JsonPropertyOrder( value = { "Examinations" }, alphabetic = true )
@JsonSerialize( include = Inclusion.NON_NULL )
@Produces( { "application/xml", "application/json", "text/xml" } )
@Consumes( { "application/xml", "application/json", "text/xml" } )
public class EspExamPlan extends AbstractEspRessourceObject
{

	private static final long serialVersionUID = 7420052283221541673L;

	private List<EspExam> m_exams;

	/**
	 * Standardkonstruktor.
	 */
	public EspExamPlan()
	{
		this.m_exams = new ArrayList<EspExam>();
	}

	/**
	 * Getter fuer die Liste der Pruefungen.
	 * 
	 * @return exams
	 */
	@XmlElement( name = "Examination" )
	@JsonProperty( "Examinations" )
	public List<EspExam> getExams()
	{
		return this.m_exams;
	}

	/**
	 * Setter fuer die Liste der Pruefungen.
	 * 
	 * @param exams
	 *            Liste der Pruefungen
	 */
	@JsonProperty( "Examinations" )
	public void setExams( List<EspExam> exams )
	{
		this.m_exams = exams;
	}

	/**
	 * Fuegt der Liste der Pruefungen einen neuen Pruefung hinzu (falls diese noch nicht enthalten ist).
	 * 
	 * @param exam
	 *            Hinzuzufuegende Pruefung
	 */
	public void addExam( EspExam exam )
	{
		if (!this.m_exams.contains( exam ))
		{
			this.m_exams.add( exam );
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
		hcb.append( getExams() );

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
		if (!(obj instanceof EspExamPlan))
		{
			return false;
		}

		EspExamPlan examPlan = (EspExamPlan) obj;

		EqualsBuilder eb = new EqualsBuilder();
		eb.append( getExams(), examPlan.getExams() );

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
		if (getExams() != null)
		{
			sb.append( "exams: [{" );

			for (EspExam exam : getExams())
			{
				sb.append( exam.toString() ).append( "},{" );
			}

			sb.deleteCharAt( sb.length() - 1 ).deleteCharAt( sb.length() - 1 ).append( "]" );
		}
		else
		{
			sb.append( "exams: [null]" );
		}

		return sb.toString();
	}
}
