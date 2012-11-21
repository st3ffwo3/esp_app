package edu.hm.esp.api.object;

/**
 * Abstrakte Basisklasse fuer alle API Ressource Objekte.
 * 
 * @author Stefan Wörner
 */
public abstract class AbstractEspRessourceObject extends AbstractEspApiObject
{

	private static final long serialVersionUID = -2814230137735050619L;

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
