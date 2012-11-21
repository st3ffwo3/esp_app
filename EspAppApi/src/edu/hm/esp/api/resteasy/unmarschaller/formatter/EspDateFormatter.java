package edu.hm.esp.api.resteasy.unmarschaller.formatter;

import java.lang.annotation.Annotation;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jboss.resteasy.spi.StringParameterUnmarshaller;
import org.jboss.resteasy.util.FindAnnotation;

import edu.hm.esp.api.resteasy.unmarschaller.EspDateFormat;

/**
 * Klasse ist beim Unmarschalling durch RESTeasy fuer die Datumsformatierung zustaendig.
 * 
 * @author Stefan Wörner
 */
public final class EspDateFormatter implements StringParameterUnmarshaller<Date>
{

	private SimpleDateFormat m_formatter;

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.jboss.resteasy.spi.StringParameterUnmarshaller#setAnnotations(java.lang.annotation.Annotation[])
	 */
	@Override
	public void setAnnotations( Annotation[] annotations )
	{
		EspDateFormat format = FindAnnotation.findAnnotation( annotations, EspDateFormat.class );
		m_formatter = new SimpleDateFormat( format.value() );
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.jboss.resteasy.spi.StringParameterUnmarshaller#fromString(java.lang.String)
	 */
	@Override
	public Date fromString( String str )
	{
		try
		{
			return m_formatter.parse( str );
		}
		catch (ParseException e)
		{
			throw new RuntimeException( e );
		}
	}
}
