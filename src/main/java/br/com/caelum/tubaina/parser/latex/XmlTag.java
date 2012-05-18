package br.com.caelum.tubaina.parser.latex;

import br.com.caelum.tubaina.TubainaException;
import br.com.caelum.tubaina.parser.Indentator;
import br.com.caelum.tubaina.parser.Tag;

@Deprecated
public class XmlTag implements Tag {

	private static final String MESSAGE = "[xml] Tag is deprecated and can't be used anymore. Use [code xml] instead";

	public XmlTag(Indentator indentator) {
		throw new TubainaException(MESSAGE);
	}
	
	public String parse(String string, String opts) {
		throw new TubainaException(MESSAGE);
	}


}
