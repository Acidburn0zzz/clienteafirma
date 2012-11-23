package es.gob.afirma;

import java.io.InputStream;

import org.junit.Assert;
import org.junit.Test;

import es.gob.afirma.core.misc.AOUtil;
import es.gob.afirma.signature.SignValidity;
import es.gob.afirma.signature.SignValidity.SIGN_DETAIL_TYPE;
import es.gob.afirma.signature.ValidateBinarySignature;

/** Prueba de validaci&oacute;n de firma binaria. */
@SuppressWarnings({ "static-method", "null" })
public class TestValidatebinarySignature {

	private static final String SIGNATURE_CADES_IMPLICIT_FILENAME = "cades_implicita.csig"; //$NON-NLS-1$
	private static final String SIGNATURE_CMS_IMPLICIT_FILENAME = "cms_implicita.csig"; //$NON-NLS-1$
	private static final String SIGNATURE_CADES_EXPLICIT_FILENAME = "cades_explicita.csig"; //$NON-NLS-1$
	private static final String SIGNATURE_CMS_EXPLICIT_FILENAME = "cms_explicita.csig"; //$NON-NLS-1$
	private static final String DATA_FILENAME = "data.jpg"; //$NON-NLS-1$


	/** Prueba de validaci&oacute;n de firma CAdES impl&iacute;cita.
	 * @throws Exception */
	@Test
	public void testValidarFirmaCadesImplicita() throws Exception {

		final String signatureFile = SIGNATURE_CADES_IMPLICIT_FILENAME;

		final InputStream is = AOUtil.getCleanClassLoader().getResourceAsStream(signatureFile);
		final byte[] signature = AOUtil.getDataFromInputStream(is);
		is.close();

		SignValidity validity = null;
		try {
			validity = ValidateBinarySignature.validate(signature, null);
		}
		catch (final Exception e) {
			Assert.fail("Ocurrio el siguiente error durante la validacion de la firma " + signatureFile + ": " + e.toString()); //$NON-NLS-1$ //$NON-NLS-2$
		}

		Assert.assertNotNull("No se ha obtenido ningun resultado del proceso de validacion de la firma " + signatureFile, validity); //$NON-NLS-1$
		Assert.assertEquals("El resultado de la validacion no es correcto para la firma " + signatureFile, SIGN_DETAIL_TYPE.OK, validity.getValidity()); //$NON-NLS-1$
	}

	/** Prueba de validaci&oacute;n de firma CMS impl&iacute;cita.
	 * @throws Exception */
	@Test
	public void testValidarFirmaCmsImplicita() throws Exception {

		final String signatureFile = SIGNATURE_CMS_IMPLICIT_FILENAME;

		final InputStream is = AOUtil.getCleanClassLoader().getResourceAsStream(signatureFile);
		final byte[] signature = AOUtil.getDataFromInputStream(is);
		is.close();

		SignValidity validity = null;
		try {
			validity = ValidateBinarySignature.validate(signature, null);
		}
		catch (final Exception e) {
			Assert.fail("Ocurrio el siguiente error durante la validacion de la firma " + signatureFile + ": " + e.toString()); //$NON-NLS-1$ //$NON-NLS-2$
		}

		Assert.assertNotNull("No se ha obtenido ningun resultado del proceso de validacion de la firma " + signatureFile, validity); //$NON-NLS-1$
		Assert.assertEquals("El resultado de la validacion no es correcto para la firma " + signatureFile, SIGN_DETAIL_TYPE.OK, validity.getValidity()); //$NON-NLS-1$
	}

	/** Prueba de validaci&oacute;n de firma CAdES expl&iacute;cita.
	 * @throws Exception */
	@Test
	public void testValidarFirmaCadesExplicita() throws Exception {

		final String signatureFile = SIGNATURE_CADES_EXPLICIT_FILENAME;

		final InputStream signIs = AOUtil.getCleanClassLoader().getResourceAsStream(signatureFile);
		final byte[] signature = AOUtil.getDataFromInputStream(signIs);
		signIs.close();
		if (signature == null || signature.length == 0) {
			Assert.fail("No se ha cargado correctamente la firma a validar"); //$NON-NLS-1$
		}

		final String dataFile = DATA_FILENAME;

		final InputStream dataIs = AOUtil.getCleanClassLoader().getResourceAsStream(dataFile);
		final byte[] data = AOUtil.getDataFromInputStream(dataIs);
		dataIs.close();
		if (data == null || data.length == 0) {
			Assert.fail("No se han cargado correctamente los datos de la firma a validar"); //$NON-NLS-1$
		}

		SignValidity validity = null;
		try {
			validity = ValidateBinarySignature.validate(signature, data);
		}
		catch (final Exception e) {
			Assert.fail("Ocurrio el siguiente error durante la validacion de la firma " + signatureFile + ": " + e.toString()); //$NON-NLS-1$ //$NON-NLS-2$
		}

		Assert.assertNotNull("No se ha obtenido ningun resultado del proceso de validacion de la firma " + signatureFile, validity); //$NON-NLS-1$
		Assert.assertEquals("El resultado de la validacion no es correcto para la firma " + signatureFile, SIGN_DETAIL_TYPE.OK, validity.getValidity()); //$NON-NLS-1$
	}

	/** Prueba de validaci&oacute;n de firma CMS expl&iacute;cita.
	 * @throws Exception */
	@Test
	public void testValidarFirmaCmsExplicita() throws Exception {

		final String signatureFile = SIGNATURE_CMS_EXPLICIT_FILENAME;

		final InputStream signIs = AOUtil.getCleanClassLoader().getResourceAsStream(signatureFile);
		final byte[] signature = AOUtil.getDataFromInputStream(signIs);
		signIs.close();

		final String dataFile = DATA_FILENAME;

		final InputStream dataIs = AOUtil.getCleanClassLoader().getResourceAsStream(dataFile);
		final byte[] data = AOUtil.getDataFromInputStream(dataIs);
		dataIs.close();

		SignValidity validity = null;
		try {
			validity = ValidateBinarySignature.validate(signature, data);
		} catch (final Exception e) {
			Assert.fail("Ocurrio el siguiente error durante la validacion de la firma " + signatureFile + ": " + e.toString()); //$NON-NLS-1$ //$NON-NLS-2$
		}

		Assert.assertNotNull("No se ha obtenido ningun resultado del proceso de validacion de la firma " + signatureFile, validity); //$NON-NLS-1$
		Assert.assertEquals("El resultado de la validacion no es correcto para la firma " + signatureFile, SIGN_DETAIL_TYPE.OK, validity.getValidity()); //$NON-NLS-1$
	}
}
