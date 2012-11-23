/*******************************************************************************
 * Este fichero forma parte del Cliente @firma.
 * El Cliente @firma es un aplicativo de libre distribucion cuyo codigo fuente puede ser consultado
 * y descargado desde http://forja-ctt.administracionelectronica.gob.es/
 * Copyright 2009,2010,2011 Gobierno de Espana
 * Este fichero se distribuye bajo  bajo licencia GPL version 2  segun las
 * condiciones que figuran en el fichero 'licence' que se acompana. Si se distribuyera este
 * fichero individualmente, deben incluirse aqui las condiciones expresadas alli.
 ******************************************************************************/

package es.gob.afirma.keystores.filters.old;

import java.util.regex.Pattern;


/** @deprecated */
@Deprecated
interface ConditionConstants {
      String SEQ = "([^\"]|[^\\{\\}])*"; //$NON-NLS-1$

      String VALUE_PATTERN = "\\{\"" + SEQ + "\"\\}()"; //$NON-NLS-1$ //$NON-NLS-2$

      String HASH_ALG_PATTERN = "(MD5|SHA1)"; //$NON-NLS-1$

      String NEXO_PATTERN = "(\\&\\&|\\|\\|)"; //$NON-NLS-1$

      String OPERATOR_PATTERN = "(\\=|\\#MATCHES\\#|\\#NOT_MATCHES\\#)"; //$NON-NLS-1$

      String FIELD_PATTERN = "((ISSUER\\.|SUBJECT\\.)(DN|SN|SERIALNUMBER|FP\\(" + HASH_ALG_PATTERN + "\\)))"; //$NON-NLS-1$ //$NON-NLS-2$

      String SIMPLE_CONDITION_PATTERN = FIELD_PATTERN + OPERATOR_PATTERN + VALUE_PATTERN;

      String COMPOUND_CONDITION_PATTERN = "\\{" + SIMPLE_CONDITION_PATTERN + "(" + NEXO_PATTERN + SIMPLE_CONDITION_PATTERN + ")*\\}"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

      Pattern COMPILED_COMPOUND_CONDITION_PATTERN = Pattern.compile(COMPOUND_CONDITION_PATTERN);

      Pattern COMPILED_NEXUS_CONDITION_PATTERN = Pattern.compile(NEXO_PATTERN);

      Pattern COMPILED_FIELD_CONDITION_PATTERN = Pattern.compile(FIELD_PATTERN);

      Pattern COMPILED_VALUE_CONDITION_PATTERN = Pattern.compile(VALUE_PATTERN);

      Pattern COMPILED_HASH_ALG_PATTERN = Pattern.compile(HASH_ALG_PATTERN);

      Pattern COMPILED_OPERATOR_PATTERN = Pattern.compile(OPERATOR_PATTERN);

}
