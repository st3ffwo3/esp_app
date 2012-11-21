package edu.hm.esp.internal.bean.database;

import java.util.List;

import javax.ejb.Local;

import edu.hm.esp.internal.object.entity.EspEntityExam;

/**
 * Interface beschreibt alle Methoden die von der Datenbank-Verwaltungs-Bean fuer Pruefungsplan-Objekte angeboten
 * werden.
 * 
 * @author Stefan Wörner
 */
@Local
public interface IEspExamPlanDatabaseLocal
{

	/**
	 * Konstante mit der JNDI-Binding-Information (local).
	 */
	String JNDI_BINDING = "EspApplication/EJB/EspExamPlanDatabaseLocal";

	/**
	 * Fuegt den uebergebenen Pruefungsplan in die Datenbank ein oder aktualisiert die zugehoerigen Pruefungen.
	 * 
	 * @param examPlan
	 *            Einzufufuegender Pruefungsplan (bestehend aus einer Liste von Pruefungen)
	 * @return Hinzugefuegte Pruefungen
	 */
	List<EspEntityExam> insertOrUpdateExamPlanWithoutReferenceUpdate( List<EspEntityExam> examPlan );
}
