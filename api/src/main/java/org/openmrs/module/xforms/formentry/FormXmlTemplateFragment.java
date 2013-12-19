package org.openmrs.module.xforms.formentry;

import java.io.UnsupportedEncodingException;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Form;
import org.openmrs.module.xforms.XformConstants;

/**
 * Convenience class for generating various fragments of an XML template for
 * OpenMRS forms.
 * 
 * @author Burke Mamlin
 * @version 1.0
 * @see org.openmrs.formentry.FormXmlTemplateBuilder
 */
public class FormXmlTemplateFragment {

	static Log log = LogFactory.getLog(FormXmlTemplateBuilder.class);

	public static String header(Form form, String url) {
		return "<?xml version=\"1.0\"?>\n" + "<?mso-infoPathSolution name=\""
				+ encodeURL(form.getName()) + "\" href=\"" + url
				+ "\" solutionVersion=\""
				+ FormEntryWrapper.getSolutionVersion(form)
				+ "\" productVersion=\"11.0.6357\" PIVersion=\"1.0.0.0\" ?>\n"
				+ "<?mso-application progid=\"InfoPath.Document\"?>\n";
	}

	public static String openForm(Form form, String namespace,
			boolean includeDefaultScripts) {
		return "<form id=\""
				+ form.getFormId()
				+ "\" name=\""
				+ StringEscapeUtils.escapeXml(form.getName())
				+ "\" "
				+ "version=\""
				+ form.getVersion()
				+ "\" "
				+ "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
				+ "xmlns:openmrs=\""
				+ namespace
				+ "\" "
				+ "xmlns:xd=\"http://schemas.microsoft.com/office/infopath/2003\">\n"
				+ "  <header>\n" + "    <enterer>"
				+ (includeDefaultScripts ? "$!{enterer}" : "") + "</enterer>\n"
				+ "    <date_entered>"
				+ (includeDefaultScripts ? "$!{dateEntered}" : "")
				+ "</date_entered>\n" + "    <session>"
				+ (includeDefaultScripts ? "$!{sessionId}" : "")
				+ "</session>\n" + "    <uid>"
				+ (includeDefaultScripts ? "$!{uid}" : "") + "</uid>\n"
				+ "  </header>\n";
	}

	public static String closeForm() {
		return "  </form>\n";
	}

	private static String encodeURL(String s) {
		String encodedString = s;
		try {
			java.net.URLEncoder.encode(s, XformConstants.DEFAULT_CHARACTER_ENCODING);
		} catch (UnsupportedEncodingException e) {
			log.error("Failed to recognize UTF-8 when encoding string: '" + s
					+ "'");
		}
		return encodedString;
	}
}
