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
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import edu.hm.esp.internal.exception.EspValidationException;
import edu.hm.esp.internal.object.AbstractEspEntityObject;

/**
 * Entitaet zur Role Tabelle.
 * 
 * @author Stefan Wörner
 */
@Entity
@Table( name = "Role", catalog = "ESP" )
public class EspEntityRole extends AbstractEspEntityObject
{

	private static final long serialVersionUID = 7960062777179846158L;

	/*
	 * Primary Key
	 */
	private String m_name;

	/*
	 * References / Relations
	 */
	private List<EspEntityUser> m_users;

	/**
	 * Standardkonstruktor.
	 */
	public EspEntityRole()
	{
		super();
		this.m_users = new ArrayList<EspEntityUser>();
	}

	/**
	 * Konstruktur mit Pflicht-Attributen.
	 * 
	 * @param name
	 *            Name der Rolle
	 */
	public EspEntityRole( String name )
	{
		this();
		this.m_name = name;
	}

	/**
	 * Konstruktur mit allen Attributen.
	 * 
	 * @param name
	 *            Name der Rolle
	 * @param users
	 *            Benutzer welche die Rolle besitzen
	 */
	public EspEntityRole( String name, List<EspEntityUser> users )
	{
		this( name );
		this.m_users = users;
	}

	/**
	 * Getter fuer den Namen der Rolle.
	 * 
	 * @return name
	 */
	@Id
	@NotNull( message = "Rollenname muss gesetzt sein" )
	@NotEmpty( message = "Rollenname darf nicht leer sein" )
	@Pattern( regexp = "[\\p{Alpha}]+", message = "Ung�ltiger Rollenname" )
	@Length( max = 10, message = "Rollenname darf aus maximal 100 Zeichen bestehen" )
	@Column( name = "Name", nullable = false, length = 100 )
	public String getName()
	{
		return this.m_name;
	}

	/**
	 * Setter fuer den Namen der Rolle.
	 * 
	 * @param name
	 *            Name der Rolle
	 */
	public void setName( String name )
	{
		this.m_name = name;
	}

	/**
	 * Getter fuer die Benutzer welche die Rolle besitzen.
	 * 
	 * @return users
	 */
	@Valid
	@ManyToMany( fetch = FetchType.LAZY )
	@JoinTable( name = "UserRoleAssignment",
				catalog = "ESP",
				joinColumns = { @JoinColumn( name = "Role_Name", nullable = false, updatable = false ) },
				inverseJoinColumns = { @JoinColumn( name = "User_LoginName", nullable = false, updatable = false ) } )
	public List<EspEntityUser> getUsers()
	{
		return this.m_users;
	}

	/**
	 * Setter fuer die Benutzer welche die Rolle besitzen.
	 * 
	 * @param users
	 *            Benutzer welche die Rolle besitzen
	 */
	public void setUsers( List<EspEntityUser> users )
	{
		this.m_users = users;
	}

	/**
	 * Fuegt einen Benutzer hinzu, falls dieser noch nicht vorhanden ist.
	 * 
	 * @param user
	 *            Hinzuzufuegender Benutzer
	 */
	public void addUser( EspEntityUser user )
	{
		if (this.m_users == null)
		{
			this.m_users = new ArrayList<EspEntityUser>();
		}

		if (!this.m_users.contains( user ))
		{
			this.m_users.add( user );
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
			throw new EspValidationException( "Ung�ltiger Rollenname: '" + getName() + "'!" );
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
		if (!(obj instanceof EspEntityRole))
		{
			return false;
		}

		EspEntityRole role = (EspEntityRole) obj;

		EqualsBuilder eb = new EqualsBuilder();
		eb.append( getName(), role.getName() );

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
