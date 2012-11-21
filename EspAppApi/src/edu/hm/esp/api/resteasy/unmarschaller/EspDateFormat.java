package edu.hm.esp.api.resteasy.unmarschaller;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.jboss.resteasy.annotations.StringParameterUnmarshallerBinder;

import edu.hm.esp.api.object.EspApiConstants;
import edu.hm.esp.api.resteasy.unmarschaller.formatter.EspDateFormatter;

/**
 * Annotation das RESTeasy Unmarschalling von Datumsweten die einer bestimmten Datumsformatierung entsprechen muessen.
 * 
 * @author Stefan Wörner
 */
@Retention( RetentionPolicy.RUNTIME )
@StringParameterUnmarshallerBinder( EspDateFormatter.class )
public @interface EspDateFormat
{

	/**
	 * Legt das Datumsformat fest.
	 */
	String value() default EspApiConstants.DEFAULT_DATE_FORMAT;
}
