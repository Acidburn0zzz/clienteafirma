/*
 * Este fichero forma parte del Cliente @firma. 
 * El Cliente @firma es un applet de libre distribuci�n cuyo c�digo fuente puede ser consultado
 * y descargado desde www.ctt.map.es.
 * Copyright 2009,2010 Gobierno de Espa�a
 * Este fichero se distribuye bajo las licencias EUPL versi�n 1.1  y GPL versi�n 3, o superiores, seg�n las
 * condiciones que figuran en el fichero 'LICENSE.txt' que se acompa�a.  Si se   distribuyera este 
 * fichero individualmente, deben incluirse aqu� las condiciones expresadas all�.
 */


package es.gob.afirma.cliente.utilidades.exp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Matcher;

import es.gob.afirma.exceptions.AOException;

/** 
 * @deprecated Usar filtros compatibles RFC2254
 */
@Deprecated
final class CompoundCondition extends AClause implements ConditionConstants {

    public CompoundCondition(String str) throws AOException
    {
        // Matcher simpleConditionMatcher = simpleConditionPattern.matcher(str);
        Matcher nexusConditionMatcher = nexusConditionPattern.matcher(str);

        Collection<Clause> cClauses = new ArrayList<Clause>();
        Collection<Nexus> cNexus = new ArrayList<Nexus>();

        if (nexusConditionMatcher.find())
        {
            int pos = 1;
            int ini = 0, fin = 0;

            String clause;
            while (nexusConditionMatcher.find(pos))
            {
                ini = nexusConditionMatcher.start();
                fin = nexusConditionMatcher.end();
                String myNexus = str.substring(ini, fin);
                cNexus.add(myNexus.equals("&&") ? Nexus.AND : Nexus.OR);

                clause = str.substring(pos, ini);
                cClauses.add(new SimpleCondition(clause));
                pos = fin;
            }
            clause = str.substring(fin, str.length() - 1);
            cClauses.add(new SimpleCondition(clause));
        }
        else
        {
            String clause = str.substring(1, str.length() - 1);
            cClauses.add(new SimpleCondition(clause));
        }

        clauses = cClauses.toArray(new Clause[cClauses.size()]);
        nexus = cNexus.toArray(new Nexus[cNexus.size()]);
    }
}
