package edu.hm.esp.api.jaxb.adapter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import edu.hm.esp.api.object.EspApiConstants;

/**
 * Datumsformatierer fuer JAXB (XML Marschalling und Unmarschalling).
 * 
 * @author Stefan Wörner
 */
public class EspDateAdapter extends XmlAdapter<String, Date>
{

	private static final DateFormat FORMATTER = new SimpleDateFormat( EspApiConstants.DEFAULT_DATE_FORMAT );

	/**
	 * {@inheritDoc}
	 * 
	 * @see javax.xml.bind.annotation.adapters.XmlAdapter#unmarshal(java.lang.Object)
	 */
	@Override
	public Date unmarshal( String date ) throws ParseException
	{
		return FORMATTER.parse( date );
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see javax.xml.bind.annotation.adapters.XmlAdapter#marshal(java.lang.Object)
	 */
	@Override
	public String marshal( Date date )
	{
		return FORMATTER.format( date );
	}
}
