/*
 * Este fichero forma parte del Cliente @firma. 
 * El Cliente @firma es un applet de libre distribuci�n cuyo c�digo fuente puede ser consultado
 * y descargado desde www.ctt.map.es.
 * Copyright 2009,2010 Gobierno de Espa�a
 * Este fichero se distribuye bajo las licencias EUPL versi�n 1.1  y GPL versi�n 3, o superiores, seg�n las
 * condiciones que figuran en el fichero 'LICENSE.txt' que se acompa�a.  Si se   distribuyera este 
 * fichero individualmente, deben incluirse aqu� las condiciones expresadas all�.
 */


package es.gob.afirma.cliente.utilidades.browser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.logging.Logger;

import es.gob.aoclasses.GraphicalFileInputStream;

class Attachment
{
    public final String url;
    //public final byte[] bytes;
    
    public Attachment(String url)
    {
        this.url= url;
    //    this.bytes= bytes;
    }
    
    public String getName()
    {
        int p1= url.lastIndexOf(File.pathSeparatorChar);
        return p1<url.length()?url.substring(p1+1):"";
    }
    
    public File getFile()
    {
        return new File(url);
    }

    public GraphicalFileInputStream getContentInputStream() throws FileNotFoundException
    {
        Logger.getLogger("es.gob.afirma").info(url);
        GraphicalFileInputStream is= new GraphicalFileInputStream(new File(url));
        return is;
    }
}
