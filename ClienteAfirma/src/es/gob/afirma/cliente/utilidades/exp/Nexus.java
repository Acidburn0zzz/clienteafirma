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

/** 
 * @deprecated Usar filtros compatibles RFC2254
 */
@Deprecated
interface Nexus
{
    public static final Nexus AND = new Nexus()
    {
        public boolean eval(boolean b1, boolean b2)
        {
            return b1 && b2;
        }
    };

    public static final Nexus OR = new Nexus()
    {
        public boolean eval(boolean b1, boolean b2)
        {
            return b1 || b2;
        }
    };

    public boolean eval(boolean b1, boolean b2);
}
