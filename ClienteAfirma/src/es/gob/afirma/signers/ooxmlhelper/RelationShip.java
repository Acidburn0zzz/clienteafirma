/*
 * Este fichero forma parte del Cliente @firma. 
 * El Cliente @firma es un applet de libre distribuci�n cuyo c�digo fuente puede ser consultado
 * y descargado desde www.ctt.map.es.
 * Copyright 2009,2010 Gobierno de Espa�a
 * Este fichero se distribuye bajo las licencias EUPL versi�n 1.1  y GPL versi�n 3, o superiores, seg�n las
 * condiciones que figuran en el fichero 'LICENSE.txt' que se acompa�a.  Si se   distribuyera este 
 * fichero individualmente, deben incluirse aqu� las condiciones expresadas all�.
 */


package es.gob.afirma.signers.ooxmlhelper;

/** Relaci&oacute;n XML seg&uacute;n la normativa OOXML. */
public class RelationShip {
    
    /** Tipo de la relaci&oacute;n principal del documento. */ 
    public final static String DOCUMENT_RELATIONSHIP_TYPE = 
        "http://schemas.openxmlformats.org/officeDocument/2006/relationships/officeDocument";
    
    private String id = null;
    private String type = null;
    private String target = null;
    
    /**
     * Construye un objeto de relaci&oacute;n OOXML.
     * @param id Identificador de la relaci&oacute;n
     * @param type Typo de la relaci&oacute;n
     * @param target Destino de la relaci&oacute;n (objeto relacionado)
     */
    public RelationShip(String id, String type, String target) {
        this.id = id;
        this.type = type;
        this.target = target;
    }
    
    /** 
     * Obtiene el identificador de la relaci&oacute;n.
     * @return Identificador de la relaci&oacute;n
     */
    public String getId() {
        return id;
    }
    
    /**
     * Obtiene el tipo de la relaci&oacute;n.
     * @return Tipo de la relaci&oacute;n
     */
    public String getType() {
        return type;
    }
    
    /**
     * Obtiene el destino de la relaci&oacute;n (el objeto relacionado)
     * @return Destino de la relaci&oacute;n (objeto relacionado)
     */
    public String getTarget() {
        return target;
    }
    
    @Override
    public String toString() {
        return "<Relationship Id=\""+id+"\" Type=\""+type+"\" Target=\""+target+"\"/>";
    }
}
