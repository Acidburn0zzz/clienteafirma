/*
 * Este fichero forma parte del Cliente @firma. 
 * El Cliente @firma es un applet de libre distribuci�n cuyo c�digo fuente puede ser consultado
 * y descargado desde www.ctt.map.es.
 * Copyright 2009,2010 Gobierno de Espa�a
 * Este fichero se distribuye bajo las licencias EUPL versi�n 1.1  y GPL versi�n 3, o superiores,
 * seg�n las condiciones que figuran en el fichero 'LICENSE.txt' que se acompa�a.  Si se distribuyera
 * este fichero individualmente, deben incluirse aqu� las condiciones expresadas all�.
 */


#include "jni.h"
/* Header for class es_gob_afirma_misc_AOWinNativeUtil */

#ifndef _Included_es_gob_afirma_misc_AOWinNativeUtil
#define _Included_es_gob_afirma_misc_AOWinNativeUtil
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     es_gob_afirma_misc_AOWinNativeUtil
 * Method:    getShortPathName
 * Signature: (Ljava/lang/String;)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_es_gob_afirma_misc_AOWinNativeUtil_getShortPathName  (JNIEnv *, jobject, jstring);

/*
 * Class:     es_gob_afirma_misc_AOWinNativeUtil
 * Method:    getLongPathName
 * Signature: (Ljava/lang/String;)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_es_gob_afirma_misc_AOWinNativeUtil_getLongPathName  (JNIEnv *, jobject, jstring);

#ifdef __cplusplus
}
#endif
#endif
