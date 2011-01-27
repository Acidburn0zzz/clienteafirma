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

import java.util.regex.Pattern;

/** 
 * @deprecated Usar filtros compatibles RFC2254
 */
@Deprecated
interface Operator
{
    public static Operator EQ = new Operator()
    {
        public boolean eval(Object o1, Object o2)
        {
            return o1.equals(o2);
        }
    };

    public static Operator MATCHES = new Operator()
    {
        public boolean eval(Object str, Object pattern)
        {
            boolean matches;
            matches = Pattern.matches((String) pattern, (String) str);
            return matches;
        }
    };

    public static Operator NOT_MATCHES = new Operator()
    {
        public boolean eval(Object str, Object pattern)
        {
            boolean matches;
            matches = Pattern.matches((String) pattern, (String) str);
            return !matches;
        }
    };

    public boolean eval(Object o1, Object o2);
}
