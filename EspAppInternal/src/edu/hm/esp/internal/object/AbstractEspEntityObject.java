package edu.hm.esp.internal.object;

/**
 * Abstrakte Basisklasse fuer alle Entity Objekte.
 * 
 * @author Stefan Wörner
 */
public abstract class AbstractEspEntityObject extends AbstractEspInternalObject
{

	private static final long serialVersionUID = -2814230137735050619L;

	/**
	 * Prueft ob es sich um ein gueltiges Objekt handelt.
	 */
	public abstract void validate();

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.basic.object.AbstractBasicObject#getExclusionList()
	 */
	@Override
	protected String[] getExclusionList()
	{
		return null;
	}
}
